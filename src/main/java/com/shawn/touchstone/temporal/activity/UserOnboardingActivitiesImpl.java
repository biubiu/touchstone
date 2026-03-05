package com.shawn.touchstone.temporal.activity;

import com.shawn.touchstone.temporal.model.AccountStore;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserOnboardingActivitiesImpl implements UserOnboardingActivities {
    
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public void sendWelcomeEmail(String email, String name) {
        executor.submit(() -> 
            System.out.println("[EMAIL] Sending welcome email to " + email + " (name: " + name + ")")
        );
    }

    @Override
    public void createAccount(String email, String name) {
        executor.submit(() -> {
            AccountStore.getInstance().create(email, name);
            System.out.println("[ACCOUNT] Created account for " + email);
        });
    }

    @Override
    public void trackAnalytics(String userId, String event) {
        executor.submit(() -> 
            System.out.println("[ANALYTICS] Tracking event: " + event + " for user: " + userId)
        );
    }

    @Override
    public void sendFollowUpEmail(String email, String name) {
        executor.submit(() -> 
            System.out.println("[EMAIL] Sending follow-up email to " + email)
        );
    }
}
