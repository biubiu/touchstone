package com.shawn.touchstone.alg;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class FileSizeTest {

    @Test
    public void processFiles() {
        FileSys fileSys = new FileSys();
        fileSys.addFile("a", 1, "c1");
        fileSys.addFile("b", 2, "c2");
        fileSys.addFile("c", 3, "c2");
        fileSys.addFile("d", 4, "c1");
        fileSys.addFile("e", 5, null);
        List<FileSys.Collection> collections = fileSys.topK(2);
        assertEquals(2, collections.size());
        assertThat( collections.get(0).getName(), is("c2"));
        assertThat( collections.get(0).getSize(), is(5));
        assertThat( collections.get(1).getName(), is("c1"));
        assertThat( collections.get(1).getSize(), is(5));
    }
}