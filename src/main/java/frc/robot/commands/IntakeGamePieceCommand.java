// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.subsystems.Intake;

public class IntakeGamePieceCommand extends StartEndCommand {
  public IntakeGamePieceCommand(Intake intake) {
    super(
      () -> {
        intake.spinIntake(0.1);
      },
      () -> {
        intake.stopSpin();
      },
      intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}
}
