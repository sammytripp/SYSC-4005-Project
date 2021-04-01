import java.util.Random;

public class Inspector implements Runnable{
    protected int id;
    protected Workstation work1;
    protected Workstation work2;
    protected Workstation work3;
    protected Random rand;
    protected double lambda;
    protected double blockedTime = 0;
    private int currentWorkstation = 1;

    public Inspector(){
    }

    public Inspector(int id, long seed, double lambda, Workstation work1, Workstation work2, Workstation work3){
        this.id = id;
        this.work1 = work1;
        this.work2 = work2;
        this.work3 = work3;
        this.lambda = lambda;
        rand = new Random(seed);
    }

    public double calculateNextServiceTime(){
        double r = rand.nextDouble();
        return - (1/lambda) * Math.log(1-r); // inverse CDF
    }

    public boolean addToWorkstation(Component comp){
        switch (currentWorkstation) {
            case 1:
                return work1.add(comp);
            case 2:
                return work2.add(comp);
            case 3:
                return work3.add(comp);
        }
        return false;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("inspector : " + id);
            double serviceTime = calculateNextServiceTime();

            try {
                Thread.sleep((long) serviceTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("inspector : " + id + " service time : " + serviceTime);

            while (!addToWorkstation(new Component(Component.Type.ONE))){
                System.out.println("inspector : " + id + " blocked");
//                blockedTime
            }
            currentWorkstation = currentWorkstation % 3 + 1;


            System.out.println("inspector : " + id + " workstation : " + currentWorkstation);
        }
    }
}
