// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Drivetrain;
//import frc.robot.subsystems.Elevator;
//import frc.robot.subsystems.Fourbar;
import frc.robot.subsystems.Intake;
//import frc.robot.subsystems.Claw;
import io.github.oblarg.oblog.Logger;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
//import frc.robot.commands.SpinTransfer;
import frc.robot.commands.IntakeGamePiece;




public class RobotContainer {


  // The robot's subsystems and commands are defined here...

  
  private final Drivetrain drivetrain = new Drivetrain();
  private final Intake intake = new Intake();
  //private final Elevator elevator = new Elevator();
  //private final Claw outtake = new Claw();
 // private final Fourbar fourbar = new Fourbar();
  
 
  private final CommandXboxController driverController = new CommandXboxController(0);

    private final Command driveCommand = new RunCommand(
      () -> {
        double speed = MathUtil.applyDeadband(driverController.getLeftY()/3, 0.09);
        double turn = MathUtil.applyDeadband(driverController.getRightX()/3, 0.08);

        drivetrain.drive(speed, turn);
      }
      , drivetrain);


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    Logger.configureLoggingAndConfig(this, false);

    // Configure the trigger bindings
    configureBindings();


  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */

  private void configureBindings() {

  
    drivetrain.setDefaultCommand(driveCommand);

    driverController
      .x()
      .whileTrue(new IntakeGamePiece(intake));

    // driverController
    // .leftBumper()
    // .whileTrue(new StartEndCommand(() -> {elevator.setPower(.1);}, () -> {elevator.setPower(0);}, elevator));

    // driverController
    // .rightBumper()
    // .whileTrue(new StartEndCommand(() -> {elevator.setPower(-.1);}, () -> {elevator.setPower(0);}, elevator));

    

    // driverController
    // .y()
    // .whileTrue(new EjectGamePiece(intake));

   
  }

  public void updateLogger(){
    Logger.updateEntries();
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */

  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return null;
  }

}
