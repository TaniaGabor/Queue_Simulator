package queque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class Server implements Runnable {
    private BlockingQueue<Task> clients;
    private int waitingPeriod;
    private boolean ok=false;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    @Override
    public String toString() {
        return "Server{" +
                "clients=" + clients +
                '}';
    }

    public BlockingQueue<Task> getClients() {
        return clients;
    }

    public void setClients(BlockingQueue<Task> clients) {
        this.clients = clients;
    }

    public void setWaitingPeriod(int waitingPeriod) {
        this.waitingPeriod = waitingPeriod;
    }

    public Server(){
        this.clients=new LinkedBlockingQueue<queque.Task>();
        this.waitingPeriod=0;
    }

    public void addTask(Task newtask)
    {

        clients.add(newtask);

    }

    public void run() {

        while (!isOk()) {
            Task processingTask = clients.peek(); //iau client
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (processingTask != null) {
                try {
                    processingTask.settProcess(processingTask.gettProcess()-1);

                    if(processingTask.gettProcess()==0)
                    {

                        clients.take();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }


    }

    public int getWaitingPeriod() {
        return waitingPeriod;
    }
}


