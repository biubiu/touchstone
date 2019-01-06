package com.shawn.touchstone.reactor;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.System.out;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReactorTest {

  public static final java.util.function.BiFunction<String, Integer, String> STRING_INTEGER_STRING_BI_FUNCTION = (str, count) -> String
      .format("%2d. %s", count, str);

  private static List<String> words = Arrays.asList(
      "the",
      "quick",
      "brown",
      "fox",
      "jumped",
      "over",
      "the",
      "lazy",
      "dog"
  );

  @Test
  public void simpleCreation() {
    Flux<String> fewWords = Flux.just("Hello", "world");
    Flux<String> manyWords = Flux.fromIterable(words);

    fewWords.subscribe(out::println);
    out.println("--------------------");
    manyWords.subscribe(out::println);
  }

  @Test
  public void printAllAndConcatS() {
    Mono<String> missing = Mono.just("s");
    Flux<String> allLetters = Flux.fromIterable(words).flatMap(word -> Flux.fromArray(word.split("")))
                                  .concatWith(missing)
                                  .distinct().sort()
                                  .zipWith(Flux.range(0, MAX_VALUE),
                                      STRING_INTEGER_STRING_BI_FUNCTION);

    allLetters.subscribe(out::println);
  }

  @Test
  public void shortCircuit() throws InterruptedException {
    Flux<String> helloPausedWord = Mono.just("Hello").concatWith(Mono.just("world").delaySubscriptionMillis(500));
    //helloPausedWord.subscribe(out::println);
    //Thread.sleep(510);
    helloPausedWord.toStream().forEach(out::println);
  }

  @Test
  public void testFirstEmitting() {
    Mono<String> a = Mono.just("oops I'm late ").delaySubscriptionMillis(450);
    Flux<String> b = Flux.just("let's get", "the party", "started").delaySubscriptionMillis(400);
    Flux.firstEmitting(a, b).toIterable().forEach(out::println);

  }

  public static class CountDownSubscriber<T> implements Subscriber<T>{

    private final CountDownLatch latch;

    public CountDownSubscriber(int count) {
       latch = new CountDownLatch(count);
    }

    @Override
    public void onSubscribe(Subscription s) {
      out.println();
    }

    @Override
    public void onNext(T t) {
      latch.countDown();
    }

    @Override
    public void onError(Throwable t) {
      try {
        latch.await();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    @Override
    public void onComplete() {
      try {
        latch.await();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

}
