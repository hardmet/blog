package ru.breathoffreedom.mvc.services.vfs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.List;


/**
 * This is test class for testing Virtual File System
 * Bean with VFS interface initialized on applicationContext.xml at the test resources
 * Tests checking countOfImages and RenamingFiles at the directories and etc.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:dispatcher-servlet.xml", "classpath:applicationContext.xml"})
public class VFSImplTest {

    @Autowired
    private VFS fileSystem;

    private String directory;
    private int[] ids;

    @Before
    public void setUp() throws Exception {
        directory = "post" + File.separator + "test";
        ids = new int[]{6, 7, 8};
        File[] files = new File[ids.length];
        for (int i = 0; i < ids.length; i++) {
            files[i] = new File(fileSystem.getRoot() + File.separator +
                    directory + File.separator + i + "image.jpg");
            if (files[i].exists()) {
                assert files[i].delete();
            }
            files[i] = new File(fileSystem.getRoot() + File.separator +
                    directory + File.separator + i + "image.jpg");
            if (!files[i].createNewFile()) {
                throw new RuntimeException("File didn't created!");
            }
        }
    }

    @After
    public void tearDown() throws Exception {
        File[] files = new File[ids.length];
        for (int i = 0; i < ids.length; i++) {
            files[i] = new File(fileSystem.getRoot() + File.separator +
                    directory + File.separator + ids[i] + ".jpg");
            if (files[i].exists()) {
                assert files[i].delete();
            }
        }
    }

    /**
     * this method counting number of images what have mime-type: image and returns it
     */
    @Test
    public void testGetCountOfImages() {
        assert fileSystem.getCountOfImages(directory) != 0;
//        List<String> paths = fileSystem.getPathsToFiles(directory);
//        for (String path : paths) {
//            System.out.println(path);
//        }
    }

    /**
     * this method rename files from directory and rename it to [ids(i)].[extension] format
     */
    @Test
    public void testRenameImages() {
        assert fileSystem.renameImages(directory, ids);
    }

}