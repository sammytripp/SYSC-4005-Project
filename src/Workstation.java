public class Workstation implements Runnable{
    private int id;

    public Workstation(int id){
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("workstation : " + id);
    }
}
