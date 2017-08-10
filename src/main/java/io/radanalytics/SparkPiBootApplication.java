package io.radanalytics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class SparkPiBootApplication {

	@Autowired
	private SparkPiProducer producer;

	public static void main(String[] args) {
		SpringApplication.run(SparkPiBootApplication.class, args);
	}
}
