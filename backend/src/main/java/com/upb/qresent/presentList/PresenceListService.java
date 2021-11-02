package com.upb.qresent.presentList;

import com.upb.qresent.course.Course;
import com.upb.qresent.course.CourseRepository;
import com.upb.qresent.qrCode.QRCode;
import com.upb.qresent.qrCode.QRCodeRepository;
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
    private PresenceListRepository presenceListRepository;
    private CourseRepository courseRepository;
    private QRCodeRepository qrCodeRepository;

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

    public PresenceList getPresenceList(ObjectId presencelistId) {
        return presenceListRepository.findById(presencelistId.toString()).orElse(null);
    }
}
