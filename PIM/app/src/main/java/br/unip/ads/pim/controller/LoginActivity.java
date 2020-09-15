package br.unip.ads.pim.controller;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import br.unip.ads.pim.R;
import br.unip.ads.pim.databinding.ActivityLoginBinding;

public class LoginActivity extends CicloVidaActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Como usamos a tag <layout> no xml, podemos obter um objeto de Binding.
        // Isso envita o uso repetitivo do método findViewById.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
    }

    public void onClickSignIn(View view) {
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

    public void onClickRegister(View view) {
        // Criar a “Intenção” de redirecionar para a Activity de Registro.
        Intent intencao = new Intent(LoginActivity.this, RegisterActivity.class);
        // Inicializar a “Intenção” em questão.
        startActivity(intencao);
    }
}