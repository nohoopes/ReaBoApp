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

import java.util.ArrayList;

import id19110100.hcmute.edu.reaboadmin.Adapter.HistoryReadingAdapter;
import id19110100.hcmute.edu.reaboadmin.Model.Book;
import id19110100.hcmute.edu.reaboadmin.Model.History;
import id19110100.hcmute.edu.reaboadmin.Model.Product;
import id19110100.hcmute.edu.reaboadmin.R;

public class HistoryFragment extends Fragment {
    private HistoryReadingAdapter historyOrderAdapter;
    private ArrayList<History> historyBooks;
    private RecyclerView historyRc;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View history= inflater.inflate(R.layout.history_fragment,container,false);
        historyRc=history.findViewById(R.id.history_recycler_view);
        historyBooks =new ArrayList<>();

        context = this.getActivity().getApplication();

        historyBooks =new ArrayList<>();
        historyOrderAdapter=new HistoryReadingAdapter(historyBooks);
        historyRc.setAdapter(historyOrderAdapter);
        historyRc.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        return history;
    }

}
