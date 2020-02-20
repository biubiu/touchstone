package com.shawn.touchstone.metrics;

import com.google.gson.Gson;
import com.shawn.touchstone.metrics.models.RequestInfo;
import com.shawn.touchstone.metrics.reporter.ConsoleReporter;
import com.shawn.touchstone.metrics.reporter.EmailReporter;
import com.shawn.touchstone.metrics.reporter.viewer.EmailViewer;
import com.shawn.touchstone.metrics.reporter.viewer.StatViewer;
import com.shawn.touchstone.metrics.reporter.viewer.ConsoleViewer;
import com.shawn.touchstone.metrics.config.EmailSender;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Demo {

    //todo passive triggering metrics -> rest interface
    //todo customized format for console and email
    //todo  in-mem storage
    //todo redis, email intializing (intializing configures from configuration files and then init those instances)
    public static void main(String[] args) {
        Gson gson = new Gson();
        MetricsStorage storage = new RedisMetricsStorage();
        Aggregator aggregator = new Aggregator();
        StatViewer console = new ConsoleViewer(gson);
        StatViewer email = new EmailViewer(new EmailSender(), new ArrayList<>());
        ConsoleReporter consoleReporter = new ConsoleReporter(storage, aggregator, console);
        consoleReporter.startRepeatedReport(60, 60);

        EmailReporter emailReporter = new EmailReporter(storage, aggregator, email);
        emailReporter.addToAddress("wangzheng@xzg.com");
        emailReporter.startDailyReport();

        MetricsCollector collector = new MetricsCollector();
        collector.recordRequest(new RequestInfo("register", 123.0, 10234));
        collector.recordRequest(new RequestInfo("register", 223.1, 11234));
        collector.recordRequest(new RequestInfo("register", 323.0, 12334));
        collector.recordRequest(new RequestInfo("login", 23.0, 12434));
        collector.recordRequest(new RequestInfo("login", 1223.0, 14234));

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
