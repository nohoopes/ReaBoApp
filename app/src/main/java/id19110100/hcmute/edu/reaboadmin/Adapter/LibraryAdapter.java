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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import id19110100.hcmute.edu.reaboadmin.Model.Book;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.LibraryViewHolder> {

    private Context context;
    private ArrayList<Library> libraries;

    //firebase auth
    private FirebaseAuth firebaseAuth;

    //constructor
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
        //get library by position from list
        Library library = libraries.get(position);
        if (library == null){
            return;
        }
        //set name
        holder.nameProduct.setText(library.getBooks().getTitle());
        //load first page to img view
        MyApplication.loadFirstPage(library.getBooks().getUrl(), holder.imgProduct);
        //button read now to open view activity
        holder.btnReadnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadHistoryToDb(library, System.currentTimeMillis()); //upload to history table
                Intent intent1 = new Intent(context, PdfViewActivity.class); //intent to pdfview activity
                intent1.putExtra("bookId", library.getBooks().getId()); //put book to extra
                context.startActivity(intent1);


            }
        });

        //check whether the book has audio book or not
        checkAudio(library, holder);

        //button to redirect to audio book player activity
        holder.btnListennow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(context, EbookActivity.class); //redirect to Ebook activity to listen audio books
                intent1.putExtra("bookId", library.getBooks().getId()); //put extra bookid
                context.startActivity(intent1);
            }
        });
    }

    //upload history do firebase
    private void uploadHistoryToDb(Library library, Long timestamp) {
        String uid = firebaseAuth.getUid();

        //set up data
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("uid", ""+uid);
        hashMap.put("id", ""+timestamp);
        hashMap.put("Books", library.getBooks());
        hashMap.put("timestamp", timestamp);

        //db ref to upload to firebase
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

    //View holder of adapter
    public class LibraryViewHolder extends RecyclerView.ViewHolder{
        private PDFView imgProduct;
        private TextView nameProduct, btnReadnow, btnListennow;

        public LibraryViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct=itemView.findViewById(R.id.library_book_img);
            nameProduct=itemView.findViewById(R.id.library_book_name);
            btnReadnow=itemView.findViewById(R.id.btn_library_read_now);
            btnListennow=itemView.findViewById(R.id.btn_library_listen_now);
        }
    }

    //check the book have the audio version, if not hide the button listen now
    private void checkAudio(Library library, LibraryViewHolder holder) {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Books");
        ref.child(library.getBooks().getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String audiobookurl ="" + snapshot.child("audiobookurl").getValue();
                if (audiobookurl.equals("null")) {
                    holder.btnListennow.setVisibility(View.GONE); //hide button listennow
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}