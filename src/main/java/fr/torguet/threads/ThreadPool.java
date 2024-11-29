package fr.torguet.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {

    static class Tache implements Runnable {
        private final int nbTaches;

        public Tache(int nbTaches) {
            this.nbTaches = nbTaches;
        }

        public void run() {
            for (int i = 0; i < this.nbTaches; i++) {
                System.out.println("Je suis la tâche " + i + " du thread " + Thread.currentThread().getName());
            }
        }
    }
    public static void main(String[] args) {
        //ExecutorService pool = Executors.newSingleThreadExecutor();
        //ExecutorService pool = Executors.newFixedThreadPool(10);
        ExecutorService pool = Executors.newCachedThreadPool();

        for (int i = 0; i < 100; i++){
            Runnable r = new Tache(10);
            pool.execute(r);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(pool);
        pool.shutdown();
    }
}
