// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.ProfiledPIDCommand;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.Drivetrain;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ProfiledDriveToDistancCommand extends ProfiledPIDCommand {
  /** Creates a new ProfiledDriveToDistancCommand. */
  public ProfiledDriveToDistancCommand(double targetDistanceMeters, Drivetrain drivetrain) {
    super(
        // The ProfiledPIDController used by the command
        new ProfiledPIDController(
            // The PID gains
            0.01,
            0,
            0,
            // The motion profile constraints
            new TrapezoidProfile.Constraints(
              DriveConstants.profiledDriveToDistanceMaxVelPerSec, 
              DriveConstants.profiledDriveToDistanceMaxAccelMetersPerSec
              )),
        // This should return the measurement
        () -> drivetrain.getDistance(),
        // This should return the goal (can also be a constant)
        () -> targetDistanceMeters,
        // This uses the output
        (output, setpoint) -> {
          drivetrain.tankDriveVolts(output, output);
        });
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
