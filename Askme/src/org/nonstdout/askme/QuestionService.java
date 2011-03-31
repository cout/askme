package org.nonstdout.askme;

import android.widget.Toast;
import android.content.Intent;
import android.app.Service;
import android.os.IBinder;
import android.os.Message;
import android.os.Looper;
import android.os.Process;
import android.os.Handler;
import android.os.HandlerThread;

import org.nonstdout.askme.Question;

class QuestionService
  extends Service
{
  public final class ServiceHandler extends Handler
  {
    public ServiceHandler(Looper looper)
    {
      super(looper);
    }

    @Override
    public void handleMessage(Message msg)
    {
      asker_.start();
    }
  }

  @Override
  public void onCreate()
  {
    HandlerThread thread = new HandlerThread(
        "ServiceStartArguments",
        Process.THREAD_PRIORITY_BACKGROUND);
    thread.start();

    service_looper_ = thread.getLooper();
    service_handler_ = new ServiceHandler(service_looper_);
  }

  @Override
  public IBinder onBind(Intent intent)
  {
    return null;
  }

  @Override
  public void onDestroy()
  {
    Toast.makeText(this, "done asking questions", Toast.LENGTH_SHORT).show();
  }

  private QuestionAsker asker_;
  private Looper service_looper_;
  private ServiceHandler service_handler_;
}
