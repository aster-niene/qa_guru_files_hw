package com.file.test.utils;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileUtils {

    public static InputStream getFile(ZipFile file, String fileName) throws IOException {
        Enumeration<? extends ZipEntry> allZe = file.entries();
        while (allZe.hasMoreElements()) {
            ZipEntry ze = allZe.nextElement();
            if (ze.getName().equals(fileName)) {
                return file.getInputStream(ze);
            }
        }
        return InputStream.nullInputStream();
    }
}