package id19110100.hcmute.edu.reaboadmin.Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.barteksc.pdfviewer.PDFView;

import java.util.ArrayList;

import id19110100.hcmute.edu.reaboadmin.PdfDetailActivity;
import id19110100.hcmute.edu.reaboadmin.PdfEditActivity;
import id19110100.hcmute.edu.reaboadmin.Class.FilterPdfAdmin;
import id19110100.hcmute.edu.reaboadmin.Class.MyApplication;
import id19110100.hcmute.edu.reaboadmin.Model.ModelPdf;
import id19110100.hcmute.edu.reaboadmin.databinding.RowPdfAdminBinding;

public class AdapterPdfAdmin extends RecyclerView.Adapter<AdapterPdfAdmin.HolderPdfAdmin> implements Filterable {

    //context
    private Context context;
    //arraylist to hold list of data
    public ArrayList<ModelPdf> pdfArrayList, filterList;

    //view binding
    private RowPdfAdminBinding binding;

    //filter
    private FilterPdfAdmin filter;

    //progress
    private ProgressDialog progressDialog;

    //add TAG
    private static final String TAG = "PDF_ADAPTER_TAG";

    //constructor
    public AdapterPdfAdmin(Context context, ArrayList<ModelPdf> pdfArrayList) {
        this.context = context;
        this.pdfArrayList = pdfArrayList;
        this.filterList = pdfArrayList;

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @NonNull
    @Override
    public HolderPdfAdmin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //bind layout
        binding = RowPdfAdminBinding.inflate(LayoutInflater.from(context), parent, false);

        return new HolderPdfAdmin(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull HolderPdfAdmin holder, int position) {
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
        holder.moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                moreOptionsDialog(model, holder);
            }
        });
        //click to open book details
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PdfDetailActivity.class);
                intent.putExtra("bookId", pdfId);
                context.startActivity(intent);
            }
        });

    }

    private void moreOptionsDialog(ModelPdf model, HolderPdfAdmin holder) {
        //options to show in dialog
        String[] options = {"Edit", "Delete"};
        //variables
        String bookId = model.getId();
        String bookUrl = model.getUrl();
        String bookTitle = model.getTitle();
        //alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose Options")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //handle dialog
                        if(i==0){
                            //edit
                            //go to edit book activity
                            Intent intent  = new Intent(context, PdfEditActivity.class);
                            intent.putExtra("bookId", bookId);
                            context.startActivity(intent);
                        }
                        else if (i==1){
                            //delete
                            MyApplication.deleteBook(context,
                                    ""+bookId,
                                    ""+bookUrl,
                                    ""+bookTitle);
                        }
                    }
                })
                .show();
    }



    @Override
    public int getItemCount() {
        return pdfArrayList.size(); //return list size
    }

    //filter
    @Override
    public Filter getFilter() {

        if(filter == null){
            filter = new FilterPdfAdmin(filterList, this);

        }
        return filter;
    }

    //view holder for row item
    class HolderPdfAdmin extends RecyclerView.ViewHolder{

        //UI view
        PDFView pdfView;
        ProgressBar progressBar;
        TextView titleTv, descriptionTv, categoryTv, sizeTv, dateTv, licenseTv;
        ImageButton moreBtn;

        public HolderPdfAdmin(@NonNull View itemView) {
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
            moreBtn = binding.moreBtn;

        }
    }
}
