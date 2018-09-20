import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class Documentation {
    public static String AUTOCOMPLETE_TEXT;
    private static StringBuilder stringBuilder;

    public static TreeView get(){
        stringBuilder = new StringBuilder();
        TreeView treeView = new TreeView();
        treeView.setCellFactory(tv ->  {
            TreeCell<String> cell = new TreeCell<String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                        setTooltip(null);
                    } else if (getTreeItem() == treeView.getRoot()) {
                        setText("RontoLang Documentation");
                        setTooltip(null);
                    } else {
                        setText(getTreeItem().getValue());
                        setTooltip(((DocumentationTreeItem)getTreeItem()).tooltip);
                    }
                }
            };
            return cell ;
        });
        treeView.setRoot(new TreeItem<>("RontoLang Documentation"));
        treeView.getRoot().getChildren().addAll(valueTypes(), globalVariables(), instructions());
        Utility.expand(treeView.getRoot());
        AUTOCOMPLETE_TEXT = stringBuilder.toString();
        return treeView;
    }

    private static DocumentationTreeItem valueTypes(){
        DocumentationTreeItem treeItem = new DocumentationTreeItem("Value Types");

        add(treeItem, 0, "any");

        add(treeItem, 1, "copy", true, "any");
        add(treeItem, 1, "type", true, "string");
        add(treeItem, 1, "print", true, "void");
        add(treeItem, 1, "println", true, "void");
        add(treeItem, 1, "serialize", true, "byte[]");

        add(treeItem, 0, "byte");
        add(treeItem, 0, "bool");
        add(treeItem, 0, "char");

        add(treeItem, 1, "isUpper", true, "bool");
        add(treeItem, 1, "isLower", true, "bool");
        add(treeItem, 1, "getUpper", true, "char");
        add(treeItem, 1, "getLower", true, "char");
        add(treeItem, 1, "isDigit", true, "bool");
        add(treeItem, 1, "isSpace", true, "bool");
        add(treeItem, 1, "isLetter", true, "bool");

        add(treeItem, 0, "int");

        add(treeItem, 1, "limit(int min, int max)", "int");

        add(treeItem, 0, "float");

        add(treeItem, 1, "round", true, "int");
        add(treeItem, 1, "ceil", true, "int");
        add(treeItem, 1, "floor", true, "int");
        add(treeItem, 1, "limit(float min, float max)", "float");

        add(treeItem, 0, "double", "A double-precision 64-bit IEEE 754 floating point.");

        add(treeItem, 1, "round", true, "int");
        add(treeItem, 1, "ceil", true, "int");
        add(treeItem, 1, "floor", true, "int");
        add(treeItem, 1, "limit(double min, double max)", "double");

        add(treeItem, 0, "string");

        add(treeItem, 1, "size", true, "int");
        add(treeItem, 1, "trim", true, "string");
        add(treeItem, 1, "getUpper", true, "string");
        add(treeItem, 1, "getLower", true, "string");
        add(treeItem, 1, "list", true, "char[]");

        add(treeItem, 1, "substring(int start)", "string");
        add(treeItem, 1, "substring(int start, int end)", "string");
        add(treeItem, 1, "charAt(int index)", "char");
        add(treeItem, 1, "replace(string target, string replacement)", "string");
        add(treeItem, 1, "split(string splitter)", "string[]");
        add(treeItem, 1, "split(string splitter, int limit)", "string[]");
        add(treeItem, 1, "indexOf(string s)", "int");
        add(treeItem, 1, "lastIndexOf(string s)", "int");
        add(treeItem, 1, "contains(string s)", "bool");
        add(treeItem, 1, "startsWith(string s)", "bool");
        add(treeItem, 1, "endsWith(string s)", "bool");

        // Complex variables

        add(treeItem, 0, "list");

        add(treeItem, 1, "size", true, "int");
        add(treeItem, 1, "shuffle", true, "void");
        add(treeItem, 1, "reverse", true, "void");
        add(treeItem, 1, "random", true, "any");

        add(treeItem, 1, "add(any item)", "void");
        add(treeItem, 1, "remove(int index)", "void");
        add(treeItem, 1, "join(string joiner)", "string");
        add(treeItem, 1, "insert(int index, any item)", "void");
        add(treeItem, 1, "startsWith(list start)", "bool");
        add(treeItem, 1, "endsWith(list end)", "bool");

        add(treeItem, 0, "map");

        add(treeItem, 1, "size", true, "int");
        add(treeItem, 1, "keys", true, "list");
        add(treeItem, 1, "values", true, "list");

        add(treeItem, 0, "file");

        add(treeItem, 1, "exists", true, "bool");
        add(treeItem, 1, "parent", true, "file");
        add(treeItem, 1, "path", true, "string");
        add(treeItem, 1, "name", true, "string");
        add(treeItem, 1, "extension", true, "string");
        add(treeItem, 1, "bytes", true, "byte[]");
        add(treeItem, 1, "read", true, "string");
        add(treeItem, 1, "isFolder", true, "bool");
        add(treeItem, 1, "size", true, "int");
        add(treeItem, 1, "lastModified", true, "int");
        add(treeItem, 1, "type", true, "string");
        add(treeItem, 1, "children", true, "file[]");
        add(treeItem, 1, "makeFolders", true, "void");
        add(treeItem, 1, "delete", true, "void");
        add(treeItem, 1, "empty", true, "void");
        add(treeItem, 1, "emptyFiles", true, "void");

        add(treeItem, 1, "write(string s)", "void");
        add(treeItem, 1, "append(string s)", "void");
        add(treeItem, 1, "writeBytes(byte[] b)", "void");
        add(treeItem, 1, "appendBytes(byte[] b)", "void");
        add(treeItem, 1, "rename(string s)", "void");
        add(treeItem, 1, "move(file f)", "void");
        add(treeItem, 1, "child(string name)", "file");
        add(treeItem, 1, "sibling(string name)", "file");

        add(treeItem, 0, "img");

        add(treeItem, 1, "width", true, "int");
        add(treeItem, 1, "height", true, "int");

        add(treeItem, 0, "font");

        add(treeItem, 1, "width(string text)", "float");
        add(treeItem, 1, "height(string text)", "float");

        add(treeItem, 0, "sound");

        add(treeItem, 1, "play", true, "int");
        add(treeItem, 1, "stop", true, "void");
        add(treeItem, 1, "loop", true, "int");

        add(treeItem, 1, "play(float volume)", "int");
        add(treeItem, 1, "play(float volume, float pitch, float pan)", "int");
        add(treeItem, 1, "stop(int id)", "void");
        add(treeItem, 1, "loop(float volume)", "int");
        add(treeItem, 1, "loop(float volume, float pitch, float pan)", "int");
        add(treeItem, 1, "setLooping(int id, bool looping)", "void");
        add(treeItem, 1, "setVolume(int id, float volume)", "void");
        add(treeItem, 1, "setPitch(int id, float pitch)", "void");
        add(treeItem, 1, "setPan(int id, float pan, float volume)", "void");

        add(treeItem, 0, "music");

        add(treeItem, 1, "play", true, "void");
        add(treeItem, 1, "stop", true, "void");
        add(treeItem, 1, "pause", true, "void");
        add(treeItem, 1, "isLooping", true, "bool");
        add(treeItem, 1, "getVolume", true, "float");
        add(treeItem, 1, "getPosition", true, "float");

        add(treeItem, 1, "setLooping(bool looping)", "void");
        add(treeItem, 1, "setVolume(float volume)", "void");
        add(treeItem, 1, "setPitch(float pitch)", "void");
        add(treeItem, 1, "setPan(float pan, float volume)", "void");

        add(treeItem, 0, "conn");

        add(treeItem, 1, "id", true, "int");
        add(treeItem, 1, "disconnect", true, "void");

        // RontoObjects

        add(treeItem, 0, "point");

        add(treeItem, 1, "x", false, "double");
        add(treeItem, 1, "y", false, "double");

        add(treeItem, 1, "length", true, "float");
        add(treeItem, 1, "normalized", true, "point");
        add(treeItem, 1, "degrees", true, "float");
        add(treeItem, 1, "radians", true, "float");
        add(treeItem, 1, "isUnit", true, "bool");

        add(treeItem, 1, "dst(point p)", "float");
        add(treeItem, 1, "dst(double x, double y)", "float");
        add(treeItem, 1, "rotateDeg(float degrees)", "point");
        add(treeItem, 1, "rotateRad(float radians)", "point");
        add(treeItem, 1, "rotate90(int direction)", "point");
        add(treeItem, 1, "limit(float limit)", "point");

        add(treeItem, 0, "rect");

        add(treeItem, 1, "x", false, "double");
        add(treeItem, 1, "y", false, "double");
        add(treeItem, 1, "width", false, "double");
        add(treeItem, 1, "height", false, "double");

        add(treeItem, 1, "center", true, "point");
        add(treeItem, 1, "area", true, "float");
        add(treeItem, 1, "ratio", true, "float");
        add(treeItem, 1, "perimeter", true, "float");

        add(treeItem, 0, "color");

        add(treeItem, 1, "r", false, "double");
        add(treeItem, 1, "g", false, "double");
        add(treeItem, 1, "b", false, "double");
        add(treeItem, 1, "a", false, "double");

        add(treeItem, 0, "sprite");

        add(treeItem, 1, "img", false, "img");
        add(treeItem, 1, "x", false, "double");
        add(treeItem, 1, "y", false, "double");
        add(treeItem, 1, "color", false, "color");
        add(treeItem, 1, "width", false, "double");
        add(treeItem, 1, "height", false, "double");
        add(treeItem, 1, "origin", false, "point");
        add(treeItem, 1, "scale", false, "point");
        add(treeItem, 1, "src", false, "rect");
        add(treeItem, 1, "angle", false, "double");
        add(treeItem, 1, "flipX", false, "bool");
        add(treeItem, 1, "flipY", false, "bool");

        add(treeItem, 1, "draw", true, "void");

        add(treeItem, 0, "camera2d");

        add(treeItem, 1, "x", false, "double");
        add(treeItem, 1, "y", false, "double");
        add(treeItem, 1, "zoom", false, "double");

        add(treeItem, 1, "mount", true, "void");

        add(treeItem, 1, "rotate(float degrees)", "void");

        return treeItem;
    }

    private static DocumentationTreeItem globalVariables(){
        DocumentationTreeItem treeItem = new DocumentationTreeItem("Global Variables");

        add(treeItem, 0, "break", true, "void");
        add(treeItem, 0, "construct", true, "void");
        add(treeItem, 0, "this", true, "any");
        add(treeItem, 0, "super", true, "any");
        add(treeItem, 0, "pressedKeys", true, "string[]");
        add(treeItem, 0, "mouseX", true, "int");
        add(treeItem, 0, "mouseY", true, "int");
        add(treeItem, 0, "mousePos", true, "point");
        add(treeItem, 0, "mouseDeltaX", true, "int");
        add(treeItem, 0, "mouseDeltaY", true, "int");
        add(treeItem, 0, "mouseDeltaPos", true, "point");
        add(treeItem, 0, "mouseScroll", true, "int");
        add(treeItem, 0, "defaultFont", true, "font");
        add(treeItem, 0, "server", true, "server");

        add(treeItem, 1, "clients", true, "conn[]");

        add(treeItem, 1, "host(int port)", "void");
        add(treeItem, 1, "host(int port, int writeBufferSize, int objectBufferSize)", "void");
        add(treeItem, 1, "send(any value)", "void");
        add(treeItem, 1, "send(conn client, any value)", "void");

        add(treeItem, 0, "client", true, "client");

        add(treeItem, 1, "join(string ip, int port)", "bool");
        add(treeItem, 1, "join(string ip, int port, int writeBufferSize, int objectBufferSize)", "bool");
        add(treeItem, 1, "send(any value)", "void");

        add(treeItem, 0, "socket", true, "socket");

        add(treeItem, 1, "isConnected", true, "bool");
        add(treeItem, 1, "queue", true, "int");
        add(treeItem, 1, "next", true, "bool");
        add(treeItem, 1, "stop", true, "void");

        add(treeItem, 1, "send(any value)", "void");

        add(treeItem, 0, "packet", true, "packet");

        add(treeItem, 1, "data", true, "any");
        add(treeItem, 1, "sender", true, "conn");
        add(treeItem, 1, "isConnection", true, "bool");
        add(treeItem, 1, "isDisconnection", true, "bool");
        add(treeItem, 1, "isNormalMessage", true, "bool");

        add(treeItem, 0, "deltaTime", true, "float");
        add(treeItem, 0, "filledShapes", true, "void");
        add(treeItem, 0, "lineShapes", true, "void");
        add(treeItem, 0, "pointShapes", true, "void");

        add(treeItem, 0, "prefs", true, "void");

        add(treeItem, 1, "open(string name)", "void");
        add(treeItem, 1, "put(string key, string value)", "void");
        add(treeItem, 1, "get(string key)", "void");

        add(treeItem, 0, "console", true, "void");

        add(treeItem, 1, "visible", false, "bool");
        add(treeItem, 1, "entered", false, "bool");
        add(treeItem, 1, "input", false, "string");
        add(treeItem, 1, "output", false, "string");

        add(treeItem, 0, "window", true, "void");

        add(treeItem, 1, "width", false, "int");
        add(treeItem, 1, "height", false, "int");
        add(treeItem, 1, "title", false, "string");
        add(treeItem, 1, "fullscreen", false, "bool");
        add(treeItem, 1, "resizable", false, "bool");
        add(treeItem, 1, "vsync", false, "bool");
        add(treeItem, 1, "decorated", false, "bool");

        return treeItem;
    }

    private static DocumentationTreeItem instructions(){
        DocumentationTreeItem treeItem = new DocumentationTreeItem("Instructions");

        add(treeItem, 0, "if(bool condition){BLOCK}","void");
        add(treeItem, 0, "else if(bool condition){BLOCK}", "void");
        add(treeItem, 0, "else{BLOCK}", "void");
        add(treeItem, 0, "return(any value)", "void");
        add(treeItem, 0, "print(any value)", "void");
        add(treeItem, 0, "println(any value)", "void");
        add(treeItem, 0, "super(?)", "any");
        add(treeItem, 0, "while(bool condition){BLOCK}", "void");
        add(treeItem, 0, "repeat(int timesToRepeat){BLOCK}", "void");
        add(treeItem, 0, "switch(any value){BLOCK}", "void");
        add(treeItem, 0, "case(any value){BLOCK}", "void");
        add(treeItem, 0, "case(){BLOCK}", "void");
        add(treeItem, 0, "for(START;CONDITION;REPEAT){BLOCK}", "void");
        add(treeItem, 0, "foreach(any var : list x){BLOCK}", "void");
        add(treeItem, 0, "when(bool condition){BLOCK}", "void");
        add(treeItem, 0, "whenever(bool condition){BLOCK}", "void");
        add(treeItem, 0, "thread(bool isDaemon){BLOCK}", "void");
        add(treeItem, 0, "runLater(bool trueForGDXFalseForGUI){BLOCK}", "void");
        add(treeItem, 0, "wait(int milliseconds)", "void");
        add(treeItem, 0, "exec{string expression}", "void");
        add(treeItem, 0, "random()", "float");
        add(treeItem, 0, "random(int range)", "int");
        add(treeItem, 0, "random(int start, int end)", "int");
        add(treeItem, 0, "abs(double num)", "double");
        add(treeItem, 0, "atan(double num)", "double");
        add(treeItem, 0, "atan2(double num)", "double");
        add(treeItem, 0, "sin(double num)", "double");
        add(treeItem, 0, "cos(double num)", "double");
        add(treeItem, 0, "tan(double num)", "double");
        add(treeItem, 0, "sinh(double num)", "double");
        add(treeItem, 0, "cosh(double num)", "double");
        add(treeItem, 0, "tanh(double num)", "double");
        add(treeItem, 0, "round(double num)", "double");
        add(treeItem, 0, "ceil(double num)", "double");
        add(treeItem, 0, "floor(double num)", "double");
        add(treeItem, 0, "asin(double num)", "double");
        add(treeItem, 0, "acos(double num)", "double");
        add(treeItem, 0, "max(double num1, double num2)", "double");
        add(treeItem, 0, "min(double num1, double num2)", "double");
        add(treeItem, 0, "sqrt(double num)", "double");
        add(treeItem, 0, "toDeg(double radians)", "double");
        add(treeItem, 0, "toRad(double degrees)", "double");
        add(treeItem, 0, "img(string name)", "img");
        add(treeItem, 0, "img(file f)", "img");
        add(treeItem, 0, "sound(string name)", "sound");
        add(treeItem, 0, "sound(file f)", "sound");
        add(treeItem, 0, "music(string name)", "music");
        add(treeItem, 0, "music(file f)", "music");
        add(treeItem, 0, "font(int size)", "font");
        add(treeItem, 0, "font(string name, int size)", "font");
        add(treeItem, 0, "font(file f, int size)", "font");
        add(treeItem, 0, "string(any value)", "string");
        add(treeItem, 0, "int(string s)", "int");
        add(treeItem, 0, "float(string s)", "float");
        add(treeItem, 0, "double(string s)", "double");
        add(treeItem, 0, "internal(string path)", "file");
        add(treeItem, 0, "local(string path)", "file");
        add(treeItem, 0, "external(string path)", "file");
        add(treeItem, 0, "absolute(string path)", "file");
        add(treeItem, 0, "classpath(string path)", "file");
        add(treeItem, 0, "rect(double x, double y, double width, double height)", "rect");
        add(treeItem, 0, "sprite(img x)", "sprite");
        add(treeItem, 0, "sprite(file f)", "sprite");
        add(treeItem, 0, "sprite(string name)", "sprite");
        add(treeItem, 0, "camera2d()", "camera2d");
        add(treeItem, 0, "point(double x, double y)", "point");
        add(treeItem, 0, "color(string name)", "color");
        add(treeItem, 0, "color(string name, float a)", "color");
        add(treeItem, 0, "color(float r, float g, float b)", "color");
        add(treeItem, 0, "color(float r, float g, float b, float a)", "color");
        add(treeItem, 0, "draw(string text)", "void");
        add(treeItem, 0, "draw(string text, int x, int y)", "void");
        add(treeItem, 0, "draw(font f, string text, int x, int y)", "void");
        add(treeItem, 0, "draw(string text, int x, int y, color c)", "void");
        add(treeItem, 0, "draw(font f, string text, int x, int y, color c)", "void");
        add(treeItem, 0, "draw(sprite s)", "void");
        add(treeItem, 0, "draw(sprite[] s)", "void");
        add(treeItem, 0, "fill(color c)", "void");
        add(treeItem, 0, "fill(float r, float g, float b)", "void");
        add(treeItem, 0, "fill(float r, float g, float b, float a)", "void");
        add(treeItem, 0, "dst(point p1, point p2)", "float");
        add(treeItem, 0, "dst(float x1, float y1, float x2, float y2)", "float");
        add(treeItem, 0, "serialize(any value)", "byte[]");
        add(treeItem, 0, "deserialize(byte[] data)", "any value");
        add(treeItem, 0, "keyDown(string name)", "bool");
        add(treeItem, 0, "keyPressed(string name)", "bool");
        add(treeItem, 0, "mouseDown(string name)", "bool");
        add(treeItem, 0, "mouseClicked(string name)", "bool");
        add(treeItem, 0, "mouseDouble(string name)", "bool");
        add(treeItem, 0, "mouseReleased(string name)", "bool");
        add(treeItem, 0, "moveMouse(point p)", "void");
        add(treeItem, 0, "moveMouse(int x, int y)", "void");
        add(treeItem, 0, "browse(string url)", "void");
        add(treeItem, 0, "google(string term)", "void");
        add(treeItem, 0, "noise(double x, double y)", "double");
        add(treeItem, 0, "noise(double x, double y, double z)", "double");
        add(treeItem, 0, "noise(double x, double y, double z, double w)", "double");
        add(treeItem, 0, "path(point start, point goal, int width, int height, string isValidFuncName", "point[]");
        add(treeItem, 0, "circle(float x, float y, float radius)", "void");
        add(treeItem, 0, "circle(float x, float y, float radius, int segments)", "void");
        add(treeItem, 0, "ellipse(float x, float y, float width, float height)", "void");
        add(treeItem, 0, "ellipse(float x, float y, float width, float height, int segments)", "void");
        add(treeItem, 0, "line(point p1, point p2)", "void");
        add(treeItem, 0, "line(float x1, float y1, float x2, float y2)", "void");
        add(treeItem, 0, "polygon(float[] vertices)", "void");
        add(treeItem, 0, "shapeColor(color c)", "void");
        add(treeItem, 0, "shapeColor(float r, float g, float b)", "void");
        add(treeItem, 0, "shapeColor(float r, float g, float b, float a)", "void");


        return treeItem;
    }

    private static void add(DocumentationTreeItem parent, int level, String value){
        if(level == 0) {
            parent.getChildren().add(new DocumentationTreeItem(value));
            stringBuilder.append(" ");
            stringBuilder.append(value.split("\\(", 2)[0]);
        }
        else
            add((DocumentationTreeItem)parent.getChildren().get(parent.getChildren().size() - 1), level - 1, value);
    }

    private static void add(DocumentationTreeItem parent, int level, String value, String returnType){
        if(level == 0){
            parent.getChildren().add(new DocumentationTreeItem(value, returnType));
            stringBuilder.append(" ");
            stringBuilder.append(value.split("\\(", 2)[0]);
        }
        else
            add((DocumentationTreeItem)parent.getChildren().get(parent.getChildren().size() - 1), level - 1, value, returnType);
    }

    private static void add(DocumentationTreeItem parent, int level, String value, boolean isReadOnly, String returnType){
        if(level == 0){
            parent.getChildren().add(new DocumentationTreeItem(value, returnType, isReadOnly));
            stringBuilder.append(" ");
            stringBuilder.append(value.split("\\(", 2)[0]);
        }
        else
            add((DocumentationTreeItem)parent.getChildren().get(parent.getChildren().size() - 1), level - 1, value, isReadOnly, returnType);
    }
}