package org.darkknight;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

/*
+----------------+
        | Application    |
        +----------------+
        |
        v
        +----------------+
        |  Shard Router  |  <-- dynamic discovery
        +----------------+
        |
        v
        +-------------------------+
        | Shard Directory Service  | (Consul, Zookeeper, custom)
        +-------------------------+
        |
        v
        +-------------------------+
        | Shards (Primaries & Replicas)
        +-------------------------+
*/
public class ShardRouterDynamic {
    private final int numberOfShards;
    private final Random random = new Random();

    public ShardRouterDynamic(int numberOfShards) {
        this.numberOfShards = numberOfShards;
    }

    // Pretend this talks to Consul/Zookeeper etc.
    private Map<String, List<String>> getShardInfoFromDirectoryService(int shardId) {
        // Dummy: In real life, fetch live metadata from discovery system
        Map<String, List<String>> shardInfo = new HashMap<>();

        shardInfo.put("primary", List.of("jdbc:postgresql://primary-shard" + shardId + ".example.com:5432/db"));
        shardInfo.put("replicas", List.of(
                "jdbc:postgresql://replica1-shard" + shardId + ".example.com:5432/db",
                "jdbc:postgresql://replica2-shard" + shardId + ".example.com:5432/db"
        ));

        return shardInfo;
    }

    private int getShardId(long userId) {
        return (int) (userId % numberOfShards);
    }

    public Connection getWriteConnection(long userId) throws SQLException {
        int shardId = getShardId(userId);
        Map<String, List<String>> shardInfo = getShardInfoFromDirectoryService(shardId);
        String primaryUrl = shardInfo.get("primary").get(0);
        String username = "your_user";
        String password = "your_password";
        return DriverManager.getConnection(primaryUrl, username, password);
    }

    public Connection getReadConnection(long userId) throws SQLException {
        int shardId = getShardId(userId);
        Map<String, List<String>> shardInfo = getShardInfoFromDirectoryService(shardId);
        List<String> replicas = shardInfo.get("replicas");
        String replicaUrl = replicas.get(random.nextInt(replicas.size()));
        String username = "your_user";
        String password = "your_password";
        return DriverManager.getConnection(replicaUrl, username, password);
    }
}
