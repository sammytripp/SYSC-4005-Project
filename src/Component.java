public class Component {

    enum Type {
        ONE, TWO, THREE
    };

    private Type type;

    public Component(Type type){
        this.type = type;
    }

    public void setType(Type type) { this.type = type; }

    public Type getType() { return type; }
}
