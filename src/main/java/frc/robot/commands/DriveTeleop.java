package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.JankDrive;

import java.util.function.DoubleSupplier;

public class DriveTeleop extends CommandBase {
    private final JankDrive drive;
    private DoubleSupplier leftInput, rightInput, steeringInput;
    private double steering, throttle;

    private final double SENSITIVITY = 0.6;

    public DriveTeleop(JankDrive drive, DoubleSupplier leftInput, DoubleSupplier rightInput, DoubleSupplier steeringInput) {
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
        steering = steeringInput.getAsDouble() > -0.25 && steeringInput.getAsDouble() < 0.25 ? 0 : steeringInput.getAsDouble();
        throttle = rightInput.getAsDouble() - leftInput.getAsDouble();
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
