package br.unip.ads.pim.controller;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import br.unip.ads.pim.R;
import br.unip.ads.pim.databinding.ActivityRegisterBinding;
import br.unip.ads.pim.model.Usuario;
import br.unip.ads.pim.model.TipoCliente;
import br.unip.ads.pim.service.RetrofitSingleton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);

        bindListeners();
    }

    private void bindListeners() {
        binding.btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Criar e preencher os dados do usuario para o consumo da API.
                Usuario usuario = new Usuario();
                // Recuperar/Preencher o tipo do usuario com base no MaterialRadioButton selecionado.
                MaterialRadioButton radioPersonType = findViewById(binding.rgPersonType.getCheckedRadioButtonId());
                String labelPersonType = radioPersonType.getText().toString();
                if (labelPersonType.equals(getString(R.string.label_physical_person))) {
                    usuario.tipo = TipoCliente.PF;
                } else {
                    usuario.tipo = TipoCliente.PJ;
                }
                // Preencher os demais dados (a partir dos campos TextInputEditText).
                usuario.nome = Objects.requireNonNull(binding.etName.getText()).toString();
                usuario.documento = Objects.requireNonNull(binding.etDocument.getText()).toString();
                usuario.email = Objects.requireNonNull(binding.etEmail.getText()).toString();
                usuario.senha = Objects.requireNonNull(binding.etPassword.getText()).toString();

                RetrofitSingleton.get().apiService.inserir(usuario).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            mostrarSnackbar("Sucesso total!");
                        } else {
                            mostrarSnackbar("Ocorreu um erro na API");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        String msg = "Ocorreu um erro inesperado";
                        Log.e(TAG, msg, t);
                        mostrarSnackbar(msg);
                    }
                });
            }
        });
    }

    private void mostrarToast(String msg) {
        Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_LONG).show();
    }

    private void mostrarSnackbar(String msg) {
        Snackbar.make(binding.btRegister, msg, Snackbar.LENGTH_LONG).show();
    }
}