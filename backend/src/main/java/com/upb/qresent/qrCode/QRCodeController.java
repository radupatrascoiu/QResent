package com.upb.qresent.qrCode;

import com.upb.qresent.presentList.PresenceList;
import com.upb.qresent.user.User;
import com.upb.qresent.utils.ResponseDto;
import com.upb.qresent.utils.Util;
import org.bson.types.ObjectId;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials = "true")
@RequestMapping("/api/qr")
public class QRCodeController {
    public final Util util;
    public final QRCodeService qrCodeService;

    public QRCodeController(Util util, QRCodeService qrCodeService) {
        this.util = util;
        this.qrCodeService = qrCodeService;
    }

    @PostMapping("/record/{courseId}/{presenceListId}/{qrID}")
    @PreAuthorize("hasRole('student')")
    public ResponseEntity<?> recordStudentViaQR(@PathVariable(value="courseId") ObjectId courseId, @PathVariable(value="presenceListId") ObjectId presenceListId, @PathVariable(value="qrID") ObjectId qrID, HttpServletRequest httpServletRequest) {
        User user = util.getUserFromRequest(httpServletRequest);
        Pair<Boolean, String> operationStatus = qrCodeService.recordStudentViaQR(user, courseId, presenceListId, qrID);
        if (operationStatus.getFirst()) {
            return ResponseEntity.ok().body(new ResponseDto("Success: You have been successfully registered into the presence list.", null));
        }
        return ResponseEntity.badRequest().body(new ResponseDto("Failed: Sorry, your request was rejected. For more information check the details box.", operationStatus.getSecond()));
    }

}
