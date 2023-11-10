package fr.torguet.threads;

class Depot {
    private int nbJetons = 0;

    public synchronized void consomme() {
        try {
            System.out.println("Je suis le thread " + Thread.currentThread().getName() + " et je veux consommer");
            while (nbJetons == 0) {
                System.out.println("Je suis le thread " + Thread.currentThread().getName() + " et j'attends");
                wait();
            }
            nbJetons--;
            System.out.println("Je suis le thread " + Thread.currentThread().getName() + " et j'ai consommé");
        } catch (InterruptedException e) {
            e.addSuppressed(e);
        }
    }

    public synchronized void produit(int n) {
        System.out.println("Je suis le thread " + Thread.currentThread().getName() + " et je produis");
        nbJetons += n;
        notifyAll();
        System.out.println("Je suis le thread " + Thread.currentThread().getName() + " et j'ai produit");
    }

    @Override
    public String toString() {
        return "Depot{" +
                "nbJetons=" + nbJetons +
                '}';
    }
}

class Consommateur extends Thread {
    private final Depot depot;

    public Consommateur(Depot depot) {
        this.depot = depot;
    }

    public void run() {
        for (int i = 0; i < 100; i++) {
            depot.consomme();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class Producteur extends Thread {
    private final Depot depot;

    public Producteur(Depot depot) {
        this.depot = depot;
    }

    public void run() {
        for (int i = 0; i < 100; i++) {
            depot.produit(1);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}


public class Conditions {
    public static void main(String[] args) {
        Depot depot = new Depot();
        Consommateur consommateur = new Consommateur(depot);
        Consommateur consommateur2 = new Consommateur(depot);
        Producteur producteur = new Producteur(depot);
        Producteur producteur2 = new Producteur(depot);
        consommateur.start();
        consommateur2.start();
        producteur.start();
        producteur2.start();

        try {
            consommateur.join();
            producteur.join();
            consommateur2.join();
            producteur2.join();
        } catch (InterruptedException e) {
            System.out.println("Erreur");
        }

        System.out.println(depot);
    }
}
