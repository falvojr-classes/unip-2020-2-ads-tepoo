package br.unip.ads.pim.controller.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import br.unip.ads.pim.R;
import br.unip.ads.pim.controller.common.BaseActivity;
import br.unip.ads.pim.controller.common.BaseCallback;
import br.unip.ads.pim.controller.home.adapter.UsersAdapter;
import br.unip.ads.pim.databinding.ActivityHomeAdmBinding;
import br.unip.ads.pim.model.usuarios.Usuario;

public class HomeAdmActivity extends BaseActivity {

    private ActivityHomeAdmBinding binding;
    private UsersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_adm);

        inicializar();
    }

    /**
     * @see <a href="https://developer.android.com/guide/topics/ui/layout/recyclerview">
     *     Create a List with RecyclerView
     *     </a>
     */
    private void inicializar() {
        binding.rvUsers.setHasFixedSize(true);
        binding.rvUsers.setLayoutManager(new LinearLayoutManager(this));

        getApi().buscarUsuarios(getBasicAuthentication()).enqueue(new BaseCallback<List<Usuario>>(this) {
            @Override
            protected void onSuccess(List<Usuario> usuarios) {
                adapter = new UsersAdapter(usuarios) {

                    @Override
                    public void ligar(Usuario usuario) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse(String.format("tel:%s", usuario.telefone)));
                        startActivity(intent);
                    }

                    @Override
                    public void enviarEmail(Usuario usuario) {
                        Uri uri = Uri.fromParts("mailto", usuario.email, null);
                        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject));
                        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.email_template_text, usuario.nome));
                        startActivity(Intent.createChooser(intent, getString(R.string.email_choose_title)));
                    }
                };
                binding.rvUsers.setAdapter(adapter);
            }
        });
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
}