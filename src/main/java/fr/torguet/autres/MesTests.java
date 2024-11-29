package fr.torguet.autres;

import java.util.Scanner;

public class MesTests {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        outer : while(true) {
            System.out.println("0 : fin, 1 : 1");
            int n = scanner.nextInt();
            switch (n) {
                case 0 -> {
                    System.out.println("FIN ?");
                    break outer;
                }
                case 1 -> {
                    System.out.println(n);
                }
                default -> {
                    System.out.println("ERROR");
                }
            }
        }

        System.out.println("EXIT");
    }
}
