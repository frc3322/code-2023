// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Fourbar;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Transfer;
import frc.robot.subsystems.Claw;
import io.github.oblarg.oblog.Logger;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.EjectGamePieceCommand;
//import frc.robot.commands.SpinTransfer;
import frc.robot.commands.IntakeGamePieceCommand;




public class RobotContainer {


  // The robot's subsystems and commands are defined here...

  
  private final Drivetrain drivetrain = new Drivetrain();
  private final Intake intake = new Intake();
  private final Claw claw = new Claw();
  private final Fourbar fourbar = new Fourbar();
  private final Transfer transfer = new Transfer();
  
 
  private final CommandXboxController driverController = new CommandXboxController(0);
  private final CommandXboxController secondaryController = new CommandXboxController(1);

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

    //driver controller (0) commands

    driverController
      .x()
      .whileTrue(new StartEndCommand (() -> intake.spinIntake(.8), () -> intake.stopSpin(), intake));

      driverController
      .a()
      .whileTrue(new StartEndCommand (() -> intake.spinIntake(-.8), () -> intake.stopSpin(), intake));

   
      //  driverController
    //  .a()
    //  .whileTrue(new EjectGamePieceCommand(transfer, intake));

     driverController
     .povDown()
     .onTrue(new InstantCommand(()->intake.resetArmEncoder()));
  
     driverController
     .b()
     .whileTrue(new StartEndCommand(() -> intake.setFlipperSpeed(.2), () -> intake.setFlipperSpeed(0), intake));

    //up
     driverController
     .y()
     .whileTrue(new StartEndCommand(()-> intake.setFlipperSpeed(-.2), ()-> intake.setFlipperSpeed(0), intake));

    driverController
    .leftBumper()
    .whileTrue(new StartEndCommand(() -> {transfer.setElevatorPower(.3);}, () -> {transfer.setElevatorPower(0);}, transfer));

   //up
    driverController
    .rightBumper()
    .whileTrue(new StartEndCommand(() -> {transfer.setElevatorPower(-.3);}, () -> {transfer.setElevatorPower(0);}, transfer));

    
    //secondary controller commands
    secondaryController
    .rightBumper()
    .whileTrue(new StartEndCommand(() -> transfer.setBeltPower(0.25), () -> transfer.setBeltPower(0), transfer));

    secondaryController
    .leftBumper()
    .whileTrue(new StartEndCommand(() -> transfer.setBeltPower(-0.25), () -> transfer.setBeltPower(0), transfer));

    secondaryController
    .a()
    .onTrue(new InstantCommand(() -> claw.setClosed(), claw));

    secondaryController
    .b()
    .onTrue(new InstantCommand(() -> claw.setOpen(), claw));

   

    secondaryController
    .povDown()
    .onTrue(new InstantCommand(() -> fourbar.fourbarDown()));

    secondaryController
    .povUp()
    .onTrue(new InstantCommand(() -> fourbar.fourbarUp()));
   
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
