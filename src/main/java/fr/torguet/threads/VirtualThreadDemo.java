package fr.torguet.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;
import java.net.*;
import java.net.http.*;


public class VirtualThreadDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final int NTASKS = 10;
        ExecutorService service = Executors.newVirtualThreadPerTaskExecutor();
        for (int i = 0; i < NTASKS; i++) {
            service.submit(() -> {
                long id = Thread.currentThread().threadId();
                LockSupport.parkNanos(1_000_000_000);
                System.out.print(id + ",");
            });
        }
        service.close();

        service = Executors.newVirtualThreadPerTaskExecutor();
        Future<String> f1 = service.submit(() -> get("https://horstmann.com/random/adjective"));
        Future<String> f2 = service.submit(() -> get("https://horstmann.com/random/noun"));
        String result = f1.get() + " " + f2.get();
        System.out.println();
        System.out.println(result);
        service.close();

        service = Executors.newVirtualThreadPerTaskExecutor();
        List<Callable<String>> callables = new ArrayList<>();
        final int ADJECTIVES = 4;
        for (int i = 1; i <= ADJECTIVES; i++)
            callables.add(() -> get("https://horstmann.com/random/adjective"));
        callables.add(() -> get("https://horstmann.com/random/noun"));
        List<String> results = new ArrayList<>();
        for (Future<String> f : service.invokeAll(callables))
            results.add(f.get());
        System.out.println(String.join(" ", results));
        service.close();
        service = Executors.newVirtualThreadPerTaskExecutor();
        List<Future<String>> futures = new ArrayList<>();
        final int TASKS = 250;
        for (int i = 1; i <= TASKS; i++)
            futures.add(service.submit(() -> get("https://horstmann.com/random/word")));
        for (Future<String> f : futures)
            System.out.print(f.get() + " ");
        System.out.println();
        service.close();
    }

    private static final HttpClient client = HttpClient.newHttpClient();

    private static final Semaphore SEMAPHORE = new Semaphore(20);

    public static String get(String url) {
        try {
            var request = HttpRequest.newBuilder().uri(new URI(url)).GET().build();
            SEMAPHORE.acquire();
            try {
                Thread.sleep(100);
                return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
            } finally {
                SEMAPHORE.release();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            var rex = new RuntimeException();
            rex.initCause(ex);
            throw rex;
        }
    }
}

