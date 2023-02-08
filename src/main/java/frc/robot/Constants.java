package frc.robot;

import edu.wpi.first.math.trajectory.TrapezoidProfile;

public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  
  }

  public static final class CAN {
    // Drivetrain motors
    public static final int FL = 12;
    public static final int FR = 2;
    public static final int BL = 9;
    public static final int BR = 17;
    
    // Elevator

    public static final int elevatorMotor = 36;
    public static final int topLimit = 0;
    public static final int bottomLimit = 1;
    
    //Intake Motors
    public static final int tRoller = 13;
    public static final int bRoller = 3;
    public static final int pivotIntake = 7;

    // Pneumatics Module
    public static final int pneumaticsMod = 0;

    // Outtake Pistons
    public static final int BP = 16;
    public static final int SP = 14;

    //Fourbar Pneumatics
    public static final int extendFourbar = 18;
    public static final int retractFourbar = 19;

    //transfer can
    public static final int transfer = 5;


    
  }

  public static final class DriveConstants {
    public static final double kTurnP = 0.03;
    public static final double kTurnI = 0;
    public static final double kTurnD = 0.0075;
    public static final double kTurnToleranceDeg = 0.05;
    public static final double kTurnRateToleranceDegPerS =0;
    public static final double kDriveP = 0.03;
    public static final double kDriveI = 0;
    public static final double kDriveD = 0.0075;
    public static final double kDriveToleranceDeg = 0.05;
    public static final double kDriveRateToleranceDegPerS =0;
    public static final double encoderTicsPerFoot = 6.84;
  }

  public static final class ElevatorConstants {
    public static final double kBottomEncoderPosition = 0;
    public static final double kTopEncoderPosition = 1;
    public static final double kp = 0;
    public static final double ki = 0;
    public static final double kd = 0;

    
  }

  public static final class IntakeConstants{
    public static final double intakeInSpeed = 0.5;
    public static final double intakeOutSpeed = 0; 
    public static final double armUpSpeed = 0.5;
    public static final double armDownSpeed = 0; 
    public static final double beltForwardSpeed = 0.5;
    public static final double beltReverseSpeed = -0.5;


  }

  public static final class PIDConstraints{

    //random numbers for that
    public static final double TTAVelocity = 0.5;
    public static final double TTAAcceleration = 0.5;
    public static final TrapezoidProfile.Constraints TTAConstraints = new TrapezoidProfile.Constraints(TTAVelocity, TTAAcceleration);

  }

  public static final class DIO {
    public static final int transferInProximitySensor = 0;
    public static final int transferOutProximitySensor = 2;
  }

  public static final class IntakeZoneLimits{
    
    public static final double slowZoneStart = 0;
    
    
  }

 
}
