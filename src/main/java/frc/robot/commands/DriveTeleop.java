package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.JankDrive;

public class DriveTeleop extends CommandBase {
    private final JankDrive drive;
    private double leftInput, rightInput, steeringInput;
    private double steering, throttle;

    private final double SENSITIVITY = 0.6;

    public DriveTeleop(JankDrive drive, double leftInput, double rightInput, double steeringInput) {
        this.drive = drive;
        this.leftInput = leftInput;
        this.rightInput = rightInput;
        this.steeringInput = steeringInput;
        addRequirements(drive);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        // Remove drift from controller by adding deadband
        steering = steeringInput > -0.25 && steeringInput < 0.25 ? 0 : steeringInput;
        steering *= 0.7; // Make steering less sensitive
        throttle = leftInput - rightInput;
        drive.set((throttle - steering) * SENSITIVITY, (throttle + steering) * SENSITIVITY);
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
