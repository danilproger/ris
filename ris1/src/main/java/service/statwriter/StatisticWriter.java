package service.statwriter;

import model.UserAndKeyAttribute;

public interface StatisticWriter {
    void write(UserAndKeyAttribute attributes);
}
