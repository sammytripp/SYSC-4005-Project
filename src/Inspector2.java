import java.util.Random;

public class Inspector2 extends Inspector{

    private double lambda3;

    public Inspector2(int id, long seed, double lambda2, double lambda3, Workstation work1, Workstation work2, Workstation work3){
        super(id, seed, lambda2, work1, work2, work3);
        this.lambda3 = lambda3;
    }


    public double calculateNextServiceTime(Component comp){
        double temp = (comp.getType() == Component.Type.TWO) ? lambda : lambda3;
        double r = rand.nextDouble();
        return - (1/temp) * Math.log(1-r); // inverse CDF
    }


    public boolean addToWorkstation(Component comp){
        switch (comp.getType()) {
            case TWO:
                return work2.add(comp);
            case THREE:
                return work3.add(comp);
        }
        return false;
    }


    @Override
    public void run() {
        System.out.println("inspector : " + id);

        // generate random component
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



    }


}
