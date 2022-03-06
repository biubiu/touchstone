package com.shawn.touchstone.alg;


import static org.hamcrest.core.Is.is;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class SnakeGameTest {

    private SnakeGame snakeGame;

    /**
     *  - 1 -
     *  - - 2
     *  - - -
     */
    @Before
    public void create() {
        int[][] food = {{0, 1}, {1, 2}};
        snakeGame = new SnakeGame(3, 3, food);
    }

    @Test
    public void whenOutBoundShouldReturn(){
        assertThat(snakeGame.move("U"), is(-1));
    }

    @Test
    public void whenEatFoodShouldAddScore() {
        assertThat(snakeGame.move("R"), is(1));
        assertThat(snakeGame.move("D"), is(1));
        assertThat(snakeGame.move("R"), is(2));
        assertThat(snakeGame.move("R"), is(-1));
    }

    @Test
    public void whenOverlappedShouldReturnNegativeOne() {

    }
}