// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;



import java.util.function.BiConsumer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.Drivetrain;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Log;




public class AutonBalanceAdjusted extends CommandBase implements Loggable{
  
  private Drivetrain drivetrain;
  private BiConsumer<Double, Double> output;

  @Log private int state;
  private int debounceCount;
  private double robotSpeedSlow;
  private double robotSpeedMid;
  private double robotSpeedFast;
  private double onChargeStationDegree;
  private double levelDegree;
  private double debounceTime;

  /** Creates a new AutoBalanceCommand. */
  public AutonBalanceAdjusted( 
    Drivetrain drivetrain, 
    BiConsumer<Double, Double> outputVolts,
    boolean reverse, 
    Subsystem... reqirements) {
    // Use addRequirements() here to declare subsystem dependencies.
    int reverseModifier = 1;
    
    if (reverse){
      reverseModifier = -1;
    }

    state = 0;
    debounceCount = 0;

    // Speed the robot drived while scoring/approaching station
    robotSpeedFast = 1.7 * reverseModifier;

    // Speed the robot drives once it is on the charging station
    robotSpeedMid = 1.7 * reverseModifier;

    // Speed the robot drives to complete the balance
    robotSpeedSlow = 1.7 * reverseModifier;

    // Angle where the robot knows it is on the charge station
    onChargeStationDegree = 12 /* * reverseModifier*/;

    // Angle where the robot can assume it is level on the charging station
    levelDegree = 3;

    // Amount of time a sensor condition needs to be met before changing states in seconds. Only used in state 1
    debounceTime = 0.3;
    
    this.drivetrain = drivetrain;
    this.output = outputVolts;
    
    addRequirements(reqirements);
  }

  public int secondsToTicks(double time) {
    return (int) (time * 50);
  }

  public double getPitch(){
    return -drivetrain.getPitch();
  }

  public double autoBalanceRoutine() {
    switch (state) {
        // drive forwards to approach station, exit when tilt is detected
        case 0:
            if (getPitch() > onChargeStationDegree) {
                state = 1;
            }
        return robotSpeedFast;
        // driving up charge station, drive slower, stopping when level
        case 1:
            if (getPitch() < levelDegree / 2) {
                debounceCount++;
            }
            if (debounceCount > secondsToTicks(debounceTime)) {
                state = 2;
                debounceCount = 0;
                return 0;
            }
            return robotSpeedMid;
        // on charge station, stop motors and wait for end of auto
        case 2:
            if (Math.abs(getPitch()) <= levelDegree / 2) {
                state = 3;
                return 0;
            }
            new DriveToDistanceCommand(.15, drivetrain);
            return 0;
            
        case 3:
            return 0;
    }
    return 0;
}

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    output.accept(autoBalanceRoutine(), autoBalanceRoutine());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return state == 3;
  }
}