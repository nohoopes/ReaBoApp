package id19110100.hcmute.edu.reaboadmin.Class;

import android.widget.Filter;

import java.util.ArrayList;

import id19110100.hcmute.edu.reaboadmin.Adapter.AdapterPdfAdmin;
import id19110100.hcmute.edu.reaboadmin.Adapter.AdapterPdfUser;
import id19110100.hcmute.edu.reaboadmin.Model.ModelPdf;

public class FilterPdfUser extends Filter {
    ArrayList<ModelPdf> filterList;
    //adapter
    AdapterPdfUser adapterPdfUser;

    //constructor
    public FilterPdfUser(ArrayList<ModelPdf> filterList, AdapterPdfUser adapterPdfUser) {
        this.filterList = filterList;
        this.adapterPdfUser = adapterPdfUser;
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
        adapterPdfUser.pdfArrayList = (ArrayList<ModelPdf>)filterResults.values;

        //notify change
        adapterPdfUser.notifyDataSetChanged();
    }
}
