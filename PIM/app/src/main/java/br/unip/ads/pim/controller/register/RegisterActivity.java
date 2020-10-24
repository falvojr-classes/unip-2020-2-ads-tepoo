package br.unip.ads.pim.controller.register;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.radiobutton.MaterialRadioButton;

import java.util.Objects;

import br.unip.ads.pim.R;
import br.unip.ads.pim.controller.common.BaseActivity;
import br.unip.ads.pim.controller.common.BaseCallback;
import br.unip.ads.pim.databinding.ActivityRegisterBinding;
import br.unip.ads.pim.model.usuarios.TipoUsuario;
import br.unip.ads.pim.model.usuarios.Usuario;
import br.unip.ads.pim.repository.remote.RemoteDataSingleton;

public class RegisterActivity extends BaseActivity {

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
                    usuario.tipo = TipoUsuario.PF;
                } else {
                    usuario.tipo = TipoUsuario.PJ;
                }
                // Preencher os demais dados (a partir dos campos TextInputEditText).
                usuario.nome = Objects.requireNonNull(binding.etName.getText()).toString();
                usuario.documento = Objects.requireNonNull(binding.etDocument.getText()).toString();
                usuario.email = Objects.requireNonNull(binding.etEmail.getText()).toString();
                usuario.senha = Objects.requireNonNull(binding.etPassword.getText()).toString();

                //TODO Implementar a validação dos dados, assim como fizemos no Login.
                RemoteDataSingleton.get().apiService.inserirUsuario(usuario).enqueue(new BaseCallback<Usuario>(RegisterActivity.this) {

                    @Override
                    protected void onSuccess(Usuario usuario) {
                        new MaterialAlertDialogBuilder(RegisterActivity.this)
                                .setTitle(R.string.title_dialog_alert)
                                .setMessage(getString(R.string.msg_register_success, usuario.nome))
                                .setCancelable(false)
                                .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> finish())
                                .show();
                    }
                });
            }
        });
    }
}