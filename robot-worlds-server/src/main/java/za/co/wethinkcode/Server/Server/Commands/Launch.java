package za.co.wethinkcode.Server.Server.Commands;


import java.io.BufferedOutputStream;
import java.io.PrintStream;

import za.co.wethinkcode.Server.World.World;
import za.co.wethinkcode.Server.Server.Server;
import za.co.wethinkcode.Server.Robot.Robot;

public class Launch {

    private World world;
    private Server server;

    public Launch(World world, Server server) {
        this.world = world;
        this.server = server;
    }

    public boolean worldIsFull(World world) {

        int currentNoRobots = world.getRobots().size();
        int currentNoObstacles = world.getObstacles().size() + world.getPits().size();
        int worldSize = world.getWorldHeight();
        int maximumNoRobots = (worldSize + 1) * (worldSize + 1);

        return currentNoRobots + currentNoObstacles >= maximumNoRobots;
    }

    public void sendResponse(Server server, String response) {
        server.sendResponse(response);
    }

    public boolean robotExists(String robotName) {
            return world.robotExists(robotName);

    }



}
