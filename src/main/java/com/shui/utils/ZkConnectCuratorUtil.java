package com.shui.utils;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.util.List;

public class ZkConnectCuratorUtil {

    public CuratorFramework zkClient = null; //zk的客户端工具Curator（在本类通过new实例化的是，自动start）
    private static final int MAX_RETRY_TIMES = 3; //定义失败重试次数
    private static final int BASE_SLEEP_TIME_MS = 5000; //连接失败后，再次重试的间隔时间 单位:毫秒
    private static final int SESSION_TIME_OUT = 1000000; //会话存活时间,根据业务灵活指定 单位:毫秒
    private static final String ZK_SERVER_IP_PORT = "192.168.31.216:2181";//Zookeeper服务所在的IP和客户端端口
    private static final String NAMESPACE = "workspace";//指定后，默认操作的所有的节点都会在该工作空间下进行

    //本类通过new ZkCuratorUtil()时，自动连通zkClient
    public ZkConnectCuratorUtil() {
        RetryPolicy retryPolicy = new RetryNTimes(MAX_RETRY_TIMES, BASE_SLEEP_TIME_MS);//首次连接失败后，重试策略
        zkClient = CuratorFrameworkFactory.builder()
                //.authorization("digest", "root:root".getBytes())//登录超级管理(需单独配)
                .connectString(ZK_SERVER_IP_PORT)
                .sessionTimeoutMs(SESSION_TIME_OUT)
                .retryPolicy(retryPolicy)
                .namespace(NAMESPACE).build();
        zkClient.start();
    }

    public void closeZKClient() {
        if (zkClient != null) {
            this.zkClient.close();
        }
    }

    //级联创建节点（原生API不支持/后台客户端也不支持，但是Curator支持）
    public static void createNodes(CuratorFramework zkClient, String nodePath, String nodeData) throws Exception {
        zkClient.create()
                .creatingParentContainersIfNeeded()//创建父节点,如果需要的话
                .withMode(CreateMode.PERSISTENT) //指定节点是临时的，还是永久的
                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE) //指定节点的操作权限
                .forPath(nodePath, nodeData.getBytes());
        System.out.println(nodePath + "节点已成功创建…");
    }

    //删除node节点及其子节点
    public static void deleteNodeWithChild(CuratorFramework zkClient, String nodePath) throws Exception {
        zkClient.delete()
                .guaranteed()                //保证删除：如果删除失败，那么在后端还是继续会删除，直到成功
                .deletingChildrenIfNeeded()  //级联删除子节点
                //.withVersion(1)//版本号可以据需使用
                .forPath(nodePath);
        System.out.println(nodePath + "节点已删除成功…");
    }

    //更新节点data数据
    public static void updateNodeData(CuratorFramework zkClient, String nodePath, String nodeNewData) throws Exception {
        zkClient.setData().withVersion(0).forPath(nodePath, nodeNewData.getBytes());//版本号据需使用，默认可以不带
        System.out.println(nodePath + "节点数据已修改成功…");
    }

    //查询node节点数据
    public static void getNodeData(CuratorFramework zkClient, String nodePath) throws Exception {
        Stat stat = new Stat();
        byte[] data = zkClient.getData().storingStatIn(stat).forPath(nodePath);
        System.out.println("节点" + nodePath + "的数据为" + new String(data));
        System.out.println("节点的版本号为：" + stat.getVersion());
    }


    //打印node子节点
    public static void printChildNodes(CuratorFramework zkClient, String parentNodePath) throws Exception {
        List<String> childNodes = zkClient.getChildren().forPath(parentNodePath);
        System.out.println("开始打印子节点");
        for (String str : childNodes) {
            System.out.println(str);
        }
    }

    public static void main(String[] args) {
        ZkConnectCuratorUtil zkUtil = new ZkConnectCuratorUtil();
        CuratorFrameworkState state = zkUtil.zkClient.getState();
        System.out.println("当前客户的状态：" + (CuratorFrameworkState.STARTED.equals(state) ? "连接中" : "已关闭"));
        zkUtil.closeZKClient();
        state = zkUtil.zkClient.getState();
        System.out.println("当前客户的状态：" + (CuratorFrameworkState.STARTED.equals(state) ? "连接成功" : "已关闭"));
    }
}
