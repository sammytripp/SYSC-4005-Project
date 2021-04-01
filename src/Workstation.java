import java.util.ArrayList;

public class Workstation implements Runnable{

    private int id;
    private ArrayList<Component> C1Buffer;
    private ArrayList<Component> C2Buffer;
    private ArrayList<Component> C3Buffer;

    private Workstation(){

    }

    public Workstation(int id){

        this.id = id;
        C1Buffer = new ArrayList<Component>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Product.Type buildProduct(){
        Product p = new Product();
        if(C1Buffer.size() > 0){
            C1Buffer.remove(1);
            return Product.Type.ONE;
        }
        if(C1Buffer.size() > 0 && C2Buffer.size() > 0){
            C1Buffer.remove(1);
            C2Buffer.remove(1);
            return Product.Type.TWO;
        }
        if(C1Buffer.size() > 0 && C3Buffer.size() > 0){
            C1Buffer.remove(1);
            C3Buffer.remove(1);
            return Product.Type.THREE;

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
    }
}
