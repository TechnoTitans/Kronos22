package frc.robot.commands;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Barrel;

public class IndexTeleop extends CommandBase {

    private final Barrel barrel;
    private final Encoder encoder;
    private Timer timer;
    private boolean finished = false;

    public IndexTeleop(Barrel barrel) {
        this.barrel = barrel;
        this.encoder = barrel.getBarrelEncoder();
        timer = new Timer();
        encoder.reset();
        addRequirements(barrel);
    }

    @Override
    public void initialize() {
        barrel.set(-1);
        timer.reset();
        timer.start();
    }

    @Override
    public void execute() {
        if (timer.hasElapsed(0.3)) {
            finished = true;
        }
    }

    @Override
    public void end(boolean interrupted) {
        barrel.set(0);
        finished = false;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
