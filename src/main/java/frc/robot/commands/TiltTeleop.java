package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BarrelTilt;

import java.util.function.DoubleSupplier;

public class TiltTeleop extends CommandBase {
    private final BarrelTilt barrelTilt;
    private final XboxController controller;

    public TiltTeleop(BarrelTilt barrelTilt, XboxController controller) {
        this.barrelTilt = barrelTilt;
        this.controller = controller;
        addRequirements(barrelTilt);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        double SPEED = 0.45;
        barrelTilt.set(controller.getRightY() * SPEED);
    }

    @Override
    public void end(boolean interrupted) {
        barrelTilt.getTilt().stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
