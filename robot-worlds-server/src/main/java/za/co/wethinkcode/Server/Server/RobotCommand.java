package za.co.wethinkcode.Server.Server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import za.co.wethinkcode.Server.Robot.MovementStatus;
import za.co.wethinkcode.Server.Robot.Robot;
import za.co.wethinkcode.Server.Robot.RobotStatus;
import za.co.wethinkcode.Server.World.Mine;
import za.co.wethinkcode.Server.Server.Commands.Launch;
import za.co.wethinkcode.Server.World.World;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RobotCommand {
    private Robot robot;
    private boolean isInWorld = false;
    private final Response response = new Response();
    private final Server server;
    private final World world;
    public Look look;
    public LookEdge edge;
    public LookPit pit;
    public LookObstacle obstacle;
    public LookRobot otherBots;
    public LookMine mine;
    private int steps;
    private boolean isSuccessful;



    public RobotCommand(Server server, World world){
        this.server = server;
        this.world = world;
    }

    public void NewCommand(JsonObject newCommand){
        try {
            if(robot.getStatus() == RobotStatus.DEAD){
                isInWorld = false;
                robot.UnLaunch();
            }
        }
        catch (NullPointerException ignored) {

        }
        try {
            switch (newCommand.get("command").getAsString()) {

                case "launch":
                    // boolean nameRepeat = false;
                    // if(world.getRobots().size() >= 8){
                    //     server.sendResponse(response.LaunchNoSpace());
                    //     break;
                    // }
                    // for (Robot robot: world.getRobots()){
                    //     if(robot.getName().equals(newCommand.get("robot").getAsString())){
                    //         server.sendResponse(response.LaunchNameTaken());
                    //         nameRepeat = true;
                    //         break;
                    //     }
                    // }
                    // if(nameRepeat){
                    //     break;
                    // }
                    // if(!isInWorld){

                    //     isInWorld = true;
                    //     robot = new Robot(newCommand.get("robot").getAsString(), world, server,
                    //             newCommand.get("arguments").getAsJsonArray().get(1).getAsInt(), newCommand.get("arguments").getAsJsonArray().get(2).getAsInt());
                    //     server.sendResponse(response.LaunchSuccess(robot));
                    //     System.out.println(robot);
                    //     server.setRobot(robot);
                    // }
                    
                    Launch command = new Launch(world, server);
                    boolean nameRepeat = false;
                    if (command.worldIsFull(world)) {
                        command.sendResponse(server, response.LaunchNoSpace());
                        break;
                    }
                    if (command.robotExists(newCommand.get("robot").getAsString())) {
                        System.out.println("name");
                        command.sendResponse(server, response.LaunchNameTaken());
                        nameRepeat = true;
                        break;
                    }
                    if (nameRepeat) {
                        break;
                    }
                    if (!isInWorld) {
                        isInWorld = true;
                        robot = new Robot(newCommand.get("robot").getAsString(), world, server,
                        newCommand.get("arguments").getAsJsonArray().get(1).getAsInt(), newCommand.get("arguments").getAsJsonArray().get(2).getAsInt());
                        command.sendResponse(server, response.LaunchSuccess(robot));
                        server.setRobot(robot);
                    }
                    break;

                case "state":
                    server.sendResponse(response.State(robot));
                    break;

                case "forward":
                    if(robot.getStatus()== RobotStatus.DEAD){
                        server.sendResponse(response.State(robot));
                        break;
                    }
                    steps = newCommand.get("arguments").getAsJsonArray().get(0).getAsInt();
                    boolean isSuccessful = true;
                    for (int i = 0; i < steps; i++){
                        MovementStatus movementStatus = robot.moveForward();
                        if(movementStatus == MovementStatus.Obstructed){
                            isSuccessful = false;
                            server.sendResponse(response.MovementObstructed(robot));
                            break;
                        }
                        if(movementStatus == MovementStatus.Fell){
                            isSuccessful = false;
                            server.sendResponse(response.MovementIntoPit());
                            robot.setStatus(RobotStatus.DEAD);
                            world.RemoveRobot(robot);
                            break;
                        }
                        if (movementStatus == MovementStatus.Mine){
                            isSuccessful = false;
                            server.sendResponse(response.stepOnMine(robot));
                            break;
                        }
                    }
                    if(isSuccessful){
                        server.sendResponse(response.MovementSuccess(robot));
                    }
                    break;

                case "back":
                    if(robot.getStatus()== RobotStatus.DEAD){
                        server.sendResponse(response.State(robot));
                        break;
                    }
                    steps = -newCommand.get("arguments").getAsJsonArray().get(0).getAsInt();
                    isSuccessful = true;
                    for (int i = 0; i < -steps; i++){
                        MovementStatus movementStatus = robot.moveBack();
                        if(movementStatus == MovementStatus.Obstructed){
                            isSuccessful = false;
                            server.sendResponse(response.MovementObstructed(robot));
                            break;
                        }
                        if(movementStatus == MovementStatus.Fell){
                            isSuccessful = false;
                            server.sendResponse(response.MovementIntoPit());
                            robot.setStatus(RobotStatus.DEAD);
                            world.RemoveRobot(robot);
                            break;
                        }
                        if (movementStatus == MovementStatus.Mine){
                            isSuccessful = false;
                            server.sendResponse(response.stepOnMine(robot));
                            break;
                        }
                    }
                    if(isSuccessful){
                        server.sendResponse(response.MovementSuccess(robot));
                    }
                    break;

                case "turn":
                    if(robot.getStatus()== RobotStatus.DEAD){
                        server.sendResponse(response.State(robot));
                        break;
                    }
                    if(newCommand.get("arguments").getAsString().equals("right")){
                        robot.turn(true);
                        server.sendResponse(response.Turn(robot));
                    }
                    else if(newCommand.get("arguments").getAsString().equals("left")){
                        robot.turn(false);
                        server.sendResponse(response.Turn(robot));
                    }
                    else {
                        server.sendResponse(response.InvalidArguments());
                    }
                    break;

                case "look":

                    if (robot.getStatus() == RobotStatus.DEAD) {
                        look.deadRobot();
                        break;
                    }

                    for (int i = 1; i <= look.range; i++) { 
                        if(robot.getX() + i == world.getWorldWidth()/2) {
                            edge.eastEdge();
                            break;
                        }
                        else if(robot.getX() - i == -world.getWorldWidth()/2) {
                            edge.westEdge();
                            break;
                        }
                        else if(robot.getY() - i == -world.getWorldHeight()/2) {
                            edge.southEdge();
                            break;
                        }
                        else if (robot.getY() + i == world.getWorldHeight()/2){
                            edge.northEdge();
                            break;
                        }


                        if (robot.pitAtPosition(robot.getX() + i, robot.getY())) {
                            pit.eastPit();
                            break;
                        }
                        else if(robot.pitAtPosition(robot.getX() - i, robot.getY())) {
                            pit.westPit();
                            break;
                        }
                        else if(robot.pitAtPosition(robot.getX(), robot.getY() - i)) {
                            pit.southPit();
                            break;
                        }
                        else if(robot.pitAtPosition(robot.getX(), robot.getY() + i)) {
                            pit.northPit();
                            break;
                        }


                        if (robot.obstacleAtPosition(robot.getX() + i, robot.getY())) {
                            obstacle.eastObstacle();
                            break;
                        }
                        else if(robot.obstacleAtPosition(robot.getX() - i, robot.getY())) {
                            obstacle.westObstacle();
                            break;
                        }
                        else if(robot.obstacleAtPosition(robot.getX(), robot.getY() - i)) {
                            obstacle.southObstacle();
                            break;
                        }
                        else if (robot.obstacleAtPosition(robot.getX(), robot.getY() + i)) {
                            obstacle.northObstacle();
                            break;
                        }

                        otherBots.otherRobot();

                        if (i <= look.range / 4) {
                            mine.minePosition();
                        }

                        if (look.objectFound) {
                            break;
                        }
                    }
//
//                    server.sendResponse(response.Look(robot, objects));
                    break;
                case "fire":
                    if (robot.getNumberShots() == 0) {
                        server.sendResponse(response.fireNoAmmo());
                        break;
                    }

                    boolean beenHit = robot.beenHit();

                    if(robot.getStatus()== RobotStatus.DEAD){
                        server.sendResponse(response.State(robot));
                        break;
                    }

                    if (beenHit) {
                        //int shields, int injuredShots, String robotStatus, int shots
                        server.sendResponse(
                                response.HitRobot(robot.getDistance(), robot.getNumberShots(),
                                        robot.returnHitRobot()));
                    } else {
                        server.sendResponse(response.MissedRobot(robot));
                    }
                    break;

                case "reload":
                    if(robot.getStatus()== RobotStatus.DEAD){
                        server.sendResponse(response.State(robot));
                        break;
                    }
                    robot.setStatus(RobotStatus.RELOADING);
                    server.sendResponse(response.State(robot));
                    robot.reload();
                    server.sendResponse(response.Reload(robot));
                    break;

                case "repair":
                    if (robot.getStatus() == RobotStatus.DEAD) {
                        server.sendResponse(response.State(robot));
                        break;
                    }
                    robot.setStatus(RobotStatus.REPAIRING);
                    server.sendResponse(response.State(robot));
                    robot.repair();
                    server.sendResponse(response.Repair(robot));
                    break;
                    
                case "mine":
                    if(!robot.canSetMines){
                        server.sendResponse(response.cannotSetMines(robot));
                        break;
                    }
                    int x = robot.getX();
                    int y = robot.getY();
                    Mine mine = new Mine(x, y);
                    world.addMine(mine);
                    robot.setStatus(RobotStatus.SETMINE);
                    server.sendResponse(response.State(robot));
                    robot.setMine();
                    server.sendResponse(response.setMine(robot));

                    String[] mySteps = new String[1];
                    mySteps[0] = String.valueOf(1);
                    Gson gson = new Gson();
                    JsonObject finalResponse = new JsonObject();

                    finalResponse.addProperty("robot",robot.getName());

                    finalResponse.addProperty("command","forward");
                    finalResponse.add("arguments", gson.toJsonTree(mySteps));

                    this.NewCommand(finalResponse);

                    if(robot.getX() == x && robot.getY() == y){
                        robot.steppedOnMine();
                        world.removeMine(mine);
                        server.sendResponse(response.stepOnMine(robot));
                    }
                    break;

                default:
                    // if(robot.getStatus()== RobotStatus.DEAD){
                    //     server.sendResponse(response.State(robot));
                    //     break;
                    // }
                    server.sendResponse(response.UnsupportedCommand());
                }
                
        }
        catch (UnsupportedOperationException | NullPointerException | NumberFormatException e) {
            server.sendResponse(response.InvalidArguments());
        }

    }

}
