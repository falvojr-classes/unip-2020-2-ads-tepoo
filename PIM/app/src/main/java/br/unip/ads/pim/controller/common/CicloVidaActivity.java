package br.unip.ads.pim.controller.common;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

@Deprecated
public class CicloVidaActivity extends AppCompatActivity {

    private static final String TAG = CicloVidaActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate chamado!");
        super.onCreate(savedInstanceState);
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
