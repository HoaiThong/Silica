package net.silica.readView.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class IOFile {

    public void writeFile(File file, String content) {
        // If the file doesn't exists, create and write to it
        // If the file exists, truncate (remove all content) and write to it
        try {
            FileWriter writer = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write(content);
            bw.close();
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

    public String readFile(String filePath) {
        String line = "";

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            line = reader.readLine();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;

    }

    public void saveCache(File file, String contents) {
        writeFile(file, contents);
    }

    public String getDataCache(String filePath) {
        return readFile(filePath);
    }
}
