package com.qualcomm.ftcrobotcontroller.opmodes;

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

    //public GyroSensor gyro;
    public GoldConfig(OpMode currentOp){
        rightMotor = currentOp.hardwareMap.dcMotor.get("motorRight");
        leftMotor = currentOp.hardwareMap.dcMotor.get("motorLeft");
        slideLeftMotor = currentOp.hardwareMap.dcMotor.get("slideLeft");
        slideRightMotor = currentOp.hardwareMap.dcMotor.get("slideRight");

        spinMotor = currentOp.hardwareMap.dcMotor.get("spinners");

        beltServo = currentOp.hardwareMap.servo.get("belt");
        climberServo = currentOp.hardwareMap.servo.get("climber");
        hookServo = currentOp.hardwareMap.servo.get("hook");
    }
    public void stop(){
        rightMotor.setPower(0);
        leftMotor.setPower(0);
        spinMotor.setPower(0);
        slideLeftMotor.setPower(0);
        slideRightMotor.setPower(0);

        beltServo.setPosition(0.5);
    }
}
