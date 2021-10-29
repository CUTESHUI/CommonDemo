package com.shui.fastdfs;
//

import lombok.extern.slf4j.Slf4j;
import top.folen.common.FastDfsException;
import top.folen.common.NameValuePair;
import top.folen.fastdfs.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class FastDFSClient {

    /**
     * 配置文件名称
     */
    private static final String CONF_FILE = "fdfs_client.conf";

    private static TrackerClient trackerClient;
    private static TrackerServer trackerServer;
    private static StorageServer storageServer;
    private static StorageClient1 storageClient;

    static {
        try {
            ClientGlobal.init(CONF_FILE);
            trackerClient = new TrackerClient();
            trackerServer = trackerClient.getTrackerServer();
            // storage server非线程安全，如非必须，不建议使用storage server
            // 通过docker不会，无法正确获取storageServer的地址信息，因此进行初始化配置
            storageServer = new StorageServer("127.0.0.1", 23000, 0);
            storageClient = new StorageClient1(trackerServer, storageServer);
            System.out.println("初始化配置信息：" + ClientGlobal.configInfo());
        } catch (Exception e) {
            log.error("初始化FastDFSClient异常", e);
        }
    }

    public static String[] upload(FastDFSFile file) {
        log.info("File Name: " + file.getName() + "File Length:" + file.getContent().length);

        NameValuePair[] metaList = new NameValuePair[1];
        metaList[0] = new NameValuePair("author", file.getAuthor());

        String[] uploadResults = null;
        try {
            uploadResults = storageClient.uploadFile(file.getContent(), file.getExt(), metaList);
        } catch (Exception e) {
            log.error("上传文件异常，文件名:" + file.getName(), e);
        }

        if (uploadResults == null && storageClient != null) {
            log.error("上传文件异常，错误码:" + storageClient.getErrorCode());
        }

        return uploadResults;
    }

    public static FileInfo getFile(String groupName, String remoteFileName) {
        try {
            return storageClient.getFileInfo(groupName, remoteFileName);
        } catch (Exception e) {
            log.error("获取文件异常", e);
        }
        return null;
    }

    public static InputStream downFile(String groupName, String remoteFileName) {
        try {
            byte[] fileByte = storageClient.downloadFile(groupName, remoteFileName);
            return new ByteArrayInputStream(fileByte);
        } catch (Exception e) {
            log.error("下载文件异常", e);
        }
        return null;
    }

    public static void deleteFile(String groupName, String remoteFileName)
            throws Exception {
        storageClient.deleteFile(groupName, remoteFileName);
    }

    public static StorageServer[] getStoreStorages(String groupName)
            throws IOException, FastDfsException {
        return trackerClient.getStoreStorages(trackerServer, groupName);
    }

    public static ServerInfo[] getFetchStorages(String groupName,
                                                String remoteFileName) throws IOException, FastDfsException {
        return trackerClient.getFetchStorages(trackerServer, groupName, remoteFileName);
    }

    /**
     * 获取配置文件中的访问路径
     *
     * @return
     */
    public static String getTrackerUrl() {
        return "http://" + trackerServer.getInetSocketAddress().getHostString() + ":" + ClientGlobal.getG_tracker_http_port() + "/";
    }
}
