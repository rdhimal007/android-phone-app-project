package roshan.dhimal.rdhima2_project_3; // Use your package name

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.HorizontalScrollView;
import androidx.appcompat.app.AppCompatActivity;

public class ApplicationA2MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a2_main);

        HorizontalScrollView scrollView = findViewById(R.id.horizontal_scroll_view);
        LinearLayout container = findViewById(R.id.landmarksContainer);

        // Array of drawable resources
        int[] imageResources = new int[]{
                R.drawable.landmark_1,
                R.drawable.landmark_2,
                R.drawable.landmark_3,
                R.drawable.landmark_4,
                R.drawable.landmark_5,
                R.drawable.landmark_6,
        };

        // Dynamically add each image to the LinearLayout
        for (int resId : imageResources) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(resId);
            imageView.setAdjustViewBounds(true); // Preserve aspect ratio
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT // Use MATCH_PARENT to fill the entire height of the parent
            );
            layoutParams.setMargins(10, 10, 10, 10); // Set margins (in pixels or use dp conversion)
            imageView.setLayoutParams(layoutParams);

            container.addView(imageView);
        }
    }
}
