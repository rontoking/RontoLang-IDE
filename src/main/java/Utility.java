import javafx.scene.control.TreeItem;

public class Utility {
    public static String repeat(String str, int num){
        String txt = "";
        for(int i = 0; i < num; i++)
            txt += str;
        return txt;
    }

    public static int getIndentNum(String str) {
        int num = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '\t')
                num++;
            else
                return num;
        }
        return num;
    }

    public static double limit(double num, double min, double max){
        if(num <= min)
            return min;
        if(num >= max)
            return max;
        return num;
    }

    public static void expand(TreeItem<?> item){
        if(item != null && !item.isLeaf()){
            item.setExpanded(true);
            for(TreeItem<?> child:item.getChildren()){
                expand(child);
            }
        }
    }
}
