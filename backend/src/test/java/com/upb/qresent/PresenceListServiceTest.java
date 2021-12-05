package com.upb.qresent;


import com.upb.qresent.course.Course;
import com.upb.qresent.course.CourseRepository;
import com.upb.qresent.presentList.PresenceList;
import com.upb.qresent.presentList.PresenceListRepository;
import com.upb.qresent.presentList.PresenceListService;
import com.upb.qresent.qrCode.QRCode;
import com.upb.qresent.qrCode.QRCodeRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PresenceListServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private PresenceListRepository presenceListRepository;

    @Mock
    private QRCodeRepository qrCodeRepository;

    private PresenceListService presenceListService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        presenceListService = new PresenceListService(presenceListRepository, courseRepository, qrCodeRepository);
    }

    @Test
    public void createPresenceList_null() {
        assertNull(presenceListService.createPresenceList(new ObjectId()));
    }

    @Test
    public void createPresenceList_notnull() {
        Course course = new Course("Mate1", new ObjectId(), (short) 1, "nimic", "nimic", "nimic", new HashSet<String>());
        ObjectId id = new ObjectId();
        course.setId(id);
        courseRepository.save(course);
        when(courseRepository.findById(anyString())).thenReturn(Optional.of(course));
        when(presenceListRepository.save(any(PresenceList.class))).thenReturn(new PresenceList(new ObjectId(), new ObjectId(), new ObjectId(), new Date(), new Date(), null));
        when(qrCodeRepository.save(any(QRCode.class))).thenReturn(new QRCode());
        assertNotNull(presenceListService.createPresenceList(new ObjectId()));
    }

    @Test
    public void getPresenceListByID() {
        ObjectId id = new ObjectId();
        assertNull(presenceListService.getPresenceListByID(id));
    }

    @Test
    public void updatePresenceList() {
        PresenceList list = null;
        assertNull(presenceListService.updatePresenceList(list));
    }

    @Test
    public void refreshAndGetPresenceListByIDisnull() {
        ObjectId id = new ObjectId();
        assertNull(presenceListService.refreshAndGetPresenceListByID(id));
    }

    @Test
    public void refreshAndGetPresenceListByIDisnotnull() {
        ObjectId id = new ObjectId();
        PresenceList list = new PresenceList(new ObjectId(), new ObjectId(), new ObjectId(), new Date(), new Date(), null);
        when(presenceListRepository.findById(anyString())).thenReturn(Optional.of(list));
        assertNotNull(presenceListService.refreshAndGetPresenceListByID(id));

    }

    @Test
    public void updateQR() {
        PresenceList list = new PresenceList(new ObjectId(), new ObjectId(), new ObjectId(), new Date(), new Date(), null);
        when(qrCodeRepository.save(any(QRCode.class))).thenReturn(new QRCode());
        assertNull(presenceListService.updateQR(list));
    }

}
