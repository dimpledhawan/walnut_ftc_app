package com.qualcomm.ftcrobotcontroller.opmodes;

import android.graphics.Color;
import android.hardware.Sensor;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Walnut Robotics on 3/6/2016.
 */
public class GoldConfig {
    //Hardware
    //Drive Stuff
    public DcMotor rightMotor;
    public DcMotor leftMotor;
    //Other
    public DcMotor spinMotor;
    //@NOTE: Motor is Y Split

    public DcMotor slideLeftMotor;
    public DcMotor slideRightMotor;

    public Servo climberServo;
    public Servo beltServo;
    public Servo hookServo;
    public Servo hook2Servo;
    public Servo ziplineServo;

    public GyroSensor gyro;
    public Color color;

    public GoldConfig(OpMode currentOp){
        //Get Hardware
        rightMotor = currentOp.hardwareMap.dcMotor.get("motorRight");
        leftMotor = currentOp.hardwareMap.dcMotor.get("motorLeft");

        slideLeftMotor = currentOp.hardwareMap.dcMotor.get("slideLeft");
        slideRightMotor = currentOp.hardwareMap.dcMotor.get("slideRight");

        spinMotor = currentOp.hardwareMap.dcMotor.get("spinners");

        beltServo = currentOp.hardwareMap.servo.get("belt");
        climberServo = currentOp.hardwareMap.servo.get("climber");
        hookServo = currentOp.hardwareMap.servo.get("hook");
        hook2Servo = currentOp.hardwareMap.servo.get("hook2");
        ziplineServo = currentOp.hardwareMap.servo.get("zipline");


        gyro = currentOp.hardwareMap.gyroSensor.get("gyro");
        gyro.calibrate();
        currentOp.telemetry.addData("Tests","Made it past calibration");
        //@TODO Gets stuck in loop?
//        boolean notInterrupted = true;
//        while(gyro.isCalibrating() && notInterrupted){
//            try{
//                Thread.sleep(200);
//            }
//            catch(InterruptedException e){
//                Thread.currentThread().interrupt();
//                notInterrupted = false;
//            }
//        }
    }
    public void stop(){
        rightMotor.setPower(0);
        leftMotor.setPower(0);
        spinMotor.setPower(0);

        slideLeftMotor.setPower(0);
        slideRightMotor.setPower(0);

        beltServo.setPosition(0.5);
        ziplineServo.setPosition(0.5);
    }
}
