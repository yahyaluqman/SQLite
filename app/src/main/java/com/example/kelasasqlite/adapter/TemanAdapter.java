package com.example.kelasasqlite.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kelasasqlite.LihatDataTeman;
import com.example.kelasasqlite.MainActivity;
import com.example.kelasasqlite.R;
import com.example.kelasasqlite.TemanEdit;
import com.example.kelasasqlite.database.DBController;
import com.example.kelasasqlite.database.Teman;

import java.util.ArrayList;
import java.util.HashMap;

public class TemanAdapter extends RecyclerView.Adapter<TemanAdapter.TemanViewHolder> {
    private ArrayList<Teman> listData;
    private Context context;

    public TemanAdapter(ArrayList<Teman> listData) {
        this.listData = listData;
    }

    @Override
    public TemanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInf = LayoutInflater.from(parent.getContext());
        View view = layoutInf.inflate(R.layout.row_data_teman,parent, false);
        return new TemanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TemanViewHolder holder, int position) {
        String id,nm,tlp;
        DBController db = new DBController(context);

        id = listData.get(position).getId();
        nm = listData.get(position).getNama();
        tlp = listData.get(position).getTelpon();

        holder.namaTxt.setTextColor(Color.BLUE);
        holder.namaTxt.setTextSize(20);
        holder.namaTxt.setText(nm);
        holder.telponTxt.setText(tlp);

        holder.cardku.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu pm = new PopupMenu(context, holder.cardku);
                pm.inflate(R.menu.popupmenu);
                pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.edit:
                                Intent i = new Intent(context, TemanEdit.class);
                                i.putExtra("id", id);
                                i.putExtra("nama", nm);
                                i.putExtra("telpon", tlp);
                                context.startActivity(i);
                                break;
                            case R.id.hapus:
                                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage("Apakah Anda Yakin Untuk Menghapus Data " + nm + "?");
                                builder.setCancelable(true);
                                builder.setNegativeButton("Tidak!", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                builder.setPositiveButton("Ya!", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        HashMap<String,String> qvalues =  new HashMap<>();
                                        qvalues.put("id", id);
                                        db.deleteData(qvalues);
                                        Toast.makeText(context, "Data Teman Berhasil Dihapus !", Toast.LENGTH_SHORT).show();
                                        Intent in = new Intent(context, MainActivity.class);
                                        context.startActivity(in);
                                    }
                                });
                                builder.show();
                                break;
                        }
                        return true;
                    }
                });
                pm.show();
                return true;
            }
        });

        holder.cardku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, LihatDataTeman.class);
                it.putExtra("id", id);
                it.putExtra("nama", nm);
                it.putExtra("telpon", tlp);
                context.startActivity(it);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (listData != null)?listData.size() : 0;
    }

    public class TemanViewHolder extends RecyclerView.ViewHolder {
        private CardView cardku;
        private TextView namaTxt,telponTxt;
        public TemanViewHolder(View view) {
            super(view);
            cardku = (CardView) view.findViewById(R.id.kartuku);
            namaTxt = (TextView) view.findViewById(R.id.textNama);
            telponTxt = (TextView) view.findViewById(R.id.textTelpon);
        }
    }
}
