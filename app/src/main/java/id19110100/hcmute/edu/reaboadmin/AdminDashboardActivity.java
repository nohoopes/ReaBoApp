package id19110100.hcmute.edu.reaboadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import id19110100.hcmute.edu.reaboadmin.Adapter.AdapterCategoryAd;
import id19110100.hcmute.edu.reaboadmin.Model.ModelCategoryAd;
import id19110100.hcmute.edu.reaboadmin.databinding.ActivityAdmindashboardBinding;

public class AdminDashboardActivity extends AppCompatActivity {

    //firebase auth
    private FirebaseAuth firebaseAuth;

    //view bind
    private ActivityAdmindashboardBinding binding;

    //array list to store category
    private ArrayList<ModelCategoryAd> categoryAdArrayList;

    //adapter
    private AdapterCategoryAd adapterCategoryAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdmindashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();
        loadCategories();

        //search
        binding.searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                //
                try{
                    adapterCategoryAd.getFilter().filter(s);
                }
                catch (Exception e){

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //click exit to log out
        binding.exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                checkUser();
            }
        });
        //click to go to pdf screen
        binding.addPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboardActivity.this, PdfAddActivity.class));
            }
        });

        //click to add category screen
        binding.addCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboardActivity.this, CategoryAddActivity.class));
            }
        });

    }

    private void loadCategories() {
        //init arraylist
        categoryAdArrayList = new ArrayList<>();
        //get all categories from firebase
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Categories");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //clear arraylist before adding data into it
                categoryAdArrayList.clear();
                for (DataSnapshot ds:snapshot.getChildren()){
                    //get data
                    ModelCategoryAd model = ds.getValue(ModelCategoryAd.class);

                    //add to arraylist
                    categoryAdArrayList.add(model);
                }
                adapterCategoryAd = new AdapterCategoryAd(AdminDashboardActivity.this, categoryAdArrayList);
                //set adapter to recycleview
                binding.categoriesRv.setAdapter(adapterCategoryAd);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void checkUser() {
        //get current user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser == null){
            //not logged in
            startActivity(new Intent(this, LoginActivity.class));
        }
        else{
            String email = firebaseUser.getEmail();
            //
            binding.subtitleTv.setText(email);
        }
    }
}