package it.unibo.oop.lab.mvcio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 */
public class Controller {

    /*
     * This class must implement a simple controller responsible of I/O access. It
     * considers a single file at a time, and it is able to serialize objects in it.
     * 
     * Implement this class with:
     * 
     * 1) A method for setting a File as current file
     * 
     * 2) A method for getting the current File
     * 
     * 3) A method for getting the path (in form of String) of the current File
     * 
     * 4) A method that gets a String as input and saves its content on the current
     * file. This method may throw an IOException.
     * 
     * 5) By default, the current file is "output.txt" inside the user home folder.
     * A String representing the local user home folder can be accessed using
     * System.getProperty("user.home"). The separator symbol (/ on *nix, \ on
     * Windows) can be obtained as String through the method
     * System.getProperty("file.separator"). The combined use of those methods leads
     * to a software that runs correctly on every platform.
     */
    private static final String HOME = System.getProperty("user.home");
    private static final String SEPARATOR = System.getProperty("file.separator");
    //private static final String FILE_TYPE = ".txt";
    private static final String DEFAULT_FILE = "output.txt";
    private File currentFile = new File(HOME + SEPARATOR + DEFAULT_FILE);
    /**
     * A method for setting a File as current file.
     * 
     * @param s
     */
    public void setCurrentFile(final String s) {
        this.currentFile = new File(HOME + SEPARATOR + s);
    }
    /**
     * 
     * @return the currentFile
     */
    public File getCurrentFile() {
        return this.currentFile;
    }
    /**
     * 
     * @return current file path
     */
    public String getFilePath() {
        return this.currentFile.getPath();
    }
    /**
     * Method that gets a String as input and saves its content on the current
     * file.
     * 
     * @param s
     * @throws IOExcetion
     */
    public void writeOnFile(final String s) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.getCurrentFile()))) {
            bw.write(s);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
