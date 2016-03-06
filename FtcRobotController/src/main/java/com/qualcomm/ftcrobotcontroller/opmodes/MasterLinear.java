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
    private GoldConfig hardware;
    //Assignment
    private DistanceMotor leftDrive;
    private DistanceMotor rightDrive;
    private DistanceDrive drive;
    //Stepper
    private AutoSections steps;

    public void initRobot(){
        hardware = new GoldConfig(this);
        telemetry.addData("Tests", "Hardware Init'd");

        leftDrive = new DistanceMotor(hardware.leftMotor, "Left",true,false,4,1,1440);
        rightDrive = new DistanceMotor(hardware.rightMotor, "Right",true, true, 4,1,1440);
        drive = new DistanceDrive(leftDrive,rightDrive,17.5);
        steps = AutoSections.POSITION1START;
    }
    @Override
    public void runOpMode(){
        //Init Stage
        try{
            telemetry.addData("Tests","Init Robot");
            initRobot();
            telemetry.addData("Tests", "Robot Init'd");
            waitForStart();
            boolean isDone = false;
            while(!isDone && opModeIsActive()){
                switch(steps){
                    case POSITION1START:
                        //@NOTE: This section is used for "priming" distance motor. It's kinda wierd
                        //PLEASE DO NOT REMOVE IT FOR NOW
                        //Thx :3
                        //@TODO Is this needed, or was this my own mistake?
                        telemetry.addData("Tests", "Finished First Test");
                        leftDrive.operate(0);
                        telemetry.addData("Tests", "Waiting for motor to prime");
                        telemetry.addData("Left Mode", hardware.leftMotor.getMode());
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
                        break;
                    case MOVEOUT:
                        isDone = true;
                        break;
                }
            }
        }
        catch(InterruptedException e){
            hardware.stop();
            Thread.currentThread().interrupt();
        }

    }
}
