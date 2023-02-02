package frc.robot;

import edu.wpi.first.math.trajectory.TrapezoidProfile;

public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  
  }

  public static final class CAN {
    // Drivetrain motors
    public static final int FL = 2;
    public static final int FR = 3;
    public static final int BL = 4;
    public static final int BR = 11;
    
    // Elevator
    public static final int elevatorMotor = 401;
    public static final int topLimit = 0;
    public static final int bottomLimit = 1;
    
    //Intake Motors
    public static final int tRoller = 200;
    public static final int bRoller = 201;
    public static final int extendBar = 202;

    // Pneumatics Module
    public static final int pneumaticsMod = 203;

    // Outtake Pistons
    public static final int BP = 300;
    public static final int SP = 301;

    //Fourbar Pneumatics
    public static final int extendFourbar = 400;
    public static final int retractFourbar = 401;

    public static final int T = 401;

    public static final int arm = 401;

    public static final int E = 401;
  }

  public static final class speeds{
    public static final double intakeIn = 0.5;
    public static final double intakeOut = -0.5;

    public static final double beltForward = 0.5;
    public static final double beltReverse = -0.5;


  }

  public static final class PIDConstraints{

    //random numbers for that
    public static final double TTAVelocity = 0.5;
    public static final double TTAAcceleration = 0.5;
    public static final TrapezoidProfile.Constraints TTAConstraints = new TrapezoidProfile.Constraints(TTAVelocity, TTAAcceleration);

  }

 
}
