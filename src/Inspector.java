import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.io.FileWriter;

public class Inspector implements Runnable{
    private int id;
    private Workstation work1;
    private Workstation work2;
    private Workstation work3;
    private Random rand;
    private Random randomComponentGenerator;
    private double lambda1;
    private double lambda2;
    private double lambda3;
    private long blockedTime = 0;

    public Inspector(int id, long seed, double lambda1, double lambda2, double lambda3, Workstation work1, Workstation work2, Workstation work3){
        this.id = id;
        this.work1 = work1;
        this.work2 = work2;
        this.work3 = work3;
        this.lambda1 = lambda1;
        this.lambda2 = lambda2;
        this.lambda3 = lambda3;
        rand = new Random(seed);
        randomComponentGenerator = new Random();
    }

    public Component generateComponent(){
        if (id == 2) {
            // Inspector 2 generates components 2 and 3
            if(randomComponentGenerator.nextBoolean()) {
                return new Component(Component.Type.TWO);
            }else {
                return new Component(Component.Type.THREE);
            }
        } else {
            // Inspector 1 generates component 1
            return new Component(Component.Type.ONE);
        }
    }

    public double calculateNextServiceTime(Component comp){
        double r = rand.nextDouble();
        switch (comp.getType()) {
            case ONE:
                return - (1/lambda1) * Math.log(1-r); // inverse CDF
            case TWO:
                return - (1/lambda2) * Math.log(1-r); // inverse CDF
            case THREE:
                return - (1/lambda3) * Math.log(1-r); // inverse CDF
        }
        return 0;
    }

    public boolean addToWorkstation(Component comp){
        // Buffer with fewest components is highest priority
        int numComponents1 = work1.getC1Buffer().size();
        int numComponents2 = work2.getC2Buffer().size();
        int numComponents3 = work3.getC3Buffer().size();

        if (id == 1) {
            if (numComponents1 == 0) { return work1.add(comp); }
            if (numComponents2 == 0) { return work2.add(comp); }
            if (numComponents3 == 0) { return work3.add(comp); }
            if (numComponents1 == 1) { return work1.add(comp); }
            if (numComponents2 == 1) { return work2.add(comp); }
            if (numComponents3 == 1) { return work3.add(comp); }

        } else if (id == 2) {
            switch (comp.getType()) {
                case TWO:
                    return work2.add(comp);
                case THREE:
                    return work3.add(comp);
            }
        }
        return false;
    }

    @Override
    public void run() {
        ArrayList serviceTimes = new ArrayList<String>();
        long simStart = System.currentTimeMillis();

        while (System.currentTimeMillis() - simStart < 480) {
            Component currentComp = generateComponent();

            double serviceTime = calculateNextServiceTime(currentComp);
            serviceTimes.add(currentComp.getType() + ", " + serviceTime);
            try {
                Thread.sleep((long) serviceTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("inspector : " + id + " || service time : " + serviceTime + " || comp : " + currentComp.getType());

            long startingSystemTime = System.currentTimeMillis();
            while (!addToWorkstation(currentComp)) {

            }
            blockedTime = System.currentTimeMillis() - startingSystemTime;


            FileWriter writer = null;
            try {
                writer = new FileWriter("Inspector" + id + ".txt");
                writer.write("Service times of Inspector " + id + System.lineSeparator());
                for (Object dd : serviceTimes) {
                    writer.write(dd + System.lineSeparator());
                }
//                writer.write("Total blocked time : " + blockedTime + System.lineSeparator());
                writer.write("Total elapsed time : " + (System.currentTimeMillis() - simStart) + System.lineSeparator()) ;
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
