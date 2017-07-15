package com.bigzhang.socket.simple.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by abc on 2017/7/15.
 */
public class ServerImpl implements IServer {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    private ServerSocket server = null;

    @Override
    public void start() {
        logger.info("ServerImpl start .........");
        logger.info("ServerImpl start .........");
        logger.info("ServerImpl start .........");
        logger.info("ServerImpl start .........");
        run();
    }

    public void run() {
        try {
            try {
                server = new ServerSocket(8888);
                logger.info("服务器启动成功");
            } catch (Exception e) {
                logger.info("没有启动监听：" + e);
            }
            Socket socket = null;
            try {
                socket = server.accept();
            } catch (Exception e) {
                logger.error("Error." + e);
            }
            String line;
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            logger.info("Client:" + in.readLine());
            line = br.readLine();
            while (!line.equals("end")) {
                writer.println(line);
                writer.flush();
                logger.info("Server:" + line);
                logger.info("Client:" + in.readLine());
                line = br.readLine();
            }
            writer.close();
            in.close();
            socket.close();
            server.close();
        } catch (Exception e) {
            logger.error("",e);
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
