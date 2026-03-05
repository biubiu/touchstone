package com.shawn.touchstone.temporal.activity;

import com.shawn.touchstone.temporal.model.AccountStore;

public class UserOnboardingActivitiesImpl implements UserOnboardingActivities {

    @Override
    public void sendWelcomeEmail(String email, String name) {
        System.out.println("[EMAIL] Sending welcome email to " + email + " (name: " + name + ")");
    }

    @Override
    public void createAccount(String email, String name) {
        AccountStore.getInstance().create(email, name);
        System.out.println("[ACCOUNT] Created account for " + email);
    }

    @Override
    public void trackAnalytics(String userId, String event) {
        System.out.println("[ANALYTICS] Tracking event: " + event + " for user: " + userId);
    }

    @Override
    public void sendFollowUpEmail(String email, String name) {
        System.out.println("[EMAIL] Sending follow-up email to " + email);
    }
}
