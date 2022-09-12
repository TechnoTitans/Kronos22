package frc.robot.utils;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Barrel;
import frc.robot.subsystems.JankDrive;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.Executors;

public class TitanDS extends CommandBase {
    private static final String RESPONSE = "OK";
    private static JankDrive drive;
    private static Barrel barrel;

    public TitanDS(JankDrive drive, Barrel barrel) {
        this.drive = drive;
        addRequirements(drive);
        this.barrel = barrel;

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
//        server.createContext("/tilt", new ));
//        server.createContext("/mode", new ));
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

            SmartDashboard.putString("out", Arrays.toString(out));
            double x = Double.valueOf(out[0])/100.0;
            double y = Double.valueOf(out[1])/100.0;
            SmartDashboard.putNumber("x", x);
            SmartDashboard.putNumber("y", y);
            drive.set(y + x, y - x);
//            drive.set(1, 1);

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

            SmartDashboard.putString("fiaing", new String(t.getRequestBody().readAllBytes()));

            t.sendResponseHeaders(200, RESPONSE.length());
            OutputStream os = t.getResponseBody();
            os.write(RESPONSE.getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();

        }
    }
}
