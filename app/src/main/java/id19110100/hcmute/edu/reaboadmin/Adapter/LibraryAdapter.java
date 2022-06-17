package id19110100.hcmute.edu.reaboadmin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import id19110100.hcmute.edu.reaboadmin.Class.MyApplication;
import id19110100.hcmute.edu.reaboadmin.EbookActivity;
import id19110100.hcmute.edu.reaboadmin.Model.Library;
import id19110100.hcmute.edu.reaboadmin.PdfAddActivity;
import id19110100.hcmute.edu.reaboadmin.PdfDetailActivity;
import id19110100.hcmute.edu.reaboadmin.PdfViewActivity;
import id19110100.hcmute.edu.reaboadmin.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import id19110100.hcmute.edu.reaboadmin.Model.Book;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.LibraryViewHolder> {

    private Context context;
    private ArrayList<Library> libraries;

    //firebase auth
    private FirebaseAuth firebaseAuth;

    public LibraryAdapter(Context context ,ArrayList<Library> libraries) {
        this.context = context;
        this.libraries = libraries;
    }

    @NonNull
    @Override
    public LibraryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.library_book,parent,false);
        firebaseAuth = FirebaseAuth.getInstance();
        return new LibraryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryViewHolder holder, int position) {
        Library library = libraries.get(position);
        if (library == null){
            return;
        }
        holder.nameProduct.setText(library.getBooks().getTitle());

        MyApplication.loadFirstPage(library.getBooks().getUrl(), holder.imgProduct);
        holder.btnReadnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*uploadHistoryToDb(library, System.currentTimeMillis());
                Intent intent1 = new Intent(context, PdfViewActivity.class);
                intent1.putExtra("bookId", library.getBooks().getId());
                context.startActivity(intent1);*/

                Intent intent1 = new Intent(context, EbookActivity.class);
                //intent1.putExtra("bookId", library.getBooks().getId());
                context.startActivity(intent1);
            }
        });
    }

    private void uploadHistoryToDb(Library library, Long timestamp) {
        String uid = firebaseAuth.getUid();

        //set up data
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("uid", ""+uid);
        hashMap.put("id", ""+timestamp);
        hashMap.put("Books", library.getBooks());
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

    @Override
    public int getItemCount() {
        if(libraries !=null){
            return libraries.size();
        }
        return 0;
    }

    public class LibraryViewHolder extends RecyclerView.ViewHolder{
        private PDFView imgProduct;
        private TextView nameProduct, btnReadnow;

        public LibraryViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct=itemView.findViewById(R.id.library_book_img);
            nameProduct=itemView.findViewById(R.id.library_book_name);
            btnReadnow=itemView.findViewById(R.id.btn_library_read_now);
        }
    }


}