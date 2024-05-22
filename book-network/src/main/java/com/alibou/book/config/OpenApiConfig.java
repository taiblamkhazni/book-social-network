package com.alibou.book.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Alibou",
                        email = "contact@aliboucoding.com",
                        url = "https://aliboucoding.com/course"
                ),
                description = "OpenApi documentation for Spring Security",
                title = "OpenApi specification - Alibou",
                version = "1.0",
                license = @License(
                        name = "Licence name",
                        url = "https://some-url.com"
                ),
                termsOfService = "Terms of service"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8088/api/v1"
                ),
                @Server(
                        description = "PROD ENV",
                        url = "https://aliboucoding.com/course"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
//@Configuration
public class OpenApiConfig {
    /*private static final String OPENAPI_URL = "http://localhost:8088/api/v1/v3/api-docs";
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
    }*/
}
