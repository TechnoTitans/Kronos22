package frc.robot.commands;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class ShootTeleop extends CommandBase {
    private final DigitalOutput dout;
    private final IndexTeleop indexTeleop;
    private final Timer timer;
    private boolean finished = false;

    public ShootTeleop(DigitalOutput dout, IndexTeleop indexTeleop) {
        this.dout = dout;
        this.indexTeleop = indexTeleop;
        this.timer = new Timer();
        SmartDashboard.putNumber("ShootTime", 21);
    }

    @Override
    public void initialize() {
        finished = false;
        timer.reset();
        //21 is min for solenoid but rio can output faster
        dout.pulse(SmartDashboard.getNumber("ShootTime", 21));
        timer.start();
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
