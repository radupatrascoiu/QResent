package com.upb.qresent.qrCode;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QRCodeRepository extends MongoRepository<QRCode, String> {
}
