package com.shawn.touchstone.reactor;


import static java.lang.Integer.MAX_VALUE;
import static java.lang.System.out;
import static java.util.stream.Collectors.toList;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class Rx2Test {

  public static final BiFunction<String, Integer, String> PRINT_STR = (str, count) -> String.format("%d. %s", count, str);

  public static final Function<String, String> MAPPING = o -> o.concat(" ").concat(
      RandomStringUtils.randomAlphabetic(6));

  private static long start = System.currentTimeMillis();

  @Test
  public void testBasicOperations() {

    out.println("-----------just---------");

    Observable.just("Hello", "Howdy").map(
        MAPPING)
        .subscribe(out::println);

    out.println("-----------from---------");

    List<String> words = Arrays.asList("What's ", "up", "Lup");
    Observable.fromIterable(words)
        .map(MAPPING)
        .subscribe(out::println);

    out.println("---------zip with int stream------------");
    Observable.fromIterable(words)
        .zipWith(IntStream.range(0, words.size()).boxed().collect(toList()),
            PRINT_STR)
        .subscribe(out::println);

    out.println("---------zip with observable stream-----------");
    Observable.fromIterable(words).flatMap(word -> Observable.fromArray(word.split("")))
        .zipWith(Observable.range(0, MAX_VALUE), PRINT_STR)
        .subscribe(out::println);

    out.println("---------zip with range limited range-----------");
    Observable.fromIterable(words).flatMap(word -> Observable.fromArray(word.split("")))
        .zipWith(Observable.range(0, 9), PRINT_STR)
        .subscribe(out::println);

    out.println("---------zip with sorted and distinction-----------");
    Observable.fromIterable(words).flatMap(word -> Observable.fromArray(word.split("")))
                        .distinct()
                        .sorted()
                        .zipWith(Observable.range(0, MAX_VALUE),
                            PRINT_STR).subscribe(out::println);



  }

  @Test
  public void testMerge() throws InterruptedException {
    Observable<Long> fast = Observable.interval(1, TimeUnit.SECONDS);
    Observable<Long> slow = Observable.interval(3, TimeUnit.SECONDS);
    Observable<Long> clock = Observable.merge(slow.filter(tick -> isSlowTickTime()),
        fast.filter(tick -> !isSlowTickTime()));

    clock.subscribe(tick -> out.println(new Date()));

    Thread.sleep(60_000);
  }


  // cold provides static data and only pumps when there is a subscriber and every subscriber receive
  // the same historical data; while hot pump only the latest data
//  @Test
//  public void testColdAndHotObservable() {
//    SomeFeed<PriceTick> feed = new SomeFeed<>();
//
//    Observable<PriceTick> obs = Observable.create(new ObservableOnSubscribe() {
//      @Override
//      public void subscribe(ObservableEmitter emitter) throws Exception {
//
//      }
//    }, BackpressureStrategy.BUFFER));
//  }
  private static boolean isSlowTickTime() {
    return (System.currentTimeMillis() - start) % 30_000 >= 15_000;
  }


  public void testOb() {
    Subscriber light = new Subscriber() {
      @Override
      public void onSubscribe(Subscription s) {

      }

      @Override
      public void onNext(Object o) {
        out.println("observing next, handling " + o);
      }

      @Override
      public void onError(Throwable t) {
        out.println("observing Errors ");
      }

      @Override
      public void onComplete() {
        out.println("observing Completed ");
      }
    };
  }
}
