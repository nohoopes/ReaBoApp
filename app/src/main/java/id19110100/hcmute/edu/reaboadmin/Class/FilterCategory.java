package id19110100.hcmute.edu.reaboadmin.Class;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.Locale;

import id19110100.hcmute.edu.reaboadmin.Adapter.AdapterCategoryAd;
import id19110100.hcmute.edu.reaboadmin.Model.ModelCategoryAd;

public class FilterCategory extends Filter {
    //arraylist
    ArrayList<ModelCategoryAd> filterList;
    //adapter
    AdapterCategoryAd adapterCategoryAd;

    //constructor


    public FilterCategory(ArrayList<ModelCategoryAd> filterList, AdapterCategoryAd adapterCategoryAd) {
        this.filterList = filterList;
        this.adapterCategoryAd = adapterCategoryAd;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        //
        if(constraint != null && constraint.length()>0){
            //change to uppercase
            constraint = constraint.toString().toUpperCase();
            ArrayList<ModelCategoryAd> filterModel = new ArrayList<>();
            for(int i=0; i<filterList.size();i++){
                //validate
                if(filterList.get(i).getCategory().toUpperCase().contains(constraint)){
                    //add to filter model
                    filterModel.add(filterList.get(i));
                }
            }
            results.count = filterModel.size();
            results.values = filterModel;
        }
        else {
            results.count = filterList.size();
            results.values = filterList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        //apply filter change
        adapterCategoryAd.categoryAdArrayList = (ArrayList<ModelCategoryAd>)filterResults.values;

        //notify change
        adapterCategoryAd.notifyDataSetChanged();
    }
}
