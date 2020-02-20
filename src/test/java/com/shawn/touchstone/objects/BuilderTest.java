package com.shawn.touchstone.objects;

import com.shawn.touchstone.objects.builder.Calzone;
import com.shawn.touchstone.objects.builder.NyPizza;
import com.shawn.touchstone.objects.builder.NyPizza.Size;
import com.shawn.touchstone.objects.builder.Pizza.Topping;
import org.junit.Test;

public class BuilderTest {

  @Test
  public void testAbstractBuilder() {
    NyPizza nyPizza = new NyPizza.Builder(Size.Small).addTopping(Topping.Pepper).build();
    Calzone zone = new Calzone.Builder().addTopping(Topping.Ham).sauceInside().build();
  }

}
