package frc.robot.commands;

import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Barrel;

public class IndexTeleop extends CommandBase {

    private final Barrel barrel;
    private final ColorSensorV3 colorSensor;
    private boolean proceed = false;
    private boolean finished = false;
    private final double threshold = 15000;

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
//        SmartDashboard.putString("getColor", String.valueOf(colorSensor.getColor()));
//        SmartDashboard.putString("getColor green", String.valueOf(colorSensor.getColor().green));
//        SmartDashboard.putString("getColor Blue", String.valueOf(colorSensor.getColor().blue));
//        SmartDashboard.putString("getColor Red", String.valueOf(colorSensor.getColor().red));
        SmartDashboard.putString("getRed", String.valueOf(colorSensor.getRed()));
        SmartDashboard.putString("bool", String.valueOf(proceed));
        SmartDashboard.putString("1", String.valueOf(threshold >= colorSensor.getRed()));
        SmartDashboard.putString("2", String.valueOf(colorSensor.getRed() >= threshold));
//        SmartDashboard.putString("getBlue", String.valueOf(colorSensor.getBlue()));
//        SmartDashboard.putString("getGreen", String.valueOf(colorSensor.getGreen()));
//        SmartDashboard.putString("CIE Color", String.valueOf(colorSensor.getCIEColor()));
//        SmartDashboard.putString("Prox", String.valueOf(colorSensor.getProximity()));
//        SmartDashboard.putString("Raw Color", String.valueOf(colorSensor.getRawColor()));
//        SmartDashboard.putString("IR", String.valueOf(colorSensor.getIR()));

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
        finished = false;
        proceed = false;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
