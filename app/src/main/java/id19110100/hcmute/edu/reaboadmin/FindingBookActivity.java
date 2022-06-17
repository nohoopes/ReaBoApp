package id19110100.hcmute.edu.reaboadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import id19110100.hcmute.edu.reaboadmin.Adapter.AdapterPdfAdmin;
import id19110100.hcmute.edu.reaboadmin.Adapter.AdapterPdfUser;
import id19110100.hcmute.edu.reaboadmin.Model.ModelPdf;
import id19110100.hcmute.edu.reaboadmin.databinding.ActivityFindingBookBinding;
import id19110100.hcmute.edu.reaboadmin.databinding.ActivityPdfListAdminBinding;


public class FindingBookActivity extends AppCompatActivity {
    private ActivityFindingBookBinding binding;
    private ArrayList<ModelPdf> pdfArrayList;
    private AdapterPdfUser adapterPdfUser;
    String namebook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        binding = ActivityFindingBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //get data from intent
        //Intent intent = getIntent();
        Intent intent = getIntent();
        namebook= intent.getStringExtra("name_search");
        binding.searchEt.setText(namebook);
        Log.e("content",namebook);



        //set category


        //load pdf list
        loadPdfList();

        //search
        binding.searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //search when user types
                try{
                    adapterPdfUser.getFilter().filter(charSequence);
                }
                catch (Exception e){

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //click to back
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    private void loadPdfList() {
        //init
        pdfArrayList = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Books");
        ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        pdfArrayList.clear();
                        for(DataSnapshot ds: snapshot.getChildren()){
                            //get data
                            ModelPdf model = ds.getValue(ModelPdf.class);
                            //add to list
                            pdfArrayList.add(model);

                        }
                        adapterPdfUser = new AdapterPdfUser(FindingBookActivity.this, pdfArrayList);
                        binding.bookRv.setAdapter(adapterPdfUser);
                        adapterPdfUser.getFilter().filter(namebook);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}