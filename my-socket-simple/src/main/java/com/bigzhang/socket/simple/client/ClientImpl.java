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
            logger.info("创建第{}个客户端Socket",counter);
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

                int counter = 0;
                while (socket.isConnected()) {
                    counter++;
                    write = new PrintWriter(socket.getOutputStream());
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String data = "Clientid="+clientId+"-1";
                    logger.info("ClientId={} send msg={}",clientId,data);
                    write.println(data);
                    write.flush();

                    Thread.currentThread().sleep(1000*5);
                    if(counter >= 1){
                        break;
                    }

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
                try {
                    if(socket!=null)
                    socket.close();
                } catch (IOException e) {
                }

            }
        }
    }



}
