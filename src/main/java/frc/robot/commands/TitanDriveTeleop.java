package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BarrelTilt;
import frc.robot.subsystems.JankDrive;


public class TitanDriveTeleop extends CommandBase {

    private NetworkTableInstance inst;
    private NetworkTable table;

    private NetworkTableEntry xEntry;
    private NetworkTableEntry yEntry;
    private NetworkTableEntry angleEntry;
    private NetworkTableEntry disableRobotEntry;
    private NetworkTableEntry shootEntry;

    private double x = 0;
    private double y = 0;
    private double angle;
    private boolean disable;
    private boolean shoot;

    private final JankDrive drive;
    private final ShootTeleop shootTeleop;
    private final BarrelTilt barrelTilt;

    public TitanDriveTeleop(JankDrive drive, ShootTeleop shootTeleop, BarrelTilt tilt) {
        inst = NetworkTableInstance.getDefault();

        table = inst.getTable("titandrive");

        this.drive = drive;
        this.shootTeleop = shootTeleop;
        this.barrelTilt = tilt;

        addRequirements(drive);
        addRequirements(barrelTilt);

    }

    @Override
    public void initialize() {

        xEntry = table.getEntry("x");
        yEntry = table.getEntry("y");
        angleEntry = table.getEntry("tiltangle");
        disableRobotEntry = table.getEntry("disabled");
        shootEntry = table.getEntry("shoot");

        angle = 0;
        shoot = false;
        disable = false;

    }

    @Override
    public void execute() {

        xEntry.setDouble(x);
        yEntry.setDouble(y);
        angleEntry.setDouble(angle);
        disableRobotEntry.setBoolean(disable);
        shootEntry.setBoolean(shoot);

        if (shoot) {
            shootTeleop.schedule();
        }

        x /= 75;
        y /= 75;
        this.drive.set(y + x, y - x);

        this.barrelTilt.set(angle);

    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    public boolean isDisabled() {
        return disable;
    }
}
