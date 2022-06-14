package id19110100.hcmute.edu.reaboadmin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import id19110100.hcmute.edu.reaboadmin.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import id19110100.hcmute.edu.reaboadmin.Model.Product;

public class HomeProductAdapter extends  RecyclerView.Adapter<HomeProductAdapter.HomeProductViewHolder>{
    private BottomSheetDialog bottomSheetDialog;
    private Context context;
    private ArrayList<Product> products;

    public HomeProductAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public HomeProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_product,parent,false);
        return new HomeProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeProductViewHolder holder, int position) {
        Product product = products.get(position);
        if(product ==null)
        {
            return;
        }
        final int image= product.getImg();
        final String name= product.getName();
        final  String price=  String.valueOf(product.getPrice());
        holder.imgProduct.setImageResource(product.getImg());
        holder.nameProduct.setText(product.getName());
        holder.priceProduct.setText(String.valueOf(product.getPrice()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog=new BottomSheetDialog(context,R.style.BottomSheetTheme);
                View sheetView=LayoutInflater.from(context).inflate(R.layout.home_product_bottom_layout,null);
                Activity a = (Activity) context;
                sheetView.findViewById(R.id.btn_add_to_library).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context,"Added to Library",Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.dismiss();
                    }
                });
                ImageView imgBottomProduct=sheetView.findViewById(R.id.home_product_bottom_img);
                TextView nameBottomProduct=sheetView.findViewById(R.id.home_product_bottom_product_name);
                TextView priceBottomProduct=sheetView.findViewById(R.id.home_product_bottom_product_price);
                TextView btnback = sheetView.findViewById(R.id.btn_back);
                btnback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.dismiss();
                    }
                });
                imgBottomProduct.setImageResource(image);
                nameBottomProduct.setText(name);
                priceBottomProduct.setText(price);

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

    public class HomeProductViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgProduct;
        private TextView nameProduct,priceProduct;

        public HomeProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct=itemView.findViewById(R.id.home_product_image);
            nameProduct=itemView.findViewById(R.id.home_product_name);
            priceProduct=itemView.findViewById(R.id.home_product_price);
        }
    }

}

