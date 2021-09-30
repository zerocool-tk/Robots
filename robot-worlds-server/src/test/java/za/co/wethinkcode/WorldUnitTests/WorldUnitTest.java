package za.co.wethinkcode.WorldUnitTests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import za.co.wethinkcode.Server.World.World;
import za.co.wethinkcode.Server.Robot.Robot;
import za.co.wethinkcode.Server.Server.Server;
import za.co.wethinkcode.Server.World.Obstacle;
import za.co.wethinkcode.Server.World.Pit;

import java.util.ArrayList;
import java.util.Hashtable;

public class WorldUnitTest {
    
    private String name = "HAL";
    private int shield = 0;
    private int shots = 0;
    
    private World world = new World();
    private Server server = new Server(world);
    private Robot robot = new Robot(name, world, server, shield, shots);
    
    Obstacle obstacle = new Obstacle(5,10,5);
    Pit pit = new Pit(20,30,5);
    
    ArrayList<Obstacle> obstacles = new ArrayList<>();
    ArrayList<Pit> pits = new ArrayList<>();
    
    World randomObstacles;
    World customObstacles;
    
    // WorldTest(){
        //     pits.add(pit);
        //     obstacles.add(obstacle);
        //     randomObstacles = new World();
        //     customObstacles = new World(pits,obstacles);
        // }
        
        // @Test
        // void testGetObstacles(){
            //     assertEquals(10, randomObstacles.getObstacles().size());
            //     assertEquals(obstacles, customObstacles.getObstacles());
            // }
            
            // @Test
    // void testGetPits(){
    //     assertEquals(10, randomObstacles.getPits().size());
    //     assertEquals(pits, customObstacles.getPits());
    // }

    @Test
    void canAddRobotToWorld() {
        world.AddRobot(robot);

        // Robot testDummy = world.robotExists(robot.getName());
        // assertNotNull(testDummy);
        // assertEquals("HAL", testDummy.getName());

    }
}
