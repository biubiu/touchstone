package com.shawn.touchstone.snapshot;

import java.util.Stack;

public class InputText {

    private StringBuilder text = new StringBuilder();

    public void append(String txt) {
        text.append(txt);
    }

   public Snapshot createSnapshot() {
        return new Snapshot(text.toString());
   }

   public void restoreSnapshot(Snapshot snapshot) {
        this.text.replace(0, this.text.length(), snapshot.getText());
   }

    public String getText() {
        return text.toString();
    }

    private static class Snapshot {
        private String txt;

        public Snapshot(String txt) {
            this.txt = txt;
        }

        public String getText() {
            return txt;
        }
    }
    static class SnapshotHolder {
        private Stack<Snapshot> snapshots = new Stack<>();

        public Snapshot popSnapshot() {
            return snapshots.pop();
        }

        public void pushSnapshot(Snapshot snapshot) {
            snapshots.push(snapshot );
        }
    }
}
