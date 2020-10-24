package br.unip.ads.pim.controller.home.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.unip.ads.pim.databinding.ItemUserBinding;
import br.unip.ads.pim.model.usuarios.Usuario;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private List<Usuario> usuarios;

    public UsersAdapter(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUserBinding bindind = ItemUserBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(bindind);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Usuario usuario = usuarios.get(position);
        holder.binding.tvNome.setText(usuario.nome);
        holder.binding.tvDocument.setText(usuario.documento);
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final ItemUserBinding binding;

        public ViewHolder(@NonNull ItemUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
