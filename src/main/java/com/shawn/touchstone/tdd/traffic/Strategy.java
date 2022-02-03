package com.shawn.touchstone.tdd.traffic;

import com.google.common.annotations.VisibleForTesting;

import java.util.Comparator;
import java.util.List;
import java.util.function.UnaryOperator;

import static com.shawn.touchstone.tdd.traffic.Strategy.Vehicle.BIKE;
import static com.shawn.touchstone.tdd.traffic.Strategy.Vehicle.CAR;
import static com.shawn.touchstone.tdd.traffic.Strategy.Vehicle.TUKTUK;

public class Strategy {
    List<Orbit> orbits;

    public Strategy(List<Orbit> orbits) {
        this.orbits = orbits;
    }

    public Winner winner(Weather weather) {
        return orbits.stream().map(orbit -> time(orbit, weather)).min(Comparator.comparingDouble(v -> v.time)).orElseThrow(RuntimeException::new);
    }

    @VisibleForTesting
    Winner time(Orbit orbit, Weather weather) {
        double crater = weather.getSpeed(orbit.craters());
        int distance = orbit.distance();
        int maxSpeed = orbit.maxSpeed();
        Vehicle vehicle = weather.vehicles.stream().max(Comparator.comparingDouble(v -> v.time(distance, maxSpeed, crater))).orElseThrow(RuntimeException::new);
        return new Winner(orbit, vehicle, vehicle.time(distance, maxSpeed, crater));
    }

    enum Weather {
        SUNNY(List.of(CAR, BIKE, TUKTUK), crater -> crater * 0.9),
        RAINY(List.of(CAR, TUKTUK), crater -> crater * 1.2),
        WINDY(List.of(CAR, BIKE), crater -> crater);

        private final UnaryOperator<Double> craterOpt;
        private final List<Vehicle> vehicles;

        Weather(List<Vehicle> vehicles, UnaryOperator<Double> craterOpt) {
            this.craterOpt = craterOpt;
            this.vehicles = vehicles;
        }

        public double getSpeed(double speed) {
            return craterOpt.apply(speed);
        }

    }

    public enum Vehicle {
        BIKE(10, 2),
        TUKTUK(12, 1),
        CAR(20, 3);

        private final int speed;
        private final int speedForCraters;

        Vehicle(int speed, int speedForCraters) {
            this.speed = speed;
            this.speedForCraters = speedForCraters;
        }

        public double time(int distance, int maxSpeed, double craters) {
            return distance * Math.min(maxSpeed, this.speed) + craters * this.speedForCraters;
        }
    }

    public record Winner(Orbit orbit, Vehicle vehicle, double time) {

    }

}
