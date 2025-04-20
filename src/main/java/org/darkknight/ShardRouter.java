package org.darkknight;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ShardRouter {
    private final int numberOfShards;
    private final Map<Integer, String> shardMap;

    public ShardRouter() {
        this.numberOfShards = 3; // Assume 3 shards
        this.shardMap = new HashMap<>();
        shardMap.put(0, "jdbc:postgresql://shard1.example.com:5432/db");
        shardMap.put(1, "jdbc:postgresql://shard2.example.com:5432/db");
        shardMap.put(2, "jdbc:postgresql://shard3.example.com:5432/db");
    }

    // Hash user_id to find shard
    private int getShardId(long userId) {
        return (int) (userId % numberOfShards);
    }

    // Get database connection for the right shard
    public Connection getConnection(long userId) throws SQLException {
        int shardId = getShardId(userId);
        String url = shardMap.get(shardId);
        String username = "your_user";
        String password = "your_password";
        return DriverManager.getConnection(url, username, password);
    }

    // Execute query (simplified example)
    public void getUserById(long userId) {
        try (Connection conn = getConnection(userId)) {
            var stmt = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
            stmt.setLong(1, userId);
            var rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("User ID: " + rs.getLong("id"));
                System.out.println("Username: " + rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}