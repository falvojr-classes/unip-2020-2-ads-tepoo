package br.unip.ads.pim.controller.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import java.util.Objects;

import br.unip.ads.pim.R;
import br.unip.ads.pim.controller.common.BaseActivity;
import br.unip.ads.pim.controller.common.BaseCallback;
import br.unip.ads.pim.controller.home.HomeActivity;
import br.unip.ads.pim.controller.usuarios.RegisterActivity;
import br.unip.ads.pim.databinding.ActivityLoginBinding;
import br.unip.ads.pim.model.usuarios.Usuario;
import br.unip.ads.pim.repository.local.LocalDataSingleton;
import br.unip.ads.pim.repository.remote.RemoteDataSingleton;
import okhttp3.Credentials;

public class LoginActivity extends BaseActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Como usamos a tag <layout> no xml, podemos obter um objeto de Binding.
        // Isso envita o uso repetitivo do método findViewById.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        //TODO Remover esse teste.
        LocalDataSingleton localData = LocalDataSingleton.get(LoginActivity.this);
        Usuario usuario = localData.db.usuarioDao().findOne();
        String token = localData.sharedPreferences.getString("token", "");
        Log.d(TAG, usuario.toString());
        Log.d(TAG, token);
    }

    public void onClickSignIn(View view) {
        // Valida o campo Email
        String email = Objects.requireNonNull(binding.etEmail.getText()).toString();
        if (TextUtils.isEmpty(email)) {
            binding.tilEmail.setError(getString(R.string.error_required_field));
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.setError(getString(R.string.error_invalid_email));
        } else {
            binding.tilEmail.setError(null);
        }

        // Valida o campo Senha
        String password = Objects.requireNonNull(binding.etPassword.getText()).toString();
        if (TextUtils.isEmpty(password)) {
            binding.tilPassword.setError(getString(R.string.error_required_field));
        } else {
            binding.tilPassword.setError(null);
        }

        // Verifica se ambos não possuem erro para fazer o Login.
        boolean isValid = binding.tilEmail.getError() == null && binding.tilPassword.getError() == null;
        if (isValid) {
            // Cria as credenciais e executa a chamada de Login.
            Usuario credenciais = new Usuario(email, password);
            RemoteDataSingleton.get().apiService.login(credenciais).enqueue(new BaseCallback<Usuario>(this) {
                @Override
                protected void onSuccess(Usuario usuario) {
                    LocalDataSingleton localData = LocalDataSingleton.get(LoginActivity.this);

                    //Armazenar o Usuário no SQLite (via Room).
                    localData.db.usuarioDao().insert(usuario);

                    //Armazenar o Token de acesso (via Shared Preferences).
                    String tokenBasicAuth = Credentials.basic(email, password);
                    localData.sharedPreferences.edit()
                            .putString("token", tokenBasicAuth)
                            .apply();

                    //Redirecina para a Home.
                    //TODO Limpar a pilha de telas.
                    Intent intencao = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intencao);
                }
            });
        }
    }

    public void onClickRegister(View view) {
        // Criar a “Intenção” de redirecionar para a Activity de Registro.
        Intent intencao = new Intent(LoginActivity.this, RegisterActivity.class);
        // Inicializar a “Intenção” em questão.
        startActivity(intencao);
    }
}