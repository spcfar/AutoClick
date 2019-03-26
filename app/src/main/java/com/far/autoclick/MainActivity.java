package com.far.autoclick;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt_open = (Button) findViewById(R.id.bt_open);
        Button bt_start1 = (Button) findViewById(R.id.bt_start1);
        Button bt_start2 = (Button) findViewById(R.id.bt_start2);
        Button bt_end = (Button) findViewById(R.id.bt_end);

        bt_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                    startActivity(intent);
                } catch (Exception e) {
                }
            }
        });

        bt_start1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MsgAccessibilityService.class);
                intent.putExtra("bl_done", true);
                intent.putExtra("Ikind", 1);
                startService(intent);
            }
        });

        bt_start2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MsgAccessibilityService.class);
                intent.putExtra("bl_done", true);
                intent.putExtra("Ikind", 2);
                startService(intent);
            }
        });

        bt_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MsgAccessibilityService.class);
                intent.putExtra("bl_done", false);
                intent.putExtra("Ikind", 0);
                startService(intent);
            }
        });
    }
}
