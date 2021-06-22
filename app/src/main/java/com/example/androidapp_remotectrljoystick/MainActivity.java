package com.example.androidapp_remotectrljoystick;

import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements JoystickView.JoystickListener {

    MainViewModel viewModel;

    private Button connectButton;
    private SeekBar rudderBar;
    private SeekBar throttleBar;
    private JoystickView joystick;
    private boolean isConnected=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new MainViewModel();

        joystick = new JoystickView(this);

        connectButton = findViewById(R.id.connect);
        connectButton.setOnClickListener(v -> Connect());

        rudderBar = findViewById(R.id.rudderBar);
        rudderBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float rudder = ((float) progress - 50) / 50;
                viewModel.setRudder(rudder);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        throttleBar = findViewById(R.id.throttleBar);
        throttleBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float throttle = ((float) progress)/100;
                viewModel.setThrottle(throttle);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }



    @Override
    public void onJoystickMoved(float xPercent, float yPercent, int id) {
        float elevator = (float) ((float) (yPercent-0.5)*-0.2);
        float aileron = (float) ((float) (xPercent-0.5)*0.2);
        viewModel.setElevator(elevator);
        viewModel.setAileron(aileron);
        }


    public void Connect(){
        try {
            EditText et = findViewById(R.id.ip);
            String ip = et.getText().toString();
            et = findViewById(R.id.port);
            int port = new Integer(et.getText().toString()).intValue();
            viewModel.Connect(ip, port);

            // change to "connected"
            isConnected=true;
            connectButton.setText("Connected");
            LightingColorFilter darken = new LightingColorFilter(1, 0x000000);
            connectButton.getBackground().setColorFilter(darken);
        } catch (IOException e) {
            e.printStackTrace();
            // notify failure
            connectButton.setText("Connection failed");
        }
    }



}
