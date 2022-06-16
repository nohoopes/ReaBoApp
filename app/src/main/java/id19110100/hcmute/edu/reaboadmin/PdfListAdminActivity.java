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
import id19110100.hcmute.edu.reaboadmin.Model.ModelPdf;
import id19110100.hcmute.edu.reaboadmin.databinding.ActivityPdfListAdminBinding;

public class PdfListAdminActivity extends AppCompatActivity {

    //array list to hold list of data
    private ArrayList<ModelPdf> pdfArrayList;

    //adapter
    private AdapterPdfAdmin adapterPdfAdmin;

    //view binding
    private ActivityPdfListAdminBinding binding;

    //add TAG
    private static final String TAG = "PDF_LIST_TAG";

    //variables
    private String categoryId, categoryTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPdfListAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //get data from intent
        Intent intent = getIntent();
        categoryId= intent.getStringExtra("categoryId");
        categoryTitle = intent.getStringExtra("categoryTitle");

        //set category
        binding.subtitleTv.setText(categoryTitle);

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
                    adapterPdfAdmin.getFilter().filter(charSequence);
                }
                catch (Exception e){
                    Log.d(TAG, "onTextChanged: "+e.getMessage());
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
        ref.orderByChild("categoryId").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        pdfArrayList.clear();
                        for(DataSnapshot ds: snapshot.getChildren()){
                            //get data
                            ModelPdf model = ds.getValue(ModelPdf.class);
                            //add to list
                            pdfArrayList.add(model);

                            Log.d(TAG, "onDataChange: "+model.getId()+" "+model.getTitle());
                        }
                        adapterPdfAdmin = new AdapterPdfAdmin(PdfListAdminActivity.this, pdfArrayList);
                        binding.bookRv.setAdapter(adapterPdfAdmin);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

}