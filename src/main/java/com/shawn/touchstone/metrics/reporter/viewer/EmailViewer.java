package com.shawn.touchstone.metrics.reporter.viewer;

import com.shawn.touchstone.metrics.models.RequestStat;
import com.shawn.touchstone.metrics.config.EmailSender;

import java.util.List;
import java.util.Map;

public class EmailViewer implements StatViewer {

    private EmailSender emailSender;
    private List<String> toAddrs;

    public EmailViewer(EmailSender emailSender, List<String> toAddrs) {
        this.emailSender = emailSender;
        this.toAddrs = toAddrs;
    }

    @Override
    public void output(Map<String, RequestStat> requestStats, long startTime, long endTime) {

    }
}
