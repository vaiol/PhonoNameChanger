
import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.*;


public class Main {

    public static void main(String[] args) {
        System.out.println("---- START ----");
        String name = "IMG";
        if(args.length != 0) {
            name = args[0];
        }
         make(name);
        System.out.println("---- END ----");

    }

    public static void make(String surName) {

        String currDir = new File("").getAbsolutePath();
        File[] files = new File(currDir).listFiles(new ImageNameFilter());
        if(files == null) {
            return;
        }

        for(int i = 0; i < files.length - 1; i++) {
            for (int j = 0; j < files.length - i - 1; j++) {
                if (getTimeLong(files[j]) > getTimeLong(files[j + 1])) {
                    File tmp = files[j];
                    files[j] = files[j + 1];
                    files[j + 1] = tmp;
                }
            }
            System.out.println(files[i]);
        }

        int i = 1;
        //String backup = "";
        for (File file : files) {
            //backup += file.getName() + "\n";
            String name = surName + "_" + trimNumber(i) + "." + getExecute(file.getName());
            file.renameTo(new File(name));
            i++;
        }
        //write("backup.data", backup);
    }

    private static String getExecute(String name) {
        String[] result = name.split("\\.");
        if(result.length > 0) {
            return result[result.length - 1];
        }
        return "";
    }

    private static String trimNumber(int i) {
        if(i < 0) {
            i *= -1;
        }
        if (i >= 10000) {
            return i + "";
        }
        String number = i +"";
        switch (number.length()) {
            case 1:
                number = "000" + number;
                break;
            case 2:
                number = "00" + number;
                break;
            case 3:
                number = "0" + number;
                break;
            default:
                break;
        }
        return number;

    }



    private static String getMetaTime(Metadata metadata)
    {
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                if(tag.getTagName().contains("Date/Time")) {
                    return tag.getDescription();
                }
            }
        }
        return null;
    }

    private static long getTimeLong(File file) {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            String sd = getMetaTime(metadata);
           // print(metadata);
            if(sd != null) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy:MM:DD HH:mm:ss");
                Date date = format.parse(sd);
                return date.getTime();
            }
        } catch (Exception e) {
        }
        return getFileCreateTime(file);
    }


    public static long getFileCreateTime(File file) {
        try {
            return Files.readAttributes(file.toPath(), BasicFileAttributes.class).creationTime().toMillis();
        } catch (IOException e) {
            return -1;
        }
    }
//
//    public static void write(String fileName, String text) {
//        File file = new File(fileName);
//        try {
//            if(!file.exists()){
//                file.createNewFile();
//            }
//            PrintWriter out = new PrintWriter(file.getAbsoluteFile());
//            try {
//                out.write(text);
//            } finally {
//                out.close();
//            }
//        } catch(IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private static void print(Metadata metadata)
//    {
//        System.out.println("-------------------------------------");
//        for (Directory directory : metadata.getDirectories()) {
//            for (Tag tag : directory.getTags()) {
//                System.out.println(tag);
//            }
//        }
//        System.out.println("-------------------------------------");
//    }
}
