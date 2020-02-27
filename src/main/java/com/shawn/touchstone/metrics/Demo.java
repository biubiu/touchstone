package com.shawn.touchstone.metrics;

import com.google.gson.Gson;
import com.shawn.touchstone.metrics.models.RequestInfo;
import com.shawn.touchstone.metrics.reporter.ConsoleReporter;
import com.shawn.touchstone.metrics.reporter.EmailReporter;
import com.shawn.touchstone.metrics.storage.MetricsStorage;
import com.shawn.touchstone.metrics.storage.RedisMetricsStorage;

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
        ConsoleReporter consoleReporter = new ConsoleReporter();
        consoleReporter.startRepeatedReport(60, 60);

        EmailReporter emailReporter = new EmailReporter();
        emailReporter.addToAddress("wangzheng@xzg.com");
        emailReporter.startDailyReport();

        MetricsCollector collector = new MetricsCollector();
        collector.recordRequest(new RequestInfo("register", 123.0, 10234l));
        collector.recordRequest(new RequestInfo("register", 223.1, 11234l));
        collector.recordRequest(new RequestInfo("register", 323.0, 12334l));
        collector.recordRequest(new RequestInfo("login", 23.0, 12434l));
        collector.recordRequest(new RequestInfo("login", 1223.0, 14234l));

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
