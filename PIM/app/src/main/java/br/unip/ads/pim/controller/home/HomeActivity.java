package br.unip.ads.pim.controller.home;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

import br.unip.ads.pim.R;
import br.unip.ads.pim.controller.common.BaseActivity;
import br.unip.ads.pim.controller.common.BaseCallback;
import br.unip.ads.pim.controller.login.LoginActivity;
import br.unip.ads.pim.databinding.ActivityHomeBinding;
import br.unip.ads.pim.model.interesses.Interesse;
import br.unip.ads.pim.model.usuarios.TipoUsuario;
import br.unip.ads.pim.model.usuarios.Usuario;
import br.unip.ads.pim.repository.remote.RemoteDataSingleton;
import retrofit2.Call;

public class HomeActivity extends BaseActivity {

    private ActivityHomeBinding binding;

    private Usuario usuarioLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        inicializar();
        adicionarEventos();
    }

    private void adicionarEventos() {
        binding.btSave.setOnClickListener(view -> {
            // Atribui os valores preenchidos pelo usuário para atualização.
            Usuario usuarioAlterado = new Usuario();
            usuarioAlterado.id = usuarioLocal.id;
            usuarioAlterado.nome = binding.etName.getText().toString();
            usuarioAlterado.documento = binding.etDocument.getText().toString();
            usuarioAlterado.telefone = binding.etPhone.getText().toString();
            // Realiza um tratamento especifico para os inputs do tipo 'radio':
            usuarioAlterado.tipo = binding.rbLegalPerson.isChecked() ? TipoUsuario.PJ : TipoUsuario.PF;
            // Identifica os interesses selecionados e os relaciona com o usuário a ser alterado
            usuarioAlterado.interesses = new ArrayList<>();
            for (Integer checkedChipId : binding.cgInteresses.getCheckedChipIds()) {
                Long idInteresse = (Long) findViewById(checkedChipId).getTag();
                usuarioAlterado.interesses.add(new Interesse(idInteresse));
            }
            //TODO Implementar a validação dos dados, assim como fizemos no Login.
            getApi().alterarUsuario(getBasicAuthentication(), usuarioAlterado.id, usuarioAlterado).enqueue(new BaseCallback<Usuario>(this) {
                @Override
                protected void onSuccess(Usuario usuarioRemoto) {
                    atualizarUsuarioLocal(usuarioRemoto);
                    mostrarDialogInfo(R.string.msg_update_success);
                }
            });
        });
    }

    private void inicializar() {
        usuarioLocal = super.getAppDatabase().usuarioDao().findOne();
        buscarUsuarioRemotamente();
    }

    private void buscarUsuarioRemotamente() {
        String basicAuth = super.getBasicAuthentication();
        RemoteDataSingleton.get().apiService.buscarUsuario(basicAuth, usuarioLocal.id).enqueue(new BaseCallback<Usuario>(this) {
            @Override
            protected void onSuccess(Usuario usuarioRemoto) {
                atualizarUsuarioLocal(usuarioRemoto);
                // Busca os interesses para criar os campos de Chips dinamicamente.
                buscarInteressesRemotamente();
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                super.onFailure(call, t);
                preencherUsuario(true);
                preencherInteressesDinamicamente(usuarioLocal.interesses, true);
            }
        });
    }

    private void buscarInteressesRemotamente() {
        String basicAuth = super.getBasicAuthentication();
        RemoteDataSingleton.get().apiService.buscarInteresses(basicAuth).enqueue(new BaseCallback<List<Interesse>>(this) {
            @Override
            protected void onSuccess(List<Interesse> interessesRemotos) {
                preencherUsuario(false);
                preencherInteressesDinamicamente(interessesRemotos, false);
            }

            @Override
            public void onFailure(Call<List<Interesse>> call, Throwable t) {
                super.onFailure(call, t);
                preencherUsuario(true);
                preencherInteressesDinamicamente(usuarioLocal.interesses, true);
            }
        });
    }

    private void preencherUsuario(boolean offline) {
        binding.tilName.setEnabled(!offline);
        binding.tilDocument.setEnabled(!offline);
        binding.tilPhone.setEnabled(!offline);
        binding.rbPhysicalPerson.setEnabled(!offline);
        binding.rbLegalPerson.setEnabled(!offline);
        binding.btSave.setEnabled(!offline);

        binding.etName.setText(usuarioLocal.nome);
        binding.etDocument.setText(usuarioLocal.documento);
        binding.etEmail.setText(usuarioLocal.email);
        binding.etPhone.setText(usuarioLocal.telefone);
        binding.rbPhysicalPerson.setChecked(TipoUsuario.PF.equals(usuarioLocal.tipo));
        binding.rbLegalPerson.setChecked(TipoUsuario.PJ.equals(usuarioLocal.tipo));
    }

    private void preencherInteressesDinamicamente(List<Interesse> interesses, boolean offline) {
        ChipGroup chipGroup = binding.cgInteresses;
        chipGroup.removeAllViews();
        for (Interesse interesse: interesses) {
            Chip chip = (Chip) getLayoutInflater().inflate(R.layout.layout_chip_choice, chipGroup, false);
            chip.setText(interesse.descricao);
            boolean checked = usuarioLocal.interesses.stream().anyMatch(it -> it.id.equals(interesse.id));
            chip.setChecked(checked);
            chip.setEnabled(!offline);
            chip.setTag(interesse.id);
            chipGroup.addView(chip);
        }
    }

    private void atualizarUsuarioLocal(Usuario usuarioRemoto) {
        // Atualiza os dados do Usuario localmente.
        getAppDatabase().usuarioDao().update(usuarioRemoto);
        // Atualiza a instância do usuarioLocal.
        usuarioLocal = getAppDatabase().usuarioDao().findOne();
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
                inicializar();
                break;
            case R.id.action_exit:
                logoff();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logoff() {
        super.getAppDatabase().usuarioDao().deleteAll();
        super.getSharedPrefs().edit().clear().apply();
        redirecionar(LoginActivity.class, true);
    }
}