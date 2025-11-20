package fr.torguet.threads;

import java.util.Timer;
import java.util.TimerTask;

public class Timers {
    public static void main(String [] args) {
        final long debut = System.currentTimeMillis();

        TimerTask afficheTemps = new TimerTask() {
            public void run() {
                System.out.println(
                        System.currentTimeMillis()- debut);
            }};

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(afficheTemps, 0, 2000);

    }
}
