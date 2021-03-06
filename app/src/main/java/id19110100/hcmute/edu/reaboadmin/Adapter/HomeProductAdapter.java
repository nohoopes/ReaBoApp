package id19110100.hcmute.edu.reaboadmin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import id19110100.hcmute.edu.reaboadmin.Class.MyApplication;
import id19110100.hcmute.edu.reaboadmin.Model.Library;
import id19110100.hcmute.edu.reaboadmin.Model.ModelPdf;
import id19110100.hcmute.edu.reaboadmin.R;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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

import id19110100.hcmute.edu.reaboadmin.Model.Product;

public class HomeProductAdapter extends  RecyclerView.Adapter<HomeProductAdapter.HomeProductViewHolder>{
    private BottomSheetDialog bottomSheetDialog;
    private Context context;
    private ArrayList<ModelPdf> products;

    //variable firebaseAuth
    private FirebaseAuth firebaseAuth;

    //Constructor
    public HomeProductAdapter(Context context, ArrayList<ModelPdf> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public HomeProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_product,parent,false);
        firebaseAuth = FirebaseAuth.getInstance();
        return new HomeProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeProductViewHolder holder, int position) {
        //get product from list at position
        ModelPdf product = products.get(position);
        if(product ==null)
        {
            return;
        }

        //get data from product
        String pdfId = product.getId();
        String categoryId = product.getCategoryId();
        String pdfUrl = product.getUrl();
        String title = product.getTitle();
        String description = product.getDescription();
        String license = product.getLicense();
        long timestamp = product.getTimestamp();
        int countview = product.getViewCount();

        //load first page to imgview
        MyApplication.loadPdfFromUrlSinglePageNoProgessbar(
                ""+pdfUrl,
                ""+title,
                holder.imgProduct);
        //set data
        holder.nameProduct.setText(title);
        holder.priceProduct.setText(license);
        //click to product to show bottom sheet
        holder.imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog=new BottomSheetDialog(context,R.style.BottomSheetTheme);
                View sheetView=LayoutInflater.from(context).inflate(R.layout.home_product_bottom_layout,null);
                Activity a = (Activity) context;
                sheetView.findViewById(R.id.btn_add_to_library).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        uploadLibraryToDb(product, System.currentTimeMillis());
                        Toast.makeText(context,"Added to Library",Toast.LENGTH_SHORT).show();
                    }
                });

                //mapping
                PDFView imgBottomProduct=sheetView.findViewById(R.id.home_product_bottom_img);
                TextView nameBottomProduct=sheetView.findViewById(R.id.home_product_bottom_product_name);
                TextView priceBottomProduct=sheetView.findViewById(R.id.home_product_bottom_product_price);
                TextView descriptionBottomProduct=sheetView.findViewById(R.id.tx_book_description);
                TextView favoriteBtn = sheetView.findViewById(R.id.txt_favorite);
                TextView btnback = sheetView.findViewById(R.id.btn_back);
                TextView cateBook = sheetView.findViewById(R.id.txt_cateBook);
                //get uid
                String uid = firebaseAuth.getUid();
                //set favorite heart whether it is checked or not
                MyApplication.checkFavorite(uid,product,favoriteBtn,context ,uid,bottomSheetDialog);
                //load category name to textview
                MyApplication.loadCategory(categoryId,cateBook);

                //
                btnback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.dismiss();
                    }
                });
                //load first page to imgview
                MyApplication.loadPdfFromUrlSinglePageNoProgessbar(
                        ""+pdfUrl,
                        ""+title,
                        imgBottomProduct);
                //set data
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
        if(products !=null){
            return products.size();
        }
        return 0;
    }

    //View Holder of Adapter
    public class HomeProductViewHolder extends RecyclerView.ViewHolder{
        private PDFView imgProduct;
        private TextView nameProduct,priceProduct;

        public HomeProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct=itemView.findViewById(R.id.home_product_image);
            nameProduct=itemView.findViewById(R.id.home_product_name);
            priceProduct=itemView.findViewById(R.id.home_product_price);
        }
    }

    //upload Library to firebase
    private void uploadLibraryToDb(ModelPdf product, Long timestamp) {
        String uid = firebaseAuth.getUid();

        //set up data
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("uid", ""+uid);
        hashMap.put("id", ""+timestamp);
        hashMap.put("Books", product);

        //db ref to upload to firebase
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

