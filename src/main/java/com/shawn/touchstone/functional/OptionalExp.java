package com.shawn.touchstone.functional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class OptionalExp {

    public class Person {
        private Optional<Car> car;
        public Optional<Car> getCar() { return car; }
    }
    public static class Car {
        private Optional<Insurance> insurance;
        public Optional<Insurance> getInsurance() { return insurance; }
    }
    public static class Insurance {
        private String name;
        public String getName() { return name; }
    }

    public static void main(String[] args) {
        Car car = new Car();
        Insurance insurance = new Insurance();
        Optional<Car> optCar = Optional.of(car);
        Optional<Car> optC = Optional.ofNullable(car);
        Optional<Insurance> optInsurance = Optional.ofNullable(insurance);
        Optional<String> name = optInsurance.map(Insurance::getName);
    }

    public String getCarInsuranceName(Optional<Person> person) {
        return person.flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .orElse("UNKNOWN");
    }

    public Set<String> getCarInsuranceName(List<Person> people) {
        return people.stream().map(Person::getCar).map(car -> car.flatMap(Car::getInsurance))
                .map(insurance -> insurance.map(Insurance::getName))
                .flatMap(Optional::stream)
                .collect(Collectors.toSet());
    }
}
