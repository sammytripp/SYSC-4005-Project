public class Product {
    enum Type{
        ONE, TWO, THREE
    };

    public Product.Type type;

    public Product(){

    }

    public Product(Product.Type type){
        this.type = type;
    }
}
