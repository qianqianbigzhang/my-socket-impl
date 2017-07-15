package com.bigzhang.socket.simple.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by abc on 2017/7/15.
 */
public class ServerImpl implements IServer {

    protected static Logger logger = LoggerFactory.getLogger(ServerImpl.class);
    private LinkedBlockingQueue<Socket> queue = new LinkedBlockingQueue(5);
    private int count = 0;

    @Override
    public void start() {
        logger.info("ServerImpl start .........");
        logger.info("ServerImpl start .........");
        //a thread accept
        new Thread(new Runnable() {
            @Override
            public void run() {
                accept0();
            }
        }).start();
        // a thread dispatch
        new Thread(new Runnable() {
            @Override
            public void run() {
                doWoker0();
            }
        }).start();
    }

    public void doWoker0() {
        for (; ; ) {
            try {
                new Thread(new Worker(queue.take())).start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    private Socket getSocket(ServerSocket server) {
        Socket socket = null;
        try {
            socket = server.accept();
            count++;
            logger.info("这个socket是第{}个连接,port={}", count, socket.getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socket;
    }

    public void accept0() {
        ServerSocket server = null;
        try {
            server = new ServerSocket(8888);
            server.setSoTimeout(1000);
        } catch (Exception e) {
            logger.error("", e);
        }
        Socket socket = null;
        while (true){
            socket = getSocket(server);
            queue.offer(socket);
        }

    }

    static class Worker implements Runnable {

        private Socket socket;

        public Worker(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            do0(socket);
        }

        public void do0(Socket socket) {
            logger.info("socket.isConnected()={}", socket.isConnected());
            BufferedReader in = null;
            PrintWriter write = null;

            String response = "";
            while (socket.isConnected()) {
                //simulate business
                try {
                    Thread.currentThread().sleep(1000 * 60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                try {
                    write = new PrintWriter(socket.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }

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

                if (request == null) {
                    logger.info("receive 数据为空!但是为什么还要接受null呢");
                    try {
                        Thread.currentThread().sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    response =  "response:" + request;
                    write.println(response);
                    write.flush();
                    logger.info("receive port={} : ={}", socket.getPort(), request);
                }

            }

            try {
                in.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


            logger.info("socket is not connected");

        }
    }


    @Override
    public void stop() {

    }
}
