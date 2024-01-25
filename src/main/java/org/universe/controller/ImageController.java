package org.universe.controller;
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

    @PostMapping("/upload")
    @ResponseBody
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        // Process the uploaded file (save it, etc.)
        System.out.println("Received file: " + file.getOriginalFilename());

        // Perform image processing and generate matching results HTML
        String matchingResults = generateMatchingResults(file);

        // Return the matching results HTML as a response
        return matchingResults;
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
