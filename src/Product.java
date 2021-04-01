public class Product {
    enum Type{
        ONE, TWO, THREE
    };

    private Type type;

    public Product(){ }

    public Product(Type type){
        this.type = type;
    }

    public void setType(Type type) { this.type = type; }

    public Type getType() { return type; }
}
