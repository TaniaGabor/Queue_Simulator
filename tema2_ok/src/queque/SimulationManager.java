package queque;


import java.io.*;
import java.util.*;

public class SimulationManager implements Runnable {
    private int timeLimit;
    private int maxProcessingTime;
    private int minProcessingTime;
    private int numberofServices;
    private int numberofClients;
    private int minArrivalTimeInterval;
    private int maxArrivalTimeInterval;
    private Scheduler scheduler;
    public List<Server> servers1;


    public SimulationManager() {
        int n = 0;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("In_Test3.txt"));
            String line = reader.readLine();
            while (line != null) {
                if (n == 0) {
                    this.setNumberofClients(Integer.valueOf(line));
                } else if (n == 1) {
                    this.setNumberofServices(Integer.valueOf(line));
                } else if (n == 2) {
                    this.setTimeLimit(Integer.valueOf(line));
                } else if (n == 3) {
                    String[] str = line.split(",");
                    this.setMinArrivalTimeInterval(Integer.valueOf(str[0]));
                    this.setMaxArrivalTimeInterval(Integer.valueOf(str[1]));
                } else if (n == 4) {
                    String[] str = line.split(",");
                    this.setMinProcessingTime(Integer.valueOf(str[0]));
                    this.setMaxProcessingTime(Integer.valueOf(str[1]));
                }
                n++;
                line = reader.readLine();

            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        servers1 = new ArrayList<>(this.getNumberofServices());
        for (int i = 0; i < this.getNumberofServices(); i++) {
            Server s = new Server();
            servers1.add(s);
            Thread t = new Thread(s);
            t.start();

        }
        this.scheduler = new Scheduler(this.getNumberofServices(), servers1);

    }

    public ArrayList<Task> generateNRandomTasks(int numberofClients) {
        ArrayList<Task> tasks = new ArrayList<queque.Task>(numberofClients);
        Random rand = new Random();
        for (int i = 0; i < numberofClients; i++) {
            int randtArrival = rand.nextInt(this.maxArrivalTimeInterval - this.minArrivalTimeInterval) + this.minArrivalTimeInterval;
            int randtProcess = rand.nextInt(this.maxProcessingTime - this.minProcessingTime) + this.minProcessingTime;
            tasks.add(new Task(i, randtArrival ,randtProcess));
        }
        Collections.sort(tasks);
        return (ArrayList<Task>) tasks;

    }

    public static void main(String[] args) {
        SimulationManager a = new SimulationManager();
        System.out.println(a.toString());
        Thread t = new Thread(a);
        t.start();

    }
    public void run() {
        int currentTime = 0;
        int time = 0;
        int clientsProcessed = 0;
        FileWriter fileWriter = null;try {
            fileWriter = new FileWriter("out.txt");
        } catch (IOException e) {
            e.printStackTrace(); }
        PrintWriter printWriter = new PrintWriter(fileWriter);
        List<Task> listaok = this.generateNRandomTasks(this.getNumberofClients());
        while (currentTime < this.getTimeLimit()) {
            List<Task> sters = new ArrayList<Task>(this.getNumberofClients());
            printWriter.println("Time  " + currentTime);
            printWriter.println(" Waiting clients: " + listaok.toString());
            for (int i = 0; i < this.scheduler.getServers().size(); i++)
                if (this.scheduler.getServers().get(i).getClients().isEmpty()) {
                    printWriter.println("Queue " + i + "closed"); } else {
                    printWriter.println("Queue  " + i + " " + this.scheduler.getServers().get(i).getClients().toString()); }
            for (Task t : listaok) {
                if (t.gettArrival() == currentTime + 1) {
                    this.scheduler.getMinWaitingTimeandAdd(t, this.scheduler.getServers());
                    clientsProcessed++;sters.add(t); }
            }listaok.removeAll(sters);currentTime++;
            try { Thread.sleep(1000);
            } catch (InterruptedException e) { e.printStackTrace(); } }
        for (int j = 0; j < this.scheduler.getServers().size(); j++) {
            if (!this.scheduler.getServers().get(j).getClients().isEmpty()) {
                for (Task p : this.scheduler.getServers().get(j).getClients()) {
                    clientsProcessed--;
                    this.scheduler.getServers().get(j).setWaitingPeriod(this.scheduler.getServers().get(j).getWaitingPeriod() - p.gettProcess()); } } }
        for (int j = 0; j < this.scheduler.getServers().size(); j++)
            time = time + this.scheduler.getServers().get(j).getWaitingPeriod();
        double averageTime = time / clientsProcessed;
        printWriter.println("Clients"+clientsProcessed);
        printWriter.println("Average time  " + averageTime);
        for (Server p : scheduler.getServers()) p.setOk(true); printWriter.close();
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }


    public void setMaxProcessingTime(int maxProcessingTime) {
        this.maxProcessingTime = maxProcessingTime;
    }


    public void setMinProcessingTime(int minProcessingTime) {
        this.minProcessingTime = minProcessingTime;
    }

    public int getNumberofServices() {
        return numberofServices;
    }

    public void setNumberofServices(int numberofServices) {
        this.numberofServices = numberofServices;
    }

    public int getNumberofClients() {
        return numberofClients;
    }

    public void setNumberofClients(int numberofClients) {
        this.numberofClients = numberofClients;
    }


    public void setMinArrivalTimeInterval(int minArrivalTimeInterval) {
        this.minArrivalTimeInterval = minArrivalTimeInterval;
    }


    public void setMaxArrivalTimeInterval(int maxArrivalTimeInterval) {
        this.maxArrivalTimeInterval = maxArrivalTimeInterval;
    }


    @Override
    public String toString() {
        return "SimulationManager{" +
                "timeLimit=" + timeLimit +
                ", maxProcessingTime=" + maxProcessingTime +
                ", minProcessingTime=" + minProcessingTime +
                ", numberofServices=" + numberofServices +
                ", numberofClients=" + numberofClients +
                ", minArrivalTimeInterval=" + minArrivalTimeInterval +
                ", maxArrivalTimeInterval=" + maxArrivalTimeInterval +
                '}';
    }
}





