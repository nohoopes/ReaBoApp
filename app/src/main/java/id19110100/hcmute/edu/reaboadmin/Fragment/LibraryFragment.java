package id19110100.hcmute.edu.reaboadmin.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import id19110100.hcmute.edu.reaboadmin.Adapter.LibraryAdapter;
import id19110100.hcmute.edu.reaboadmin.Model.Book;
import id19110100.hcmute.edu.reaboadmin.Model.Product;
import id19110100.hcmute.edu.reaboadmin.R;

public class LibraryFragment extends Fragment {
    ArrayList<Product> products;
    RecyclerView cartItemRc;
    TextView total_price;
    Button btn_confirm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.library,container,false);
        RecyclerView lvlibraey_A;
        LibraryAdapter adapter;

        ArrayList<Book> list= new ArrayList<>();
        list.add(new Book(1));
        list.add(new Book(1));
        list.add(new Book(1));
        list.add(new Book(1));
        list.add(new Book(1));
        list.add(new Book(1));
        list.add(new Book(1));

        RecyclerView lvlibraey_C;
        LibraryAdapter adapter1;

        lvlibraey_C = (RecyclerView) view.findViewById(R.id.C_categoty_listview);

        adapter1 = new LibraryAdapter(this.getActivity(),list);

        lvlibraey_C.setAdapter(adapter1);
        lvlibraey_C.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        adapter1.notifyDataSetChanged();

        RecyclerView lvlibrary_A;
        LibraryAdapter adapter2;
        lvlibraey_A = (RecyclerView) view.findViewById(R.id.A_categoty_listview);
        adapter1 = new LibraryAdapter(this.getActivity(),list);

        lvlibraey_A.setAdapter(adapter1);
        lvlibraey_A.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        adapter1.notifyDataSetChanged();

        return view;
    }

}