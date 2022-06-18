package id19110100.hcmute.edu.reaboadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import id19110100.hcmute.edu.reaboadmin.Class.MyApplication;
import id19110100.hcmute.edu.reaboadmin.databinding.ActivityPdfDetailBinding;

public class PdfDetailActivity extends AppCompatActivity {

    //view binding
    private ActivityPdfDetailBinding binding;

    //get pdfId from intent
    String bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPdfDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //get data from intent
        Intent intent = getIntent();
        bookId = intent.getStringExtra("bookId");


        //click to go back
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //load book details
        loadBookDetails();

        //increase book views
        MyApplication.incrementBookViewsCount(bookId);

        //click to view pdf
        binding.readBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent1 = new Intent(PdfDetailActivity.this, PdfViewActivity.class);
               intent1.putExtra("bookId", bookId);
               startActivity(intent1);
            }
        });


    }
    // Loading the book's details
    private void loadBookDetails() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Books");
        ref.child(bookId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //get data
                        String title = ""+snapshot.child("title").getValue();
                        String description = ""+snapshot.child("description").getValue();
                        String categoryId = ""+snapshot.child("categoryId").getValue();
                        String viewCount = ""+snapshot.child("viewCount").getValue();
                        String license = ""+snapshot.child("license").getValue();
                        String downloadCount = ""+snapshot.child("downloadCount").getValue();
                        String url = ""+snapshot.child("url").getValue();
                        String timestamp = ""+snapshot.child("timestamp").getValue();

                        //format date
                        String date = MyApplication.formatTimestamp(Long.parseLong(timestamp));

                        //load category
                        MyApplication.loadCategory(""+categoryId, binding.categoryTv);

                        //load pdf from url with first page
                        MyApplication.loadPdfFromUrlSinglePage(""+url,""+title, binding.pdfView, binding.progressBar);

                        //load pdf size
                        MyApplication.loadPdfSize(""+url,""+title,binding.sizeTv);

                        //set data
                        binding.titleTv.setText(title);
                        binding.descriptionTv.setText(description);
                        binding.viewTv.setText(viewCount.replace("null","N/A"));
                        binding.downloadTv.setText(downloadCount.replace("null","N/A"));
                        binding.dateTv.setText(date);
                        binding.licenseTv.setText(license);



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}