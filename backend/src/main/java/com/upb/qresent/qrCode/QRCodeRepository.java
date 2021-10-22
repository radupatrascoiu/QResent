package com.upb.qresent.qrCode;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface QRCodeRepository extends MongoRepository<QRCode, String> {
}
