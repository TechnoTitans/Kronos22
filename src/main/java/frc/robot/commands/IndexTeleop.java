package frc.robot.commands;

import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Barrel;

public class IndexTeleop extends CommandBase {

    private final Barrel barrel;
    private final double threshold = 11000;
    private final ColorSensorV3 colorSensor;
    private boolean proceed = false;

    public IndexTeleop(Barrel barrel, ColorSensorV3 colorSensor) {
        this.barrel = barrel;
        this.colorSensor = colorSensor;
        addRequirements(barrel);
    }

    @Override
    public void initialize() {
        barrel.set(0.25);
    }

    @Override
    public void execute() {
        if (threshold >= colorSensor.getRed() && !proceed) { // This makes sure the barrel has indexed off of the color otherwise the other
            //if will instantly stop the barrel from indexing because it's already on the color.
            proceed = true;
        }

    }

    @Override
    public void end(boolean interrupted) {
        barrel.set(0);
        proceed = false;
    }

    @Override
    public boolean isFinished() {
        return colorSensor.getRed() >= threshold && proceed;
    }
}
