package id19110100.hcmute.edu.reaboadmin.Class;

import static id19110100.hcmute.edu.reaboadmin.Class.Constants.MAX_BYTE_PDF;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import id19110100.hcmute.edu.reaboadmin.Model.Favorite;
import id19110100.hcmute.edu.reaboadmin.Model.Library;
import id19110100.hcmute.edu.reaboadmin.Model.ModelPdf;
import id19110100.hcmute.edu.reaboadmin.R;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    //convert timestamp to date
    public static final String formatTimestamp(long timestamp){
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timestamp);
        //format timestamp to dd/mm/yyyy
        String date = DateFormat.format("dd/MM/yyy", cal).toString();

        return date;
    }

    //delete book
    public static void deleteBook(Context context, String bookId, String bookUrl, String bookTitle) {
        //add tag
        String TAG = "DELETE_BOOK_TAG";

        Log.d(TAG, "deleteBook: Deleting...");
        //progress
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Please wait...");
        progressDialog.setMessage("Deleting "+ bookTitle+"...");
        progressDialog.show();

        Log.d(TAG, "deleteBook: Deleting from firebase");
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(bookUrl);
        storageReference.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //success
                        Log.d(TAG, "onSuccess: Deleted from storage");
                        Log.d(TAG, "onSuccess: Deleting from db");

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Books");
                        ref.child(bookId)
                                .removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        //success
                                        Log.d(TAG, "onSuccess: Deleted from db");
                                        progressDialog.dismiss();
                                        Toast.makeText(context, ""+bookTitle+" Book Deleted Successfully!", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        //fail
                                        Log.d(TAG, "onFailure: Fail to delete due to "+e.getMessage());
                                        progressDialog.dismiss();
                                        Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //fail
                        Log.d(TAG, "onFailure: Failed to delete due to "+e.getMessage());
                        progressDialog.dismiss();
                        Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //load pdf size
    public static void loadPdfSize(String pdfUrl, String pdfTitle, TextView sizeTv) {
        //add tag
        String TAG="PDF_SIZE_TAG";

        //using url from firebase to get size
        StorageReference ref = FirebaseStorage.getInstance().getReferenceFromUrl(pdfUrl);
        ref.getMetadata()
                .addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                    @Override
                    public void onSuccess(StorageMetadata storageMetadata) {
                        //success
                        //get size in byte
                        double bytes = storageMetadata.getSizeBytes();
                        Log.d(TAG, "onSuccess: "+pdfTitle+" "+bytes);
                        //convert to kb or mb
                        double kb = bytes/1024;
                        double mb = kb/1024;

                        if(mb>=1){
                            sizeTv.setText(String.format("%.2f", mb)+" MB");
                        }
                        else  if(kb>=1){
                            sizeTv.setText(String.format("%.2f", kb)+" KB");
                        }
                        else {
                            sizeTv.setText(String.format("%.2f", bytes)+" bytes");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //fail
                        Log.d(TAG, "onFailure: "+e.getMessage());
                    }
                });
    }

    //load pdf from url with first page
    public static void loadPdfFromUrlSinglePageNoProgessbar(String pdfUrl, String pdfTitle, PDFView pdfView) {

        //add tag
        String TAG = "PDF_FROM_URL_TAG";
        //using url from firebase to get file
        StorageReference ref = FirebaseStorage.getInstance().getReferenceFromUrl(pdfUrl);
        ref.getBytes(MAX_BYTE_PDF)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        //success
                        Log.d(TAG, "onSuccess: "+pdfTitle+" success");

                        //set to pdfview
                        pdfView.fromBytes(bytes)
                                .pages(0) //first page only
                                .spacing(0)
                                .swipeHorizontal(false)
                                .enableSwipe(false)
                                .onError(new OnErrorListener() {
                                    @Override
                                    public void onError(Throwable t) {

                                        Log.d(TAG, "onError: "+t.getMessage());
                                    }
                                })
                                .onPageError(new OnPageErrorListener() {
                                    @Override
                                    public void onPageError(int page, Throwable t) {

                                        Log.d(TAG, "onPageError: "+t.getMessage());
                                    }
                                })
                                .onLoad(new OnLoadCompleteListener() {
                                    @Override
                                    public void loadComplete(int nbPages) {

                                        //pdf load
                                        Log.d(TAG, "loadComplete: pdf load");
                                    }
                                })
                                .load();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //fail
                        Log.d(TAG, "onFailure: fail "+e.getMessage());
                    }
                });

    }

    public static void loadPdfFromUrlSinglePage(String pdfUrl, String pdfTitle, PDFView pdfView, ProgressBar progressBar) {

        //add tag
        String TAG = "PDF_FROM_URL_TAG";
        //using url from firebase to get file
        StorageReference ref = FirebaseStorage.getInstance().getReferenceFromUrl(pdfUrl);
        ref.getBytes(MAX_BYTE_PDF)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        //success
                        Log.d(TAG, "onSuccess: "+pdfTitle+" success");

                        //set to pdfview
                        pdfView.fromBytes(bytes)
                                .pages(0) //first page only
                                .spacing(0)
                                .swipeHorizontal(false)
                                .enableSwipe(false)
                                .onError(new OnErrorListener() {
                                    @Override
                                    public void onError(Throwable t) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Log.d(TAG, "onError: "+t.getMessage());
                                    }
                                })
                                .onPageError(new OnPageErrorListener() {
                                    @Override
                                    public void onPageError(int page, Throwable t) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Log.d(TAG, "onPageError: "+t.getMessage());
                                    }
                                })
                                .onLoad(new OnLoadCompleteListener() {
                                    @Override
                                    public void loadComplete(int nbPages) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        //pdf load
                                        Log.d(TAG, "loadComplete: pdf load");
                                    }
                                })
                                .load();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //fail
                        Log.d(TAG, "onFailure: fail "+e.getMessage());
                    }
                });

    }

    //load category
    public static void loadCategory(String categoryId, TextView categoryTv) {
        //get category by id

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Categories");
        ref.child(categoryId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //get category
                        String category ="" + snapshot.child("category").getValue();

                        //set category
                        categoryTv.setText(category);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public static void incrementBookViewsCount(String bookId){
        //get book views count
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Books");
        ref.child(bookId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //get views count
                        String viewCount = ""+snapshot.child("viewCount").getValue();

                        //if null replace with 0
                        if(viewCount.equals("")||viewCount.equals("null")){
                            viewCount = "0";
                        }

                        //increase
                        long newViewsCount = Long.parseLong(viewCount)+1;
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("viewCount", newViewsCount);

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Books");
                        reference.child(bookId)
                                .updateChildren(hashMap);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    //load first page of pdf to show as an image
    public static void loadFirstPage(String pdfUrl, PDFView pdfView) {
        //add tag
        String TAG = "PDF_FROM_URL_TAG";
        //using url from firebase to get file
        StorageReference ref = FirebaseStorage.getInstance().getReferenceFromUrl(pdfUrl);
        ref.getBytes(MAX_BYTE_PDF)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        //set to pdfview if success
                        pdfView.fromBytes(bytes)
                                .pages(0) //first page only
                                .spacing(0)
                                .swipeHorizontal(false)
                                .enableSwipe(false)
                                .onError(new OnErrorListener() {
                                    @Override
                                    public void onError(Throwable t) {

                                        Log.d(TAG, "onError: "+t.getMessage());
                                    }
                                })
                                .onPageError(new OnPageErrorListener() {
                                    @Override
                                    public void onPageError(int page, Throwable t) {

                                        Log.d(TAG, "onPageError: "+t.getMessage());
                                    }
                                })
                                .onLoad(new OnLoadCompleteListener() {
                                    @Override
                                    public void loadComplete(int nbPages) {

                                        //pdf load
                                        Log.d(TAG, "loadComplete: pdf load");
                                    }
                                })
                                .load();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //fail
                        Log.d(TAG, "onFailure: fail "+e.getMessage());
                    }
                });

    }
    //if the book in favorite table already, change the heart image
    static Boolean return_data;
    public static void checkFavorite(String userID, ModelPdf book, TextView favoritebtn, Context context, String uid, BottomSheetDialog bottomsheet){
        return_data = false;
        //ArrayList<Favorite> favorites = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Favorite");
        ref.orderByChild("uid").equalTo(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Favorite my_favorite = new Favorite();
                for (DataSnapshot ds:snapshot.getChildren()){
                    Favorite favorite = ds.getValue(Favorite.class);
                    if(favorite.getBooks().getId().equals(book.getId())) {
                        my_favorite = favorite;
                        return_data = true;
                    }
                }
                final Favorite my_favorite_1= my_favorite;
                if(return_data){
                    //if the book is in the table already, remove it and change image heart
                    favoritebtn.setBackground(ContextCompat.getDrawable(context, R.drawable.heart_check));
                    favoritebtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                deleteFavorite(my_favorite_1.getId());
                                favoritebtn.setBackground(ContextCompat.getDrawable(context, R.drawable.heart));
                                Toast.makeText(context, "Removed from Favorite", Toast.LENGTH_SHORT).show();
                                bottomsheet.dismiss();
                        }
                        public void deleteFavorite(String id){
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Favorite");
                            ref.child(id).removeValue();
                        }

                    });
                }else{
                    //if the book is not in the table already, add it and change image heart
                    favoritebtn.setBackground(ContextCompat.getDrawable(context, R.drawable.heart));
                    favoritebtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            uploadFavoriteToDb(book,System.currentTimeMillis(),uid);
                            Toast.makeText(context, "Added to Favorite", Toast.LENGTH_SHORT).show();
                            favoritebtn.setBackground(ContextCompat.getDrawable(context, R.drawable.heart_check));

                        }
                        public void uploadFavoriteToDb(ModelPdf product, Long timestamp,String uid) {

                            //set up data
                            HashMap<String,Object> hashMap = new HashMap<>();
                            hashMap.put("uid", ""+uid);
                            hashMap.put("id", ""+timestamp);
                            hashMap.put("Books", product);

                            //ref to upload to firebase
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Favorite");
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

                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}
