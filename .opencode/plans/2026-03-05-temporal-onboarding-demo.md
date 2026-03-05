# Temporal User Onboarding Demo Implementation Plan

> **For Claude:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** Build a Temporal workflow demo that demonstrates user onboarding: send welcome email → wait for email confirmation → create account → track analytics → wait → send follow-up email.

**Architecture:** Kotlin + Temporal Java SDK (JVM-compatible). Activities run in worker threads with print statements. Temporalite for local dev server.

**Tech Stack:** Kotlin 2.0, Temporal Java SDK, Temporalite

---

### Task 1: Add Temporal Dependencies

**Files:**
- Modify: `gradle/libs.versions.toml`
- Modify: `build.gradle.kts`

**Step 1: Add Temporal version to libs.versions.toml**

Add after line 18 (after `assertj = "3.27.3"`):
```toml
temporal = "1.26.0"
slf4j = "2.0.16"
```

Add after line 43 (after `assertj-core`):
```toml
temporal-sdk = { group = "io.temporal", name = "temporal-sdk", version.ref = "temporal" }
temporal-springboot = { group = "io.temporal", name = "temporal-springboot-starter", version.ref = "temporal" }
slf4j-simple = { group = "org.slf4j", name = "slf4j-simple", version.ref = "slf4j" }
```

**Step 2: Add dependencies to build.gradle.kts**

Add after line 30 (after `implementation(libs.snakeyaml)`):
```kotlin
implementation(libs.temporal.sdk)
runtimeOnly(libs.slf4j.simple)
```

**Step 3: Commit**
```bash
git add gradle/libs.versions.toml build.gradle.kts
git commit -m "feat: add Temporal SDK dependencies"
```

---

### Task 2: Create Workflow Data Models

**Files:**
- Create: `src/main/java/com/shawn/touchstone/temporal/model/OnboardingRequest.java`
- Create: `src/main/java/com/shawn/touchstone/temporal/model/OnboardingStatus.java`

**Step 1: Create OnboardingRequest.java**

```java
package com.shawn.touchstone.temporal.model;

import java.io.Serializable;

public class OnboardingRequest implements Serializable {
    private String userId;
    private String email;
    private String name;

    public OnboardingRequest() {}

    public OnboardingRequest(String userId, String email, String name) {
        this.userId = userId;
        this.email = email;
        this.name = name;
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
```

**Step 2: Create OnboardingStatus.java**

```java
package com.shawn.touchstone.temporal.model;

public enum OnboardingStatus {
    PENDING_EMAIL_CONFIRMATION,
    PENDING_ACCOUNT_CREATION,
    PENDING_FOLLOWUP,
    COMPLETED
}
```

**Step 3: Commit**
```bash
git add src/main/java/com/shawn/touchstone/temporal/model/
git commit -m "feat: add Temporal onboarding data models"
```

---

### Task 3: Create Activity Interface and Implementation

**Files:**
- Create: `src/main/java/com/shawn/touchstone/temporal/activity/UserOnboardingActivities.java`
- Create: `src/main/java/com/shawn/touchstone/temporal/activity/UserOnboardingActivitiesImpl.java`

**Step 1: Create UserOnboardingActivities.java**

```java
package com.shawn.touchstone.temporal.activity;

import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface UserOnboardingActivities {
    void sendWelcomeEmail(String email, String name);
    void createAccount(String email, String name);
    void trackAnalytics(String userId, String event);
    void sendFollowUpEmail(String email, String name);
}
```

**Step 2: Create UserOnboardingActivitiesImpl.java**

```java
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
```

**Step 3: Create AccountStore.java (utility for in-memory account storage)**

Create: `src/main/java/com/shawn/touchstone/temporal/model/AccountStore.java`

```java
package com.shawn.touchstone.temporal.model;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class AccountStore {
    private static final AccountStore INSTANCE = new AccountStore();
    private final Map<String, Account> accounts = new ConcurrentHashMap<>();

    private AccountStore() {}

    public static AccountStore getInstance() {
        return INSTANCE;
    }

    public void create(String email, String name) {
        accounts.put(email, new Account(email, name));
    }

    public Account get(String email) {
        return accounts.get(email);
    }

    public static class Account {
        private final String email;
        private final String name;

        public Account(String email, String name) {
            this.email = email;
            this.name = name;
        }

        public String getEmail() { return email; }
        public String getName() { return name; }
    }
}
```

**Step 4: Commit**
```bash
git add src/main/java/com/shawn/touchstone/temporal/activity/
git add src/main/java/com/shawn/touchstone/temporal/model/AccountStore.java
git commit -m "feat: add Temporal activity interface and implementation"
```

---

### Task 4: Create Workflow Interface and Implementation

**Files:**
- Create: `src/main/java/com/shawn/touchstone/temporal/workflow/UserOnboardingWorkflow.java`
- Create: `src/main/java/com/shawn/touchstone/temporal/workflow/UserOnboardingWorkflowImpl.java`

**Step 1: Create UserOnboardingWorkflow.java**

```java
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
```

**Step 2: Create UserOnboardingWorkflowImpl.java**

```java
package com.shawn.touchstone.temporal.workflow;

import com.shawn.touchstone.temporal.activity.UserOnboardingActivities;
import com.shawn.touchstone.temporal.model.OnboardingRequest;
import com.shawn.touchstone.temporal.model.OnboardingStatus;
import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;
import io.temporal.workflow.Waiter;

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
        // Step 1: Send welcome email
        activities.sendWelcomeEmail(request.getEmail(), request.getName());
        
        // Step 2: Wait for email confirmation (signal)
        Workflow.await(() -> status != OnboardingStatus.PENDING_EMAIL_CONFIRMATION);

        // Step 3: Create account
        activities.createAccount(request.getEmail(), request.getName());
        status = OnboardingStatus.PENDING_ACCOUNT_CREATION;

        // Step 4: Track analytics
        activities.trackAnalytics(request.getUserId(), "account_created");

        // Step 5: Wait 30 seconds (demo of Temporal timer)
        Workflow.newTimer(Duration.ofSeconds(30)).await();

        // Step 6: Send follow-up email
        activities.sendFollowUpEmail(request.getEmail(), request.getName());

        // Step 7: Complete
        status = OnboardingStatus.COMPLETED;
    }

    @Override
    public void confirmEmail(String token) {
        // Simple confirmation - in real app, validate token
        if (status == OnboardingStatus.PENDING_EMAIL_CONFIRMATION) {
            status = OnboardingStatus.PENDING_ACCOUNT_CREATION;
        }
    }

    @Override
    public OnboardingStatus getStatus() {
        return status;
    }
}
```

**Step 3: Commit**
```bash
git add src/main/java/com/shawn/touchstone/temporal/workflow/
git commit -m "feat: add Temporal workflow interface and implementation"
```

---

### Task 5: Create Worker and Starter

**Files:**
- Create: `src/main/java/com/shawn/touchstone/temporal/UserOnboardingWorker.java`
- Create: `src/main/java/com/shawn/touchstone/temporal/UserOnboardingStarter.java`

**Step 1: Create UserOnboardingWorker.java**

```java
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
        // Create WorkflowService
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        
        // Create WorkflowClient
        WorkflowClient client = WorkflowClient.newInstance(service);
        
        // Create WorkerFactory
        WorkerFactory factory = WorkerFactory.newInstance(client);
        
        // Create Worker
        Worker worker = factory.newWorker("user-onboarding-task-queue");
        
        // Register workflow and activities
        worker.registerWorkflowImplementationTypes(UserOnboardingWorkflowImpl.class);
        worker.registerActivitiesImplementations(new UserOnboardingActivitiesImpl());
        
        // Start factory
        factory.start();
        
        System.out.println("Worker started. Press Ctrl+C to exit.");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            factory.shutdown();
            service.shutdown();
        }));
    }
}
```

**Step 2: Create UserOnboardingStarter.java**

```java
package com.shawn.touchstone.temporal;

import com.shawn.touchstone.temporal.model.OnboardingRequest;
import com.shawn.touchstone.temporal.workflow.UserOnboardingWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;

import java.util.UUID;

public class UserOnboardingStarter {

    public static void main(String[] args) throws Exception {
        // Create WorkflowService
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        
        // Create WorkflowClient
        WorkflowClient client = WorkflowClient.newInstance(service);
        
        // Create workflow stub
        String workflowId = "user-onboarding-" + UUID.randomUUID();
        
        UserOnboardingWorkflow workflow = client.newWorkflowStub(
            UserOnboardingWorkflow.class,
            WorkflowOptions.newBuilder()
                .setTaskQueue("user-onboarding-task-queue")
                .setWorkflowId(workflowId)
                .build()
        );
        
        // Start workflow asynchronously
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
        
        // Keep main thread alive for demo
        Thread.sleep(60000);
        
        service.shutdown();
    }
}
```

**Step 3: Create ConfirmEmail.java for sending signal**

Create: `src/main/java/com/shawn/touchstone/temporal/ConfirmEmail.java`

```java
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
        
        // Send signal via untyped stub
        WorkflowStub.fromTyped(workflow).signal("confirmEmail", "dummy-token");
        
        System.out.println("Email confirmed for workflow: " + workflowId);
        
        service.shutdown();
    }
}
```

**Step 4: Commit**
```bash
git add src/main/java/com/shawn/touchstone/temporal/
git commit -m "feat: add Temporal worker, starter, and confirm-email tool"
```

---

### Task 6: Create README and Test

**Files:**
- Create: `temporal/README.md`

**Step 1: Create README.md**

```markdown
# Temporal User Onboarding Demo

A simple demo showcasing Temporal's core concepts: workflows, activities, signals, and timers.

## Prerequisites

- Java 21
- Gradle
- Docker (for Temporalite)

## Running the Demo

1. Start Temporalite (embedded Temporal server):
   ```bash
   docker run -p 7233:7233 temporalite:latest start --namespace default
   ```

2. Build the project:
   ```bash
   ./gradlew build
   ```

3. Start the Worker (terminal 1):
   ```bash
   ./gradlew run -Pmain=com.shawn.touchstone.temporal.UserOnboardingWorker
   ```

4. Start the Starter (terminal 2):
   ```bash
   ./gradlew run -Pmain=com.shawn.touchstone.temporal.UserOnboardingStarter
   ```

5. Confirm email (terminal 3):
   ```bash
   ./gradlew run -Pmain=com.shawn.touchstone.temporal.ConfirmEmail --args="<workflow-id-from-step-4>"
   ```

## Expected Output

- Starter: "Starting workflow for: john.doe@example.com"
- Worker: "[EMAIL] Sending welcome email to john.doe@example.com"
- After confirmEmail signal:
  - "[ACCOUNT] Created account for john.doe@example.com"
  - "[ANALYTICS] Tracking event: account_created"
- After 30 seconds:
  - "[EMAIL] Sending follow-up email to john.doe@example.com"

## Architecture

- **Workflow**: Orchestrates the process, waits for signals and timers
- **Activities**: Execute actual work (email, account, analytics)
- **Worker**: Polls task queue and executes activities/workflows
- **Starter**: Initiates the workflow
```

**Step 2: Commit**
```bash
git add temporal/README.md
git commit -m "docs: add Temporal demo README"
```

---

## Plan Complete

Implementation plan saved to `.opencode/plans/2026-03-05-temporal-onboarding-demo.md`

### Execution Choice

**1. Subagent-Driven (this session)** - I dispatch fresh subagent per task, review between tasks, fast iteration

**2. Parallel Session (separate)** - Open new session with executing-plans, batch execution with checkpoints

Which approach?
