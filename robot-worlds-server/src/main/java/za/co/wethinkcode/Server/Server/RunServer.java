package za.co.wethinkcode.Server.Server;

import za.co.wethinkcode.Server.World.World;
import za.co.wethinkcode.Server.World.Obstacle;
import za.co.wethinkcode.Server.World.Pit;
import za.co.wethinkcode.Server.Server.WorldCommands;

// import za.co.wethinkcode.Server.AcceptClients;

import java.io.*;
import java.util.ArrayList;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "robotWorldServer", mixinStandardHelpOptions = true, version = "0.00", description = "Runs robot world server.")
public class RunServer implements Runnable{
    private static ArrayList<Pit> pits = new ArrayList<>();
    private static ArrayList<Obstacle> obstacles = new ArrayList<>();
    private static World world;

    @Option(names = "-p", required = false, description = "Specify port")
    private int newPortValue;

    // @Parameters(index = "0..*", description = "Coordinates")
    // private String[] coord;

    @Option(names = "-o", split = " ",required = false, description = "Specify obstacles")
    private String[] obstacleCoordinates;

    @Option(names = "-s", required = false, description = "Specify size.")
    private int size;

    public static void main(String[] args) throws IOException {

        world = new World();

        int exitCode  = new CommandLine(new RunServer()).execute(args);

        // for (String arg : args ) {
        //     System.out.println(arg);
        // }


        // if (args != null && args.length > 0) {
        //     switch(args[0]) {
        //         case "Empty" -> {
        //            world.setWorldObstacles(pits, obstacles);
        //         }
        //     }
        // }

        System.out.println("Obstacles");
        for(Obstacle obstacle: world.getObstacles()){
            System.out.println("("+obstacle.getBottomLeftX()+","+obstacle.getBottomLeftY()+")");
        }
        System.out.println("\nPits");
        for(Pit pit: world.getPits()){
            System.out.println("("+pit.getBottomLeftX()+","+pit.getBottomLeftY()+")");
        }

        Runnable r = new AcceptClients(world);
        Thread task = new Thread(r);
        task.start();

        WorldCommands worldCommands = new WorldCommands(world);
        worldCommands.handleWorldCommands();

        System.exit(0);
    }

    @Override
    public void run() {
        if ( newPortValue != 0 ) {
            world.setDefaultPort(newPortValue);
            // System.out.println(world.getDefaultPort());
        }
        // obstacles.add(new Obstacle(Integer.parseInt(coord.split(",")[0]), Integer.parseInt(coord.split(",")[1]), 5));

        if ( obstacleCoordinates != null ) {
            for ( String coordinate : obstacleCoordinates ) {
                String[] bottomCoordinates = coordinate.split(",");
                obstacles.add(new Obstacle(Integer.parseInt(bottomCoordinates[0]), Integer.parseInt(bottomCoordinates[1]), 1));
            }
        }
        world.setWorldObstacles(pits, obstacles);
        
        if ( size != 0 ) {
            world.setWorldSize(size);
        }
    }
}