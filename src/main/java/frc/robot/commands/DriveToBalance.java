// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.ProfiledPIDCommand;
import frc.robot.subsystems.Drivetrain;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class DriveToBalance extends ProfiledPIDCommand {

  
  /** Creates a new DriveToBalance. */
  public DriveToBalance(Drivetrain drivetrain) {
    
    super(
        // The ProfiledPIDController used by the command
        new ProfiledPIDController(
            // The PID gains
            drivetrain.balancekP,
            drivetrain.balancekI,
            drivetrain.balancekD,
            // The motion profile constraints
            new TrapezoidProfile.Constraints(2, 2)),
        // This should return the measurement
        () -> drivetrain.getPitch(),
        // This should return the goal (can also be a constant)
        () -> 0,
        // This uses the output
        (output, setpoint) -> {
          drivetrain.tankDriveVolts(-output, -output);
          // Use the output (and setpoint, if desired) here
        });
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
  }
  

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return getController().getPositionError() < 5;
  }
}
