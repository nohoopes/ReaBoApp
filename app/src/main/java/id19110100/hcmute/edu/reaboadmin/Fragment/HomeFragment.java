package id19110100.hcmute.edu.reaboadmin.Fragment;

import id19110100.hcmute.edu.reaboadmin.Adapter.AdapterPdfAdmin;
import id19110100.hcmute.edu.reaboadmin.Adapter.HomeBannerAdapter;
import id19110100.hcmute.edu.reaboadmin.Adapter.HomeBookAdapter;
import id19110100.hcmute.edu.reaboadmin.Adapter.HomeCategoryAdapter;
import id19110100.hcmute.edu.reaboadmin.Adapter.HomeFilterProduct;
import id19110100.hcmute.edu.reaboadmin.Adapter.HomeProductAdapter;
import id19110100.hcmute.edu.reaboadmin.Class.MyApplication;
import id19110100.hcmute.edu.reaboadmin.Model.Banner;
import id19110100.hcmute.edu.reaboadmin.Model.Category;
import id19110100.hcmute.edu.reaboadmin.Model.ModelCategoryAd;
import id19110100.hcmute.edu.reaboadmin.Model.ModelPdf;
import  id19110100.hcmute.edu.reaboadmin.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import id19110100.hcmute.edu.reaboadmin.databinding.ActivityPdfListAdminBinding;
import me.relex.circleindicator.CircleIndicator3;



public class HomeFragment extends Fragment implements HomeFilterProduct {
    /////////////// best deal image slider
    private ViewPager2 homeViewPager;
    private CircleIndicator3 homeCircleIndicator;
    private List<Banner> banners;
    private HomeBannerAdapter homeBannerAdapter;
    private Handler handler=new Handler(Looper.getMainLooper());
    private Runnable runnable=new Runnable()
    {
        @Override
        public void run() {
            int currentPos=homeViewPager.getCurrentItem();
            if(currentPos== banners.size()-1){
                homeViewPager.setCurrentItem(0);
            }else
            {
                homeViewPager.setCurrentItem(currentPos+1);
            }
        }
    };
    /////////////// home category recycler view
    private RecyclerView homeHorRecyclerView;
    private HomeCategoryAdapter homeCategoryAdapter;
    private ArrayList<Category> homeCategories;
    /////////////// home product recycler view
    private RecyclerView homeVerRecyclerView;
    private HomeProductAdapter homeProductAdapter;
    private ArrayList<ModelPdf> products;

    private EditText searchRestaurantText;
    RecyclerView home_thiendinh_recyclerview;
    RecyclerView home_cooking_recyclerview;


    //// Testing get pdf
    private ArrayList<ModelPdf> pdfArrayList;

    //adapter
    private AdapterPdfAdmin adapterPdfAdmin;

    //view binding
    private ActivityPdfListAdminBinding binding;

    //add TAG
    private static final String TAG = "PDF_LIST_TAG";

    //variables
    private String categoryId, categoryTitle;
    Context context;
    Activity activity;
    List<ModelPdf> listmodelpdf;

    public static ArrayList<ModelPdf> listbooks;
    String name;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View home=inflater.inflate(R.layout.fragment_home,container,false);
        //////////////// mapping
        homeViewPager = home.findViewById(R.id.home_view_pager);
        homeCircleIndicator = home.findViewById(R.id.home_circle_indicator);
        homeHorRecyclerView =home.findViewById(R.id.home_hor_recycler_view);
        homeVerRecyclerView=home.findViewById(R.id.home_ver_recycler_view);
        searchRestaurantText=home.findViewById(R.id.search_edit_text);
        home_thiendinh_recyclerview = home.findViewById(R.id.home_thiendinh_recyclerview);
        home_cooking_recyclerview = home.findViewById(R.id.home_cooking_recyclerview);

        context = this.getActivity();
        activity = this.getActivity();
        searchRestaurantText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i== EditorInfo.IME_ACTION_DONE)
                {
                    return true;
                }
                else {
                    return false;
                }
            }
        });

        /////////////// banner slider
        homeViewPager.setOffscreenPageLimit(3);
        homeViewPager.setClipToPadding(false);
        homeViewPager.setClipChildren(false);
        CompositePageTransformer compositePageTransformer=new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        homeViewPager.setPageTransformer(compositePageTransformer);
        banners = getBanners();
        homeBannerAdapter =new HomeBannerAdapter(banners);
        homeViewPager.setAdapter(homeBannerAdapter);
        homeCircleIndicator.setViewPager(homeViewPager);
        homeViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable,3000);

            }
        });
        ////////////// category recycler view
        homeCategories=new ArrayList<>();

        homeCategories.add(new Category(R.drawable.hot_deal,"Hot Deal"));
        homeCategories.add(new Category(R.drawable.rank,"Ranking"));
        homeCategories.add(new Category(R.drawable.new_logo,"New Books"));
        homeCategories.add(new Category(R.drawable.zen,"ZEN"));
        homeCategories.add(new Category(R.drawable.open_book,"Learning"));

        homeCategoryAdapter=new HomeCategoryAdapter(this,getActivity(),homeCategories);

        listmodelpdf = new ArrayList<>();
        categoryId = "1654416458010";

        loadPdfList();







        //List<Book> list= new ArrayList<>();

        //list.add(new Book(1));
        //list.add(new Book(1));
        //list.add(new Book(1));
        //list.add(new Book(1));
        //list.add(new Book(1));
        //list.add(new Book(1));
        //list.add(new Book(1));

        ////// products in Thien Dinh category
        //////home_thiendinh_recyclerview.setAdapter(homeBookAdapter);
        //////home_thiendinh_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        //////home_thiendinh_recyclerview.setHasFixedSize(true);
        //////home_thiendinh_recyclerview.setNestedScrollingEnabled(false);
        ////// products in Cookery category
        //////home_cooking_recyclerview.setAdapter(homeBookAdapter);
        //////home_cooking_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        //////home_cooking_recyclerview.setHasFixedSize(true);
        //////home_cooking_recyclerview.setNestedScrollingEnabled(false);

        homeHorRecyclerView.setAdapter(homeCategoryAdapter);
        homeHorRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        homeHorRecyclerView.setHasFixedSize(true);
        homeHorRecyclerView.setNestedScrollingEnabled(false);

        ////////////// product recycler view
        products =new ArrayList<>();

        homeProductAdapter=new HomeProductAdapter(getActivity(), products);
        homeVerRecyclerView.setAdapter(homeProductAdapter);
        homeVerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));



        return home;
    }

    ///////////// Images for banner in homepage
    private List<Banner> getBanners(){
        List<Banner> list=new ArrayList<>();

        list.add(new Banner((R.drawable.banner1)));
        list.add(new Banner((R.drawable.banner2)));
        list.add(new Banner((R.drawable.banner3)));
        list.add(new Banner((R.drawable.banner4)));
        list.add(new Banner((R.drawable.banner5)));
        list.add(new Banner((R.drawable.banner6)));
        list.add(new Banner((R.drawable.banner7)));
        list.add(new Banner((R.drawable.banner8)));
        return list;
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable,3000);
    }


    @Override
    public void callBack(int position, ArrayList<ModelPdf> list) {
        homeProductAdapter=new HomeProductAdapter(getContext(),list);
        homeProductAdapter.notifyDataSetChanged();
        homeVerRecyclerView.setAdapter(homeProductAdapter);
    }


    private void loadPdfList() {
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
                        listbooks = pdfArrayList;
                        HomeBookAdapter homeBookAdapter_ThienDinh = new HomeBookAdapter();
                        // Get Romantic-type books
                        ArrayList<ModelPdf> romantic_books =getModelPDFbyCateID(listbooks,"1655351774971");
                        homeBookAdapter_ThienDinh.setData(context,romantic_books);
                        home_thiendinh_recyclerview.setAdapter(homeBookAdapter_ThienDinh);
                        home_thiendinh_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
                        home_thiendinh_recyclerview.setHasFixedSize(true);
                        home_thiendinh_recyclerview.setNestedScrollingEnabled(false);
                        // Set data for cookery cate
                        ArrayList<ModelPdf> cookery_books =getModelPDFbyCateID(listbooks,"1655351761011");
                        HomeBookAdapter homeBookAdapter_Cookery = new HomeBookAdapter();
                        homeBookAdapter_Cookery.setData(context,cookery_books);
                        home_cooking_recyclerview.setAdapter(homeBookAdapter_Cookery);
                        home_cooking_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
                        home_cooking_recyclerview.setHasFixedSize(true);
                        home_cooking_recyclerview.setNestedScrollingEnabled(false);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public ArrayList<ModelPdf> getModelPDFbyCateID(ArrayList<ModelPdf> booklist,String id){
        ArrayList<ModelPdf> return_array= new ArrayList<>();
        for (int i=0;i< booklist.size();i++){
            if (booklist.get(i).getCategoryId().equals(id)){
                return_array.add(booklist.get(i));
            }
        }
        return return_array;
    }








}