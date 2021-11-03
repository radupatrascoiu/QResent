package com.upb.qresent.presentList;

import com.upb.qresent.utils.ResponseDto;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials = "true")
@RequestMapping("/api")
public class PresenceListController {
    private PresenceListService presenceListService;

    public PresenceListController(PresenceListService presenceListService) {
        this.presenceListService = presenceListService;
    }

    // TODO metoda sa validez un cod qr
    // TODO refresh QR code

    @PostMapping("/presencelist/{courseId}")
//    @PreAuthorize("hasRole('professor')")
    public PresenceList createPresenceList(@PathVariable(value="courseId") ObjectId courseId) {
        return presenceListService.createPresenceList(courseId);
    }

    @GetMapping("/presencelist/{presencelistId}")
//    @PreAuthorize("hasRole('professor')")
    public ResponseEntity<?> getPresenceList(@PathVariable(value="presencelistId") ObjectId presencelistId) {
        PresenceList presenceList = presenceListService.getPresenceList(presencelistId);
        if (presenceList == null) {
            return ResponseEntity.badRequest().body(new ResponseDto("We couldn't find the present list", null));
        }

        return ResponseEntity.ok().body(new ResponseDto("Success", presenceList));
    }
}
