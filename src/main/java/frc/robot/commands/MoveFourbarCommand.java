// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Types.FourbarPosition;
import frc.robot.subsystems.Fourbar;

public class MoveFourbarCommand extends InstantCommand {

  private FourbarPosition fourbarPosition;
  private Fourbar fourbar;

  /** Creates a new MoveForebar. */
  public MoveFourbarCommand(FourbarPosition fourbarPosition, Fourbar fourbar) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(fourbar);
    this.fourbarPosition = fourbarPosition;
    this.fourbar = fourbar;



  }

  
}
