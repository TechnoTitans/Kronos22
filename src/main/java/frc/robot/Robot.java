// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    private RobotContainer robotContainer;
    private SendableChooser<Boolean> driveChooser;

    public static double shoot_delay = 45;

    public static boolean isController = false;

    /**
     * This function is run when the robot is first started up and should be used for any
     * initialization code.
     */
    @Override
    public void robotInit() {
        // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
        // autonomous chooser on the dashboard.
        robotContainer = new RobotContainer();
        driveChooser = new SendableChooser();
        robotContainer.dout.set(false);
        robotContainer.barrel.getBarrel().resetEncoder();
        robotContainer.drive.coast();

        driveChooser.setDefaultOption("controller", true);
        driveChooser.addOption("website", false);
        SmartDashboard.putData("drive Mode", driveChooser);
    }

    /**
     * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
     * that you want ran during disabled, autonomous, teleoperated and test.
     *
     * <p>This runs after the mode specific periodic functions, but before LiveWindow and
     * SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
        // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
        // commands, running already-scheduled commands, removing finished or interrupted commands,
        // and running subsystem periodic() methods.  This must be called from the robot's periodic
        // block in order for anything in the Command-based framework to work.
        CommandScheduler.getInstance().run();

        if (isController) {
            shoot_delay = SmartDashboard.getNumber("ShootTime", 45);
            if (shoot_delay < 25) {
                SmartDashboard.putNumber("ShootTime", 25);
                shoot_delay = 25;
            }
        }

    }

    /**
     * This function is called once each time the robot enters Disabled mode.
     */
    @Override
    public void disabledInit() {
        robotContainer.drive.brake();
        robotContainer.drive.set(0, 0);
        robotContainer.tiltMotor.stop();
        robotContainer.barrelMotor.stop();
        robotContainer.spike.set(Relay.Value.kOff);
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void disabledPeriodic() {
    }

    /**
     * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
     */
    @Override
    public void autonomousInit() {
    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        robotContainer.drive.brake();
        isController = driveChooser.getSelected();
        if (isController) {
            SmartDashboard.putNumber("ShootTime", 45);
            CommandScheduler.getInstance().setDefaultCommand(robotContainer.drive, robotContainer.driveTeleop);
            CommandScheduler.getInstance().setDefaultCommand(robotContainer.barrelTilt, robotContainer.tiltTeleop);
        }
    }

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
    }

    @Override
    public void testInit() {
        robotContainer.drive.coast();
        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll();
    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
    }

    /**
     * This function is called once when the robot is first started up.
     */
    @Override
    public void simulationInit() {
    }

    /**
     * This function is called periodically whilst in simulation.
     */
    @Override
    public void simulationPeriodic() {
    }
}
