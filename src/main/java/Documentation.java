import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class Documentation {
    public static String AUTOCOMPLETE_TEXT;
    private static StringBuilder strBuilder;

    public static TreeView get(){
        strBuilder = new StringBuilder();
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
        AUTOCOMPLETE_TEXT = strBuilder.toString();
        return treeView;
    }

    private static DocumentationTreeItem valueTypes(){
        DocumentationTreeItem treeItem = new DocumentationTreeItem("Value Types");

        add(treeItem, 0, "any");

        add(treeItem, 1, "copy", true, "any");
        add(treeItem, 1, "swap(any variable)", "void");
        add(treeItem, 1, "type", true, "str");
        add(treeItem, 1, "str", true, "str");
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

        add(treeItem, 0, "str");

        add(treeItem, 1, "size", true, "int");
        add(treeItem, 1, "trim", true, "str");
        add(treeItem, 1, "getUpper", true, "str");
        add(treeItem, 1, "getLower", true, "str");
        add(treeItem, 1, "list", true, "char[]");

        add(treeItem, 1, "substr(int start)", "str");
        add(treeItem, 1, "substr(int start, int end)", "str");
        add(treeItem, 1, "charAt(int index)", "char");
        add(treeItem, 1, "replace(str target, str replacement)", "str");
        add(treeItem, 1, "split(str splitter)", "str[]");
        add(treeItem, 1, "split(str splitter, int limit)", "str[]");
        add(treeItem, 1, "indexOf(str s)", "int");
        add(treeItem, 1, "lastIndexOf(str s)", "int");
        add(treeItem, 1, "contains(str s)", "bool");
        add(treeItem, 1, "startsWith(str s)", "bool");
        add(treeItem, 1, "endsWith(str s)", "bool");

        // Complex variables

        add(treeItem, 0, "list");

        add(treeItem, 1, "size", true, "int");
        add(treeItem, 1, "last", false, "any");
        add(treeItem, 1, "shuffle", true, "void");
        add(treeItem, 1, "reverse", true, "void");
        add(treeItem, 1, "random", true, "any");
        add(treeItem, 1, "sort", true, "void");

        add(treeItem, 1, "add(any item)", "void");
        add(treeItem, 1, "remove(int index)", "void");
        add(treeItem, 1, "swap(int index1, int index2)", "void");
        add(treeItem, 1, "join(str joiner)", "str");
        add(treeItem, 1, "insert(int index, any item)", "void");
        add(treeItem, 1, "startsWith(list start)", "bool");
        add(treeItem, 1, "endsWith(list end)", "bool");
        add(treeItem, 1, "sort(bool direction)", "void");
        add(treeItem, 1, "sort(x, y, bool condition)", "void");
        add(treeItem, 1, "any(x, bool condition)", "bool");
        add(treeItem, 1, "any(any value)", "bool");
        add(treeItem, 1, "count(x, bool condition)", "int");
        add(treeItem, 1, "count(any value)", "int");
        add(treeItem, 1, "first(x, bool condition)", "any");
        add(treeItem, 1, "first(any value)", "any");
        add(treeItem, 1, "last(x, bool condition)", "any");
        add(treeItem, 1, "last(any value)", "any");
        add(treeItem, 1, "all(x, bool condition)", "list");
        add(treeItem, 1, "all(any value)", "list");
        add(treeItem, 1, "removeFirst(x, bool condition)", "bool");
        add(treeItem, 1, "removeFirst(any value)", "bool");
        add(treeItem, 1, "removeLast(x, bool condition)", "bool");
        add(treeItem, 1, "removeLast(any value)", "bool");
        add(treeItem, 1, "removeAll(x, bool condition)", "int");
        add(treeItem, 1, "removeAll(any value)", "int");
        add(treeItem, 1, "firstIndexOf(x, bool condition)", "int");
        add(treeItem, 1, "firstIndexOf(any value)", "int");
        add(treeItem, 1, "lastIndexOf(x, bool condition)", "int");
        add(treeItem, 1, "lastIndexOf(any value)", "int");
        add(treeItem, 1, "sub(int start)", "list");
        add(treeItem, 1, "sub(any value)", "list");

        add(treeItem, 0, "map");

        add(treeItem, 1, "size", true, "int");
        add(treeItem, 1, "keys", true, "list");
        add(treeItem, 1, "values", true, "list");

        add(treeItem, 0, "file");

        add(treeItem, 1, "exists", true, "bool");
        add(treeItem, 1, "parent", true, "file");
        add(treeItem, 1, "path", true, "str");
        add(treeItem, 1, "name", true, "str");
        add(treeItem, 1, "extension", true, "str");
        add(treeItem, 1, "bytes", true, "byte[]");
        add(treeItem, 1, "read", true, "str");
        add(treeItem, 1, "isFolder", true, "bool");
        add(treeItem, 1, "size", true, "int");
        add(treeItem, 1, "lastModified", true, "int");
        add(treeItem, 1, "type", true, "str");
        add(treeItem, 1, "children", true, "file[]");
        add(treeItem, 1, "makeFolders", true, "void");
        add(treeItem, 1, "delete", true, "void");
        add(treeItem, 1, "empty", true, "void");
        add(treeItem, 1, "emptyFiles", true, "void");

        add(treeItem, 1, "write(str s)", "void");
        add(treeItem, 1, "append(str s)", "void");
        add(treeItem, 1, "writeBytes(byte[] b)", "void");
        add(treeItem, 1, "appendBytes(byte[] b)", "void");
        add(treeItem, 1, "rename(str s)", "void");
        add(treeItem, 1, "move(file f)", "void");
        add(treeItem, 1, "child(str name)", "file");
        add(treeItem, 1, "sibling(str name)", "file");

        add(treeItem, 0, "img");

        add(treeItem, 1, "width", true, "int");
        add(treeItem, 1, "height", true, "int");

        add(treeItem, 1, "draw(double x, double y)", "void");

        add(treeItem, 0, "font");

        add(treeItem, 1, "width(str text)", "float");
        add(treeItem, 1, "height(str text)", "float");

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

        add(treeItem, 0, "cam2");

        add(treeItem, 1, "x", false, "double");
        add(treeItem, 1, "y", false, "double");
        add(treeItem, 1, "zoom", false, "double");

        add(treeItem, 1, "mount", true, "void");

        add(treeItem, 1, "rotate(float degrees)", "void");

        add(treeItem, 0, "expr");

        add(treeItem, 1, "run", true, "any");

        add(treeItem, 0, "func");

        add(treeItem, 1, "run(any arguments)", "any");

        return treeItem;
    }

    private static DocumentationTreeItem globalVariables(){
        DocumentationTreeItem treeItem = new DocumentationTreeItem("Global Variables");

        add(treeItem, 0, "null", true, "null");
        add(treeItem, 0, "exit", true, "void");
        add(treeItem, 0, "break", true, "void");
        add(treeItem, 0, "construct", true, "void");
        add(treeItem, 0, "this", true, "any");
        add(treeItem, 0, "super", true, "any");
        add(treeItem, 0, "scanBool", true, "bool");
        add(treeItem, 0, "scanByte", true, "byte");
        add(treeItem, 0, "scanInt", true, "int");
        add(treeItem, 0, "scanFloat", true, "float");
        add(treeItem, 0, "scanDouble", true, "double");
        add(treeItem, 0, "scanStr", true, "str");
        add(treeItem, 0, "fps", true, "int");
        add(treeItem, 0, "pressedKeys", true, "str[]");
        add(treeItem, 0, "mouseX", true, "int");
        add(treeItem, 0, "mouseY", true, "int");
        add(treeItem, 0, "mousePos", true, "point");
        add(treeItem, 0, "mouseDeltaX", true, "int");
        add(treeItem, 0, "mouseDeltaY", true, "int");
        add(treeItem, 0, "mouseDeltaPos", true, "point");
        add(treeItem, 0, "mouseScroll", true, "int");
        add(treeItem, 0, "clipboard", false, "str");
        add(treeItem, 0, "defaultFont", true, "font");
        add(treeItem, 0, "server", true, "server");

        add(treeItem, 1, "clients", true, "conn[]");

        add(treeItem, 1, "host(int port)", "void");
        add(treeItem, 1, "host(int port, int writeBufferSize, int objectBufferSize)", "void");
        add(treeItem, 1, "send(any value)", "void");
        add(treeItem, 1, "send(conn client, any value)", "void");

        add(treeItem, 0, "client", true, "client");

        add(treeItem, 1, "join(str ip, int port)", "bool");
        add(treeItem, 1, "join(str ip, int port, int writeBufferSize, int objectBufferSize)", "bool");
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

        add(treeItem, 1, "open(str name)", "void");
        add(treeItem, 1, "put(str key, str value)", "void");
        add(treeItem, 1, "get(str key)", "void");

        add(treeItem, 0, "console", true, "void");

        add(treeItem, 1, "visible", false, "bool");
        add(treeItem, 1, "entered", false, "bool");
        add(treeItem, 1, "input", false, "str");
        add(treeItem, 1, "output", false, "str");

        add(treeItem, 0, "window", true, "void");

        add(treeItem, 1, "width", false, "int");
        add(treeItem, 1, "height", false, "int");
        add(treeItem, 1, "title", false, "str");
        add(treeItem, 1, "fullscreen", false, "bool");
        add(treeItem, 1, "resizable", false, "bool");
        add(treeItem, 1, "vsync", false, "bool");
        add(treeItem, 1, "decorated", false, "bool");
        add(treeItem, 1, "cam", false, "cam2");

        add(treeItem, 0, "website", true, "void");

        add(treeItem, 1, "stop", true, "void");
        add(treeItem, 1, "port", true, "int");

        return treeItem;
    }

    private static DocumentationTreeItem instructions(){
        DocumentationTreeItem treeItem = new DocumentationTreeItem("Instructions");

        add(treeItem, 0, "if(bool condition){BLOCK}","void");
        add(treeItem, 0, "else if(bool condition){BLOCK}", "void");
        add(treeItem, 0, "else{BLOCK}", "void");
        add(treeItem, 0, "func(PARAMETERS){BLOCK}","func");
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
        add(treeItem, 0, "waitUntil(bool condition)", "void");
        add(treeItem, 0, "waitUntil(bool condition, int intervalBetweenChecksInMillis)", "void");
        add(treeItem, 0, "eval{str expression}", "void");
        add(treeItem, 0, "parse{str expression}", "expr");
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
        add(treeItem, 0, "snap(double num, double cellSize)", "double");
        add(treeItem, 0, "digit(double num, int index)", "int");
        add(treeItem, 0, "prime(int num)", "bool");
        add(treeItem, 0, "img(str name)", "img");
        add(treeItem, 0, "img(file f)", "img");
        add(treeItem, 0, "sound(str name)", "sound");
        add(treeItem, 0, "sound(file f)", "sound");
        add(treeItem, 0, "music(str name)", "music");
        add(treeItem, 0, "music(file f)", "music");
        add(treeItem, 0, "font(int size)", "font");
        add(treeItem, 0, "font(str name, int size)", "font");
        add(treeItem, 0, "font(file f, int size)", "font");
        add(treeItem, 0, "str(any value)", "str");
        add(treeItem, 0, "int(str s)", "int");
        add(treeItem, 0, "float(str s)", "float");
        add(treeItem, 0, "double(str s)", "double");
        add(treeItem, 0, "internal(str path)", "file");
        add(treeItem, 0, "local(str path)", "file");
        add(treeItem, 0, "external(str path)", "file");
        add(treeItem, 0, "absolute(str path)", "file");
        add(treeItem, 0, "classpath(str path)", "file");
        add(treeItem, 0, "rect(double x, double y, double width, double height)", "rect");
        add(treeItem, 0, "sprite(img x)", "sprite");
        add(treeItem, 0, "sprite(file f)", "sprite");
        add(treeItem, 0, "sprite(str name)", "sprite");
        add(treeItem, 0, "cam2()", "cam2");
        add(treeItem, 0, "point(double x, double y)", "point");
        add(treeItem, 0, "color(str name)", "color");
        add(treeItem, 0, "color(str name, float a)", "color");
        add(treeItem, 0, "color(float r, float g, float b)", "color");
        add(treeItem, 0, "color(float r, float g, float b, float a)", "color");
        add(treeItem, 0, "expr(any expression)", "expr");
        add(treeItem, 0, "draw(str text)", "void");
        add(treeItem, 0, "draw(str text, int x, int y)", "void");
        add(treeItem, 0, "draw(font f, str text, int x, int y)", "void");
        add(treeItem, 0, "draw(str text, int x, int y, color c)", "void");
        add(treeItem, 0, "draw(font f, str text, int x, int y, color c)", "void");
        add(treeItem, 0, "draw(sprite s)", "void");
        add(treeItem, 0, "draw(sprite[] s)", "void");
        add(treeItem, 0, "fill(color c)", "void");
        add(treeItem, 0, "fill(float r, float g, float b)", "void");
        add(treeItem, 0, "fill(float r, float g, float b, float a)", "void");
        add(treeItem, 0, "dst(point p1, point p2)", "float");
        add(treeItem, 0, "dst(float x1, float y1, float x2, float y2)", "float");
        add(treeItem, 0, "serialize(any value)", "byte[]");
        add(treeItem, 0, "deserialize(byte[] data)", "any value");
        add(treeItem, 0, "keyDown(str name)", "bool");
        add(treeItem, 0, "keyPressed(str name)", "bool");
        add(treeItem, 0, "mouseDown(str name)", "bool");
        add(treeItem, 0, "mouseClicked(str name)", "bool");
        add(treeItem, 0, "mouseDouble(str name)", "bool");
        add(treeItem, 0, "mouseReleased(str name)", "bool");
        add(treeItem, 0, "moveMouse(point p)", "void");
        add(treeItem, 0, "moveMouse(int x, int y)", "void");
        add(treeItem, 0, "browse(str url)", "void");
        add(treeItem, 0, "html(str url)", "str");
        add(treeItem, 0, "google(str term)", "void");
        add(treeItem, 0, "website(func serve(str url, str(str) params))", "void");
        add(treeItem, 0, "website(func serve(str url, str(str) params), file root)", "void");
        add(treeItem, 0, "website(int port, func serve(str url, str(str) params))", "void");
        add(treeItem, 0, "website(int port, func serve(str url, str(str) params), file root)", "void");
        add(treeItem, 0, "noise(double x, double y)", "double");
        add(treeItem, 0, "noise(double x, double y, double z)", "double");
        add(treeItem, 0, "noise(double x, double y, double z, double w)", "double");
        add(treeItem, 0, "path(point start, point goal, int width, int height, func isValid(int x, int y)", "point[]");
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
            strBuilder.append(" ");
            strBuilder.append(value.split("\\(", 2)[0]);
        }
        else
            add((DocumentationTreeItem)parent.getChildren().get(parent.getChildren().size() - 1), level - 1, value);
    }

    private static void add(DocumentationTreeItem parent, int level, String value, String returnType){
        if(level == 0){
            parent.getChildren().add(new DocumentationTreeItem(value, returnType));
            strBuilder.append(" ");
            strBuilder.append(value.split("\\(", 2)[0]);
        }
        else
            add((DocumentationTreeItem)parent.getChildren().get(parent.getChildren().size() - 1), level - 1, value, returnType);
    }

    private static void add(DocumentationTreeItem parent, int level, String value, boolean isReadOnly, String returnType){
        if(level == 0){
            parent.getChildren().add(new DocumentationTreeItem(value, returnType, isReadOnly));
            strBuilder.append(" ");
            strBuilder.append(value.split("\\(", 2)[0]);
        }
        else
            add((DocumentationTreeItem)parent.getChildren().get(parent.getChildren().size() - 1), level - 1, value, isReadOnly, returnType);
    }
}
