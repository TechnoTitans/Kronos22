package frc.robot.commands;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShootTeleop extends CommandBase {
    private final Solenoid solenoid;
    private final IndexTeleop indexTeleop;
    private Timer timer;
    private final double TIME = 0.5;

    public ShootTeleop(Solenoid solenoid, IndexTeleop indexTeleop) {
        this.solenoid = solenoid;
        this.indexTeleop = indexTeleop;
    }

    @Override
    public void initialize() {
        timer = new Timer();
        timer.reset();
        timer.start();
        solenoid.set(true);
    }

    @Override
    public void execute() {
        if (timer.advanceIfElapsed(TIME)) {
            solenoid.set(false);
            indexTeleop.schedule();
        }
    }

    @Override
    public void end(boolean interrupted) {
        solenoid.set(false);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
