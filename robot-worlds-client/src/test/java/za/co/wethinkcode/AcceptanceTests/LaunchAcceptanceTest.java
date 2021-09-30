package za.co.wethinkcode.AcceptanceTests;

import za.co.wethinkcode.Client.RobotWorldClient;
import za.co.wethinkcode.Client.RobotWorldJsonClient;

import com.fasterxml.jackson.databind.JsonNode;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class LaunchAcceptanceTest {
    private final static int DEFAULT_PORT = 5000;
    private final static String DEFAULT_IP = "localhost";

    private final RobotWorldClient serverClient = new RobotWorldJsonClient();
    private final RobotWorldClient serverClientTwo = new RobotWorldJsonClient();
    private final RobotWorldClient serverClientThree = new RobotWorldJsonClient();
    private final RobotWorldClient serverClientFour = new RobotWorldJsonClient();
    private final RobotWorldClient serverClientFive = new RobotWorldJsonClient();
    private final RobotWorldClient serverClientSix = new RobotWorldJsonClient();
    private final RobotWorldClient serverClientSeven = new RobotWorldJsonClient();
    private final RobotWorldClient serverClientEight = new RobotWorldJsonClient();
    private final RobotWorldClient serverClientNine = new RobotWorldJsonClient();
    private final RobotWorldClient serverClientTen = new RobotWorldJsonClient();


    @BeforeEach
    void connectToServer() {
        serverClient.connect(DEFAULT_IP, DEFAULT_PORT);
        serverClientTwo.connect(DEFAULT_IP, DEFAULT_PORT);
        serverClientThree.connect(DEFAULT_IP, DEFAULT_PORT);
        serverClientFour.connect(DEFAULT_IP, DEFAULT_PORT);
        serverClientFive.connect(DEFAULT_IP, DEFAULT_PORT);
        serverClientSix.connect(DEFAULT_IP, DEFAULT_PORT);
        serverClientSeven.connect(DEFAULT_IP, DEFAULT_PORT);
        serverClientEight.connect(DEFAULT_IP, DEFAULT_PORT);
        serverClientNine.connect(DEFAULT_IP, DEFAULT_PORT);
        serverClientTen.connect(DEFAULT_IP, DEFAULT_PORT);


    }

    @AfterEach
    void disconnectFromServer() {
        serverClient.disconnect();
        serverClientTwo.disconnect();
        serverClientThree.disconnect();
        serverClientFour.disconnect();
        serverClientFive.disconnect();
        serverClientSix.disconnect();
        serverClientSeven.disconnect();
        serverClientEight.disconnect();
        serverClientNine.disconnect();
        serverClientTen.disconnect();


    }

    @Test
    void validLaunchShouldSucceed1x1() {

        assertTrue(serverClient.isConnected());

        String request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\", \"5\", \"5\"]" +
                "}";
        JsonNode response = serverClient.sendRequest(request);

        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());


        System.out.println(response);

        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("position"));
        // assertEquals(0, response.get("data").get("position").get(0).asInt());
        // assertEquals(0, response.get("data").get("position").get(1).asInt());

        assertNotNull(response.get("state"));

    }


    @Test
    void invalidLaunchShouldFail1x1() {
        // Given that I am connected to a running Robot Worlds server
        assertTrue(serverClient.isConnected());

        // When I send an invalid launch request with the command "luanch" instead of "launch"
        String request = "{" +
                "\"robot\": \"HAL\"," +
                "\"command\": \"luanch\"," +
                "\"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        JsonNode response = serverClient.sendRequest(request);

        // Then I should get an error response
        assertNotNull(response.get("result"));
        assertEquals("ERROR", response.get("result").asText());

        // And the message "Unsupported command"
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("message"));
        System.out.println(response);
        assertTrue(response.get("data").get("message").asText().contains("Unsupported command"));
    }


    @Test
    void canLaunchAnotherRobot2x2Empty() {
        assertTrue(serverClient.isConnected());

        String clientOneRequest = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\", \"5\", \"5\"]" +
                "}";
        JsonNode responseOne = serverClient.sendRequest(clientOneRequest);

        System.out.println(responseOne);

        assertNotNull(responseOne.get("result"));
        assertEquals("OK", responseOne.get("result").asText());


        System.out.println(responseOne);

        assertNotNull(responseOne.get("data"));
        assertNotNull(responseOne.get("data").get("position"));
        // assertEquals(0, response.get("data").get("position").get(0).asInt());
        // assertEquals(0, response.get("data").get("position").get(1).asInt());
        assertNotNull(responseOne.get("state"));


        assertTrue(serverClientTwo.isConnected());

        String clientTwoRequest = "{" +
                "  \"robot\": \"R2D2\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\", \"6\", \"6\"]" +
                "}";
        JsonNode responseTwo = serverClientTwo.sendRequest(clientTwoRequest);

        System.out.println(responseTwo);
        assertNotNull(responseTwo.get("result"));
        assertEquals("OK", responseTwo.get("result").asText());


        assertNotNull(responseTwo.get("data").get("position"));
        // assertEquals(0, response1.get("data").get("position").get(0).asInt());
        // assertEquals(0, response1.get("data").get("position").get(1).asInt());
        assertNotNull(responseTwo.get("state"));

    }

    @Test
    void worldWithoutObsIsFull2x2Empty() {

        // Client One Connects. LAUNCH should SUCCEED.
        assertTrue(serverClient.isConnected());

        String clientOneRequest = "{" +
                "  \"robot\": \"ONE\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\", \"5\", \"5\"]" +
                "}";
        JsonNode responseOne = serverClient.sendRequest(clientOneRequest);
        
        assertNotNull(responseOne.get("result"));
        assertEquals("OK", responseOne.get("result").asText());

        assertNotNull(responseOne.get("data"));
        assertNotNull(responseOne.get("data").get("position"));

        assertNotNull(responseOne.get("state"));

        // Client Two Connects. LAUNCH should SUCCEED.
        assertTrue(serverClientTwo.isConnected());

        String clientTwoRequest = "{" +
                "  \"robot\": \"TWO\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\", \"5\", \"5\"]" +
                "}";
        JsonNode responseTwo = serverClientTwo.sendRequest(clientTwoRequest);
        
        assertNotNull(responseTwo.get("result"));
        assertEquals("OK", responseTwo.get("result").asText());

        assertNotNull(responseTwo.get("data"));
        assertNotNull(responseTwo.get("data").get("position"));

        assertNotNull(responseTwo.get("state"));

        // Client Three Connects. LAUNCH should SUCCEED.
        assertTrue(serverClientThree.isConnected());

        String clientThreeRequest = "{" +
                "  \"robot\": \"THREE\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\", \"5\", \"5\"]" +
                "}";
        JsonNode responseThree = serverClientThree.sendRequest(clientThreeRequest);
        
        assertNotNull(responseThree.get("result"));
        assertEquals("OK", responseThree.get("result").asText());

        assertNotNull(responseThree.get("data"));
        assertNotNull(responseThree.get("data").get("position"));

        assertNotNull(responseThree.get("state"));

        // Client Four Connects. LAUNCH should SUCCEED.
        assertTrue(serverClientFour.isConnected());

        String clientFourRequest = "{" +
                "  \"robot\": \"FOUR\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\", \"5\", \"5\"]" +
                "}";
        JsonNode responseFour = serverClientFour.sendRequest(clientFourRequest);
        
        assertNotNull(responseFour.get("result"));
        assertEquals("OK", responseFour.get("result").asText());

        assertNotNull(responseFour.get("data"));
        assertNotNull(responseFour.get("data").get("position"));

        assertNotNull(responseFour.get("state"));

        // Client Five Connects. LAUNCH should SUCCEED.
        assertTrue(serverClientFive.isConnected());

        String clientFiveRequest = "{" +
                "  \"robot\": \"FIVE\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\", \"5\", \"5\"]" +
                "}";
        JsonNode responseFive = serverClientFive.sendRequest(clientFiveRequest);
        
        assertNotNull(responseFive.get("result"));
        assertEquals("OK", responseFive.get("result").asText());

        assertNotNull(responseFive.get("data"));
        assertNotNull(responseFive.get("data").get("position"));

        assertNotNull(responseFive.get("state"));

        // Client Six Connects. LAUNCH should SUCCEED.
        assertTrue(serverClientSix.isConnected());

        String clientSixRequest = "{" +
                "  \"robot\": \"SIX\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\", \"5\", \"5\"]" +
                "}";
        JsonNode responseSix = serverClientSix.sendRequest(clientSixRequest);
        
        assertNotNull(responseSix.get("result"));
        assertEquals("OK", responseSix.get("result").asText());

        assertNotNull(responseSix.get("data"));
        assertNotNull(responseSix.get("data").get("position"));

        assertNotNull(responseSix.get("state"));

        // Client Seven Connects. LAUNCH should SUCCEED.
        assertTrue(serverClientSeven.isConnected());

        String clientSevenRequest = "{" +
                "  \"robot\": \"SEVEN\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\", \"5\", \"5\"]" +
                "}";
        JsonNode responseSeven = serverClientSeven.sendRequest(clientSevenRequest);
        
        assertNotNull(responseSeven.get("result"));
        assertEquals("OK", responseSeven.get("result").asText());

        assertNotNull(responseSeven.get("data"));
        assertNotNull(responseSeven.get("data").get("position"));

        assertNotNull(responseSeven.get("state"));

        // Client Eight Connects. LAUNCH should SUCCEED.
        assertTrue(serverClientEight.isConnected());

        String clientEightRequest = "{" +
                "  \"robot\": \"EIGHT\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\", \"5\", \"5\"]" +
                "}";
        JsonNode responseEight = serverClientEight.sendRequest(clientEightRequest);
        
        assertNotNull(responseEight.get("result"));
        assertEquals("OK", responseEight.get("result").asText());

        assertNotNull(responseEight.get("data"));
        assertNotNull(responseEight.get("data").get("position"));

        assertNotNull(responseEight.get("state"));

        // Client Nine Connects. LAUNCH should SUCCEED.
        assertTrue(serverClientNine.isConnected());

        String clientNineRequest = "{" +
                "  \"robot\": \"NINE\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\", \"5\", \"5\"]" +
                "}";
        JsonNode responseNine = serverClientNine.sendRequest(clientNineRequest);
        System.out.println(responseNine);
        assertNotNull(responseNine.get("result"));
        assertEquals("OK", responseNine.get("result").asText());

        assertNotNull(responseNine.get("data"));
        assertNotNull(responseNine.get("data").get("position"));

        assertNotNull(responseNine.get("state"));

        // Client Ten Connects. LAUNCH should FAIL
        assertTrue(serverClientTen.isConnected());

        String clientTenRequest = "{" +
                "  \"robot\": \"TEN\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\", \"5\", \"5\"]" +
                "}";
        JsonNode responseTen = serverClientTen.sendRequest(clientTenRequest);

        assertNotNull(responseTen.get("result"));
        assertEquals("ERROR", responseTen.get("result").asText());

        assertNotNull(responseTen.get("data"));
        assertEquals("No more space in this world", responseTen.get("data").get("message").asText());

    }

    @Test
    void LaunchRobotsIntoAWorldWithAnObstacle2x2() {
                // Client One Connects. LAUNCH should SUCCEED.
                assertTrue(serverClient.isConnected());

                String clientOneRequest = "{" +
                        "  \"robot\": \"ONE\"," +
                        "  \"command\": \"launch\"," +
                        "  \"arguments\": [\"shooter\", \"5\", \"5\"]" +
                        "}";
                JsonNode responseOne = serverClient.sendRequest(clientOneRequest);
                
                assertNotNull(responseOne.get("result"));
                assertEquals("OK", responseOne.get("result").asText());
        
                assertNotNull(responseOne.get("data"));
                assertNotNull(responseOne.get("data").get("position"));
                assertNotEquals(1, responseOne.get("data").get("position").get(0));
                assertNotEquals(1, responseOne.get("data").get("position").get(1));
        
                assertNotNull(responseOne.get("state"));
        
                // Client Two Connects. LAUNCH should SUCCEED.
                assertTrue(serverClientTwo.isConnected());
        
                String clientTwoRequest = "{" +
                        "  \"robot\": \"TWO\"," +
                        "  \"command\": \"launch\"," +
                        "  \"arguments\": [\"shooter\", \"5\", \"5\"]" +
                        "}";
                JsonNode responseTwo = serverClientTwo.sendRequest(clientTwoRequest);
                
                assertNotNull(responseTwo.get("result"));
                assertEquals("OK", responseTwo.get("result").asText());
        
                assertNotNull(responseTwo.get("data"));
                assertNotNull(responseTwo.get("data").get("position"));
                assertNotEquals(1, responseTwo.get("data").get("position").get(0));
                assertNotEquals(1, responseTwo.get("data").get("position").get(1));
        
                assertNotNull(responseTwo.get("state"));
        
                // Client Three Connects. LAUNCH should SUCCEED.
                assertTrue(serverClientThree.isConnected());
        
                String clientThreeRequest = "{" +
                        "  \"robot\": \"THREE\"," +
                        "  \"command\": \"launch\"," +
                        "  \"arguments\": [\"shooter\", \"5\", \"5\"]" +
                        "}";
                JsonNode responseThree = serverClientThree.sendRequest(clientThreeRequest);
                
                assertNotNull(responseThree.get("result"));
                assertEquals("OK", responseThree.get("result").asText());
        
                assertNotNull(responseThree.get("data"));
                assertNotNull(responseThree.get("data").get("position"));
                assertNotEquals(1, responseThree.get("data").get("position").get(0));
                assertNotEquals(1, responseThree.get("data").get("position").get(1));
        
                assertNotNull(responseThree.get("state"));
        
                // Client Four Connects. LAUNCH should SUCCEED.
                assertTrue(serverClientFour.isConnected());
        
                String clientFourRequest = "{" +
                        "  \"robot\": \"FOUR\"," +
                        "  \"command\": \"launch\"," +
                        "  \"arguments\": [\"shooter\", \"5\", \"5\"]" +
                        "}";
                JsonNode responseFour = serverClientFour.sendRequest(clientFourRequest);
                
                assertNotNull(responseFour.get("result"));
                assertEquals("OK", responseFour.get("result").asText());
        
                assertNotNull(responseFour.get("data"));
                assertNotNull(responseFour.get("data").get("position"));
                assertNotEquals(1, responseFour.get("data").get("position").get(0));
                assertNotEquals(1, responseFour.get("data").get("position").get(1));
        
                assertNotNull(responseFour.get("state"));
        
                // Client Five Connects. LAUNCH should SUCCEED.
                assertTrue(serverClientFive.isConnected());
        
                String clientFiveRequest = "{" +
                        "  \"robot\": \"FIVE\"," +
                        "  \"command\": \"launch\"," +
                        "  \"arguments\": [\"shooter\", \"5\", \"5\"]" +
                        "}";
                JsonNode responseFive = serverClientFive.sendRequest(clientFiveRequest);
                
                assertNotNull(responseFive.get("result"));
                assertEquals("OK", responseFive.get("result").asText());
        
                assertNotNull(responseFive.get("data"));
                assertNotNull(responseFive.get("data").get("position"));
                assertNotEquals(1, responseFive.get("data").get("position").get(0));
                assertNotEquals(1, responseFive.get("data").get("position").get(1));
        
                assertNotNull(responseFive.get("state"));
        
                // Client Six Connects. LAUNCH should SUCCEED.
                assertTrue(serverClientSix.isConnected());
        
                String clientSixRequest = "{" +
                        "  \"robot\": \"SIX\"," +
                        "  \"command\": \"launch\"," +
                        "  \"arguments\": [\"shooter\", \"5\", \"5\"]" +
                        "}";
                JsonNode responseSix = serverClientSix.sendRequest(clientSixRequest);
                
                assertNotNull(responseSix.get("result"));
                assertEquals("OK", responseSix.get("result").asText());
        
                assertNotNull(responseSix.get("data"));
                assertNotNull(responseSix.get("data").get("position"));
                assertNotEquals(1, responseSix.get("data").get("position").get(0));
                assertNotEquals(1, responseSix.get("data").get("position").get(1));
        
                assertNotNull(responseSix.get("state"));
        
                // Client Seven Connects. LAUNCH should SUCCEED.
                assertTrue(serverClientSeven.isConnected());
        
                String clientSevenRequest = "{" +
                        "  \"robot\": \"SEVEN\"," +
                        "  \"command\": \"launch\"," +
                        "  \"arguments\": [\"shooter\", \"5\", \"5\"]" +
                        "}";
                JsonNode responseSeven = serverClientSeven.sendRequest(clientSevenRequest);
                
                assertNotNull(responseSeven.get("result"));
                assertEquals("OK", responseSeven.get("result").asText());
        
                assertNotNull(responseSeven.get("data"));
                assertNotNull(responseSeven.get("data").get("position"));
                assertNotEquals(1, responseSeven.get("data").get("position").get(0));
                assertNotEquals(1, responseSeven.get("data").get("position").get(1));
        
                assertNotNull(responseSeven.get("state"));
        
                // Client Eight Connects. LAUNCH should SUCCEED.
                assertTrue(serverClientEight.isConnected());
        
                String clientEightRequest = "{" +
                        "  \"robot\": \"EIGHT\"," +
                        "  \"command\": \"launch\"," +
                        "  \"arguments\": [\"shooter\", \"5\", \"5\"]" +
                        "}";
                JsonNode responseEight = serverClientEight.sendRequest(clientEightRequest);
                
                assertNotNull(responseEight.get("result"));
                assertEquals("OK", responseEight.get("result").asText());
        
                assertNotNull(responseEight.get("data"));
                assertNotNull(responseEight.get("data").get("position"));
                assertNotEquals(1, responseEight.get("data").get("position").get(0));
                assertNotEquals(1, responseEight.get("data").get("position").get(1));
        
                assertNotNull(responseEight.get("state"));
    }

    @Test
    void worldWithAnObstacleIsFull2x2() {
                // Client One Connects. LAUNCH should SUCCEED.
                assertTrue(serverClient.isConnected());

                String clientOneRequest = "{" +
                        "  \"robot\": \"ONE\"," +
                        "  \"command\": \"launch\"," +
                        "  \"arguments\": [\"shooter\", \"5\", \"5\"]" +
                        "}";
                JsonNode responseOne = serverClient.sendRequest(clientOneRequest);
                
                assertNotNull(responseOne.get("result"));
                assertEquals("OK", responseOne.get("result").asText());
        
                assertNotNull(responseOne.get("data"));
                assertNotNull(responseOne.get("data").get("position"));
                assertNotEquals(1, responseOne.get("data").get("position").get(0));
                assertNotEquals(1, responseOne.get("data").get("position").get(1));
        
                assertNotNull(responseOne.get("state"));
        
                // Client Two Connects. LAUNCH should SUCCEED.
                assertTrue(serverClientTwo.isConnected());
        
                String clientTwoRequest = "{" +
                        "  \"robot\": \"TWO\"," +
                        "  \"command\": \"launch\"," +
                        "  \"arguments\": [\"shooter\", \"5\", \"5\"]" +
                        "}";
                JsonNode responseTwo = serverClientTwo.sendRequest(clientTwoRequest);
                
                assertNotNull(responseTwo.get("result"));
                assertEquals("OK", responseTwo.get("result").asText());
        
                assertNotNull(responseTwo.get("data"));
                assertNotNull(responseTwo.get("data").get("position"));
                assertNotEquals(1, responseTwo.get("data").get("position").get(0));
                assertNotEquals(1, responseTwo.get("data").get("position").get(1));
        
                assertNotNull(responseTwo.get("state"));
        
                // Client Three Connects. LAUNCH should SUCCEED.
                assertTrue(serverClientThree.isConnected());
        
                String clientThreeRequest = "{" +
                        "  \"robot\": \"THREE\"," +
                        "  \"command\": \"launch\"," +
                        "  \"arguments\": [\"shooter\", \"5\", \"5\"]" +
                        "}";
                JsonNode responseThree = serverClientThree.sendRequest(clientThreeRequest);
                
                assertNotNull(responseThree.get("result"));
                assertEquals("OK", responseThree.get("result").asText());
        
                assertNotNull(responseThree.get("data"));
                assertNotNull(responseThree.get("data").get("position"));
                assertNotEquals(1, responseThree.get("data").get("position").get(0));
                assertNotEquals(1, responseThree.get("data").get("position").get(1));
        
                assertNotNull(responseThree.get("state"));
        
                // Client Four Connects. LAUNCH should SUCCEED.
                assertTrue(serverClientFour.isConnected());
        
                String clientFourRequest = "{" +
                        "  \"robot\": \"FOUR\"," +
                        "  \"command\": \"launch\"," +
                        "  \"arguments\": [\"shooter\", \"5\", \"5\"]" +
                        "}";
                JsonNode responseFour = serverClientFour.sendRequest(clientFourRequest);
                
                assertNotNull(responseFour.get("result"));
                assertEquals("OK", responseFour.get("result").asText());
        
                assertNotNull(responseFour.get("data"));
                assertNotNull(responseFour.get("data").get("position"));
                assertNotEquals(1, responseFour.get("data").get("position").get(0));
                assertNotEquals(1, responseFour.get("data").get("position").get(1));
        
                assertNotNull(responseFour.get("state"));
        
                // Client Five Connects. LAUNCH should SUCCEED.
                assertTrue(serverClientFive.isConnected());
        
                String clientFiveRequest = "{" +
                        "  \"robot\": \"FIVE\"," +
                        "  \"command\": \"launch\"," +
                        "  \"arguments\": [\"shooter\", \"5\", \"5\"]" +
                        "}";
                JsonNode responseFive = serverClientFive.sendRequest(clientFiveRequest);
                
                assertNotNull(responseFive.get("result"));
                assertEquals("OK", responseFive.get("result").asText());
        
                assertNotNull(responseFive.get("data"));
                assertNotNull(responseFive.get("data").get("position"));
                assertNotEquals(1, responseFive.get("data").get("position").get(0));
                assertNotEquals(1, responseFive.get("data").get("position").get(1));
        
                assertNotNull(responseFive.get("state"));
        
                // Client Six Connects. LAUNCH should SUCCEED.
                assertTrue(serverClientSix.isConnected());
        
                String clientSixRequest = "{" +
                        "  \"robot\": \"SIX\"," +
                        "  \"command\": \"launch\"," +
                        "  \"arguments\": [\"shooter\", \"5\", \"5\"]" +
                        "}";
                JsonNode responseSix = serverClientSix.sendRequest(clientSixRequest);
                
                assertNotNull(responseSix.get("result"));
                assertEquals("OK", responseSix.get("result").asText());
        
                assertNotNull(responseSix.get("data"));
                assertNotNull(responseSix.get("data").get("position"));
                assertNotEquals(1, responseSix.get("data").get("position").get(0));
                assertNotEquals(1, responseSix.get("data").get("position").get(1));
        
                assertNotNull(responseSix.get("state"));
        
                // Client Seven Connects. LAUNCH should SUCCEED.
                assertTrue(serverClientSeven.isConnected());
        
                String clientSevenRequest = "{" +
                        "  \"robot\": \"SEVEN\"," +
                        "  \"command\": \"launch\"," +
                        "  \"arguments\": [\"shooter\", \"5\", \"5\"]" +
                        "}";
                JsonNode responseSeven = serverClientSeven.sendRequest(clientSevenRequest);
                
                assertNotNull(responseSeven.get("result"));
                assertEquals("OK", responseSeven.get("result").asText());
        
                assertNotNull(responseSeven.get("data"));
                assertNotNull(responseSeven.get("data").get("position"));
                assertNotEquals(1, responseSeven.get("data").get("position").get(0));
                assertNotEquals(1, responseSeven.get("data").get("position").get(1));
        
                assertNotNull(responseSeven.get("state"));
        
                // Client Eight Connects. LAUNCH should SUCCEED.
                assertTrue(serverClientEight.isConnected());
        
                String clientEightRequest = "{" +
                        "  \"robot\": \"EIGHT\"," +
                        "  \"command\": \"launch\"," +
                        "  \"arguments\": [\"shooter\", \"5\", \"5\"]" +
                        "}";
                JsonNode responseEight = serverClientEight.sendRequest(clientEightRequest);
                
                assertNotNull(responseEight.get("result"));
                assertEquals("OK", responseEight.get("result").asText());
        
                assertNotNull(responseEight.get("data"));
                assertNotNull(responseEight.get("data").get("position"));
                assertNotEquals(1, responseEight.get("data").get("position").get(0));
                assertNotEquals(1, responseEight.get("data").get("position").get(1));
        
                assertNotNull(responseEight.get("state"));

                // Client Nine Connects. LAUNCH should FAIL
                assertTrue(serverClientTen.isConnected());

                String clientNineRequest = "{" +
                        "  \"robot\": \"NINE\"," +
                        "  \"command\": \"launch\"," +
                        "  \"arguments\": [\"shooter\", \"5\", \"5\"]" +
                        "}";
                JsonNode responseNine = serverClientNine.sendRequest(clientNineRequest);
        
                assertNotNull(responseNine.get("result"));
                assertEquals("ERROR", responseNine.get("result").asText());
        
                assertNotNull(responseNine.get("data"));
                assertEquals("No more space in this world", responseNine.get("data").get("message").asText());
    }
}