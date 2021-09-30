package za.co.wethinkcode.Server.Server;

import org.json.JSONObject;
import za.co.wethinkcode.Server.Robot.Robot;
// import za.co.wethinkcode.Server.World.Direction;
import za.co.wethinkcode.Server.World.Mine;
// import za.co.wethinkcode.Server.World.ObjectTypes;
import za.co.wethinkcode.Server.World.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LookRobot {
    public int range;
    private Robot robot;
    private final Response response = new Response();
    private final World world;
    private final Server server;
    public int i = 1;

    HashMap<String, Object> object = new HashMap<>();
    List<HashMap<String, Object>> objects = new ArrayList<>();
    boolean objectFound = false;
    private JSONObject obj;

    public LookRobot(Server server, World world) {
        this.world = world;
        this.server = server;
        range = world.getVisibility();
    }

    public JSONObject getObject(String direction, String type, int distance) {
        object.put("direction", direction);
        object.put("type", type);
        object.put("distance", distance);
        objects.add(object);
        return new JSONObject(objects);
    }

    public JSONObject otherRobot() {
        for (Robot otherRobot: world.getRobots()){
            if (otherRobot.getX() == robot.getX() && otherRobot.getY() == robot.getY() + i) {
                obj = getObject("NORTH", "ROBOT", i);
                objectFound = true;
                break;
            }
            else if (otherRobot.getX() == robot.getX() && otherRobot.getY() == robot.getY() - i) {
                obj = getObject("SOUTH", "ROBOT", i);
                objectFound = true;
                break;
            }
            else if (otherRobot.getX() == robot.getX() + i && otherRobot.getY() == robot.getY()) {
                obj = getObject("EAST", "ROBOT", i);
                objectFound = true;
                break;
            }
            else if (otherRobot.getX() == robot.getX() - i && otherRobot.getY() == robot.getY() + i) {
                obj = getObject("WEST", "ROBOT", i);
                objectFound = true;
                break;
            }
        }
        return obj;

    }
    
}
