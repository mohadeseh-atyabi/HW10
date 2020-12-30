package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class is a server.
 */
public class Main {

    public static void main(String[] args) {

        try (ServerSocket welcomingSocket = new ServerSocket(7657)) {
            System.out.print("Server started.\nWaiting for a client ... ");
            try (Socket connectionSocket = welcomingSocket.accept()) {
                System.out.println("client accepted!");
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

                    System.out.println("Receive: " + input);
                    if (input.equals("over")){
                        toContinue = false;
                        continue;
                    }
                    out.write(toSend.toString().getBytes());
                    System.out.println("Sent: " + toSend);
                }
                System.out.print("All messages sent.\nClosing client ... ");
            } catch (IOException ex) {
                System.err.println(ex);
            }
            System.out.print("done.\nClosing server ... ");
        } catch (IOException ex) {
            System.err.println(ex);
        }
        System.out.println("done.");
        
    }
}
