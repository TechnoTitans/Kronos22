package frc.robot.utils;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Robot;
import frc.robot.commands.AutoShoot;
import frc.robot.subsystems.Barrel;
import frc.robot.subsystems.BarrelTilt;
import frc.robot.subsystems.JankDrive;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.concurrent.Executors;

public class TitanDS extends CommandBase {
    private static final String RESPONSE = "OK";
    private static JankDrive drive;
    private static Barrel barrel;
    private static BarrelTilt barrelTilt;
    private static AutoShoot autoShoot;
    private static Relay spike;

    public TitanDS(JankDrive drive, Barrel barrel, BarrelTilt barrelTilt, AutoShoot autoShoot, Relay spike) {
        this.drive = drive;
        this.barrel = barrel;
        this.barrelTilt = barrelTilt;
        this.autoShoot = autoShoot;
        this.spike = spike;
        addRequirements(drive);
        addRequirements(barrel);
        addRequirements(barrelTilt);

        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(1683), 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        server.createContext("/", new FileHandler(Filesystem.getDeployDirectory().toPath().resolve("content/index.html")));
        server.createContext("/content/css/bootstrap.min.css", new FileHandler(Filesystem.getDeployDirectory().toPath().resolve("content/css/bootstrap.min.css")));
        server.createContext("/content/css/main.css", new FileHandler(Filesystem.getDeployDirectory().toPath().resolve("content/css/main.css")));
        server.createContext("/content/js/bootstrap.bundle.min.js", new FileHandler(Filesystem.getDeployDirectory().toPath().resolve("content/js/bootstrap.bundle.min.js")));
        server.createContext("/content/js/jquery.js", new FileHandler(Filesystem.getDeployDirectory().toPath().resolve("content/js/jquery.js")));
        server.createContext("/content/js/main.js", new FileHandler(Filesystem.getDeployDirectory().toPath().resolve("content/js/main.js")));

        server.createContext("/drive", new DriveReader());
        server.createContext("/shoot", new ShootReader());
        server.createContext("/tilt", new TiltReader());
        server.createContext("/mode", new ModeReader());
        server.createContext("/time", new TimeReader());
        server.setExecutor(Executors.newCachedThreadPool());
        server.start();
    }

    private static class FileHandler implements HttpHandler {
        final Path path;
        public FileHandler (Path path) {this.path = path;}

        @Override
        public void handle(HttpExchange t) throws IOException {
            File file = new File(path.toString());
            InputStream in = new BufferedInputStream(new FileInputStream(file));
            t.sendResponseHeaders(200, file.length());
            OutputStream os = t.getResponseBody();
            os.write(in.readAllBytes());
            os.flush();
            os.close();
        }
    }

    private static class DriveReader implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            if (!t.getRequestMethod().equalsIgnoreCase("POST")) t.sendResponseHeaders(404, 0);
            // {"x":0,"y":0}

            String json = new String(t.getRequestBody().readAllBytes());
            String[] out = json.substring(1, json.length() - 1).replaceAll("\".\":", "").split(",");

            double x = Double.valueOf(out[0])/100.0;
            double y = Double.valueOf(out[1])/100.0;
            drive.set(y + x, y - x);

            t.sendResponseHeaders(200, RESPONSE.length());
            OutputStream os = t.getResponseBody();
            os.write(RESPONSE.getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();
        }
    }

    private static class ShootReader implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            if (!t.getRequestMethod().equalsIgnoreCase("POST")) t.sendResponseHeaders(404, 0);

            CommandScheduler.getInstance().schedule(autoShoot);

            t.sendResponseHeaders(200, RESPONSE.length());
            OutputStream os = t.getResponseBody();
            os.write(RESPONSE.getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();
        }
    }

    private static class TiltReader implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            if (!t.getRequestMethod().equalsIgnoreCase("POST")) t.sendResponseHeaders(404, 0);

            String json = new String(t.getRequestBody().readAllBytes());
            double tiltVal = Double.parseDouble(json.substring(1, json.length() - 1).replaceAll("\".*\":", "").replace("\"", ""));
            barrelTilt.set(tiltVal);

            t.sendResponseHeaders(200, RESPONSE.length());
            OutputStream os = t.getResponseBody();
            os.write(RESPONSE.getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();
        }
    }

    private static class ModeReader implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            if (!t.getRequestMethod().equalsIgnoreCase("POST")) t.sendResponseHeaders(404, 0);

            String json = new String(t.getRequestBody().readAllBytes());
            boolean compressorMode = !Boolean.parseBoolean(json.substring(1, json.length() - 1).replaceAll("\".*\":", ""));

            if (compressorMode) {
                spike.set(Relay.Value.kForward);
            } else {
                spike.set(Relay.Value.kOff);
            }

            t.sendResponseHeaders(200, RESPONSE.length());
            OutputStream os = t.getResponseBody();
            os.write(RESPONSE.getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();
        }
    }

    private static class TimeReader implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            if (!t.getRequestMethod().equalsIgnoreCase("POST")) t.sendResponseHeaders(404, 0);

            String json = new String(t.getRequestBody().readAllBytes());
            int timeVal = Integer.parseInt(json.substring(1, json.length() - 1).replaceAll("\".*\":", "").replace("\"", ""));

            Robot.shoot_delay = timeVal;
            if (Robot.shoot_delay < 25) {
                Robot.shoot_delay = 25;
            }
            t.sendResponseHeaders(200, RESPONSE.length());
            OutputStream os = t.getResponseBody();
            os.write(RESPONSE.getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();

        }
    }
}
