import java.io.*;
import java.util.Date;


public class Main {

    public static void main(String[] args) {
        Date dateBefore = new Date();
        System.out.println("---- START ----");
        String name = "IMG";
        if (args.length != 0) {
            name = args[0];
        }
        make(name);

        System.out.println("---- END ----");
        Date dateAfter = new Date();
        System.out.println("Time: " + ((dateAfter.getTime() - dateBefore.getTime()) / 1000.0) + " sec.");

    }

    public static void make(String surName) {

        String currDir = new File("").getAbsolutePath();
        ImageFile[] files = getArraysImageFiles(new File(currDir).listFiles(new ImageNameFilter()));
        if (files == null) {
            return;
        }

        files = sort(files);

        int i = 1;
        //String backup = "";
        for (ImageFile file : files) {
            //backup += file.getName() + "\n";
            System.out.println(file.getFile().getName());
            String name = surName + "_" + trimNumber(i) + "." + getExecute(file.getFile().getName());
            file.getFile().renameTo(new File(name));
            i++;
        }
        //write("backup.data", backup);
    }

    private static ImageFile[] getArraysImageFiles(File[] files) {
        ImageFile[] imageFiles = new ImageFile[files.length];
        int i = 0;
        for (File file : files) {
            imageFiles[i] = new ImageFile(file);
            i++;
        }
        return imageFiles;
    }

    private static String getExecute(String name) {
        String[] result = name.split("\\.");
        if (result.length > 0) {
            return result[result.length - 1];
        }
        return "";
    }

    private static String trimNumber(int i) {
        if (i < 0) {
            i *= -1;
        }
        String number = i + "";
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




    public static ImageFile[] sort(ImageFile[] values) {
        // check for empty or null array
        if (values == null || values.length == 0) {
            return null;
        }
        return quicksort(values, 0, values.length - 1);
    }

    private static ImageFile[] quicksort(ImageFile[] files, int low, int high) {
        int i = low, j = high;
        // Get the pivot element from the middle of the list
        ImageFile pivot = files[low + (high - low) / 2];

        // Divide into two lists
        while (i <= j) {
            // If the current value from the left list is smaller then the pivot
            // element then get the next element from the left list
            while (files[i].getCreationTime() < pivot.getCreationTime()) {
                i++;
            }
            // If the current value from the right list is larger then the pivot
            // element then get the next element from the right list
            while (files[j].getCreationTime() > pivot.getCreationTime()) {
                j--;
            }

            // If we have found a values in the left list which is larger then
            // the pivot element and if we have found a value in the right list
            // which is smaller then the pivot element then we exchange the
            // values.
            // As we are done we can increase i and j
            if (i <= j) {
                exchange(files, i, j);
                i++;
                j--;
            }
        }
        // Recursion
        if (low < j)
            quicksort(files, low, j);
        if (i < high)
            quicksort(files, i, high);
        return files;
    }

    private static ImageFile[] exchange(ImageFile[] files, int i, int j) {
        ImageFile temp = files[i];
        files[i] = files[j];
        files[j] = temp;
        return files;
    }

//    private static String getParseTime(File file) {
//        String result = null;
//        try {
//            result = download(file.getAbsolutePath());
//        } catch (Exception e) {
//        }
//        return result;
//    }
//    public static String download(String path) {
//        StringBuilder result = new StringBuilder();
//        File file = new File(path);
//
//        try {
//            BufferedReader in = new BufferedReader(new FileReader( file.getAbsoluteFile()));
//            try {
//                String s;
//                while ((s = in.readLine()) != null) {
//                    result.append(s);
//                    result.append("\n");
//                }
//            } finally {
//                in.close();
//            }
//        } catch(IOException e) {
//            throw new RuntimeException(e);
//        }
//        return result.toString();
//    }
//
//
//
//
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

