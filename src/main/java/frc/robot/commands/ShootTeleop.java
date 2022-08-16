package frc.robot.commands;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Robot;

public class ShootTeleop extends CommandBase {
    private final DigitalOutput dout;
    private final IndexTeleop indexTeleop;
    private final Timer timer;
    private boolean finished = false;

    public ShootTeleop(DigitalOutput dout, IndexTeleop indexTeleop) {
        this.dout = dout;
        this.indexTeleop = indexTeleop;
        this.timer = new Timer();
    }

    @Override
    public void initialize() {
        //21 is min for solenoid but rio can output faster
        timer.reset();
        timer.start();
        dout.pulse(Robot.shoot_delay);
    }

    @Override
    public void execute() {
        if (timer.hasElapsed(2)) {
            CommandScheduler.getInstance().schedule(indexTeleop);
            finished = true;
        }
    }

    @Override
    public void end(boolean interrupted) {
        finished = false;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
