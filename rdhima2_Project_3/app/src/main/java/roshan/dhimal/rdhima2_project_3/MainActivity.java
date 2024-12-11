package roshan.dhimal.rdhima2_project_3;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity implements FragmentOne.OnLandmarkSelectedListener {

    private boolean isLandscape = false;
    private boolean isFragmentTwoShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new FragmentOne())
                    .commit();
        }
    }

    @Override
    public void onLandmarkSelected(String landmarkUrl) {
        FragmentTwo newFragment = FragmentTwo.newInstance(landmarkUrl);
        getSupportFragmentManager().beginTransaction();

        if (isLandscape) {
            if (!isFragmentTwoShown) {
                // Landscape mode and FragmentTwo is not yet shown
                ViewGroup.LayoutParams lp = findViewById(R.id.fragment_container).getLayoutParams();
                lp.width = getResources().getDisplayMetrics().widthPixels / 3;
                findViewById(R.id.fragment_container).setLayoutParams(lp);
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, newFragment).commit();
                isFragmentTwoShown = true;
            } else {
                // Landscape mode and FragmentTwo is already shown, replace it
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, newFragment).commit();
            }
        } else {
            // Portrait mode, replace FragmentOne with FragmentTwo
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, newFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_exit) {
            finish();
            return true;
        } else if (itemId == R.id.action_launch_a2) {
            Intent intent = new Intent(this, ApplicationA2MainActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (isFragmentTwoShown) {
            // If FragmentTwo is shown in landscape mode, go back to initial configuration
            ViewGroup.LayoutParams lp = findViewById(R.id.fragment_container).getLayoutParams();
            lp.width = getResources().getDisplayMetrics().widthPixels;
            findViewById(R.id.fragment_container).setLayoutParams(lp);
            isFragmentTwoShown = false;
        } else {
            super.onBackPressed();
        }
    }
}
