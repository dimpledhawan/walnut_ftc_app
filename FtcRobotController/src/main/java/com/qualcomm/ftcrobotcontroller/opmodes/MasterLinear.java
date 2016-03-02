package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.walnuthillseagles.walnutlibrary.DistanceDrive;
import com.walnuthillseagles.walnutlibrary.DistanceMotor;
import com.walnuthillseagles.walnutlibrary.LinearControlScheme;
import com.walnuthillseagles.walnutlibrary.TimedMotor;
import com.walnuthillseagles.walnutlibrary.WalnutServo;

/**
 * Created by Yan Vologzhanin on 1/23/2016.
 */
public class MasterLinear extends LinearOpMode {
    //Hardware
    private DcMotor leftDriveMotor;
    private DcMotor rightDriveMotor;
    private DistanceMotor leftDrive;
    private DistanceMotor rightDrive;
    private TimedMotor leftTimedDrive;
    //Assignment

    public void initRobot(){
        leftDriveMotor = hardwareMap.dcMotor.get("motorLeft");
        rightDriveMotor = hardwareMap.dcMotor.get("motorRight");
        telemetry.addData("Tests", "Hardware Init'd");

        leftDrive = new DistanceMotor(leftDriveMotor, "Left",true,false,4,1,1440);
        rightDrive = new DistanceMotor(rightDriveMotor, "Right",true, true, 4,1,1440);
        leftTimedDrive = new TimedMotor(leftDriveMotor,"Left",true,false);

    }
    @Override
    public void runOpMode(){
        //Init Stage
        try{
            telemetry.addData("Tests","Init Robot");
            initRobot();
            telemetry.addData("Tests", "Robot Init'd");
            waitForStart();
        }
        catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
        //Running Code
        try{
//              sleep(1000);
//              leftDriveMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
//              leftDriveMotor.setTargetPosition(4000);
//              leftDriveMotor.setPower(0.9);

//            telemetry.addData("Tests", "Starting First Test");
//            leftDrive.operate(100);
//            leftDrive.waitForCompletion();

//            leftTimedDrive.operate(5);
//            leftTimedDrive.waitForCompletion();

//            telemetry.addData("Tests", "Finished First Test");
//            sleep(2000);
//            telemetry.addData("Tests", "Starting Second Test");
            rightDrive.operate(100);
            leftDrive.operate(100);

            while(opModeIsActive()){
                telemetry.addData("Left Power",leftDriveMotor.getPower());
                telemetry.addData("Right Power",rightDriveMotor.getPower());
                telemetry.addData("Left Target", leftDriveMotor.getTargetPosition());
                telemetry.addData("Right Target", rightDriveMotor.getTargetPosition());
                telemetry.addData("Left Pos", leftDriveMotor.getCurrentPosition());
                telemetry.addData("Right Pos", rightDriveMotor.getCurrentPosition());
                waitOneFullHardwareCycle();
            }
//            telemetry.addData("Tests", "Finished Second Test");
//            sleep(2000);
//            leftDrive.operate(-100);
//            leftDrive.waitForCompletion();
//            sleep(2000);
            telemetry.addData("Tests", "Complete");
        }
        catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }

    }
}
