package com.example.androidapp_remotectrljoystick;

import java.io.IOException;

public class MainViewModel extends androidx.lifecycle.ViewModel {
    Model FGPlayer;

    public MainViewModel(){
    FGPlayer = new Model();
    }

    // init model
    // update IP, Port

    // "connect" pressed
    public void Connect(String ip, int port) throws IOException {
        FGPlayer.connectToFlightGear(ip, port);
    }

    // set features:
    public void setAileron(float aileron){
        FGPlayer.setAileron(aileron);
    }

    public void setRudder(float rudder){
        FGPlayer.setRudder(rudder);
    }

    public void setElevator(float elevator){
        FGPlayer.setElevator(elevator);
    }

    public void setThrottle(float throttle){
        FGPlayer.setThrottle(throttle);
    }
}
