import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageNameFilter implements FilenameFilter {
    ImageNameFilter() {
    }

    @Override
    public boolean accept(File dir, String name) {
        return isImage(name);
    }

    private static boolean isImage(String name) {
        Pattern p = Pattern.compile(".+\\.(jpg|png|bmp|jpeg)");
        Matcher m = p.matcher(name.toLowerCase());
        return m.matches();
    }
}
