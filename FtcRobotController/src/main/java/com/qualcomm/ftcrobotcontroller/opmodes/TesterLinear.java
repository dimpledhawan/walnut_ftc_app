package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.walnuthillseagles.walnutlibrary.DistanceDrive;
import com.walnuthillseagles.walnutlibrary.DistanceMotor;

/**
 * Created by Yan Vologzhanin on 1/23/2016.
 */
public class TesterLinear {
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
    private double turnCorrectionFactor;
    private double linearCorrectionFactor;
    
    
    private LinearOpMode helperOp;
    
    public MasterLinear(String myAlliance, AutoSections stepTest, LinearOpMode myOp){
        alliance = myAlliance.toUpperCase();
        //First is red, second is blue
        orientation = alliance.equals("RED") ? 1:-1;
        turnCorrectionFactor = alliance.equals("RED") ? 0 : 0;
        linearCorrectionFactor = alliance.equals("RED") ? 0: 0;
        startPos = myStart;
        delay = (long)(myDelay * 1000);
        willPark = parkCheck;
        helperOp = myOp;
        steps = stepTest;
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
        hardware.ziplineServo.setPosition(0.51);

        helperOp.telemetry.addData("Tests", "Hardware Init'd");
        //Setup Software
        leftDrive = new DistanceMotor(hardware.leftMotor, "Left",true,false,4,1,1440);
        rightDrive = new DistanceMotor(hardware.rightMotor, "Right",true, true, 4,1,1440);
        drive = new DistanceDrive(leftDrive,rightDrive,17);
        
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
                        safeDelay();
                        isDone = true; 
                        break;
                    case POSITION1START:
                        runPlan1();
                        isDone=true;
                        break;
                    case POSITION2START:
                        runPlan2();
                        isDone=true;
                        break;
                    case DEPLOYCLIMBERS:
                        deployClimbers();
                        isDone=true;
                        break;
                    case MOVEOUT:
                        //Extra code
                        doPark();
                        isDone = true;
                        break;
                }
                helperOp.telemetry.addData("Current Step", steps);
            }
        }
        catch(InterruptedException e){
            hardware.stop();
            Thread.currentThread().interrupt();
        }
        finally{
          hardware.stop();
        }
        helperOp.telemetry.addData("Tests","Done");
      
    }
    public void safeDelay() throws InterruptedException{
        if(delay > 0)
            helperOp.sleep(delay);
    }
    public void runPlan1() throws InterruptedException{
        //Move to position
        drive.linearDrive(35.5, 0.5);
        drive.waitForCompletion();
        drive.tankTurn((-40 * orientation)+turnCorrectionFactor, 0.5);
        drive.waitForCompletion();
        drive.linearDrive(54, 0.5);
        drive.waitForCompletion();
        drive.linearDrive(10, 0.5);
        drive.waitForCompletion();
        drive.linearDrive(-10,0.5);
        drive.waitForCompletion();
        drive.tankTurn((-42.5 * orientation)+turnCorrectionFactor, 0.5);
        drive.waitForCompletion();
        drive.linearDrive(11, 0.5);
        drive.waitForCompletion();

    }

    public void runPlan2() throws InterruptedException{

        //Move to position
        drive.forwardPivotTurn((-47 * orientation)+turnCorrectionFactor, 0.5);
        drive.waitForCompletion();
        drive.linearDrive(93.5, 0.5);
        drive.waitForCompletion();
        drive.linearDrive(10, 0.5);
        drive.waitForCompletion();
        drive.linearDrive(-10, 0.5);
        drive.waitForCompletion();
        drive.tankTurn((-35.5 * orientation)+turnCorrectionFactor, 0.5);
        drive.waitForCompletion();
        drive.linearDrive(28, 0.5);
        drive.waitForCompletion();

    }

    public void deployClimbers() throws InterruptedException{
        hardware.climberServo.setPosition(0.15);
        helperOp.sleep(1500);
        hardware.climberServo.setPosition(0.75);
        helperOp.sleep(1000);
    }
    public void doPark() throws InterruptedException{
            drive.linearDrive(-60,0.5); //60
            drive.waitForCompletion();
        
    }

}
