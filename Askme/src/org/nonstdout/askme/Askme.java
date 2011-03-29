package org.nonstdout.askme;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class Askme
  extends ListActivity
  implements TextToSpeech.OnInitListener,
             TextToSpeech.OnUtteranceCompletedListener
{
  public static final String TAG = "Askme";

  // private ArrayList<String> strings_ = new ArrayList<String>();

  private ArrayAdapter<String> list_adapter_;
  private static final String[] strings_ = new String[] { "foo", "bar", "baz" };

  private TextToSpeech tts_;

  private Button start_button_;
  private Button stop_button_;

  private Handler handler_ = new Handler();

  private String speech_text_;
  private String speech_id_;

  private Runnable speech_task_ = new Runnable() {
    public void run()
    {
      Log.e(TAG, speech_id_);
      Log.e(TAG, speech_text_);
      HashMap<String, String> params = new HashMap<String, String>();
      params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, speech_id_);
      tts_.speak(
          speech_text_,
          TextToSpeech.QUEUE_FLUSH,
          params);
    }
  };

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.main);

    list_adapter_ = new ArrayAdapter<String>(this, R.layout.list_item, strings_);
    setListAdapter(list_adapter_);

    final ListView list_view = getListView();
    list_view.setItemsCanFocus(false);
    list_view.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

    tts_ = new TextToSpeech(this, this);
    int result = tts_.setOnUtteranceCompletedListener(this);
    if (result != TextToSpeech.SUCCESS)
    {
      Log.e(TAG, "Could not set utterance completed listener");
    }

    start_button_ = (Button) findViewById(R.id.button_start);
    start_button_.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        start();
      }
    });

    stop_button_ = (Button) findViewById(R.id.button_stop);
    stop_button_.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        stop();
      }
    });
  }

  @Override
  public void onDestroy()
  {
    super.onDestroy();
    tts_.shutdown();
  }

  @Override
  public void onInit(int status)
  {
    if (status == TextToSpeech.SUCCESS)
    {
      int result = tts_.setLanguage(Locale.US);
      if (result == TextToSpeech.LANG_MISSING_DATA ||
          result == TextToSpeech.LANG_NOT_SUPPORTED)
      {
        Log.e(TAG, "Language is not available.");
      }
      else
      {
        // start_button_.setEnabled(true);
      }
    }
    else
    {
      Log.e(TAG, "Could not initialize TextToSpeech.");
    }
  }

  @Override
  public void onUtteranceCompleted(String utterance_id)
  {
    Log.e(TAG, "utterance complete");
    speak("Hello again", 3000, "HELLO_AGAIN");
  }

  private void speak(String str, long delay, String utterance_id)
  {
    speech_text_ = str;
    speech_id_ = utterance_id;
    handler_.removeCallbacks(speech_task_);
    handler_.postDelayed(speech_task_, delay);
  }

  private void start()
  {
    speak("Hello", 0, "HELLO");
  }

  private void stop()
  {
    tts_.stop();
    handler_.removeCallbacks(speech_task_);
  }
}
