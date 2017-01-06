package ru.breathoffreedom.mvc.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.breathoffreedom.mvc.models.blog.Post;
import ru.breathoffreedom.mvc.models.file.Image;
import ru.breathoffreedom.mvc.services.blog.BlogService;
import ru.breathoffreedom.mvc.services.image.ImageDir;
import ru.breathoffreedom.mvc.services.image.ImageFormat;
import ru.breathoffreedom.mvc.services.image.service.ImageService;
import ru.breathoffreedom.mvc.services.vfs.VFS;
import ru.breathoffreedom.mvc.services.vfs.VFSImpl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class for control operations with files
 */
@Controller
public class FileUploadController {

    private VFS fileSystem;
    private ImageService imageService;
    private BlogService blogService;
    private Logger log;

    @Autowired
    public FileUploadController(VFS fileSystem, ImageService imageService, BlogService blogService) {
        this.fileSystem = fileSystem;
        this.imageService = imageService;
        this.blogService = blogService;
    }

    /**
     * method for upload file to the server
     * @param uploadImage - file to upload
     * @return - result messages of uploading uploadImages
     */
    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    @ResponseBody
    public List<String> handleFileUpload(@RequestParam("uploadImages") MultipartFile[] uploadImage,
                                         @RequestParam("id") Integer id, @RequestParam("type") String type) {
        List<String> resultOfUpload = new ArrayList<>();
        if (id != null && id != 0) {
            if (ImageDir.postContent.name().equals(type)) {
                for (MultipartFile file : uploadImage) {
                    uploadPostContentImage(file, id, resultOfUpload);
                }
            } else if (ImageDir.postMain.name().equals(type)) {
                for (MultipartFile file : uploadImage) {
                    uploadPostMainImage(file, id, resultOfUpload);
                }
            }
        } else {
            resultOfUpload.add("post can't be 0 or NULL! Upload failed!");
        }
        return resultOfUpload;
    }

    @RequestMapping(value = "/remove/image", method = RequestMethod.POST)
    @ResponseBody
    public boolean removeImage(@RequestParam("type") String type,
                               @RequestParam("entityId") Integer entityId) {
        if (ImageDir.postContent.name().equals(type)) {
            if (entityId != null) {
                return removePostContentImage(entityId);
            } else {
                throw new NullPointerException("Error! Id of image can't be null!");
            }
        } else if (ImageDir.postMain.name().equals(type)) {
            return removePostMainImage(entityId);
        }
        return false;
    }


    private void uploadPostContentImage(MultipartFile file, int postId, List<String> resultOfUpload) {
        Post post = blogService.getPost(postId);
        Image image = new Image(post);
        int imageId = imageService.save(image).getId();

        String rootPath = fileSystem.getRoot();
        String dir = fileSystem.getDirectory("post" + File.separator +
                                                postId + File.separator + imageId);
        String extension = VFSImpl.getFileExtension(file.getOriginalFilename());

        for (ImageFormat format : ImageFormat.getFormatsByDir(ImageDir.postContent)) {
            String pathToImage = rootPath + dir +
                    File.separator + format + "." + extension;
            uploadImageByPath(file, pathToImage, resultOfUpload);
        }
    }

    private boolean removePostContentImage(int id) {
        Image imageToRemove = imageService.getImage(id);
        ImageFormat[] formats = ImageFormat.getFormatsByDir(ImageDir.postContent);
        boolean isDeleted = fileSystem.removeImage(imageToRemove, formats);
        if (isDeleted) {
            imageService.remove(id);
        }
        return isDeleted;
    }


    private void uploadPostMainImage(MultipartFile file, int postId, List<String> resultOfUpload) {
        Post post = blogService.getPost(postId);
        if (!file.isEmpty()) {
            post.setHasMainImage(true);
            blogService.save(post);
        }
        String rootPath = fileSystem.getRoot();
        String dir = fileSystem.getDirectory("post" + File.separator + postId);
        String extension = VFSImpl.getFileExtension(file.getOriginalFilename());


        for (ImageFormat format : ImageFormat.getFormatsByDir(ImageDir.postMain)) {
            String pathToImage = rootPath + dir +
                    File.separator + format + "." + extension;
            uploadImageByPath(file, pathToImage, resultOfUpload);
        }
    }

    private boolean removePostMainImage(int entityId) {
        Post post = blogService.getPost(entityId);
        ImageFormat[] formats = ImageFormat.getFormatsByDir(ImageDir.postMain);
        boolean isDeleted = fileSystem.removeImage(entityId, formats);
        if (isDeleted) {
            post.setHasMainImage(false);
            blogService.save(post);
        }
        return isDeleted;
    }


    private List<String> uploadImageByPath(MultipartFile file, String path, List<String> resultOfUpload) {
        if (!file.isEmpty()) {
            StringBuilder fileName = new StringBuilder(path);
            File newFile = new File(fileName.toString());

            try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(newFile))) {
                byte[] fileBytes = file.getBytes();
                stream.write(fileBytes);
                stream.close();

                fileName.insert(0, "File is saved under: ");
                resultOfUpload.add(fileName.toString());
            } catch (IOException e) {
                e.printStackTrace();
                resultOfUpload.add("File upload is failed: " + e.getMessage());
            }
        } else {
            resultOfUpload.add("File: " + file.getOriginalFilename() + " upload is failed: File is empty");
        }
        return resultOfUpload;
    }
}
