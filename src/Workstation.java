import java.util.ArrayList;

public class Workstation implements Runnable{

    enum Type{
        ONE, TWO, THREE
    };

    private static final int BUFFER_SIZE = 2;

    private int id;
    private ArrayList<Component> C1Buffer;
    private ArrayList<Component> C2Buffer;
    private ArrayList<Component> C3Buffer;
    protected double lambda;
    private Type type;


    public Workstation(int id, double lambda) {
        this.id = id;
        this.lambda = lambda;
        type = Type.ONE;
        C1Buffer = new ArrayList<Component>();
    }

    public Workstation(int id, double lambda, boolean c2) {
        this.id = id;
        this.lambda = lambda;
        C1Buffer = new ArrayList<Component>();
        if (c2) {
            C2Buffer = new ArrayList<Component>();
            type = Type.TWO;
        } else {
            C3Buffer = new ArrayList<Component>();
            type = Type.THREE;
        }
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public Type getType() { return type; }

    public void setType(Type type) { this.type = type; }

    public ArrayList<Component> getC1Buffer() {
        return C1Buffer;
    }

    public void setC1Buffer(ArrayList<Component> C1Buffer) {
        this.C1Buffer = C1Buffer;
    }

    public ArrayList<Component> getC2Buffer() {
        return C2Buffer;
    }

    public void setC2Buffer(ArrayList<Component> c2Buffer) {
        C2Buffer = c2Buffer;
    }

    public ArrayList<Component> getC3Buffer() {
        return C3Buffer;
    }

    public void setC3Buffer(ArrayList<Component> c3Buffer) {
        C3Buffer = c3Buffer;
    }

    public Product buildProduct(){
        switch(type) {
            case ONE:
                if(C1Buffer.size() > 0){
                    C1Buffer.remove(1);
                    System.out.println("Built product 1");
                    return new Product(Product.Type.ONE);
                }
                break;
            case TWO:
                if(C1Buffer.size() > 0 && C2Buffer.size() > 0){
                    C1Buffer.remove(1);
                    C2Buffer.remove(1);
                    System.out.println("Built product 2");
                    return new Product(Product.Type.TWO);
                }
                break;
            case THREE:
                if(C1Buffer.size() > 0 && C3Buffer.size() > 0){
                    C1Buffer.remove(1);
                    C3Buffer.remove(1);
                    System.out.println("Built product 3");
                    return new Product(Product.Type.THREE);
                }
                break;
        }
        return null;
    }

    /**
     * Adds component to the queue. Is thread safe, no protection needed
     * @param comp
     * @return True if added successfully
     */
    public synchronized boolean add(Component comp){
        return false;
    }

    @Override
    public void run() {

        System.out.println("workstation : " + id);

        /*
        try {
            Thread.sleep((long) serviceTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
    }
}
