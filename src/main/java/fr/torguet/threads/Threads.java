package fr.torguet.threads;

class MonThread extends Thread {
    public void run() {
        System.out.println("Je suis le thread " + this.getName());
    }
}

class MonRunnable implements Runnable {
    public void run() {
        System.out.println("Je suis le thread " + Thread.currentThread().getName());
    }
}
public class Threads {
    public static void main(String[] args) {
        MonThread thread1 = new MonThread();
        MonThread thread2 = new MonThread();
        Thread thread3 = new Thread(new MonRunnable());
        Thread thread4 = new Thread(new MonRunnable());
        Thread thread5 = new Thread() {
            public void run() {
                System.out.println("Je suis le thread " + this.getName());
            }
        };
        Thread thread6 = new Thread(new Runnable() {
            public void run() {
                System.out.println("Je suis le thread " + Thread.currentThread().getName());
            }
        });
        Thread thread7 = new Thread(() -> System.out.println("Je suis le thread " + Thread.currentThread().getName()));
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
        thread7.start();

        System.out.println("Je suis le thread " + Thread.currentThread().getName());
    }
}
