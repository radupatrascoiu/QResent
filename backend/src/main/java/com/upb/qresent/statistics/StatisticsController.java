package com.upb.qresent.statistics;

import com.upb.qresent.presentList.PresenceList;
import com.upb.qresent.presentList.PresenceListService;
import com.upb.qresent.utils.ResponseDto;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials = "true")
@RequestMapping("/api/statistics")
public class StatisticsController {
    private StatisticsService statisticsService;
    private PresenceListService presenceListService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService, PresenceListService presenceListService) {
        this.statisticsService = statisticsService;
        this.presenceListService = presenceListService;
    }

    @GetMapping("/{courseId}/{courseNo}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getStatisticsByWeek(@PathVariable(value="courseId") ObjectId courseId, @PathVariable(value="courseNo") int courseNo) {
        // trebuie sa intoarca maxim 3 liste de prezenta
        List<PresenceList> presenceLists = presenceListService.getPresenceListsByCourse(courseId).stream().filter(x -> {
            try {
                return statisticsService.getCourseNoByData(x.getTimestampClosed()) == courseNo;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return false;
        }).collect(Collectors.toList());
        if (presenceLists.isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseDto("We couldn't find the present list", null));
        }
        return ResponseEntity.ok().body(new ResponseDto("Success", presenceLists));
    }

    // TODO: de adaugat in DB daca nu exista
    // de tras din DB daca exista
    // facut un obiect cu 4 nr: nr total, nr prezenti la inceput, mij, sfarsit
}
