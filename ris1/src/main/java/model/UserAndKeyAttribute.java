package model;

import java.util.Map;

public class UserAndKeyAttribute {
    private final Map<String, Integer> usersToNodesCount;
    private final Map<String, Integer> keysToNodesCount;

    public UserAndKeyAttribute(Map<String, Integer> userToEditedNodesCount, Map<String, Integer> keyToMarkedNodesCount) {
        this.usersToNodesCount = userToEditedNodesCount;
        this.keysToNodesCount = keyToMarkedNodesCount;
    }

    public Map<String, Integer> getUsersToNodesCount() {
        return usersToNodesCount;
    }

    public Map<String, Integer> getKeysToNodesCount() {
        return keysToNodesCount;
    }
}
