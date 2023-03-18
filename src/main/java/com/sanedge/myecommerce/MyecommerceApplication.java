package com.sanedge.myecommerce;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyecommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyecommerceApplication.class, args);
	}

	private static void openSwaggerUI() throws IOException {
		Runtime rt = Runtime.getRuntime();

		rt.exec(
				"rundll32 url.dll,FileProtocolHandler " +
						"http://localhost:3060/swagger-ui/index.html");
	}

}
