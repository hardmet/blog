package ru.breathoffreedom.mvc.services.vfs;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.breathoffreedom.mvc.models.file.Image;
import ru.breathoffreedom.mvc.services.image.ImageFormat;

import javax.activation.MimetypesFileTypeMap;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * This class is implementation of Virtual File System. It' needed for
 * file processing, encapsulating and keeping wire with OS file system
 */
@Service
public class VFSImpl implements VFS {

    @Value("${file.system.root}")
    private String root;

    @Override
    public String getRoot() {
        return root;
    }

    @Override
    public String getMediaDir() {
        int index = root.lastIndexOf(File.separator);
        return root.substring(index);
    }

    /**
     * Method getDirectory create directory if it not exist or return
     * null if error happened or return existing directory
     *
     * @param directoryName - relative path to directory what is needed excluding root directory
     * @return - directory or null if directory doesn't exist and can't to be created
     */
    @Override
    public String getDirectory(String directoryName) {
        String dirPath = root + File.separator + directoryName;
        String [] dirs = dirPath.split(File.separator);
        String dir = File.separator + dirs[1];
        for (int i = 1; i < dirs.length; i++) {
            File tempDirectory = new File(dir);
            if (!tempDirectory.exists()) {
                if (!tempDirectory.mkdir()) {
                    throw new ResourceNotFoundException("This directory didn't created! " +
                            "Wrong path or system doesn't have permissions to create this directory");
                }
            }
            if (i == dirs.length - 1) {
                return File.separator + directoryName;
            }
            dir = dir + File.separator + dirs[i + 1];
        }
        return File.separator + directoryName;
    }

    @Override
    public boolean isExist(String path) {
        return new File(root + path).exists();
    }

    @Override
    public boolean isDirectory(String path) {
        return new File(root + path).isDirectory();
    }

    @Override
    public String getAbsolutePath(String file) {
        return new File(root + file).getAbsolutePath();
    }

    @Override
    public String getImage(int imageId, int postId, ImageFormat format) {
        return getMediaDir() + File.separator + "post" + File.separator + postId +
                File.separator + imageId + File.separator + format + ".jpg";
    }

    @Override
    public String getMainImage(int postId, ImageFormat format) {
        return getMediaDir() + File.separator + "post" + File.separator + postId +
                File.separator + format + ".jpg";
    }

    /**
     * Method getPathsToFiles need for return List of files placed at the directory
     * Searching is recursive.
     *
     * @param rootDirectory - relative path to directory what is needed excluding root directory
     * @return - list of files, without folders
     */
    @Override
    public List<File> getPathsToFiles(String rootDirectory) {
        FileIterator iterator = new FileIterator(rootDirectory);
        List<File> listOfFiles = new ArrayList<>();
        while (iterator.hasNext()) {
            listOfFiles.add(iterator.next());
        }
        return listOfFiles;
    }

    /**
     * This method searching paths to files of directory, and counting the files
     * that have mimeType first part image
     *
     * @param directory - directory where counting is happened
     * @return - count of image file in directory
     */
    @Override
    public int getCountOfImages(String directory) {
        List<File> files = getPathsToFiles(directory);
        int counter = 0;
        for (File file : files) {
            String mimeType = new MimetypesFileTypeMap().getContentType(file);
            String type = mimeType.split("/")[0];
            if (type.equals("image")) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * This method is renaming all images in directory by [id].[extension] pattern
     *
     * @param directory - where images needed to rename
     * @param ids       - new names for file
     * @return - result of rename
     */
    public boolean renameImages(String directory, int[] ids) {
        Iterator iterator = getIterator(directory);
        for (int i = 0; iterator.hasNext(); ) {
            Path pathToFile = Paths.get((String) iterator.next());
            File fileToRename = new File(pathToFile.toString());
            String extension = getFileExtension(fileToRename);
            if (extension.equals("jpg") || extension.equals("png") || extension.equals("jpeg")) {
                String fileName = root + File.separator + directory + File.separator +
                        String.valueOf(ids[i]) + "." + extension;
                if (!new File(fileName).exists()) {
                    if (fileToRename.renameTo(new File(fileName))) {
                        i++;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean removeImages(int postId) {
        boolean resultOfDelete = false;
        FileIterator iterator = new FileIterator("images" + File.separator + "post" +
                File.separator + postId);
        while (iterator.hasNext()) {
            File file = iterator.next();
            try {
                resultOfDelete = removeFile(file);
            } catch (IOException e) {
                e.printStackTrace();
                return resultOfDelete;
            }
        }
        return true;
    }

    @Override
    public boolean removeImage(Image image, ImageFormat[] formats) {
        boolean resultOfDelete;
        for (ImageFormat format : formats) {
            String pathToImage = getRoot()+ File.separator + "post" + File.separator +
                    image.getPost().getId() + File.separator + image.getId() + File.separator +
                    format.name() + ".jpg";
            resultOfDelete = removeByPath(pathToImage);
            if (!resultOfDelete) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean removeImage(int postId, ImageFormat[] formats) {
        boolean resultOfDelete;
        for (ImageFormat format : formats) {
            String pathToImage = getRoot() + File.separator + "post" + File.separator + postId +
                                    File.separator + format + ".jpg";
            resultOfDelete = removeByPath(pathToImage);
            if (!resultOfDelete) {
                return false;
            }
        }
        return true;
    }


    private boolean removeByPath(String path) {
        File file = new File(path);
        try {
            return removeFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean removeFile(File file) throws IOException {
        if (!file.delete()) {
            throw new IOException("Wrong deleting image:" + file.getAbsolutePath());
        }
        return true;
    }

    /**
     * finds the file extension from all file name
     *
     * @param file - file is needed to find extension
     * @return - extension or ""
     */
    private static String getFileExtension(File file) {
        if (file != null) {
            String fileName = file.getName();
            getFileExtension(fileName);
        }
        return "";
    }

    public static String getFileExtension(String fileName) {
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            return fileName.substring(i + 1);
        }
        return "";
    }

    /**
     * method for give FileIterator in directory
     *
     * @param startDir - root directory for iterator
     * @return - FileIterator ready to visit directories and files
     */
    @Override
    public Iterator<File> getIterator(String startDir) {
        return new FileIterator(startDir);
    }

    /**
     * This help class for using File Iterations.
     */
    private class FileIterator implements Iterator<File> {
        private Queue<File> files = new LinkedList<>();

        /**
         * constructor of File Iterator
         *
         * @param path - path to directory where iterator will be start visiting
         */
        FileIterator(String path) {
            files.add(new File(root + File.separator + path));
        }

        @Override
        public boolean hasNext() {
            return !files.isEmpty();
        }

        /**
         * getting next absolute path to the next file
         *
         * @return - absolute path to file as String
         */
        @Override
        public File next() {
            File file = files.peek();
            if (file.isDirectory()) {
                files.remove(file);
                File[] listFiles = file.listFiles();
                if (listFiles != null) {
                    Collections.addAll(files, listFiles);
                }
            }
            return files.poll();
        }

        @Override
        public void remove() {
            files.remove();
        }
    }
}
