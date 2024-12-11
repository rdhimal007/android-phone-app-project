// IMyAidlInterface.aidl
package roshan.dhimal.clipserver;

interface IMyAidlInterface {
    void play(String songId);
    void pause();
    void stop();
    void resume();
    void stopService();
}
