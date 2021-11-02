package com.upb.qresent.utils;

import com.upb.qresent.course.Course;
import com.upb.qresent.course.CourseRepository;
import com.upb.qresent.presentList.PresenceList;
import com.upb.qresent.presentList.PresenceListRepository;
import com.upb.qresent.qrCode.QRCode;
import com.upb.qresent.qrCode.QRCodeRepository;
import com.upb.qresent.user.User;
import com.upb.qresent.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Set;

@Service
public class Util {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private PresenceListRepository presenceListRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QRCodeRepository qrCodeRepository;

    public void insertFakeDataInDB() {
        courseRepository.deleteAll();
        presenceListRepository.deleteAll();
        userRepository.deleteAll();
        qrCodeRepository.deleteAll();

        Course ubd = courseRepository.insert(new Course("UBD", null, "Cancer", "Sa ridici mana", "Nu exista", Set.of("Luni: 10-12", "Vineri: 18-20")));
        Course pp = courseRepository.insert(new Course("PP", null, "Greu rau", "Sa rupi", "Sanki", Set.of("Luni: 12-14", "Joi: 18-20")));
        User boiceaRegele = userRepository.insert(new User("Boicea Alexandru", "professor", "boicea.alexandru@stud.com", Set.of(ubd.getId())));
        User mihnea = userRepository.insert(new User("Mihnea Muraru", "admin", "mihnea.muraru@stud.com", Set.of(pp.getId(), ubd.getId())));
        
        ubd.setProfessorId(boiceaRegele.getId());
        pp.setProfessorId(mihnea.getId());
        
        courseRepository.save(ubd);
        courseRepository.save(pp);

        User radu = userRepository.insert(new User("Radu Patrascoiu", "student", "radu.patrascoiu@stud.com", "344C5", Set.of(ubd.getId())));
        User patrick = userRepository.insert(new User("Patrick Vitoga","student","patrick.vitoga@stud.com", "344C5", Set.of(ubd.getId())));
        User alin = userRepository.insert(new User("Alin Velea","student", "alin.velea@stud.com", "344C2", Set.of(ubd.getId())));
        User alex = userRepository.insert(new User("Alexandru Apostol","student", "alex.apostol@stud.com", "344C2", Set.of(ubd.getId())));
        User cami = userRepository.insert(new User("Gabriela Camelia","student","gabriela.camelia@stud.com", "344C5", Set.of(ubd.getId())));
        User andrei = userRepository.insert(new User("Andrei Clej","student", "andrei.clej@stud.com", "344C5", Set.of(ubd.getId())));
        PresenceList presenceList = presenceListRepository.insert(new PresenceList(ubd.getId(), boiceaRegele.getId(), null, Date.from(Instant.now()), Date.from(Instant.now()), Set.of(radu.getId(), patrick.getId(), alin.getId(), alex.getId(), cami.getId(), andrei.getId())));
        QRCode qrCode = qrCodeRepository.insert(new QRCode(ubd.getId(), Date.from(Instant.now()), Date.from(Instant.now())));

        presenceList.setQrId(qrCode.getId());
        presenceListRepository.save(presenceList);

    }
}
