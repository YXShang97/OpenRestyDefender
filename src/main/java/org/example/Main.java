package org.example;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

@Service
public class Main {
    // TCP server
    private static Rule rule;

//    private static ReentrantReadWriteLock reentrantLock = new ReentrantReadWriteLock();

    @Autowired
    private static AmazonS3 amazonS3;

    public class RuleRefreshingThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(200);
                    // get the latest rule from S3
                    S3Object s3Object = amazonS3.getObject("openrestydefenderrules", "rule.json");
                    // read the rule from the S3 object
                    ObjectMapper objectMapper = new ObjectMapper();
                    // update the rule
                    // immutable object
//                    reentrantLock.writeLock().lock();
                    Rule newRule = objectMapper.readValue(s3Object.getObjectContent(), Rule.class);
//                    reentrantLock.writeLock().unlock();
                    rule = newRule;
                } catch (Exception exception) {
                }
            }
        }
    }

    public static class WorkerThread extends Thread {
        Socket socket;

        public WorkerThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                while (true) {
                    String string = bufferedReader.readLine();
                    if (string == null) {
                        break;
                    }
                    HttpRequest httpRequest = HttpRequest.from(string);
//                    reentrantLock.readLock().lock();
                    if (rule.check(httpRequest)) {
                        printWriter.println("Y");
                    } else {
                        printWriter.println("N");
                    }
//                    reentrantLock.readLock().unlock();
                    printWriter.flush();
                }
            } catch (Exception exception) {
            }
        }
    }

    public static void main(String[] args) throws Exception {
        // preheat: load the rule from S3
        S3Object s3Object = amazonS3.getObject("openrestydefenderrules", "rule.json");
        ObjectMapper objectMapper = new ObjectMapper();
        rule = objectMapper.readValue(s3Object.getObjectContent(), Rule.class);

        // Socket programming
        ServerSocket serverSocket = new ServerSocket(6666);
        while (true) {
            Socket socket = serverSocket.accept();
            new WorkerThread(socket).start();
        }
    }
}
