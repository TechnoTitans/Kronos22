package frc.robot.commands;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Robot;
import frc.robot.utils.TitanButton;

public class ShootTeleop extends CommandBase {
    private final DigitalOutput dout;
    private final IndexTeleop indexTeleop;
    private final TitanButton btn;
    private final Timer timer;
    private boolean finished = false;

    public ShootTeleop(DigitalOutput dout, IndexTeleop indexTeleop, TitanButton btn) {
        this.dout = dout;
        this.indexTeleop = indexTeleop;
        this.btn = btn;
        this.timer = new Timer();
    }

    @Override
    public void initialize() {
        if (!dout.isPulsing()) {
            //21 is min for solenoid but rio can output faster
            btn.rumble(0.3);
            timer.reset();
            timer.start();
            SmartDashboard.putNumber("actual delay", Robot.shoot_delay);
            dout.pulse(Robot.shoot_delay);
        }
    }

    @Override
    public void execute() {
        if (timer.hasElapsed(1)) {
            CommandScheduler.getInstance().schedule(indexTeleop); //turns barrel after shot
            finished = true;
        }
    }

    @Override
    public void end(boolean interrupted) {
        finished = false;
        btn.stopRumble();
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
