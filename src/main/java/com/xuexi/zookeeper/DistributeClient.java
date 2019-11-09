package com.xuexi.zookeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class DistributeClient {

	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		
		DistributeClient client = new DistributeClient();
		
		// 1 连接zookeeper集群
		client.getConnect();
		
		// 2 注册监听
		client.getChlidren();
		
		// 3 业务逻辑处理
		client.business();
		
	}

	private void business() throws InterruptedException {
		Thread.sleep(Long.MAX_VALUE);
	}

	private void getChlidren() throws KeeperException, InterruptedException {
		
		List<String> children = zkClient.getChildren("/servers", true);
		
		// 瀛樺偍鏈嶅姟鍣ㄨ妭鐐逛富鏈哄悕绉伴泦鍚�
		ArrayList<String> hosts = new ArrayList<>();
		
		for (String child : children) {
			
			byte[] data = zkClient.getData("/servers/"+child, false, null);
			
			hosts.add(new String(data));
		}
		
		// 灏嗘墍鏈夊湪绾夸富鏈哄悕绉版墦鍗板埌鎺у埗鍙�
		System.out.println(hosts);
		
	}

	private String connectString = "192.168.88.128:2181,192.168.88.130:2181,192.168.88.131:2181";
	private int sessionTimeout = 2000;
	private ZooKeeper zkClient;

	private void getConnect() throws IOException {
	
		zkClient = new ZooKeeper(connectString , sessionTimeout , new Watcher() {
			
			@Override
			public void process(WatchedEvent event) {
				
				try {
					getChlidren();
				} catch (KeeperException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
	}
}
