package com.bigzhang.socket.simple.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by abc on 2017/7/15.
 */
public class ServerImpl implements IServer {

    protected static Logger logger = LoggerFactory.getLogger(ServerImpl.class);
    private ServerSocket server = null;

    @Override
    public void start() {
        logger.info("ServerImpl start .........");
        logger.info("ServerImpl start .........");
        logger.info("ServerImpl start .........");
        logger.info("ServerImpl start .........");
        run();
    }

    public Socket getSocket(Socket socket) {
        if (socket == null) {
            try {
                socket = server.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return socket;
    }



    public void run() {
        try {
            server = new ServerSocket(8888);
        } catch (Exception e) {
            logger.error("",e);
        }

        int count = 0;
        for (;;count++){
            new Thread(new Worker(getSocket(null))).run();
            if(count > 2){
                try {
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    static class Worker implements Runnable{

        private Socket socket;

        public Worker(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            do0(socket);

        }

        public void do0(Socket socket) {
            logger.info("socket.isConnected()={}",socket.isConnected());
            while (socket.isConnected()) {
                BufferedReader in = null;
                try {
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String request = null;
                try {
                    request = in.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (request.contains("2")) {
                    logger.info("client request contain 2");
                } else {
                    logger.info("client request not contain 2");
                }
            }
            logger.info("socket is not connected");
        }
    }


    @Override
    public void stop() {

        if (server != null) {
            try {
                server.close();
            } catch (IOException e) {
                logger.error("serverSocket.close fail!", e);
            }
        }
    }
}
