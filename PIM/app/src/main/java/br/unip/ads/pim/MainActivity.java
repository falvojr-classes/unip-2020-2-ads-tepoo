package br.unip.ads.pim;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate chamado!");
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart chamado!");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume chamado!");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause chamado!");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop chamado!");
        super.onStop();
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart chamado!");
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy chamado!");
        super.onDestroy();
    }
}