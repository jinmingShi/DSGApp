package jimmy.example.com.dsgapp.presenter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Matrix;
import android.location.Location;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import jimmy.example.com.dsgapp.MainActivity;
import jimmy.example.com.dsgapp.MyDialogFragment;
import jimmy.example.com.dsgapp.R;
import jimmy.example.com.dsgapp.model.VenuesItem;

/**
 * Created by Jinming on 1/26/18.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<VenuesItem> list;
    private Unbinder unbinder;
    private RecyclerView recyclerView;

    private Context context;
    private Location location;

    public MyAdapter(Context context, List<VenuesItem> list, Location location) {
        this.context = context;
        this.list = list;
        this.location = location;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        VenuesItem item = list.get(position);
        holder.name.setText(item.getName());
        holder.location.setText(item.getLocation().getAddress() + "\n" + item.getLocation().getCity() + "\n" + item.getLocation().getState());
        Location t = new Location("");
        t.setLatitude(item.getLocation().getLatitude());
        t.setLongitude(item.getLocation().getLongitude());
        holder.distance.setText("Distance: " + (int) location.distanceTo(t) / 1000 + "km");
        if (item.getPhotos() != null && !item.getPhotos().isEmpty()) {
            String url = item.getPhotos().get(0).getUrl();
            Picasso.with(context)
                    .load(url)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(holder.imageView);
        } else {
            holder.imageView.setImageResource(R.drawable.ic_launcher_foreground);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * It used to get reference to the RecyclerView on which this adapter is attached;
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.name) TextView name;
        @BindView(R.id.location) TextView location;
        @BindView(R.id.photo) ImageView imageView;
        @BindView(R.id.radioBtn) RadioButton radioButton;
        @BindView(R.id.distance) TextView distance;

        MyViewHolder(View view) {
            super(view);
            unbinder = ButterKnife.bind(this, view);
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @OnClick({R.id.radioBtn, R.id.right_side})
        @Override
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.radioBtn:
                    if (getAdapterPosition() != 0) {
                        AlertDialog.Builder builder;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                        } else {
                            builder = new AlertDialog.Builder(context);
                        }
                        builder.setTitle("Change Store?")
                                .setMessage("Are you sure you want to change your favourite store?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        radioButton.setChecked(true);
                                        ((RadioButton) MyAdapter.this.recyclerView.getChildAt(0).findViewById(R.id.radioBtn)).setChecked(false);
                                        Log.d("TAG", "onClick: " + getAdapterPosition());
                                        VenuesItem item = list.remove(getAdapterPosition());
                                        list.add(0, item);
                                        //notifyDataSetChanged();
                                        notifyItemInserted(0);
                                        notifyItemRemoved(getAdapterPosition());
                                    }
                                })
                                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                    /**
                                     * This method will be invoked when a button in the dialog is clicked.
                                     *
                                     * @param dialog the dialog that received the click
                                     * @param which  the button that was clicked (ex.
                                     *               {@link DialogInterface#BUTTON_POSITIVE}) or the position
                                     */
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ((RadioButton) v).setChecked(false);
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                    break;
                case R.id.right_side:
                    Log.i("hello", "onClick: right side");
                    FragmentManager fragmentManager = ((MainActivity)context).getSupportFragmentManager();
                    VenuesItem item = list.get(getAdapterPosition());
                    Bundle bundle = new Bundle();
                    String url = "";
                    if (item.getPhotos() != null && !item.getPhotos().isEmpty()) {
                        url = item.getPhotos().get(0).getUrl();
                    }
                    //bundle.putString("image", item.getPhotos().get(0).getUrl());
                    bundle.putString("url", url);
                    bundle.putString("address", item.getLocation().getAddress());
                    bundle.putString("zipcode", item.getLocation().getPostalCode());
                    bundle.putString("city", item.getLocation().getCity());
                    bundle.putDouble("rating", item.getRating());
                    String distance = ((TextView) MyAdapter.this.recyclerView.getChildAt(getAdapterPosition()).findViewById(R.id.distance)).getText().toString();
                    bundle.putString("distance", distance);
                    MyDialogFragment fragment = MyDialogFragment.newInstance(bundle);
                    fragment.show(fragmentManager, "details");
                    break;
            }
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        unbinder.unbind();
    }
}
