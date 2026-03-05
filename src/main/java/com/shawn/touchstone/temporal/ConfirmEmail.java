package com.shawn.touchstone.temporal;

import com.shawn.touchstone.temporal.workflow.UserOnboardingWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowStub;
import io.temporal.serviceclient.WorkflowServiceStubs;

public class ConfirmEmail {
    
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: ConfirmEmail <workflow-id>");
            return;
        }
        
        String workflowId = args[0];
        
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowClient client = WorkflowClient.newInstance(service);
        
        UserOnboardingWorkflow workflow = client.newWorkflowStub(
            UserOnboardingWorkflow.class,
            workflowId
        );
        
        WorkflowStub.fromTyped(workflow).signal("confirmEmail", "dummy-token");
        
        System.out.println("Email confirmed for workflow: " + workflowId);
        
        service.shutdown();
    }
}
