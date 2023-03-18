// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
//arooshwashere
import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.TransferConstants;
import frc.robot.Types.FourbarPosition;
import frc.robot.commands.AutonBalanceCommand;
import frc.robot.commands.DriveToDistanceCommand;
import frc.robot.commands.MoveClawCommand;
//import frc.robot.commands.MoveFourbarCommand;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Fourbar;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Transfer;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.Logger;
import io.github.oblarg.oblog.annotations.Config;

public class RobotContainer implements Loggable{

  // The robot's subsystems and commands are defined here...

  private final Drivetrain drivetrain = new Drivetrain();
  private final Intake intake = new Intake();
  private final Claw claw = new Claw();
  private final Fourbar fourbar = new Fourbar();
  private final Transfer transfer = new Transfer();
  

 private double speedy = -1;

//  @Config
//  public void setty(double x){
//     speedy = x;
//  }

  private final CommandXboxController driverController = new CommandXboxController(0);
  private final CommandXboxController secondaryController = new CommandXboxController(1);

  SendableChooser<Command> autChooser = new SendableChooser<>();

  private final Command driveCommand = new RunCommand(
      () -> {
        double speed = MathUtil.applyDeadband(driverController.getLeftY(), 0.09);
        double turn = MathUtil.applyDeadband(driverController.getRightX(), 0.08);

        drivetrain.drive(speed, turn);
      }, drivetrain);


  
  public RobotContainer() {
    Logger.configureLoggingAndConfig(this, false);
    autChooser.addOption("nothing", null);
    autChooser.addOption("place and leave", new PlaceAndLeave());
    autChooser.addOption("just place", new JustPlace());
    autChooser.addOption("place balance", new PlaceBalance());
    autChooser.setDefaultOption("just place", new JustPlace());
    SmartDashboard.putData("select autonomous", autChooser);

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
    transfer.setDefaultCommand(transfer.beltRunCommand(transfer));
  
    // driver controller (0) commands

    //Automated intake control
    driverController
        .leftBumper()
        .onTrue(
          new InstantCommand(()-> fourbar.fourbarDown())
          .andThen(new WaitCommand(.5).unless(()->fourbar.getFourBarPosition() == FourbarPosition.RETRACT))

          .andThen(intake.flipDownSpin()))
        .onFalse(intake.flipUpStop());

   

    //Driver 4 bar toggle OR eject... needs testing
    driverController
        .a()
        .whileTrue(new StartEndCommand(()->intake.spinIntake(intake.testingSpeed), ()->intake.spinIntakeBottomFaster(0), intake));


    //Driver manual intake up
    driverController
        .povUp()
        .whileTrue(new StartEndCommand(() -> intake.setFlipperSpeed(-0.4), () -> intake.setFlipperSpeed(0), intake));

    //Driver manual intkae down
    driverController
        .povDown()
        .whileTrue(new StartEndCommand(() -> intake.setFlipperSpeed(0.3), () -> intake.setFlipperSpeed(0), intake));

    //Driver intake arm reset
    driverController
        .povLeft()
        .onTrue(new InstantCommand(() -> intake.resetArmEncoder()));

    //driver manual intake up number 2
    driverController
    .axisGreaterThan(2, 0)
    .onTrue(
          new InstantCommand(()-> fourbar.fourbarDown())
          .andThen(new WaitCommand(.5).unless(()->fourbar.getFourBarPosition() == FourbarPosition.RETRACT))

          .andThen(intake.cubeFlipDownSpin()))
        .onFalse(intake.flipUpStop());

    //driver manual intake down number two
    driverController
        .axisGreaterThan(3, 0)
        .whileTrue(new StartEndCommand(() -> intake.spinIntake(IntakeConstants.cubeIntakeInSpeed),
        () -> intake.spinIntake(0)));      

    // driver's claw open override
    driverController
        .b()
        .onTrue(new InstantCommand(() -> claw.setOpen(), claw));

    // x to reduce transfer speed
    driverController
        .x()
        .onTrue(new InstantCommand(() -> transfer.setActiveBeltSpeed(TransferConstants.cubeTransferSpeed)));

    // y for normal transfer function
    driverController
        .y()
        .onTrue(new InstantCommand(() -> transfer.setActiveBeltSpeed(TransferConstants.coneTransferSpeed)));

    //driver manual intake spin - REWRITE AS ACTUAL START END
    driverController
        .rightBumper()
        .whileTrue(new StartEndCommand(() -> intake.spinIntake(IntakeConstants.coneIntakeInSpeed),
            () -> intake.spinIntake(0)));


    // secondary controls timeee

    //secondary claw close
    secondaryController
        .y()
        .onTrue(new InstantCommand(() -> claw.setClosed(), claw));
            
    //secondary claw ppen
    secondaryController
        .b()
        .onTrue(new InstantCommand(() -> claw.setOpen(), claw));


    //secondary transfer stop override
    secondaryController
        .x()
        .onTrue(
          new InstantCommand(() -> transfer.setBeltPower(0))
        );



    //Secondary 4 bar in with claw and intake safety
     secondaryController
        .povDown()
        .onTrue(
            new InstantCommand(() -> claw.setClosed(), claw)
                .andThen(new WaitCommand(.5))
                .andThen(
                    new InstantCommand(() -> fourbar.fourbarDown()))
                //.andThen(new WaitCommand(.75))
                //.andThen(new InstantCommand(() -> claw.setOpen(), claw))

        );
    
    //secondary 4 bar out with claw and intake safety
    secondaryController
        .povUp()
        .onTrue(
            new InstantCommand(() -> /*claw.setClosed(), claw)
                .alongWith(*/intake.flipUp())
                .andThen(new WaitCommand(.5))
            .andThen(new InstantCommand(() -> fourbar.fourbarUp()))
            );
            
             
    //Secondary manual transfer
    secondaryController
        .axisGreaterThan(5, 0.09)
        .whileTrue(new RunCommand(
            () -> transfer.setBeltPower(MathUtil.applyDeadband(secondaryController.getRightY() / 2, 0.09)), transfer));


    //secodnary controller manual transfer other way
     secondaryController
        .axisLessThan(5, -0.09)
        .whileTrue(new RunCommand(
            () -> transfer.setBeltPower(MathUtil.applyDeadband(secondaryController.getRightY() / 2, 0.09)), transfer));

  
    //secondary manual intake up
    secondaryController
     .axisGreaterThan(2, 0)
     .whileTrue(new RunCommand(() -> intake.setFlipperSpeed(-driverController.getLeftTriggerAxis()/3), intake));

    //secondary manual intake down 
    secondaryController
        .axisGreaterThan(3, 0)
        .whileTrue(new RunCommand(() -> intake.setFlipperSpeed(driverController.getRightTriggerAxis()/3), intake));


    secondaryController
      .povRight()
      .whileTrue(
        new StartEndCommand(
         ()-> intake.spinIntakeTopFaster(IntakeConstants.coneIntakeInSpeed) ,
         ()-> intake.spinIntakeTopFaster(0),
          intake)
      );


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
    return autChooser.getSelected();     
  }

  /*
  AUTON COMMANDS BELOW
  */
  private class PlaceAndLeave extends SequentialCommandGroup {
    private PlaceAndLeave() {
        addCommands(
           
           new MoveClawCommand(Types.ClawPosition.CLOSED, claw),
          //MoveFourBarCommand works, but it doesn't move on. Is not working. Maybe because it needs to be a different type of command?
            fourbar.createMoveCommand(Types.FourbarPosition.EXTEND),
            new WaitCommand(3.5),
            new MoveClawCommand(Types.ClawPosition.OPEN, claw),
            new WaitCommand(0.5),
            new MoveClawCommand(Types.ClawPosition.CLOSED, claw),
            new WaitCommand(0.5),
            fourbar.createMoveCommand(Types.FourbarPosition.RETRACT),
            new WaitCommand(0.5),
            new MoveClawCommand(Types.ClawPosition.OPEN, claw),
            

            
            new InstantCommand(
                () -> drivetrain.resetEncoders(),
                drivetrain
            ),
            new DriveToDistanceCommand(-4, drivetrain)
        );

    }
}

private class JustPlace extends SequentialCommandGroup {
    private JustPlace() {
        addCommands(
            
            new MoveClawCommand(Types.ClawPosition.CLOSED, claw),
            //MoveFourBarCommand works, but it doesn't move on. Is not working. Maybe because it needs to be a different type of command?
            fourbar.createMoveCommand(Types.FourbarPosition.EXTEND),
            new WaitCommand(3.5),
            new MoveClawCommand(Types.ClawPosition.OPEN, claw),
            new WaitCommand(0.5),
            new MoveClawCommand(Types.ClawPosition.CLOSED, claw),
            new WaitCommand(0.5),
            fourbar.createMoveCommand(Types.FourbarPosition.RETRACT),
            new WaitCommand(0.5),
            new MoveClawCommand(Types.ClawPosition.OPEN, claw)
            
        );
    }
}


private class PlaceBalance extends SequentialCommandGroup {
    private PlaceBalance() {
        addCommands(
            
            new MoveClawCommand(Types.ClawPosition.CLOSED, claw),
            //MoveFourBarCommand works, but it doesn't move on. Is not working. Maybe because it needs to be a different type of command?
            fourbar.createMoveCommand(Types.FourbarPosition.EXTEND),
            new WaitCommand(3.5),
            new MoveClawCommand(Types.ClawPosition.OPEN, claw),
            new WaitCommand(0.5),
            new MoveClawCommand(Types.ClawPosition.CLOSED, claw),
            new WaitCommand(0.5),
            fourbar.createMoveCommand(Types.FourbarPosition.RETRACT),
            new WaitCommand(0.5),
            new MoveClawCommand(Types.ClawPosition.OPEN, claw),

            new AutonBalanceCommand(
                drivetrain,
                drivetrain::tankDriveVolts,
                false,
                drivetrain
            )

        );
    }
}
 

}
