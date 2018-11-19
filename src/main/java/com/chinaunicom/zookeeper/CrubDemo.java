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
 * @date 2018��11��16��
 */
public class CrubDemo {

	private String servers = "192.168.81.3:2181";

	private int sessionTimeout = 2000;

	private ZooKeeper zk = null;

	public void connectionZk() throws IOException {
		
		 zk = new ZooKeeper(servers, sessionTimeout, new Watcher() {

			public void process(WatchedEvent event) {
				System.out.println("�յ��¼�start");
				System.out.println(event);
				System.out.println("�յ��¼�end");
			}});
		 System.out.println("���ӽ���");
		 System.out.println(zk.getState());
	}
	public void create() throws KeeperException, InterruptedException {
		
		System.out.println("/n1. ���� ZooKeeper �ڵ� (znode �� java, ���ݣ� root ��Ȩ�ޣ� OPEN_ACL_UNSAFE ���ڵ����ͣ� Persistent");
		zk.create("/java", "root".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
	}
	//��ȡ����
	public void get() throws KeeperException, InterruptedException {
		
		System.out.println("/n2. ��ȡ ZooKeeper �ڵ� (znode �� java, ���ݣ� root ��Ȩ�ޣ� OPEN_ACL_UNSAFE ���ڵ����ͣ� Persistent");
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
