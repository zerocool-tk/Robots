package za.co.wethinkcode.AcceptanceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import za.co.wethinkcode.Client.RobotWorldClient;
import za.co.wethinkcode.Client.RobotWorldJsonClient;

import com.fasterxml.jackson.databind.JsonNode;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class StateAcceptanceTest {
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
    void stateOfValidLaunchedRobot1x1() {
        assertTrue(serverClient.isConnected());

        String request = "{" +
            "  \"robot\": \"HAL\"," +
            "  \"command\": \"launch\"," +
            "  \"arguments\": [\"shooter\", \"5\", \"5\"]" +
            "}";
        JsonNode response = serverClient.sendRequest(request);

        assertNotNull(response.get("result"));
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("position"));
        assertNotNull(response.get("data").get("position").get(0).asInt());
        assertNotNull(response.get("data").get("position").get(1).asInt());


        //Checking if launch values are equal to the values after state has been run. FIgure out a way to do this using mock config file.

        int initialLaunchPositionX = response.get("data").get("position").get(0).asInt();
        int initialLaunchPositionY = response.get("data").get("position").get(1).asInt();
        String initialLaunchDirection = response.get("state").get("direction").asText(); 
        String requestState = "{" +
        "  \"robot\": \"HAL\"," +
        "  \"command\": \"state\"," +
        "  \"arguments\": []" +
        "}";

        JsonNode stateResponse = serverClient.sendRequest(requestState);

        // System.out.println(stateResponse);
        /* State response still contains result property, despite not being specified on the curriculum*/
        // assertNull(stateResponse.get("result"));
        assertNotNull(stateResponse.get("state"));

        assertNotNull(stateResponse.get("state").get("position"));
        System.out.println(stateResponse.get("state").get("position"));
        assertEquals(initialLaunchPositionX, stateResponse.get("state").get("position").get(0).asInt());
        assertEquals(initialLaunchPositionY, stateResponse.get("state").get("position").get(1).asInt());
        assertEquals(initialLaunchDirection, stateResponse.get("state").get("direction").asText());

        assertNotNull(stateResponse.get("state"));




        
    }
}
