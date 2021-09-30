package za.co.wethinkcode.Server.Server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import za.co.wethinkcode.Server.Robot.Robot;
import za.co.wethinkcode.Server.World.World;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Server implements Runnable {

    private final BufferedReader in;
    private final PrintStream out;
    private Robot robot;
    private final World world;

    public Server(World world) {
        this.world = world;
        this.in = new BufferedReader(new InputStreamReader(System.in));
        this.out = new PrintStream(new ByteArrayOutputStream());
    }

    public Server(Socket socket, World world) throws IOException {
        this.world = world;
        String clientMachine = socket.getInetAddress().getHostName();
        System.out.println("Connection from " + clientMachine);

        out = new PrintStream(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
    }

    public void run() {
        try {
            String messageFromClient;
            JsonObject jsonMessage;
            RobotCommand command = new RobotCommand(this, world);
            while((messageFromClient = in.readLine()) != null) {
//                System.out.println("Message \"" + messageFromClient + "\" from " + clientMachine);
                jsonMessage  = JsonParser.parseString(messageFromClient).getAsJsonObject();
                command.NewCommand(jsonMessage);
            }
        } catch(IOException ex) {
            System.out.println("Shutting down single client server");
        } finally {
            // robot.setStatus(RobotStatus.DEAD);
            world.RemoveRobot(robot);
            closeQuietly();
        }
    }

    private void closeQuietly() {
        try { in.close(); out.close();
        } catch(IOException ignored) {}
    }

    public void sendResponse(String response){
        out.println(response);
        // System.out.println("Hello");
        out.flush();
    }

    public void setRobot(Robot robot){
        this.robot = robot;
    }

    public String getOutString() {
        // System.out.println(out.toString());
        // System.out.println("Hello");
        return this.out.toString();
    }
}