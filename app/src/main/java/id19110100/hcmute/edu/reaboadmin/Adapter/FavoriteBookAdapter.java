package id19110100.hcmute.edu.reaboadmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import id19110100.hcmute.edu.reaboadmin.R;

public class FavoriteBookAdapter extends BaseAdapter {

    private Context context;
    private int layout;

    public FavoriteBookAdapter(Context context, int layout) {
        this.context = context;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return 8;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        LinearLayout background;
        TextView name;
        TextView btn_read;
        ImageView img;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
        } else{
            holder = (ViewHolder) view.getTag();
        }

        holder = new ViewHolder();

        holder.background = (LinearLayout) view.findViewById(R.id.favorite_book_layout);
        holder.img = (ImageView) view.findViewById(R.id.favorite_book_img);
        holder.name = (TextView) view.findViewById(R.id.favorite_book_name);
        holder.btn_read =(TextView) view.findViewById(R.id.btn_favorite_read_now);
        holder.btn_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Read book", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
