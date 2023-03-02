// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.Types.FourbarPosition;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Fourbar;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Transfer;
import frc.robot.subsystems.Claw;
import io.github.oblarg.oblog.Logger;

import java.time.Instant;

import javax.swing.text.html.HTMLDocument.BlockElement;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.DriveToDistanceCommand;
import frc.robot.commands.EjectGamePieceCommand;
//import frc.robot.commands.SpinTransfer;
import frc.robot.commands.IntakeGamePieceCommand;
import frc.robot.commands.MoveClawCommand;
import frc.robot.commands.MoveFourbarCommand;
import frc.robot.Constants.*;
//arooshwashere

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

        drivetrain.jankDrive(speed, turn);
      }, drivetrain);

  private final Command elevatorCommand = new RunCommand(
      () -> {
        double elevatorPower = MathUtil.applyDeadband(secondaryController.getLeftY() / 2, 0.09);
        double transferPower = -MathUtil.applyDeadband(secondaryController.getRightY() / 2, 0.09);

        transfer.setElevatorPower(elevatorPower);
        transfer.setBeltPower(transferPower);

        // if(transfer.isFrontOccupied()){
        // transfer.setBeltPower(transfer.activeBeltSpeed);
        // }
        // if(transfer.isBackOccupied()){
        // transfer.setBeltPower(0);
        // }

      }, transfer);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    Logger.configureLoggingAndConfig(this, false);

    // Configure the trigger bindings
    configureBindings();

  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be
   * created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with
   * an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link
   * CommandXboxController
   * Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or
   * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */

  private void configureBindings() {

    // default commands
    drivetrain.setDefaultCommand(driveCommand);
    transfer.setDefaultCommand(transfer.beltRunCommand());
   // intake.setDefaultCommand(intake.spinIntakeWhileUp(transfer.isBeltRunning()));
    // transfer.setDefaultCommand(elevatorCommand);

    // driver controller (0) commands

    driverController
        .leftBumper()
        .onTrue(
          new InstantCommand(()-> fourbar.fourbarDown())
          .andThen(new WaitCommand(.5).unless(()->fourbar.getFourBarPosition() == FourbarPosition.RETRACT))

          .andThen(intake.flipDownSpin()))
        .onFalse(intake.flipUpStop());

    // driverController
    //     .leftTrigger()
    //     .onTrue(intake.flipDownEject()
    //     .andThen(new InstantCommand(() -> fourbar.fourbarDown()))
    //     )
    //     .onFalse(intake.flipUpStop());

    driverController
        .a()
        .onTrue(

            new SequentialCommandGroup(
                new InstantCommand(() -> claw.setClosed(), claw)
                    .andThen(new WaitCommand(.5))
                    .andThen(
                        fourbar.fourbarToggle())

            )

        );

    // driverController
    // .rightBumper()
    // .onTrue(
    // new InstantCommand(() -> claw.setClosed(), claw)
    // .andThen(new WaitCommand(.5))
    // .andThen(
    // fourbar.fourbarToggle()
    // ));

    driverController
        .povUp()
        .whileTrue(new StartEndCommand(() -> intake.setFlipperSpeed(-0.4), () -> intake.setFlipperSpeed(0), intake));

    driverController
        .povDown()
        .whileTrue(new StartEndCommand(() -> intake.setFlipperSpeed(0.3), () -> intake.setFlipperSpeed(0), intake));

    driverController
        .povLeft()
        .onTrue(new InstantCommand(() -> intake.resetArmEncoder()));

    driverController
        .axisGreaterThan(2, 0)
        .whileTrue(new RunCommand(() -> intake.setFlipperSpeed(-driverController.getLeftTriggerAxis()/3), intake));

    driverController
        .axisGreaterThan(3, 0)
        .whileTrue(new RunCommand(() -> intake.setFlipperSpeed(driverController.getRightTriggerAxis()/3), intake));

    driverController
        .b()
        .onTrue(new InstantCommand(() -> claw.setOpen(), claw));

    // x for align cube
    driverController
        .x()
        .onTrue(new InstantCommand(() -> transfer.setActiveBeltSpeed(TransferConstants.cubeTransferSpeed)));

    // y for align cone
    driverController
        .y()
        .onTrue(new InstantCommand(() -> transfer.setActiveBeltSpeed(TransferConstants.coneTransferSpeed)));

    driverController
        .rightBumper()
        .whileTrue(new StartEndCommand(() -> intake.spinIntake(IntakeConstants.coneIntakeInSpeed),
            () -> intake.spinIntake(0)));

    // driverController
    // .a()
    // .onTrue(
    // new InstantCommand(() -> claw.setClosed(), claw)
    // .andThen(new WaitCommand(.75))
    // .andThen(
    // new InstantCommand(() -> fourbar.fourbarDown())
    // ));

    // secondary controls

    secondaryController
        .y()
        .onTrue(new InstantCommand(() -> claw.setClosed(), claw));
            

    secondaryController
        .b()
        .onTrue(new InstantCommand(() -> claw.setOpen(), claw));

    secondaryController
        .x()
        .onTrue(
          new InstantCommand(() -> transfer.setBeltPower(0))
        );

    secondaryController
        .leftBumper()
        .onTrue(transfer.elevatorToBottom());

    secondaryController
        .rightBumper()
        .onTrue(transfer.elevatorToTop());

    secondaryController
        .povDown()
        .onTrue(
            new InstantCommand(() -> claw.setClosed(), claw)
                .andThen(new WaitCommand(.5))
                .andThen(
                    new InstantCommand(() -> fourbar.fourbarDown()))
                .andThen(new WaitCommand(.75))
                .andThen(new InstantCommand(() -> claw.setOpen(), claw))

        );

    secondaryController
        .povUp()
        .onTrue(
            new InstantCommand(() -> claw.setClosed(), claw)
                .alongWith(intake.flipUp())
                .andThen(new WaitCommand(.5))
            .andThen(transfer.elevatorToBottom().alongWith(new InstantCommand(() -> fourbar.fourbarUp()))
            ));
            
             

    secondaryController
        .axisGreaterThan(5, 0)
        .whileTrue(new RunCommand(
            () -> transfer.setBeltPower(MathUtil.applyDeadband(secondaryController.getRightY() / 2, 0.09)), transfer));

    secondaryController
        .axisLessThan(5, 0)
        .whileTrue(new RunCommand(
            () -> transfer.setBeltPower(MathUtil.applyDeadband(secondaryController.getRightY() / 2, 0.09)), transfer));

    secondaryController
        .axisGreaterThan(1, 0)
        .whileTrue(new RunCommand(
            () -> transfer.setElevatorPower(MathUtil.applyDeadband(secondaryController.getLeftY() / 2, 0.09)),
            transfer));

    secondaryController
        .axisLessThan(1, 0)
        .whileTrue(new RunCommand(
            () -> transfer.setElevatorPower(MathUtil.applyDeadband(secondaryController.getLeftY() / 2, 0.09)),
            transfer));

    // secondaryController
    // .axisGreaterThan(2, 0)
    // .whileTrue(new RunCommand(()->
    // transfer.setElevatorPower(secondaryController.getLeftTriggerAxis()/2),
    // transfer));

    // secondaryController
    // .axisGreaterThan(3, 0)
    // .whileTrue(new RunCommand(()->
    // transfer.setElevatorPower(-secondaryController.getRightTriggerAxis()/2),
    // transfer));

    // down trasfer reversed

  }

  public void updateLogger() {
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
           
           new MoveClawCommand(Types.ClawPosition.CLOSED, claw),
          //MoveFourBarCommand works, but it doesn't move on. Is not working. Maybe because it needs to be a different type of command?
            new MoveFourbarCommand(Types.FourbarPosition.EXTEND, fourbar),
            new WaitCommand(4.5),
            new MoveClawCommand(Types.ClawPosition.OPEN, claw),
            new WaitCommand(0.5),
            new MoveClawCommand(Types.ClawPosition.CLOSED, claw),
            new WaitCommand(0.5),
            new MoveFourbarCommand(Types.FourbarPosition.RETRACT, fourbar),
            new WaitCommand(0.5),
            new MoveClawCommand(Types.ClawPosition.OPEN, claw),
            

            
            new InstantCommand(
                () -> drivetrain.resetEncoders(),
                drivetrain
            ),
            new DriveToDistanceCommand(-5, drivetrain)
        );

    }
}



}
