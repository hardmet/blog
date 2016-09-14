package ru.breathoffreedom.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.breathoffreedom.mvc.services.vfs.VFS;

import javax.annotation.security.RolesAllowed;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This class for control operations with files
 */
@Controller
public class FileUploadController {

    private final VFS fileSystem;

    @Autowired
    public FileUploadController(VFS fileSystem) {
        this.fileSystem = fileSystem;
    }

    /**
     * method for upload file to the server
     * @param uploadImage - file to upload
     * @param directory   - directory - destination to save files
     * @return - result messages of uploading uploadImages
     */
    @RolesAllowed(value={"ROLE_ADMIN"})
    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    @ResponseBody
    public List<String> handleFileUpload(@RequestParam("uploadImages") MultipartFile[] uploadImage,
                                         @RequestParam("directory") String directory) {
        System.out.println("handleFileUpload is called");
        List<String> resultOfUpload = new ArrayList<>();
        for (MultipartFile file : uploadImage) {
            if (!file.isEmpty()) {
                try {
                    byte[] fileBytes = file.getBytes();
                    String rootPath = fileSystem.getRoot();
                    System.out.println("Server rootPath: " + rootPath);
                    System.out.println("File original name: " + file.getOriginalFilename());
                    System.out.println("File content type: " + file.getContentType());
                    StringBuilder fileName = new StringBuilder(rootPath);
                    fileName.append(File.separator);
                    fileName.append(fileSystem.getDirectory("post" + File.separator + directory));
                    fileName.append(File.separator);
                    fileName.append(file.getOriginalFilename());
                    System.out.println(fileName);
                    File newFile = new File(fileName.toString());
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(newFile));
                    stream.write(fileBytes);
                    stream.close();
                    fileName.insert(0, "File is saved under: ");
                    resultOfUpload.add(fileName.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    resultOfUpload.add("File upload is failed: " + e.getMessage());
                }
            } else {
                resultOfUpload.add("File: " + file.getOriginalFilename() + " upload is failed: File is empty");
            }
        }
        return resultOfUpload;
    }
}
