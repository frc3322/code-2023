package frc.robot.commands;
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Types.ClawPosition;
import frc.robot.subsystems.Outtake;



public class MoveClaw extends CommandBase {

  private ClawPosition clawPosition;
  private Outtake claw;

  /** Creates a new MoveClaw. */
  public MoveClaw(ClawPosition clawPosition, Outtake claw) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.clawPosition = clawPosition;
    this.claw = claw;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
