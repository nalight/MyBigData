package com.chinaunicom.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * 
 * @author zhangzc
 * 
 * @date 2018年11月16日
 */
public class CrubDemo {

	private String servers = "192.168.81.3:2181";

	private int sessionTimeout = 2000;

	private ZooKeeper zk = null;

	public void connectionZk() throws IOException {
		
		 zk = new ZooKeeper(servers, sessionTimeout, new Watcher() {

			public void process(WatchedEvent event) {
				System.out.println("收到事件start");
				System.out.println(event);
				System.out.println("收到事件end");
			}});
		 System.out.println("连接结束");
		 System.out.println(zk.getState());
	}
	public void create() throws KeeperException, InterruptedException {
		
		System.out.println("/n1. 创建 ZooKeeper 节点 (znode ： java, 数据： root ，权限： OPEN_ACL_UNSAFE ，节点类型： Persistent");
		zk.create("/java", "root".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
	}
	//获取数据
	public void get() throws KeeperException, InterruptedException {
		
		System.out.println("/n2. 获取 ZooKeeper 节点 (znode ： java, 数据： root ，权限： OPEN_ACL_UNSAFE ，节点类型： Persistent");
		byte[] b = zk.getData("/java", true, null);
		
		System.out.println(new String(b));
	}
	public void set() throws KeeperException, InterruptedException {
		
		Stat st = zk.setData("/java", "java".getBytes(), -1);
		
		System.out.println(st);
		
	}
	public void del	() throws InterruptedException, KeeperException {
		
		zk.delete("/java", -1);
	}
	public void close() throws InterruptedException {
		
		zk.close();
	}
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		
		CrubDemo crubDemo = new CrubDemo();
		
		crubDemo.connectionZk();
		
		crubDemo.create();
		//crubDemo.get();
		
		//crubDemo.set();
	}
}
