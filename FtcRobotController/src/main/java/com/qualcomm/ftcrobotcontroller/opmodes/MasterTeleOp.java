package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.walnuthillseagles.walnutlibrary.*;
import com.walnuthillseagles.walnutlibrary.WalnutServo;
/**
 * Created by dimpledhawan on 2/27/16.
 */
public class MasterTeleOp extends OpMode{
    //Hardware
    private GoldConfig hardware;

    //Assignment
    private IncMotor rightDrive;
    private IncMotor leftDrive;

    private IncMotor slideLeft;
    private IncMotor slideRight;
    private DigMotor spinner;

    private ContinuousServo belt;
    private WalnutServo climber;
    private WalnutServo door;
    private WalnutServo hook;
    private WalnutServo hook2;
    private ContinuousServo zipline;
    //Control Scheme
    ControlScheme buttons;
    @Override
    public void init(){
        telemetry.addData("DEBUG", "STARTING INIT");
        //Init Hardware
        hardware=new GoldConfig(this);
        //Create Assignment
        //Drive
        rightDrive =
            new IncMotor(hardware.rightMotor,"Right Drive",false,"RIGHTY1",false,0.05);
        leftDrive =
            new IncMotor(hardware.leftMotor, "Left Drive", false, "LEFTY1", true, 0.05);
        //Spinners
        spinner = new DigMotor(hardware.spinMotor, "Spinners", false, true);
        spinner.addButton("B2", 0);
        spinner.addButton("A2", -1);
        spinner.addButton("Y2", 1);
        //Other
        slideLeft =
            new IncMotor(hardware.slideLeftMotor, "Sliders", false, "LEFTY2", true, 0.25);
        slideRight =
            new IncMotor(hardware.slideRightMotor, "Sliders", false, "LEFTY2", false, 0.25);
        //@TODO Figure out how Servos want to be used
        belt = new ContinuousServo(hardware.beltServo, "Belt",0.5,"RIGHTX2",false,0.1);

        zipline = new ContinuousServo(hardware.ziplineServo,"Zipline",0.51,"RIGHTZ1", true,0.05);
        zipline.addInput("LEFTZ1",false);

        double hook1StartPos = Servo.MIN_POSITION+.25;
        hook = new WalnutServo(hardware.hookServo,hook1StartPos,true);
        hook.addButton("LBUMP1",hook1StartPos);
        hook.addButton("RBUMP1",1);

        double hook2StartPos = Servo.MAX_POSITION-.4;
        hook2 = new WalnutServo(hardware.hook2Servo,hook2StartPos,true);
        hook2.addButton("LBUMP1",hook2StartPos);
        hook2.addButton("RBUMP1",0);

        climber = new WalnutServo(hardware.climberServo,0.7,true);
        climber.addButton("X1",0.85);
        climber.addButton("Y1",0.15);

        /*
        hook = new WalnutServo(hookServo, 0, "RBUMP1", 0, true);
        hook.addButton("LBUMP1",1,true);*/
        //Add all items to control scheme
        buttons = new ControlScheme();
        buttons.add(leftDrive);
        buttons.add(rightDrive);

        //Disabled on 03/06/16 because of stringing issues
        buttons.add(slideLeft);
        buttons.add(slideRight);
        buttons.add(spinner);

        buttons.add(belt);
        //buttons.add(door);
        buttons.add(climber);
        buttons.add(hook);
        buttons.add(hook2);
        buttons.add(zipline);
        telemetry.addData("DEBUG","FINISHED INIT");
        telemetry.addData("Zipline Data", zipline.getTablePos());
    }
    @Override
    public void start(){
        telemetry.addData("DEBUG", "MADE IT TO START");
        VirtualGamepad.startProcessing(this);
    }
    @Override
    public void loop(){
        buttons.operate();
        telemetry.addData("Zipline Left",gamepad1.left_trigger);
        telemetry.addData("Zipline Right", gamepad1.right_trigger);

    }
    @Override
    public void stop(){
    buttons.stop();
    }
}
