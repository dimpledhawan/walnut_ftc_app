package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.walnuthillseagles.walnutlibrary.*;
import com.walnuthillseagles.walnutlibrary.WalnutServo;

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
    //Control Scheme
    ControlScheme buttons;
    public void init(){
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
            new IncMotor(hardware.slideLeftMotor, "Sliders", false, "LEFTY2", false, 0.25);
        slideRight =
            new IncMotor(hardware.slideRightMotor, "Sliders", false, "LEFTY2", true, 0.25);
        //@TODO Figure out how Servos want to be used
        belt = new ContinuousServo(hardware.beltServo, "Belt",0.5,"RIGHTX2",false,0.1);

        hook = new WalnutServo(hardware.hookServo,0,true);
        hook.addButton("LBUMP1",1);
        hook.addButton("RBUMP1",0);

        climber = new WalnutServo(hardware.climberServo,0.8,true);
        climber.addButton("X2",0.5);

        /*
        hook = new WalnutServo(hookServo, 0, "RBUMP1", 0, true);
        hook.addButton("LBUMP1",1,true);*/
        //Add all items to control scheme
        buttons = new ControlScheme();
        buttons.add(leftDrive);
        buttons.add(rightDrive);
        //Uncomment these lines when all objects made

        buttons.add(slideLeft);
        buttons.add(spinner);
        buttons.add(slideRight);

        buttons.add(belt);
        //buttons.add(door);
        buttons.add(climber);
        buttons.add(hook);
    }
    public void start(){
    VirtualGamepad.startProcessing(this);
    }
    public void loop(){
    buttons.operate();
    }
    public void stop(){
    buttons.stop();
    }
}
