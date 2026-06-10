package com.project.poc_stable_values;

import com.project.poc_stable_values.dto.FraudAnalysisRequest;
import com.project.poc_stable_values.service.FraudAnalysisService;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PocStableValuesApplication {

	public static void main(String[] args) {

		FraudAnalysisService service = new FraudAnalysisService();

		var request =
				new FraudAnalysisRequest(
						"12345678901",
						"SELFIE_BASE64",
						"DOCUMENT_BASE64",
						"eyJhbGciOiJSUzI1NiIsImtpZCI6ImtpZC0wMDEifQ.eyJzdWIiOiIxMjM0NTY3ODkwMSIsIm5hbWUiOiJJY2FybyBDYWV0YW5vIiwiaWF0IjoxNzE1NjAwMDAwfQ.signature"
				);

		System.out.println("=== First Request ===");

		service.analyze(request);

		System.out.println(request);

		System.out.println("=== Second Request ===");

		service.analyze(request);
	}

}
