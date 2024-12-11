// IMyAidlInterface.aidl
package course.examples.Services.MusicService;

// Declare any non-default types here with import statements

interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
      void play(String songName);
      void pause();
      void stop();
      void resume();
      void stopService();
}