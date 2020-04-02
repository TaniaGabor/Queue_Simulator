package queque;

import java.util.List;

public class Scheduler {
    private List<Server> servers;
    private int maxNoServers;
    public boolean ok=false;

    public Scheduler(int maxNoServers,List<Server> servers)
    {
        this.maxNoServers=maxNoServers;
        this.servers=servers;


    }
    public void getMinWaitingTimeandAdd(Task client,List<Server> servers )
    {

        int min =servers.get(0).getWaitingPeriod();
        int p=0;
        for(int i=0;i<servers.size();i++)
        {
            if(servers.get(i).getWaitingPeriod()<min)
            {
                min= servers.get(i).getWaitingPeriod();
                p=i;
            }
        }
        servers.get(p).setWaitingPeriod(servers.get(p).getWaitingPeriod()+client.gettProcess());
        servers.get(p).addTask(client);




    }
    public List<Server> getServers() {
        return servers;
    }

    public void setServers(List<Server> servers) {
        this.servers = servers;
    }

    public int getMaxNoServers() {
        return maxNoServers;
    }

    public void setMaxNoServers(int maxNoServers) {
        this.maxNoServers = maxNoServers;
    }
}
