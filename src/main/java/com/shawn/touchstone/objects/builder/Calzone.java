package com.shawn.touchstone.objects.builder;

public class Calzone extends Pizza {

  private final Boolean sourceInside;

  public static class Builder extends Pizza.Builder<Builder> {

    private boolean sauceInside = false; // Default


    public Builder sauceInside() {
      sauceInside = true;
      return this;
    }


    @Override
    public Calzone build() {
      return new Calzone(this);
    }


    @Override
    protected Builder self() {
      return this;
    }
  }

  private Calzone(Builder builder) {
    super(builder);
    sourceInside = builder.sauceInside;
  }
}
