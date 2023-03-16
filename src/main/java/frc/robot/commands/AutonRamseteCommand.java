// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.Drivetrain;

/** Add your docs here. */
public class AutonRamseteCommand extends RamseteCommand {

    public AutonRamseteCommand(Trajectory path, Drivetrain drivetrain){
        super(
            path,
            drivetrain::getPose,
            new RamseteController(0, 0),
            new SimpleMotorFeedforward(
                DriveConstants.ksVolts,
                DriveConstants.kvVoltSecondsPerMeter,
                DriveConstants.kaVoltSecondsSquaredPerMeter
            ),
            DriveConstants.kDriveKinematics,
            drivetrain::getWheelSpeeds,
            new PIDController(DriveConstants.kDrivePRamsete, 0, 0),
            new PIDController(DriveConstants.kDrivePRamsete, 0, 0),
            drivetrain::tankDriveVolts,
            drivetrain
        );
    }
}