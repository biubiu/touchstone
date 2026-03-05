package com.shawn.touchstone.temporal.workflow;

import com.shawn.touchstone.temporal.activity.UserOnboardingActivities;
import com.shawn.touchstone.temporal.model.OnboardingRequest;
import com.shawn.touchstone.temporal.model.OnboardingStatus;
import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;

import java.time.Duration;

public class UserOnboardingWorkflowImpl implements UserOnboardingWorkflow {

    private OnboardingStatus status = OnboardingStatus.PENDING_EMAIL_CONFIRMATION;

    private final UserOnboardingActivities activities = Workflow.newActivityStub(
        UserOnboardingActivities.class,
        ActivityOptions.newBuilder()
            .setStartToCloseTimeout(Duration.ofMinutes(5))
            .build()
    );

    @Override
    public void start(OnboardingRequest request) {
        activities.sendWelcomeEmail(request.getEmail(), request.getName());
        
        Workflow.await(() -> status != OnboardingStatus.PENDING_EMAIL_CONFIRMATION);

        activities.createAccount(request.getEmail(), request.getName());
        status = OnboardingStatus.PENDING_ACCOUNT_CREATION;

        activities.trackAnalytics(request.getUserId(), "account_created");

        Workflow.sleep(Duration.ofSeconds(30));

        activities.sendFollowUpEmail(request.getEmail(), request.getName());

        status = OnboardingStatus.COMPLETED;
    }

    @Override
    public void confirmEmail(String token) {
        if (status == OnboardingStatus.PENDING_EMAIL_CONFIRMATION) {
            status = OnboardingStatus.PENDING_ACCOUNT_CREATION;
        }
    }

    @Override
    public OnboardingStatus getStatus() {
        return status;
    }
}
