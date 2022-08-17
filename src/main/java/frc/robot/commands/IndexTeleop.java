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
    private final int mor = 5;
    private Timer timer;

    public IndexTeleop(Barrel barrel) {
        this.barrel = barrel;
        this.encoder = barrel.getBarrelEncoder();
        timer = new Timer();
        addRequirements(barrel);
    }

    @Override
    public void initialize() {
        encoder.reset();
        timer.reset();
        timer.start();
        barrel.set(-1);
    }

    @Override
    public void execute() {
        if (timer.hasElapsed(2)) {
            barrel.set(0);
        }
    }

    @Override
    public void end(boolean interrupted) {
        barrel.getBarrel().stop();
    }

    @Override
    public boolean isFinished() {
        double distance = encoder.getDistance();
        return distance <= (distance+mor) && distance >= -(distance+mor);
    }

}
