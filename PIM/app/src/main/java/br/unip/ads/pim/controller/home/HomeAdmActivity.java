package br.unip.ads.pim.controller.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
                adapter = new UsersAdapter(usuarios);
                binding.rvUsers.setAdapter(adapter);
            }
        });
    }
}