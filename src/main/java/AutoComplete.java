import java.util.ArrayList;

public class AutoComplete {
    private static int start, end;
    private static String code, selection;

    public static ArrayList<String> getEntries(String text){
        ArrayList<String> entries = new ArrayList<>();
        code = text;
        start = 0;
        end = 0;
        selection = "";
        while (true){
            String e = getEntry();
            if(e == null)
                return entries;
            else {
                entries.add(e);
            }
        }
    }

    private static String getEntry(){
        setup();
        if(end >= code.length())
            return null;
        if(!Character.isLetter(mark()) && mark() != '_')
            selection = "";
        else{
            while (end < code.length() && (Character.isLetter(mark()) || Character.isDigit(mark()) || mark() == '_'))
                end++;
            selection = code.substring(start, end);
        }
        return selection;
    }

    private static boolean isStringStartOrEnd(boolean insideString, char strChar){ // A start or end of a string.
        if(!insideString)
            return (mark() == '\'' || mark() == '"') && !isCharEscaped(end);
        else
            return mark() == strChar && (mark() == '\'' || mark() == '"') && !isCharEscaped(end);
    }

    private static boolean isCharEscaped(int index){
        if(index == 0 || code.charAt(index - 1) != '\\') // There isn't a backslash so it is escaped.
            return false;
        return !isCharEscaped(index - 1); // It is only escaped if the backslash before it is NOT escaped.
    }

    private static char mark(){
        return code.charAt(end);
    }

    private static void setup(){
        boolean insideString = false;
        char strChar = '"';
        while (end < code.length() && (insideString || (!Character.isLetter(mark()) && mark() != '_'))){
            if(isStringStartOrEnd(insideString, strChar)){
                insideString = !insideString;
                strChar = mark();
            }
            end++;
        }
        selection = "";
        start = end;
    }
}
