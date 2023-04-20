package br.com.gabriel.itstime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    DatabaseReference databaseReference;

    RecyclerView recyclerView;
    ArrayList<MedicamentsItem> medicamentsItemArrayList;
    MedicamentRecyclerAdapter adapter;

    Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).hide();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        medicamentsItemArrayList = new ArrayList<>();

        buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewDialogAdd viewDialogAdd = new ViewDialogAdd();
                viewDialogAdd.showDialog(MainActivity.this);
            }
        });

        readData();
    }

    private void readData() {

        databaseReference.child("MEDICAMENT").orderByChild("medicamentName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                medicamentsItemArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    MedicamentsItem medicament = dataSnapshot.getValue(MedicamentsItem.class);
                    medicamentsItemArrayList.add(medicament);
                }
                adapter = new MedicamentRecyclerAdapter(MainActivity.this, medicamentsItemArrayList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public class ViewDialogAdd {

        //Dialog Formulário para adicionar produto
        public void showDialog(Context context){
            final Dialog dialog = new Dialog(context);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alert_dialog_add_new_medicament);

            EditText textName = dialog.findViewById(R.id.textName);
            EditText textHours = dialog.findViewById(R.id.textHours);
            EditText textPeriod = dialog.findViewById(R.id.textPeriod);


            Button buttonAdd = dialog.findViewById(R.id.buttonAdd);
            Button buttonCancel = dialog.findViewById(R.id.buttonCancel);

            buttonAdd.setText("ADD");

            //Botão de cancelar o formulário
            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });


            //Botão de confirmação para adicionar informações a lista
            buttonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = "medicament" + new Date().getTime();
                    String name = textName.getText().toString();
                    String hours = textHours.getText().toString();
                    String period = textPeriod.getText().toString();

                    if(name.isEmpty()){
                        Toast.makeText(context, "Please Enter ALl data.....", Toast.LENGTH_SHORT).show();
                    }else {
                        databaseReference.child("MEDICAMENT").child(id).setValue(new MedicamentsItem(id, name, hours, period));
                        Toast.makeText(context, "DONE!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
            });

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
    }
}