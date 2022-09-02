package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BarrelTilt;

public class TiltTeleop extends CommandBase {
    private final BarrelTilt barrelTilt;
    private final double SPEED = 0.45;
    private int button;

    public TiltTeleop(BarrelTilt barrelTilt, int button) {
        this.barrelTilt = barrelTilt;
        this.button = button;
        addRequirements(barrelTilt);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        if (button == 0) {
            barrelTilt.set(SPEED);
        } else if (button == 180) {
            barrelTilt.set(-SPEED*0.5); // We slow down because when it's going down gravity helps it go faster but faster is too fast.
        } else {
            barrelTilt.set(0);
        }
    }

    @Override
    public void end(boolean interrupted) {
        barrelTilt.getTilt().stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
