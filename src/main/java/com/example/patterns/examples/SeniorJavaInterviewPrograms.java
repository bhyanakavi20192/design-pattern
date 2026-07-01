package com.example.patterns.examples;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SeniorJavaInterviewPrograms {

    record Employee(int id, String name, String department, int age, int salary) {
        @Override
        public String toString() {
            return name + " " + department + " " + salary;
        }
    }

    private static final List<Employee> EMPLOYEES = List.of(
            new Employee(1, "Asha", "Engineering", 31, 150000),
            new Employee(2, "Ravi", "Sales", 34, 120000),
            new Employee(3, "Meera", "HR", 28, 95000),
            new Employee(4, "Dev", "Engineering", 29, 130000),
            new Employee(5, "Neha", "Engineering", 25, 110000),
            new Employee(6, "Isha", "HR", 37, 80000),
            new Employee(7, "George", "Sales", 45, 100000)
    );

    public static void main(String[] args) {
        secondHighestSalary();
        highestSalaryByDepartment();
        countEmployeesByDepartment();
        averageSalaryByDepartment();
        youngestAndOldestEmployee();
        sortByMultipleFields();
        removeDuplicates();
        findDuplicates();
        frequencyOfCharacters();
        frequencyOfWords();
        firstNonRepeatedCharacter();
        partitionEmployees();
        convertListToMap();
        mergeLists();
        flattenNestedLists();
        groupByAge();
        topThreeSalaries();
        commonElements();
        joinNames();
        summarizeSalaryStatistics();
    }

    private static void secondHighestSalary() {
        Integer result = EMPLOYEES.stream()
                .map(Employee::salary)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(1)
                .findFirst()
                .orElseThrow();
        print("Second highest salary", result);
    }

    private static void highestSalaryByDepartment() {
        Map<String, Optional<Employee>> result = EMPLOYEES.stream()
                .collect(Collectors.groupingBy(Employee::department,
                        LinkedHashMap::new,
                        Collectors.maxBy(Comparator.comparing(Employee::salary))));
        print("Highest salary by department", result);
    }

    private static void countEmployeesByDepartment() {
        Map<String, Long> result = EMPLOYEES.stream()
                .collect(Collectors.groupingBy(Employee::department, LinkedHashMap::new, Collectors.counting()));
        print("Count employees by department", result);
    }

    private static void averageSalaryByDepartment() {
        Map<String, Double> result = EMPLOYEES.stream()
                .collect(Collectors.groupingBy(Employee::department,
                        LinkedHashMap::new,
                        Collectors.averagingDouble(Employee::salary)));
        print("Average salary by department", result);
    }

    private static void youngestAndOldestEmployee() {
        Employee youngest = EMPLOYEES.stream()
                .min(Comparator.comparing(Employee::age))
                .orElseThrow();
        Employee oldest = EMPLOYEES.stream()
                .max(Comparator.comparing(Employee::age))
                .orElseThrow();
        print("Youngest/oldest employee", youngest.name() + " / " + oldest.name());
    }

    private static void sortByMultipleFields() {
        List<Employee> result = EMPLOYEES.stream()
                .sorted(Comparator.comparing(Employee::department)
                        .thenComparing(Employee::salary, Comparator.reverseOrder()))
                .toList();
        print("Sort by multiple fields", result);
    }

    private static void removeDuplicates() {
        List<Integer> result = List.of(1, 2, 2, 3, 3, 4).stream()
                .distinct()
                .toList();
        print("Remove duplicates", result);
    }

    private static void findDuplicates() {
        Set<Integer> seen = new HashSet<>();
        Set<Integer> result = List.of(1, 2, 2, 3, 3, 4).stream()
                .filter(number -> !seen.add(number))
                .collect(Collectors.toSet());
        print("Find duplicates", result);
    }

    private static void frequencyOfCharacters() {
        Map<Character, Long> result = "banana".chars()
                .mapToObj(ch -> (char) ch)
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()));
        print("Frequency of characters", result);
    }

    private static void frequencyOfWords() {
        Map<String, Long> result = Arrays.stream("java spring java streams".split(" "))
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()));
        print("Frequency of words", result);
    }

    private static void firstNonRepeatedCharacter() {
        Character result = "swiss".chars()
                .mapToObj(ch -> (char) ch)
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() == 1)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow();
        print("First non-repeated character", result);
    }

    private static void partitionEmployees() {
        Map<Boolean, List<Employee>> result = EMPLOYEES.stream()
                .collect(Collectors.partitioningBy(employee -> employee.salary() >= 100000));
        print("Partition employees", result);
    }

    private static void convertListToMap() {
        Map<Integer, String> result = EMPLOYEES.stream()
                .collect(Collectors.toMap(Employee::id, Employee::name, (first, second) -> first, LinkedHashMap::new));
        print("Convert List to Map", result);
    }

    private static void mergeLists() {
        List<String> first = List.of("Asha", "Ravi");
        List<String> second = List.of("Meera", "Dev");
        List<String> result = Stream.concat(first.stream(), second.stream()).toList();
        print("Merge lists", result);
    }

    private static void flattenNestedLists() {
        List<List<String>> teams = List.of(List.of("Asha", "Dev"), List.of("Ravi", "Meera"));
        List<String> result = teams.stream()
                .flatMap(List::stream)
                .toList();
        print("Flatten nested lists", result);
    }

    private static void groupByAge() {
        Map<Integer, List<Employee>> result = EMPLOYEES.stream()
                .collect(Collectors.groupingBy(Employee::age, LinkedHashMap::new, Collectors.toList()));
        print("Group by age", result);
    }

    private static void topThreeSalaries() {
        List<Integer> result = EMPLOYEES.stream()
                .map(Employee::salary)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .toList();
        print("Find top 3 salaries", result);
    }

    private static void commonElements() {
        List<Integer> first = List.of(1, 2, 3, 4);
        Set<Integer> second = Set.copyOf(List.of(3, 4, 5, 6));
        List<Integer> result = first.stream()
                .filter(second::contains)
                .toList();
        print("Find common elements", result);
    }

    private static void joinNames() {
        String result = EMPLOYEES.stream()
                .map(Employee::name)
                .collect(Collectors.joining(", "));
        print("Join names", result);
    }

    private static void summarizeSalaryStatistics() {
        IntSummaryStatistics result = EMPLOYEES.stream()
                .mapToInt(Employee::salary)
                .summaryStatistics();
        print("Summarize salary statistics", result);
    }

    private static void print(String title, Object result) {
        System.out.println(title + " -> " + result);
    }
}
