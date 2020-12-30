package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try (Socket client = new Socket("0.0.0.0", 1212)) {
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
