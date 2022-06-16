package id19110100.hcmute.edu.reaboadmin.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import id19110100.hcmute.edu.reaboadmin.Class.MyApplication;
import id19110100.hcmute.edu.reaboadmin.Model.History;
import id19110100.hcmute.edu.reaboadmin.PdfViewActivity;
import id19110100.hcmute.edu.reaboadmin.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.barteksc.pdfviewer.PDFView;

import java.util.ArrayList;

import id19110100.hcmute.edu.reaboadmin.Model.Book;
import id19110100.hcmute.edu.reaboadmin.Model.Product;

public class HistoryReadingAdapter extends  RecyclerView.Adapter<HistoryReadingAdapter.HistoryOrderViewHolder>{
    private Context context;
    private final ArrayList<History> histories;

    public HistoryReadingAdapter(Context context, ArrayList<History> histories) {
        this.context = context;
        this.histories = histories;
    }

    @NonNull
    @Override
    public HistoryOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.history_book_item,parent,false);
        return new HistoryOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryOrderViewHolder holder, @SuppressLint("RecyclerView") int position) {
        History history = histories.get(position);
        if(history ==null)
        {
            return;
        }
        holder.nameHistory.setText(history.getBooks().getTitle());
        holder.timeHistory.setText(MyApplication.formatTimestamp(history.getTimestamp()));
        holder.btnReadHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(context, PdfViewActivity.class);
                intent1.putExtra("bookId", history.getBooks().getId());
                context.startActivity(intent1);
            }
        });
        MyApplication.loadFirstPage(history.getBooks().getUrl(), holder.imgHistory);
    }

    @Override
    public int getItemCount() {
        if(histories !=null)
        {
            return histories.size();
        }
        return 0;
    }

    public class HistoryOrderViewHolder extends RecyclerView.ViewHolder{
        private final TextView nameHistory, timeHistory, btnReadHistory;
        private final PDFView imgHistory;
        public HistoryOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            nameHistory=itemView.findViewById(R.id.history_book_name);
            timeHistory=itemView.findViewById(R.id.history_book_time);
            btnReadHistory=itemView.findViewById(R.id.btn_history_read_cont);
            imgHistory=itemView.findViewById(R.id.history_book_img);
        }
    }
}
