package com.qualcomm.ftcrobotcontroller.opmodes;

import android.hardware.Sensor;

import com.qualcomm.hardware.hitechnic.HiTechnicNxtColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
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
public class MasterLinear {
    //Hardware
    private GoldConfig hardware;
    //Assignment
    private DistanceMotor leftDrive;
    private DistanceMotor rightDrive;
    private DistanceDrive drive;
    //Stepper
    private AutoSections steps;
    //Instance Fields
    private String alliance;
    private int startPos;
    private long delay;
    private boolean willPark;
    private int orientation;
    private LinearOpMode helperOp;
    
    public MasterLinear(String myAlliance, int myStart,
                        double myDelay, boolean parkCheck,LinearOpMode myOp){
        alliance = myAlliance.toUpperCase();
        orientation = alliance.equals("RED") ? 1:-1;
        startPos=myStart;
        delay = (long)(myDelay * 1000);
        willPark = parkCheck;
        helperOp = myOp;
        initRobot();
    }

    public void initRobot(){
        //Get Hardware
        hardware = new GoldConfig(helperOp);
        //Setup Hardware
        hardware.climberServo.setPosition(0.75);
        hardware.beltServo.setPosition(0.5);
        hardware.hook2Servo.setPosition(Servo.MAX_POSITION-.4);
        hardware.hookServo.setPosition(Servo.MIN_POSITION+.25);

        helperOp.telemetry.addData("Tests", "Hardware Init'd");
        //Setup Software
        leftDrive = new DistanceMotor(hardware.leftMotor, "Left",true,false,4,1,1440);
        rightDrive = new DistanceMotor(hardware.rightMotor, "Right",true, true, 4,1,1440);
        drive = new DistanceDrive(leftDrive,rightDrive,17);
        steps = AutoSections.POSITION1START;
        helperOp.telemetry.addData("Tests", "Software Init'd");
    }

    public void execute(){
        helperOp.telemetry.addData("Tests","Made it past Start");
        try{
            boolean isDone = false;
            ///////////////////////////////////////////////////////////////////////////////////
            while(!isDone && helperOp.opModeIsActive()){
                helperOp.telemetry.addData("Current Step",steps);
                switch(steps){
                    case STARTINGDELAY:
                        if(delay > 0)
                            helperOp.sleep(delay);
                        if(startPos == 1)
                            steps = AutoSections.POSITION1START;
                        else
                            steps = AutoSections.POSITION2START;
                    case POSITION1START:
                        //Turn on Spinners
                        hardware.spinMotor.setPower(1);

                        //Move to position
                        drive.linearDrive(36 * orientation, 0.5);
                        drive.waitForCompletion();
                        drive.tankTurn(-45 * orientation, 0.5);
                        drive.waitForCompletion();
                        drive.linearDrive(47 * orientation, 0.5);
                        drive.waitForCompletion();
                        drive.tankTurn(-45 * orientation, 0.5);
                        drive.waitForCompletion();
                        drive.linearDrive(20 * orientation, 0.5);
                        drive.waitForCompletion();

                        //Turn off Spinners
                        hardware.spinMotor.setPower(0);

                        steps = AutoSections.DEPLOYCLIMBERS;
                    case POSITION2START:
                        //Turn on Spinners
                        hardware.spinMotor.setPower(1);

                        //Move to position
                        drive.linearDrive(10, 0.5);
                        drive.waitForCompletion();
                        drive.forwardPivotTurn(-45 * orientation, 0.5);
                        drive.waitForCompletion();
                        drive.linearDrive(93, 0.5);
                        drive.waitForCompletion();
                        drive.tankTurn(-45, 0.5);
                        drive.waitForCompletion();
                        drive.linearDrive(30, 0.5);
                        drive.waitForCompletion();

                        steps = AutoSections.DEPLOYCLIMBERS;
                    case DEPLOYCLIMBERS:
                        hardware.climberServo.setPosition(.18);
                        helperOp.sleep(1000);
                        hardware.climberServo.setPosition(.47);
                        helperOp.sleep(1000);
                        steps = AutoSections.MOVEOUT;
                    case MOVEOUT:
                        //Extra code
                        if(!willPark){
                            drive.linearDrive(-60,0.5);
                            drive.waitForCompletion();
                        }
                        isDone = true;
                        break;
                }
                helperOp.waitOneFullHardwareCycle();
            }
        }
        catch(InterruptedException e){
            hardware.stop();
            Thread.currentThread().interrupt();
        }
        helperOp.telemetry.addData("Tests","Done");

    }
}
