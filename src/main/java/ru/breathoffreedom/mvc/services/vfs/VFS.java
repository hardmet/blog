package ru.breathoffreedom.mvc.services.vfs;

import ru.breathoffreedom.mvc.models.file.Image;
import ru.breathoffreedom.mvc.services.image.ImageFormat;

import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * This interface is needed for file and directories processing
 * It contains main methods for work with files, iterating into directories and
 * finding some places at the File System of the OS
 */
public interface VFS {

    boolean isExist(String path);

    boolean isDirectory(String path);

    String getAbsolutePath(String file);

    Iterator<File> getIterator(String startDir);

    String getRoot();

    String getDirectory(String directoryName);

    List<File> getPathsToFiles(String rootDirectory);

    int getCountOfImages(String directory);

    boolean renameImages(String directory, int[] ids);

    boolean removeImages(int postId);

    boolean removeImage(Image image, ImageFormat [] formats);

}
