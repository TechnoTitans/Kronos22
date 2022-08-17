//package frc.robot.commands;
//
//import edu.wpi.first.math.controller.PIDController;
//import edu.wpi.first.wpilibj.Encoder;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj2.command.CommandBase;
//import frc.robot.subsystems.Barrel;
//
//public class IndexTeleop1 extends CommandBase {
//
//    private final Barrel barrel;
//    private final Encoder encoder;
//    private double error;
//    private final double kP = 0.01;
//    private PIDController pidController;
//    private final int mor = 5;
//    private int counter = 0;
//
//    public IndexTeleop(Barrel barrel) {
//        this.barrel = barrel;
//        this.encoder = barrel.getBarrelEncoder();
//        addRequirements(barrel);
//    }
//
//    @Override
//    public void initialize() {
////        barrel.getBarrel().brake();
//        counter++;
//        pidController = new PIDController(kP, 0, 0);
//        encoder.reset();
//    }
//
//    @Override
//    public void execute() {
//        error = (barrel.DISTANCE*counter) - encoder.getRaw();
//        barrel.set(-1);
//    }
//
//    @Override
//    public void end(boolean interrupted) {
//        barrel.getBarrel().stop();
//        counter++;
//    }
//
//    @Override
//    public boolean isFinished() {
////        return error <= mor && error >= -mor;
//        return false;
//    }
//
//}
