package frc.robot;

public final class Types {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  
  }

    //Enum for transfer direction. Used in RobotContainer and SpinTransfer
    public enum TransferDirection {
      FORWARD,
      REVERSE
    }

    public enum ClawPosition {
      OPEN,
      CLOSED
    }

    public enum FourbarPosition {
      RETRACT,
      EXTEND
    }

    public enum ElevatorPosition {
      BOTTOM,
      MID,
      TOP
    }

    public enum IntakePosition {
      BOTTOM,
      TOP
    }
    public enum LaunchTo {
      LOW,
      MID,
      TOP
    }

}
