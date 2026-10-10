package org.firstinspires.ftc.teamcode.hardware.subsystems;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.firstinspires.ftc.teamcode.hardware.devices.Motor;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.Range;

public class Intake {
  private final List<CRServo> servos;
  private final List<Motor> motors;

  public Intake(CRServo... servos) {
    this.servos = Collections.unmodifiableList(Arrays.asList(servos));
    this.motors = Collections.emptyList();
  }

  public Intake(Motor... motors) {
    this.servos = Collections.emptyList();
    this.motors = Collections.unmodifiableList(Arrays.asList(motors));
  }

  public Intake(DcMotorEx... motors) {
    this.servos = Collections.emptyList();
    this.motors = List.of();
    for (DcMotorEx motor : motors)
      this.motors.add(new Motor(motor));
  }

  // Batch power controls
  public void setPowerAll(double power) {
    double p = Range.clip(power, -1.0, 1.0);
    for (CRServo s : servos)
      s.setPower(p);
    for (Motor m : motors)
      m.setPower(p);
  }

  public void stopAll() {
    setPowerAll(0.0);
  }

  // Utilities
  public int size() {
    return servos.size() + motors.size();
  }

  public CRServo getServo(int index) {
    return servos.get(index);
  }

  public Motor getMotor(int index) {
    return motors.get(index);
  }

  public List<CRServo> asServoList() {
    return servos; // already unmodifiable
  }

  public List<Motor> asMotorList() {
    return motors; // already unmodifiable
  }

  public double[] getPowers() {
    double[] out = new double[this.size()];
    for (int i = 0; i < servos.size(); i++)
      out[i] = servos.get(i).getPower();
    for (int i = 0; i < motors.size(); i++)
      out[servos.size() + i] = motors.get(i).getPower();
    return out;
  }
}