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
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.commons.io.FileUtils;

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
    public String handleFileUpload(@RequestParam("file") MultipartFile file) throws InterruptedException {
        if (file.isEmpty()) {
            return "Please select a file to upload";
        }

        try {
            // Create the directory if it doesn't exist
            File directory = new File(uploadDirectory);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            else {
                FileUtils.cleanDirectory(directory); // Clear the directory.
            }

            // Save the file
            String orig_filename = file.getOriginalFilename();
            int lastDotIndex = orig_filename.lastIndexOf('.');
            String extension = "";
            if (lastDotIndex > 0 && lastDotIndex < orig_filename.length() - 1) {
                // Get file extension
                extension = orig_filename.substring(lastDotIndex);
            }
            System.out.println(lastDotIndex);
            String filePath = uploadDirectory + File.separator + "uploaded_image" + extension; // Rename, to prevent filename errors.
            File uploadedFile = new File(filePath);
            file.transferTo(uploadedFile);

            System.out.println(String.format("File %s uploaded successfully and saved as: %s", orig_filename, filePath));

            // Perform image matching and generate matching results HTML
            String matchingResults = generateMatchingResults(file);

            // Return the matching results HTML as a response
            return matchingResults;

        } catch (IOException e) {
            return "Failed to upload file: " + e.getMessage();
        }
    }


    // Currently not in use.
    @RequestMapping("/getAdditionalImage")
    @ResponseBody
    public byte[] getAdditionalImage() throws IOException {
        // Load the additional image from the classpath or any location
        File file = new File("src/main/resources/static/200OK.png");
        return org.apache.commons.io.FileUtils.readFileToByteArray(file);
    }

    private String generateMatchingResults(MultipartFile file) throws IOException, InterruptedException {
        // Delegate a Python script to match similar images.
        //String arg1 = "";
        //String arg2 = "";
        ProcessBuilder match_image = new ProcessBuilder("python", "python/image_match.py");

        // Start the process
        Process process = match_image.start();

        // Get the input stream (standard output) of the process
        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        // Read and print the output
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println("Python script output: " + line);
        }

        // Wait for the process to complete
        int exitCode = process.waitFor();
        System.out.println("Python script executed with exit code: " + exitCode);

        String res = "OK";
        return res;
        // Simple return 4 default images:
        //return "<h2>Matching Results</h2><img src=\"/api/image/getAdditionalImage\" alt=\"Additional Image\" width=\"300\"><img src=\"/api/image/getAdditionalImage\" alt=\"Additional Image\" width=\"300\"><img src=\"/api/image/getAdditionalImage\" alt=\"Additional Image\" width=\"300\"><img src=\"/api/image/getAdditionalImage\" alt=\"Additional Image\" width=\"300\">";
    }
}
