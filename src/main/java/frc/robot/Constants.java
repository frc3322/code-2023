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
    //public static final double encoderTicsPerFoot = 6.84;


    //0.00075 bad too slow
    //
  }


  public static final class IntakeConstants{
    @Config public static final double fastIntakeInV = 5.0;
    public static final double slowIntakeInV = 2.0;

    public static final double intakeLowV = -3;
    public static final double intakeMidV = -6.5;
    public static final double intakeHighV = -11;

    public static final double armUpSpeed = -0.3;
    public static final double armUpSlowSpeed = -0.2;

    public static final double armDownSpeed = 0.2; 
    
    @Config public static final double bottomRollerSpeedMultiplier = 0.6;
    public static final double bottomRollerSpeedAdditive = 0.3;

  }


  public static final class DIO {
    public static final int intakeTopSensor = 0;

  }


  public static final class IntakeZoneLimits {
    
    public static final double slowZoneStart = 3;
    public static final double topLimitOff = 2;
    public static final double bottomLimitOff = 7;
    
  }

  public static final class LEDConstants {

    public static final int blinkinPort = 1;
    
    public static final double rainbowValue = -0.99;
    public static final double rainbowPartyValue = -0.97;
    public static final double rainbowChaseValue = -0.79;
    public static final double blueValue = 0.83;
    public static final double purpleValue = 0.91;
    public static final double yellowValue = 0.69;
    public static final double oceanSinelon = -0.75;

  }

}
