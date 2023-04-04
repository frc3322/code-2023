// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.Drivetrain;
import io.github.oblarg.oblog.Loggable;

/** A command that will turn the robot to the specified angle. */
public class TurnToGyroAngleCommand extends PIDCommand implements Loggable{
  /**
   * Turns to robot to the specified angle.
   *
   * @param targetAngleDegrees The angle to turn to
   * @param drive The drive subsystem to use
   */

  private Drivetrain drive;
  
  public TurnToGyroAngleCommand(double targetAngleDegrees, Drivetrain drive) {
    super(
      new PIDController(DriveConstants.kTurnP, DriveConstants.kTurnI, DriveConstants.kTurnD),
        // Close loop on heading
        drive::getYaw,
        // Set reference to target
        targetAngleDegrees,
        // Pipe output to turn robot
        output -> drive.autonDrive(0, output),
        // Require the drive
        drive);
    
    this.drive = drive;
    SmartDashboard.putData("Angle Controller", getController());
    

    // Set the controller to be continuous (because it is an angle controller)
    getController().enableContinuousInput(-180, 180);
    
    // Set the controller tolerance - the delta tolerance ensures the robot is stationary at the
    // setpoint before it is considered as having reached the reference
    getController()
        .setTolerance(DriveConstants.kTurnToleranceDeg, DriveConstants.kTurnRateToleranceDegPerS);
  }

  @Override
  public boolean isFinished() {
    // End when the controller is at the reference.
    return getController().atSetpoint();
  }

  @Override
  public void initialize() {
    // End when the controller is at the reference.
    drive.resetGyro();;
  }

  
}