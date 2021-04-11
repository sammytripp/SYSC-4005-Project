import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.io.FileWriter;

public class Workstation implements Runnable {

    enum Type { ONE, TWO, THREE }

    private static final int BUFFER_SIZE = 2;

    private int id;
    private ArrayList<Component> C1Buffer;
    private ArrayList<Component> C2Buffer;
    private ArrayList<Component> C3Buffer;
    private Type type;
    private final Random rand;
    private double lambda;
    private long processingTime = 0;
    private int totalProducts = 0;



    public Workstation(int id, double lambda, long seed) {
        this.id = id;
        this.lambda = lambda;
        type = Type.ONE;
        C1Buffer = new ArrayList<>();
        rand = new Random();
    }

    public Workstation(int id, double lambda, long seed, boolean c2) {
        this.id = id;
        this.lambda = lambda;
        C1Buffer = new ArrayList<>();
        if (c2) {
            C2Buffer = new ArrayList<>();
            type = Type.TWO;
        } else {
            C3Buffer = new ArrayList<>();
            type = Type.THREE;
        }
        rand = new Random();
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

    public synchronized Product buildProduct(){
        switch(type) {
            case ONE:
                if(C1Buffer.size() > 0) {
                    C1Buffer.remove(0);
                    System.out.println("Built product 1 - workstation: " + id);
                    return new Product(Product.Type.ONE);
                }
                break;
            case TWO:
                if(C1Buffer.size() > 0 && C2Buffer.size() > 0) {
                    C1Buffer.remove(0);
                    C2Buffer.remove(0);
                    System.out.println("Built product 2 - workstation: " + id);
                    return new Product(Product.Type.TWO);
                }
                break;
            case THREE:
                if(C1Buffer.size() > 0 && C3Buffer.size() > 0) {
                    C1Buffer.remove(0);
                    C3Buffer.remove(0);
                    System.out.println("Built product 3 - workstation: " + id);
                    return new Product(Product.Type.THREE);
                }
                break;
        }
        return null;
    }

    public double wsServiceTime(){
        double r = rand.nextDouble();
        return - (1/lambda) * Math.log(1-r); // inverse CDF
    }

    /**
     * Adds component to the queue. Is thread safe, no protection needed
     * @param comp Component
     * @return True if added successfully
     */
    public synchronized boolean add(Component comp){
        if(comp.getType() == Component.Type.ONE && C1Buffer.size() < BUFFER_SIZE){
            C1Buffer.add(comp);
            return true;
        }
        if(comp.getType() == Component.Type.TWO && C2Buffer.size() < BUFFER_SIZE){
            C2Buffer.add(comp);
            return true;
        }
        if(comp.getType() == Component.Type.THREE && C3Buffer.size() < BUFFER_SIZE){
            C3Buffer.add(comp);
            return true;
        }
        return false;
    }


    @Override
    public void run() {
        long simStart = System.currentTimeMillis();
        ArrayList serviceTimes = new ArrayList<Double>();
        while (System.currentTimeMillis() - simStart < 480) {
//            System.out.println("workstation : " + id);
            Product currentProd = buildProduct();
            long startingSystemTime = System.currentTimeMillis();

            while (currentProd == null) {
                // Necessary components not present in buffer(s)
                currentProd = buildProduct();
            }
            // Product processing successful
            double serviceTime = wsServiceTime();
            serviceTimes.add(serviceTime);
            try {
                Thread.sleep((long) serviceTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            totalProducts += 1;
            processingTime += System.currentTimeMillis() - startingSystemTime;

            System.out.println("workstation : " + id + " || service time : " + serviceTime + " || prod : " + currentProd.getType());

            System.out.println("workstation : " + id + " is done, elapsed time : " + (System.currentTimeMillis() - simStart) + "ms");

            FileWriter writer = null;
            try {
                writer = new FileWriter("Workstation" + id + ".txt");
                writer.write("Service times of Workstation " + id + System.lineSeparator());
                for (Object dd : serviceTimes) {
                    writer.write(dd + System.lineSeparator());
                }
                writer.write("Total items produced : " + totalProducts + System.lineSeparator());
                writer.write("Total elapsed time : " + (System.currentTimeMillis() - simStart) + System.lineSeparator());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
