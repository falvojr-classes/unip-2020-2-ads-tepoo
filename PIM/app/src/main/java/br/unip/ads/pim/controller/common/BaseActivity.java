package br.unip.ads.pim.controller.common;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    protected final String TAG = this.getClass().getSimpleName();


    protected void startActivity(Class<?> activity) {
        startActivity(activity, false);
    }

    protected void startActivity(Class<?> activity, boolean clearHistory) {
        Intent intent = new Intent(this, activity);
        if (clearHistory) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        startActivity(intent);
    }
}
