package com.shawn.touchstone.alg;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

public class PhoneNumberMnemonicsTest {

    private PhoneNumberMnemonics phoneNumberMnemonics;

    @Before
    public void init() {
        phoneNumberMnemonics = new PhoneNumberMnemonics();
    }

    @Test
    public void givenNumShouldReturnAllSubsets() {
        List<String> generated = phoneNumberMnemonics.generate("1905");
        assertThat(generated, hasSize(12));
        assertThat(generated, containsInAnyOrder("1w0j", "1w0k", "1w0l", "1x0j", "1x0k", "1x0l", "1y0j", "1y0k", "1y0l", "1z0j", "1z0k",
                "1z0l"));
    }
}