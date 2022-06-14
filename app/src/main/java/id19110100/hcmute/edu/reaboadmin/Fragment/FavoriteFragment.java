package id19110100.hcmute.edu.reaboadmin.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import id19110100.hcmute.edu.reaboadmin.Adapter.FavoriteBookAdapter;
import id19110100.hcmute.edu.reaboadmin.R;

public class FavoriteFragment extends Fragment {



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.favorite_list_fragment,container,false);
        ListView lvfavorite;
        FavoriteBookAdapter adapter;

        lvfavorite = (ListView) view.findViewById(R.id.lv_fragment_favorite);

        adapter = new FavoriteBookAdapter(this.getActivity(), R.layout.favorite_book_item);
        lvfavorite.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return view;

    }


}