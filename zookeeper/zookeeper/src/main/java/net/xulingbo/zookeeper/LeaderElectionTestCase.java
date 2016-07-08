package net.xulingbo.zookeeper;
import net.xulingbo.zookeeper.LeaderElection.LeaderElection;

public class LeaderElectionTestCase{
public static void main(String[] args) {
    	
    	for(int i=0;i<3;i++){
    		Thread t=new Thread(new Runnable() {				
				@Override
				public void run() {
					String connectString = "localhost:2181";				
					LeaderElection le = new LeaderElection(connectString, "/GroupMembers");
					try {
						le.findLeader();
					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					while(true);
				}
			},"T_"+i);
    		t.start();
    	}
        
    }
	
}