package net.bytecoding.epublibrary.utils;

import android.util.Log;

import org.apache.commons.io.FileUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class ZipUtil {
    private static final int BUFFER_SIZE = 1024 * 8;


    public static void unzipAll(File sourceZipFile, File unzipDestinationDirectory) throws IOException {
        int BUFFER = 2048;
        List zipFiles = new ArrayList();
//        File sourceZipFile = new File(inputZip);
//        File unzipDestinationDirectory = new File(destinationDirectory);
        unzipDestinationDirectory.mkdirs();

        ZipFile zipFile;
        // Open Zip file for reading
        zipFile = new ZipFile(sourceZipFile, ZipFile.OPEN_READ);

        // Create an enumeration of the entries in the zip file
        Enumeration zipFileEntries = zipFile.entries();

        // Process each entry
        while (zipFileEntries.hasMoreElements()) {
            // grab a zip file entry
            ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();

            String currentEntry = entry.getName();
            System.out.println("create : " + currentEntry);

            File destFile = new File(unzipDestinationDirectory, currentEntry);

            if (currentEntry.endsWith(".zip")) {
                zipFiles.add(destFile.getAbsolutePath());
            }

            // grab file's parent directory structure
            File destinationParent = destFile.getParentFile();

            // create the parent directory structure if needed
            destinationParent.mkdirs();

            try {
                // extract file if not a directory
                if (!entry.isDirectory()) {
                    BufferedInputStream is = new BufferedInputStream(zipFile.getInputStream(entry));
                    int currentByte;
                    // establish buffer for writing file
                    byte data[] = new byte[BUFFER];

                    // write the current file to disk
                    FileOutputStream fos = new FileOutputStream(destFile);
                    BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);

                    // read and write until last byte is encountered
                    while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
                        dest.write(data, 0, currentByte);
                    }
                    dest.flush();
                    dest.close();
                    is.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        zipFile.close();

        for (Iterator iter = zipFiles.iterator(); iter.hasNext(); ) {
            String zipName = (String) iter.next();
            File sourceZipFile1 = new File(zipName);
            String destinationDirectory = unzipDestinationDirectory.getParent()  + zipName.substring(0, zipName.lastIndexOf(".zip"));
            File unzipDestinationDirectory1 = new File(destinationDirectory);
            unzipAll(sourceZipFile1, unzipDestinationDirectory1);
        }

    }

    /**
     * Unzip archive file all
     *
     * @param zipFile
     * @param targetDir
     * @throws IOException
     */
    public static final void _unzipAll(File zipFile, File targetDir) throws IOException {
        ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
        ZipEntry zentry = null;

        // if exists remove
        if (targetDir.exists()) {
            FileUtils.deleteDirectory(targetDir);
            targetDir.mkdirs();
        } else {
            targetDir.mkdirs();
        }

        // unzip all entries
        while ((zentry = zis.getNextEntry()) != null) {
            String fileNameToUnzip = zentry.getName();
            File targetFile = new File(targetDir, fileNameToUnzip);

            // if directory
            if (zentry.isDirectory()) {
                (new File(targetFile.getAbsolutePath())).mkdirs();
            } else {
                // make parent dir
                (new File(targetFile.getParent())).mkdirs();

                unzipEntry(zis, targetFile);
            }
        }
        zis.close();
    }

    /**
     * Unzip one entry
     *
     * @param zis
     * @param targetFile
     * @return
     * @throws IOException
     */
    @SuppressWarnings("resource")
    private static final File unzipEntry(ZipInputStream zis, File targetFile) throws IOException {
        FileOutputStream fos = new FileOutputStream(targetFile);

        byte[] buffer = new byte[zis.available()];
        int len = 0;
        while ((len = zis.read(buffer)) != -1) {
            fos.write(buffer, 0, len);
        }

        return targetFile;
    }


    public static void unzip(String zipFile, String location) throws IOException {
        try {
            File f = new File(location);
            if (!f.isDirectory()) {
                f.mkdirs();
            }
            ZipInputStream zin = new ZipInputStream(new FileInputStream(zipFile));
            try {
                ZipEntry ze = null;
                while ((ze = zin.getNextEntry()) != null) {
                    String path = location + ze.getName();

                    if (ze.isDirectory()) {
                        File unzipFile = new File(path);
                        if (!unzipFile.isDirectory()) {
                            unzipFile.mkdirs();
                        }
                    } else {
                        FileOutputStream fout = new FileOutputStream(path, false);
                        try {
                            for (int c = zin.read(); c != -1; c = zin.read()) {
                                fout.write(c);
                            }
                            zin.closeEntry();
                        } finally {
                            fout.close();
                        }
                    }
                }
            } finally {
                zin.close();
            }
        } catch (Exception e) {
            Log.e("msg: ", "Unzip exception", e);
        }
    }
}
