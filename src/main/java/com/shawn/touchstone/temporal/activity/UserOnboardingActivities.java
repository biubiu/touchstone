package com.shawn.touchstone.temporal.activity;

import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface UserOnboardingActivities {
    void sendWelcomeEmail(String email, String name);
    void createAccount(String email, String name);
    void trackAnalytics(String userId, String event);
    void sendFollowUpEmail(String email, String name);
}
