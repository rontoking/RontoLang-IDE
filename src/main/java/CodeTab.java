import javafx.scene.control.Tab;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class CodeTab extends Tab {
    public String code;

    public CodeTab(String name){
        super(name);
        code = "";
    }

    public void save(String projectPath){
        try {
            Files.write(new File(projectPath + "resources\\code\\" + getText()).toPath(), code.getBytes(), StandardOpenOption.CREATE_NEW, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
