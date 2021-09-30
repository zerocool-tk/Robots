package za.co.wethinkcode.ServerUnitTests;

import za.co.wethinkcode.Server.World.World;
import za.co.wethinkcode.Server.Server.Server;
import za.co.wethinkcode.Server.Robot.Robot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

public class CommandLaunchTest {
    private PrintStream output = new PrintStream(new ByteArrayOutputStream());

    private String name = "HAL";
    private int shield = 0;
    private int shots = 0;
    
    private World world = new World();
    private Server server = new Server(world);
    private Robot robot = new Robot(name, world, server, shield, shots);


    // Consider moving this to server unit tests
    // @Test
    void canSendResponse() {
        System.setOut(output);

        server.sendResponse("Hello World!");

        assertEquals("Hello World!", server.getOutString());
        System.setOut(System.out);

    }


    
}
