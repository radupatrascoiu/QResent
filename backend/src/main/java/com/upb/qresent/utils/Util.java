package com.upb.qresent.utils;

import com.upb.qresent.course.CourseRepository;
import com.upb.qresent.presentList.PresenceListRepository;
import com.upb.qresent.qrCode.QRCodeRepository;
import com.upb.qresent.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

//        userRepository.insert(new User("Mihnea Muraru", "mihnea.muraru@stud.com", "mihnea.muraru@google.com"));
//        Professor boiceaRegele = professorRepository.insert(new Professor("Boicea Alexandru", "boicea.alexandru@stud.com", "boicea.alexandru@google.com"));
//        Course ubd = courseRepository.insert(new Course("UBD", boiceaRegele.getId(), "Cancer", "Sa ridici mana", "Nu exista", Set.of("Luni: 10-12", "Vineri: 18-20")));
//        Student radu = studentRepository.insert(new Student("Radu Patrascoiu", "radu.patrascoiu@stud.com", "344C5", "radu.patrascoiu@google.com", Set.of(ubd.getId())));
//        Student patrick = studentRepository.insert(new Student("Patrick Vitoga", "patrick.vitoga@stud.com", "344C5", "patrick.vitoga@google.com", Set.of(ubd.getId())));
//        Student alin = studentRepository.insert(new Student("Alin Velea", "alin.velea@stud.com", "344C2", "alin.velea@google.com", Set.of(ubd.getId())));
//        Student alex = studentRepository.insert(new Student("Alexandru Apostol", "alex.apostol@stud.com", "344C2", "alex.apostol@google.com", Set.of(ubd.getId())));
//        Student cami = studentRepository.insert(new Student("Gabriela Camelia", "gabriela.camelia@stud.com", "344C5", "gabriela.camelia@google.com", Set.of(ubd.getId())));
//        Student andrei = studentRepository.insert(new Student("Andrei Clej", "andrei.clej@stud.com", "344C5", "andrei.clej@google.com", Set.of(ubd.getId())));
//        QRCode qrCode = qrCodeRepository.insert(new QRCode(ubd.getId(), "/validate/" + ubd.getId() + "/" , Date.from(Instant.now()), Date.from(Instant.now())));
//        qrCode.setLink(qrCode.getLink() + qrCode.getId());
//        presenceListRepository.insert(new PresenceList(ubd.getId(), boiceaRegele.getId(), qrCode.getId(), Date.from(Instant.now()), Date.from(Instant.now()), Set.of(radu.getId(), patrick.getId(), alin.getId(), alex.getId(), cami.getId(), andrei.getId())));
    }
}
