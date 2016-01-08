package com.qualcomm.ftcrobotcontroller.walnutLibrary;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.ftcrobotcontroller.walnutLibrary.DigSpinner;

import java.util.ArrayList;

/**
 * Created by Yan Vologzhanin on 1/4/2016.
 */
public class WalnutServo {
    private Servo servo;
    private ArrayList<ButtonEvent> buttons;

    private double startPos;


    public WalnutServo(Servo myServo, double myStartPos,
                       String daButton, double myPos, boolean sticky){
        Servo servo = myServo;
        buttons = new ArrayList<ButtonEvent>();
        ButtonEvent firstButton = new ButtonEvent(daButton,myPos,sticky);
        buttons.add(firstButton);
        startPos = myStartPos;

    }
    public void addButton(String daButton, double myPos, boolean sticky){
        ButtonEvent newButton = new ButtonEvent(daButton,myPos,sticky);
        buttons.add(newButton);
    }
    public void operate(){
        ButtonEvent temp;
        for(int i = 0;i<buttons.size();i++){
            temp = buttons.get(i);
            if(WalnutMotor.GamepadUpdater.boolValues[temp.getPos()]){
                //@TODO Change method name here to make more sense
                servo.setPosition(temp.getPow());
            }
            if(!temp.checkSticky()&&!WalnutMotor.GamepadUpdater.boolValues[temp.getPos()]){
                this.resetServo();
            }

        }
    }
    public void resetServo(){
        servo.setPosition(startPos);
    }
}
