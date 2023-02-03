package frc.robot;

import edu.wpi.first.math.trajectory.TrapezoidProfile;

public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  
  }

  public static final class DriveConstants {
    public static final double kTurnP = 0.03;
    public static final double kTurnI = 0;
    public static final double kTurnD = 0.0075;
    public static final double kTurnToleranceDeg = 0.05;
    public static final double kTurnRateToleranceDegPerS = 10;

    public static final double kDriveP = 0.03;
    public static final double kDriveI = 0;
    public static final double kDriveD = 0.0075;
    public static final double kDriveToleranceDeg = 0.05;
    public static final double kDriveRateToleranceDegPerS = 10;

    public static final double encoderTicsPerFoot = 6.84;
  }

  public static final class CAN {
    // Drivetrain motors
    public static final int FL = 12;
    public static final int FR = 2;
    public static final int BL = 9;
    public static final int BR = 17;
    
    // Elevator
    public static final int elevatorMotor = 8;
    public static final int topLimit = 0;
    public static final int bottomLimit = 1;
    
    //Intake Motors
    public static final int tRoller = 13;
    public static final int bRoller = 3;
    public static final int extendBar = 45;

    // Pneumatics Module
    public static final int pneumaticsMod = 15;

    // Outtake Pistons
    public static final int BP = 16;
    public static final int SP = 13;

    //Fourbar Pneumatics
    public static final int extendFourbar = 18;
    public static final int retractFourbar = 19;

    public static final int T = 20;

    public static final int arm = 21;

    public static final int E = 22;
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
