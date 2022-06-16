package id19110100.hcmute.edu.reaboadmin.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import id19110100.hcmute.edu.reaboadmin.Model.Category;
import id19110100.hcmute.edu.reaboadmin.Model.ModelPdf;
import id19110100.hcmute.edu.reaboadmin.PdfListAdminActivity;
import id19110100.hcmute.edu.reaboadmin.R;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import id19110100.hcmute.edu.reaboadmin.Model.Product;

public class HomeCategoryAdapter extends  RecyclerView.Adapter<HomeCategoryAdapter.HomeCategoryViewHolder> {
    private HomeFilterProduct homeFilterProduct;
    private Activity homeActivity;
    private ArrayList<Category> homeCategories;
    private ArrayList<ModelPdf> pdfArrayList;
    private static final String TAG = "PDF_LIST_TAG";
    private AdapterPdfAdmin adapterPdfAdmin;




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
            loadPdfListByHotdeal(position);
            //homeFilterProduct.callBack(position, products);
            checked = false;
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index=position;
                notifyDataSetChanged();
                if(row_index==0)
                {
                    loadPdfListByHotdeal(row_index);
                    //homeFilterProduct.callBack(row_index, products);
                }
                else if(row_index==1)
                {
                    loadPdfListByRanking(row_index);
                    //homeFilterProduct.callBack(row_index, products);

                }
                else if(row_index==2)
                {
                    loadPdfListByNewBook(row_index);

                    //homeFilterProduct.callBack(row_index, products);

                }
                else if(row_index==3)
                {
                    loadPdfListByZenCat(row_index);
                    //homeFilterProduct.callBack(row_index, products);

                }
                else if(row_index==4)
                {

                    loadPdfListByAcaCat(row_index);
                    //homeFilterProduct.callBack(row_index, products);

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

    private void loadPdfList(int position) {
        //init
        pdfArrayList = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Books");
        ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        pdfArrayList.clear();
                        for(DataSnapshot ds: snapshot.getChildren()){
                            //get data
                            ModelPdf model = ds.getValue(ModelPdf.class);
                            //add to list
                            pdfArrayList.add(model);

                            Log.d(TAG, "onDataChange: "+model.getId()+" "+model.getTitle());
                        }
                        homeFilterProduct.callBack(position, pdfArrayList);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


    public ArrayList<ModelPdf> gethotdeal(ArrayList<ModelPdf> listBooks){
        ArrayList<ModelPdf> return_data = new ArrayList<>();
        if(listBooks.size() >= 5){
            for (int i=0;i<5;i++){
                return_data.add(listBooks.get(i));
            }
        }
        else{
            for (int i=0;i<listBooks.size();i++){
                return_data.add(listBooks.get(i));
            }
        }
        return return_data;
    }

    private void loadPdfListByHotdeal(int position) {
        //init
        pdfArrayList = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Books");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pdfArrayList.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    //get data
                    ModelPdf model = ds.getValue(ModelPdf.class);
                    //add to list
                    pdfArrayList.add(model);

                    Log.d(TAG, "onDataChange: "+model.getId()+" "+model.getTitle());
                }

                ArrayList<ModelPdf> hotdeals = gethotdeal(pdfArrayList);

                homeFilterProduct.callBack(position, hotdeals);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private ArrayList<ModelPdf> getRanking (ArrayList<ModelPdf> listBooks){
        ArrayList<ModelPdf> return_data = new ArrayList<>();
        ArrayList<ModelPdf> list_search_book = listBooks;
        Collections.sort(list_search_book, new Comparator<ModelPdf>(){
            public int compare(ModelPdf e1, ModelPdf e2){
                return e2.getViewCount() - (e1.getViewCount());
            }
        });
        //System.out.println(listBooks);

        if(list_search_book.size() >= 5){
            for (int i=0;i<5;i++){
                return_data.add(list_search_book.get(i));
            }
        }
        else{
            for (int i=0;i<list_search_book.size();i++){
                return_data.add(list_search_book.get(i));
            }
        }
        return return_data;
    }


    private void loadPdfListByRanking(int position) {
        //init
        pdfArrayList = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Books");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pdfArrayList.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    //get data
                    ModelPdf model = ds.getValue(ModelPdf.class);
                    //add to list
                    pdfArrayList.add(model);

                    Log.d(TAG, "onDataChange: "+model.getId()+" "+model.getTitle());
                }

                ArrayList<ModelPdf> hotdeals = getRanking(pdfArrayList);

                homeFilterProduct.callBack(position, hotdeals);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private ArrayList<ModelPdf> getnewbook(ArrayList<ModelPdf> listBooks){
        ArrayList<ModelPdf> return_data = new ArrayList<>();
        if(listBooks.size() >= 5){
            int count=0;
            for (int i=listBooks.size()-1;count<5;i--){
                return_data.add(listBooks.get(i));
                count +=1;
            }
        }
        else{
            for (int i=0;i<listBooks.size();i++){
                return_data.add(listBooks.get(i));
            }
        }
        return return_data;
    }


    private void loadPdfListByNewBook(int position) {
        //init
        pdfArrayList = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Books");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pdfArrayList.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    //get data
                    ModelPdf model = ds.getValue(ModelPdf.class);
                    //add to list
                    pdfArrayList.add(model);

                    Log.d(TAG, "onDataChange: "+model.getId()+" "+model.getTitle());
                }

                ArrayList<ModelPdf> hotdeals = getnewbook(pdfArrayList);

                homeFilterProduct.callBack(position, hotdeals);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public ArrayList<ModelPdf> getModelPDFbyCateID(ArrayList<ModelPdf> booklist,String id){
        ArrayList<ModelPdf> return_array= new ArrayList<>();
        int count =0;
        for (int i=0;i< booklist.size()&&count<5;i++){
            if (booklist.get(i).getCategoryId().equals(id)){
                return_array.add(booklist.get(i));
                count+=1;
            }
        }
        return return_array;
    }

    private void loadPdfListByZenCat(int position) {
        //init
        pdfArrayList = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Books");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pdfArrayList.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    //get data
                    ModelPdf model = ds.getValue(ModelPdf.class);
                    //add to list
                    pdfArrayList.add(model);

                    Log.d(TAG, "onDataChange: "+model.getId()+" "+model.getTitle());
                }

                ArrayList<ModelPdf> hotdeals = getModelPDFbyCateID(pdfArrayList,"1655377410674");

                homeFilterProduct.callBack(position, hotdeals);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadPdfListByAcaCat(int position) {
        //init
        pdfArrayList = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Books");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pdfArrayList.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    //get data
                    ModelPdf model = ds.getValue(ModelPdf.class);
                    //add to list
                    pdfArrayList.add(model);

                    Log.d(TAG, "onDataChange: "+model.getId()+" "+model.getTitle());
                }

                ArrayList<ModelPdf> hotdeals = getModelPDFbyCateID(pdfArrayList,"1655377371010");

                homeFilterProduct.callBack(position, hotdeals);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
