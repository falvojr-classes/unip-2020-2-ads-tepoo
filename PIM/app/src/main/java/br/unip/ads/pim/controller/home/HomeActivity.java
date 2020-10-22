package br.unip.ads.pim.controller.home;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

import br.unip.ads.pim.R;
import br.unip.ads.pim.controller.common.BaseActivity;
import br.unip.ads.pim.controller.common.BaseCallback;
import br.unip.ads.pim.controller.login.LoginActivity;
import br.unip.ads.pim.databinding.ActivityHomeBinding;
import br.unip.ads.pim.model.interesses.Interesse;
import br.unip.ads.pim.model.usuarios.Usuario;
import br.unip.ads.pim.repository.local.LocalDataSingleton;
import br.unip.ads.pim.repository.remote.ApiService;
import br.unip.ads.pim.repository.remote.RemoteDataSingleton;
import retrofit2.Call;

import static br.unip.ads.pim.repository.remote.ApiService.AUTHORIZATION;

public class HomeActivity extends BaseActivity {

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        init();

        //TODO Adicionar ícone de sair na Toolbar.
    }

    private void init() {
        LocalDataSingleton localData = LocalDataSingleton.get(this);
        // Recupera o Token de Basic Authorization para consumir a API
        String basicAuth = localData.sharedPreferences.getString(AUTHORIZATION, "");

        //TODO Recuperar os dados do usuário da API para evitar desincronização de dados.

        Usuario usuarioLogado = localData.db.usuarioDao().findOne();
        binding.etName.setText(usuarioLogado.nome);
        binding.etDocument.setText(usuarioLogado.documento);
        binding.etEmail.setText(usuarioLogado.email);
        binding.etPhone.setText(usuarioLogado.telefone);

        RemoteDataSingleton.get().apiService.buscarInteresses(basicAuth).enqueue(new BaseCallback<List<Interesse>>(this) {
            @Override
            protected void onSuccess(List<Interesse> interesses) {
                createInterests(interesses, usuarioLogado);
            }

            @Override
            public void onFailure(Call<List<Interesse>> call, Throwable t) {
                super.onFailure(call, t);
                createInterests(usuarioLogado.interesses, usuarioLogado);
            }
        });
    }

    private void createInterests(List<Interesse> interesses, Usuario usuarioLogado) {
        ChipGroup chipGroup = binding.cgInteresses;
        chipGroup.removeAllViews();
        for (Interesse interesse: interesses) {
            Chip chip = (Chip) getLayoutInflater().inflate(R.layout.layout_chip_choice, chipGroup, false);
            chip.setText(interesse.descricao);
            boolean checked = usuarioLogado.interesses.stream().anyMatch(it -> it.id.equals(interesse.id));
            chip.setChecked(checked);
            chipGroup.addView(chip);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sync:
                //TODO Sincronizar com o servidor (API).
                init();
                break;
            case R.id.action_exit:
                logoff();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logoff() {
        LocalDataSingleton localData = LocalDataSingleton.get(this);
        localData.db.usuarioDao().deleteAll();
        localData.sharedPreferences.edit().clear().apply();
        startActivity(LoginActivity.class, true);
    }
}