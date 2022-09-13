package frc.robot.commands;

import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Barrel;

public class IndexTeleop extends CommandBase {

    private final Barrel barrel;
    private final double threshold = 15000;
    private final ColorSensorV3 colorSensor;
    private boolean proceed = false;
    private boolean finished = false;

    public IndexTeleop(Barrel barrel, ColorSensorV3 colorSensor) {
        this.barrel = barrel;
        this.colorSensor = colorSensor;
        addRequirements(barrel);
    }

    @Override
    public void initialize() {
        barrel.set(0.35);
    }

    @Override
    public void execute() {
        if (threshold >= colorSensor.getRed() && !proceed) { // This makes sure the barrel has indexed off of the color otherwise the other
            //if will instantly stop the barrel from indexing because it's already on the color.
            proceed = true;
        }

        if (colorSensor.getRed() >= threshold && proceed) {
            barrel.set(0);
            finished = true;
        }
    }

    @Override
    public void end(boolean interrupted) {
        proceed = false;
        finished = false;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
