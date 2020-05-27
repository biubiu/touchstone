package com.shawn.touchstone.tdd.marsrover;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class LocationSpec {

    private Location location;

    private Point point;

    private Point max;
    @Rule
    public ExpectedException ee = ExpectedException.none();

    @Before
    public void setup() {
        point = new Point(1, 1);
        max = new Point(2, 2);
        location = new Location(point, Direction.NORTH);
    }
    @Test
    public void whenInputIsNThenLocationIsN() {
        assertThat(location.getPoint(), is(point));
        assertThat(location.getDirection(), is(Direction.NORTH));
    }

    @Test
    public void givenDirectionIsNorthWhenTurnLeftThenDirectionIsWest() {
        location.turnLeft();
        assertThat(location.getDirection(), is(Direction.WEST));
    }

    @Test
    public void givenDirectionIsEastWhenTurnLeftThenDirectionIsSouth() {
        location.turnLeft();
        location.turnLeft();
        assertThat(location.getDirection(), is(Direction.SOUTH));
    }

    @Test
    public void givenDirectionIsNorthWhenTurnRightThenDirectionIsEast() {
        location.turnRight();
        assertThat(location.getDirection(), is(Direction.EAST));
    }

    @Test
    public void givenDirectionIsNorthWhenMoveForwardThenPositionIsSet() {
        location.forward(max);
        assertThat(location.getPoint(), is(new Point(1, 2)));
    }

    @Test
    public void givenDirectionIsEastWhenMoveForwardThenPositionIsSet() {
        location.turnRight();
        location.forward(max);
        assertThat(location.getPoint(), is(new Point(2, 1)));
    }

    @Test
    public void givenDirectionIsSouthWhenMoveForwardThenPositionIsSet(){
        location.turnRight();
        location.turnRight();
        location.forward(max);
        assertThat(location.getPoint(), is(new Point(1, 0)));
    }

    @Test
    public void givenDirectionIsWestWhenMoveForwardThenPositionIsSet(){
        location.turnRight();
        location.turnRight();
        location.turnRight();
        location.forward(max);
        assertThat(location.getPoint(), is(new Point(0, 1)));
    }

    @Test
    public void whenMoveOverMinThenShouldResetToZero() {
        location.turnRight();
        location.turnRight();
        location.forward(max);
        location.forward(max);
        assertThat(location.getPoint(), is(new Point(1, 0)));
    }

    @Test
    public void whenMoveOverMaxThenShouldResetToMax() {
        location.forward(max);
        location.forward(max);
        location.forward(max);
        assertThat(location.getPoint(), is(new Point(1, 2)));
    }

    @Test
    public void givenObstaclesThenShouldReturnFalse() {
        List<Point> obstacles = Lists.newArrayList(new Point(1, 2));
        assertFalse(location.forward(max, obstacles));
    }

    @Test
    public void whenMoveBackwardThenPositionIsSet() {
        location.backward(max);
        assertThat(location.getPoint(), is(new Point(1, 0)));
    }
}