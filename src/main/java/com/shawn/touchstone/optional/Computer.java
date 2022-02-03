package com.shawn.touchstone.optional;

import java.util.Optional;

public class Computer {

    private Optional<Soundcard> soundcard;

    public Optional<Soundcard> getSoundcard() {
        return soundcard;
    }

    public void setSoundcard(Optional<Soundcard> soundcard) {
        this.soundcard = soundcard;
    }

    public static String getSoundcardVersion(Computer computer){
        return computer.getSoundcard().flatMap(Soundcard::getUsb).map(USB::getVersion).orElse("UNKNOWN");
    }

    public static class Soundcard {
        private Optional<USB> usb;

        public Optional<USB> getUsb() {
            return usb;
        }

        public void setUsb(Optional<USB> usb) {
            this.usb = usb;
        }
    }


    public static class USB {
        String Version;

        public String getVersion() {
            return Version;
        }

        public void setVersion(String version) {
            Version = version;
        }
    }
}
