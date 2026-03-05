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
