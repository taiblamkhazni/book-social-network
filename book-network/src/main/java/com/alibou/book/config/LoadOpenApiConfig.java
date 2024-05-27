package com.alibou.book.config;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

//@Configuration
public class LoadOpenApiConfig {
    private static final String OPENAPI_URL = "http://127.0.0.1:8088/api/v1/v3/api-docs";
    private static final String OUTPUT_FILE = "book-network-ui/src/openapi/openApi.json";

    @Bean
    public CommandLineRunner saveOpenApiJson() {
        return args -> {
            RestTemplate restTemplate = new RestTemplate();
            String openApiJson = restTemplate.getForObject(OPENAPI_URL, String.class);

            if (openApiJson != null) {
                saveToFile(openApiJson, OUTPUT_FILE);
                generateAngularClient();
            } else {
                System.err.println("Failed to fetch OpenAPI JSON.");
            }
        };
    }

    private void saveToFile(String content, String filePath) {
        try (FileWriter fileWriter = new FileWriter(new File(filePath))) {
            fileWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void generateAngularClient() {
        try {
            File directory = new File("book-network-ui");
            String absolutePathToUi = directory.getAbsolutePath();
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "cmd.exe", "/c","npx", "ng-openapi-gen",
                    "--input", absolutePathToUi+"/src/openapi/openApi.json",
                    "--output", "src/app/services/api"
            );
            processBuilder.directory(new File(absolutePathToUi));
            processBuilder.inheritIO();
            Process process = processBuilder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
