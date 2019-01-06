package com.shawn.touchstone.functional;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.junit.Test;

/**
 * Created by shangfei on 4/8/17.
 */
public class StreamTest {

  @Test
  public void testImperativeVSFunctional() {
    final List<BigDecimal> prices = Arrays.asList(new BigDecimal("10"),
        new BigDecimal("30"),
        new BigDecimal("17"),
        new BigDecimal("15"),
        new BigDecimal("18"),
        new BigDecimal("45"),
        new BigDecimal("12"));
    BigDecimal totalDiscountedPriceByFunctional = prices.stream()
                                            .filter(o -> o.compareTo(BigDecimal.valueOf(20)) > 0)
                                            .map(o -> o.multiply(BigDecimal.valueOf(0.9)))
                                            .reduce(BigDecimal.ZERO, BigDecimal::add);



    BigDecimal totalDiscountedPriceByHabitual = BigDecimal.ZERO;
    for(BigDecimal price: prices) {
      if(price.compareTo(BigDecimal.valueOf(20)) > 0) {
        totalDiscountedPriceByHabitual = totalDiscountedPriceByHabitual
                                        .add(price.multiply(BigDecimal.valueOf(0.9)));
      }
    }
    assertEquals(totalDiscountedPriceByHabitual, totalDiscountedPriceByFunctional);

  }


  @Test
  public void testIteratingList(){
    final List<String> friends = Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");
    friends.stream().forEach(new Consumer<String>() {
      @Override
      public void accept(String s) {
        System.out.println(s);
      }
    });

    friends.stream().forEach((final String name) -> System.out.println(name));
    friends.stream().forEach(name -> System.out.println(name));
    friends.stream().forEach(System.out::println);
  }

  @Test
  public void testTransformList(){
    final List<String> friends = Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");
    friends.stream().map(String::toUpperCase).forEach(System.out::println);
    friends.stream().map(String::length).forEach(System.out::println);

  }

  @Test
  public void testReusablePredicate(){

    final List<String> friends = Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");
    final List<String> editors = Arrays.asList("Gua", "Nic", "Nel", "Rita", "Sara", "Scott");
    friends.stream().filter(new NameStartsWithNOrR()).forEach(System.out::println);
    friends.stream().filter(NAME_STARTS_N_OR_R).forEach(System.out::println);
    long count =editors.stream().filter(checkIfStartWith("N")).count();
    long count2 = editors.stream().filter(startsWithLetter.apply("N")).count();
    System.out.println(count);
    System.out.println(count2);
  }

  public class NameStartsWithNOrR implements Predicate<String> {

    @Override
    public boolean test(String s) {
      return s.startsWith("S") || s.startsWith("R");
    }
  }

  final Predicate<String> NAME_STARTS_N_OR_R = s -> s.startsWith("S") || s.startsWith("R");

  final static Predicate<String> checkIfStartWith(String letter){
    return name -> name.startsWith(letter);
  }

  final Function<String, Predicate<String>>  startsWithLetter = letter -> name -> name.startsWith(letter);


  @Test
  public void testFound(){
    final List<String> friends = Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");
    assertEquals("Brian", pickName(friends.stream(), "B").orElse("Not found"));
  }

  public static Optional<String> pickName(Stream<String> names, String letter) {
    return names.filter(checkIfStartWith(letter)).findFirst();
  }

  @Test
  public void testReduce(){
    final List<String> friends = Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");
    int total = friends.stream().mapToInt(String::length).reduce(0, (a, b) -> a + b);
    System.out.println(total);

    String longerThanShangfei = friends.stream().reduce("shangfei", (name1, name2) -> name1.length() > name2.length() ? name1: name2);
    Optional<String> longest = friends.stream().reduce((name1, name2) -> name1.length() > name2.length() ? name1: name2);
    System.out.println(longest);

  }

  @Test
  public void testJoin(){
    final List<String> friends = Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");
    System.out.println(String.join(",", friends));
  }
}
