// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.

// package frc.robot.commands;

// import frc.robot.Constants;
// import frc.robot.Types.*;
// import frc.robot.subsystems.Elevator;
// import frc.robot.subsystems.Transfer;
// import frc.robot.Constants.*;
// import edu.wpi.first.wpilibj2.command.CommandBase;

// public class SpinTransferCommand extends CommandBase {
//   /** Creates a new SpinTransfer. */

//   private TransferDirection direction;
//   private boolean colorSensorFoundObject;
//   private Transfer transfer;
//   private Elevator elevator;
  
//   public SpinTransferCommand(TransferDirection direction, Transfer transfer, Elevator elevator) {
//     // Use addRequirements() here to declare subsystem dependencies.
//     addRequirements(transfer);
//     this.direction = direction;
//     this.transfer = transfer;
//     this.elevator = elevator;
//   }

//   // Called when the command is initially scheduled.
//   @Override
//   public void initialize() {
    
//   }

//   // Called every time the scheduler runs while the command is scheduled.
//   @Override
//   public void execute() {
//     //if (elevator.getDown()){
//       if(direction == TransferDirection.FORWARD){
//         //while(!colorSensorFoundObject){
//         transfer.setBeltPower(IntakeConstants.beltForwardSpeed);
//        // }
//       }
//       else{
//         //while(!colorSensorFoundObject){
//           transfer.setBeltPower(IntakeConstants.beltReverseSpeed);
//         //}
//       }
//     //}

//   }

//   // Called once the command ends or is interrupted.
//   @Override
//   public void end(boolean interrupted) {}

//   // Returns true when the command should end.
//   @Override
//   public boolean isFinished() {
//     return false;
//   }
// }
