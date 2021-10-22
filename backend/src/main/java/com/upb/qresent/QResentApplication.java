package com.upb.qresent;

import com.upb.qresent.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class QResentApplication implements CommandLineRunner {
	@Autowired
	Util util;

	public static void main(String[] args) {
		SpringApplication.run(QResentApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		util.insertFakeDataInDB();
	}
}
