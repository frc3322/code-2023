// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.ProfiledPIDCommand;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.Drivetrain;
import io.github.oblarg.oblog.Loggable;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ProfiledTurnToAngleCommand extends ProfiledPIDCommand implements Loggable {
  /** Creates a new ProfiledTurnToAngleCommand. */
  public ProfiledTurnToAngleCommand(double targetAngleDegrees, Drivetrain drivetrain) {
    super(
        // The ProfiledPIDController used by the command
        new ProfiledPIDController(
            // The PID gains
            0.01,
            0,
            0,
            // The motion profile constraints
            new TrapezoidProfile.Constraints(
              DriveConstants.profiledTurnToAngleMaxVelDegPerSec,
              DriveConstants.profiledTurnToAngleMaxAccelDegPerSec
              )),
        // This should return the measurement
        () -> drivetrain.getYaw(),
        // This should return the goal (can also be a constant)
        () -> targetAngleDegrees,
        // This uses the output
        (output, setpoint) -> {
          drivetrain.autonDrive(0, output);
        });

        SmartDashboard.putData("ProfiledTurnToAngle", getController());

        getController().enableContinuousInput(-180, 180);
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return getController().atGoal();
  }
}
