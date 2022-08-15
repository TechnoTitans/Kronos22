package frc.robot.commands;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShootTeleop extends CommandBase {
    private final DigitalOutput dout;
    private final IndexTeleop indexTeleop;
    private final double TIME = 0.5;

    public ShootTeleop(DigitalOutput dout, IndexTeleop indexTeleop) {
        this.dout = dout;
        this.indexTeleop = indexTeleop;
    }

    @Override
    public void initialize() {
        //21 is min for solenoid but rio can output faster
        dout.pulse(21);
    }

    @Override
    public void execute() {
    }

    @Override
    public void end(boolean interrupted) {
        dout.set(false);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
