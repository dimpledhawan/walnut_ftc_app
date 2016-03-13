package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Walnut Robotics on 3/13/2016.
 */
public class Auto_BlueFar_wait_stay extends LinearOpMode
{
    public void runOpMode(){
        //Prepare
        telemetry.addData("Tests", "Starting in Sample Master");
        MasterLinear sample = new MasterLinear("BLUE",2,10,true,this);
        telemetry.addData("Tests", "I'M READY FOR FREADY!");
        //Do
        try{
            waitForStart();
            sample.execute();
        }
        catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
