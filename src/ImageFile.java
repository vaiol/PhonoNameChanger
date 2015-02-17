import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageFile {
    private File file;
    private long creationTime;
    ImageFile(File file) {
        this.file = file;
        creationTime = getTime(file);
    }
    
    public long getCreationTime() {
        return creationTime;
    }

    public File getFile() {
        return file;
    }


    private static long getTime(File file) {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            String sd = getMetaTime(metadata);
            // print(metadata);
            if (sd != null) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy:MM:DD HH:mm:ss");
                Date date = format.parse(sd);
                return date.getTime();
            }
        } catch (Exception e) {
            System.out.print("-!-");
        }
        return getAttributeTime(file);
    }

    private static String getMetaTime(Metadata metadata) {
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                if (tag.getTagName().contains("Date/Time")) {
                    return tag.getDescription();
                }
            }
        }
        return null;
    }


    private static long getAttributeTime(File file) {
        try {
            return Files.readAttributes(file.toPath(), BasicFileAttributes.class).creationTime().toMillis();
        } catch (IOException e) {
            return -1;
        }
    }
    
}
