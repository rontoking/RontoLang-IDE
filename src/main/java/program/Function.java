package program;

import java.util.ArrayList;

public class Function {
    public enum Access{
        Public, Private
    }

    public String name;
    public String type;
    public Access access;
    public ArrayList<Parameter> parameters;
    public ArrayList<Instruction> code;

    public Function(String name){
        this.name = name;
        this.type = "";
        this.access = Access.Public;
        this.parameters = new ArrayList<Parameter>();
        this.code = new ArrayList<Instruction>();
    }
}
