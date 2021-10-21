package com.upb.qresent.utils;

import com.upb.qresent.administrator.Administrator;
import com.upb.qresent.administrator.AdministratorRepository;
import com.upb.qresent.course.Course;
import com.upb.qresent.course.CourseRepository;
import com.upb.qresent.presentList.PresenceList;
import com.upb.qresent.presentList.PresenceListRepository;
import com.upb.qresent.professor.Professor;
import com.upb.qresent.professor.ProfessorRepository;
import com.upb.qresent.qrCode.QRCode;
import com.upb.qresent.qrCode.QRCodeRepository;
import com.upb.qresent.student.Student;
import com.upb.qresent.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Set;

@Service
public class Util {
    @Autowired
    private AdministratorRepository administratorRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private PresenceListRepository presenceListRepository;
    @Autowired
    private ProfessorRepository professorRepository;
    @Autowired
    private QRCodeRepository qrCodeRepository;
    @Autowired
    private StudentRepository studentRepository;

    public void insertFakeDataInDB() {
        administratorRepository.deleteAll();
        courseRepository.deleteAll();
        presenceListRepository.deleteAll();
        professorRepository.deleteAll();
        qrCodeRepository.deleteAll();
        studentRepository.deleteAll();

        administratorRepository.insert(new Administrator("Mihnea Muraru", "mihnea.muraru@stud.com", "mihnea.muraru@google.com"));
        Professor boiceaRegele = professorRepository.insert(new Professor("Boicea Alexandru", "boicea.alexandru@stud.com", "boicea.alexandru@google.com"));
        Course ubd = courseRepository.insert(new Course("UBD", boiceaRegele.getId(), "Cancer", "Sa ridici mana", "Nu exista", Set.of("Luni: 10-12", "Vineri: 18-20")));
        Student radu = studentRepository.insert(new Student("Radu Patrascoiu", "radu.patrascoiu@stud.com", "344C5", "radu.patrascoiu@google.com", Set.of(ubd.getId())));
        Student patrick = studentRepository.insert(new Student("Patrick Vitoga", "patrick.vitoga@stud.com", "344C5", "patrick.vitoga@google.com", Set.of(ubd.getId())));
        Student alin = studentRepository.insert(new Student("Alin Velea", "alin.velea@stud.com", "344C2", "alin.velea@google.com", Set.of(ubd.getId())));
        Student alex = studentRepository.insert(new Student("Alexandru Apostol", "alex.apostol@stud.com", "344C2", "alex.apostol@google.com", Set.of(ubd.getId())));
        Student cami = studentRepository.insert(new Student("Gabriela Camelia", "gabriela.camelia@stud.com", "344C5", "gabriela.camelia@google.com", Set.of(ubd.getId())));
        Student andrei = studentRepository.insert(new Student("Andrei Clej", "andrei.clej@stud.com", "344C5", "andrei.clej@google.com", Set.of(ubd.getId())));
        QRCode qrCode = qrCodeRepository.insert(new QRCode(ubd.getId(), "/validate/" + ubd.getId() + "/" , Date.from(Instant.now()), Date.from(Instant.now())));
        qrCode.setLink(qrCode.getLink() + qrCode.getId());
        presenceListRepository.insert(new PresenceList(ubd.getId(), boiceaRegele.getId(), qrCode.getId(), Date.from(Instant.now()), Date.from(Instant.now()), Set.of(radu.getId(), patrick.getId(), alin.getId(), alex.getId(), cami.getId(), andrei.getId())));
    }
}
