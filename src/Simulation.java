public class Simulation {

    public static void main(String[]  args){
        System.out.print("Running simulation");
        Workstation work1 = new Workstation(1);
        Workstation work2 = new Workstation(2);
        Workstation work3 = new Workstation(3);

        Inspector inspector1 = new Inspector(1);
        Inspector inspector2 = new Inspector(2);
    }
}
