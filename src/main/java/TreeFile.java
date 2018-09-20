import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;

public class TreeFile extends TreeItem{
    public Path file;
    public boolean isText;

    public TreeFile(Path f){
        super(f.getFileName().toString(), new ImageView(image(f)));
        file = f;
        if(Files.isDirectory(f))
            isText = false;
        else
            isText = true;
    }

    private static Image image(Path f){
        if(f != null) {
            ImageIcon icon = (ImageIcon) FileSystemView.getFileSystemView().getSystemIcon(f.toFile());
            if(icon != null) {
                BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
                Graphics g = image.createGraphics();
                icon.paintIcon(null, g, 0, 0);
                g.dispose();

                return SwingFXUtils.toFXImage(image, null);
            }
        }
        return null;
    }

    public static Path file(TreeFile tf){
        return tf.file;
    }
}
