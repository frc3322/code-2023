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

import javax.swing.text.html.HTMLDocument.BlockElement;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.EjectGamePieceCommand;
//import frc.robot.commands.SpinTransfer;
import frc.robot.commands.IntakeGamePieceCommand;
import frc.robot.commands.MoveClawCommand;
import frc.robot.commands.MoveFourbarCommand;
import frc.robot.Constants.*;




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
        double speed = MathUtil.applyDeadband(driverController.getLeftY(), 0.09);
        double turn = MathUtil.applyDeadband(driverController.getRightX(), 0.08);

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
    transfer.setDefaultCommand(transfer.beltRunCommand());

    //driver controller (0) commands

    driverController
      .x()
      //.whileTrue(new StartEndCommand (() -> intake.spinIntake(IntakeConstants.intakeInSpeed), () -> intake.spinIntake(0), intake));
      .onTrue(intake.flipDownSpin())
      .onFalse(intake.flipUpStop());


      driverController
      .a()
      .whileTrue(new StartEndCommand (() -> intake.spinIntake(-IntakeConstants.intakeInSpeed), () -> intake.spinIntake(0), intake));

      driverController
      .b()
      .whileTrue(new StartEndCommand (() -> intake.setFlipperSpeed(0.2), () -> intake.setFlipperSpeed(0), intake));

      driverController
      .y()
      .whileTrue(new StartEndCommand (() -> intake.setFlipperSpeed(-0.2), () -> intake.setFlipperSpeed(0), intake));

     driverController
     .povDown()
     .onTrue(intake.flipDown());

     driverController
     .povUp()
     .onTrue(intake.flipUp());

     driverController
     .povLeft()
     .onTrue(new InstantCommand(() -> intake.resetArmEncoder()));

    driverController
    .leftBumper()
    .whileTrue(new StartEndCommand(() -> {transfer.setElevatorPower(ElevatorConstants.elevatorSpeed);}, () -> {transfer.setElevatorPower(0);}, transfer).until(transfer.elevatorAtBottom()
    ));

   //up
    driverController
    .rightBumper()
    .whileTrue(new StartEndCommand(() -> {transfer.setElevatorPower(-ElevatorConstants.elevatorSpeed);}, () -> {transfer.setElevatorPower(0);}, transfer).until(transfer.elevatorAtTop())
    );

    
    //secondary controller commands
    secondaryController
    .rightBumper()
    .whileTrue(new StartEndCommand(() -> transfer.setBeltPower(TransferConstants.transferSpeed), () -> transfer.setBeltPower(0), transfer));

    secondaryController
    .leftBumper()
    .whileTrue(new StartEndCommand(() -> transfer.setBeltPower(-TransferConstants.transferSpeed), () -> transfer.setBeltPower(0), transfer));

    secondaryController
    .a()
    .onTrue(new InstantCommand(() -> claw.setClosed(), claw)
    .andThen(new WaitCommand(1))
    .andThen(transfer.elevatorToBottom())
    );
  

    secondaryController
    .b()
    .onTrue(new InstantCommand(() -> claw.setOpen(), claw));

    secondaryController
    .povLeft()
    .onTrue(transfer.elevatorToBottom());

    secondaryController
    .povRight()
    .onTrue(transfer.elevatorToTop());


    secondaryController
    .povDown()
    .onTrue(
      new InstantCommand(() -> claw.setClosed(), claw)
    .andThen(new WaitCommand(.75))
    .andThen(
      new InstantCommand(() -> fourbar.fourbarDown())
    ));
   
  

    secondaryController
    .povUp()
    .onTrue(
      new InstantCommand(() -> claw.setClosed(), claw)
    .andThen(new WaitCommand(.75))
    .andThen(
      new InstantCommand(() -> fourbar.fourbarUp())
    ));
   
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
    return new PlaceAndLeave();
  }

  /*
  AUTON COMMANDS BELOW
  */
  private class PlaceAndLeave extends SequentialCommandGroup {
    private PlaceAndLeave() {
        addCommands(
            new MoveFourbarCommand(Types.FourbarPosition.EXTEND, fourbar),
            new WaitCommand(0.5),
            new MoveClawCommand(Types.ClawPosition.OPEN, claw),
            new WaitCommand(0.5),
            new MoveClawCommand(Types.ClawPosition.CLOSED, claw),
            new WaitCommand(0.5),
            new MoveFourbarCommand(Types.FourbarPosition.RETRACT, fourbar),
            new WaitCommand(0.5),
            new MoveClawCommand(Types.ClawPosition.OPEN, claw)
            
            //DriveToDistanceCommand(),
        );

    }
}

}
