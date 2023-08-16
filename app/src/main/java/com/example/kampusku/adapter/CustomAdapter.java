package com.example.kampusku.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kampusku.DatabaseHelper.DatabaseHelper;
import com.example.kampusku.R;
import com.example.kampusku.menu.AddStudentActivity;
import com.example.kampusku.menu.DetailStudentActivity;
import com.example.kampusku.menu.StudentsActivity;
import com.example.kampusku.menu.UpdateStudentActivity;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    Activity activity;
    private Context context;
    private ArrayList nimStudent, nameStudent, birthStudent, genderStudent, addressStudent;
    Animation translate_anim;

    public CustomAdapter(Activity activity, Context context, ArrayList nimStudent, ArrayList nameStudent, ArrayList birthStudent, ArrayList genderStudent, ArrayList addressStudent) {
        this.activity = activity;
        this.context = context;
        this.nimStudent = nimStudent;
        this.nameStudent = nameStudent;
        this.birthStudent = birthStudent;
        this.genderStudent = genderStudent;
        this.addressStudent = addressStudent;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        holder.txt_nim.setText(String.valueOf(nimStudent.get(position)));
        holder.txt_nama.setText(String.valueOf(nameStudent.get(position)));
        holder.txt_tglLahir.setText(String.valueOf(birthStudent.get(position)));
        holder.txt_jenisKelamin.setText(String.valueOf(genderStudent.get(position)));
        holder.txt_alamat.setText(String.valueOf(addressStudent.get(position)));

        holder.mainLayout.setOnClickListener(v-> {
            int clickPosition = holder.getAdapterPosition();

            if (clickPosition != RecyclerView.NO_POSITION) {
                PopupMenu popupMenu = new PopupMenu(context, holder.mainLayout);
                popupMenu.getMenuInflater().inflate(R.menu.option_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(item -> {
                    if (item.getItemId() == R.id.menu_update) {
                        Intent updateIntent = new Intent(context, UpdateStudentActivity.class);
                        // Pass necessary data using intent extras
                        updateIntent.putExtra("nim", String.valueOf(nimStudent.get(clickPosition)));
                        updateIntent.putExtra("nama", String.valueOf(nameStudent.get(clickPosition)));
                        updateIntent.putExtra("tglLahir", String.valueOf(birthStudent.get(clickPosition)));
                        updateIntent.putExtra("jk", String.valueOf(genderStudent.get(clickPosition)));
                        updateIntent.putExtra("alamat", String.valueOf(addressStudent.get(clickPosition)));

                        activity.startActivityForResult(updateIntent, 1);
                    } else if (item.getItemId() == R.id.menu_detail) {
                        Intent updateIntent = new Intent(context, DetailStudentActivity.class);
                        // Pass necessary data using intent extras
                        updateIntent.putExtra("nim", String.valueOf(nimStudent.get(clickPosition)));
                        updateIntent.putExtra("nama", String.valueOf(nameStudent.get(clickPosition)));
                        updateIntent.putExtra("tglLahir", String.valueOf(birthStudent.get(clickPosition)));
                        updateIntent.putExtra("jk", String.valueOf(genderStudent.get(clickPosition)));
                        updateIntent.putExtra("alamat", String.valueOf(addressStudent.get(clickPosition)));

                        activity.startActivityForResult(updateIntent, 1);
                    } else if (item.getItemId() == R.id.menu_delete) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Hapus Data Mahasiswa ?");
                        builder.setMessage("Apakah Anda Yakin Ingin Menghapus Data " + String.valueOf(nameStudent.get(clickPosition)) + " ?");

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DatabaseHelper dbHelper = new DatabaseHelper(context);
                                dbHelper.deleteStudent(String.valueOf(nimStudent.get(clickPosition)));

                                Intent updateIntent = new Intent(context, StudentsActivity.class);
                                activity.startActivityForResult(updateIntent, 1);
                            }
                        });

                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                        builder.create().show();
                    }

                    return false;
                });

                // Set a title for the popup menu
                popupMenu.setGravity(Gravity.END); // Align the menu to the end of the anchor view
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return nimStudent.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_nim, txt_nama, txt_tglLahir, txt_jenisKelamin, txt_alamat;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_nim = itemView.findViewById(R.id.text_nimStudent);
            txt_nama = itemView.findViewById(R.id.text_nameStudent);
            txt_tglLahir = itemView.findViewById(R.id.text_tglLahirStudent);
            txt_jenisKelamin = itemView.findViewById(R.id.text_jkStudent);
            txt_alamat = itemView.findViewById(R.id.text_alamatStudent);
            mainLayout = itemView.findViewById(R.id.mainLayout);

            translate_anim = AnimationUtils.loadAnimation(context,R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }
    }
}
