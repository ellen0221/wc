import java.io.File;
import java.io.FilenameFilter;

public class FileFilter implements FilenameFilter {

    String extension;
    FileFilter(String extension)
    {
        this.extension = extension;
    }

    public boolean accept(File dir, String filename)
    {
        return filename.endsWith(extension);
    }
}
