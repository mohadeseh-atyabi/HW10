package com.company;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * This class is a client.
 */
public class Main {

    public static void main(String[] args) {

        try (Socket client = new Socket("0.0.0.0", 7657)) {
            System.out.println("Connected to server.");
            OutputStream out = client.getOutputStream();
            InputStream in = client.getInputStream();
            byte[] buffer = new byte[2048];
            boolean toContinue = true;
            String input;
            Scanner myObj = new Scanner(System.in);
            while (toContinue){
                System.out.print("Sent: ");
                input = myObj.nextLine();
                out.write(input.getBytes());

                if (input.equals("over")){
                    toContinue = false;
                    continue;
                }

                int read = in.read(buffer);
                String received = new String(buffer,0,read);
                System.out.println("Receive: " + received);
            }
            System.out.print("All messages sent.\nClosing ... ");
        } catch (IOException ex) {
            System.err.println(ex);
        }
        System.out.println("done.");

    }
}
