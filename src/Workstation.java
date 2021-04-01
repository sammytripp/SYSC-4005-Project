import java.util.ArrayList;

public class Workstation implements Runnable{

    private int id;
    private ArrayList<Component> C1Buffer;
    private ArrayList<Component> C2Buffer;
    private ArrayList<Component> C3Buffer;

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
