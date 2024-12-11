package course.examples.Services.MusicService;


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




public class MusicService extends Service {
	private static String CHANNEL_ID = "Music player style" ;
	private static final int NOTIFICATION_ID = 1;
	Notification notification;

	@Override
	public void onCreate() {
		super.onCreate();
		createNotifications();
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return new MusicServiceImpl();
	}

	private void createNotifications(){
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			CharSequence name = "Music player notification";
			String description = "The channel for music player notifications";
			int importance = NotificationManager.IMPORTANCE_DEFAULT;
			NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
			channel.setDescription(description);
			// Register the channel with the system; you can't change the importance
			// or other notification behaviors after this
			NotificationManager notificationManager = getSystemService(NotificationManager.class);
			notificationManager.createNotificationChannel(channel);
		}

		final Intent notificationIntent = new Intent(getApplicationContext(),
				MusicService.class);

		final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				notificationIntent, 0) ;

		notification =
				new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
						.setSmallIcon(android.R.drawable.ic_media_play)
						.setOngoing(true).setContentTitle("Music Playing")
						.setContentText("Click to Access Music Player")
						.setTicker("Music is playing!")
						.setFullScreenIntent(pendingIntent, false)
						.build();
		startForeground(1,notification);

	}

	public class MusicServiceImpl extends IMyAidlInterface.Stub{

		MediaPlayer mediaPlayer = null;

		@Override
		public void play(String name) throws RemoteException {
			if(mediaPlayer != null)
				stop();
			mediaPlayer = MediaPlayer.create(getApplicationContext(),getSong(name));
			mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mediaPlayer) {
					Intent intent = new Intent("unbind");
					sendBroadcast(intent);
				}
			});

			mediaPlayer.start();
		}

		@Override
		public void pause() throws RemoteException {
			mediaPlayer.pause();
		}

		@Override
		public void stop() throws RemoteException {
			mediaPlayer.stop();
		}

		@Override
		public void resume() throws RemoteException {
			mediaPlayer.start();
		}

		@Override
		public void stopService() throws RemoteException {
			stopSelf();
		}

		private int getSong(String req){
			switch (req){
				case "1": return R.raw.mfile1;
				case "2": return R.raw.mfile2;
				case "3": return R.raw.mfile3;
				case "4": return R.raw.mfile4;
				case "5": return R.raw.badnews;
			}
			throw new UnsupportedOperationException("Illegal state");
		}
	}
}

