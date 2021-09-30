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

public class LookAcceptanceTest {
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


    // @Test
    // void testlook_emptycommand1x1() {

    //         assertTrue(serverClient.isConnected());
    //         String request = "{" +
    //                 "  \"robot\": \"HAL\"," +
    //                 "  \"command\": \"launch\"," +
    //                 "  \"arguments\": [\"shooter\", \"5\", \"5\"]" +
    //                 "}";
    //         JsonNode response = serverClient.sendRequest(request);
            
    //         assertNotNull(response.get("result"));
    //         assertEquals("OK", response.get("result").asText());

    //         assertNotNull(response.get("data"));
    //         assertNotNull(response.get("data").get("position"));

    //         String request_1 = "{" +
    //                 "  \"robot\": \"HAL\"," +
    //                 "  \"command\": \"look\"," +
    //                 "  \"arguments\": []" +
    //                 "}";

    //         JsonNode response2 = serverClient.sendRequest(request_1);

    //         assertNotNull(response2.get("result"));
    //         assertEquals("OK", response2.get("result").asText());

    //         assertNotNull(response2.get("data"));
    //         assertNotNull(response2.get("data").get("objects"));
    //     }


    // @Test
    // void testlook_object_inview(){

    //     assertTrue(serverClient.isConnected());
    //     String request = "{" +
    //             "  \"robot\": \"HAL\"," +
    //             "  \"command\": \"launch\"," +
    //             "  \"arguments\": [\"shooter\", \"5\", \"5\"]" +
    //             "}";
    //     JsonNode response = serverClient.sendRequest(request);

    //     assertNotNull(response.get("result"));
    //     assertEquals("OK", response.get("result").asText());

    //     assertNotNull(response.get("data"));
    //     assertNotNull(response.get("data").get("position"));

    //     String request_1 = "{" +
    //             "  \"robot\": \"HAL\"," +
    //             "  \"command\": \"look\"," +
    //             "  \"arguments\": []" +
    //             "}";

    //     JsonNode response2 = serverClient.sendRequest(request_1);

    //     assertNotNull(response2.get("result"));
    //     assertEquals("OK", response2.get("result").asText());

    //     assertNotNull(response2.get("data"));
    //     assertNotNull(response2.get("data").get("objects"));

    //     Iterator<JsonNode> r = response2.get("data").get("objects").elements();
    //     List<JsonNode> actualList = new ArrayList<JsonNode>();
    //     r.forEachRemaining(actualList::add);
    //     assertEquals(4,actualList.size());
    // }
}
