package frc.robot.commands;

import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Barrel;

public class IndexTeleop extends CommandBase {

    private final Barrel barrel;
    private final ColorSensorV3 colorSensor;
    private boolean finished = false;
    private boolean proceed = false;
    private final double threshold = 0.60;

    public IndexTeleop(Barrel barrel, ColorSensorV3 colorSensor) {
        this.barrel = barrel;
        this.colorSensor = colorSensor;
        addRequirements(barrel);
    }

    @Override
    public void initialize() {
        barrel.set(1);
    }

    @Override
    public void execute() {
        if (threshold-15 >= colorSensor.getColor().green && !proceed) { // This makes sure the barrel has indexed off of the color otherwise the other
            //if will instantly stop the barrel from indexing because it's already on the color.
            proceed = true;
        }

        if (colorSensor.getColor().green >= threshold && proceed) { //TBD VALUE
            barrel.set(0);
            finished = true;
        }
    }

    @Override
    public void end(boolean interrupted) {
        finished = false;
        proceed = false;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
