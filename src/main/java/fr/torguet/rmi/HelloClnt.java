package fr.torguet.rmi;

import java.rmi.*;

public class HelloClnt {
    public static void main(String[] args) {
        try {
            // Récupération d'un proxy sur l'objet
            Hello obj = (Hello) Naming.lookup("//localhost/HelloServer");
            // Appel d'une méthode sur l'objet distant
            String message = obj.sayHello();
            System.out.println(message);

            for (int i = 0; i < 1000; i++) {
                PersonneImpl p = new PersonneImpl("Albert", "Einstein");
                message = obj.sayHello(p);
                System.out.println(message);
                p = null; // force le GC local et le GC réparti
            }

            Runtime.getRuntime().gc();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
