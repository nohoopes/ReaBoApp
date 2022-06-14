package id19110100.hcmute.edu.reaboadmin.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import id19110100.hcmute.edu.reaboadmin.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id19110100.hcmute.edu.reaboadmin.Model.Book;
import id19110100.hcmute.edu.reaboadmin.Model.Product;

public class HistoryReadingAdapter extends  RecyclerView.Adapter<HistoryReadingAdapter.HistoryOrderViewHolder>{
    private final ArrayList<Book> history;
    private ArrayList<Product> products;
    private double totalOrderPrice;


    public HistoryReadingAdapter(ArrayList<Book> orders) {
        this.history = orders;
    }

    @NonNull
    @Override
    public HistoryOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.history_book_item,parent,false);
        return  new HistoryOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryOrderViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Book order = history.get(position);
        if(order ==null)
        {
            return;
        }


    }

    @Override
    public int getItemCount() {
        if(history !=null)
        {
            return history.size();
        }
        return 0;
    }

    public class HistoryOrderViewHolder extends RecyclerView.ViewHolder{
        private final TextView nameHistory, timeHistory, btnReadHistory;
        private final ImageView imgHistory;
        public HistoryOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            nameHistory=itemView.findViewById(R.id.history_book_name);
            timeHistory=itemView.findViewById(R.id.history_book_time);
            btnReadHistory=itemView.findViewById(R.id.btn_history_read_cont);
            imgHistory=itemView.findViewById(R.id.history_book_img);
        }
    }
}
