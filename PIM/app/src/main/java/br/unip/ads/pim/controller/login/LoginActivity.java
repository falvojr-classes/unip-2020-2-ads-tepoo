package br.unip.ads.pim.controller.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import br.unip.ads.pim.R;
import br.unip.ads.pim.controller.CicloVidaActivity;
import br.unip.ads.pim.controller.home.HomeActivity;
import br.unip.ads.pim.controller.usuarios.RegisterActivity;
import br.unip.ads.pim.databinding.ActivityLoginBinding;
import br.unip.ads.pim.model.usuarios.Usuario;
import br.unip.ads.pim.service.RetrofitSingleton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            RetrofitSingleton.get().apiService.login(credenciais).enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    if (response.isSuccessful()) {
                        //TODO Armazenar o Usuário e seu respectivo Token.

                        //Redirecina para a Home.
                        //TODO Limpar a pilha de telas.
                        Intent intencao = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intencao);
                    } else {
                        //TODO Refatorar os métodos com códifo replicado em uma classe base.
                        String errorMessage = RetrofitSingleton.get().parseErrorMessage(response);
                        new MaterialAlertDialogBuilder(LoginActivity.this)
                                .setTitle(R.string.title_dialog_alert)
                                .setMessage(errorMessage)
                                .setCancelable(false)
                                .setPositiveButton(android.R.string.ok, null)
                                .show();
                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    //TODO Refatorar os métodos com códifo replicado em uma classe base.
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