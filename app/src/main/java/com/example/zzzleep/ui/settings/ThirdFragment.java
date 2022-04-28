package com.example.zzzleep.ui.settings;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.zzzleep.MainActivity;
import com.example.zzzleep.R;
import com.example.zzzleep.databinding.FragmentSecondBinding;
import com.example.zzzleep.databinding.FragmentThirdBinding;

import java.util.Calendar;

public class ThirdFragment extends Fragment {

    private FragmentThirdBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentThirdBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        SharedPreferences.Editor editor = prefs.edit();
        String name = prefs.getString("name", "Stranger");
        TextView nameText = view.findViewById(R.id.welcome);
        nameText.setText("Welcome, " + name + "!");

        binding.buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean mon = prefs.getBoolean("monday", false) ;
                Boolean tue = prefs.getBoolean("tuesday", false) ;
                Boolean wed = prefs.getBoolean("wednesday", false);
                Boolean thur = prefs.getBoolean("thursday", false);
                Boolean fri = prefs.getBoolean("friday", false);
                Boolean sat = prefs.getBoolean("saturday", false);
                Boolean sun = prefs.getBoolean("sunday", false);

                Context context = v.getContext();
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());

                calendar.set(Calendar.HOUR_OF_DAY, prefs.getInt("hour", 00));
                calendar.set(Calendar.MINUTE, prefs.getInt("minute", 00));
                calendar.set(Calendar.SECOND, 00);

                AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(context, AlarmReceiver.class);


                if (mon) {
                    setAlarm(2, calendar, am, intent, context);
                }
                if (tue) {
                    setAlarm(3, calendar, am, intent, context);
                }
                if (wed) {
                    setAlarm(4, calendar, am, intent, context);
                }
                if (thur) {
                    setAlarm(5, calendar, am, intent, context);
                }
                if (fri) {
                    setAlarm(6, calendar, am, intent, context);
                }
                if (sat) {
                    setAlarm(7, calendar, am, intent, context);
                }
                if (sun) {
                    setAlarm(1, calendar, am, intent, context);
                }

                editor.putBoolean("remember",  true);
                editor.apply();

                Log.d("remember after", String.valueOf(prefs.getBoolean("remember", false)));
                Intent intent2 = new Intent(binding.getRoot().getContext(), MainActivity.class);
                startActivity(intent2);
            }
        });

    }
    private void setAlarm(int day, Calendar calendar, AlarmManager am, Intent intent, Context context) {
        calendar.set(Calendar.DAY_OF_WEEK, day);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, day, intent, 0);
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}