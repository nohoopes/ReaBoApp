package id19110100.hcmute.edu.reaboadmin.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

import id19110100.hcmute.edu.reaboadmin.Class.FilterPdfAdmin;
import id19110100.hcmute.edu.reaboadmin.Class.FilterPdfUser;
import id19110100.hcmute.edu.reaboadmin.Class.MyApplication;
import id19110100.hcmute.edu.reaboadmin.Model.ModelPdf;
import id19110100.hcmute.edu.reaboadmin.PdfDetailActivity;
import id19110100.hcmute.edu.reaboadmin.R;
import id19110100.hcmute.edu.reaboadmin.databinding.RowPdfUserBinding;

public class AdapterPdfUser extends RecyclerView.Adapter<AdapterPdfUser.HolderPdfUser> implements Filterable{
    private Context context;
    //arraylist to hold list of data
    public ArrayList<ModelPdf> pdfArrayList, filterList;
    private BottomSheetDialog bottomSheetDialog;

    //view binding
    private RowPdfUserBinding binding;

    //filter
    private FilterPdfUser filter;

    //progress
    private ProgressDialog progressDialog;
    private static final String TAG = "PDF_ADAPTER_TAG";
    private FirebaseAuth firebaseAuth;

    public AdapterPdfUser(Context context, ArrayList<ModelPdf> pdfArrayList) {
        this.context = context;
        this.pdfArrayList = pdfArrayList;
        this.filterList = pdfArrayList;
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);
    }
    @NonNull
    @Override
    public HolderPdfUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //bind layout
        binding = RowPdfUserBinding.inflate(LayoutInflater.from(context), parent, false);
        firebaseAuth = FirebaseAuth.getInstance();
        return new HolderPdfUser(binding.getRoot());
    }


    @Override
    public void onBindViewHolder(@NonNull HolderPdfUser holder, int position) {
        //handle
        //get data
        ModelPdf model = pdfArrayList.get(position);
        String pdfId = model.getId();
        String categoryId = model.getCategoryId();
        String pdfUrl = model.getUrl();
        String title = model.getTitle();
        String description = model.getDescription();
        String license = model.getLicense();
        long timestamp = model.getTimestamp();

        //convert timestamp to dd/mm/yyyy
        String formatDate = MyApplication.formatTimestamp(timestamp);

        //set data
        holder.titleTv.setText(title);
        holder.descriptionTv.setText(description);
        holder.licenseTv.setText(license);
        holder.dateTv.setText(formatDate);

        //load more details data
        //load category
        MyApplication.loadCategory(
                ""+categoryId,
                holder.categoryTv);
        //load pdf file with first page
        MyApplication.loadPdfFromUrlSinglePage(
                ""+pdfUrl,
                ""+title,
                holder.pdfView,
                holder.progressBar);
        //load pdf size
        MyApplication.loadPdfSize(
                ""+pdfUrl,
                ""+title,
                holder.sizeTv);

        //click to show dialog to show option edit or delete
        //click to open book details
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog=new BottomSheetDialog(context, R.style.BottomSheetTheme);
                View sheetView=LayoutInflater.from(context).inflate(R.layout.home_product_bottom_layout,null);
                Activity a = (Activity) context;
                sheetView.findViewById(R.id.btn_add_to_library).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        uploadLibraryToDb(model, System.currentTimeMillis());
                        Toast.makeText(context,"Added to Library",Toast.LENGTH_SHORT).show();
                    }
                });
                PDFView imgBottomProduct=sheetView.findViewById(R.id.home_product_bottom_img);
                TextView nameBottomProduct=sheetView.findViewById(R.id.home_product_bottom_product_name);
                TextView priceBottomProduct=sheetView.findViewById(R.id.home_product_bottom_product_price);
                TextView descriptionBottomProduct=sheetView.findViewById(R.id.tx_book_description);
                TextView favoriteBtn = sheetView.findViewById(R.id.txt_favorite);
                TextView cateBook = sheetView.findViewById(R.id.txt_cateBook);
                MyApplication.loadCategory(categoryId,cateBook);
                String uid = firebaseAuth.getUid();
                MyApplication.checkFavorite(uid,model,favoriteBtn,context ,uid,bottomSheetDialog);
                TextView btnback = sheetView.findViewById(R.id.btn_back);
                btnback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.dismiss();
                    }
                });
                MyApplication.loadPdfFromUrlSinglePageNoProgessbar(
                        ""+pdfUrl,
                        ""+title,
                        imgBottomProduct);

                nameBottomProduct.setText(title);
                priceBottomProduct.setText(license);
                descriptionBottomProduct.setText(description);

                bottomSheetDialog.setContentView(sheetView);
                bottomSheetDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return pdfArrayList.size(); //return list size
    }

    //filter
    @Override
    public Filter getFilter() {

        if(filter == null){
            filter = new FilterPdfUser(filterList, this);

        }
        return filter;
    }
    class HolderPdfUser extends RecyclerView.ViewHolder{

        //UI view
        PDFView pdfView;
        ProgressBar progressBar;
        TextView titleTv, descriptionTv, categoryTv, sizeTv, dateTv, licenseTv;


        public HolderPdfUser(@NonNull View itemView) {
            super(itemView);

            //init ui view
            pdfView = binding.pdfView;
            progressBar = binding.progressBar;
            titleTv = binding.titleTv;
            descriptionTv = binding.descriptionTv;
            categoryTv = binding.categoryTv;
            sizeTv = binding.sizeTv;
            dateTv = binding.dateTv;
            licenseTv = binding.licenseTv;

        }
    }
    private void uploadLibraryToDb(ModelPdf product, Long timestamp) {
        String uid = firebaseAuth.getUid();

        //set up data
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("uid", ""+uid);
        hashMap.put("id", ""+timestamp);
        hashMap.put("Books", product);

        //db ref
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Library");
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
