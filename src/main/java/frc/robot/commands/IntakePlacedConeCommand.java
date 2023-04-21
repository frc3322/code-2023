// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.BiConsumer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.IntakeConstants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;

public class IntakePlacedConeCommand extends CommandBase {
  private Drivetrain drivetrain;
  private Intake intake;
  private BiConsumer<Double, Double> output;
  private boolean intakeDown = false;
  
  private enum States{
    START,
    CLIMB,
    DESCEND,
    END
  }

  private States state = States.START;

  private double robotSpeed = -4;
  private double levelDegree = -4;
  private double endRunTime = 1; //1.5

  public double cubeIntakeTime = 2;

  private double time;
  
  
  /** Creates a new DriveOverChargeStation. */
  public IntakePlacedConeCommand(Drivetrain drivetrain, Intake intake) {
    this.drivetrain = drivetrain;
    this.intake = intake;
    this.output = drivetrain::tankDriveVolts;
    addRequirements(drivetrain);
  }

  private int secondsToTicks(double time) {
    return (int) (time * 50);
  }

  private double getSpeed(){
    switch(state){
      case START:
        if(drivetrain.getPitch() < levelDegree){
          state = States.CLIMB;
        }
        break;
      case CLIMB:
        if(drivetrain.getPitch() > -levelDegree){
          state = States.DESCEND;
        }
        break;
      case DESCEND:
        if(Math.abs(drivetrain.getPitch()) > levelDegree){
          time++;
        }
        if(time > secondsToTicks(endRunTime)){
          state = States.END;
        }
        break;
      case END:
        return 0;
    }

    switch(state){
      case START:
        return robotSpeed;
      case CLIMB:
        return robotSpeed;
      case DESCEND:
        if(!intakeDown) {
            if(!intake.atBottom()) {
                intake.setFlipperSpeed(intake.calculateIntakeFlipDown());
            }
            intakeDown = intake.atBottom();
            if (time > secondsToTicks(cubeIntakeTime)) {
                intake.spinIntake(IntakeConstants.slowIntakeInV);
            }
            else {
                intake.spinIntake(0);
            }
        }
        return robotSpeed;
      case END:
        return 0;
    }
    return 0;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    state = States.START;
    time = 0;
    intakeDown = false;
    output.accept(0.0, 0.0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speed = getSpeed();
    output.accept(speed, speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    output.accept(0.0, 0.0);
    intake.spinIntake(0);
    while(!intake.atTop()) {
        intake.setFlipperSpeed(intake.calculateIntakeFlipUp());
    }

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return state == States.END;
  }
}

