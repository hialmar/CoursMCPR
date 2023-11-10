package fr.torguet.threads;

class Compte {
    private double solde;
    public synchronized void deposer(double somme){
        double soldeTemp = solde + somme;
        Thread.yield();
        solde = soldeTemp;
    }
    public double getSolde() {
        return solde;
    }
}



public class Synchronisation {


    public static void main(String[] args) {
        Compte compte = new Compte();
        Thread t1 = new Thread() {
            public void run() {
                for (int i = 0; i < 100; i++) {
                    compte.deposer(1000);
                }
            }
        };
        Thread t2 = new Thread() {
            public void run() {
                for (int i = 0; i < 100; i++) {
                    compte.deposer(1000);
                }
            }
        };
        Thread t3 = new Thread() {
            public void run() {
                for (int i = 0; i < 100; i++) {
                    compte.deposer(1000);
                }
            }
        };

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            System.out.println("Erreur");
        }

        System.out.println(compte.getSolde());
    }
}
