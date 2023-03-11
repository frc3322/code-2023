// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.Types.LaunchTo;

public class LaunchCommand extends CommandBase {
  /** Creates a new LaunchCommand. */
  private Intake intake;
  private LaunchTo launchTo;

  public LaunchCommand(Intake intake, LaunchTo launchTo) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(intake);
    this.intake=intake;
    this.launchTo=launchTo;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    switch(launchTo){
      case LOW:
        //set roller speeds to launch it to low
        break;
      case MID:
        //set roller speeds to launch it to mid
        break;
      case TOP:
        //set roller speeds to launch it to top
        break;

    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
