package queque;

public class Task implements Comparable<Task> {
    private int ID;
    private int tArrival;
    private int tProcess;



    public Task(int ID, int tArrival,int tProcess)
    {
        this.ID=ID;
        this.tArrival=tArrival;
        this.tProcess=tProcess;

    }

    public int gettArrival() {
        return tArrival;
    }
    public int gettProcess() {
        return tProcess;
    }
    public void settProcess(int tProcess) {
        this.tProcess = tProcess;
    }


    public int compareTo(Task o) {
        if(this.tArrival < o.tArrival) return -1;
        if(this.tArrival > o.tArrival) return 1;
        return 0;
    }

    @Override
    public String toString() {
        return "("+ID+" "+tArrival+" "+tProcess+")";
    }
}
