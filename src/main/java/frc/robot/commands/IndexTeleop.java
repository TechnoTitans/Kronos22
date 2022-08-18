package frc.robot.commands;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Barrel;

public class IndexTeleop extends CommandBase {

    private final Barrel barrel;
    private final Encoder encoder;
    private boolean finished = false;
    private final int direction;

    public IndexTeleop(Barrel barrel, int direction) {
        this.barrel = barrel;
        this.encoder = barrel.getBarrelEncoder();
        encoder.reset();
        this.direction = direction;
        addRequirements(barrel);
    }

    @Override
    public void initialize() {
        if (direction == 0) {
            barrel.set(-0.5);
        } else if (direction == 1){
            barrel.set(0.5);
        } else {
            barrel.set(0.5);
        }
    }

    @Override
    public void execute() {
    }

    @Override
    public void end(boolean interrupted) {
        barrel.set(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
