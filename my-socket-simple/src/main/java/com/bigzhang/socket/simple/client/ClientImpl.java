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

            while (true) {
                write = new PrintWriter(socket.getOutputStream());
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                write.println("1234567890");
                write.flush();
                Thread.currentThread().sleep(100);
            }
        } catch (Exception e) {
            logger.error("", e);
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
