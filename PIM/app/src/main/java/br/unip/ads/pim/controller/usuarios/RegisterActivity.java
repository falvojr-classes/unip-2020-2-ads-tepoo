package br.unip.ads.pim.controller.usuarios;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import br.unip.ads.pim.R;
import br.unip.ads.pim.databinding.ActivityRegisterBinding;
import br.unip.ads.pim.model.usuarios.Usuario;
import br.unip.ads.pim.model.usuarios.TipoCliente;
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
                            new MaterialAlertDialogBuilder(RegisterActivity.this)
                                    .setTitle(R.string.title_dialog_alert)
                                    .setMessage(getString(R.string.msg_register_success, usuario.nome))
                                    .setCancelable(false)
                                    .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> finish())
                                    .show();
                        } else {
                            //TODO Refatorar os métodos com códifo replicado em uma classe base.
                            String errorMessage = RetrofitSingleton.get().parseErrorMessage(response);
                            new MaterialAlertDialogBuilder(RegisterActivity.this)
                                    .setTitle(R.string.title_dialog_alert)
                                    .setMessage(errorMessage)
                                    .setCancelable(false)
                                    .setPositiveButton(android.R.string.ok, null)
                                    .show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        //TODO Refatorar os métodos com códifo replicado em uma classe base.
                        String msg = getString(R.string.error_network);
                        Log.e(TAG, msg, t);
                        showSnackbar(msg);
                    }
                });
            }
        });
    }

    private void showSnackbar(String msg) {
        Snackbar.make(binding.btRegister, msg, Snackbar.LENGTH_LONG).show();
    }
}