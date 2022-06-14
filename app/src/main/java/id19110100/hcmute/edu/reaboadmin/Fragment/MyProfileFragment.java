package id19110100.hcmute.edu.reaboadmin.Fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import id19110100.hcmute.edu.reaboadmin.R;

public class MyProfileFragment extends Fragment {
    private TextView fullNameTv;
    private EditText usernameEdit,passwordEdit,birthdateEdit,addressEdit,phoneEdit;
    private Button updateProfileBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myProfile=inflater.inflate(R.layout.my_profile_fragment,container,false);
        ////////mapping
        fullNameTv=myProfile.findViewById(R.id.my_profile_full_name);
        usernameEdit=myProfile.findViewById(R.id.my_profile_username);
        passwordEdit=myProfile.findViewById(R.id.my_profile_password);
        birthdateEdit=myProfile.findViewById(R.id.my_profile_birthdate);
        addressEdit=myProfile.findViewById(R.id.my_profile_address);
        phoneEdit=myProfile.findViewById(R.id.my_profile_phone);
        updateProfileBtn=myProfile.findViewById(R.id.btn_update_profile);
        Activity context = this.getActivity();



        return myProfile;
    }
}