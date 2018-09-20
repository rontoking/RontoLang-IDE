import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    public static void zipFile(String fileToZip, String zipFile, boolean excludeContainingFolder)
            throws IOException {
        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
        Path srcFile = Paths.get(fileToZip);
        if(excludeContainingFolder && Files.isDirectory(srcFile)) {
            try(Stream<Path> files = Files.list(srcFile)) {
                Path[] list = files.toArray(Path[]::new);
                if(list != null){
                    for(int i = 0; i < list.length; i++){
                        addToZip("", fileToZip + "/" + list[i].getFileName().toString(), zipOut);
                    }
                }
            }
        } else {
            addToZip("", fileToZip, zipOut);
        }

        zipOut.flush();
        zipOut.close();
    }

     private static void addToZip(String path, String srcFile, ZipOutputStream zipOut)
            throws IOException {
        Path file = Paths.get(srcFile);
        String filePath = "".equals(path) ? file.getFileName().toString() : path + "/" + file.getFileName().toString();
        if (Files.isDirectory(file)) {
            try(Stream<Path> files = Files.list(file)) {
                Path[] list = files.toArray(Path[]::new);
                if(list != null){
                    for(int i = 0; i < list.length; i++){
                        addToZip(filePath, srcFile + "/" + list[i].getFileName().toString(), zipOut);
                    }
                }
            }
        } else {
            zipOut.putNextEntry(new ZipEntry(filePath));
            FileInputStream in = new FileInputStream(srcFile);

            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int len;
            while ((len = in.read(buffer)) != -1) {
                zipOut.write(buffer, 0, len);
            }
            in.close(); // THIS DID NOT EXIST WHEN I COPIED THIS STUPID CLASS!!! SO MANY 'used by process' FUCKING EXCEPTIONS! KMS!
        }
    }

    public static void extract(String jarFile, String destdir) throws IOException {
        java.util.jar.JarFile jarfile = new java.util.jar.JarFile(new File(jarFile)); //jar file path(here sqljdbc4.jar)
        java.util.Enumeration<java.util.jar.JarEntry> enu= jarfile.entries();
        while(enu.hasMoreElements())
        {
            java.util.jar.JarEntry je = enu.nextElement();

            Path fl = Paths.get(destdir, je.getName());
            if(!Files.exists(fl))
            {
                Files.createDirectories(fl.getParent());
                fl = Paths.get(destdir, je.getName());
            }
            if(je.isDirectory())
            {
                continue;
            }
            java.io.InputStream is = jarfile.getInputStream(je);
            FileOutputStream fo = new FileOutputStream(fl.toFile());
            while(is.available()>0)
            {
                fo.write(is.read());
            }
            fo.close();
            is.close();
        }
    }
}