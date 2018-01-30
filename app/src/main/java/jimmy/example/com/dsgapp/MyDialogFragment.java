package jimmy.example.com.dsgapp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyDialogFragment extends DialogFragment {
    @BindView(R.id.address) TextView address;
    @BindView(R.id.ratingBar) RatingBar ratingBar;
    @BindView(R.id.imageView) ImageView imageView;
    @BindView(R.id.distance) TextView distance;
    private Unbinder unbinder;

    public MyDialogFragment() {
        // Required empty public constructor
    }

    public static MyDialogFragment newInstance(Bundle bundle) {
        MyDialogFragment fragment = new MyDialogFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_dialog, container, false);
        unbinder = ButterKnife.bind(this, view);
        getDialog().setTitle("DICK\\u0027S Sporting Goods");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String url = bundle.getString("url", "");
        if (url != "") {
            Picasso.with(getActivity())
                    .load(url)
                    .into(imageView);
        }
        ratingBar.setRating((float)bundle.getDouble("rating") - 4);
        address.setText(bundle.getString("address") + "\n" + bundle.getString("zipcode") + "\n" + bundle.getString("city") + "\n");
        distance.setText(bundle.getString("distance"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
