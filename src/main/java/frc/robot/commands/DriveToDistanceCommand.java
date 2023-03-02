// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.Drivetrain;

/** A command that will turn the robot to the specified angle. */
public class DriveToDistanceCommand extends PIDCommand {
  
  public DriveToDistanceCommand(double targetDistance, Drivetrain drive) {

    super(
      new PIDController(DriveConstants.kDriveP, DriveConstants.kDriveI, DriveConstants.kDriveD),
        // Close loop on heading
        drive::getDistance,
        // Set reference to target
        targetDistance,
        // Pipe output to turn robot
        output -> drive.jankDrive(output,0),
        // Require the drive
        drive);

    SmartDashboard.putData("Distance Controller", getController());
    

    // Set the controller to be continuous
    // DO NOT REMOVE THIS LINE //
    getController().enableContinuousInput(0, 60);
    
    // Set the controller tolerance - the delta tolerance ensures the robot is stationary at the
    // setpoint before it is considered as having reached the reference
    getController()
        .setTolerance(DriveConstants.kDriveToleranceMeters, DriveConstants.kDriveRateToleranceMetersPerS);
  }

  @Override
  public boolean isFinished() {
    // End when the controller is at the reference.
    return getController().atSetpoint();
  }
}