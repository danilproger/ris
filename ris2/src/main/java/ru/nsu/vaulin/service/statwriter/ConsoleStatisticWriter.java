package ru.nsu.vaulin.service.statwriter;

import ru.nsu.vaulin.model.UserAndKeyAttribute;

import java.util.Map;

public class ConsoleStatisticWriter implements StatisticWriter {

    @Override
    public void write(UserAndKeyAttribute attributes) {
        printStats("Edited nodes count for users", attributes.getUsersToNodesCount());
        printStats("Different nodes count containing key", attributes.getKeysToNodesCount());
    }

    private void printStats(String title, Map<String, Integer> stats) {
        System.out.println(title);
        stats.entrySet().stream()
                .sorted((lhs, rhs) -> -(lhs.getValue() - rhs.getValue()))
                .forEach(it -> System.out.printf("%s: %d\n", it.getKey(), it.getValue()));
    }
}
