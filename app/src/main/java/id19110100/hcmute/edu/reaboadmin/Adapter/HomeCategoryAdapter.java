package id19110100.hcmute.edu.reaboadmin.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import id19110100.hcmute.edu.reaboadmin.Model.Category;
import id19110100.hcmute.edu.reaboadmin.R;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id19110100.hcmute.edu.reaboadmin.Model.Product;

public class HomeCategoryAdapter extends  RecyclerView.Adapter<HomeCategoryAdapter.HomeCategoryViewHolder> {
    private HomeFilterProduct homeFilterProduct;
    private Activity homeActivity;
    private ArrayList<Category> homeCategories;




    private boolean checked=true;
    private boolean selected=true;
    private int row_index=-1;

    public HomeCategoryAdapter(HomeFilterProduct homeFilterProduct, Activity homeActivity, ArrayList<Category> homeCategories) {
        this.homeFilterProduct = homeFilterProduct;
        this.homeActivity = homeActivity;
        this.homeCategories = homeCategories;
    }


    @NonNull
    @Override
    public HomeCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);
        return new HomeCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeCategoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Category category =homeCategories.get(position);
        if(category ==null)
        {
            return;
        }
        holder.imgCategory.setImageResource(category.getResourceId());
        holder.nameCategory.setText(category.getName());
        if(checked) {
            ArrayList<Product> products = new ArrayList<>();
            //products = getProductbyCate("Cake");
            products.add(new Product(1, "The old man and the sea", 50000,R.drawable.hot_deal_imh,""));
            products.add(new Product(1, "The old man and the sea", 50000,R.drawable.hot_deal_imh,""));
            products.add(new Product(1, "The old man and the sea", 50000,R.drawable.hot_deal_imh,""));

            homeFilterProduct.callBack(position, products);
            checked = false;
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index=position;
                notifyDataSetChanged();
                if(row_index==0)
                {
                    ArrayList<Product> products =new ArrayList<>();
                    //products = getProductbyCate("Cake");

                    products.add(new Product(1, "The Old Man And The Sea", 50000,R.drawable.hot_deal_imh,""));
                    products.add(new Product(1, "The Old Man And The Sea", 50000,R.drawable.hot_deal_imh,""));
                    products.add(new Product(1, "The Old Man And The Sea", 50000,R.drawable.hot_deal_imh,""));

                    homeFilterProduct.callBack(row_index, products);
                }
                else if(row_index==1)
                {
                    ArrayList<Product> products =new ArrayList<>();
                    //products = getProductbyCate("Frozen Dessert");

                    products.add(new Product(1,"The Prophet",50000,R.drawable.rank_img,""));
                    products.add(new Product(2,"The Prophet",60000,R.drawable.rank_img,""));
                    products.add(new Product(3,"The Prophet",70000,R.drawable.rank_img,""));
                    products.add(new Product(4,"The Prophet",80000,R.drawable.rank_img,""));

                    homeFilterProduct.callBack(row_index, products);

                }
                else if(row_index==2)
                {
                    ArrayList<Product> products =new ArrayList<>();
                    // products = getProductbyCate("Dessert Soup");

                    products.add(new Product(1,"The Danish Girl",50000,R.drawable.new_img,""));
                    products.add(new Product(2,"The Danish Girl",60000,R.drawable.new_img,""));


                    homeFilterProduct.callBack(row_index, products);

                }
                else if(row_index==3)
                {
                    ArrayList<Product> products =new ArrayList<>();
                    //products = getProductbyCate("Drink");

                    products.add(new Product(1,"Thien That Ra Khong Kho",50000,R.drawable.zen_img,""));

                    homeFilterProduct.callBack(row_index, products);

                }
                else if(row_index==4)
                {
                    ArrayList<Product> products =new ArrayList<>();
                    //products = getProductbyCate("Custard");

                    products.add(new Product(1,"Doi Tho",50000,R.drawable.learning_img,""));
                    products.add(new Product(1,"Doi Tho",50000,R.drawable.learning_img,""));
                    products.add(new Product(1,"Doi Tho",50000,R.drawable.learning_img,""));
                    products.add(new Product(1,"Doi Tho",50000,R.drawable.learning_img,""));
                    products.add(new Product(1,"Doi Tho",50000,R.drawable.learning_img,""));


                    homeFilterProduct.callBack(row_index, products);

                }
            }
        });
        if (selected) {
            if (position == 0) {
                holder.cardView.setBackgroundResource(R.drawable.bg_change);
                selected=false;
            }
        }
        else
        {
            if(row_index==position)
            {
                holder.cardView.setBackgroundResource(R.drawable.bg_change);
            }
            else
            {
                holder.cardView.setBackgroundResource(R.drawable.bg_default);
            }
        }

    }



    @Override
    public int getItemCount() {
        if(homeCategories!=null)
        {
            return homeCategories.size();
        }
        return 0;
    }

    public class HomeCategoryViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgCategory;
        private TextView nameCategory;
        private CardView cardView;
        public HomeCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCategory = itemView.findViewById(R.id.item_category_image);
            nameCategory = itemView.findViewById(R.id.item_category_name);
            cardView=itemView.findViewById(R.id.item_category_cv);
        }
    }
}
