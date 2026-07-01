package com.example.patterns.service;

import com.example.patterns.model.JavaInterviewExample;
import com.example.patterns.model.SolidPrinciple;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JavaLearningService {

    public List<SolidPrinciple> solidPrinciples() {
        return List.of(
                solid("S", "Single Responsibility Principle",
                        "One class should have one main job.",
                        "class InvoiceCalculator { double total(Invoice invoice) { return invoice.itemsTotal(); } }\nclass InvoicePrinter { String print(Invoice invoice) { return invoice.toString(); } }",
                        "Split classes when a change request mentions a different reason to edit the same class."),
                solid("O", "Open/Closed Principle",
                        "Add new behavior by adding code, not rewriting stable code.",
                        "interface DiscountPolicy { double apply(double amount); }\nclass FestivalDiscount implements DiscountPolicy { public double apply(double amount) { return amount * 0.90; } }",
                        "Strategy is the easiest Java example for this principle."),
                solid("L", "Liskov Substitution Principle",
                        "A child type should work anywhere the parent type is expected.",
                        "interface Payment { void pay(double amount); }\nclass CardPayment implements Payment { public void pay(double amount) { /* charge card */ } }",
                        "If subclass methods throw surprise exceptions for normal parent behavior, LSP is probably broken."),
                solid("I", "Interface Segregation Principle",
                        "Prefer small focused interfaces over one large everything-interface.",
                        "interface Printer { void print(); }\ninterface Scanner { void scan(); }\nclass BasicPrinter implements Printer { public void print() { } }",
                        "Do not force classes to implement methods they cannot honestly support."),
                solid("D", "Dependency Inversion Principle",
                        "High-level code should depend on abstractions, not concrete details.",
                        "class OrderService {\n  private final PaymentGateway gateway;\n  OrderService(PaymentGateway gateway) { this.gateway = gateway; }\n}",
                        "Constructor injection in Spring is DIP in everyday clothing.")
        );
    }

    public List<JavaInterviewExample> interviewExamples() {
        return List.of(
                example("Second highest salary", "Stream distinct, sort, skip",
                        """
                        Integer secondHighest = employees.stream()
                                .map(Employee::salary)
                                .distinct()
                                .sorted(Comparator.reverseOrder())
                                .skip(1)
                                .findFirst()
                                .orElseThrow();
                        System.out.println(secondHighest);
                        """,
                        "130000"),
                example("Highest salary by department", "groupingBy + maxBy",
                        """
                        Map<String, Optional<Employee>> result = employees.stream()
                                .collect(Collectors.groupingBy(Employee::department,
                                        Collectors.maxBy(Comparator.comparing(Employee::salary))));
                        System.out.println(result);
                        """,
                        "{Engineering=Optional[Asha Engineering 150000], Sales=Optional[Ravi Sales 120000], HR=Optional[Meera HR 95000]}"),
                example("Count employees by department", "groupingBy + counting",
                        """
                        Map<String, Long> result = employees.stream()
                                .collect(Collectors.groupingBy(Employee::department, Collectors.counting()));
                        System.out.println(result);
                        """,
                        "{Engineering=3, Sales=2, HR=2}"),
                example("Average salary by department", "averagingDouble",
                        """
                        Map<String, Double> result = employees.stream()
                                .collect(Collectors.groupingBy(Employee::department,
                                        Collectors.averagingDouble(Employee::salary)));
                        System.out.println(result);
                        """,
                        "{Engineering=130000.0, Sales=110000.0, HR=87500.0}"),
                example("Youngest and oldest employee", "min/max comparator",
                        """
                        Employee youngest = employees.stream()
                                .min(Comparator.comparing(Employee::age))
                                .orElseThrow();
                        Employee oldest = employees.stream()
                                .max(Comparator.comparing(Employee::age))
                                .orElseThrow();
                        System.out.println(youngest.name() + " / " + oldest.name());
                        """,
                        "Neha / George"),
                example("Sort by multiple fields", "comparing + thenComparing",
                        """
                        List<Employee> result = employees.stream()
                                .sorted(Comparator.comparing(Employee::department)
                                        .thenComparing(Employee::salary, Comparator.reverseOrder()))
                                .toList();
                        System.out.println(result);
                        """,
                        "[Asha Engineering 150000, Dev Engineering 130000, Neha Engineering 110000, Meera HR 95000, Isha HR 80000, Ravi Sales 120000, George Sales 100000]"),
                example("Remove duplicates", "distinct",
                        """
                        List<Integer> result = List.of(1, 2, 2, 3, 3, 4).stream()
                                .distinct()
                                .toList();
                        System.out.println(result);
                        """,
                        "[1, 2, 3, 4]"),
                example("Find duplicates", "Set add trick",
                        """
                        Set<Integer> seen = new HashSet<>();
                        Set<Integer> duplicates = List.of(1, 2, 2, 3, 3, 4).stream()
                                .filter(number -> !seen.add(number))
                                .collect(Collectors.toSet());
                        System.out.println(duplicates);
                        """,
                        "[2, 3]"),
                example("Frequency of characters", "groupingBy identity",
                        """
                        Map<Character, Long> result = "banana".chars()
                                .mapToObj(ch -> (char) ch)
                                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
                        System.out.println(result);
                        """,
                        "{b=1, a=3, n=2}"),
                example("Frequency of words", "split + groupingBy",
                        """
                        Map<String, Long> result = Arrays.stream("java spring java streams".split(" "))
                                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
                        System.out.println(result);
                        """,
                        "{java=2, spring=1, streams=1}"),
                example("First non-repeated character", "LinkedHashMap preserves order",
                        """
                        Character result = "swiss".chars()
                                .mapToObj(ch -> (char) ch)
                                .collect(Collectors.groupingBy(Function.identity(),
                                        LinkedHashMap::new, Collectors.counting()))
                                .entrySet().stream()
                                .filter(entry -> entry.getValue() == 1)
                                .map(Map.Entry::getKey)
                                .findFirst()
                                .orElseThrow();
                        System.out.println(result);
                        """,
                        "w"),
                example("Partition employees", "partitioningBy",
                        """
                        Map<Boolean, List<Employee>> result = employees.stream()
                                .collect(Collectors.partitioningBy(employee -> employee.salary() >= 100000));
                        System.out.println(result);
                        """,
                        "{true=[high earners], false=[others]}"),
                example("Convert List to Map", "toMap",
                        """
                        Map<Integer, String> result = employees.stream()
                                .collect(Collectors.toMap(Employee::id, Employee::name));
                        System.out.println(result);
                        """,
                        "{1=Asha, 2=Ravi, 3=Meera, 4=Dev, 5=Neha, 6=Isha, 7=George}"),
                example("Merge lists", "Stream.concat",
                        """
                        List<String> result = Stream.concat(listA.stream(), listB.stream())
                                .toList();
                        System.out.println(result);
                        """,
                        "[Asha, Ravi, Meera, Dev]"),
                example("Flatten nested lists using flatMap", "flatMap",
                        """
                        List<List<String>> teams = List.of(List.of("Asha", "Dev"), List.of("Ravi", "Meera"));
                        List<String> result = teams.stream()
                                .flatMap(List::stream)
                                .toList();
                        System.out.println(result);
                        """,
                        "[Asha, Dev, Ravi, Meera]"),
                example("Group by age", "groupingBy",
                        """
                        Map<Integer, List<Employee>> result = employees.stream()
                                .collect(Collectors.groupingBy(Employee::age));
                        System.out.println(result);
                        """,
                        "{31=[Asha Engineering 150000], 34=[Ravi Sales 120000], 28=[Meera HR 95000], 29=[Dev Engineering 130000], 25=[Neha Engineering 110000], 37=[Isha HR 80000], 45=[George Sales 100000]}"),
                example("Find top 3 salaries", "distinct + limit",
                        """
                        List<Integer> result = employees.stream()
                                .map(Employee::salary)
                                .distinct()
                                .sorted(Comparator.reverseOrder())
                                .limit(3)
                                .toList();
                        System.out.println(result);
                        """,
                        "[150000, 130000, 120000]"),
                example("Find common elements between two lists", "filter contains",
                        """
                        List<Integer> first = List.of(1, 2, 3, 4);
                        Set<Integer> second = Set.copyOf(List.of(3, 4, 5, 6));
                        List<Integer> result = first.stream()
                                .filter(second::contains)
                                .toList();
                        System.out.println(result);
                        """,
                        "[3, 4]"),
                example("Join names into a comma-separated string", "joining",
                        """
                        String result = employees.stream()
                                .map(Employee::name)
                                .collect(Collectors.joining(", "));
                        System.out.println(result);
                        """,
                        "Asha, Ravi, Meera, Dev, Neha, Isha, George"),
                example("Summarize salary statistics", "summaryStatistics",
                        """
                        IntSummaryStatistics stats = employees.stream()
                                .mapToInt(Employee::salary)
                                .summaryStatistics();
                        System.out.println(stats);
                        """,
                        "IntSummaryStatistics{count=7, sum=785000, min=80000, average=112142.857143, max=150000}")
        );
    }

    private static SolidPrinciple solid(String letter, String name, String simpleMeaning,
                                        String javaExample, String interviewTip) {
        return new SolidPrinciple(letter, name, simpleMeaning, javaExample, interviewTip);
    }

    private static JavaInterviewExample example(String title, String concept, String code, String output) {
        return new JavaInterviewExample(title, concept, code.stripIndent().trim(), output);
    }
}
