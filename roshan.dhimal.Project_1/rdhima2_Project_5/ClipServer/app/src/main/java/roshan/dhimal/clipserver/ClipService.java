
package roshan.dhimal.clipserver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class ClipService extends Service {
    private static final String CHANNEL_ID = "MusicPlayerChannel";
    private static final int NOTIFICATION_ID = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        startForegroundService();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MusicServiceImpl();
    }

    private void createNotificationChannel() {
        CharSequence name = "Music player notification";
        String description = "The channel for music player notifications";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    private void startForegroundService() {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_media_play)
                .setContentTitle("Music Playing")
                .setContentText("Tap to return to the app")
                .setContentIntent(pendingIntent)
                .setTicker("Music is playing!")
                .setOngoing(true)
                .build();
        startForeground(NOTIFICATION_ID, notification);
    }

    class MusicServiceImpl extends IMyAidlInterface.Stub {
        private MediaPlayer mediaPlayer;

        @Override
        public void play(String songId) throws RemoteException {
            int resId = getSongResId(songId);
            mediaPlayer = MediaPlayer.create(ClipService.this, resId);
            if (mediaPlayer == null) {
                throw new RemoteException("Unable to load media.");
            }
            mediaPlayer.setOnCompletionListener(mp -> stopSelf());
            mediaPlayer.start();
        }

        @Override
        public void pause() throws RemoteException {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }
        }

        @Override
        public void stop() throws RemoteException {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        }

        @Override
        public void resume() throws RemoteException {
            if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
        }

        @Override
        public void stopService() throws RemoteException {
            stopSelf();
        }

        private int getSongResId(String songId) {
            switch (songId) {
                case "1": return R.raw.mfile1;
                case "2": return R.raw.mfile2;
                case "3": return R.raw.mfile3;
                case "4": return R.raw.mfile4;
                case "5": return R.raw.badnews;
                default: throw new IllegalArgumentException("Invalid song ID");
            }
        }
    }
}
