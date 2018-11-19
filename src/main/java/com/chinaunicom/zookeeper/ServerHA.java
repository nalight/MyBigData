package com.chinaunicom.zookeeper;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooKeeper.States;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooDefs.Ids;

//服务启动在/servers下注册子节点/servera “ip”
public class ServerHA {

	private String server = "192.168.81.3:2181";

	private int sessionTimeout = 2000;

	private ZooKeeper zk = null;
	
	private volatile List<String> servers=null;
	
	public void conn() throws IOException, KeeperException, InterruptedException {
		
		 zk = new ZooKeeper(server, sessionTimeout, new Watcher() {

				public void process(WatchedEvent event) {
					System.out.println("收到事件start");
					System.out.println(event);
					
					System.out.println("收到事件end");
				}});
			 System.out.println("连接结束");
			 System.out.println(zk.getState());
			 
				 String path = zk.create("/servers/"+"server01", "192.168.81.3".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
			
				 System.out.println("create"+path);
				 
	}
	//模拟业务处理
		public void doBusiness() throws InterruptedException {
			
			Thread.sleep(Integer.MAX_VALUE);
		}
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		
		ServerHA server = new ServerHA();
		
		server.conn();
		
		server.doBusiness();
	}
}
