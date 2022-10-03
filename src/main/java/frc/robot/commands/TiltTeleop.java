package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BarrelTilt;

import java.util.function.DoubleSupplier;

public class TiltTeleop extends CommandBase {
    private final BarrelTilt barrelTilt;
    private final DoubleSupplier button;

    public TiltTeleop(BarrelTilt barrelTilt, DoubleSupplier button) {
        this.barrelTilt = barrelTilt;
        this.button = button;
        addRequirements(barrelTilt);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        double SPEED = 0.45;
        if (button.getAsDouble() == 0) {
            barrelTilt.set(SPEED);
        } else if (button.getAsDouble() == 180) {
            barrelTilt.set(-SPEED *0.5);
        } else {
            barrelTilt.set(0);
        }
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
