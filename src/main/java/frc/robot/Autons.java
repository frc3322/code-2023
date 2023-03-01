// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.MoveClawCommand;
import frc.robot.commands.MoveFourbarCommand;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Fourbar;

/** Add your docs here. */
public class Autons {
    Claw claw = new Claw();
    Fourbar fourbar = new Fourbar();

    public class PlaceAndLeave extends SequentialCommandGroup {
        public PlaceAndLeave() {
            addCommands(
            //MoveFourBarCommand works, but it doesn't move on. Is not working. Maybe because it needs to be a different type of command?
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
