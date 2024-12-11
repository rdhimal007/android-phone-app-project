package rdhimal.rdhima2_project_4;
//PlayerThread.java

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Pair;
import java.util.Random;

public class PlayerThread extends Thread {
    private int playerId;
    private Pair<Integer, Integer> gopherLocation;
    private Handler uiHandler;
    private Handler threadHandler;
    private Pair<Integer, Integer> initialGuess;

    public PlayerThread(int playerId, Pair<Integer, Integer> gopherLocation, Handler uiHandler, Pair<Integer, Integer> initialGuess) {
        this.playerId = playerId;
        this.gopherLocation = gopherLocation;
        this.uiHandler = uiHandler;
        this.initialGuess = initialGuess;
    }

    @Override
    public void run() {
        Looper.prepare();
        threadHandler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) { // Assuming 1 represents a stop command
                    Looper.myLooper().quitSafely();
                }
            }
        };

        makeGuessesContinuously();
        Looper.loop();
    }

    private void makeGuessesContinuously() {
        Pair<Integer, Integer> currentGuess = initialGuess;
        while (!Thread.currentThread().isInterrupted()) {
            GameActivity.GuessResult result = evaluateGuess(currentGuess);
            sendGuessResult(currentGuess, result);
            if (result == GameActivity.GuessResult.SUCCESS) {
                break;
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            currentGuess = makeGuess();
        }
    }

    private Pair<Integer, Integer> makeGuess() {
        Random random = new Random();
        int x = random.nextInt(10);
        int y = random.nextInt(10);
        return new Pair<>(x, y);
    }

    private GameActivity.GuessResult evaluateGuess(Pair<Integer, Integer> guess) {
        int dx = Math.abs(guess.first - gopherLocation.first);
        int dy = Math.abs(guess.second - gopherLocation.second);

        if (dx == 0 && dy == 0) {
            return GameActivity.GuessResult.SUCCESS;
        } else if (dx <= 1 && dy <= 1) {
            return GameActivity.GuessResult.NEAR_MISS;
        } else if (dx <= 2 && dy <= 2) {
            return GameActivity.GuessResult.CLOSE_GUESS;
        } else {
            return GameActivity.GuessResult.COMPLETE_MISS;
        }
    }

    private void sendGuessResult(Pair<Integer, Integer> guess, GameActivity.GuessResult result) {
        Message msg = Message.obtain();
        Bundle data = new Bundle();
        data.putInt("playerId", playerId);
        data.putInt("guessX", guess.first);
        data.putInt("guessY", guess.second);
        data.putSerializable("result", result);
        msg.setData(data);
        uiHandler.sendMessage(msg);
    }
}
