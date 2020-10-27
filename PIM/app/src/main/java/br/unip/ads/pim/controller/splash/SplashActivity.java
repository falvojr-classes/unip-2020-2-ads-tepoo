package br.unip.ads.pim.controller.splash;

import android.os.Bundle;
import android.os.Handler;

import br.unip.ads.pim.R;
import br.unip.ads.pim.controller.common.BaseActivity;
import br.unip.ads.pim.controller.login.LoginActivity;
import br.unip.ads.pim.model.usuarios.Usuario;
import br.unip.ads.pim.repository.local.LocalDataSingleton;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // https://stackoverflow.com/a/11576546/3072570
        setTheme(R.style.NoActionBar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Usuario usuarioLogado = LocalDataSingleton.get(this).db.usuarioDao().findOne();
        new Handler().postDelayed(() -> {
            if (usuarioLogado == null) {
                redirecionar(LoginActivity.class, true);
            } else {
                redirecionarHome(usuarioLogado);
            }
        }, 2000);
    }
}