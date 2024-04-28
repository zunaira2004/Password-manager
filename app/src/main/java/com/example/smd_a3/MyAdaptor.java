package com.example.smd_a3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdaptor extends RecyclerView.Adapter<MyAdaptor.Viewholder>{

    ArrayList<passwordModel> passwords;
    Context context;
    AppCompatActivity parentActivity;


    MyAdaptor(ArrayList<passwordModel> pass, Context c,AppCompatActivity parentActivity)
    {
        passwords=pass;
        context=c;
        this.parentActivity=parentActivity;
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_password,parent,false);
        return new Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

            holder.tvName.setText(passwords.get(position).getUsername());
            holder.tvPass.setText(passwords.get(position).getPassword());
            holder.tvUrl.setText(passwords.get(position).getUrl());

        if (!(parentActivity instanceof MainActivity)) {
            MyDatabaseHelper databaseHelper = new MyDatabaseHelper(context);
            databaseHelper.open();
            ArrayList<passwordModel> deletedData = databaseHelper.getAllDeletedData();
            databaseHelper.close();

            passwords.addAll(deletedData);

            notifyDataSetChanged();
        }
        holder.tvName.setText(passwords.get(position).getUsername());
        holder.tvPass.setText(passwords.get(position).getPassword());
        holder.tvUrl.setText(passwords.get(position).getUrl());


        if (!(parentActivity instanceof MainActivity)) {

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    AlertDialog.Builder deleteDialog = new AlertDialog.Builder(context);
                    deleteDialog.setTitle("Confirmation");
                    deleteDialog.setMessage("Do you want to delete it or restore it?");
                    deleteDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            MyDatabaseHelper database = new MyDatabaseHelper(context);
                            database.open();
                            int Delete = passwords.get(holder.getAdapterPosition()).getId();
                            database.deletePasswordDataPermanently(passwords.get(holder.getAdapterPosition()).getId());
                            database.close();

                            passwords.remove(holder.getAdapterPosition());

                            notifyItemRemoved(holder.getAdapterPosition());
                            Toast.makeText(context, "Password deleted", Toast.LENGTH_SHORT).show();
                        }
                    });
                    deleteDialog.setNegativeButton("Restore", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    return false;
                }
            });
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {

            AlertDialog.Builder deleteDialog = new AlertDialog.Builder(context);
            deleteDialog.setTitle("Confirmation");
            deleteDialog.setMessage("Do you really want to delete it?");
            deleteDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // delete code
                    Log.d("MyAdapter", "Delete button clicked");

                    MyDatabaseHelper database = new MyDatabaseHelper(context);
                    database.open();
                    database.deletePasswordData(passwords.get(holder.getAdapterPosition()).getId());
                    database.close();

                    passwords.remove(holder.getAdapterPosition());
                    Log.d("MyAdapter", "Item removed from passwords array");

                    notifyItemRemoved(holder.getAdapterPosition());
                    Log.d("MyAdapter", "Adapter notified of item removal");


                    // Notify user
                    Toast.makeText(context, "Password deleted", Toast.LENGTH_SHORT).show();
                }
            });
            deleteDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            deleteDialog.show();

            return false;
        }
        });


            holder.btnEditOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog editDialog = new AlertDialog.Builder(context).create();
                    View view = LayoutInflater.from(context).inflate(R.layout.edit_password, null, false);
                    editDialog.setView(view);

                    EditText etNameEdit = view.findViewById(R.id.etNameEdit);
                    EditText etPassEdit = view.findViewById(R.id.etPassEdit);
                    EditText etLinkEdit = view.findViewById(R.id.etLinkEdit);

                    Button btnEditData = view.findViewById(R.id.btnEditData);

                    etNameEdit.setText(passwords.get(holder.getAdapterPosition()).getUsername());
                    etPassEdit.setText(passwords.get(holder.getAdapterPosition()).getPassword());
                    etLinkEdit.setText(passwords.get(holder.getAdapterPosition()).getUrl());


                    editDialog.show();

//                    btnCancel.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            editDialog.dismiss();
//                        }
//                    });

                    btnEditData.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String name = etNameEdit.getText().toString().trim();
                            String pass = etPassEdit.getText().toString();
                            String url = etLinkEdit.getText().toString();

                            MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(context);
                            myDatabaseHelper.open();
                            myDatabaseHelper.updatePassword(passwords.get(holder.getAdapterPosition()).getId(),
                                    name, pass,url);
                            myDatabaseHelper.close();

                            editDialog.dismiss();

                            passwords.get(holder.getAdapterPosition()).setUsername(name);
                            passwords.get(holder.getAdapterPosition()).setPassword(pass);
                            passwords.get(holder.getAdapterPosition()).setUrl(url);

                            notifyDataSetChanged();

                        }
                    });
                }
            });
    }

    @Override
    public int getItemCount() {

         return passwords.size();
    }




    public static class Viewholder extends RecyclerView.ViewHolder{
        TextView tvName,tvPass,tvUrl;
        Button btnEditOption;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            tvName=itemView.findViewById(R.id.tvName);
            tvPass=itemView.findViewById(R.id.tvPass);
            tvUrl=itemView.findViewById(R.id.tvUrl);
            btnEditOption=itemView.findViewById(R.id.btnEditOption);
        }
    }
}
