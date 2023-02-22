// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

// Uncomment Later

package frc.robot.commands;
import frc.robot.subsystems.Transfer;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.Constants.*;

public class IntakeGamePieceCommand extends SequentialCommandGroup {
  public IntakeGamePieceCommand(Transfer transfer, Intake intake) {
     super(
      intake.flipDown(),
      (new StartEndCommand(() ->{
        intake.spinIntake(IntakeConstants.coneIntakeInSpeed);
      },
        () -> {
        intake.spinIntake(0);
        
      })),
      intake.flipUp()
     );
   }

//   // Called when the command is initially scheduled.
//   @Override
//   public void initialize() {}
 }
