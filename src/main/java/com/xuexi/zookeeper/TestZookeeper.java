package com.xuexi.zookeeper;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

public class TestZookeeper {

	private String connectString="192.168.88.128:2181,192.168.88.130:2181,192.168.88.131:2181";
	private int sessionTimeout = 2000;
	private ZooKeeper zkClient;

	@Before
	public void init() throws IOException{
		
		zkClient = new ZooKeeper(connectString, sessionTimeout , new Watcher() {
			
			@Override
			public void process(WatchedEvent event) {
				
//				System.out.println("---------start----------");
//				List<String> children;
//				try {
//					children = zkClient.getChildren("/", true);
//					
//					for (String child : children) {
//						System.out.println(child);
//					}
//					System.out.println("---------end----------");
//				} catch (KeeperException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}
		});
	}
	
	// 1 鍒涘缓鑺傜偣
	@Test
	public void createNode() throws KeeperException, InterruptedException{
		
		String path = zkClient.create("/atguigu", "dahaigezuishuai".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		
		System.out.println(path);
		
	}
	
	// 2 鑾峰彇瀛愯妭鐐� 骞剁洃鎺ц妭鐐圭殑鍙樺寲
	@Test
	public void getDataAndWatch() throws KeeperException, InterruptedException{
		
		List<String> children = zkClient.getChildren("/", true);
		
		for (String child : children) {
			System.out.println(child);
		}
		
		Thread.sleep(Long.MAX_VALUE);
	}
	
	// 3 鍒ゆ柇鑺傜偣鏄惁瀛樺湪
	@Test
	public void exist() throws KeeperException, InterruptedException{
		
		Stat stat = zkClient.exists("/atguigu", false);
		
		System.out.println(stat==null? "not exist":"exist");
	}
}
