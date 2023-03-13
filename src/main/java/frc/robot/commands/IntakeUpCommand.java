// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.


//Not Using


// package frc.robot.commands;

// import edu.wpi.first.wpilibj2.command.CommandBase;
// import frc.robot.subsystems.Intake;

// public class IntakeUpCommand extends CommandBase {
//   /** Creates a new IntakeUpCommand. */
//   private Intake intake;
//   public IntakeUpCommand(Intake intake) {
//     // Use addRequirements() here to declare subsystem dependencies.
//     addRequirements(intake);
//     this.intake=intake;
//   }
  

//   // Called when the command is initially scheduled.
//   @Override
//   public void initialize() {}

//   // Called every time the scheduler runs while the command is scheduled.
//   @Override
//   public void execute() {
//     intake.setFlipperSpeed(intake.calculateIntakeFlipUp());
//   }

//   // Called once the command ends or is interrupted.
//   @Override
//   public void end(boolean interrupted) {}

//   // Returns true when the command should end.
//   @Override
//   public boolean isFinished() {
//     if (intake.calculateIntakeFlipUp()==0){
//       return true;
//     }
//     return false;
//   }
// }
