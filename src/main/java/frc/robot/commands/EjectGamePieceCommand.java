// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

// Uncomment Later

package frc.robot.commands;
import frc.robot.subsystems.Transfer;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.StartEndCommand;

public class EjectGamePieceCommand extends StartEndCommand {
  public EjectGamePieceCommand(Transfer transfer, Intake intake) {
     super(
       () -> {
        // transfer.setBeltPower(double);
        //will need to add arm positioning
        intake.spinIntake(-0.3);
       },   // Extend intake arm and spin intake motors away from robot
       () -> {
        //restore arm position
        intake.stopSpin();
       },
       transfer, intake);  // Uses intake subsystem
   }

//   // Called when the command is initially scheduled.
//   @Override
//   public void initialize() {}
 }
