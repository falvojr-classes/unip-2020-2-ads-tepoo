package br.unip.ads.pim.controller.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import java.util.List;

import br.unip.ads.pim.R;
import br.unip.ads.pim.controller.common.BaseActivity;
import br.unip.ads.pim.controller.common.BaseCallback;
import br.unip.ads.pim.controller.home.adapter.UsersAdapter;
import br.unip.ads.pim.databinding.ActivityHomeAdmBinding;
import br.unip.ads.pim.model.usuarios.Usuario;

public class HomeAdmActivity extends BaseActivity {

    private ActivityHomeAdmBinding binding;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_adm);

        criarRecyclerView();
    }

    /**
     * @see <a href="https://developer.android.com/guide/topics/ui/layout/recyclerview">
     *     Create a List with RecyclerView
     *     </a>
     */
    private void criarRecyclerView() {
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
                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_EMAIL, usuario.email);
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Contato App PIM");
                        intent.putExtra(Intent.EXTRA_TEXT, String.format("Olá %s, ", usuario.nome));
                        startActivity(Intent.createChooser(intent, "Send Email"));
                    }
                };
                binding.rvUsers.setAdapter(adapter);
            }
        });
    }
}