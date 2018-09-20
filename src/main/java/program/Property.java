package program;

public class Property {
    public String type;
    public String name;
    public Instruction value;
    public Function.Access access;

    public Property(String name){
        this.type = "";
        this.name = name;
        this.value = null;
        this.access = Function.Access.Public;
    }
}
