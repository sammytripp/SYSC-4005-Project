public class Inspector implements Runnable{
    private int id;
    private Workstation work1;
    private Workstation work2;
    private Workstation work3;


    public Inspector(int id, Workstation work1, Workstation work2, Workstation work3){
        this.id = id;
        this.work1 = work1;
        this.work2 = work2;
        this.work3 = work3;
    }

    @Override
    public void run() {
        System.out.println("inspector : " + id);
    }
}
