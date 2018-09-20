package program;

import java.util.ArrayList;

public class Program {
    public ArrayList<Class> classes;

    public Program() {
        classes = new ArrayList<Class>();
    }

    public Class getClass(String name){
        for(int i = 0; i < classes.size(); i++)
            if(classes.get(i).name.equals(name))
                return classes.get(i);
        return null;
    }
}
