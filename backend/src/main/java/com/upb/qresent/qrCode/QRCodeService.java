package com.upb.qresent.qrCode;

import com.upb.qresent.course.Course;
import com.upb.qresent.course.CourseRepository;
import com.upb.qresent.presentList.PresenceList;
import com.upb.qresent.presentList.PresenceListRepository;
import com.upb.qresent.user.User;
import org.bson.types.ObjectId;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class QRCodeService {
    public final QRCodeRepository qrCodeRepository;
    public final PresenceListRepository presenceListRepository;
    public final CourseRepository courseRepository;

    public QRCodeService(QRCodeRepository qrCodeRepository, PresenceListRepository presenceListRepository, CourseRepository courseRepository) {
        this.qrCodeRepository = qrCodeRepository;
        this.presenceListRepository = presenceListRepository;
        this.courseRepository = courseRepository;
    }

    public QRCode getQRByID(ObjectId qrID) {
        return qrCodeRepository.findById(qrID.toString()).orElse(null);
    }

    public Pair<Boolean, String> recordStudentViaQR(User user, ObjectId courseId, ObjectId presenceListId, ObjectId qrID) {
        PresenceList presenceList = presenceListRepository.findById(presenceListId.toString()).orElse(null);
        Course course = courseRepository.findById(courseId.toString()).orElse(null);
        QRCode qrCode = qrCodeRepository.findById(qrID.toString()).orElse(null);

        if (user == null || presenceList == null || course == null || qrCode == null) return Pair.of(false, "Your profile is incomplete so we couldn't find all the information for the validation.");

        Date now = new Date();
        if(!user.getCourses().contains(courseId)) return Pair.of(false, "You are not enrolled for this course.");
        if(!qrCode.getPresenceListId().equals(presenceListId)) return Pair.of(false, "Your QR code doesn't match the presence list.");
        if(!presenceList.getCourseId().equals(courseId)) return Pair.of(false, "The presence list doesn't match the provided course.");
        if(!presenceList.getQrId().equals(qrID)) return Pair.of(false, "Your QR code is not recognized in the presence list.");
        if(now.after(presenceList.getTimestampClosed())) return Pair.of(false, "The presence list is closed.");
        if(now.after(qrCode.getTimestampExpires())) return Pair.of(false, "The QR code is expired.");
        if(presenceList.getStudents().contains(user.getId())) return Pair.of(false, "Sorry, you are already marked as present.");
        // TODO optional, check if the course intervals match with the current time

        if (!presenceList.insertStudentIntoPresenceList(user.getId())) return Pair.of(false, "Sorry, we couldn't add you this time. Please try again!");
        presenceListRepository.save(presenceList);
        return Pair.of(true, "You was marked successfully as present.");
    }
}
