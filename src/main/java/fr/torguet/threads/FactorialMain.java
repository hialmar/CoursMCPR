package fr.torguet.threads;

import java.util.concurrent.ForkJoinPool;

public class FactorialMain {
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        FactorialSquareCalculator calculator = new FactorialSquareCalculator(150);

        forkJoinPool.execute(calculator);

        System.out.println(calculator.join());

    }
}
