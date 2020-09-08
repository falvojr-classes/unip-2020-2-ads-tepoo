package br.unip.ads.pim;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import br.unip.ads.pim.databinding.ActivityMainBinding;

public class MainActivity extends CicloVidaActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    public void clickSignIn(View view) {
        // TODO Validar os campos de Login
        String email = binding.etEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            binding.tilEmail.setError(getString(R.string.error_required_field));
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.setError(getString(R.string.error_invalid_email));
        } else {
            binding.tilEmail.setError(null);
        }
        // TODO Integrar com o Login via API
    }

    public void clickRegister(View view) {
        // Criar a “Intenção” de redirecionar para a Activity de Registro.
        Intent intencao = new Intent(MainActivity.this, RegisterActivity.class);
        // Inicializar a “Intenção” em questão.
        startActivity(intencao);
    }
}