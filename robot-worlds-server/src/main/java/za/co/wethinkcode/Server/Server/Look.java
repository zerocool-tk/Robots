package za.co.wethinkcode.Server.Server;

import org.json.JSONObject;
import za.co.wethinkcode.Server.Robot.Robot;
import za.co.wethinkcode.Server.World.Direction;
import za.co.wethinkcode.Server.World.Mine;
import za.co.wethinkcode.Server.World.ObjectTypes;
import za.co.wethinkcode.Server.World.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Look {
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


    public Look(Server server, World world) {
        this.world = world;
        this.server = server;
        range = world.getVisibility();
    }


    public JSONObject getObject(Direction direction, ObjectTypes type, int distance) {
        object.put("direction", direction);
        object.put("type", type);
        object.put("distance", distance);
        objects.add(object);
        return new JSONObject(objects);
    }


    public void deadRobot() {
        server.sendResponse(response.State(robot));
    }
}