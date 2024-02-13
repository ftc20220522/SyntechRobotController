package Regionals;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import org.firstinspires.ftc.robotcore.internal.system.Deadline;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import java.util.concurrent.TimeUnit;

@Autonomous(name="AutoOutsideBlue")
public class AutoRegionalsOB extends LinearOpMode {
    private final int READ_PERIOD = 2;
    int location = 0;
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        DcMotor motorBackRight = hardwareMap.dcMotor.get("motor8");
        DcMotor motorFrontRight = hardwareMap.dcMotor.get("motor7");
        DcMotor motorBackLeft = hardwareMap.dcMotor.get("motor2");
        DcMotor motorFrontLeft = hardwareMap.dcMotor.get("motor3");
        DcMotor motorIntake = hardwareMap.dcMotor.get("motor5");
        DcMotor motorLauncher = hardwareMap.dcMotor.get("motor4");
        DcMotorEx motorSlideLeft = hardwareMap.get(DcMotorEx.class, "motor1");
        motorSlideLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DcMotorEx motorSlideRight = hardwareMap.get(DcMotorEx.class, "motor6");
        motorSlideRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorSlideRight.setDirection(DcMotorSimple.Direction.REVERSE);

        Servo servoClamp = hardwareMap.servo.get("servo1");
        Servo servoHOT = hardwareMap.servo.get("servo5"); //Hook ot
        Servo servoFOT = hardwareMap.servo.get("servo4"); // flap ot
        Servo servoTOT = hardwareMap.servo.get("servo2"); // top ot
        Servo servoBOT = hardwareMap.servo.get("servo3"); // bottom ot
        Servo servoFL = hardwareMap.servo.get("servo6");

        HuskyLens huskyLens = hardwareMap.get(HuskyLens.class, "huskylens");
        Pose2d startPose = new Pose2d(-38, 61, Math.toRadians(90));
        drive.setPoseEstimate(startPose);

        motorSlideLeft.setTargetPosition(0);
        motorSlideRight.setTargetPosition(0);
        servoClamp.setPosition(0.6);
        servoTOT.setPosition(0.83);
        servoBOT.setPosition(0.69);
        servoFOT.setPosition(0.51);
        servoHOT.setPosition(0.67);
        servoFL.setPosition(0.69);

        //Left Movement
        TrajectorySequence purpleL = drive.trajectorySequenceBuilder(startPose)
                .lineToConstantHeading(new Vector2d(-38,31))
                .lineToConstantHeading(new Vector2d(-32,31))
                .build();
        //Common
        TrajectorySequence whiteL = drive.trajectorySequenceBuilder(purpleL.end())
                .lineToLinearHeading(new Pose2d(-62, 36, 0))
                .build();
        TrajectorySequence toBoardL = drive.trajectorySequenceBuilder(whiteL.end())
                .lineToConstantHeading(new Vector2d(-58,34.5))
                .lineToConstantHeading(new Vector2d(-58,11.5))
                .lineToConstantHeading(new Vector2d(20,11.5))
                .build();
        //Board Pixel
        TrajectorySequence posL = drive.trajectorySequenceBuilder(toBoardL.end())
                .lineToConstantHeading(new Vector2d(46,40))
                .lineToConstantHeading(new Vector2d(55.5,40))
                .build();
        TrajectorySequence endL = drive.trajectorySequenceBuilder(posL.end())
                .lineToConstantHeading(new Vector2d(46,40))
                .build();



        //Middle Movement
        TrajectorySequence purpleM = drive.trajectorySequenceBuilder(startPose)
                .lineToLinearHeading(new Pose2d(-35, 29, 0))
                .build();
        //Common
        TrajectorySequence whiteM = drive.trajectorySequenceBuilder(purpleM.end())
                .lineToLinearHeading(new Pose2d(-62, 36, 0))
                .build();
        TrajectorySequence toBoardM = drive.trajectorySequenceBuilder(whiteM.end())
                .lineToConstantHeading(new Vector2d(-58,34.5))
                .lineToConstantHeading(new Vector2d(-58,11.5))
                .lineToConstantHeading(new Vector2d(20,11.5))
                .build();
        //Board Pixel
        TrajectorySequence posM = drive.trajectorySequenceBuilder(toBoardM.end())
                .lineToConstantHeading(new Vector2d(46,33))
                .lineToConstantHeading(new Vector2d(55.5,33))
                .build();
        TrajectorySequence endM = drive.trajectorySequenceBuilder(posM.end())
                .lineToConstantHeading(new Vector2d(46,33))
                .build();



        //Right Movement
        TrajectorySequence purpleR = drive.trajectorySequenceBuilder(startPose)
                .lineToLinearHeading(new Pose2d(-46,30,0))
                .build();
        //Common
        TrajectorySequence whiteR = drive.trajectorySequenceBuilder(purpleR.end())
                .lineToConstantHeading(new Vector2d(-46,36))
                .lineToConstantHeading(new Vector2d(-62,36))
                .build();
        TrajectorySequence toBoardR = drive.trajectorySequenceBuilder(whiteR.end())
                .lineToConstantHeading(new Vector2d(-58,34.5))
                .lineToConstantHeading(new Vector2d(-58,11.5))
                .lineToConstantHeading(new Vector2d(20,11.5))
                .build();
        //Board Pixel
        TrajectorySequence posR = drive.trajectorySequenceBuilder(toBoardR.end())
                .lineToConstantHeading(new Vector2d(46,29))
                .lineToConstantHeading(new Vector2d(55.5,29))
                .build();
        TrajectorySequence endR = drive.trajectorySequenceBuilder(posR.end())
                .lineToConstantHeading(new Vector2d(46,29))
                .build();


        //Husky Lens
        Deadline rateLimit = new Deadline(READ_PERIOD, TimeUnit.SECONDS);
        rateLimit.expire();
        if (!huskyLens.knock()) {
            telemetry.addData(">>", "Problem communicating with " + huskyLens.getDeviceName());
        } else {
            telemetry.addData(">>", "Press start to continue");
        }
        huskyLens.selectAlgorithm(HuskyLens.Algorithm.COLOR_RECOGNITION);
        ElapsedTime timer = new ElapsedTime();
        telemetry.update();
        waitForStart();
        timer.reset();
        while (opModeIsActive()) {
            if (!rateLimit.hasExpired()) {
                continue;
            }
            rateLimit.reset();
            telemetry.update();
            HuskyLens.Block[] blocks = huskyLens.blocks();
            telemetry.addData("Block count", blocks.length);
            for (int i = 0; i < blocks.length; i++) {
                telemetry.addData("Block", blocks[i].toString());
                if (blocks[i].x <= 100) {
                    telemetry.addData("Pos:", "Left");
                    telemetry.update();
                    location = 1;
                } else if (blocks[i].x > 100 && blocks[i].x <= 200) {
                    telemetry.addData("Pos:", "Middle");
                    telemetry.update();
                    location = 2;
                } else if (blocks[i].x > 200) {
                    telemetry.addData("Pos:", "Right");
                    telemetry.update();
                    location = 3;
                }
            }
            if (blocks.length == 0 && timer.milliseconds()>1500) {
                location = 3;
            }
            if (location != 0) {
                break;
            }
        }

        if (location == 1) {
            drive.followTrajectorySequence(purpleL);
            servoClamp.setPosition(0.1);
            sleep(50);

            //White & Move
            drive.followTrajectorySequence(whiteL);
//            servoHOT.setPosition(0.52);
//            motorIntake.setPower(1);
            sleep(1000);
//            motorIntake.setPower(0);
//            servoHOT.setPosition(0.67);
            drive.followTrajectorySequence(toBoardL);

            //Viper Slides Up & Set
            motorSlideRight.setTargetPosition(1000);
            motorSlideLeft.setTargetPosition(1000);
            motorSlideRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorSlideLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorSlideRight.setVelocity(1000);
            motorSlideLeft.setVelocity(1000);
            sleep(250);
            servoTOT.setPosition(0.54);
            servoBOT.setPosition(0.47);
            sleep(700);
            motorSlideRight.setTargetPosition(0);
            motorSlideLeft.setTargetPosition(0);
            motorSlideRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorSlideLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorSlideRight.setVelocity(1000);
            motorSlideLeft.setVelocity(1000);

            //Position to Board
            drive.followTrajectorySequence(posL);
            sleep(50);
            servoFOT.setPosition(0.66);
            sleep(50);

            //End
            drive.followTrajectorySequence(endL);
            motorSlideRight.setTargetPosition(1000);
            motorSlideLeft.setTargetPosition(1000);
            motorSlideRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorSlideLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorSlideRight.setVelocity(1000);
            motorSlideLeft.setVelocity(1000);
            servoTOT.setPosition(0.83);
            servoBOT.setPosition(0.69);
            servoFOT.setPosition(0.51);
            servoHOT.setPosition(0.52);
            sleep(1300);
            motorSlideRight.setTargetPosition(0);
            motorSlideLeft.setTargetPosition(0);
            motorSlideRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorSlideLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorSlideRight.setVelocity(1000);
            motorSlideLeft.setVelocity(1000);
            sleep(2000);
        } else if (location == 2) {
            drive.followTrajectorySequence(purpleM);
            servoClamp.setPosition(0.1);
            sleep(50);

            //White & Move
            drive.followTrajectorySequence(whiteM);
//            servoHOT.setPosition(0.52);
//            motorIntake.setPower(1);
            sleep(1000);
//            motorIntake.setPower(0);
//            servoHOT.setPosition(0.67);
            drive.followTrajectorySequence(toBoardM);

            //Viper Slides Up & Set
            motorSlideRight.setTargetPosition(1000);
            motorSlideLeft.setTargetPosition(1000);
            motorSlideRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorSlideLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorSlideRight.setVelocity(1000);
            motorSlideLeft.setVelocity(1000);
            sleep(250);
            servoTOT.setPosition(0.54);
            servoBOT.setPosition(0.47);
            sleep(700);
            motorSlideRight.setTargetPosition(0);
            motorSlideLeft.setTargetPosition(0);
            motorSlideRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorSlideLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorSlideRight.setVelocity(1000);
            motorSlideLeft.setVelocity(1000);

            //Position to Board
            drive.followTrajectorySequence(posM);
            sleep(50);
            servoFOT.setPosition(0.66);
            sleep(50);

            //End
            drive.followTrajectorySequence(endM);
            motorSlideRight.setTargetPosition(1000);
            motorSlideLeft.setTargetPosition(1000);
            motorSlideRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorSlideLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorSlideRight.setVelocity(1000);
            motorSlideLeft.setVelocity(1000);
            servoTOT.setPosition(0.83);
            servoBOT.setPosition(0.69);
            servoFOT.setPosition(0.51);
            servoHOT.setPosition(0.52);
            sleep(1300);
            motorSlideRight.setTargetPosition(0);
            motorSlideLeft.setTargetPosition(0);
            motorSlideRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorSlideLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorSlideRight.setVelocity(1000);
            motorSlideLeft.setVelocity(1000);
            sleep(2000);
        } else if (location == 3) {
            //Purple
            drive.followTrajectorySequence(purpleR);
            servoClamp.setPosition(0.1);
            sleep(50);

            //White & Move
            drive.followTrajectorySequence(whiteR);
//            servoHOT.setPosition(0.52);
//            motorIntake.setPower(1);
            sleep(1000);
//            motorIntake.setPower(0);
//            servoHOT.setPosition(0.67);
            drive.followTrajectorySequence(toBoardR);

            //Viper Slides Up & Set
            motorSlideRight.setTargetPosition(1000);
            motorSlideLeft.setTargetPosition(1000);
            motorSlideRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorSlideLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorSlideRight.setVelocity(1000);
            motorSlideLeft.setVelocity(1000);
            sleep(250);
            servoTOT.setPosition(0.54);
            servoBOT.setPosition(0.47);
            sleep(700);
            motorSlideRight.setTargetPosition(0);
            motorSlideLeft.setTargetPosition(0);
            motorSlideRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorSlideLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorSlideRight.setVelocity(1000);
            motorSlideLeft.setVelocity(1000);

            //Position to Board
            drive.followTrajectorySequence(posR);
            sleep(50);
            servoFOT.setPosition(0.66);
            servoHOT.setPosition(0.52);
            sleep(50);

            //End
            drive.followTrajectorySequence(endR);
            motorSlideRight.setTargetPosition(1000);
            motorSlideLeft.setTargetPosition(1000);
            motorSlideRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorSlideLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorSlideRight.setVelocity(1000);
            motorSlideLeft.setVelocity(1000);
            servoTOT.setPosition(0.83);
            servoBOT.setPosition(0.69);
            servoFOT.setPosition(0.51);
            servoHOT.setPosition(0.52);
            sleep(1300);
            motorSlideRight.setTargetPosition(0);
            motorSlideLeft.setTargetPosition(0);
            motorSlideRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorSlideLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorSlideRight.setVelocity(1000);
            motorSlideLeft.setVelocity(1000);
            sleep(2000);
            }
        }
}
