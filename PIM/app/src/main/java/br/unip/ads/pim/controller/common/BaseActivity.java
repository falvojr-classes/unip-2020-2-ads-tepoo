package br.unip.ads.pim.controller.common;

import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import br.unip.ads.pim.R;
import br.unip.ads.pim.controller.home.HomeActivity;
import br.unip.ads.pim.controller.home.HomeAdmActivity;
import br.unip.ads.pim.model.usuarios.TipoUsuario;
import br.unip.ads.pim.model.usuarios.Usuario;
import br.unip.ads.pim.repository.local.AppDatabase;
import br.unip.ads.pim.repository.local.LocalDataSingleton;
import br.unip.ads.pim.repository.remote.ApiService;
import br.unip.ads.pim.repository.remote.RemoteDataSingleton;

import static br.unip.ads.pim.repository.remote.ApiService.AUTHORIZATION;

public class BaseActivity extends AppCompatActivity {

    protected final String TAG = this.getClass().getSimpleName();

    protected AppDatabase getAppDatabase() {
        return LocalDataSingleton.get(this).db;
    }

    protected SharedPreferences getSharedPrefs() {
        return LocalDataSingleton.get(this).sharedPreferences;
    }

    protected String getBasicAuthentication() {
        return getSharedPrefs().getString(AUTHORIZATION, "");
    }

    protected ApiService getApi() {
        return RemoteDataSingleton.get().apiService;
    }

    protected void redirecionar(Class<?> activity) {
        redirecionar(activity, false);
    }

    protected void redirecionar(Class<?> activity, boolean clearHistory) {
        Intent intent = new Intent(this, activity);
        if (clearHistory) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        startActivity(intent);
    }

    protected void redirecionarHome(Usuario usuario) {
        boolean ehAdm = TipoUsuario.ADM.equals(usuario.tipo);
        redirecionar(ehAdm ? HomeAdmActivity.class : HomeActivity.class, true);
    }

    protected void mostrarDialogInfo(@StringRes int info) {
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.title_dialog_info)
                .setMessage(info)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }
}
