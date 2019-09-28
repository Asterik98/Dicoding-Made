package com.example.asterik.moviecatalog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.util.Calendar;

import static java.lang.Boolean.TRUE;


public class RemainderFragment extends Fragment {
    Switch mySwitch;
    Switch mySwitch2;
    private AlarmReceiver alarmReceiver;
    public RemainderFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alarmReceiver = new AlarmReceiver();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_remainder, container, false);
        mySwitch=v.findViewById(R.id.switcher);
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==TRUE) {
                    alarmReceiver.setOneTimeAlarm(getContext(), AlarmReceiver.TYPE_ONE_TIME);
                }else{
                    alarmReceiver.cancelAlarm(getContext(), AlarmReceiver.TYPE_REPEATING);
                }
            }
        });
        mySwitch2=v.findViewById(R.id.switcher2);
        mySwitch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==TRUE) {
                    alarmReceiver.setRepeatingAlarm(getContext(), AlarmReceiver.TYPE_REPEATING, "MovieCatalog is waiting you");
                }else{
                    alarmReceiver.cancelAlarm(getContext(), AlarmReceiver.TYPE_REPEATING);
                }
            }

        });
        return v;
    }


}
