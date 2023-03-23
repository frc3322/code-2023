// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Fourbar;
import frc.robot.commands.MoveClawCommand;
import frc.robot.Types;
// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class PlaceConeCommandGroup extends SequentialCommandGroup {
  /** Creates a new PlaceConeCommandGroup. */
  private Claw claw;
  private Fourbar fourbar;
  public PlaceConeCommandGroup(Claw claw, Fourbar fourbar) {
    addRequirements(claw, fourbar);
    this.claw=claw;
    this.fourbar=fourbar;
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new MoveClawCommand(Types.ClawPosition.CLOSED, claw),
      fourbar.createMoveCommand(Types.FourbarPosition.EXTEND),
      new WaitCommand(2),
      new MoveClawCommand(Types.ClawPosition.OPEN, claw),
      new WaitCommand(0.5),
      new MoveClawCommand(Types.ClawPosition.CLOSED, claw),
      new WaitCommand(0.5),
      fourbar.createMoveCommand(Types.FourbarPosition.RETRACT),
      new WaitCommand(0.5),
      new MoveClawCommand(Types.ClawPosition.OPEN, claw));
  }
}
