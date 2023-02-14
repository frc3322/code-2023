// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.

// package frc.robot.commands;

// import frc.robot.Constants;
// import frc.robot.Types.*;
// import frc.robot.subsystems.Transfer;
// import frc.robot.Constants.*;

// import java.lang.reflect.Proxy;
// import java.net.ProxySelector;

// import javax.swing.UIDefaults.ProxyLazyValue;

// import com.revrobotics.ColorSensorV3.ProximitySensorMeasurementRate;

// import edu.wpi.first.wpilibj.DigitalGlitchFilter;
// import edu.wpi.first.wpilibj.DigitalInput;
// import edu.wpi.first.wpilibj2.command.CommandBase;
// import edu.wpi.first.wpilibj2.command.ProxyCommand;

// public class SpinTransferCommand extends CommandBase {
//   /** Creates a new SpinTransfer. */

//   private TransferDirection direction;
//   private boolean colorSensorFoundObject;
//   private Transfer transfer;
  


  
//   public SpinTransferCommand(TransferDirection direction, Transfer transfer) {
//     // Use addRequirements() here to declare subsystem dependencies.
//     addRequirements(transfer);
//     this.direction = direction;
//     this.transfer = transfer;
//   }

//   // Called when the command is initially scheduled.
//   @Override
//   public void initialize() {
    
//   }

//   // Called every time the scheduler runs while the command is scheduled.
//   @Override
//   public void execute() {

//     //Guard condition: Exit early if there is nothing to do
//     if (!transfer.shouldRunBelt()) {
//       return;
//     } 
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
