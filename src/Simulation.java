public class Simulation {

    public static void main(String[]  args){
        System.out.println("Running simulation");

        // Initialize workstations
        double work_lambda1 = 0.217182777;
        double work_lambda2 = 0.090150136;
        double work_lambda3 = 0.113693;

        Workstation work1 = new Workstation(1, work_lambda1);
        Thread threadWork1 = new Thread(work1, "Workstation1");

        Workstation work2 = new Workstation(2, work_lambda2,true);
        Thread threadWork2 = new Thread(work2, "Workstation2");

        Workstation work3 = new Workstation(3, work_lambda3,false);
        Thread threadWork3 = new Thread(work3, "Workstation3");

        // Initialize the inspector values
        long seed1 = 1;
        long seed2 = 2;
        double insp_lambda1 = 0.096544573;
        double insp_lambda2 = 0.06436289;
        double insp_lambda3 = 0.0484667;

        Thread insp1 = new Thread(new Inspector(1, seed1, insp_lambda1, work1, work2, work3), "Inspector1");
        Thread insp2 = new Thread(new Inspector2(2, seed2, insp_lambda2, insp_lambda3, work1, work2, work3), "Inspector2");

        threadWork1.start();
        threadWork2.start();
        threadWork3.start();

        insp1.start();
        insp2.start();
    }
}
