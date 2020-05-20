package com.shawn.touchstone.utj2.ch10;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;

import static com.shawn.touchstone.utj2.ch10.ContainsMatches.containsMatches;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SearchTest {

    private InputStream stream;
    @Before
    public void turnoffLoggin() {
        Search.LOGGER.setLevel(Level.OFF);
    }

    @After
    public void closeSrc() throws IOException {
        stream.close();
    }
    @Test
    public void returnsMatchesShowingContextWhenSearchStrInContent() throws IOException {
        String pageContent = "There are certain queer times and occasions "
                + "in this strange mixed affair we call life when a man "
                + "takes this whole universe for a vast practical joke, "
                + "though the wit thereof he but dimly discerns, and more "
                + "than suspects that the joke is at nobody's expense but "
                + "his own.";
        // ...
        stream = streamOn(pageContent);
        // search
        Search search = new Search(stream, "practical joke", "1");
        Search.LOGGER.setLevel(Level.OFF);
        search.setSurroundingCharacterCount(10);

        search.execute();

        assertFalse(search.errored());
        assertThat(search.getMatches(), containsMatches(new Match[]{
                new Match("1", "practical joke",
                        "or a vast practical joke, though t")
        }));
//        List<Match> matches = search.getMatches();
//        assertTrue(matches.size() >= 1);
//        Match match = matches.get(0);
//        assertThat(match.searchString, is("practical joke"));
//        assertThat(match.surroundingContext, is(
//                "or a vast practical joke, though t"));

    }

    @Test
    public void noMatchesReturnedWhenSearchStringNotInContent() throws IOException {
        stream = streamOn("any text");
        Search search = new Search(stream, "smelt", "http://bit.ly/15sYPA7");

        search.execute();

        assertThat(search.getMatches().size(), is(0));
    }

    @Test
    public void returnsErroredWhenUnableToReadSteram() {
        stream = createStreamThrowingErrorWhenRead();
        Search search = new Search(stream, "", "");

        search.execute();

        assertTrue(search.errored());
    }

    private InputStream streamOn(String pageContent) {
        return new ByteArrayInputStream(pageContent.getBytes());
    }

    private InputStream createStreamThrowingErrorWhenRead(){
        return new InputStream() {
            @Override
            public int read() throws IOException {
                throw new IOException();
            }
        };
    }

}