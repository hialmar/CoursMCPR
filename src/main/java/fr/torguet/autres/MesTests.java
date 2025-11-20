package fr.torguet.autres;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class MesTests {

    static int reduce(List<Integer> ints, BinaryOperator<Integer> sum) {
        int result = ints.getFirst();
        for (int index = 1; index < ints.size(); index++) {
            result = sum.apply(result, ints.get(index));
        }
        return result;
    }


    public static void test1() {
        List<Integer> ints = List.of(3, 6, 2, 1);
        BinaryOperator<Integer> sum = Integer::sum;

        int result = reduce(ints, sum);
        System.out.println("sum = " + result);
        BinaryOperator<Integer> max = (a, b) -> a > b ? a: b;

        result = ints.getFirst();
        for (int index = 1; index < ints.size(); index++) {
            result = max.apply(result, ints.get(index));
        }
        System.out.println("max = " + result);


        int result1 = reduce(ints.subList(0, 2), sum);
        int result2 = reduce(ints.subList(2, 4), sum);

        result = sum.apply(result1, result2);
        System.out.println("sum = " + result);

    }

    static void test2() throws IOException {
        Stream<Integer> ints = Stream.of(0, 0, 0, 0);

        int sum = ints.reduce(0, (a, b) -> a + b);
        System.out.println("sum = " + sum);

        ints = Stream.empty(); //Stream.of(0, 0, 0, 0); // Stream.of(2, 8, 1, 5, 3);
        Optional<Integer> optional = ints.reduce((i1, i2) -> i1 > i2 ? i1: i2);

        if (optional.isPresent()) {
            System.out.println("result = " + optional.orElseThrow());
        } else {
            System.out.println("No result could be computed");
        }

    }

    public static void main(String[] args) throws IOException, InterruptedException {




        test2();

        System.exit(0);

        // The URI of the file
        URI uri = URI.create("https://www.gutenberg.org/files/98/98-0.txt");

// The code to open create an HTTP request
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(uri).build();


// The sending of the request
        HttpResponse<Stream<String>> response = client.send(request, HttpResponse.BodyHandlers.ofLines());
        List<String> lines;
        try (Stream<String> stream = response.body()) {
            lines = stream
                    .dropWhile(line -> !line.equals("A TALE OF TWO CITIES"))
                    .takeWhile(line -> !line.equals("*** END OF THE PROJECT GUTENBERG EBOOK A TALE OF TWO CITIES ***"))
                    .toList();
        }
        System.out.println("# lines = " + lines.size());

        System.exit(0);

        Stream.Builder<String> builder = Stream.builder();

        builder.add("one")
                .add("two")
                .add("three")
                .add("four");

        Stream<String> stream = builder.build();

        List<String> list = stream.toList();
        System.out.println("list = " + list);

        System.exit(0);


        String sentence = "For there is good news yet to hear and fine things to be seen";

        Pattern pattern = Pattern.compile(" ");
        Stream<String> stream3 = pattern.splitAsStream(sentence);
        List<String> words = stream3.toList();

        System.out.println("words = " + words);



        Path log = Path.of("/var/log/wifi.log"); // adjust to fit your installation
        try (Stream<String> lines2 = Files.lines(log)) {

            long warnings =
                    lines2.filter(line -> line.contains("Apple"))
                            .count();
            System.out.println("Number of Apples = " + warnings);

        } catch (IOException e) {
            // do something with the exception
        }



        sentence = "Hello Duke";
        List<String> letters =
                sentence.chars()
                        .mapToObj(Character::toString)
                        .collect(Collectors.toList());
        System.out.println("letters = " + letters);




        Random random = new Random(System.currentTimeMillis());
        letters =
                random.doubles(1_000, 0d, 1d)
                        .mapToObj(rand ->
                                rand < 0.5 ? "A" : // 50% of A
                                        rand < 0.8 ? "B" : // 30% of B
                                                rand < 0.9 ? "C" : // 10% of C
                                                        "D")  // 10% of D
                        .collect(Collectors.toList());

        // letters.stream().forEach(System.out::println);

        var map =
                letters.stream()
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        map.forEach((letter, number) -> System.out.println(letter + " :: " + number));

        System.exit(0);

        List<Boolean> booleans =
                random.doubles(1_000, 0d, 1d)
                        .mapToObj(rand -> rand <= 0.5) // you can tune the probability here
                        .collect(Collectors.toList());

// Let us count the number of true in this list
        long numberOfTrue =
                booleans.stream()
                        .filter(b -> b)
                        .count();
        System.out.println("numberOfTrue = " + numberOfTrue);

        System.exit(0);

        random = new Random(System.currentTimeMillis());
        List<Integer> randomInts =
                random.ints(10, 1, 5)
                        .boxed()
                        .collect(Collectors.toList());
        System.out.println("randomInts = " + randomInts);

        System.exit(0);


        String [] lettersTab = {"A", "B", "C", "D"};
        List<String> listLetters =
                IntStream.range(0, 10)
                        .mapToObj(index -> lettersTab[index % lettersTab.length])
                        .collect(Collectors.toList());
        System.out.println("listLetters = " + listLetters);




        Stream<String> iterated = Stream.iterate("+", s -> s.length() <= 5, s -> s + "+");
        iterated.forEach(System.out::println);


        iterated = Stream.iterate("+", s -> s + "+");
        iterated.limit(5L).forEach(System.out::println);


        Stream<String> generated = Stream.generate(() -> "+");
        List<String> strings =
                generated
                        .limit(10L)
                        .collect(Collectors.toList());

        System.out.println("strings = " + strings);

        System.exit(0);


        strings = List.of("one", "two", "three", "four");
        List<String> result =
                strings.stream()
                        .peek(s -> System.out.println("Starting with = " + s))
                        .filter(s -> s.startsWith("t"))
                        .peek(s -> System.out.println("Filtered = " + s))
                        .map(String::toUpperCase)
                        .peek(s -> System.out.println("Mapped = " + s))
                        .collect(Collectors.toList());
        System.out.println("result = " + result);


        List<Integer> list0 = List.of(1, 2, 3);
        List<Integer> list1 = List.of(4, 5, 6);
        List<Integer> list2 = List.of(7, 8, 9);

// 1st pattern: concat
        List<Integer> concat =
                Stream.concat(list0.stream(), list1.stream())
                        .collect(Collectors.toList());

// 2nd pattern: flatMap
        List<Integer> flatMap =
                Stream.of(list0.stream(), list1.stream(), list2.stream())
                        .flatMap(Function.identity())
                        .collect(Collectors.toList());

        System.out.println("concat  = " + concat);
        System.out.println("flatMap = " + flatMap);

        System.exit(0);

        List<String> l = new ArrayList(Arrays.asList("one", "two"));
        Stream<String> sl = l.stream();
        l.add("three");
        l.add("four");
        l.add("five");
        l.add("six");
        l.add("seven");
        Spliterator<String> spliterator = l.spliterator();
        Spliterator<String> sp = spliterator.trySplit();
        while (sp != null) {
            sp.forEachRemaining(System.out::println);
            sp = spliterator.trySplit();
        }
        spliterator.forEachRemaining(System.out::println);


        Function<String, Stream<Integer>> flatParser = s -> {
            try {
                return Stream.of(Integer.parseInt(s));
            } catch (NumberFormatException e) {
            }
            return Stream.empty();
        };



        strings = List.of("1", " ", "2", "3 ", "", "3");

        List<Integer> ints =
                strings.stream()
                        .<Integer>mapMulti((string, consumer) -> {
                            try {
                                consumer.accept(Integer.parseInt(string));
                            } catch (NumberFormatException ignored) {
                            }
                        })
                        .collect(Collectors.toList());
        System.out.println("ints = " + ints);

        ints =
                strings.stream()
                        .flatMap(flatParser)
                        .collect(Collectors.toList());
        System.out.println("ints = " + ints);

        System.exit(0);


        strings = List.of("one","two","three","four");
        List<Integer> lengths = strings.stream()
                .map(String::length)
                .collect(Collectors.toList());
        System.out.println("lengths = " + lengths);

        IntSummaryStatistics stats = strings.stream()
                .mapToInt(String::length)
                .summaryStatistics();
        System.out.println("stats = " + stats);


        var map2 = strings.stream()
                .collect(groupingBy(String::length, counting()));
        map2.forEach((key, value) -> System.out.println(key + " :: " + value));

        System.exit(0);

        try {
            final var filePath = "./src/main/java/fr/torguet/autres/MesTests.java";
            final var wordOfInterest = "public";

            try(var stream2 = Files.lines(Path.of(filePath))) {
                long count = stream2.filter(line -> line.contains(wordOfInterest))
                        .count();

                System.out.println(String.format("Found %d lines with the word %s", count, wordOfInterest));
            }

            try (var reader = Files.newBufferedReader(Path.of(filePath))) {
                String line = "";
                long count = 0;

                while((line = reader.readLine()) != null) {
                    if(line.contains(wordOfInterest)) {
                        count++;
                    }
                }

                System.out.println(String.format("Found %d lines with the word %s", count, wordOfInterest));
            }
        } catch(Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        System.exit(0);

        IntStream.rangeClosed(0, 5)
                .forEach(System.out::println);

        IntStream.iterate(0, i -> i < 15, i -> i + 3)
                .forEach(System.out::println);

        List<String> names = List.of("Jack", "Paula", "Kate", "Peter");

        names.forEach(name -> System.out.println(name));

        names.stream()
                .filter(name -> name.length() == 4)
                .forEach(name -> System.out.println(name));

        names.stream()
                .filter(name -> name.length() == 4)
                .map(String::toUpperCase)
                .forEach(System.out::println);



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
