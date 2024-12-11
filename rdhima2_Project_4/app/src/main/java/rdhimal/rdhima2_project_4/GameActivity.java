package rdhimal.rdhima2_project_4;
////GameActivity.java


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private TableLayout player1Table;
    private TableLayout player2Table;
    private Button start_button;
    private TextView statusText;

    private Button stop_button;
    private PlayerThread player1Thread;
    private PlayerThread player2Thread;
    private Handler uiHandler;
    private Map<Pair<Integer, Integer>, Integer> guessCount1 = new HashMap<>();
    private Map<Pair<Integer, Integer>, Integer> guessCount2 = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        player1Table = findViewById(R.id.player1_table);
        player2Table = findViewById(R.id.player2_table);
        start_button = findViewById(R.id.start_button);
        stop_button = findViewById(R.id.stop_button);
        statusText = findViewById(R.id.game_status_text);

        setupTables();
        uiHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Bundle data = msg.getData();
                int playerId = data.getInt("playerId");
                Pair<Integer, Integer> guess = new Pair<>(data.getInt("guessX"), data.getInt("guessY"));
                GuessResult result = (GuessResult) data.getSerializable("result");
                updateTable(playerId, guess, result);
                if (result == GuessResult.SUCCESS) {
                    showGopherLocation(guess);
                }
            }
        };
        start_button.setOnClickListener(v -> startGame());
        stop_button.setOnClickListener(v -> stopGame());
    }
    private void startGameUpdates() {
        uiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                statusText.setText("Game's running... " + System.currentTimeMillis());
                uiHandler.postDelayed(this, 1000);  // updates status every second
            }
        }, 1000);  // Initial delay of 1 second before the first update
    }

    private void setupTables() {
        initializeTable(player1Table);
        initializeTable(player2Table);
    }

    private void initializeTable(TableLayout table) {
        table.removeAllViews();  // Ensure the table is empty before initialization
        for (int i = 0; i < 10; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            for (int j = 0; j < 10; j++) {
                TextView textView = new TextView(this);
                textView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                textView.setGravity(Gravity.CENTER);
                textView.setPadding(8, 8, 8, 8);
                textView.setText("-");
                row.addView(textView);
            }
            table.addView(row);
        }
        // Log the initial setup
        logTableStructure(table, "Initial Setup");
    }
    private void logTableStructure(TableLayout table, String stage) {
        Log.d("GameActivity", stage + " - Total Rows: " + table.getChildCount());
        for (int i = 0; i < table.getChildCount(); i++) {
            TableRow row = (TableRow) table.getChildAt(i);
            Log.d("GameActivity", "Row " + i + " - Total Columns: " + row.getChildCount());
        }
    }

    private void startGame() {
        Pair<Integer, Integer> gopherLocation = getRandomLocation();
        // Player 1 starts with a random guess
        Pair<Integer, Integer> player1StartGuess = new Pair<>(new Random().nextInt(10), new Random().nextInt(10));
        // Player 2 starts with a fixed location, for example, always starting from (0,0)
        Pair<Integer, Integer> player2StartGuess = new Pair<>(0, 0);

        player1Thread = new PlayerThread(1, gopherLocation, uiHandler, player1StartGuess);
        player2Thread = new PlayerThread(2, gopherLocation, uiHandler, player2StartGuess);

        player1Thread.start();
        player2Thread.start();
        startGameUpdates(); // Start updating the game status periodically
    }

    private void stopGame() {
        if (player1Thread != null) {
            player1Thread.interrupt();
        }
        if (player2Thread != null) {
            player2Thread.interrupt();
        }
        uiHandler.removeCallbacksAndMessages(null); // Remove any pending posts of Runnable or messages

    }

    private Pair<Integer, Integer> getRandomLocation() {
        Random random = new Random();
        int x = random.nextInt(10);  // Ensure it ranges from 0 to 9
        int y = random.nextInt(10);  // Ensure it ranges from 0 to 9
        return new Pair<>(x, y);
    }

    private void updateTable(int playerId, Pair<Integer, Integer> guess, GuessResult result) {
        TableLayout table = (playerId == 1) ? player1Table : player2Table;
        TableRow row = (TableRow) table.getChildAt(guess.first);  // Ensure 'first' refers to row index
        TextView cell = (TextView) row.getChildAt(guess.second);  // Ensure 'second' refers to column index
        Map<Pair<Integer, Integer>, Integer> guessCount = (playerId == 1) ? guessCount1 : guessCount2;

        int count = guessCount.getOrDefault(guess, 0) + 1;
        guessCount.put(guess, count);
        cell.setText(count + ":" + formatGuessResult(result));
        if (result == GuessResult.SUCCESS) {
            showGopherLocation(guess);
            showWinnerMessage(playerId);
            stopGame();
        }
        // Log table structure after update
        logTableStructure(table, "After Update");
    }
    private void showWinnerMessage(int playerId) {
        String winnerMessage = "Player " + playerId + " wins!";
        statusText.setText("Player " + playerId + " wins!");

        Toast.makeText(this, winnerMessage, Toast.LENGTH_LONG).show();

        // Highlight the winning player's table
        if (playerId == 1) {
            player1Table.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        } else {
            player2Table.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        }
    }

    private void showGopherLocation(Pair<Integer, Integer> location) {
        updateCell(player1Table, location, "G");
        updateCell(player2Table, location, "G");
    }

    private void updateCell(TableLayout table, Pair<Integer, Integer> location, String text) {
        TableRow row = (TableRow) table.getChildAt(location.first);
        TextView cell = (TextView) row.getChildAt(location.second);
        cell.setText(text);
    }

    private String formatGuessResult(GuessResult result) {
        switch (result) {
            case SUCCESS: return "G";
            case NEAR_MISS: return "N";
            case CLOSE_GUESS: return "C";
            default: return "M";
        }
    }

    enum GuessResult { SUCCESS, NEAR_MISS, CLOSE_GUESS, COMPLETE_MISS }
}

