package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.JankDrive;

public class DriveTeleop extends CommandBase {
    private final JankDrive drive;
    private double leftInput, rightInput, steeringInput, throttle;

    private final double SENSITIVITY = 1;

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
        // Remove drift from controller
//        steeringInput = steeringInput > -0.25 && steeringInput < 0.25 ? 0 : steeringInput;
//        throttle = rightInput - leftInput;
        drive.set(0,0.3);
//        drive.set((throttle - steeringInput) * SENSITIVITY, (throttle + steeringInput) * SENSITIVITY);
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
