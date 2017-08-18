package com.core.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 */
public class FtpUtil {

    private static FTPClient ftpClient;
    //private static String fileName;

    /**
     * init ftp servere
     */
    public FtpUtil() {
        this.reSet();
    }

    public void reSet() {
        this.connectServer(CommConstant.ip, CommConstant.port, CommConstant.userName, CommConstant.userPwd, CommConstant.path);
    }

    /**
     * @param ip
     * @param port
     * @param userName
     * @param userPwd
     * @param path
     * @throws SocketException
     * @throws IOException
     *             function:连接到服务器
     */
    public void connectServer(String ip, int port, String userName,
                              String userPwd, String path) {
        ftpClient = new FTPClient();
        try {
            // 连接
            ftpClient.connect(ip, port);
            // 登录
            ftpClient.login(userName, userPwd);
            if (path != null && path.length() > 0) {
                // 跳转到指定目录
                ftpClient.changeWorkingDirectory(path);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @throws IOException
     *             function:关闭连接
     */
    public void closeServer() {
        if (ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param path
     * @return function:读取指定目录下的文件名
     * @throws IOException
     */
    public List<String> getFileList(String path) {
        List<String> fileLists = new ArrayList<String>();
        // 获得指定目录下所有文件名
        FTPFile[] ftpFiles = null;
        try {
            ftpFiles = ftpClient.listFiles(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; ftpFiles != null && i < ftpFiles.length; i++) {
            FTPFile file = ftpFiles[i];
            if (file.isFile()) {
                fileLists.add(file.getName());
            }
        }
        System.out.println(fileLists);
        return fileLists;
    }

    /**
     * @return function:从服务器上读取指定的文件
     * @throws ParseException
     * @throws IOException
     * {"orderMoneyCount":62400,"orderNumber":2}
     */
    public String readFile(String fileName) {
        InputStream ins = null;
        JSONObject obj = new JSONObject();
        int orderMoney = 0;
        String[] str;
        int orderNumber = 0;
        try {
            // 从服务器上读取指定的文件
            ftpClient.enterLocalPassiveMode();
            ins = ftpClient.retrieveFileStream(fileName);
            if (ins == null) {
                return null;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(ins, CommConstant.strencoding));
            String line;

            while ((line = reader.readLine()) != null) {
                str = line.split(",");
                if(line.isEmpty()){

                }else{
                    orderNumber++;
                    orderMoney += Integer.parseInt(str[11]);
                }
            }
            obj.put("orderMoneyCount", orderMoney);
            obj.put("orderNumber", orderNumber);

            reader.close();
            if (ins != null) {
                ins.close();
            }
            ftpClient.getReply();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return obj.toString();
    }


    /**
     * @param fileName
     *            function:删除文件
     */
    public void deleteFile(String fileName) {
        try {
            ftpClient.deleteFile(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
