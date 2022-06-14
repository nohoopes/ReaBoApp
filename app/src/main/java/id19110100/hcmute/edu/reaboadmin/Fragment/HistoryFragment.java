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
import id19110100.hcmute.edu.reaboadmin.Model.Product;
import id19110100.hcmute.edu.reaboadmin.R;

public class HistoryFragment extends Fragment {
    private HistoryReadingAdapter historyOrderAdapter;
    private ArrayList<Book> historybooks;
    private RecyclerView historyRc;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View history= inflater.inflate(R.layout.history_fragment,container,false);
        historyRc=history.findViewById(R.id.history_recycler_view);
        historybooks =new ArrayList<>();

        context = this.getActivity().getApplication();

        ArrayList<Product> products1=new ArrayList<>();
        products1.add(new Product(1,"Cake1",50000 ,R.drawable.cake1,""));
        products1.add(new Product(2,"Cake2",40000,R.drawable.cake1,""));
        products1.add(new Product(3,"Cake3",60000,R.drawable.cake1,""));
        ArrayList<Product> products2=new ArrayList<>();
        products2.add(new Product(1,"Cake1",50000,R.drawable.cake1,""));
        products2.add(new Product(2,"Frozen Dessert1",40000,R.drawable.frozen_dessert1,""));
        products2.add(new Product(3,"Cake7",60000,R.drawable.cake1,""));
        ArrayList<Product> products3=new ArrayList<>();
        products3.add(new Product(1,"Frozen Dessert3",50000,R.drawable.frozen_dessert1,""));
        products3.add(new Product(2,"Cake2",40000,R.drawable.cake1,""));
        products3.add(new Product(3,"Dessert Soup1",60000,R.drawable.dessert_soup1,""));
        ArrayList<Product> products4=new ArrayList<>();
        products4.add(new Product(1,"Drink1",50000,R.drawable.drink1,""));
        products4.add(new Product(2,"Drink10",40000,R.drawable.drink1,""));
        products4.add(new Product(3,"Cake3",60000,R.drawable.cake1,""));
        ArrayList<Product> products5=new ArrayList<>();
        products5.add(new Product(1,"Custard10",50000,R.drawable.custard1,""));
        products5.add(new Product(2,"Cake5",40000,R.drawable.drink1,""));
        products5.add(new Product(3,"Custard1",60000,R.drawable.custard1,""));

        historybooks =new ArrayList<>();
        historyOrderAdapter=new HistoryReadingAdapter( historybooks);
        historyRc.setAdapter(historyOrderAdapter);
        historyRc.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        return history;
    }




}
