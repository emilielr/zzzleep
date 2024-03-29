package com.example.zzzleep.ui.settings;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.zzzleep.R;
import com.example.zzzleep.databinding.FragmentSettingsBinding;

import java.util.Calendar;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(root.getContext());
        SharedPreferences.Editor editor = prefs.edit();

        Button save = root.findViewById(R.id.button);

        CheckBox mon = root.findViewById(R.id.checkbox_monday);
        CheckBox tue = root.findViewById(R.id.checkbox_tuesday);
        CheckBox wed = root.findViewById(R.id.checkbox_wednesday);
        CheckBox thur = root.findViewById(R.id.checkbox_thursday);
        CheckBox fri = root.findViewById(R.id.checkbox_friday);
        CheckBox sat = root.findViewById(R.id.checkbox_saturday);
        CheckBox sun = root.findViewById(R.id.checkbox_sunday);


        mon.setChecked(prefs.getBoolean("monday", false));
        tue.setChecked(prefs.getBoolean("tuesday", false));
        wed.setChecked(prefs.getBoolean("wednesday", false));
        thur.setChecked(prefs.getBoolean("thursday", false));
        fri.setChecked(prefs.getBoolean("friday", false));
        sat.setChecked(prefs.getBoolean("saturday", false));
        sun.setChecked(prefs.getBoolean("sunday", false));


        EditText editName = root.findViewById(R.id.name);
        editName.setText(prefs.getString("name", ""));

        TimePicker time = root.findViewById(R.id.bedtime_subject);

        time.setHour(prefs.getInt("hour", 00));
        time.setMinute(prefs.getInt("minute", 00));






        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                editor.putString("name", editName.getText().toString());

                editor.putBoolean("monday", mon.isChecked());
                editor.putBoolean("tuesday", tue.isChecked());
                editor.putBoolean("wednesday", wed.isChecked());
                editor.putBoolean("thursday", thur.isChecked());
                editor.putBoolean("friday", fri.isChecked());
                editor.putBoolean("saturday", sat.isChecked());
                editor.putBoolean("sunday", sun.isChecked());
                editor.putInt("minute",time.getMinute());
                editor.putInt("hour",time.getHour());
                editor.apply();


                Context context = v.getContext();
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());

                calendar.set(Calendar.HOUR_OF_DAY, prefs.getInt("hour", 00));
                calendar.set(Calendar.MINUTE, prefs.getInt("minute", 00));
                calendar.set(Calendar.SECOND, 00);

                AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(context, AlarmReceiver.class);


                    if (mon.isChecked()) {
                        setAlarm(2, calendar, am, intent, context);
                    }else{
                        cancelAlarm(2, am, intent, context);
                    }
                    if (tue.isChecked()) {
                        setAlarm(3, calendar, am, intent, context);
                    }else{
                        cancelAlarm(3, am, intent, context);
                    }
                    if (wed.isChecked()) {
                        setAlarm(4, calendar, am, intent, context);
                    }else{
                        cancelAlarm(4, am, intent, context);
                    }
                    if (thur.isChecked()) {
                        setAlarm(5, calendar, am, intent, context);
                    }else{
                        cancelAlarm(5, am, intent, context);
                    }
                    if (fri.isChecked()) {
                        setAlarm(6, calendar, am, intent, context);
                    }else{
                        cancelAlarm(6, am, intent, context);
                    }
                    if (sat.isChecked()) {
                        setAlarm(7, calendar, am, intent, context);
                    }else{
                        cancelAlarm(7, am, intent, context);
                    }
                    if (sun.isChecked()) {
                        setAlarm(1, calendar, am, intent, context);
                    }else{
                        cancelAlarm(1, am, intent, context);
                    }

            }
        });

        return root;
    }

    private void setAlarm(int day, Calendar calendar, AlarmManager am, Intent intent, Context context) {
        calendar.set(Calendar.DAY_OF_WEEK, day);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, day, intent, 0);
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
    private void cancelAlarm(int day, AlarmManager am, Intent intent, Context context) {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, day, intent, PendingIntent.FLAG_NO_CREATE);
        if (pendingIntent != null && am != null) {
            am.cancel(pendingIntent);
        }
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
