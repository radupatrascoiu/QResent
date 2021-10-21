package com.upb.qresent.qrCode;

import org.springframework.stereotype.Controller;

@Controller
public class QRCodeController {
    private QRCodeRepository qrCodeRepository;

    public QRCodeController(QRCodeRepository qrCodeRepository) {
        this.qrCodeRepository = qrCodeRepository;
    }
}
