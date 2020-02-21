package com.shawn.touchstone.metrics.reporter;

import com.google.common.annotations.VisibleForTesting;
import com.shawn.touchstone.metrics.Aggregator;
import com.shawn.touchstone.metrics.storage.MemMetricsStorage;
import com.shawn.touchstone.metrics.storage.MetricsStorage;
import com.shawn.touchstone.metrics.storage.RedisMetricsStorage;
import com.shawn.touchstone.metrics.reporter.viewer.EmailViewer;
import com.shawn.touchstone.metrics.reporter.viewer.StatViewer;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class EmailReporter extends ScheduledReporter {
    private static final Long DAY_HOURS_IN_SECONDS = 86400L;

    public EmailReporter() {
        this(new MemMetricsStorage(), new Aggregator(), new EmailViewer());
    }
    public EmailReporter(MetricsStorage metricsStorage, Aggregator aggregator, StatViewer statViewer) {
        super(metricsStorage, aggregator, statViewer);
    }

    public void addToAddress(String address) {
        super.viewer.addRecipients(address);
    }

    public void startDailyReport() {
        Date firstTime = trimTimeFieldsToZeroForNextDay(new Date());
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                long durationInMillis = DAY_HOURS_IN_SECONDS * 1000;
                long endTimeInMillis = System.currentTimeMillis();
                long startTimeInMillis = endTimeInMillis - durationInMillis;
                doStatAndReport(startTimeInMillis, endTimeInMillis);
            }
        }, firstTime, DAY_HOURS_IN_SECONDS * 1000);
    }

    @VisibleForTesting
    protected Date trimTimeFieldsToZeroForNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

}
