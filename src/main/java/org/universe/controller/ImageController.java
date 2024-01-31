package org.universe.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/api/image")
public class ImageController {

    @RequestMapping("/home")
    public ModelAndView home() {
        return new ModelAndView("index");
    }

    @Value("${upload.directory}")
    private String uploadDirectory;

    @PostMapping("/upload")
    @ResponseBody
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "Please select a file to upload";
        }

        try {
            // Get the file bytes
            byte[] bytes = file.getBytes();

            // Create the directory if it doesn't exist
            File directory = new File(uploadDirectory);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Construct the file path and save the file
            String filePath = uploadDirectory + File.separator + file.getOriginalFilename();
            File uploadedFile = new File(filePath);
            file.transferTo(uploadedFile);

            System.out.println("File uploaded successfully to: " + filePath);

            // Perform image processing and generate matching results HTML
            String matchingResults = generateMatchingResults(file);

            // Return the matching results HTML as a response
            return matchingResults;
        } catch (IOException e) {
            return "Failed to upload file: " + e.getMessage();
        }
    }

    @RequestMapping("/getAdditionalImage")
    @ResponseBody
    public byte[] getAdditionalImage() throws IOException {
        // Load the additional image from the classpath or any location
        // For simplicity, let's assume you have an "image.png" file in the resources folder
        // You may need to adjust the path accordingly based on your project structure
        File file = new File("src/main/resources/static/200OK.png");
        return org.apache.commons.io.FileUtils.readFileToByteArray(file);
    }

    private String generateMatchingResults(MultipartFile file) {
        // Replace this with your image processing and result generation logic
        // For demonstration purposes, returning a simple HTML string
        return "<h2>Matching Results</h2><img src=\"/api/image/getAdditionalImage\" alt=\"Additional Image\" width=\"300\"><img src=\"/api/image/getAdditionalImage\" alt=\"Additional Image\" width=\"300\"><img src=\"/api/image/getAdditionalImage\" alt=\"Additional Image\" width=\"300\"><img src=\"/api/image/getAdditionalImage\" alt=\"Additional Image\" width=\"300\">";
    }
}
