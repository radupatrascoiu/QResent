package com.upb.qresent.presentList;

import com.upb.qresent.course.CourseRepository;
import com.upb.qresent.utils.ResponseDto;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials = "true")
@RequestMapping("/api")
public class PresenceListController {
    private final PresenceListService presenceListService;
    private final CourseRepository courseRepository;

    public PresenceListController(PresenceListService presenceListService, CourseRepository courseRepository) {
        this.presenceListService = presenceListService;
        this.courseRepository = courseRepository;
    }

    @PostMapping("/presencelist/{courseId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> createPresenceList(@PathVariable(value="courseId") ObjectId courseId) {
        PresenceList presenceList = presenceListService.createPresenceList(courseId);
        if (presenceList == null) {
            return ResponseEntity.badRequest().body(new ResponseDto("Failed", ""));
        }
        return ResponseEntity.ok().body(new ResponseDto("Success", presenceList.getId().toString()));
    }

    @GetMapping("/presencelist/{presencelistId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getPresenceList(@PathVariable(value="presencelistId") ObjectId presencelistId) {
        PresenceList presenceList = presenceListService.refreshAndGetPresenceListByID(presencelistId);
        if (presenceList == null) {
            return ResponseEntity.badRequest().body(new ResponseDto("We couldn't find the present list", null));
        }
        return ResponseEntity.ok().body(new ResponseDto("Success", presenceList));
    }


    @GetMapping("/presencelist/export/{presencelistId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> export(@PathVariable(value="presencelistId") ObjectId presencelistId) {
        PresenceList presenceList = presenceListService.getPresenceListByID(presencelistId);
        if (presenceList == null) {
            return ResponseEntity.badRequest().body(new ResponseDto("We couldn't find the present list", null));
        }
        Object presenceListProjection = presenceListService.getPresenceListProjection(presenceList);
        return ResponseEntity.ok().body(new ResponseDto("Success", presenceListProjection));
    }
}
