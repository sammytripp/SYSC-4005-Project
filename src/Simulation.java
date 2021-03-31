public class Simulation {

    public static void main(String[]  args){
        System.out.println("Running simulation");
        Workstation work1 = new Workstation(1);
        Thread threadWork1 = new Thread(work1, "Work1");

        Workstation work2 = new Workstation(2);
        Thread threadWork2 = new Thread(work2, "Work2");

        Workstation work3 = new Workstation(3);
        Thread threadWork3 = new Thread(work3, "Work3");


        Thread insp1 = new Thread(new Inspector(1, work1, work2, work3), "Insp1");
        Thread insp2 = new Thread(new Inspector(2, work1, work2, work3), "Insp2");

        threadWork1.start();
        threadWork2.start();
        threadWork3.start();

        insp1.start();
        insp2.start();
    }
}
