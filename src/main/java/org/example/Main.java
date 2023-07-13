package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    // TCP server

    public static class WorkerThread extends Thread {
        Socket socket;

        public WorkerThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                while (true) {
                    String string = bufferedReader.readLine();
                    printWriter.println("###" + string + "####");
                    printWriter.flush();
                }
            } catch (Exception exception) {
            }
        }
    }

    public static void main(String[] args) throws Exception {
        // Socket programming
        ServerSocket serverSocket = new ServerSocket(6666);
        while (true) {
            Socket socket = serverSocket.accept();
            new WorkerThread(socket).start();
        }
    }
}
