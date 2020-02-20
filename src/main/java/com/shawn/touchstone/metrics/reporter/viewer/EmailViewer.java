package com.shawn.touchstone.metrics.reporter.viewer;

import com.shawn.touchstone.metrics.models.RequestStat;
import com.shawn.touchstone.metrics.config.EmailSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmailViewer implements StatViewer {

    private EmailSender emailSender;
    private List<String> toAddrs;

    public EmailViewer() {
        this.emailSender = EmailSender.getInstance();
        this.toAddrs = new ArrayList<>();
    }


    @Override
    public void addRecipients(String email) {
        toAddrs.add(email);
    }

    @Override
    public void output(Map<String, RequestStat> requestStats, long startTime, long endTime) {

    }
}
