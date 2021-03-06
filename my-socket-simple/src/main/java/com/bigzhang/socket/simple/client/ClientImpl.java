package com.bigzhang.socket.simple.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by abc on 2017/7/15.
 */
public class ClientImpl {

    protected static  Logger logger = LoggerFactory.getLogger(ClientImpl.class);

    public void start() {

        //main Thread
        int counter = 1;
        for (;;counter++){
            new Thread(new ClientWorker(counter)).start();

            if(counter >3){
                break;
            }
        }


    }

    static class ClientWorker implements Runnable{

        private Integer clientId;

        private Socket socket;

        public ClientWorker(Integer clientId) {
            logger.info("clientId={}",clientId);
            this.clientId = clientId;
        }

        @Override
        public void run() {
            PrintWriter write = null;
            BufferedReader in = null;
            try {
                socket = new Socket("127.0.0.1", 8888);
                socket.setSoTimeout(1000);
                logger.info("创建第{}个客户端Socket,port={}",clientId,socket.getLocalPort());
                String response = "";
                int counter = 0;
                while (socket.isConnected()) {
                    counter++;
                    write = new PrintWriter(socket.getOutputStream());
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String data = "Clientid="+clientId+"-1";
                    logger.info("ClientId={} send msg={}",clientId,data);
                    write.println(data);
                    write.flush();
                    response = in.readLine();
                    if(response == null || "".equals(response)){
                        logger.info("无响应，socket may closed!");
                        break;
                    }
//                    Thread.currentThread().sleep(1000*5);
                }
            } catch (Exception e) {
                logger.error("", e);
            } finally {
                if(write != null){
                    write.close();
                }

                try {
                    if(in!=null)
                    in.close();
                } catch (IOException e) {

                }
                int port = -1;
                try {
                     port = socket.getLocalPort();
                    logger.info("关闭socket>port={}",port);
                    if(socket!=null)
                    socket.close();

                } catch (IOException e) {
                    logger.error("关闭port="+port+"失败",e);
                }

            }
        }
    }



}
