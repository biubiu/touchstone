package com.shawn.touchstone.snapshot;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class InputTextTest {


    @Test
    public void replaceInputTextShouldReturn() {
        InputText input = new InputText();
        InputText.SnapshotHolder snapshotHolder = new InputText.SnapshotHolder();

        String first = "first";
        String second = "second";

        snapshotHolder.pushSnapshot(input.createSnapshot());
        input.append(first);

        snapshotHolder.pushSnapshot(input.createSnapshot());
        input.append(second);

        assertThat(input.getText(), is(first + second));

        input.restoreSnapshot(snapshotHolder.popSnapshot());
        assertThat(input.getText(), is(first));
    }
}
