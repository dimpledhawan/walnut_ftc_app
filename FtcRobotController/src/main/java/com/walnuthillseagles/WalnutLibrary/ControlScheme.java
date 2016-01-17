package com.walnutHillsEagles.WalnutLibrary;

import java.util.ArrayList;

/**
 * Created by Yan Vologzhanin on 1/17/2016.
 */
public class ControlScheme {
    private ArrayList<Drivable> controls;

    public ControlScheme(){
        controls = new ArrayList<Drivable>();
    }
    public void addControl(Drivable obj){
        controls.add(obj);
    }
    public void operate(){
        for(int i=0;i<controls.size();i++){
            controls.get(i).operate();
        }
    }
}
