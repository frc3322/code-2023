package frc.robot;

import edu.wpi.first.math.trajectory.TrapezoidProfile;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Config;

public final class Constants{
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  
  }

  public static final class CAN {
    // Drivetrain motors
    public static final int FL = 12;
    public static final int FR = 2;
    public static final int BL = 9;
    public static final int BR = 17;
    
    // Brake

    public static final int brakeReverse = 5;
    public static final int brakeForward = 4;
    
    
    //Intake Motors
    public static final int tRoller = 13;
    public static final int bRoller = 3;
    public static final int pivotIntake = 7;

    // Pneumatics Module
    public static final int pneumaticsMod = 1;

    // Outtake Pistons
    public static final int clawExtend = 6;
    public static final int clawRetract = 7;

    //Fourbar Pneumatics
    public static final int extendFourbar = 0;
    public static final int retractFourbar = 3;

   

    
  }

  public static final class DriveConstants {
    public static final double kTurnP = 0.015;
    public static final double kTurnI = 0;
    public static final double kTurnD = 0.0075;

    public static final double kTurnToleranceDeg = 0.5;
    public static final double kTurnRateToleranceDegPerS =5;

    public static final double kDriveP = 0.15;
    public static final double kDriveI = 0;
    public static final double kDriveD = 0.0075;
    
    public static final double kDriveToleranceMeters = 0.4;
    public static final double kDriveRateToleranceMetersPerS =0.1;

    public static final double profiledTurnToAngleMaxAccelDegPerSec = 3;
    public static final double profiledTurnToAngleMaxVelDegPerSec = 30;
    public static final double profiledDriveToDistanceMaxAccelMetersPerSec = 0.6;
    public static final double profiledDriveToDistanceMaxVelPerSec = 5;

    //public static final double encoderTicsPerFoot = 6.84;


    //0.00075 bad too slow
    //
  }


  public static final class IntakeConstants{
    @Config public static final double fastIntakeInV = 5.0;
    public static final double slowIntakeInV = 2.0;

    public static final double intakeLowV = -1.5;
    public static final double intakeMidV = -7;
    public static final double intakeHighV = -8.5;

    public static final double armUpSpeed = -0.3;
    public static final double armUpSlowSpeed = -0.2;

    public static final double armDownSpeed = 0.2; 
    
    @Config public static final double bottomRollerSpeedMultiplier = 0.6;
    public static final double bottomRollerSpeedAdditive = 0.3;

  }


  public static final class DIO {
    public static final int intakeTopSensor = 0;
    public static final int clawSensor = 2;
  }


  public static final class IntakeZoneLimits{
    
    public static final double slowZoneStart = 3;
    public static final double topLimitOff = 2;
    public static final double bottomLimitOff = 7;
    
  }

 
}
