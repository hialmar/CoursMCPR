package fr.torguet.threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class Processus {
    public static void main(String[] args) throws IOException {
        Process process = Runtime.getRuntime().exec(new String[]{"ls","-lh"});
        BufferedReader input1 = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = input1.readLine()) != null) {
            System.out.println(line);
        }
        input1.close();

        Process process2 = Runtime.getRuntime().exec(new String[]{"bc"});
        BufferedReader input = new BufferedReader(new InputStreamReader(process2.getInputStream()));
        PrintStream output = new PrintStream(process2.getOutputStream());
        output.println("2 + 3");
        output.flush();
        System.out.println(input.readLine());
        output.println("quit");
        output.flush();
        System.out.println(input.readLine());
    }
}
