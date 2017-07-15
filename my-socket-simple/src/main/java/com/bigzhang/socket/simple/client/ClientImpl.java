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

    protected Logger logger = LoggerFactory.getLogger(getClass());

    public void start() {
        PrintWriter write = null;
        BufferedReader in = null;
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", 8888);
            logger.info("ClientImpl start success....");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            write = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String readline;
            readline = br.readLine();
            while (!readline.equals("end")) {
                write.println(readline);
                write.flush();
                logger.info("Client:" + readline);
                logger.info("Server:" + in.readLine());
                readline = br.readLine();
            }
        } catch (Exception e) {
            System.out.println("can not listen to:" + e);// 出错，打印出错信息
        } finally {
            write.close();
            try {
                in.close();
            } catch (IOException e) {

            }
            try {
                socket.close();
            } catch (IOException e) {
            }

        }
    }
}
