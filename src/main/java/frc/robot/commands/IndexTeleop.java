package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Barrel;

public class IndexTeleop extends CommandBase {

    private final Barrel barrel;
    private final Encoder encoder;
    private final int mor = 4;
    private int counter = 1;
    private double oldval;

    public IndexTeleop(Barrel barrel) {
        this.barrel = barrel;
        this.encoder = barrel.getBarrelEncoder();
        encoder.reset();
        addRequirements(barrel);
    }

    @Override
    public void initialize() {
        barrel.set(-1);
    }

    @Override
    public void execute() {
    }

    @Override
    public void end(boolean interrupted) {
        barrel.set(0);
        counter++;
    }

    @Override
    public boolean isFinished() {
        SmartDashboard.putNumber("Encoder Distance", encoder.getDistance());
        SmartDashboard.putNumber("Encoder Raw", encoder.getRaw());
        SmartDashboard.putNumber("Encoder Old", oldval);
        oldval = encoder.getDistance();
        SmartDashboard.putNumber("diff", (encoder.getDistance()-oldval));
        SmartDashboard.putNumber("Counter", counter);
        SmartDashboard.putNumber("Margin of Error", mor);
        SmartDashboard.putNumber("Target Distance", barrel.DISTANCE);
        SmartDashboard.putBoolean("Finished", (barrel.DISTANCE * counter)+encoder.getDistance() <= mor && (barrel.DISTANCE * counter)+encoder.getDistance() >= -mor);

        return (barrel.DISTANCE * counter)+encoder.getDistance() <= mor && (barrel.DISTANCE * counter)+encoder.getDistance() >= -mor;
    }

}
