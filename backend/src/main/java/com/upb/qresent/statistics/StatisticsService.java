package com.upb.qresent.statistics;

import com.upb.qresent.presentList.PresenceList;
import com.upb.qresent.presentList.PresenceListService;
import com.upb.qresent.user.UserService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatisticsService {
    private StatisticsRepository statisticsRepository;
    private PresenceListService presenceListService;
    private UserService userService;

    public StatisticsService(StatisticsRepository statisticsRepository, PresenceListService presenceListService, UserService userService) {
        this.statisticsRepository = statisticsRepository;
        this.presenceListService = presenceListService;
        this.userService = userService;
    }

    public Statistics retrieveOrGenerateStatistics(int courseNo) {
        return statisticsRepository.findByCourseNo(courseNo);
    }

    public int getCourseNoByData(Date endDate) throws ParseException {
        Date startDate = new GregorianCalendar(2021, Calendar.OCTOBER, 4).getTime();
        return (int) (Math.abs(endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24 * 7)) + 1;
    }

    public Statistics getStatisticsByWeek(ObjectId courseId, int courseNo) {

        Statistics courseNoAndCourseId = statisticsRepository.findByCourseNoAndCourseId(courseNo, courseId);
        if (courseNoAndCourseId != null) {
            return courseNoAndCourseId;
        }

        // trebuie sa intoarca 3 liste de prezenta
        List<PresenceList> presenceLists = presenceListService.getPresenceListsByCourse(courseId).stream().filter(x -> {
            try {
                return getCourseNoByData(x.getTimestampClosed()) == courseNo;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return false;
        }).collect(Collectors.toList());

        int totalNumberOfStudents = 0;
        int numberOfStudentsPresentAtTheBeginning = 0;
        int numberOfStudentsPresentDuringTheCourse = 0;
        int numberOfStudentsPresentAtTheEndOfTheCourse = 0;

        if (!presenceLists.isEmpty()) {
            totalNumberOfStudents = userService.getNumberOfStudentsFromACourse(courseId);
            numberOfStudentsPresentAtTheBeginning = presenceLists.get(0).getStudents().size();
            numberOfStudentsPresentDuringTheCourse = presenceLists.get(1).getStudents().size();
            numberOfStudentsPresentAtTheEndOfTheCourse = presenceLists.get(2).getStudents().size();
        }

        return statisticsRepository.save(new Statistics(courseId, courseNo, LocalDateTime.now(),
                List.of(totalNumberOfStudents, numberOfStudentsPresentAtTheBeginning,
                        numberOfStudentsPresentDuringTheCourse, numberOfStudentsPresentAtTheEndOfTheCourse), presenceLists
                .stream().map(PresenceList::getId).collect(Collectors.toList())));
    }
}
