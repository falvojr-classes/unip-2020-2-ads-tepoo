package br.unip.ads.pim.controller.common;

import android.content.Context;
import android.util.Log;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import br.unip.ads.pim.R;
import br.unip.ads.pim.repository.remote.RemoteDataSingleton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BaseCallback<T> implements Callback<T> {

    private final Context context;

    protected abstract void onSuccess(T body);

    public BaseCallback(Context context) {
        this.context = context;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            onSuccess(response.body());
        } else {
            String errorMessage = RemoteDataSingleton.get().parseErrorMessage(response);
            showAlert(errorMessage);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        String msg = this.context.getString(R.string.error_network);
        Log.e(this.getClass().getSimpleName(), msg, t);
        showAlert(msg);
    }

    protected void showAlert(String errorMessage) {
        new MaterialAlertDialogBuilder(this.context)
                .setTitle(R.string.title_dialog_alert)
                .setMessage(errorMessage)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }
}
