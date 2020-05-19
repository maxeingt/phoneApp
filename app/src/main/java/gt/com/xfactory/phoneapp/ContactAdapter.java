package gt.com.xfactory.phoneapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import gt.com.xfactory.phoneapp.pojo.Persona;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private Context context;
    private List<Persona> listaPersonas;

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNombre, txtEdad, txtTelefono;
        private LinearLayout linearLayout;

        public ContactViewHolder(View view) {
            super(view);
            txtNombre =(TextView)view.findViewById(R.id.persona_nombre);
            txtTelefono =(TextView)view.findViewById(R.id.persona_telefono);
        }


    }

    public ContactAdapter(List<Persona> contactos, Context context) {
        this.listaPersonas = contactos;
        this.context = context;
    }

    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_personal, parent, false);

        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, final int position) {
        Persona p = listaPersonas.get(position);

        holder.txtNombre.setText(p.getNombre());
        holder.txtTelefono.setText(p.getTelefono());
        //holder.txtEdad.setText(p.getEdad());
    }

    @Override
    public int getItemCount() {
        return listaPersonas.size();
    }
}