package za.co.wethinkcode.Server.Server;

import za.co.wethinkcode.Server.Robot.RobotStatus;
import za.co.wethinkcode.Server.Robot.Robot;

import za.co.wethinkcode.Server.World.World;
import za.co.wethinkcode.Server.World.Obstacle;
import za.co.wethinkcode.Server.World.Pit;
import za.co.wethinkcode.Server.World.Mine;

import java.util.Locale;
import java.util.Scanner;

public class WorldCommands{
    static String command;
    static String argument;
    Scanner scanner;
    String input;
    World world;

    public WorldCommands(World world) {
        scanner = new Scanner(System.in);
        this.world = world;
    }

    public void handleWorldCommands(){
        boolean flag = true;
        while (flag){
            System.out.println("What is your bidding master?");
            input = scanner.nextLine();
            splitCommand(input);
            switch (command.toLowerCase(Locale.ROOT)){
                case "off":
                case"quit":
                case "exit":
                    flag = false;
                    break;

                case "robots":
                    System.out.println("Robots:");
                    for(Robot robot: world.getRobots()){
                        System.out.println("\n" + robot.getName() +", state:");
                        System.out.println("   Position: [" + robot.getX() +
                                ", " + robot.getY() + "]");
                        System.out.println("   Direction: " + robot.getCurrentDirection());
                        System.out.println("   Shields: " + robot.getShields());
                        System.out.println("   Shots: " + robot.getNumberShots());
                        System.out.println("   Status: " + robot.getStatus());
                    }
                    break;

                case "dump":
                    System.out.println("\nObstacles");
                    for(Obstacle obstacle: world.getObstacles()){
                        System.out.println("("+obstacle.getBottomLeftX()+","+obstacle.getBottomLeftY()+")");
                    }
                    System.out.println("\nPits");
                    for(Pit pit: world.getPits()){
                        System.out.println("("+pit.getBottomLeftX()+","+pit.getBottomLeftY()+")");
                    }
                    System.out.println("\nMines");
                    for(Mine mine: world.getMines()){
                        System.out.println("("+mine.getX()+","+mine.getY()+")");
                    }
                    for(Robot robot: world.getRobots()){
                        System.out.println("\n" + robot.getName() +", state:");
                        System.out.println("   Position: [" + robot.getX() +
                                ", " + robot.getY() + "]");
                        System.out.println("   Direction: " + robot.getCurrentDirection());
                        System.out.println("   Shields: " + robot.getShields());
                        System.out.println("   Shots: " + robot.getNumberShots());
                        System.out.println("   Status: " + robot.getStatus());
                    }
                    break;

                case "purge":
                    boolean purgedRobot = false;
                    for(Robot robot: world.getRobots()){
                        if(robot.getName().equals(argument)){
                            robot.setStatus(RobotStatus.DEAD);
                            world.RemoveRobot(robot);
                            purgedRobot = true;
                            break;
                        }
                    }
                    if(purgedRobot){
                        System.out.println("Master, I have destroyed this robot: " + argument);
                    }
                    else {
                        System.out.println("Master, I cannot find this robot: " + argument);
                    }
                    break;

                default:
                    System.out.println("Master, I did not understand this request: " + input);
            }
        }
    }

    public static void splitCommand(String input) {
        String [] commandAndArgs = input.split(" ");

        if (commandAndArgs.length == 2) {
            command = commandAndArgs[0];
            argument = commandAndArgs[1];
        } else {
            command = commandAndArgs[0];
        }
    }
}
