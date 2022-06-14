package id19110100.hcmute.edu.reaboadmin.Class;

import android.widget.Filter;

import java.util.ArrayList;

import id19110100.hcmute.edu.reaboadmin.Adapter.AdapterCategoryAd;
import id19110100.hcmute.edu.reaboadmin.Adapter.AdapterPdfAdmin;
import id19110100.hcmute.edu.reaboadmin.Model.ModelCategoryAd;
import id19110100.hcmute.edu.reaboadmin.Model.ModelPdf;

public class FilterPdfAdmin extends Filter {
    //arraylist
    ArrayList<ModelPdf> filterList;
    //adapter
    AdapterPdfAdmin adapterPdfAdmin;

    //constructor


    public FilterPdfAdmin(ArrayList<ModelPdf> filterList, AdapterPdfAdmin adapterPdfAdmin) {
        this.filterList = filterList;
        this.adapterPdfAdmin = adapterPdfAdmin;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        //
        if(constraint != null && constraint.length()>0){
            //change to uppercase
            constraint = constraint.toString().toUpperCase();
            ArrayList<ModelPdf> filterModel = new ArrayList<>();
            for(int i=0; i<filterList.size();i++){
                //validate
                if(filterList.get(i).getTitle().toUpperCase().contains(constraint)){
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
        adapterPdfAdmin.pdfArrayList = (ArrayList<ModelPdf>)filterResults.values;

        //notify change
        adapterPdfAdmin.notifyDataSetChanged();
    }
}
