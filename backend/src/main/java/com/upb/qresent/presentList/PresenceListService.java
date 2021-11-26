package com.upb.qresent.presentList;

import com.upb.qresent.course.Course;
import com.upb.qresent.course.CourseRepository;
import com.upb.qresent.qrCode.QRCode;
import com.upb.qresent.qrCode.QRCodeRepository;
import com.upb.qresent.user.User;
import com.upb.qresent.user.UserRepository;
import com.upb.qresent.utils.Constants;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Service
public class PresenceListService {
    private final PresenceListRepository presenceListRepository;
    private final CourseRepository courseRepository;
    private final QRCodeRepository qrCodeRepository;
    private final UserRepository userRepository;

    public PresenceListService(PresenceListRepository presenceListRepository, CourseRepository courseRepository, QRCodeRepository qrCodeRepository, UserRepository userRepository) {
        this.presenceListRepository = presenceListRepository;
        this.courseRepository = courseRepository;
        this.qrCodeRepository = qrCodeRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public PresenceList createPresenceList(ObjectId courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId.toString());

        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            ObjectId professorId = course.getProfessorId();
            PresenceList presenceList = presenceListRepository.save(new PresenceList(courseId, professorId, null, Date.from(Instant.now()), Date.from(Instant.now().plus(Duration.ofMillis(Constants.presenceListExpirationTime))), Set.of()));
            QRCode qrCode = qrCodeRepository.save(new QRCode(presenceList.getId(), Date.from(Instant.now()), Date.from(Instant.now().plus(Duration.ofMillis(Constants.qrCodeExpirationTime)))));

            presenceList.setQrId(qrCode.getId());
            return presenceListRepository.save(presenceList);
        }

        return null;
    }

    public PresenceList updateQR(PresenceList presenceList) {
        QRCode newQR = new QRCode(presenceList.getId());
        newQR = qrCodeRepository.save(newQR);
        presenceList.setQrId(newQR.getId());
        System.out.println("updated");
        return presenceListRepository.save(presenceList);
    }

    public PresenceList refreshAndGetPresenceListByID(ObjectId presencelistId) {
        var presenceList = presenceListRepository.findById(presencelistId.toString()).orElse(null);
        if (presenceList == null) return null;
        Date now = Date.from(Instant.now());


        // generate a new QR if the previous one expired, but presence list didn't
        if (!now.after(presenceList.getTimestampClosed())) {
            ObjectId qrID = presenceList.getQrId();
            String qrIDString = qrID.toString();

            QRCode qr = qrCodeRepository.findById(qrIDString).orElse(null);
            if (qr == null) return null;
            System.out.println(now);
            System.out.println(qr.getTimestampExpires());

            if (now.after(qr.getTimestampExpires())) {
                presenceList = this.updateQR(presenceList);
            }
            System.out.println(presenceList);
        }
        return presenceList;
    }

    public PresenceList getPresenceListByID(ObjectId presencelistId) {
        return presenceListRepository.findById(presencelistId.toString()).orElse(null);
    }

    public Map<String, Object> getPresenceListProjection(PresenceList presenceList) {
        List<User> students = new ArrayList<>();
        for (ObjectId studentID : presenceList.getStudents()) {
            User user = userRepository.findById(studentID.toString()).orElse(null);
            if(user != null) { user.setRole(""); students.add(user); }
        }
        return Map.ofEntries(
                Map.entry("id", presenceList.getId().toString()),
                Map.entry("students", students),
                Map.entry("timestampCreated", presenceList.getTimestampCreated())
        );
    }

    public PresenceList updatePresenceList(PresenceList presenceList) {
        return presenceListRepository.save(presenceList);
    }
}
