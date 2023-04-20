package br.com.gabriel.itstime;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MedicamentRecyclerAdapter extends RecyclerView.Adapter<MedicamentRecyclerAdapter.ViewHolder> {

    Context context;
    ArrayList<MedicamentsItem> medicamentsItemArrayList;
    DatabaseReference databaseReference;


    public MedicamentRecyclerAdapter(Context context, ArrayList<MedicamentsItem> medicamentsItemArrayList) {
        this.context = context;
        this.medicamentsItemArrayList = medicamentsItemArrayList;
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.medicament_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MedicamentsItem medicament = medicamentsItemArrayList.get(position);

        holder.textName.setText("Name: " + medicament.getMedicamentName());
        holder.textHours.setText("Hours: " + medicament.getMedicamentHours());
        holder.textPeriod.setText("Period: " + medicament.getMedicamentPeriod());

        holder.buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ViewDialogUpdate viewDialogUpdate = new ViewDialogUpdate();
                viewDialogUpdate.showDialog(context,
                        medicament.getMedicamentID(),
                        medicament.getMedicamentName(),
                        medicament.getMedicamentHours(),
                        medicament.getMedicamentPeriod());
            }
        });

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ViewDialogConfirmDelete viewDialogConfirmDelete = new ViewDialogConfirmDelete();
                viewDialogConfirmDelete.showDialog(context, medicament.getMedicamentID());
            }
        });


    }

    @Override
    public int getItemCount() {
        return medicamentsItemArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textName;
        TextView textHours;
        TextView textPeriod;

        Button buttonDelete;
        Button buttonUpdate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textName = itemView.findViewById(R.id.textName);
            textHours = itemView.findViewById(R.id.textHours);
            textPeriod = itemView.findViewById(R.id.textPeriod);

            buttonDelete = itemView.findViewById(R.id.buttonDelete);
            buttonUpdate = itemView.findViewById(R.id.buttonUpdate);
        }
    }


    public class ViewDialogUpdate {
        public void showDialog(Context context, String id, String name, String hours, String period) {
            final Dialog dialog = new Dialog(context);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alert_dialog_add_new_medicament);

            EditText textName = dialog.findViewById(R.id.textName);
            EditText textHours = dialog.findViewById(R.id.textHours);
            EditText textPeriod = dialog.findViewById(R.id.textPeriod);


            textName.setText(name);
            textHours.setText(hours);
            textPeriod.setText(period);

            Button buttonUpdate = dialog.findViewById(R.id.buttonAdd);
            Button buttonCancel = dialog.findViewById(R.id.buttonCancel);

            buttonUpdate.setText("UPDATE");
            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            buttonUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String newName = textName.getText().toString();
                    String newHours = textHours.getText().toString();
                    String newPeriod = textPeriod.getText().toString();

                    if (name.isEmpty()) {
                        Toast.makeText(context, "Please Enter ALl data.....", Toast.LENGTH_SHORT).show();
                    } else {

                        if (newName.equals(name) && newHours.equals(hours) && newPeriod.equals(period)) {
                            Toast.makeText(context, "you don't change anything", Toast.LENGTH_SHORT).show();
                        } else {
                            databaseReference.child("MEDICAMENT").child(id).setValue(new MedicamentsItem(id, newName, newHours, newPeriod));
                            Toast.makeText(context, "Medicament Update sucessfully!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                }
            });


            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
    }

    public class ViewDialogConfirmDelete {
        public void showDialog(Context context, String id) {
            final Dialog dialog = new Dialog(context);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.view_dialog_confirm_delete);

            Button buttonDelete = dialog.findViewById(R.id.buttonDelete);
            Button buttonCancel = dialog.findViewById(R.id.buttonCancel);

            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    databaseReference.child("MEDICAMENT").child(id).removeValue();
                    Toast.makeText(context, "Medicament Deleted sucessfully!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });


            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
    }
}
