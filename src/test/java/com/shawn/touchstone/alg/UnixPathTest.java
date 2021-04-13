package com.shawn.touchstone.alg;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class UnixPathTest {

    @Test
    public void shouldReturnShortenPath() {
        String path = "/foo/../test/../test/../foo//bar/./baz";
        String expected = "/foo/bar/baz";
        assertThat(UnixPath.shortenPath(path), is(expected));
    }
}