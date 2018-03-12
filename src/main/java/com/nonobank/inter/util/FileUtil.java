package com.nonobank.inter.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by tangrubei on 2018/3/9.
 */
public class FileUtil {


    public static void deleteFile(File path){
        if (!path.exists()) {
            return;
        }
        if (path.isFile()) {
            path.delete();
            return;
        }
        File[] files = path.listFiles();
        for (int i = 0; i < files.length; i++) {
            deleteFile(files[i]);
        }
        path.delete();

    }

    public static void writeFile(String filepath,String context) throws IOException {
        Path path = Paths.get(filepath);
        Files.write(path,context.getBytes());
    }

    public static String readFile(String filepath) throws IOException {
        String restr = new String(Files.readAllBytes(Paths.get(filepath)));
        return restr;
    }

}
