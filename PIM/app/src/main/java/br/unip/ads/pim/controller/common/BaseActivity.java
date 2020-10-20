package br.unip.ads.pim.controller.common;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    protected final String TAG = this.getClass().getSimpleName();

    protected void startActivity(Class<?> destino) {
        Intent intencao = new Intent(this, destino);
        startActivity(intencao);
    }
}
