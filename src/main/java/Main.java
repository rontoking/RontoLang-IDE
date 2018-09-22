import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.fxmisc.richtext.LineNumberFactory;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import java.util.stream.Stream;

// TODO: Make documentation

public class Main extends Application {
    private BorderPane root = new BorderPane();
    private BorderPane centerPane = new BorderPane();
    private SplitPane splitPane = new SplitPane();
    private AutocompletionTextArea codeArea = new AutocompletionTextArea();
    private TreeView<String> projectView = new TreeView<>();
    private TreeView documentation = Documentation.get();
    private BorderPane botPane = new BorderPane();
    private HBox projectPane = new HBox();
    private HBox codePane = new HBox();
    private HBox resourcePane = new HBox();
    private MenuBar topPane = new MenuBar();
    private TabPane tabPane = new TabPane();
    private TextArea log = new TextArea();
    private TextArea errorLog = new TextArea();
    private SplitPane logPane = new SplitPane();

    private double scroll = 24;
    private String projectRoot, templatePath;
    private LabeledTextField projectName;
    private Button runProjectButton;
    private Process process;

    private Stage primaryStage;
    private boolean IGNORE_TEXT_CHANGED;
    private boolean IS_IDE_BUSY;

    private Preferences preferences;
    private final String LAST_PROJECT_OPENED = "lastProjectOpened";

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        templatePath = Paths.get(".").toAbsolutePath().toString();
        templatePath = templatePath.substring(0, templatePath.length() - 1);
        templatePath = templatePath + "Template/";

        addNodes();
        primaryStage.setTitle("RontoLang IDE");
        primaryStage.getIcons().add(new Image("icon.png"));
        primaryStage.setScene(new Scene(root, 1600, 900));

        IGNORE_TEXT_CHANGED = false;
        IS_IDE_BUSY = false;

        preferences = Preferences.userNodeForPackage(Main.class);
        String lastProject = preferences.get(LAST_PROJECT_OPENED, "Default Project");
        if(Files.exists(Paths.get(Paths.get(templatePath).getParent().toAbsolutePath().toString() + "/Projects/" + lastProject))) {
            projectName.field.setText(lastProject);
        }else{
            projectName.field.setText("Default Project");
        }
        createProject();

        primaryStage.setOnCloseRequest(e -> {
            saveCodeFiles(false);
        });
        primaryStage.show();
    }

    private void addNodes(){
        initBotPane();
        initCodeArea();
        initProjectView();
        initTopPane();
        initTabPane();

        log.setEditable(false);
        log.styleProperty().set("-fx-text-fill: #ffffff;-fx-control-inner-background:#000000;-fx-font-weight: bold;");

        splitPane.getItems().addAll(projectView, codeArea, documentation);
        splitPane.setDividerPosition(0, 0.15f);
        splitPane.setDividerPosition(1, 0.85f);

        errorLog.setEditable(false);
        errorLog.styleProperty().set("-fx-text-fill: #ff0000;-fx-control-inner-background:#000000;-fx-font-weight: bold;");

        logPane.getItems().addAll(log, errorLog);
        logPane.setDividerPosition(0, 0.5f);

        centerPane.setCenter(splitPane);
        centerPane.setTop(tabPane);
        centerPane.setBottom(logPane);

        root.setCenter(centerPane);
        root.setTop(topPane);
        root.setBottom(botPane);
    }

    private void initTabPane(){
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            displayTabCode();
        });
    }

    private void displayTabCode(){
        IGNORE_TEXT_CHANGED = true;
        codeArea.clear();
        if(tabPane.getSelectionModel().getSelectedItem() == null){
            codeArea.setDisable(true);
        }else {
            codeArea.setDisable(false);
            codeArea.appendText(((CodeTab) tabPane.getSelectionModel().getSelectedItem()).code);
        }
        IGNORE_TEXT_CHANGED = false;
    }

    private void deleteFolder(Path folder) {
        try(Stream<Path> files = Files.list(folder)){
            Path[] list = files.toArray(Path[]::new);
            if(list != null){
                for(int i = 0; i < list.length; i++) {
                    if(Files.isDirectory(list[i])) {
                        deleteFolder(list[i]);
                    } else if(!list[i].getFileName().toString().equals("rontolang.png") && !list[i].getFileName().toString().equals("rontolang.wav") && !list[i].getFileName().toString().equals("cavestory.ttf")){
                        try {
                            Files.delete(list[i]);
                        } catch (IOException e) {
                            e.printStackTrace();
                            log.appendText(e.toString());
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copyToTemplate(String folderName) {
        try(Stream<Path> files = Files.list(Paths.get(projectRoot + "/resources/" + folderName))){
            Path[] list = files.toArray(Path[]::new);
            if(list != null){
                for(int i = 0; i < list.length; i++) {
                    try {
                        Files.copy(list[i], Paths.get(templatePath + folderName + "/" + list[i].getFileName().toString()), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        e.printStackTrace();
                        log.appendText(e.toString());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getSourceCode(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < tabPane.getTabs().size(); i++){
            sb.append(((CodeTab)tabPane.getTabs().get(i)).code + " ");
        }
        return sb.toString();
    }

    private void saveCodeFiles(boolean isChained){
        if(!IS_IDE_BUSY || isChained) {
            if(!isChained)
                IS_IDE_BUSY = true;
            deleteFolder(Paths.get(projectRoot + "resources/code"));
            for (int i = 0; i < tabPane.getTabs().size(); i++) {
                ((CodeTab) tabPane.getTabs().get(i)).save(projectRoot);
            }
            if(!isChained)
                IS_IDE_BUSY = false;
            log.appendText("Source code has been saved.\n");
        }else {
            log.appendText("IDE is busy. Wait a bit and try again.");
        }
    }

    private void build(boolean isChained){
        if(!IS_IDE_BUSY || isChained) {
            if(!isChained)
                IS_IDE_BUSY = true;
            try {
                deleteFolder(Paths.get(projectRoot + "resources/code"));
                deleteFolder(Paths.get(templatePath + "images"));
                deleteFolder(Paths.get(templatePath + "sounds"));
                deleteFolder(Paths.get(templatePath + "music"));
                deleteFolder(Paths.get(templatePath + "fonts"));
                copyToTemplate("images");
                copyToTemplate("sounds");
                copyToTemplate("music");
                copyToTemplate("fonts");
                saveCodeFiles(true);
                Files.write(Paths.get(templatePath + "code/code"), getSourceCode().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
                Files.deleteIfExists(Paths.get(projectRoot + "build/" + projectName.field.getText() + ".jar"));
                ZipUtil.zipFile(Paths.get(templatePath).toAbsolutePath().toString(), Paths.get(projectRoot + "build/" + projectName.field.getText() + ".jar").toAbsolutePath().toString(), true);
                projectView.setRoot(getNodesForDirectory(Paths.get(projectRoot)));
                Utility.expand(projectView.getRoot());

            } catch (IOException e1) {
                e1.printStackTrace();
                log.appendText(e1.toString());
            }
            if(!isChained)
                IS_IDE_BUSY = false;
            log.appendText("JAR has been built.\n");
        }else {
            log.appendText("IDE is busy. Wait a bit and try again.");
        }
    }

    private void run() {
        if(runProjectButton.getText().equals("Run Project")) {
            errorLog.clear();
            if (!IS_IDE_BUSY) {
                build(false);
                Thread processThread = new Thread(() -> {
                    ProcessBuilder pb = new ProcessBuilder("java", "-jar", projectName.field.getText() + ".jar");
                    pb.directory(new File(projectRoot + "build"));
                    try {
                        pb.redirectErrorStream(true);
                        pb.redirectOutput(new File(projectRoot + "build/output"));
                        log.appendText("JAR is running.\n");
                        process = pb.start();
                        process.waitFor();
                        Platform.runLater(() -> {
                            runProjectButton.setText("Run Project");
                            log.appendText("JAR has finished running.\n");
                            try {
                                Files.deleteIfExists(Paths.get(projectRoot + "build/output"));
                            } catch (IOException e) {
                                e.printStackTrace();
                                log.appendText(e.toString());
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                        log.appendText(e.toString());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        log.appendText(e.toString());
                    }
                });
                Thread outputThread = new Thread(() -> {
                    while (runProjectButton.getText().equals("Terminate Process")){
                        Platform.runLater(() -> {
                            try {
                                errorLog.setText(new String(Files.readAllBytes(Paths.get(projectRoot + "build/output"))));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                outputThread.setDaemon(true);
                processThread.setDaemon(true);
                try {
                    Files.deleteIfExists(Paths.get(projectRoot + "build/output"));
                } catch (IOException e) {
                    e.printStackTrace();
                    log.appendText(e.toString());
                }
                runProjectButton.setText("Terminate Process");
                processThread.start();
                outputThread.start();
            }
        }else{
            process.destroyForcibly();
        }
    }

    private void initBotPane(){
        projectName = new LabeledTextField("Project Name: ", "Default Project");

        Button renameProjectBtn = new Button("Rename Project");
        renameProjectBtn.setOnAction(e -> {
            renameProject();
        });

        Button openProjectBtn = new Button("Open Project");
        openProjectBtn.setOnAction(e -> {
            createProject();
        });

        Button buildProjectBtn = new Button("Build Project");
        buildProjectBtn.setOnAction(e -> {
            build(false);
        });

        Button saveProjectBtn = new Button("Save Project");
        saveProjectBtn.setOnAction(e -> {
            saveCodeFiles(false);
        });

        runProjectButton = new Button("Run Project");
        runProjectButton.setOnAction(e -> {
            run();
        });

        projectPane.getChildren().addAll(projectName, runProjectButton, saveProjectBtn, buildProjectBtn, openProjectBtn, renameProjectBtn);

        LabeledTextField nameField = new LabeledTextField("Code File Name: ", "Main");
        Button addBtn = new Button("Add");
        addBtn.setOnAction(e -> {
            for(int i = 0; i < tabPane.getTabs().size(); i++){
                if(tabPane.getTabs().get(i).getText().equals(nameField.field.getText()))
                    return;
            }
            tabPane.getTabs().add(new CodeTab(nameField.field.getText()));
        });
        Button renameBtn = new Button("Rename");
        renameBtn.setOnAction(e -> {
            if(tabPane.getTabs().size() > 0){
                for(int i = 0; i < tabPane.getTabs().size(); i++){
                    if(tabPane.getTabs().get(i).getText().equals(nameField.field.getText()))
                        return;
                }
                tabPane.getSelectionModel().getSelectedItem().setText(nameField.field.getText());
            }
        });
        Button deleteBtn = new Button("Delete");
        deleteBtn.setOnAction(e -> {
            if(tabPane.getTabs().size() > 0){
                tabPane.getTabs().remove(tabPane.getSelectionModel().getSelectedIndex());
            }
        });
        codePane.getChildren().addAll(nameField, addBtn, renameBtn, deleteBtn);

        Button imgBtn = new Button("Add Image");
        Button fontBtn = new Button("Add Font");
        Button soundBtn = new Button("Add Sound");
        Button musicBtn = new Button("Add Music");
        Button removeBtn = new Button("Remove Resource");

        imgBtn.setOnAction(e -> {
            getResource(Paths.get(projectRoot + "resources/images/"), "Add Image", new String[]{"png", "jpg", "jpeg"});
        });
        fontBtn.setOnAction(e -> {
            getResource(Paths.get(projectRoot + "resources/fonts/"), "Add Font", new String[]{"ttf"});
        });
        soundBtn.setOnAction(e -> {
            getResource(Paths.get(projectRoot + "resources/sounds/"), "Add Sound", new String[]{"ogg", "wav", "mp3"});
        });
        musicBtn.setOnAction(e -> {
            getResource(Paths.get(projectRoot + "resources/music/"), "Add Music", new String[]{"ogg", "wav", "mp3"});
        });
        removeBtn.setOnAction(e -> {
            removeResource();
        });

        resourcePane.getChildren().addAll(imgBtn, fontBtn, soundBtn, musicBtn, removeBtn);
        resourcePane.setAlignment(Pos.CENTER);

        botPane.setLeft(codePane);
        botPane.setCenter(resourcePane);
        botPane.setRight(projectPane);
    }

    private void getResource(Path destination, String title, String[] extensions){
        FileChooser fileChooser = new FileChooser();
        for(int i = 0; i < extensions.length; i++){
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(extensions[i].toUpperCase(), "*." + extensions[i].toLowerCase()));
        }
        fileChooser.setTitle(title);
        File chosenFile = fileChooser.showOpenDialog(primaryStage);
        if (chosenFile != null) {
            try {
                Files.copy(chosenFile.toPath(), Paths.get(destination.toAbsolutePath().toString() + "/" + chosenFile.getName()));
                projectView.setRoot(getNodesForDirectory(((TreeFile)projectView.getRoot()).file));
                Utility.expand(projectView.getRoot());
            } catch (IOException e) {
                e.printStackTrace();
                log.appendText(e.toString());
            }
        }
    }

    private void setProjectRoot(){
        projectRoot = Paths.get(".").toAbsolutePath().toString();
        projectRoot = projectRoot.substring(0, projectRoot.length() - 1);
        projectRoot = projectRoot + "Projects/" + projectName.field.getText() + "/";
    }

    private void removeResource(){
        TreeFile tf = (TreeFile)projectView.getSelectionModel().getSelectedItem();
        if(tf != null && !Files.isDirectory(tf.file) && !tf.getValue().toString().split("/.")[1].equals("jar")){
            try {
                Files.deleteIfExists(tf.file);
                projectView.setRoot(getNodesForDirectory(((TreeFile)projectView.getRoot()).file));
                Utility.expand(projectView.getRoot());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void renameProject(){
        try {
            Files.move(Paths.get(projectRoot), Paths.get(Paths.get(projectRoot).getParent().toAbsolutePath().toString() + "/" + projectName.field.getText()));
            preferences.put(LAST_PROJECT_OPENED, projectName.field.getText());
            preferences.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }
        setProjectRoot();
        projectView.setRoot(getNodesForDirectory(Paths.get(projectRoot)));
        Utility.expand(projectView.getRoot());
    }

    private void createProject(){
        try {
            preferences.put(LAST_PROJECT_OPENED, projectName.field.getText());
            preferences.flush();
            setProjectRoot();
            Files.createDirectories(Paths.get(projectRoot + "build"));
            Files.createDirectories(Paths.get(projectRoot + "resources/images"));
            Files.createDirectories(Paths.get(projectRoot + "resources/sounds"));
            Files.createDirectories(Paths.get(projectRoot + "resources/music"));
            Files.createDirectories(Paths.get(projectRoot + "resources/fonts"));
            Files.createDirectories(Paths.get(projectRoot + "resources/code"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }

        IGNORE_TEXT_CHANGED = true;
        codeArea.clear();
        tabPane.getTabs().clear();
        IGNORE_TEXT_CHANGED = false;

        try(Stream<Path> files = Files.list(Paths.get(projectRoot + "resources/code"))) {
            Path[] list = files.toArray(Path[]::new);
            if(list != null){
                for(int i = 0; i < list.length; i++){
                    tabPane.getTabs().add(new CodeTab(list[i].getFileName().toString()));
                    try {
                        ((CodeTab)tabPane.getTabs().get(i)).code = new String(Files.readAllBytes(list[i]));
                    } catch (IOException e) {
                        e.printStackTrace();
                        log.appendText(e.toString());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayTabCode();

        projectView.setRoot(getNodesForDirectory(Paths.get(projectRoot)));
        Utility.expand(projectView.getRoot());
    }

    private void initTopPane(){
        MenuItem saveItem = new MenuItem("Save Project");
        saveItem.setOnAction(e -> {
            saveCodeFiles(false);
        });
        saveItem.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        MenuItem runItem = new MenuItem("Run Project");
        runItem.setOnAction(e -> {
            run();
        });
        runItem.setAccelerator(KeyCombination.keyCombination("F5"));
        MenuItem buildItem = new MenuItem("Build Project");
        buildItem.setOnAction(e -> {
            build(false);
        });
        buildItem.setAccelerator(KeyCombination.keyCombination("Ctrl+B"));

        MenuItem openItem = new MenuItem("Open Project");
        openItem.setOnAction(e -> {
            createProject();
        });
        MenuItem renameItem = new MenuItem("Rename Project");
        renameItem.setOnAction(e -> {
            renameProject();
        });

        Menu projectMenu = new Menu("Project");
        projectMenu.getItems().addAll(openItem, renameItem, saveItem, buildItem, runItem);

        MenuItem imgItem = new MenuItem("Add Image");
        MenuItem fontItem = new MenuItem("Add Font");
        MenuItem soundItem = new MenuItem("Add Sound");
        MenuItem musicItem = new MenuItem("Add Music");
        MenuItem removeResourceItem = new MenuItem("Remove Resource");

        imgItem.setOnAction(e -> {
            getResource(Paths.get(projectRoot + "resources/images/"), "Add Image", new String[]{"png", "jpg", "jpeg"});
        });
        fontItem.setOnAction(e -> {
            getResource(Paths.get(projectRoot + "resources/fonts/"), "Add Font", new String[]{"ttf"});
        });
        soundItem.setOnAction(e -> {
            getResource(Paths.get(projectRoot + "resources/sounds/"), "Add Sound", new String[]{"ogg", "wav", "mp3"});
        });
        musicItem.setOnAction(e -> {
            getResource(Paths.get(projectRoot + "resources/music/"), "Add Music", new String[]{"ogg", "wav", "mp3"});
        });
        removeResourceItem.setOnAction(e -> {
            removeResource();
        });

        Menu resourceMenu = new Menu("Resource");
        resourceMenu.getItems().addAll(imgItem, fontItem, soundItem, musicItem, removeResourceItem);

        topPane.getMenus().addAll(projectMenu, resourceMenu);
    }

    private void initProjectView(){
        projectView.setOnMouseClicked(event -> {
            if(projectView.getSelectionModel().getSelectedIndex() != -1) {
                if( event.getButton() == MouseButton.PRIMARY) {
                    if(event.getClickCount() == 3){
                        projectView.setRoot(getNodesForDirectory(((TreeFile)projectView.getRoot()).file));
                        Utility.expand(projectView.getRoot());
                    }
                }
            }
        });
    }

    private TreeFile getNodesForDirectory(Path directory) {
        TreeFile root = new TreeFile(directory);
        try (Stream<Path> files = Files.list(directory)){
            Path[] list = files.toArray(Path[]::new);
            if(list != null){
                for (int i = 0; i < list.length; i++) {
                    if(!list[i].getFileName().toString().equals("code")) {
                        if (Files.isDirectory(list[i])) {
                            root.getChildren().add(getNodesForDirectory(list[i]));
                        } else if(!list[i].getParent().getFileName().toString().equals("build") || !list[i].getFileName().toString().equals("output")){
                            TreeFile tf = new TreeFile(list[i]);
                            root.getChildren().add(tf);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }

    private void initCodeArea(){
        codeArea.setDisable(true);
        codeArea.setOnMouseClicked(event -> {
            codeArea.entriesPopup.hide();
        });
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        codeArea.setStyle("-fx-font-weight: bold;-fx-background-color: #ffffff");
        codeArea.addEventFilter(ScrollEvent.ANY, (e) -> {
            if(e.isControlDown()) {
                codeArea.setStyle("-fx-font-weight: bold;-fx-background-color: #ffffff; -fx-font-size:" + Utility.limit(scroll + e.getDeltaY() * 0.1d, 10, 100));
                scroll = Utility.limit(scroll + e.getDeltaY() * 0.1d, 10, 100);
            }
        });
        codeArea.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER){
                if(codeArea.getText().length() > 0 && codeArea.getText().substring(0, codeArea.getCaretPosition()).trim().length() > 0 && codeArea.getText().substring(0, codeArea.getCaretPosition()).trim().charAt(codeArea.getText().substring(0, codeArea.getCaretPosition()).trim().length() - 1) == '{'){
                    codeArea.insertText(codeArea.getCaretPosition(), Utility.repeat("\t", Utility.getIndentNum(codeArea.getParagraph(codeArea.getCurrentParagraph() - 1).getText())) + "\t");
                    int pos = codeArea.getCaretPosition();
                    codeArea.insertText(codeArea.getCaretPosition(),"\n" + Utility.repeat("\t", Utility.getIndentNum(codeArea.getParagraph(codeArea.getCurrentParagraph() - 1).getText())) + "}");
                    codeArea.moveTo(pos);
                }else {
                    codeArea.insertText(codeArea.getCaretPosition(), Utility.repeat("\t", Utility.getIndentNum(codeArea.getParagraph(codeArea.getCurrentParagraph() - 1).getText())));
                }
            }
        });
        codeArea.setOnKeyTyped(e -> {
            if(e.getCharacter().equals("(")){
                codeArea.insertText(codeArea.getCaretPosition(), ")");
                codeArea.moveTo(codeArea.getCaretPosition() - 1);
            }else if(e.getCharacter().equals("[")){
                codeArea.insertText(codeArea.getCaretPosition(), "]");
                codeArea.moveTo(codeArea.getCaretPosition() - 1);
            }else if(e.getCharacter().equals("\"") && (codeArea.getText().length() <= 1 || codeArea.getText().charAt(codeArea.getCaretPosition() - 2) != '/')){
                codeArea.insertText(codeArea.getCaretPosition(), "\"");
                codeArea.moveTo(codeArea.getCaretPosition() - 1);
            }else if(e.getCharacter().equals("'") && (codeArea.getText().length() <= 1 || codeArea.getText().charAt(codeArea.getCaretPosition() - 2) != '/')){
                codeArea.insertText(codeArea.getCaretPosition(), "'");
                codeArea.moveTo(codeArea.getCaretPosition() - 1);
            }
        });
        codeArea.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!IGNORE_TEXT_CHANGED) {
                ((CodeTab) tabPane.getSelectionModel().getSelectedItem()).code = newValue;
                codeArea.getEntries().clear();
                if(codeArea.getText().length() > 0)
                    codeArea.getEntries().addAll(AutoComplete.getEntries(getSourceCode() + Documentation.AUTOCOMPLETE_TEXT + " public static private class main update close true false"));
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}