package za.co.wethinkcode.AcceptanceTests;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

import za.co.wethinkcode.Client.RobotWorldClient;
import za.co.wethinkcode.Client.RobotWorldJsonClient;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class MovementAcceptanceTest {

    private final static int DEFAULT_PORT = 5000;
    private final static String DEFAULT_IP = "localhost";
    private final RobotWorldClient serverClient = new RobotWorldJsonClient();

    @BeforeEach
    void connectToServer() {
        serverClient.connect(DEFAULT_IP, DEFAULT_PORT);
    }

    @AfterEach
    void disconnectFromServer() {
        serverClient.disconnect();
    }

    @Test
    void unobstructedPathTest2x2Empty() {
        assertTrue(serverClient.isConnected());

        String request1 = "{" +
                "  \"robot\": \"LILY\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"standard\", \"3\", \"3\"]" +
                "}";

        String request_2 = "{" +
                "  \"robot\": \"LILY\"," +
                "  \"command\": \"forward\"," +
                "  \"arguments\": [\"1\"]"+
                "}";

        JsonNode response = serverClient.sendRequest(request1);
        JsonNode response_2 = serverClient.sendRequest(request_2);
        System.out.println(response);
        System.out.println(response_2);

        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());

        assertNotNull(response_2.get("result"));
        assertEquals("OK", response_2.get("result").asText());
        assertNotNull(response_2.get("state").get("position"));
    }


//     @Test
    void obstructedPathByPitTest2x2() {
        assertTrue(serverClient.isConnected());

        String request1 = "{" +
                "  \"robot\": \"BLOSSOM\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"standard\", \"3\", \"3\"]" +
                "}";

        String request_2 = "{" +
                "  \"robot\": \"BLOSSOM\"," +
                "  \"command\": \"forward\"," +
                "  \"arguments\": [\"7\"]"+
                "}";

        JsonNode response = serverClient.sendRequest(request1);
        JsonNode response_2 = serverClient.sendRequest(request_2);
        System.out.println(response);
        System.out.println(response_2);

        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());

        assertNotNull(response_2.get("result"));
        assertEquals("OK", response_2.get("result").asText());

        assertEquals("DEAD", response_2.get("state").get("status").asText());
        assertEquals("Fell", response_2.get("data").get("message"));

    }

    @Test
    void outOfBoundsTest2x2() {
        //Note: when testing if the robot can go to the edge, the y co-ordinate only increments to 99 and not 200

        assertTrue(serverClient.isConnected());

        String request1 = "{" +
                "  \"robot\": \"MARIGOLD\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"standard\", \"3\", \"3\"]" +
                "}";

        String request_2 = "{" +
                "  \"robot\": \"MARIGOLD\"," +
                "  \"command\": \"forward\"," +
                "  \"arguments\": [\"10\"]"+
                "}";

        JsonNode response = serverClient.sendRequest(request1);
        JsonNode response_2 = serverClient.sendRequest(request_2);
        System.out.println(response);
        System.out.println(response_2);

        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());
//
        assertNotNull(response_2.get("result"));
//        if ((response_2.get("data").get("message").asText()) != ("Obstructed")) {
        assertEquals("OK", response_2.get("result").asText());
        assertEquals("At the NORTH edge", response_2.get("data").get("message").asText());
        assertNotNull(response_2.get("state").get("position"));
//        }
    }

//     @Test
    void surviveMineTest2x2() {
        assertTrue(serverClient.isConnected());

        String request1 = "{" +
                "  \"robot\": \"IRIS\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"standard\", \"3\", \"3\"]" +
                "}";

        String request_2 = "{" +
                "  \"robot\": \"IRIS\"," +
                "  \"command\": \"forward\"," +
                "  \"arguments\": [\"5\"]"+
                "}";

        JsonNode response = serverClient.sendRequest(request1);
        JsonNode response_2 = serverClient.sendRequest(request_2);
        System.out.println(response);
        System.out.println(response_2);
        System.out.println(response_2.get("state").get("position").asText());

        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());

        assertNotNull(response_2.get("result"));
        assertEquals("OK", response_2.get("result").asText());

        if (response_2.get("state").get("status").asText() == "NORMAL") {
            assertEquals("NORMAL", response_2.get("state").get("status").asText());
        }

        assertEquals("MINE", response_2.get("data").get("message"));

    }

}