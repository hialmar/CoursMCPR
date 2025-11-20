import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestingTest {
    Stream<String> strings;

    @BeforeEach
    void setUp() {
        strings = Stream.of("one", "two", "three", "four");
    }

    @Test
    void test() {
        BinaryOperator<Integer> combiner = Integer::sum;
        BiFunction<Integer, String, Integer> accumulator =
                (partialReduction, element) -> partialReduction + element.length();

        int result = strings.reduce(0, accumulator, combiner);
        System.out.println("sum = " + result);

        assertEquals(15, result);
    }

    @Test
    void test2() {
        Function<String, Integer> mapper = String::length;
        BinaryOperator<Integer> combiner = Integer::sum;

        BiFunction<Integer, String, Integer> accumulator =
                (partialReduction, element) -> partialReduction + mapper.apply(element);

        int result = strings.reduce(0, accumulator, combiner);

        System.out.println("sum = " + result);

        assertEquals(15, result);
    }

    @Test
    void testCount() {
        Collection<String> strings =
                List.of("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten");

        long count =
                strings.stream()
                        .filter(s -> s.length() == 3)
                        .count();
        System.out.println("count = " + count);

        assertEquals(4, count);
    }

    @Test
    void testForEach() {
        Stream<String> strings = Stream.of("one", "two", "three", "four");
        strings.filter(s -> s.length() == 3)
                .map(String::toUpperCase)
                .forEach(System.out::println);
    }

    @Test
    void testForEachCapturingLambda() {
        Stream<String> strings = Stream.of("one", "two", "three", "four");
        List<String> result = new ArrayList<>();

        strings.filter(s -> s.length() == 3)
                .map(String::toUpperCase)
                .forEach(result::add);

        System.out.println("result = " + result);

        assertArrayEquals(new String[]{"ONE", "TWO"}, result.toArray());
    }

    @Test
    void testForEachNotCapturingLambda() {
        Stream<String> strings = Stream.of("one", "two", "three", "four");

        List<String> result =
                strings.filter(s -> s.length() == 3)
                        .map(String::toUpperCase)
                        .collect(Collectors.toList());

        System.out.println("result = " + result);
        assertArrayEquals(new String[]{"ONE", "TWO"}, result.toArray());

        result.add("TEST"); // works
    }

    @Test
    void testForEachNotCapturingLambda2() {
        Stream<String> strings = Stream.of("one", "two", "three", "four");

        List<String> result =
                strings.filter(s -> s.length() == 3)
                        .map(String::toUpperCase)
                        .toList();

        System.out.println("result = " + result);
        assertArrayEquals(new String[]{"ONE", "TWO"}, result.toArray());

        // immutable list : result.add("TEST"); // does not work
    }

    @Test
    void testToArray() {
        Stream<String> strings = Stream.of("one", "two", "three", "four");

        var result =
                strings.filter(s -> s.length() == 3)
                        .map(String::toUpperCase)
                        .toArray(String[]::new);

        System.out.println("result = " + Arrays.toString(result));
        assertArrayEquals(new String[]{"ONE", "TWO"}, result);

        // immutable list : result.add("TEST"); // does not work
    }
}
