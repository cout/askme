/*
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.content.Service;

import org.nonstdout.askme.Question;

public class QuestionService
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

  public QuestionService(QuestionAsker asker)
  {
    super("QuestionService");

    context_ = context;
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
  public int onStartCommand(Intent intent, int flags, int start_id)
  {
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
}
*/
