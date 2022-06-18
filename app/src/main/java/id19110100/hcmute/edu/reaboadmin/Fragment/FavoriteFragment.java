package id19110100.hcmute.edu.reaboadmin.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import id19110100.hcmute.edu.reaboadmin.Adapter.FavoriteBookAdapter;
import id19110100.hcmute.edu.reaboadmin.Model.Favorite;
import id19110100.hcmute.edu.reaboadmin.Model.Library;
import id19110100.hcmute.edu.reaboadmin.R;

public class FavoriteFragment extends Fragment {
    //firebase auth
    private FirebaseAuth firebaseAuth;

    //variables
    ArrayList<Favorite> favorites;
    FavoriteBookAdapter adapter;
    ListView lvfavorite;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        firebaseAuth = FirebaseAuth.getInstance();
        View view = inflater.inflate(R.layout.favorite_list_fragment,container,false);
        context = this.getActivity();
        lvfavorite = (ListView) view.findViewById(R.id.lv_fragment_favorite);
        loadFavorite();


        return view;

    }

    //load all favorite books to listview
    private void loadFavorite() {
        //get uid
        String uid = firebaseAuth.getUid();
        //init arraylist
        favorites = new ArrayList<>();
        //get all libraries from firebase
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Favorite");
        ref.orderByChild("uid").equalTo(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //clear arraylist before adding data into it
                favorites.clear();
                for (DataSnapshot ds:snapshot.getChildren()){
                    //get data
                    Favorite favorite = ds.getValue(Favorite.class);

                    //add to arraylist
                    favorites.add(favorite);
                }

                adapter = new FavoriteBookAdapter(context, R.layout.favorite_book_item,favorites);
                lvfavorite.setAdapter(adapter);
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}