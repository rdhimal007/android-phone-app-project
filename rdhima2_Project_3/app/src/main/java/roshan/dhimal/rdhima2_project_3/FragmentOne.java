package roshan.dhimal.rdhima2_project_3;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.fragment.app.Fragment;

public class FragmentOne extends Fragment {
    private OnLandmarkSelectedListener callback;

    public interface OnLandmarkSelectedListener {
        void onLandmarkSelected(String landmarkUrl);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (OnLandmarkSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnLandmarkSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        ListView listView = view.findViewById(R.id.listview_landmarks);
        String[] landmarks = getResources().getStringArray(R.array.landmarks);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, landmarks);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedLandmarkUrl = getLandmarkUrl(position);
            callback.onLandmarkSelected(selectedLandmarkUrl);
        });
        return view;
    }

    private String getLandmarkUrl(int position) {
        String[] landmarkUrls = new String[]{
                "https://www.msichicago.org", // URL for the Museum of Science and Industry
                "https://www.mlb.com/cubs/ballpark", // URL for Wrigley Field
                "https://www.lpzoo.org", // URL for Lincoln Park Zoo
                "https://www.willistower.com/", // Sears tower
                "https://360chicago.com/", // john hankcock tower
                "https://nbc-tower.com/", // NBC tower


        };
        return landmarkUrls[position];
    }

}
