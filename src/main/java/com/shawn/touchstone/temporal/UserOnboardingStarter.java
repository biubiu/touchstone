package com.shawn.touchstone.temporal;

import com.shawn.touchstone.temporal.model.OnboardingRequest;
import com.shawn.touchstone.temporal.workflow.UserOnboardingWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;

import java.util.UUID;

public class UserOnboardingStarter {

    public static void main(String[] args) throws Exception {
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        
        WorkflowClient client = WorkflowClient.newInstance(service);
        
        String workflowId = "user-onboarding-" + UUID.randomUUID();
        
        UserOnboardingWorkflow workflow = client.newWorkflowStub(
            UserOnboardingWorkflow.class,
            WorkflowOptions.newBuilder()
                .setTaskQueue("user-onboarding-task-queue")
                .setWorkflowId(workflowId)
                .build()
        );
        
        OnboardingRequest request = new OnboardingRequest(
            "user-" + UUID.randomUUID(),
            "john.doe@example.com",
            "John Doe"
        );
        
        System.out.println("Starting workflow for: " + request.getEmail());
        WorkflowClient.start(workflow::start, request);
        
        System.out.println("Workflow started with ID: " + workflowId);
        System.out.println("Waiting for email confirmation...");
        System.out.println("To confirm email, run confirm-email with: " + workflowId);
        
        Thread.sleep(60000);
        
        service.shutdown();
    }
}
