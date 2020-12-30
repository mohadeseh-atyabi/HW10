package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

class ClientHandler implements Runnable {

    private Socket connectionSocket;
    private int clientNum;

    public ClientHandler(Socket connectionSocket, int clientNum) {
        this.connectionSocket = connectionSocket;
        this.clientNum=clientNum;
    }

    @Override
    public void run() {
        try {
            OutputStream out = connectionSocket.getOutputStream();
            InputStream in = connectionSocket.getInputStream();
            byte[] buffer = new byte[2048];
            boolean toContinue = true;
            StringBuilder toSend = new StringBuilder();
            String input;
            while (toContinue){
                int read = in.read(buffer);
                input = new String(buffer,0,read);

                if (toSend.length()==0){
                    toSend.append(input);
                } else
                    toSend.append('\n').append(input);

                System.out.println("Receive from " + clientNum + ": " + input);
                if (input.equals("over")){
                    toContinue = false;
                    continue;
                }
                out.write(toSend.toString().getBytes());
                System.out.println("Sent to " + clientNum + ": " + toSend);
                Thread.sleep(2000);
            }
            System.out.println("All messages sent.\nClosing client" + clientNum +"... ");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                connectionSocket.close();
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }
}

