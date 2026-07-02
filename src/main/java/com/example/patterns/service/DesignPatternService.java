package com.example.patterns.service;

import com.example.patterns.model.DesignPattern;
import com.example.patterns.model.PatternCategory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DesignPatternService {

    private final List<DesignPattern> patterns = List.of(
            pattern("Singleton", PatternCategory.CREATIONAL, "Ensure a class has one shared instance and provide a global access point.",
                    "Spring beans are singleton scoped by default inside the application context.",
                    "Shared stateless services, configuration holders, caches, and expensive collaborators.",
                    "Global state can hide dependencies and complicate testing.", 95, 2,
                    List.of("Singleton", "Client"),
                    "classDiagram\nclass Client\nclass Singleton {\n  -static instance\n  -Singleton()\n  +getInstance()\n}\nClient --> Singleton : uses"),
            pattern("Factory Method", PatternCategory.CREATIONAL, "Let subclasses or collaborators decide which concrete object to create.",
                    "Bean factories, FactoryBean, and strategy selection based on configuration.",
                    "Object creation varies by runtime condition or product family.",
                    "Adds indirection and can become ceremony for simple construction.", 88, 3,
                    List.of("Creator", "ConcreteCreator", "Product", "ConcreteProduct"),
                    "classDiagram\nclass Creator {\n  +factoryMethod()\n}\nclass Product\nclass ConcreteProduct\nclass ConcreteCreator\nCreator <|-- ConcreteCreator\nProduct <|-- ConcreteProduct\nConcreteCreator --> ConcreteProduct : creates"),
            pattern("Abstract Factory", PatternCategory.CREATIONAL, "Create related families of objects without naming their concrete classes.",
                    "Profiles or configuration classes that produce matching service families.",
                    "Multiple implementations must stay compatible across a product family.",
                    "Can be heavy when only one product varies.", 74, 4,
                    List.of("AbstractFactory", "ConcreteFactory", "AbstractProduct", "ConcreteProduct"),
                    "classDiagram\nclass AbstractFactory {\n  +createRepository()\n  +createNotifier()\n}\nclass CloudFactory\nclass LocalFactory\nclass Repository\nclass Notifier\nAbstractFactory <|-- CloudFactory\nAbstractFactory <|-- LocalFactory\nCloudFactory --> Repository\nCloudFactory --> Notifier"),
            pattern("Builder", PatternCategory.CREATIONAL, "Construct complex objects step by step while keeping construction readable.",
                    "DTO builders, request builders, and immutable configuration objects.",
                    "Objects have many optional fields or validation rules.",
                    "May duplicate fields between the target object and builder.", 86, 2,
                    List.of("Builder", "Product", "Director"),
                    "classDiagram\nclass Report {\n  +title\n  +sections\n}\nclass ReportBuilder {\n  +title()\n  +section()\n  +build()\n}\nclass ReportController\nReportController --> ReportBuilder\nReportBuilder --> Report : builds"),
            pattern("Prototype", PatternCategory.CREATIONAL, "Create new objects by copying an existing prototype.",
                    "Prototype-scoped beans and template objects copied before customization.",
                    "Creation is expensive and objects share a common baseline.",
                    "Deep copying mutable object graphs is easy to get wrong.", 58, 3,
                    List.of("Prototype", "Client", "Clone"),
                    "classDiagram\nclass Client\nclass Prototype {\n  +clone()\n}\nclass ConcretePrototype\nPrototype <|-- ConcretePrototype\nClient --> Prototype : clones"),
            pattern("Adapter", PatternCategory.STRUCTURAL, "Convert one interface into another interface clients expect.",
                    "Wrapping third-party SDKs behind application-specific ports.",
                    "External APIs do not match your domain interface.",
                    "Too many adapters can hide important external behavior.", 91, 2,
                    List.of("Client", "Target", "Adapter", "Adaptee"),
                    "classDiagram\nclass PaymentService\nclass PaymentPort {\n  +charge()\n}\nclass StripeAdapter {\n  +charge()\n}\nclass StripeSdk {\n  +createPayment()\n}\nPaymentService --> PaymentPort\nPaymentPort <|.. StripeAdapter\nStripeAdapter --> StripeSdk"),
            pattern("Bridge", PatternCategory.STRUCTURAL, "Separate abstraction from implementation so both can vary independently.",
                    "Service abstractions combined with interchangeable delivery channels or storage engines.",
                    "Two dimensions of variation would otherwise create many subclasses.",
                    "Introduces extra types that need clear naming.", 61, 4,
                    List.of("Abstraction", "RefinedAbstraction", "Implementor", "ConcreteImplementor"),
                    "classDiagram\nclass Notification\nclass UrgentNotification\nclass Sender {\n  +send()\n}\nclass EmailSender\nclass SmsSender\nNotification <|-- UrgentNotification\nNotification o-- Sender\nSender <|.. EmailSender\nSender <|.. SmsSender"),
            pattern("Composite", PatternCategory.STRUCTURAL, "Treat individual objects and object groups uniformly.",
                    "Menus, permission trees, category hierarchies, and nested workflow steps.",
                    "Clients should work with leaves and containers using the same API.",
                    "Uniform APIs can expose operations that do not make sense for every node.", 72, 3,
                    List.of("Component", "Leaf", "Composite"),
                    "classDiagram\nclass Component {\n  +render()\n}\nclass MenuItem\nclass MenuGroup {\n  +add()\n  +render()\n}\nComponent <|.. MenuItem\nComponent <|.. MenuGroup\nMenuGroup o-- Component"),
            pattern("Decorator", PatternCategory.STRUCTURAL, "Add responsibilities to an object dynamically without changing its class.",
                    "Servlet filters, Spring Security filter chains, and wrapped services.",
                    "Behavior should be composed at runtime in layers.",
                    "Debugging call stacks can become harder.", 79, 3,
                    List.of("Component", "ConcreteComponent", "Decorator"),
                    "classDiagram\nclass Service {\n  +execute()\n}\nclass CoreService\nclass LoggingDecorator\nclass CachingDecorator\nService <|.. CoreService\nService <|.. LoggingDecorator\nService <|.. CachingDecorator\nLoggingDecorator o-- Service\nCachingDecorator o-- Service"),
            pattern("Facade", PatternCategory.STRUCTURAL, "Provide a simple interface over a complex subsystem.",
                    "Application services that coordinate repositories, gateways, validators, and events.",
                    "Controllers need a clean workflow-level API.",
                    "A facade can become a large procedural coordinator.", 93, 2,
                    List.of("Facade", "Subsystem", "Client"),
                    "flowchart LR\nController --> OrderFacade\nOrderFacade --> InventoryService\nOrderFacade --> PaymentGateway\nOrderFacade --> EmailService"),
            pattern("Flyweight", PatternCategory.STRUCTURAL, "Share fine-grained immutable objects to reduce memory usage.",
                    "Caching repeated value objects such as icons, permissions, or formatting styles.",
                    "Many objects repeat the same intrinsic state.",
                    "Separating intrinsic and extrinsic state can reduce readability.", 43, 4,
                    List.of("Flyweight", "FlyweightFactory", "Client"),
                    "classDiagram\nclass Client\nclass StyleFactory {\n  +getStyle()\n}\nclass TextStyle\nClient --> StyleFactory\nStyleFactory --> TextStyle : reuses"),
            pattern("Proxy", PatternCategory.STRUCTURAL, "Control access to another object through a substitute object.",
                    "Spring AOP proxies for transactions, security, caching, and lazy loading.",
                    "Cross-cutting behavior should wrap a target transparently.",
                    "Proxy behavior can surprise callers when boundaries are unclear.", 96, 3,
                    List.of("Subject", "RealSubject", "Proxy"),
                    "classDiagram\nclass AccountService {\n  +transfer()\n}\nclass AccountServiceImpl\nclass TransactionProxy\nAccountService <|.. AccountServiceImpl\nAccountService <|.. TransactionProxy\nTransactionProxy --> AccountServiceImpl"),
            pattern("@Transactional", PatternCategory.SPRING_ARCHITECTURE, "Wrap a service method in a database transaction so all writes commit together or roll back together.",
                    "@Transactional on a service method asks Spring to create a proxy that opens, commits, or rolls back the transaction.",
                    "One business action changes multiple rows and partial success would create messy data.",
                    "It only protects work inside the transaction boundary; remote API calls are not magically rolled back.", 98, 3,
                    List.of("Service Method", "Spring Proxy", "Transaction Manager", "Database"),
                    "sequenceDiagram\nparticipant Browser\nparticipant Service\nparticipant TxProxy as Spring Transaction Proxy\nparticipant DB\nBrowser->>TxProxy: placeOrder()\nTxProxy->>DB: BEGIN\nTxProxy->>Service: run business logic\nService->>DB: save order and items\nalt success\nTxProxy->>DB: COMMIT\nelse exception\nTxProxy->>DB: ROLLBACK\nend"),
            pattern("Saga", PatternCategory.SPRING_ARCHITECTURE, "Coordinate a long business process across services using local transactions and compensating actions.",
                    "Use orchestration with a workflow service, or choreography with events between Spring Boot services.",
                    "A user journey crosses multiple services such as order, payment, inventory, and shipping.",
                    "You must design compensation steps because distributed rollback is not a simple undo button.", 82, 5,
                    List.of("Saga Orchestrator", "Local Transaction", "Compensation", "Events"),
                    "flowchart LR\nOrder[Create Order] --> Payment[Take Payment]\nPayment --> Inventory[Reserve Stock]\nInventory --> Shipping[Book Shipping]\nInventory -. fails .-> Refund[Refund Payment]\nRefund --> Cancel[Cancel Order]"),
            pattern("Outbox", PatternCategory.SPRING_ARCHITECTURE, "Save domain data and the event message in the same database transaction, then publish the message later.",
                    "A Spring service writes an order and an outbox row together; a scheduled publisher or Debezium sends the event to Kafka/RabbitMQ.",
                    "You need reliable events and cannot risk saving data but losing the message.",
                    "Consumers may receive duplicates, so handlers should be idempotent.", 87, 4,
                    List.of("Business Table", "Outbox Table", "Publisher", "Message Broker"),
                    "flowchart LR\nService -->|one transaction| DB[(Database)]\nDB --> Orders[orders table]\nDB --> Outbox[outbox table]\nOutbox --> Publisher[Outbox Publisher]\nPublisher --> Broker[Kafka or RabbitMQ]\nBroker --> Consumer[Other Service]"),
            pattern("Circuit Breaker", PatternCategory.SPRING_ARCHITECTURE, "Stop calling an unhealthy dependency for a while so your app fails fast and recovers calmly.",
                    "Resilience4j circuit breakers wrap RestClient, WebClient, or Feign calls with fallback behavior.",
                    "A downstream service is slow, flaky, or unavailable and repeated calls would exhaust your app.",
                    "Fallbacks must be honest; returning stale or default data is useful only when the business accepts it.", 85, 3,
                    List.of("Closed", "Open", "Half-Open", "Fallback"),
                    "stateDiagram-v2\n[*] --> Closed\nClosed --> Open: failures pass limit\nOpen --> HalfOpen: wait duration ends\nHalfOpen --> Closed: trial calls pass\nHalfOpen --> Open: trial calls fail\nOpen --> Fallback: fail fast"),
            pattern("Chain of Responsibility", PatternCategory.BEHAVIORAL, "Pass a request along handlers until one handles it.",
                    "Servlet filters, Spring Security filters, and validation pipelines.",
                    "Processing has ordered, optional, or short-circuiting steps.",
                    "Order matters and can be difficult to reason about.", 83, 3,
                    List.of("Handler", "ConcreteHandler", "Client"),
                    "flowchart LR\nRequest --> AuthFilter --> RateLimitFilter --> ValidationFilter --> Controller"),
            pattern("Command", PatternCategory.BEHAVIORAL, "Encapsulate a request as an object.",
                    "Job objects, domain commands, undoable actions, and queued work.",
                    "Requests need logging, retrying, scheduling, or undo.",
                    "Can create many small command classes.", 77, 3,
                    List.of("Command", "Invoker", "Receiver"),
                    "classDiagram\nclass Command {\n  +execute()\n}\nclass CreateInvoiceCommand\nclass JobRunner\nclass BillingService\nCommand <|.. CreateInvoiceCommand\nJobRunner --> Command\nCreateInvoiceCommand --> BillingService"),
            pattern("Interpreter", PatternCategory.BEHAVIORAL, "Represent and evaluate a grammar for a small language.",
                    "Search filters, rule expressions, and query DSLs.",
                    "A simple domain language makes repeated rules clearer.",
                    "For complex grammars, use a parser library instead.", 35, 5,
                    List.of("Expression", "TerminalExpression", "NonTerminalExpression", "Context"),
                    "flowchart TD\nExpression --> AndExpression\nExpression --> RoleExpression\nExpression --> RegionExpression\nAndExpression --> RoleExpression\nAndExpression --> RegionExpression"),
            pattern("Iterator", PatternCategory.BEHAVIORAL, "Traverse a collection without exposing its internal structure.",
                    "Java Iterable, streams, repository pagination, and cursor APIs.",
                    "Clients need sequential access to hidden or paged data.",
                    "Concurrent changes and lazy resources need careful lifecycle handling.", 89, 2,
                    List.of("Iterator", "Aggregate", "ConcreteIterator"),
                    "classDiagram\nclass Aggregate {\n  +iterator()\n}\nclass Iterator {\n  +hasNext()\n  +next()\n}\nclass PageIterator\nAggregate --> Iterator\nIterator <|.. PageIterator"),
            pattern("Mediator", PatternCategory.BEHAVIORAL, "Centralize complex communication between objects.",
                    "Application event publishers, workflow coordinators, and message buses.",
                    "Many components interact in tangled ways.",
                    "The mediator can become a hidden god object.", 62, 4,
                    List.of("Mediator", "Colleague"),
                    "flowchart LR\nInventory --> CheckoutMediator\nPayment --> CheckoutMediator\nShipping --> CheckoutMediator\nCheckoutMediator --> OrderEvents"),
            pattern("Memento", PatternCategory.BEHAVIORAL, "Capture and restore an object's previous state without exposing internals.",
                    "Draft saves, undo snapshots, and aggregate history checkpoints.",
                    "Users or workflows need rollback and restore.",
                    "Snapshots can consume storage and may leak sensitive data.", 46, 4,
                    List.of("Originator", "Memento", "Caretaker"),
                    "classDiagram\nclass Editor {\n  +save()\n  +restore()\n}\nclass Snapshot\nclass History\nEditor --> Snapshot : creates\nHistory o-- Snapshot\nEditor --> History"),
            pattern("Observer", PatternCategory.BEHAVIORAL, "Notify dependent objects when state changes.",
                    "Application events, domain events, listeners, and WebSocket updates.",
                    "Several reactions should happen after a state change without tight coupling.",
                    "Event order and transaction timing must be explicit.", 94, 3,
                    List.of("Subject", "Observer", "ConcreteObserver"),
                    "flowchart LR\nOrderService -->|publishes| OrderCreatedEvent\nOrderCreatedEvent --> EmailListener\nOrderCreatedEvent --> AnalyticsListener\nOrderCreatedEvent --> FulfillmentListener"),
            pattern("State", PatternCategory.BEHAVIORAL, "Let an object change behavior when its internal state changes.",
                    "Order, ticket, and workflow lifecycle implementations.",
                    "Conditional behavior depends heavily on lifecycle state.",
                    "State classes can be overkill for small state machines.", 68, 4,
                    List.of("Context", "State", "ConcreteState"),
                    "stateDiagram-v2\n[*] --> Draft\nDraft --> Submitted\nSubmitted --> Paid\nSubmitted --> Cancelled\nPaid --> Shipped\nShipped --> [*]"),
            pattern("Strategy", PatternCategory.BEHAVIORAL, "Define interchangeable algorithms behind one interface.",
                    "Injecting different pricing, sorting, validation, or notification policies.",
                    "The algorithm changes independently from the client.",
                    "Clients still need a selection mechanism.", 97, 2,
                    List.of("Context", "Strategy", "ConcreteStrategy"),
                    "classDiagram\nclass PricingService\nclass DiscountStrategy {\n  +apply()\n}\nclass SeasonalDiscount\nclass LoyaltyDiscount\nPricingService --> DiscountStrategy\nDiscountStrategy <|.. SeasonalDiscount\nDiscountStrategy <|.. LoyaltyDiscount"),
            pattern("Template Method", PatternCategory.BEHAVIORAL, "Define an algorithm skeleton while subclasses fill selected steps.",
                    "Base import jobs, report generation flows, and framework lifecycle hooks.",
                    "The workflow is fixed but individual steps vary.",
                    "Inheritance coupling can make customization rigid.", 66, 3,
                    List.of("AbstractClass", "ConcreteClass"),
                    "classDiagram\nclass ImportJob {\n  +run()\n  #read()\n  #validate()\n  #write()\n}\nclass CsvImportJob\nclass JsonImportJob\nImportJob <|-- CsvImportJob\nImportJob <|-- JsonImportJob"),
            pattern("Visitor", PatternCategory.BEHAVIORAL, "Add operations to an object structure without changing the element classes.",
                    "Exporters, validators, and reporting over stable domain trees.",
                    "The object structure is stable but operations change often.",
                    "Adding new element types becomes more expensive.", 49, 5,
                    List.of("Visitor", "Element", "ConcreteElement"),
                    "classDiagram\nclass Visitor {\n  +visitInvoice()\n  +visitPayment()\n}\nclass ExportVisitor\nclass Element {\n  +accept()\n}\nclass Invoice\nclass Payment\nVisitor <|.. ExportVisitor\nElement <|.. Invoice\nElement <|.. Payment\nInvoice --> Visitor\nPayment --> Visitor")
    );

    public List<DesignPattern> findAll() {
        return patterns.stream()
                .sorted(Comparator.comparing(pattern -> pattern.category().ordinal()))
                .toList();
    }

    public Map<PatternCategory, Long> countByCategory() {
        return patterns.stream()
                .collect(Collectors.groupingBy(DesignPattern::category, Collectors.counting()));
    }

    private static DesignPattern pattern(String name, PatternCategory category, String intent, String springExample,
                                         String whenToUse, String tradeOff, int popularity, int complexity,
                                         List<String> participants, String diagram) {
        return new DesignPattern(name, category, intent, springExample, whenToUse, tradeOff,
                realLifeExample(name), interviewAnswer(name), memoryTrick(name), codeExample(name),
                popularity, complexity, participants, diagram);
    }

    private static String realLifeExample(String name) {
        return switch (name) {
            case "Singleton" -> "A government ID office has one official record system. Everyone uses the same source instead of creating separate copies.";
            case "Factory Method" -> "A restaurant order counter decides whether to send your order to pizza, burger, or dessert preparation without you calling the chef directly.";
            case "Abstract Factory" -> "A furniture brand sells matching Victorian chair, sofa, and table families. You choose the style factory, and every product matches.";
            case "Builder" -> "Ordering a custom laptop step by step: RAM, storage, color, warranty. The final laptop is created only after all choices are set.";
            case "Prototype" -> "A photocopy shop makes new forms from one approved master copy, then fills small details for each customer.";
            case "Adapter" -> "A travel plug adapter lets an Indian charger work in a US socket without changing the charger.";
            case "Bridge" -> "A TV remote can work with Sony or Samsung TVs. Remote behavior and TV implementation change independently.";
            case "Composite" -> "A company org chart treats one employee and a whole department through the same 'show cost' or 'show members' operation.";
            case "Decorator" -> "Buying coffee and adding milk, caramel, and whipped cream. Each add-on wraps the same basic coffee with extra behavior.";
            case "Facade" -> "Hotel reception is one friendly desk, but behind it are housekeeping, billing, kitchen, and room service.";
            case "Flyweight" -> "A text editor reuses one font style object for thousands of repeated letters instead of storing full style data on every character.";
            case "Proxy" -> "An office receptionist controls access to the manager: schedule, security check, or forward only important calls.";
            case "@Transactional" -> "A bank transfer debits one account and credits another. If credit fails, debit must be rolled back.";
            case "Saga" -> "An online order journey: create order, take payment, reserve stock, book shipping. If stock fails, refund payment and cancel order.";
            case "Outbox" -> "A courier shop writes the parcel record and delivery slip together. Later, the delivery team picks slips and sends parcels.";
            case "Circuit Breaker" -> "An electrical breaker stops power flow when a device keeps failing, then allows a test after things cool down.";
            case "Chain of Responsibility" -> "Airport security has multiple counters: ticket check, baggage scan, immigration. Each step can pass or stop the passenger.";
            case "Command" -> "A restaurant waiter writes your request as an order ticket. Kitchen can execute it now, later, or retry if needed.";
            case "Interpreter" -> "A calculator reads '2 + 3 * 4' using grammar rules and evaluates the expression.";
            case "Iterator" -> "A TV remote next button moves through channels without exposing how channels are stored internally.";
            case "Mediator" -> "An air traffic controller coordinates planes so pilots do not all talk directly to each other.";
            case "Memento" -> "A game save point stores your current state so you can restore it later.";
            case "Observer" -> "You subscribe to a YouTube channel; when a video is posted, all subscribers get notified.";
            case "State" -> "A traffic signal behaves differently in red, yellow, and green states, even though it is the same signal.";
            case "Strategy" -> "Google Maps can choose fastest route, shortest route, walking route, or public transport route using interchangeable algorithms.";
            case "Template Method" -> "A school exam process is fixed: enter hall, answer paper, submit. Different subjects customize the questions.";
            case "Visitor" -> "A tax auditor visits salary, business, and investment income objects and calculates tax without changing those income classes.";
            default -> "Think of it as a repeatable solution to a design problem you see in normal software and normal life.";
        };
    }

    private static String interviewAnswer(String name) {
        return switch (name) {
            case "Singleton" -> "I use Singleton when exactly one shared instance should exist, like a Spring service bean or configuration provider. In Spring, singleton scope is default, so I usually rely on the container instead of writing manual getInstance code.";
            case "Factory Method" -> "Factory Method hides object creation. The client asks for a product through a common type, and the factory decides the concrete class. I use it when creation logic depends on input, profile, or runtime rules.";
            case "Abstract Factory" -> "Abstract Factory creates families of related objects. I would use it when several implementations must be consistent, for example cloud repository plus cloud notifier versus local repository plus local notifier.";
            case "Builder" -> "Builder helps create complex objects step by step, especially immutable objects with many optional fields. It avoids huge constructors and makes object creation readable.";
            case "Prototype" -> "Prototype creates objects by cloning an existing configured object. I use it when object creation is expensive and many new objects share a common base setup.";
            case "Adapter" -> "Adapter converts an incompatible external API into the interface my application expects. In real projects, I use adapters around payment gateways, SMS providers, or third-party SDKs.";
            case "Bridge" -> "Bridge separates abstraction from implementation so both can change independently. It is useful when two dimensions vary, such as notification type and delivery channel.";
            case "Composite" -> "Composite lets clients treat single objects and groups uniformly. It is useful for trees like menus, organization structures, permissions, or nested categories.";
            case "Decorator" -> "Decorator adds behavior without changing the original class. In Java and Spring, filters, wrappers, and security chains are common examples.";
            case "Facade" -> "Facade provides a simple API over a complex subsystem. In Spring, an application service often acts as a facade over repositories, validators, payment, and events.";
            case "Flyweight" -> "Flyweight reduces memory by sharing common immutable state. I use it when many objects repeat the same data, like styles, icons, permissions, or cached metadata.";
            case "Proxy" -> "Proxy controls access to another object. Spring uses proxies for transactions, caching, security, and AOP, so the target method gets extra behavior around it.";
            case "@Transactional" -> "@Transactional tells Spring to wrap a method in a transaction proxy. If the method succeeds, changes commit; if a runtime exception occurs, changes roll back.";
            case "Saga" -> "Saga manages distributed transactions using local transactions and compensating actions. It is important in microservices because one database transaction cannot cover all services.";
            case "Outbox" -> "Outbox makes event publishing reliable by saving the business data and event record in the same database transaction, then publishing the event later from the outbox table.";
            case "Circuit Breaker" -> "Circuit Breaker protects my app from repeatedly calling a failing dependency. It opens after failures, returns fallback quickly, and later tests recovery in half-open state.";
            case "Chain of Responsibility" -> "Chain of Responsibility sends a request through handlers one by one. Each handler can process, reject, or pass it forward. Servlet filters are a classic example.";
            case "Command" -> "Command wraps a request as an object. This is useful for queues, retries, audit logs, undo operations, and background jobs.";
            case "Interpreter" -> "Interpreter represents grammar rules as objects and evaluates expressions. I would use it for small rule languages, but for complex grammar I would choose a parser library.";
            case "Iterator" -> "Iterator provides a standard way to traverse items without exposing the internal collection structure. Java collections and streams build on this idea.";
            case "Mediator" -> "Mediator centralizes communication between components so they do not depend directly on each other. Event publishers and workflow coordinators often play this role.";
            case "Memento" -> "Memento captures an object's state so it can be restored later without exposing internal fields. Undo, drafts, and save points are common examples.";
            case "Observer" -> "Observer notifies subscribers when something changes. In Spring, application events and listeners are a common Observer-style implementation.";
            case "State" -> "State moves state-specific behavior into separate classes. It is useful when an object behaves differently across lifecycle states like draft, paid, shipped, or cancelled.";
            case "Strategy" -> "Strategy defines interchangeable algorithms behind one interface. I use it when business rules vary, like pricing, discounts, validation, routing, or sorting.";
            case "Template Method" -> "Template Method defines a fixed algorithm skeleton and lets subclasses customize steps. It is useful for import jobs, report generation, and framework lifecycle hooks.";
            case "Visitor" -> "Visitor adds new operations to a stable object structure without modifying each element class. It is useful for exports, validations, and reports over domain trees.";
            default -> "I explain the problem it solves, when I would use it, and one tradeoff so the answer sounds practical.";
        };
    }

    private static String memoryTrick(String name) {
        return switch (name) {
            case "Singleton" -> "One instance, one shared door.";
            case "Factory Method" -> "Ask the factory, not the concrete class.";
            case "Abstract Factory" -> "Factory of matching families.";
            case "Builder" -> "Build step by step, then call build.";
            case "Prototype" -> "Clone the prepared sample.";
            case "Adapter" -> "Make two incompatible plugs fit.";
            case "Bridge" -> "Two dimensions, one bridge.";
            case "Composite" -> "Leaf and group look the same.";
            case "Decorator" -> "Wrap behavior like toppings.";
            case "Facade" -> "One front desk for many departments.";
            case "Flyweight" -> "Share the repeated light parts.";
            case "Proxy" -> "Stand-in object controls access.";
            case "@Transactional" -> "All DB writes win together or lose together.";
            case "Saga" -> "Every step needs a compensation step.";
            case "Outbox" -> "Save data and message together.";
            case "Circuit Breaker" -> "Fail fast before failure spreads.";
            case "Chain of Responsibility" -> "Pass request along the chain.";
            case "Command" -> "Request becomes an object.";
            case "Interpreter" -> "Grammar becomes code.";
            case "Iterator" -> "Next without knowing storage.";
            case "Mediator" -> "Everyone talks through coordinator.";
            case "Memento" -> "Save now, restore later.";
            case "Observer" -> "Publish once, notify many.";
            case "State" -> "State changes behavior.";
            case "Strategy" -> "Swap the algorithm.";
            case "Template Method" -> "Fixed recipe, custom steps.";
            case "Visitor" -> "New operation visits old objects.";
            default -> "Problem, solution, example, tradeoff.";
        };
    }

    private static String codeExample(String name) {
        return switch (name) {
            case "Singleton" -> """
                    @Service
                    class InvoiceService {
                        // Spring creates one shared singleton bean by default.
                        BigDecimal total(Invoice invoice) {
                            return invoice.total();
                        }
                    }
                    """;
            case "Factory Method" -> """
                    interface Notification { void send(String message); }
                    class EmailNotification implements Notification { public void send(String m) { } }
                    class SmsNotification implements Notification { public void send(String m) { } }

                    class NotificationFactory {
                        Notification create(String channel) {
                            return channel.equals("sms") ? new SmsNotification() : new EmailNotification();
                        }
                    }
                    """;
            case "Abstract Factory" -> """
                    interface Repository { }
                    interface Notifier { }
                    interface AppFactory {
                        Repository repository();
                        Notifier notifier();
                    }
                    class CloudFactory implements AppFactory {
                        public Repository repository() { return new CloudRepository(); }
                        public Notifier notifier() { return new CloudNotifier(); }
                    }
                    """;
            case "Builder" -> """
                    record User(String name, String email, boolean active) {
                        static Builder builder() { return new Builder(); }
                        static class Builder {
                            String name;
                            String email;
                            boolean active = true;
                            Builder name(String value) { this.name = value; return this; }
                            Builder email(String value) { this.email = value; return this; }
                            User build() { return new User(name, email, active); }
                        }
                    }
                    """;
            case "Prototype" -> """
                    class ReportTemplate implements Cloneable {
                        String title;
                        List<String> sections;

                        ReportTemplate copy() {
                            return new ReportTemplate(title, new ArrayList<>(sections));
                        }
                    }
                    """;
            case "Adapter" -> """
                    interface PaymentPort { void charge(int amount); }
                    class StripeSdk { void createPayment(int cents) { } }

                    class StripeAdapter implements PaymentPort {
                        private final StripeSdk stripe = new StripeSdk();
                        public void charge(int amount) {
                            stripe.createPayment(amount * 100);
                        }
                    }
                    """;
            case "Bridge" -> """
                    interface Sender { void send(String text); }
                    class EmailSender implements Sender { public void send(String text) { } }

                    abstract class Notification {
                        protected final Sender sender;
                        Notification(Sender sender) { this.sender = sender; }
                        abstract void notifyUser(String text);
                    }
                    """;
            case "Composite" -> """
                    interface MenuComponent { void render(); }
                    class MenuItem implements MenuComponent { public void render() { } }
                    class MenuGroup implements MenuComponent {
                        private final List<MenuComponent> children = new ArrayList<>();
                        void add(MenuComponent child) { children.add(child); }
                        public void render() { children.forEach(MenuComponent::render); }
                    }
                    """;
            case "Decorator" -> """
                    interface Coffee { int cost(); }
                    class PlainCoffee implements Coffee { public int cost() { return 50; } }
                    class MilkDecorator implements Coffee {
                        private final Coffee coffee;
                        MilkDecorator(Coffee coffee) { this.coffee = coffee; }
                        public int cost() { return coffee.cost() + 10; }
                    }
                    """;
            case "Facade" -> """
                    @Service
                    class OrderFacade {
                        void placeOrder(Order order) {
                            inventory.reserve(order);
                            payment.charge(order);
                            email.sendConfirmation(order);
                        }
                    }
                    """;
            case "Flyweight" -> """
                    class StyleFactory {
                        private final Map<String, TextStyle> cache = new HashMap<>();
                        TextStyle get(String font) {
                            return cache.computeIfAbsent(font, TextStyle::new);
                        }
                    }
                    record TextStyle(String font) { }
                    """;
            case "Proxy" -> """
                    class TransactionProxy implements AccountService {
                        private final AccountService target;
                        public void transfer() {
                            begin();
                            target.transfer();
                            commit();
                        }
                    }
                    """;
            case "@Transactional" -> """
                    @Service
                    class TransferService {
                        @Transactional
                        public void transfer(long from, long to, BigDecimal amount) {
                            accountRepository.debit(from, amount);
                            accountRepository.credit(to, amount);
                        }
                    }
                    """;
            case "Saga" -> """
                    class OrderSaga {
                        void start(Order order) {
                            createOrder(order);
                            if (!takePayment(order)) {
                                cancelOrder(order);
                            }
                        }
                        void compensate(Order order) { refund(order); cancelOrder(order); }
                    }
                    """;
            case "Outbox" -> """
                    @Transactional
                    void placeOrder(Order order) {
                        orderRepository.save(order);
                        outboxRepository.save(new OutboxEvent("OrderCreated", order.id()));
                    }

                    // Later: publisher reads outbox table and sends to Kafka/RabbitMQ.
                    """;
            case "Circuit Breaker" -> """
                    @CircuitBreaker(name = "payment", fallbackMethod = "fallback")
                    PaymentStatus callPayment(Order order) {
                        return paymentClient.charge(order);
                    }

                    PaymentStatus fallback(Order order, Exception ex) {
                        return PaymentStatus.PENDING;
                    }
                    """;
            case "Chain of Responsibility" -> """
                    interface Handler { boolean handle(Request request); }
                    class AuthHandler implements Handler {
                        public boolean handle(Request request) {
                            return request.hasToken();
                        }
                    }
                    // Request flows through auth, validation, rate-limit handlers.
                    """;
            case "Command" -> """
                    interface Command { void execute(); }
                    class CreateInvoiceCommand implements Command {
                        private final BillingService billing;
                        public void execute() { billing.createInvoice(); }
                    }
                    """;
            case "Interpreter" -> """
                    interface Expression { boolean matches(User user); }
                    class RoleExpression implements Expression {
                        public boolean matches(User user) {
                            return user.role().equals("ADMIN");
                        }
                    }
                    """;
            case "Iterator" -> """
                    Iterator<Employee> iterator = employees.iterator();
                    while (iterator.hasNext()) {
                        System.out.println(iterator.next().name());
                    }

                    employees.stream().forEach(System.out::println);
                    """;
            case "Mediator" -> """
                    class CheckoutMediator {
                        void checkout(Order order) {
                            inventory.reserve(order);
                            payment.charge(order);
                            shipping.book(order);
                        }
                    }
                    """;
            case "Memento" -> """
                    record EditorSnapshot(String text) { }
                    class Editor {
                        private String text;
                        EditorSnapshot save() { return new EditorSnapshot(text); }
                        void restore(EditorSnapshot snapshot) { text = snapshot.text(); }
                    }
                    """;
            case "Observer" -> """
                    @Component
                    class EmailListener {
                        @EventListener
                        void on(OrderCreated event) {
                            emailService.send(event.orderId());
                        }
                    }
                    """;
            case "State" -> """
                    interface OrderState { void next(Order order); }
                    class PaidState implements OrderState {
                        public void next(Order order) {
                            order.setState(new ShippedState());
                        }
                    }
                    """;
            case "Strategy" -> """
                    interface DiscountStrategy { BigDecimal apply(BigDecimal amount); }
                    class LoyaltyDiscount implements DiscountStrategy {
                        public BigDecimal apply(BigDecimal amount) {
                            return amount.multiply(BigDecimal.valueOf(0.90));
                        }
                    }
                    """;
            case "Template Method" -> """
                    abstract class ImportJob {
                        final void run() {
                            read();
                            validate();
                            write();
                        }
                        abstract void read();
                        abstract void write();
                        void validate() { }
                    }
                    """;
            case "Visitor" -> """
                    interface Visitor { void visit(Invoice invoice); }
                    interface Element { void accept(Visitor visitor); }
                    class Invoice implements Element {
                        public void accept(Visitor visitor) {
                            visitor.visit(this);
                        }
                    }
                    """;
            default -> """
                    // Explain the pattern with:
                    // 1. Interface or abstraction
                    // 2. Concrete implementation
                    // 3. Client using abstraction
                    """;
        };
    }
}
