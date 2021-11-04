package com.upb.qresent.presentList;

import com.upb.qresent.course.Course;
import com.upb.qresent.course.CourseRepository;
import com.upb.qresent.qrCode.QRCode;
import com.upb.qresent.qrCode.QRCodeRepository;
import com.upb.qresent.qrCode.QRCodeService;
import com.upb.qresent.utils.Constants;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
public class PresenceListService {
    private final PresenceListRepository presenceListRepository;
    private final CourseRepository courseRepository;
    private final QRCodeRepository qrCodeRepository;


    public PresenceListService(PresenceListRepository presenceListRepository, CourseRepository courseRepository, QRCodeRepository qrCodeRepository) {
        this.presenceListRepository = presenceListRepository;
        this.courseRepository = courseRepository;
        this.qrCodeRepository = qrCodeRepository;
    }

    public PresenceList createPresenceList(ObjectId courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId.toString());

        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            ObjectId professorId = course.getProfessorId();
            PresenceList presenceList = presenceListRepository.insert(new PresenceList(courseId, professorId, null, Date.from(Instant.now()), Date.from(Instant.now().plus(Duration.ofMillis(Constants.presenceListExpirationTime))), Set.of()));
            QRCode qrCode = qrCodeRepository.insert(new QRCode(presenceList.getId(), Date.from(Instant.now()), Date.from(Instant.now().plus(Duration.ofMillis(Constants.qrCodeExpirationTime)))));

            presenceList.setQrId(qrCode.getId());
            return presenceListRepository.save(presenceList);
        }

        return null;
    }

    public PresenceList updateQR(PresenceList presenceList) {
        QRCode newQR = new QRCode(presenceList.getId());
        newQR = qrCodeRepository.save(newQR);
        presenceList.setQrId(newQR.getId());
        return presenceListRepository.save(presenceList);
    }

    public PresenceList refreshAndGetPresenceListByID(ObjectId presencelistId) {
        var presenceList = presenceListRepository.findById(presencelistId.toString()).orElse(null);
        if (presenceList == null) return null;
        Date now = Date.from(Instant.now());

        // generate a new QR if the previous one expired, but presence list didn't
        if (!now.after(presenceList.getTimestampClosed())) {
            ObjectId qrID = presenceList.getQrId();
            Optional<QRCode> optionalQR = qrCodeRepository.findById(qrID.toString());
            QRCode qr = optionalQR.orElse(null);
            if (qr == null) return null;

            if (now.after(qr.getTimestampExpires())) {
                presenceList = this.updateQR(presenceList);
            }
        }
        return presenceList;
    }

    public PresenceList getPresenceListByID(ObjectId presencelistId) {
        return presenceListRepository.findById(presencelistId.toString()).orElse(null);
    }

    public PresenceList updatePresenceList(PresenceList presenceList) {
        return presenceListRepository.save(presenceList);
    }
}
