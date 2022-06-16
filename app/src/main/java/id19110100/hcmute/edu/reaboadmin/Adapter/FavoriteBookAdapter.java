package id19110100.hcmute.edu.reaboadmin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import id19110100.hcmute.edu.reaboadmin.Class.MyApplication;
import id19110100.hcmute.edu.reaboadmin.Model.Favorite;
import id19110100.hcmute.edu.reaboadmin.Model.Library;
import id19110100.hcmute.edu.reaboadmin.PdfViewActivity;
import id19110100.hcmute.edu.reaboadmin.R;

public class FavoriteBookAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Favorite> listFavorite;
    private FirebaseAuth firebaseAuth;

    public FavoriteBookAdapter(Context context, int layout, ArrayList<Favorite> ListFavorite) {
        this.context = context;
        this.layout = layout;
        this.listFavorite = ListFavorite;
    }

    @Override
    public int getCount() {
        if(listFavorite != null){
            return listFavorite.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        LinearLayout background;
        TextView name;
        TextView btn_read;
        PDFView img;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        firebaseAuth = FirebaseAuth.getInstance();
        ViewHolder holder;
        Favorite my_favorite = listFavorite.get(i);
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
        } else{
            holder = (ViewHolder) view.getTag();
        }

        holder = new ViewHolder();

        holder.background = (LinearLayout) view.findViewById(R.id.favorite_book_layout);
        holder.img = (PDFView) view.findViewById(R.id.favorite_book_img);
        MyApplication.loadPdfFromUrlSinglePageNoProgessbar(my_favorite.getBooks().getUrl(),my_favorite.getBooks().getTitle(), holder.img);
        holder.name = (TextView) view.findViewById(R.id.favorite_book_name);
        holder.name.setText(my_favorite.getBooks().getTitle());
        holder.btn_read =(TextView) view.findViewById(R.id.btn_favorite_read_now);
        holder.btn_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadHistoryToDb(my_favorite, System.currentTimeMillis());
                Intent intent1 = new Intent(context, PdfViewActivity.class);
                intent1.putExtra("bookId", my_favorite.getBooks().getId());
                context.startActivity(intent1);
            }
        });
        return view;
    }

    private void uploadHistoryToDb(Favorite favorite, Long timestamp) {
        String uid = firebaseAuth.getUid();

        //set up data
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("uid", ""+uid);
        hashMap.put("id", ""+timestamp);
        hashMap.put("Books", favorite.getBooks());
        hashMap.put("timestamp", timestamp);

        //db ref
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("History");
        ref.child(""+timestamp)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }
}
