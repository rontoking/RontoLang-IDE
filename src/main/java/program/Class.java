package program;

import java.util.ArrayList;

public class Class {
    public String name;
    public String parentClass; // Via 'extends'.
    public ArrayList<Function> classFunctions, objectFunctions;
    public ArrayList<Property> classProperties, objectProperties;

    public Class(String name, String parent){
        this.name = name;
        parentClass = parent;
        classFunctions = new ArrayList<Function>();
        objectFunctions = new ArrayList<Function>();
        classProperties = new ArrayList<Property>();
        objectProperties = new ArrayList<Property>();
    }

}
