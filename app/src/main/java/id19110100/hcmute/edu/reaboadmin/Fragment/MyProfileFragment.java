package id19110100.hcmute.edu.reaboadmin.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import id19110100.hcmute.edu.reaboadmin.LoginActivity;
import id19110100.hcmute.edu.reaboadmin.Model.Library;
import id19110100.hcmute.edu.reaboadmin.R;
import id19110100.hcmute.edu.reaboadmin.RegisterActivity;

public class MyProfileFragment extends Fragment {
    private TextView fullNameTv;
    private EditText usernameEdit,passwordEdit,fullnameEdit,confirmpasswordEdit;
    private Button updateProfileBtn;

    private FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myProfile=inflater.inflate(R.layout.my_profile_fragment,container,false);
        ////////mapping
        fullNameTv=myProfile.findViewById(R.id.my_profile_full_name);
        usernameEdit=myProfile.findViewById(R.id.my_profile_email);
        passwordEdit=myProfile.findViewById(R.id.my_profile_password);
        fullnameEdit=myProfile.findViewById(R.id.my_profile_full_name_edit);
        updateProfileBtn=myProfile.findViewById(R.id.btn_update_profile);
        confirmpasswordEdit=myProfile.findViewById(R.id.my_profile_confirm_password);
        Activity context = this.getActivity();

        firebaseAuth = FirebaseAuth.getInstance();

        loadUser();
        updateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });

        return myProfile;
    }

    private void loadUser() {
        //get all libraries from firebase
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //clear arraylist before adding data into it
                String name = ""+snapshot.child("name").getValue();
                String email = ""+snapshot.child("email").getValue();

                fullNameTv.setText(name);
                usernameEdit.setText(email);
                fullnameEdit.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private String name ="", email = "", password ="";

    private void validateData() {

        //get data
        name = fullnameEdit.getText().toString().trim();
        email = usernameEdit.getText().toString().trim();
        password = passwordEdit.getText().toString().trim();

        String confirmPassword = confirmpasswordEdit.getText().toString().trim();

        //validate data
        if(TextUtils.isEmpty(name)){
            Toast.makeText(getActivity(), "Please enter your name...!", Toast.LENGTH_SHORT).show();
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(getActivity(), "Invalid email pattern...!", Toast.LENGTH_SHORT).show();
        }
        else if (!TextUtils.isEmpty(password)) {
            if (TextUtils.isEmpty(confirmPassword)) {
                Toast.makeText(getActivity(), "Please confirm password...!", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(getActivity(), "Password doesn't match...!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Processing, please wait...", Toast.LENGTH_SHORT).show();
                updatePassword(password);
            }
        }
        else {
            Toast.makeText(getActivity(), "Processing, please wait...", Toast.LENGTH_SHORT).show();
            updateUserInfor();
        }
    }

    private void updatePassword(String newPassword) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Password updated successfully...", Toast.LENGTH_SHORT).show();
                            updateUserInfor();
                        }
                    }
                });
    }

    private void updateUserInfor() {
        //time
        long timestamp = System.currentTimeMillis();

        //get user id
        String uid = firebaseAuth.getUid();

        //set up data to add in realtime db
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid", uid);
        hashMap.put("email", email);
        hashMap.put("name", name);
        hashMap.put("userType", "user");
        hashMap.put("timestamp", timestamp);


        //set data to db
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(uid)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getActivity(), "Account updated successfully...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        //fail
                        Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}