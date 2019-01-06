package com.shawn.touchstone.optional;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by shangfei on 23/7/17.
 */
public class OptionalTest {

    @Test
    public void testComputerWithoutUSB(){
        Optional<Computer.Soundcard> sc = Optional.empty();
        Computer.Soundcard soundcard = new Computer.Soundcard();
        sc.ifPresent(System.out::println);
        assertEquals(Optional.empty(), sc);
        sc = Optional.of(new Computer.Soundcard());
        assertTrue(sc.isPresent());
        Computer computer = new Computer();
        computer.setSoundcard(Optional.empty());
        assertEquals("UNKNOWN",Computer.getSoundcardVersion(computer));
    }

    @Test
    public void testOptionalFilter(){
        Computer.USB usb = new Computer.USB();
        //usb.setVersion("3.0");
        Optional<Computer.USB> maybeUSB = Optional.of(usb);
        String version = maybeUSB.filter(o -> "3.0".equals(o.getVersion()))
                                .map(Computer.USB::getVersion)
                                .orElse("UNKNOWN");
        assertEquals("UNKNOWN", version);
    }

    @Test
    public void testOptionalFlat(){
        Optional<Computer> computer = Optional.of(new Computer());
        Optional<Computer.Soundcard> soundcard = Optional.of(new Computer.Soundcard());
        Optional<Computer.USB> usb = Optional.of(new Computer.USB());
        String version = computer.flatMap(Computer::getSoundcard)
                                .flatMap(o -> o.getUsb())
                                .map(Computer.USB::getVersion)
                                .orElse("UNKNOWN");
    }
}