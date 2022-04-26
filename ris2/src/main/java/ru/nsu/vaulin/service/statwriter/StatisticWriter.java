package ru.nsu.vaulin.service.statwriter;

import ru.nsu.vaulin.model.UserAndKeyAttribute;

public interface StatisticWriter {
    void write(UserAndKeyAttribute attributes);
}
