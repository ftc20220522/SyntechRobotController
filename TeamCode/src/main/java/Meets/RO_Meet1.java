package Meets;

import static java.lang.Math.signum;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import java.util.concurrent.TimeUnit;


@TeleOp(group = "FINALCODE")
@Disabled
public class RO_Meet1 extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        //Center Odometery Wheel in Motor Port 0 (motor1 encoder)
        //Right Odometery Wheel in Motor Port 1 (motor2 encoder)
        //Left Odometery Wheel in Motor Port 2 (motor3 encoder)

        DcMotor motorBackRight = hardwareMap.dcMotor.get("motor1");
        DcMotor motorFrontRight = hardwareMap.dcMotor.get("motor2");
        DcMotor motorBackLeft = hardwareMap.dcMotor.get("motor3");
        DcMotor motorFrontLeft = hardwareMap.dcMotor.get("motor4");
        DcMotor motorIntake = hardwareMap.dcMotor.get("motor5");
        DcMotorEx motorSlideLeft = hardwareMap.get(DcMotorEx.class, "motor6");
        motorSlideLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorSlideLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        DcMotorEx motorSlideRight= hardwareMap.get(DcMotorEx.class, "motor7");
        motorSlideRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DcMotor temp = hardwareMap.dcMotor.get("motor8");
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        Servo servoLauncher = hardwareMap.servo.get("servo7");
        Servo servoROT = hardwareMap.servo.get("servo2");
        servoROT.setDirection(Servo.Direction.REVERSE);
        Servo servoLOT = hardwareMap.servo.get("servo3");
        Servo servoTOT = hardwareMap.servo.get("servo5");
        servoTOT.setDirection(Servo.Direction.REVERSE);
        Servo servoBOT = hardwareMap.servo.get("servo4");
        CRServo servoInt = hardwareMap.crservo.get("servo6");


//        motorSlideRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
//        motorSlideLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        /*
         Reverse the right side motors
         Reverse left motors if you are using NeveRests
         motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
         motorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);
        */

        servoLauncher.setPosition(0.67);

        waitForStart();
        if (isStopRequested()) return;

        motorSlideLeft.setTargetPosition(0);
        motorSlideRight.setTargetPosition(0);
        servoTOT.setPosition(0.89);
        servoBOT.setPosition(1);

        double y;
        double x;
        double rx;
        int position = 0;
        int prevposition = 0;
        boolean a = false;
        boolean pull = false;
        boolean toggle = false;
        int speed = 4000;
        long start;
        long end = 0;
        boolean settime = false;

        while (opModeIsActive()) {


            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio, but only when
            // at least one is out of the range [-1, 1]
            // servo2.setDirection(Servo.Direction.REVERSE);
            if (gamepad1.right_trigger > 0) {
                y = -gamepad1.left_stick_y; // Remember, this is reversed!
                x = gamepad1.left_stick_x; // Counteract imperfect strafing
                rx = gamepad1.right_stick_x;
            } else if (gamepad1.left_trigger > 0) {
                y = 0.25 * -gamepad1.left_stick_y; // Remember, this is reversed!
                x = 0.25 * gamepad1.left_stick_x; // Counteract imperfect strafing
                rx = 0.35 * gamepad1.right_stick_x;
            } else {
                y = -0.5 * gamepad1.left_stick_y; // Remember, this is reversed!
                x = 0.5 * gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
                rx = 0.65 * gamepad1.right_stick_x;
            }

            /*
             Denominator is the largest motor power (absolute value) or 1
             This ensures all the powers maintain the same ratio, but only when
             at least one is out of the range [-1, 1]
            */
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;
            motorFrontLeft.setPower(-frontLeftPower);
            motorBackLeft.setPower(-backLeftPower);
            motorFrontRight.setPower(frontRightPower);
            motorBackRight.setPower(backRightPower);


//            telemetry.addData("odometer middle pos", motorBackRight.getCurrentPosition());
//            telemetry.addData("odometer right pos", motorFrontRight.getCurrentPosition());
//            telemetry.addData("odometer left pos", motorBackLeft.getCurrentPosition());
//            telemetry.update();

            //Viper Slide Preset
            if (gamepad2.x) {
                speed=4000;
                position = 1000;
            }
            if (gamepad2.y) {
                speed=4000;
                position = 1750;
            }
            if (gamepad2.b) {
                speed=4000;
                position = 2250;
            }
            if (gamepad2.a) {
                speed=4000;
                position = 0;
            }

            if (gamepad2.left_stick_y != 0) {
                motorSlideRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                motorSlideLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                motorSlideRight.setVelocity(-signum(gamepad2.left_stick_y)*1900);
                motorSlideLeft.setVelocity(-signum(gamepad2.left_stick_y)*2000);
                position = motorSlideLeft.getCurrentPosition();
                prevposition = position;
                a = true;
            } else if (a) {
                motorSlideRight.setVelocity(0);
                motorSlideLeft.setVelocity(0);
                motorSlideRight.setTargetPosition(motorSlideLeft.getCurrentPosition());
                motorSlideRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motorSlideRight.setVelocity(1000);
                position = motorSlideLeft.getCurrentPosition();
                prevposition = position;
                a = false;
            }
            if (prevposition != position && gamepad2.left_stick_y == 0) {
                motorSlideRight.setTargetPosition(position);
                motorSlideLeft.setTargetPosition(position);
                motorSlideRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motorSlideLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motorSlideRight.setVelocity(speed);
                motorSlideLeft.setVelocity(speed);
                prevposition=position;
            }
            telemetry.addData("position", position);
            telemetry.addData("right", motorSlideRight.getCurrentPosition());
            telemetry.addData("positionReal", motorSlideRight.getTargetPosition());
            telemetry.addData("lefd", motorSlideLeft.getCurrentPosition());
            telemetry.addData("leftOdometry", temp.getCurrentPosition());
            telemetry.addData("rightOdometry", temp.getCurrentPosition());
            telemetry.addData("midOdometry", temp.getCurrentPosition());

            //Top of D Pad - servoROT = "servo1" & servoLOT = "servo2"
            if (gamepad2.dpad_up) {
                if (servoLOT.getPosition() > 0.8 && servoROT.getPosition() < 0.1) {
                    servoLOT.setPosition(0.14);
                    servoROT.setPosition(0.8);
                    TimeUnit.MILLISECONDS.sleep(350);
                } else {
                    servoLOT.setPosition(0.84);
                    servoROT.setPosition(0.08);
                    TimeUnit.MILLISECONDS.sleep(350);
                }
            }

            //Left of D Pad - servoLOT = "servo2"
            if (gamepad2.dpad_left) {
                if (servoLOT.getPosition() > 0.8) {
                    servoLOT.setPosition(0.14);
                    TimeUnit.MILLISECONDS.sleep(350);
                } else {
                    servoLOT.setPosition(0.84);
                    TimeUnit.MILLISECONDS.sleep(350);
                }
            }

            //Right of D Pad - servoROT = "servo1"
            if (gamepad2.dpad_right) {
                if (servoROT.getPosition() < 0.2) {
                    servoROT.setPosition(0.67);
                    TimeUnit.MILLISECONDS.sleep(350);
                } else {
                    servoROT.setPosition(0.14);
                    TimeUnit.MILLISECONDS.sleep(350);
                }
            }
            if (gamepad1.a) {
                if (servoBOT.getPosition() < 0.95) {
                    servoBOT.setPosition(1);
                } else {
                    servoBOT.setPosition(0.9);
                }
            }
            //Outtake Servos - WORKING: DO NOT TOUCH
            if (gamepad2.left_bumper) {
                if (servoTOT.getPosition()<0.7) {
                    //Down
                    if (position != 0) {
                        position = 0;
                        motorSlideRight.setTargetPosition(0);
                        motorSlideLeft.setTargetPosition(0);
                        motorSlideRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        motorSlideLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        motorSlideRight.setVelocity(4000);
                        motorSlideLeft.setVelocity(4000);
                        prevposition = position;
                        TimeUnit.MILLISECONDS.sleep(1100);
                    }
                    servoBOT.setPosition(1.0);
                    TimeUnit.MILLISECONDS.sleep(900);
                    servoTOT.setPosition(0.89);
                    TimeUnit.MILLISECONDS.sleep(350);
                } else {
                    //UP
                    servoBOT.setPosition(0.92);
                    if (position < 500) {
                        position = 550;
                        motorSlideRight.setTargetPosition(550);
                        motorSlideLeft.setTargetPosition(550);
                        motorSlideRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        motorSlideLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        motorSlideRight.setVelocity(1000);
                        motorSlideLeft.setVelocity(1000);
                        prevposition = position;
                        TimeUnit.MILLISECONDS.sleep(1100);
                    }
                    servoTOT.setPosition(0.685);
                    servoBOT.setPosition(0.45);
                    TimeUnit.MILLISECONDS.sleep(1100);
                    servoBOT.setPosition(0);
                    TimeUnit.MILLISECONDS.sleep(350);
                }
            }

            telemetry.addData("servo r pos.", servoROT.getPosition());
            telemetry.addData("servo l pos.", servoLOT.getPosition());
            telemetry.addData("servo bottom pos.", servoBOT.getPosition());
            telemetry.addData("servo top pos.", servoTOT.getPosition());
            telemetry.update();

            //Intake
            if (gamepad1.left_bumper) {
                motorIntake.setPower(1);
                servoInt.setPower(1);
            } else if (gamepad1.right_bumper) {
                motorIntake.setPower(-1);
                servoInt.setPower(-1);
            } else {
                motorIntake.setPower(0);
                servoInt.setPower(0);
            }

            //Flight Launcher
            if (gamepad2.start) {
                servoLauncher.setPosition(0.0);
                TimeUnit.MILLISECONDS.sleep(350);
            } else {
                servoLauncher.setPosition(0.3);
            }

            //Pull Up
            if (gamepad1.back && !pull) {
                servoTOT.setPosition(0.685);
                position = 3070;
                TimeUnit.MILLISECONDS.sleep(350);
                pull = true;
            } else if (gamepad1.back && pull){
                speed = 800;
                position = 50;
                motorBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                motorFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                motorFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                motorBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                TimeUnit.MILLISECONDS.sleep(1000);
                servoTOT.setPosition(0.89);
            }
        }
    }
}