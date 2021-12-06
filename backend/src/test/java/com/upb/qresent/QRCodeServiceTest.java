package com.upb.qresent;

import com.upb.qresent.course.Course;
import com.upb.qresent.course.CourseRepository;
import com.upb.qresent.presentList.PresenceList;
import com.upb.qresent.presentList.PresenceListRepository;
import com.upb.qresent.qrCode.QRCode;
import com.upb.qresent.qrCode.QRCodeRepository;
import com.upb.qresent.qrCode.QRCodeService;
import com.upb.qresent.statistics.StatisticsRepository;
import com.upb.qresent.user.User;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QRCodeServiceTest {
    @Mock
    private CourseRepository courseRepository;

    @Mock
    private PresenceListRepository presenceListRepository;

    @Mock
    private QRCodeRepository qrCodeRepository;

    @Mock
    StatisticsRepository statisticsRepository;

    private QRCodeService qrCodeService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        qrCodeService = new QRCodeService(qrCodeRepository, presenceListRepository, courseRepository,statisticsRepository);
    }

    @Test
    public void getQRByID_nullcase() {
        ObjectId id = new ObjectId();
        assertNull(qrCodeService.getQRByID(id));
    }

    @Test
    public void getQRByID_notnullcase() {
        ObjectId id = new ObjectId();
        when(qrCodeRepository.findById(anyString())).thenReturn(Optional.of(new QRCode()));
        assertNotNull(qrCodeService.getQRByID(id));
    }

    @Test
    public void recordStudentViaQR_nullcase() {
        ObjectId id = new ObjectId();
        assertNotNull(qrCodeService.recordStudentViaQR(null, id, id, id));
    }

    @Test
    public void recordStudentViaQR_notnullcase() {
        Course course = new Course("Mate1", new ObjectId(), (short) 1, "nimic", "nimic","nimic","nimic");
        ObjectId id = new ObjectId();
        course.setId(id);
        courseRepository.save(course);
        PresenceList presenceList = new PresenceList(id, id, id, new Date(), new Date(), new HashSet<ObjectId>());
        User user = new User();
        user.setCourses(new HashSet<ObjectId>());
        user.getCourses().add(id);
        QRCode qrCode = new QRCode();
        qrCode.setPresenceListId(id);
        when(courseRepository.findById(anyString())).thenReturn(Optional.of(course));
        when(presenceListRepository.findById(anyString())).thenReturn(Optional.of(presenceList));
        when(qrCodeRepository.findById(anyString())).thenReturn(Optional.of(qrCode));
        assertNotNull(qrCodeService.recordStudentViaQR(user, id, id, id));
    }


}
