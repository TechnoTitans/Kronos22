package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.JankDrive;

import java.util.function.DoubleSupplier;

public class DriveTeleop extends CommandBase {
    private final JankDrive drive;
    private final XboxController controller;

    public DriveTeleop(JankDrive drive, XboxController controller) {
        this.drive = drive;
        this.controller = controller;
        addRequirements(drive);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        // Remove drift from controller by adding deadband
        double steering = controller.getLeftX();
        double throttle = controller.getLeftY();
        double SENSITIVITY = 0.5;
        drive.set((throttle + steering) * SENSITIVITY, (throttle - steering) * SENSITIVITY);
    }

    @Override
    public void end(boolean interrupted) {
        drive.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
