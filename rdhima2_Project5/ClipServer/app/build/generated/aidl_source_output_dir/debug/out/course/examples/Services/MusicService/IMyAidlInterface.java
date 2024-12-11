/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package course.examples.Services.MusicService;
// Declare any non-default types here with import statements
public interface IMyAidlInterface extends android.os.IInterface
{
  /** Default implementation for IMyAidlInterface. */
  public static class Default implements course.examples.Services.MusicService.IMyAidlInterface
  {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    @Override public void play(java.lang.String songName) throws android.os.RemoteException
    {
    }
    @Override public void pause() throws android.os.RemoteException
    {
    }
    @Override public void stop() throws android.os.RemoteException
    {
    }
    @Override public void resume() throws android.os.RemoteException
    {
    }
    @Override public void stopService() throws android.os.RemoteException
    {
    }
    @Override
    public android.os.IBinder asBinder() {
      return null;
    }
  }
  /** Local-side IPC implementation stub class. */
  public static abstract class Stub extends android.os.Binder implements course.examples.Services.MusicService.IMyAidlInterface
  {
    /** Construct the stub at attach it to the interface. */
    public Stub()
    {
      this.attachInterface(this, DESCRIPTOR);
    }
    /**
     * Cast an IBinder object into an course.examples.Services.MusicService.IMyAidlInterface interface,
     * generating a proxy if needed.
     */
    public static course.examples.Services.MusicService.IMyAidlInterface asInterface(android.os.IBinder obj)
    {
      if ((obj==null)) {
        return null;
      }
      android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (((iin!=null)&&(iin instanceof course.examples.Services.MusicService.IMyAidlInterface))) {
        return ((course.examples.Services.MusicService.IMyAidlInterface)iin);
      }
      return new course.examples.Services.MusicService.IMyAidlInterface.Stub.Proxy(obj);
    }
    @Override public android.os.IBinder asBinder()
    {
      return this;
    }
    @Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
    {
      java.lang.String descriptor = DESCRIPTOR;
      if (code >= android.os.IBinder.FIRST_CALL_TRANSACTION && code <= android.os.IBinder.LAST_CALL_TRANSACTION) {
        data.enforceInterface(descriptor);
      }
      switch (code)
      {
        case INTERFACE_TRANSACTION:
        {
          reply.writeString(descriptor);
          return true;
        }
      }
      switch (code)
      {
        case TRANSACTION_play:
        {
          java.lang.String _arg0;
          _arg0 = data.readString();
          this.play(_arg0);
          reply.writeNoException();
          break;
        }
        case TRANSACTION_pause:
        {
          this.pause();
          reply.writeNoException();
          break;
        }
        case TRANSACTION_stop:
        {
          this.stop();
          reply.writeNoException();
          break;
        }
        case TRANSACTION_resume:
        {
          this.resume();
          reply.writeNoException();
          break;
        }
        case TRANSACTION_stopService:
        {
          this.stopService();
          reply.writeNoException();
          break;
        }
        default:
        {
          return super.onTransact(code, data, reply, flags);
        }
      }
      return true;
    }
    private static class Proxy implements course.examples.Services.MusicService.IMyAidlInterface
    {
      private android.os.IBinder mRemote;
      Proxy(android.os.IBinder remote)
      {
        mRemote = remote;
      }
      @Override public android.os.IBinder asBinder()
      {
        return mRemote;
      }
      public java.lang.String getInterfaceDescriptor()
      {
        return DESCRIPTOR;
      }
      /**
       * Demonstrates some basic types that you can use as parameters
       * and return values in AIDL.
       */
      @Override public void play(java.lang.String songName) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(songName);
          boolean _status = mRemote.transact(Stub.TRANSACTION_play, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void pause() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_pause, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void stop() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_stop, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void resume() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_resume, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void stopService() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_stopService, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }
    static final int TRANSACTION_play = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_pause = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
    static final int TRANSACTION_stop = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
    static final int TRANSACTION_resume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
    static final int TRANSACTION_stopService = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
  }
  public static final java.lang.String DESCRIPTOR = "course.examples.Services.MusicService.IMyAidlInterface";
  /**
   * Demonstrates some basic types that you can use as parameters
   * and return values in AIDL.
   */
  public void play(java.lang.String songName) throws android.os.RemoteException;
  public void pause() throws android.os.RemoteException;
  public void stop() throws android.os.RemoteException;
  public void resume() throws android.os.RemoteException;
  public void stopService() throws android.os.RemoteException;
}
