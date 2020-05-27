package com.shawn.touchstone.tdd.marsrover;

import static java.lang.String.format;

public class Ship {

    private Location location;

    private Planet planet;

    public Ship(Location location, Planet planet) {
        this.location = location;
        this.planet = planet;
    }

    public Location getLocation() {
        return location;
    }

    public void execute(String commands) {
        char[] chs = commands.trim().toUpperCase().toCharArray();
        for (char ch : chs) {
            switch (ch) {
                case 'L': location.turnLeft(); break;
                case 'R': location.turnRight(); break;
                case 'F':
                    if (!location.forward(planet.getMax(), planet.getObstacles())) {
                        throw new RuntimeException(format("The point %s collides with obstacle",location.getPoint()));
                    }
                    break;
                case 'B':
                    if (!location.backward(planet.getMax(), planet.getObstacles())) {
                        throw new RuntimeException(format("The point %s collides with obstacle",location.getPoint()));
                    }
                    break;
            }
        }
    }

    public Planet getPlanet() {
        return planet;
    }
}
