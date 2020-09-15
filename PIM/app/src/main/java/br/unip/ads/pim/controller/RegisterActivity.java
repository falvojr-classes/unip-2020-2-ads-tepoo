package br.unip.ads.pim.controller;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.radiobutton.MaterialRadioButton;

import java.util.Objects;

import br.unip.ads.pim.R;
import br.unip.ads.pim.databinding.ActivityRegisterBinding;
import br.unip.ads.pim.model.Cliente;
import br.unip.ads.pim.model.TipoCliente;

public class RegisterActivity extends AppCompatActivity {

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
                // Criar e preencher os dados do cliente para o consumo da API.
                Cliente cliente = new Cliente();
                // Recuperar/Preencher o tipo do cliente com base no MaterialRadioButton selecionado.
                MaterialRadioButton radioPersonType = findViewById(binding.rgPersonType.getCheckedRadioButtonId());
                String labelPersonType = radioPersonType.getText().toString();
                if (labelPersonType.equals(getString(R.string.label_physical_person))) {
                    cliente.tipo = TipoCliente.PF;
                } else {
                    cliente.tipo = TipoCliente.PJ;
                }
                // Preencher os demais dados (a partir dos campos TextInputEditText).
                cliente.nome = Objects.requireNonNull(binding.etName.getText()).toString();
                cliente.documento = Objects.requireNonNull(binding.etDocument.getText()).toString();
                cliente.email = Objects.requireNonNull(binding.etEmail.getText()).toString();
                cliente.senha = Objects.requireNonNull(binding.etPassword.getText()).toString();

                //TODO Consumir a API REST (Disciplina de POO2)
            }
        });
    }
}