package com.shawn.touchstone.tdd.marsrover;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.lang.String.format;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShipSpec {

    private Point point;

    private Location location;

    private Ship ship;

    private Point maxPoint;

    private Planet planet;

    private List<Point> obstacles;

    @BeforeEach
    public void setup() {
        point = new Point(1, 1);
        location = new Location(point, Direction.NORTH);
        maxPoint = new Point(4, 4);
        obstacles = Lists.newArrayList(new Point(3, 3), new Point(2, 3));
        planet = new Planet(maxPoint, obstacles);
        ship = new Ship(location, planet);
    }

    @Test
    public void whenShipCreatedThenLocationIsSet() {
        assertThat(ship.getLocation(), is(location));
    }

    @Test
    public void whenShipCreatedThenPlanetIsSet() {
        assertThat(ship.getPlanet(), is(planet));
        assertThat(ship.getPlanet().getMax(), is(maxPoint));
        assertThat(ship.getPlanet().getObstacles(), is(obstacles));
    }

    @Test
    public void whenReceivingLCommandThenTurnLeft() {
        ship.execute("L");
        assertThat(ship.getLocation().getDirection(), is(Direction.WEST));
    }

    @Test
    public void whenReceivingRCommandThenTurnRight() {
        ship.execute("R");
        assertThat(ship.getLocation().getDirection(), is(Direction.EAST));
    }

    @Test
    public void whenReceivingFCommandThenLocationChange() {
        ship.execute("F");
        assertThat(ship.getLocation().getPoint(), is(new Point(1, 2)));
    }

    @Test
    public void whenReceivingBCommandThenLocationChange() {
        ship.execute("B");
        assertThat(ship.getLocation().getPoint(), is(new Point(1, 0)));
    }

    @Test
    public void whenReceivingAListOfCommandsThenLocationChangeAccordingly() {
      ship.execute("RFFLF");
      assertThat(ship.getLocation().getPoint(), is(new Point(3, 2)));
        assertThat(ship.getLocation().getDirection(), is(Direction.NORTH));
    }

    @Test
    public void whenExceedingMaxThenLocationRemainAtEdge() {
        ship.execute("FFFF");
        assertThat(ship.getLocation().getPoint(), is (new Point(1, 4)));
    }

    @Test
    public void whenCollideWithObstaclesShouldThrowRuntimeException() {
        RuntimeException exp = assertThrows(RuntimeException.class, () -> ship.execute("FFRF"));
        assertThat(exp.getMessage(), is(format("The point %s collides with obstacle", new Point(2, 3))));
    }
}