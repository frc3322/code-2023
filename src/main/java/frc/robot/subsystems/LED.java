// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.util.HashMap;
import java.util.function.BooleanSupplier;

class State {
  private BooleanSupplier condition;
  private double ledPattern;

  private State(BooleanSupplier condition, double ledPattern) {
    this.condition = condition;
    this.ledPattern = ledPattern;
  }

  public static State defaultState(double pattern) {
    return new State(() -> true, pattern);
  }

  public static State conditionalState(double pattern) {
    return new State(() -> false, pattern);
  }

  public void setCondition(BooleanSupplier condition) {
    this.condition = condition;
  }

  public boolean conditionMet() {
    return condition.getAsBoolean();
  }

  public double getPattern() {
    return ledPattern;
  }
}

public class LEDs extends SubsystemBase {
  public enum Modes {
   RED
   BLUE
   CUBE
   CONE
  }

  // Color constants here: https://www.revrobotics.com/content/docs/REV-11-1105-UM.pdf
  private final double RED = 0.61;
  private final double PURPLE = 0.85;
  private final double YELLOW = 0.71;
  private final double BLUE = -0.99;

  private static final LEDs instance = new LEDs();
  private final Spark blinkin = new Spark(0);
  // First state listed is default, last state listed is highest priority
  private Modes[] priority =
      new Modes[] {
        Modes.RED, Modes.BLUE, Modes.CUBE, Modes.CONE,
      };

  private HashMap<Modes, State> stateMap = new HashMap<>();

  /** Creates a new LEDs. */
  public LEDs() {
    stateMap.put(Modes.RED, State.defaultState(SOLIDBLUE));
    stateMap.put(Modes.BLUE, State.conditionalState(RAINBOW));
    stateMap.put(Modes.CUBE, State.conditionalState(SOLIDGREEN));
    stateMap.put(Modes.CONE, State.conditionalState(SOLIDBLUE));
  }

  public void setCondition(Modes mode, BooleanSupplier condition) {
    stateMap.get(mode).setCondition(condition);
  }

  @Override
  public void periodic() {
    double output = 0;
    for (Modes mode : priority) {
      State state = stateMap.get(mode);
      if (state.conditionMet()) {
        output = state.getPattern();
      }
    }
    blinkin.set(output);
  }

  public static LEDs get() {
    return instance;
  }
}

 