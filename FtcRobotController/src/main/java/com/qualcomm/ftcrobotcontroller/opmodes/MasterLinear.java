package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.walnuthillseagles.walnutlibrary.DistanceDrive;
import com.walnuthillseagles.walnutlibrary.DistanceMotor;

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
    private double turnCorrectionFactor;
    private double linearCorrectionFactor;
    private double linearCorrectionFactor2;
    
    
    private LinearOpMode helperOp;
    
    public MasterLinear(String myAlliance, int myStart,
                        double myDelay, boolean parkCheck, LinearOpMode myOp){
        alliance = myAlliance.toUpperCase();
        //First is red, second is blue
        orientation = alliance.equals("RED") ? 1:-1;
        //Due to a leftward drift caused in motors, these have been placed temporarly for comp
        turnCorrectionFactor = alliance.equals("RED") ? 0 : -2.25;
        linearCorrectionFactor = alliance.equals("RED") ? 0: -8;
        linearCorrectionFactor2 = alliance.equals("RED") ? 0:5;
        //Instance fields
        startPos = myStart;
        delay = (long)(myDelay * 1000);
        willPark = parkCheck;
        //Used for telemtery
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
        hardware.ziplineServo.setPosition(0.51);

        helperOp.telemetry.addData("Tests", "Hardware Init'd");
        //Setup Software
        leftDrive = new DistanceMotor(hardware.leftMotor, "Left",true,false,4,1,1440);
        rightDrive = new DistanceMotor(hardware.rightMotor, "Right",true, true, 4,1,1440);
        drive = new DistanceDrive(leftDrive,rightDrive,17);
        steps = AutoSections.STARTINGDELAY;
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
                        steps = (startPos == 1) ?
                                AutoSections.POSITION1START:AutoSections.POSITION2START;
                        break;
                    case POSITION1START:
                        runPlan1();
                        steps = AutoSections.DEPLOYCLIMBERS;
                        break;
                    case POSITION2START:
                        runPlan2();
                        steps = AutoSections.DEPLOYCLIMBERS;
                        break;
                    case DEPLOYCLIMBERS:
                        deployClimbers();
                        helperOp.waitOneFullHardwareCycle();
                        steps = AutoSections.MOVEOUT;
                        break;
                    case MOVEOUT:
                        //Extra code
                        doPark();
                        isDone = true;
                        break;
                }
                helperOp.waitOneFullHardwareCycle();
                helperOp.telemetry.addData("Current Step", steps);
            }
        }
        catch(InterruptedException e){
            hardware.stop();
            Thread.currentThread().interrupt();
        }
        helperOp.telemetry.addData("Tests","Done");

    }
    public void safeDelay() throws InterruptedException{
        if(delay > 0)
            helperOp.sleep(delay);
    }
    public void runPlan1() throws InterruptedException{
        //Turn on Spinners
        hardware.spinMotor.setPower(1);

        //Move to position
        drive.linearDrive(35.5, 0.5);
        drive.waitForCompletion();
        drive.tankTurn(((-37+turnCorrectionFactor) * orientation), 0.5);
        drive.waitForCompletion();
        drive.linearDrive(54, 0.5);
        drive.waitForCompletion();
        drive.linearDrive(10, 0.5);
        drive.waitForCompletion();
        drive.linearDrive(-10,0.5);
        drive.waitForCompletion();
        drive.tankTurn(((-41.5+turnCorrectionFactor) * orientation), 0.5);
        drive.waitForCompletion();
        drive.linearDrive(14, 0.5);
        drive.waitForCompletion();

        //Turn off Spinners
        hardware.spinMotor.setPower(0);
    }

    public void runPlan2() throws InterruptedException{
        //Turn on Spinners
        hardware.spinMotor.setPower(1);

        //Move to position
        drive.forwardPivotTurn((-43 * orientation), 0.5);
        drive.waitForCompletion();
        drive.linearDrive(104.5 + linearCorrectionFactor, 0.5);
        drive.waitForCompletion();
        drive.linearDrive(15, 0.5);
        drive.waitForCompletion();
        drive.linearDrive(-15, 0.5);
        drive.waitForCompletion();
        drive.tankTurn(((-35.5+turnCorrectionFactor) * orientation), 0.5);
        drive.waitForCompletion();
        drive.linearDrive(19.5+linearCorrectionFactor2, 0.5);
        drive.waitForCompletion();

        //Turn off Spinners
        hardware.spinMotor.setPower(0);
    }

    public void deployClimbers() throws InterruptedException{
        hardware.climberServo.setPosition(0.15);
        helperOp.sleep(1500);
        hardware.climberServo.setPosition(0.75);
        helperOp.sleep(1000);
    }
    public void doPark() throws InterruptedException{
        if(!willPark){
            drive.linearDrive(-60,0.5); //60
            drive.waitForCompletion();
        }
    }

}
