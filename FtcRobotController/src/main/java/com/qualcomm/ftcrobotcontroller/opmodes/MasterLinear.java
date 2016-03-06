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
    //Hardware
    private GoldConfig hardware;
    private DistanceMotor leftDrive;
    private DistanceMotor rightDrive;
    private DistanceDrive drive;
    //Assignment

    public void initRobot(){
        hardware = new GoldConfig(this);
        telemetry.addData("Tests", "Hardware Init'd");

        leftDrive = new DistanceMotor(hardware.leftMotor, "Left",true,false,4,1,1440);
        rightDrive = new DistanceMotor(hardware.rightMotor, "Right",true, true, 4,1,1440);
        drive = new DistanceDrive(leftDrive,rightDrive,17.5);
    }
    @Override
    public void runOpMode(){
        //Init Stage
        try{
            telemetry.addData("Tests","Init Robot");
            initRobot();
            telemetry.addData("Tests", "Robot Init'd");
            waitForStart();

            //@NOTE: This section is used for "priming" distance motor. It's kinda wierd
            //PLEASE DO NOT REMOVE IT FOR NOW
            //Thx :3
            //@TODO Is this needed, or was this my own mistake?
            telemetry.addData("Tests", "Finished First Test");
            leftDrive.operate(0);
            telemetry.addData("Tests", "Waiting for motor to prime");
            telemetry.addData("Left Mode", leftDriveMotor.getMode());
            leftDrive.waitForCompletion();

            //^^^^DONT REMOVE THIS^^^^^//

            telemetry.addData("Tests", "Starting Right Motor");
            rightDrive.operate(20);
            telemetry.addData("Tests", "Starting Left Motor");
            leftDrive.operate(20);

            leftDrive.waitForCompletion();
            rightDrive.waitForCompletion();

            rightDrive.operate(-20);
            leftDrive.operate(-20);
            leftDrive.waitForCompletion();
            rightDrive.waitForCompletion();

            drive.linearDrive(30,1);
            drive.waitForCompletion();
            drive.tankTurn(90,1);
            drive.waitForCompletion();




            while(opModeIsActive()){
                telemetry.addData("Left Power",leftDriveMotor.getPower());
                telemetry.addData("Left Target", leftDriveMotor.getTargetPosition());
                telemetry.addData("Left Pos", leftDriveMotor.getCurrentPosition());
                telemetry.addData("Left Speed", leftDrive.getSpeedLimit());
                telemetry.addData("Left Mode", leftDriveMotor.getMode());
                telemetry.addData("Left Operates",leftDrive.operateCount);

                telemetry.addData("Right Power",rightDriveMotor.getPower());
                telemetry.addData("Right Target", rightDriveMotor.getTargetPosition());
                telemetry.addData("Right Pos", rightDriveMotor.getCurrentPosition());
                telemetry.addData("Right Speed",rightDrive.getSpeedLimit());
                telemetry.addData("Right Mode", rightDriveMotor.getMode());
                telemetry.addData("Right Operates", rightDrive.operateCount);

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
            hardware.stop();
            Thread.currentThread().interrupt();
        }

    }
}
