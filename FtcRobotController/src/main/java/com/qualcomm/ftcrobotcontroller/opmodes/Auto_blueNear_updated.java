package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by dimpledhawan on 2/13/16.
 */
public class Auto_blueNear_updated extends LinearOpMode
{
    private DcMotor motorLeft;
    private DcMotor motorRight;
    private DcMotor spinners;

    private Servo climber;

    private GyroSensor gyro; //spins when robot starts somewhere else //NEEDS TO BE SCREWED IN!!!

    public void initialize() {
        motorLeft = hardwareMap.dcMotor.get("motorLeft");
        motorRight = hardwareMap.dcMotor.get("motorRight");
        spinners = hardwareMap.dcMotor.get("spinners");
        climber = hardwareMap.servo.get("climber");
        gyro = hardwareMap.gyroSensor.get("gyro");

        gyro.calibrate();

        climber.setPosition(.75);
    }

    public void runOpMode() throws InterruptedException {
        initialize();
        EncoderMotorTask motorTaskLeft = new EncoderMotorTask(this, motorLeft);
        EncoderMotorTask motorTaskRight = new EncoderMotorTask(this, motorRight);
        GyroMotorClass gyroTurn = new GyroMotorClass(this, gyro, motorLeft, motorRight);

        while (gyro.isCalibrating()) {
            Thread.sleep(50);
        }
        waitForStart();
        int step = 0;

        if (!motorTaskLeft.isRunning()) {
            double targEnc = 1440 * 72 / 4 / Math.PI; //62.5
            motorTaskLeft.startMotor("step1 left", .5, targEnc, Direction.MOTOR_BACKWARD);
        }
        if (!motorTaskRight.isRunning()) {
            double targEnc = 1440 * 72 / 4 / Math.PI;
            motorTaskRight.startMotor("step1 right", .5, targEnc, Direction.MOTOR_FORWARD);
        }

        while(!(motorTaskLeft.targetReached() && motorTaskRight.targetReached()))
        {

        }

        motorRight.setPower(0);
        motorLeft.setPower(0);
        motorTaskLeft.stop();
        motorTaskRight.stop();


        motorTaskLeft.startMotor("Name", 0.5, 1440, Direction.MOTOR_BACKWARD);

        while(!motorTaskLeft.targetReached()){

        }

        motorLeft.setPower(0);
        motorTaskLeft.stop();

        if (!motorTaskLeft.isRunning()) {
            double targEnc = 1440 * 12 / 4 / Math.PI;
            motorTaskLeft.startMotor("step1 left", 0.5, targEnc, Direction.MOTOR_BACKWARD);
        }
        if (!motorTaskRight.isRunning()) {
            double targEnc = 1440 * 12 / 4 / Math.PI;
            motorTaskRight.startMotor("step1 right", 0.5, targEnc, Direction.MOTOR_FORWARD);
        }

        while(!(motorTaskLeft.targetReached() && motorTaskRight.targetReached()))
        {

        }

        motorRight.setPower(0);
        motorLeft.setPower(0);
        motorTaskLeft.stop();
        motorTaskRight.stop();

        climber.setPosition(.18);
        sleep(1000);
        climber.setPosition(.42);
        sleep(1000);

        if (!motorTaskLeft.isRunning()) {
            double targEnc = 1440 * 29 / 4 / Math.PI;
            motorTaskLeft.startMotor("step1 left", 1, targEnc, Direction.MOTOR_BACKWARD);
        }
        if (!motorTaskRight.isRunning()) {
            double targEnc = 1440 * 29 / 4 / Math.PI;
            motorTaskRight.startMotor("step1 right", 1, targEnc, Direction.MOTOR_FORWARD);
        }

        while(!(motorTaskLeft.targetReached() && motorTaskRight.targetReached()))
        {

        }

        motorRight.setPower(0);
        motorLeft.setPower(0);
        motorTaskLeft.stop();
        motorTaskRight.stop();

        waitOneFullHardwareCycle();
    }
}