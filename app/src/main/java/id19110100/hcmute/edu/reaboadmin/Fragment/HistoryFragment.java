package id19110100.hcmute.edu.reaboadmin.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import id19110100.hcmute.edu.reaboadmin.Adapter.HistoryReadingAdapter;
import id19110100.hcmute.edu.reaboadmin.Model.Book;
import id19110100.hcmute.edu.reaboadmin.Model.History;
import id19110100.hcmute.edu.reaboadmin.Model.Library;
import id19110100.hcmute.edu.reaboadmin.Model.Product;
import id19110100.hcmute.edu.reaboadmin.R;

public class HistoryFragment extends Fragment {
    private HistoryReadingAdapter historyOrderAdapter;
    private ArrayList<History> historyBooks;
    private RecyclerView historyRc;

    //firebase auth
    private FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View history= inflater.inflate(R.layout.history_fragment,container,false);
        firebaseAuth = FirebaseAuth.getInstance();

        historyRc=history.findViewById(R.id.history_recycler_view);

        loadHistory();

        return history;
    }

    private void loadHistory() {

        String uid = firebaseAuth.getUid();
        //init arraylist
        historyBooks = new ArrayList<>();
        //get all libraries from firebase
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("History");
        ref.orderByChild("uid").equalTo(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //clear arraylist before adding data into it
                historyBooks.clear();
                for (DataSnapshot ds:snapshot.getChildren()){
                    //get data
                    History history = ds.getValue(History.class);

                    //add to arraylist
                    historyBooks.add(history);
                }
                HistoryReadingAdapter adapter = new HistoryReadingAdapter(getActivity(), historyBooks);
                historyRc.setAdapter(adapter);
                historyRc.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
