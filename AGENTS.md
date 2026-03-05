# AGENTS.md - Touchstone Codebase Guide

## Overview
Touchstone is a Java/Kotlin learning project demonstrating various programming concepts (TDD, algorithms, design patterns, functional programming, etc.). It uses Gradle as the build system.

## Build Commands

| Command | Description |
|---------|-------------|
| `./gradlew build` | Build and run all tests |
| `./gradlew clean build` | Clean and rebuild |
| `./gradlew test` | Run all tests |
| `./gradlew test --tests "com.shawn.touchstone.tdd.TicTacToeSpec"` | Run single test class |
| `./gradlew test --tests "com.shawn.touchstone.tdd.TicTacToeSpec.whenXOutsideBoardThenRuntimeExp"` | Run single test method |
| `./gradlew compileJava` | Compile Java sources |
| `./gradlew compileKotlin` | Compile Kotlin sources |
| `./gradlew jar` | Create JAR archive |

## Project Structure

```
src/
├── main/
│   ├── java/com/shawn/touchstone/    # Java source files
│   └── kotlin/com/shawn/sample/      # Kotlin source files
└── test/
    └── java/com/shawn/touchstone/    # Test files
```

## Test Framework

- **JUnit 5** (Jupiter) - Primary test framework
- **Hamcrest** - Matchers (`org.hamcrest.Matchers.*`)
- **AssertJ** - Fluent assertions (`org.assertj.core.api.Assertions.assertThat`)
- **Mockito** - Mocking framework

## Code Style Guidelines

### Naming Conventions
- Classes: `PascalCase` (e.g., `TicTacToe`, `RateLimiter`)
- Methods/Variables: `camelCase` (e.g., `nextPlayer`, `boardSize`)
- Constants: `UPPER_SNAKE_CASE` (e.g., `MAX_RETRY`)
- Test classes: Use `Spec` suffix (e.g., `TicTacToeSpec`, `ShipSpec`)
- Test methods: Descriptive `when[Condition]Then[Result]` format

### Java Style
- Use 4 spaces for indentation (no tabs)
- Opening brace on same line
- One space after keywords (`if`, `for`, `while`, etc.)
- No spaces inside parentheses: `method(arg)` not `method( arg )`
- Import statements: No wildcards (`import java.util.List;` not `import java.util.*;`)
- Use explicit types over `var` for public APIs
- Prefer immutable collections when possible

### Kotlin Style
- Follow Kotlin idioms (use `val` over `var`)
- Use expression bodies where appropriate
- Follow standard Kotlin naming (no semicolons)

### Error Handling
- Throw specific exceptions rather than generic `RuntimeException`
- Use meaningful error messages: `throw new IllegalArgumentException("message")`
- Consider returning `Optional<T>` instead of null for nullable values

### Testing Conventions
- One `// comment` describing the scenario above each test
- Use Hamcrest matchers: `assertThat(actual, is(expected))`
- Use JUnit 5 assertions for exceptions: `assertThrows(RuntimeException.class, () -> ...)`
- Group related tests with blank lines

### Import Order (recommended)
1. Java standard library
2. Third-party libraries
3. Project packages

```java
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import org;
import org.mockito.junit.jupiter.api.Test.Mockito;

import com.shawn.touchstone.alg.LRUCache;
```

### Type Guidelines
- Use interfaces for types: `List<String>` not `ArrayList<String>`
- Avoid raw types: Use `List<String>` not `List`
- Prefer wrapper classes over primitives in collections

### Best Practices
- Keep methods small and focused (single responsibility)
- Use meaningful variable names
- Write self-documenting code
- Keep package structure organized by feature/domain

## Dependencies

Key libraries:
- Guava 33.4.0 - Google utilities
- Reactor 3.7.2 - Reactive programming
- RxJava 2.2.21 - Reactive extensions
- Lombok 1.18.36 - Code generation
- Temporal SDK 1.26.0 - Workflow engine
- XStream 1.4.21 - XML serialization

## IDE Notes
- Project uses Java 21 toolchain
- Gradle wrapper available: `./gradlew`
- Eclipse project files included (`.project`, `.classpath`, `.settings/`)
