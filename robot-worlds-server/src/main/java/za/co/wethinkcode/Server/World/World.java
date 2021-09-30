package za.co.wethinkcode.Server.World;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import za.co.wethinkcode.Server.Robot.Robot;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class World {
    ArrayList<Robot> allRobots = new ArrayList<>();
    HashMap<String, Robot> robotsInWorld = new HashMap<>();
    ArrayList<Obstacle> obstacles = new ArrayList<>();
    ArrayList<Pit> pits = new ArrayList<>();
    ArrayList<Mine> mines = new ArrayList<>();

    private int worldWidth;
    private int worldHeight;
    private int visibility;
    private int repairShield;
    private int reload;
    private int steMineTime;
    private int maxShield;

    HashMap<String, Integer> sniperRobot;
    HashMap<String, Integer> pistolRobot;
    HashMap<String, Integer> standardRobot;
    private int DEFAULT_PORT = 5000;
    private String DEFAULT_HOST = "localhost";

    public World(){
        try {
            File input = new File("config/WorldSpecs.json");
            JsonElement fileElement = JsonParser.parseReader(new FileReader(input));
            JsonObject fileObject = fileElement.getAsJsonObject();

            //Extracting basic fields

            worldHeight = fileObject.get("height").getAsInt();
            worldWidth = fileObject.get("width").getAsInt();
            visibility = fileObject.get("visibility").getAsInt();
            repairShield = fileObject.get("repair").getAsInt();
            reload = fileObject.get("reload").getAsInt();
            steMineTime = fileObject.get("mine").getAsInt();
            maxShield = fileObject.get("maxShield").getAsInt();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // createDesignedObstacles();
        // createDesignedPits();
        setRobotParams();
    }

    public void setWorldObstacles(ArrayList<Pit> pits, ArrayList<Obstacle> obstacles){
        this.pits = pits;
        this.obstacles = obstacles;
    }

    public void setRobotParams() {
        //Sets config details based on robot type.

        sniperRobot = new HashMap<>();
        sniperRobot.put("shot-distance",4);
        sniperRobot.put("shots",2);
        sniperRobot.put("shield-strength",3);


        pistolRobot = new HashMap<>();
        pistolRobot.put("shot-distance",2);
        pistolRobot.put("shots",4);
        pistolRobot.put("shield-strength",3);


        standardRobot = new HashMap<>();
        standardRobot.put("shot-distance",3);
        standardRobot.put("shots",3);
        standardRobot.put("shield-strength",3);
    }

    public void RemoveRobot(Robot robot){
        allRobots.remove(robot);
        robot.UnLaunch();
    }

    private void createDesignedObstacles() {
        //Make obstacle list empty
        Obstacle newObstacle = new Obstacle(0 , -47, 1);
        obstacles.add(newObstacle);
        newObstacle = new Obstacle(16 , -47, 1);
        obstacles.add(newObstacle);
        newObstacle = new Obstacle(32 , -47, 1);
        obstacles.add(newObstacle);
        newObstacle = new Obstacle(44 , -47, 1);
        obstacles.add(newObstacle);
        newObstacle = new Obstacle(44 , 8, 1);
        obstacles.add(newObstacle);
        newObstacle = new Obstacle(44 , 33, 1);
        obstacles.add(newObstacle);
        newObstacle = new Obstacle(44 , 58, 1);
        obstacles.add(newObstacle);
        newObstacle = new Obstacle(32 , 73, 1);
        obstacles.add(newObstacle);
        newObstacle = new Obstacle(16 , 83, 1);
        obstacles.add(newObstacle);
        newObstacle = new Obstacle(0 , 93, 1);
        obstacles.add(newObstacle);
        newObstacle = new Obstacle(0 , 11, 1);
        obstacles.add(newObstacle);
        newObstacle = new Obstacle(-5 , 13, 1);
        obstacles.add(newObstacle);
        newObstacle = new Obstacle(-9 , 14, 1);
        obstacles.add(newObstacle);
        newObstacle = new Obstacle(-13 , 8, 1);
        obstacles.add(newObstacle);
        newObstacle = new Obstacle(-12 , -1, 1);
        obstacles.add(newObstacle);
        newObstacle = new Obstacle(-7 , -9, 1);
        obstacles.add(newObstacle);
        newObstacle = new Obstacle(0 , -15, 1);
        obstacles.add(newObstacle);
    }

    private void createDesignedPits() {
        //Make obstacle list empty
        Pit newPit = new Pit(-16, -47,1);
        pits.add(newPit);
        newPit =  new Pit(-32, -47,1);
        pits.add(newPit);
        newPit =  new Pit(-44, -47, 1);
        pits.add(newPit);
        newPit =  new Pit(-44, -17, 1);
        pits.add(newPit);
        newPit =  new Pit(-44, 8, 1);
        pits.add(newPit);
        newPit =  new Pit(-44, 33, 1);
        pits.add(newPit);
        newPit =  new Pit(-44, 58, 1);
        pits.add(newPit);
        newPit =  new Pit(-32,73,1);
        pits.add(newPit);
        newPit =  new Pit(-16, 83, 1);
        pits.add(newPit);
        newPit =  new Pit(-7, -9, 1);
        pits.add(newPit);
        newPit =  new Pit(-12, -1, 1);
        pits.add(newPit);
        newPit =  new Pit(-13, 8, 1);
        pits.add(newPit);
        newPit =  new Pit(-9, 14, 1);
        pits.add(newPit);
        newPit =  new Pit(-5, 13, 1);
        pits.add(newPit);
    }

    public ArrayList<Obstacle> getObstacles(){
        return obstacles;
    }

    public ArrayList<Pit> getPits(){
        return pits;
    }

    public ArrayList<Robot> getRobots() { return allRobots; }

    public void AddRobot(Robot robot){
        // System.out.println(allRobots);

        allRobots.add(robot);
        robotsInWorld.put(robot.getName(), robot);
    }

    public int getWorldWidth(){
        return worldWidth;
    }

    public int getWorldHeight() {
        return worldHeight;
    }

    public void addMine(Mine mine){
        mines.add(mine);
    }

    public void removeMine(Mine mine){
        mines.remove(mine);
    }

    public ArrayList<Mine> getMines(){
        return mines;
    }

    public int getVisibility() {
        return visibility;
    }

    public int getReload() {
        return reload;
    }

    public int getMaxShield() {
        return maxShield;
    }

    public int getSteMineTime() {
        return steMineTime;
    }

    public int getRepairShield() {
        return repairShield;
    }

    public void setDefaultPort(int newPortValue) {
        this.DEFAULT_PORT = newPortValue;
    }

    public int getDefaultPort() {
        return this.DEFAULT_PORT;
    }

    public void setDefaultHost(String newHostAddress) {
        this.DEFAULT_HOST = newHostAddress;
    }

    public String getDefaultHost() {
        return this.DEFAULT_HOST;
    }

    public void resetWorldServer() {
        DEFAULT_HOST = "localhost";
        DEFAULT_PORT = 5000;
    }

    public void setWorldSize(int size) {
        worldHeight = size;
        worldWidth = size;
    }

    public String getWorldSize() {

        String worldSize = "Size is " + (worldHeight) + "x" + (worldWidth);

        return worldSize;
    }

    public boolean robotExists(String robotName) {
        try {
            // System.out.println(robotName);
            return robotsInWorld.get(robotName).hasLaunched();
        } catch (NullPointerException e)  {
            return false;
        }
    }



}