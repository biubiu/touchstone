package com.shawn.touchstone.objects.builder;

import java.util.EnumSet;
import java.util.Set;

public abstract class Pizza {

  public enum Topping {
    Ham, Mushroom, Onion, Pepper
  }

  Set<Topping> toppings;

  // builder is a recursive type parameters
  abstract static class Builder<T extends Builder<T>> {

    EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);

    public T addTopping(Topping topping) {
      toppings.add(topping);
      return self();
    }

    abstract Pizza build();

    protected abstract T self();
  }

  Pizza(Builder<?> builder) {
    toppings = builder.toppings.clone();
  }
}


