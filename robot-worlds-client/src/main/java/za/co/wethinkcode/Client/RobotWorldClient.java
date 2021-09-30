package za.co.wethinkcode.Client;

import com.fasterxml.jackson.databind.JsonNode;

public interface RobotWorldClient {
    
    void connect(String ipAddress, int port);

    boolean isConnected();

    void disconnect();

    JsonNode sendRequest(String requestJsonString);

    String sendRequestAsString(String requestString);
}
