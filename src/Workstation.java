public class Workstation implements Runnable{
    private int id;

    public Workstation(int id){
        this.id = id;
    }


    /**
     * Adds component to the queue. Is thread safe, no protection needed
     * @param comp
     * @return True if added successfully
     */
    public synchronized boolean add(Component comp){
        return false;
    }

    @Override
    public void run() {
        System.out.println("workstation : " + id);
    }
}
