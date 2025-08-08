package org.firstinspires.ftc.teamcode;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.android.AndroidSoundPool;

@TeleOp(name = "CCprogram2 (Blocks to Java)")
public class CCprogram2 extends LinearOpMode {

  private DcMotor RightWheelMotor;
  private DcMotor shoulder;
  private Servo elbow;
  private CRServo intake;
  private AndroidSoundPool androidSoundPool;
  private DcMotor LeftWheelMotor;

  /**
   * This OpMode offers Tank Drive style TeleOp control for a direct drive robot.
   *
   * In this Tank Drive mode, the left and right joysticks (up
   * and down) drive the left and right motors, respectively.
   */
  @Override
  public void runOpMode() {
    int max_speed;
    int locked;

    RightWheelMotor = hardwareMap.get(DcMotor.class, "RightWheelMotor");
    shoulder = hardwareMap.get(DcMotor.class, "shoulder");
    elbow = hardwareMap.get(Servo.class, "elbow");
    intake = hardwareMap.get(CRServo.class, "intake");
    androidSoundPool = new AndroidSoundPool();
    LeftWheelMotor = hardwareMap.get(DcMotor.class, "LeftWheelMotor");

    // Reverse one of the drive motors.
    // You will have to determine which motor to reverse for your robot.
    // In this example, the right motor was reversed so that positive
    // applied power makes it move the robot in the forward direction.
    RightWheelMotor.setDirection(DcMotor.Direction.REVERSE);
    shoulder.setDirection(DcMotor.Direction.REVERSE);
    shoulder.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    shoulder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    elbow.setDirection(Servo.Direction.FORWARD);
    elbow.setPosition(1);
    intake.setDirection(CRServo.Direction.REVERSE);
    intake.setPower(0);
    androidSoundPool.initialize(SoundPlayer.getInstance());
    androidSoundPool.setVolume(1);
    max_speed = -1;
    locked = 0;
    waitForStart();
    androidSoundPool.play("RawRes:ss_alarm");
    if (opModeIsActive()) {
      // Put run blocks here.
      while (opModeIsActive()) {
        // Put loop blocks here.
        if (gamepad1.a) {
          max_speed = max_speed == -0.5 ? -1 : -0.5;
        }
        // The Y axis of a joystick ranges from -1 in its topmost position to +1 in its bottommost position.
        // We negate this value so that the topmost position corresponds to maximum forward power.
        LeftWheelMotor.setPower(Math.min(Math.max(gamepad1.left_stick_y, max_speed), 1));
        RightWheelMotor.setPower(locked ? LeftWheelMotor.getPower() : Math.min(Math.max(gamepad1.right_stick_y, max_speed), 1));
        shoulder.setPower(gamepad2.right_stick_y * 0.5);
        telemetry.addData("Max Speed", max_speed);
        telemetry.addData("Locked", locked);
        telemetry.addData("Left Pow", LeftWheelMotor.getPower());
        telemetry.addData("Right Pow", RightWheelMotor.getPower());
        telemetry.update();
        locked = gamepad1.b;
      }
    }

    androidSoundPool.close();
  }
}
