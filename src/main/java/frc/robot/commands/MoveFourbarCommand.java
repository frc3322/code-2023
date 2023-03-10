// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Types.FourbarPosition;
import frc.robot.subsystems.Fourbar;

public class MoveFourbarCommand extends CommandBase {

  private FourbarPosition fourbarDestination;
  private Fourbar fourbar;

  /** Creates a new MoveForebar. */
  public MoveFourbarCommand(FourbarPosition fourbarDestination, Fourbar fourbar) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(fourbar);

    this.fourbarDestination = fourbarDestination;
    this.fourbar = fourbar;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (fourbarDestination == FourbarPosition.EXTEND) {
      fourbar.fourbarUp();
    }
    else {
      fourbar.fourbarDown();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //make same change to fourbar as well
    if (fourbarDestination==fourbar.getFourBarPosition()){
        return true;
    }
    return false;
  }
}
