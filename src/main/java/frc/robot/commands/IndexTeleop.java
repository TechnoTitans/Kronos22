package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Barrel;

public class IndexTeleop extends CommandBase {

    private final Barrel barrel;
    private final Encoder encoder;
    private final double SPEED = 0.3;
    private double error;
    private final double kP = 0.03;
    private PIDController pidController;
    private final byte mor = 5;

    public IndexTeleop(Barrel barrel) {
        this.barrel = barrel;
        this.encoder = barrel.getBarrelEncoder();
        addRequirements(barrel);
    }

    @Override
    public void initialize() {
//        barrel.getBarrel().brake();
        pidController = new PIDController(kP, 0, 0);
        barrel.set(SPEED);
    }

    @Override
    public void execute() {
        error = barrel.DISTANCE - encoder.getRaw();
        barrel.set(pidController.calculate(error));
    }

    @Override
    public void end(boolean interrupted) {
        barrel.getBarrel().stop();
    }

    @Override
    public boolean isFinished() {
        return error <= mor && error >= -mor;
    }

}
