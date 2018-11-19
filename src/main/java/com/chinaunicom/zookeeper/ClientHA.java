package com.chinaunicom.zookeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooKeeper;
//�ͻ��˼���/serversĿ¼�·������䶯����ķ����б�
public class ClientHA {
	
	private String server = "192.168.81.3:2181";

	private int sessionTimeout = 2000;

	private ZooKeeper zk = null;
	
	private volatile List<String> servers=null;
	
	public void conn() throws IOException, KeeperException, InterruptedException {
		
		 zk = new ZooKeeper(server, sessionTimeout, new Watcher() {

				public void process(WatchedEvent event) {
					System.out.println("�յ��¼�start");
					System.out.println(event);
					
					if(event.getType()==EventType.NodeChildrenChanged&&event.getPath().equals("/servers")) {
						
						try {
							updServerList();
						
						} catch (KeeperException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					System.out.println("�յ��¼�end");
				}});
			 System.out.println("���ӽ���");
			 
			 updServerList();
			 System.out.println(zk.getState());
	}
	//���·����б�
	public void updServerList() throws KeeperException, InterruptedException {
		
		List<String> attr = new ArrayList<String>();
		
		List<String> serverList = zk.getChildren("/servers", true);
		
		for(String temp:serverList) {
			
			attr.add(new String(zk.getData("/servers/"+temp, false, null)));
		}
		servers = attr;
		
		System.out.println(servers);
				
				
		
	}
	//ģ��ҵ����
	public void doBusiness() throws InterruptedException {
		
		Thread.sleep(Integer.MAX_VALUE);
	}
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		
		ClientHA client = new ClientHA();
		
		client.conn();
		
		
		client.doBusiness();
	}
	

}
