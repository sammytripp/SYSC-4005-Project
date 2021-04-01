import java.util.Random;

public class Inspector implements Runnable{
    private int id;
    private Workstation work1;
    private Workstation work2;
    private Workstation work3;
    private Random rand;
    private Random randomComponentgenerator;
    private double lambda1;
    private double lambda2;
    private double lambda3;
    private long blockedTime = 0;
    private int currentWorkstation = 1;

    public Inspector(){
    }

    public Inspector(int id, long seed, double lambda1, double lambda2, double lambda3, Workstation work1, Workstation work2, Workstation work3){
        this.id = id;
        this.work1 = work1;
        this.work2 = work2;
        this.work3 = work3;
        this.lambda1 = lambda1;
        this.lambda2 = lambda2;
        this.lambda3 = lambda3;
        rand = new Random(seed);
        randomComponentgenerator = new Random();
    }

    public Component generateComponent(){
        if (id == 2) {
            if(randomComponentgenerator.nextBoolean()) {
                return new Component(Component.Type.TWO);
            }else {
                return new Component(Component.Type.THREE);
            }
        } else {
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
        if (id == 1) {
            switch (currentWorkstation) {
                case 1:
                    return work1.add(comp);
                case 2:
                    return work2.add(comp);
                case 3:
                    return work3.add(comp);
            }
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
        while (true) {
//            System.out.println("inspector : " + id);

            Component currentComp = generateComponent();

            double serviceTime = calculateNextServiceTime(currentComp);

            try {
                Thread.sleep(1000);
                Thread.sleep((long) serviceTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("inspector : " + id + " || service time : " + serviceTime + " || comp : " + currentComp.getType());

            long startingsystemTime = System.currentTimeMillis();
            while (!addToWorkstation(currentComp)){
                // temporary to avoid flooding output
                try {
                    Thread.sleep(1000);
                    System.out.println("inspector : " + id + " blocked");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                blockedTime
            }
            blockedTime += System.currentTimeMillis() - startingsystemTime;
            currentWorkstation = currentWorkstation % 3 + 1;


//            System.out.println("inspector : " + id + " workstation : " + currentWorkstation);
        }
    }
}
