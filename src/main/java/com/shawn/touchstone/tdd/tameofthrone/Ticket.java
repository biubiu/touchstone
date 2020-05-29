package com.shawn.touchstone.tdd.tameofthrone;

import com.google.common.base.MoreObjects;

import java.util.Objects;

public class Ticket {
    private String msg;
    private Kingdom recipient;
    private Kingdom sender;

    public Ticket(Kingdom sender, String msg, Kingdom recipient) {
        this.msg = msg;
        this.recipient = recipient;
        this.sender = sender;
    }

    public String getMsg() {
        return msg;
    }

    public Kingdom getRecipient() {
        return recipient;
    }

    public Kingdom getSender() {
        return sender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return msg.equals(ticket.msg) &&
                recipient == ticket.recipient &&
                sender == ticket.sender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(msg, recipient, sender);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("msg", msg)
                .add("recipient", recipient)
                .add("sender", sender)
                .toString();
    }
}
