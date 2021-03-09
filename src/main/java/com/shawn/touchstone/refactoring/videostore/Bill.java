package com.shawn.touchstone.refactoring.videostore;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Bill {

  public String statement(Invoice invoice, List<Play> plays) {
    return renderPlayText(invoice, plays);
  }
  public String renderPlayText(Invoice invoice, List<Play> plays) {
    int totalAmount = 0;
  int volumeCredits = 0;
    StringBuilder result = new StringBuilder();
    for (Entry<String, Integer> playAudiences : invoice.performaces.entrySet()) {
      int audience = playAudiences.getValue();
      String name = playAudiences.getKey();
      Play play = plays.stream().filter(o -> name.equals(o.name)).findAny().get();
      int thisAmount = amountFor(audience, play);
      volumeCredits += volumeCreditsFor(audience, name, play);

      result.append(format(name, thisAmount, audience));
      totalAmount += thisAmount;
    }
    result.append("Amount owed is ").append(totalAmount / 100).append("\n");
    result.append("You earned ").append(volumeCredits).append("credits");
    return result.toString();
  }

  private String format(String name, int amount, int audience) {
    return String.format("%s : %s (%s) seats \n", name, amount / 100, audience);
  }

  private int volumeCreditsFor(int audience, String name, Play play) {
    int result = 0;
    result += Math.max(audience - 30, 0);
    if ("comedy" == play.type) {
      result += Math.floor(audience / 5);
    }
    return result;
  }

  private int amountFor(int audience, Play play) {
    int result;
    switch (play.type) {
      case "tragedy":
        result = 40000;
        if (audience > 30) {
          result += 1000 * (audience - 30);
        }
        break;
      case "comedy":
        result = 30000;
        if (audience > 20) {
          result += 10000 + 500 * (audience - 20);
        }
        result += 300 * audience;
        break;
      default:
        throw new RuntimeException("no such play");
    }
    return result;
  }

  public static class Invoice {

    String customers;
    Map<String, Integer> performaces;
  }

  public static class Play {

    String name;
    String type;
  }
}
