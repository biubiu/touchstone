package com.shawn.touchstone.alg;

import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;

public class UnixPathTest {

    @Test
    public void shouldReturnShortenPath() {
        String path = "/foo/../test/../test/../foo//bar/./baz";
        String expected = "/foo/bar/baz";
        assertThat(UnixPath.shortenPath(path), is(expected));
    }
}