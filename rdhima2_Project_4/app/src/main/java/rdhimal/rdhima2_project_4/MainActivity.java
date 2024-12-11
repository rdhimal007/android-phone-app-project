package rdhimal.rdhima2_project_4;
//MainActivity.java

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissionsAndStartGame();
    }

    private void checkPermissionsAndStartGame() {
        if (ContextCompat.checkSelfPermission(this, "rdhimal.rdhima2_project_4.GAME_PLAYER") != PackageManager.PERMISSION_GRANTED) {
            Log.d("MainActivity", "Requesting permission.");
            ActivityCompat.requestPermissions(this, new String[]{"rdhimal.rdhima2_project_4.GAME_PLAYER"}, PERMISSION_REQUEST_CODE);
        } else {
            Log.d("MainActivity", "Permission already granted. Starting game.");
            startGame();
        }
    }

    private void startGame() {
        Log.d("MainActivity", "Starting GameActivity.");
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("MainActivity", "Permission granted by user. Starting game.");
                startGame();
            } else {
                Log.d("MainActivity", "Permission denied by user.");
                Toast.makeText(this, "Permission denied. Cannot start the game.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
