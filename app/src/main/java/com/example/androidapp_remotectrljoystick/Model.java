package com.example.androidapp_remotectrljoystick;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Model {

    private Socket fg;
//    private thread
    private PrintWriter out;
    private float throttle;
    private float rudder;
    private float aileron;
    private float elevator;

    public void connectToFlightGear(String ip, int port) throws IOException {
        // open socket
        new Thread(() -> {
            try {
                fg = new Socket(ip, port);
                out = new PrintWriter(fg.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // send updated data to FG
    public void sendData(float v, String feature) {
        new Thread(() ->{
            out.print("set /controls/" + feature + String.valueOf(v) + "\r\n");
            out.flush();
        }).start();
    }

    public void setAileron(float aileron) {
        this.aileron = aileron;
        sendData(aileron, "flight/aileron ");
    }

    public void setRudder(float rudder) {
        this.rudder = rudder;
        sendData(rudder, "flight/rudder ");
    }

    public void setThrottle(float throttle) {
        this.throttle = throttle;
        sendData(throttle, "engines/current-engine/throttle ");
    }

    public void setElevator(float elevator) {
        this.elevator = elevator;
        sendData(elevator, "flight/elevator ");
    }
}