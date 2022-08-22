package frc.robot.commands;

import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Barrel;

public class IndexTeleop extends CommandBase {

    private final Barrel barrel;
    private final Encoder encoder;
    private boolean finished = false;
    private final double threshold = 0.60;
    private final ColorSensorV3 colorSensor;

    public IndexTeleop(Barrel barrel, ColorSensorV3 colorSensor) {
        this.barrel = barrel;
        this.encoder = barrel.getBarrelEncoder();
        encoder.reset();
        this.colorSensor = colorSensor;
        addRequirements(barrel);
    }

    @Override
    public void initialize() {
        barrel.set(-1);
    }

    @Override
    public void execute() {
        if (colorSensor.getColor().green >= threshold) { //TBD VALUE
            barrel.set(0);
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
