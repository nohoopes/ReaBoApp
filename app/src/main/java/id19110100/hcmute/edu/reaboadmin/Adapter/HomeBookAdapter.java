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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import id19110100.hcmute.edu.reaboadmin.Model.Book;

public class HomeBookAdapter extends RecyclerView.Adapter<HomeBookAdapter.BookViewHoler> {
    private BottomSheetDialog bottomSheetDialog;
    private List<Book> mbooks;
    private Context context;



    public void setData (Context context, List<Book> list){
        this.mbooks = list;
        this.context = context;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public BookViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (parent.getContext ()).inflate(R.layout.item_book, parent,false);
        return new BookViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHoler holder, int position) {
        Book book = mbooks.get(position);
        if (book == null){
            return;
            // Get resource
        }


        holder.item_book_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog=new BottomSheetDialog(context,R.style.BottomSheetTheme);
                View sheetView=LayoutInflater.from(context).inflate(R.layout.home_product_bottom_layout,null);
                Activity a = (Activity) context;
                sheetView.findViewById(R.id.btn_add_to_library).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context,"Added to Cart",Toast.LENGTH_SHORT).show();
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
                //imgBottomProduct.setImageResource(image);
                //nameBottomProduct.setText(name);
                //priceBottomProduct.setText(price);

                bottomSheetDialog.setContentView(sheetView);
                bottomSheetDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        if(mbooks != null){
            return mbooks.size();
        }
        return 0;
    }

    public class BookViewHoler extends RecyclerView.ViewHolder {

        ConstraintLayout item_book_layout;
        ShapeableImageView item_book_img;
        TextView tv_item_book_name,tv_item_book_cat,tv_item_book_price;


        public BookViewHoler(@NonNull View itemView) {
            super(itemView);

            item_book_layout = itemView.findViewById(R.id.item_book_layout);
            item_book_img = itemView.findViewById(R.id.item_book_img);
            tv_item_book_name = itemView.findViewById(R.id.tv_item_book_name);
            tv_item_book_cat = itemView.findViewById(R.id.tv_item_book_cat);
            tv_item_book_price = itemView.findViewById(R.id.tv_item_book_price);

        }
    }
}
