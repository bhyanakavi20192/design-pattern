# Design Patterns Explorer

A Spring Boot web application that explains all 23 classic Gang of Four design patterns with:

- Pattern category summaries
- Spring Boot examples
- Use cases and tradeoffs
- Mermaid diagrams for each pattern
- Chart.js popularity and complexity charts
- Category filtering in the application view
- Spring architecture lessons for `@Transactional`, Saga, Outbox, and Circuit Breaker
- Interview-ready pattern answers with real-life examples and memory tricks
- Expandable Java coding examples for every pattern
- SOLID principles with Java examples
- Senior Java interview stream programs with expected output

## Run

```bash
mvn spring-boot:run
```

Then open:

```text
http://localhost:8080
```

## Run the Java Interview Examples

From the project directory:

```bash
mvn -q -DskipTests compile exec:java -Dexec.mainClass=com.example.patterns.examples.SeniorJavaInterviewPrograms
```

Or run `SeniorJavaInterviewPrograms` directly from your IDE.

## Project Structure

```text
src/main/java/com/example/patterns
  controller/PatternController.java
  model/DesignPattern.java
  model/PatternCategory.java
  model/SolidPrinciple.java
  model/JavaInterviewExample.java
  service/DesignPatternService.java
  service/JavaLearningService.java
  examples/SeniorJavaInterviewPrograms.java
src/main/resources
  templates/index.html
  static/css/app.css
  static/js/app.js
```

## Notes

The local Codex environment used to create this project has Java installed but does not have Maven installed, so the project was scaffolded and syntax-checked by inspection rather than executed locally.
