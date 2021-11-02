package com.upb.qresent.qrCode;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class QRCodeController {
    private QRCodeRepository qrCodeRepository;

    public QRCodeController(QRCodeRepository qrCodeRepository) {
        this.qrCodeRepository = qrCodeRepository;
    }
}
