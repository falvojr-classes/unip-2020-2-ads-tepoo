package br.unip.ads.pim.controller.home;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

import br.unip.ads.pim.R;
import br.unip.ads.pim.controller.common.BaseActivity;
import br.unip.ads.pim.controller.common.BaseCallback;
import br.unip.ads.pim.databinding.ActivityHomeBinding;
import br.unip.ads.pim.model.interesses.Interesse;
import br.unip.ads.pim.model.usuarios.Usuario;
import br.unip.ads.pim.repository.local.LocalDataSingleton;
import br.unip.ads.pim.repository.remote.ApiService;
import br.unip.ads.pim.repository.remote.RemoteDataSingleton;

import static br.unip.ads.pim.repository.remote.ApiService.AUTHORIZATION;

public class HomeActivity extends BaseActivity {

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        init();
    }

    private void init() {
        LocalDataSingleton localData = LocalDataSingleton.get(this);
        // Recupera o Token de Basic Authorization para consumir a API
        String basicAuth = localData.sharedPreferences.getString(AUTHORIZATION, "");
        RemoteDataSingleton.get().apiService.buscarInteresses(basicAuth).enqueue(new BaseCallback<List<Interesse>>(this) {
            @Override
            protected void onSuccess(List<Interesse> interesses) {

                ChipGroup chipGroup = binding.cgInteresses;
                for (Interesse interesse: interesses) {
                    Chip chip = (Chip) getLayoutInflater().inflate(R.layout.layout_chip_choice, chipGroup, false);
                    chip.setText(interesse.descricao);
                    chipGroup.addView(chip);
                }

                Usuario usuarioLogado = localData.db.usuarioDao().findOne();

                binding.etName.setText(usuarioLogado.nome);
                binding.etDocument.setText(usuarioLogado.documento);
                binding.etEmail.setText(usuarioLogado.email);
            }
        });
    }
}