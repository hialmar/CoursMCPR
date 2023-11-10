package fr.torguet.threads;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Futures {

    private static class SquareCalculator {

        // private ExecutorService executor = Executors.newSingleThreadExecutor();
        private final ExecutorService executor = Executors.newFixedThreadPool(2);

        public Future<Integer> calculate(Integer input) {
            return executor.submit(() -> {
                Thread.sleep(100L *input);
                return input * input;
            });
        }

        public void shutdown() {
            executor.shutdown();
        }
    }
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        SquareCalculator squareCalculator = new SquareCalculator();

        Future<Integer> future = squareCalculator.calculate(10);

        while(!future.isDone()) {
            System.out.println("Calculating...");
            Thread.sleep(300);
        }

        Integer result = future.get();

        System.out.println("result = " + result);


        Future<Integer> future1 = squareCalculator.calculate(10);
        Future<Integer> future2 = squareCalculator.calculate(100);

        while (!(future1.isDone() && future2.isDone())) {
            System.out.printf(
                    "future1 is %s and future2 is %s%n",
                    future1.isDone() ? "done" : "not done",
                    future2.isDone() ? "done" : "not done"
            );
            Thread.sleep(300);
        }

        Integer result1 = future1.get();
        Integer result2 = future2.get();

        System.out.println(result1 + " and " + result2);

        squareCalculator.shutdown();
    }
}
