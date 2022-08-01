package frc.robot.commands;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShootTeleop extends CommandBase {
    private final IndexTeleop indexTeleop;
    private final DigitalOutput dout;
    private final double TIME_MS = 20;

    public ShootTeleop(IndexTeleop indexTeleop, DigitalOutput dout) {
        this.indexTeleop = indexTeleop;
        this.dout = dout;
    }

    @Override
    public void initialize() {
        if (!dout.isPulsing()) {
            dout.pulse(TIME_MS);
        }
    }

    @Override
    public void end(boolean interrupted) {
        indexTeleop.schedule();
    }

    @Override
    public boolean isFinished() {
        return !dout.isPulsing();
    }
}
