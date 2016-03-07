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
    private LinearOpMode helperOp;
    
    public MasterLinear(String myAlliance, int myStart,
                        double myDelay, boolean parkCheck,LinearOpMode myOp){
        alliance = myAlliance;
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

    @Override
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
                            this.sleep(delay);
                        steps = AutoSections.POSITION1START;
                    case POSITION1START:
                        drive.backwardsPivotTurn(90);
                        drive.waitForCompletion();
                        drive.backwardsPivotTurn(-90);
                        drive.waitForCompletion();
                        drive.forwardPivotTurn(90);
                        drive.waitForCompletion();
                        drive.forwardPivotTurn(-90);
                        drive.waitForCompletion();

                        steps = AutoSections.MOVEOUT;
                        break;
                    case MOVEOUT:
                        //Extra code
                        if(!willPark){
                            //@TODO Add unpark code here
                            sleep(50);
                        }
                        isDone = true;
                        break;
                }
                waitOneFullHardwareCycle();
            }
        }
        catch(InterruptedException e){
            hardware.stop();
            Thread.currentThread().interrupt();
        }
        telemetry.addData("Tests","Done");

    }
}
