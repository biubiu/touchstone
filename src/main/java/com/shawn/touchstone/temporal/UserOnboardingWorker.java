package com.shawn.touchstone.temporal;

import com.shawn.touchstone.temporal.activity.UserOnboardingActivities;
import com.shawn.touchstone.temporal.activity.UserOnboardingActivitiesImpl;
import com.shawn.touchstone.temporal.workflow.UserOnboardingWorkflow;
import com.shawn.touchstone.temporal.workflow.UserOnboardingWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;

public class UserOnboardingWorker {
    
    public static void main(String[] args) {
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        
        WorkflowClient client = WorkflowClient.newInstance(service);
        
        WorkerFactory factory = WorkerFactory.newInstance(client);
        
        Worker worker = factory.newWorker("user-onboarding-task-queue");
        
        worker.registerWorkflowImplementationTypes(UserOnboardingWorkflowImpl.class);
        worker.registerActivitiesImplementations(new UserOnboardingActivitiesImpl());
        
        factory.start();
        
        System.out.println("Worker started. Press Ctrl+C to exit.");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            factory.shutdown();
            service.shutdown();
        }));
    }
}
