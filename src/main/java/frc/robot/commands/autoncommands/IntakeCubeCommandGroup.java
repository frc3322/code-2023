// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autoncommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Drivetrain;

import frc.robot.commands.DriveToDistanceCommand;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class IntakeCubeCommandGroup extends SequentialCommandGroup {
  /** Creates a new IntakeCubeGroup. */
  private Intake intake;
  private Drivetrain drive;

  public IntakeCubeCommandGroup(Intake intake, Drivetrain drive) {
    addRequirements(intake, drive);
    this.intake=intake;
    this.drive=drive;
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new InstantCommand(()->intake.flipDownSpin()),
      new WaitCommand(.5),
      new DriveToDistanceCommand(.25, drive),
      new InstantCommand(()->intake.flipUpStop()),
      new DriveToDistanceCommand(-.25, drive)
    );
  }
}
