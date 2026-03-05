package com.shawn.touchstone.temporal.workflow;

import com.shawn.touchstone.temporal.model.OnboardingRequest;
import com.shawn.touchstone.temporal.model.OnboardingStatus;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.QueryMethod;

@WorkflowInterface
public interface UserOnboardingWorkflow {

    @WorkflowMethod
    void start(OnboardingRequest request);

    @SignalMethod
    void confirmEmail(String token);

    @QueryMethod
    OnboardingStatus getStatus();
}
