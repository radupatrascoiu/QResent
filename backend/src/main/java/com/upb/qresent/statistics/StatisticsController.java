package com.upb.qresent.statistics;

import com.upb.qresent.presentList.PresenceListService;
import com.upb.qresent.user.UserService;
import com.upb.qresent.utils.ResponseDto;
import com.upb.qresent.utils.StatisticsDto;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials = "true")
@RequestMapping("/api/statistics")
public class StatisticsController {
    private StatisticsService statisticsService;
    private PresenceListService presenceListService;
    private UserService userService;

    public StatisticsController(StatisticsService statisticsService, PresenceListService presenceListService, UserService userService) {
        this.statisticsService = statisticsService;
        this.presenceListService = presenceListService;
        this.userService = userService;
    }

    @GetMapping("/{courseId}/{courseNo}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getStatisticsByWeek(@PathVariable(value = "courseId") ObjectId courseId, @PathVariable(value = "courseNo") int courseNo) {
        Statistics statistics = statisticsService.getStatisticsByWeek(courseId, courseNo);

        if (statistics == null) {
            return ResponseEntity.badRequest().body(new ResponseDto("We couldn't find the present list", null));
        }

        List<Integer> calculatedValues = statistics.getCalculatedValues();
        return ResponseEntity.ok().body(new ResponseDto("Success",
                new StatisticsDto(courseId.toString(),
                        calculatedValues.get(0),
                        presenceListService.getPresenceListByID(statistics.getPresentList().get(0)).getStudents().size(),
                        presenceListService.getPresenceListByID(statistics.getPresentList().get(1)).getStudents().size(),
                        presenceListService.getPresenceListByID(statistics.getPresentList().get(2)).getStudents().size())));
    }
}
