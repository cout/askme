package org.nonstdout.askme;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Locale;

public class Askme
  extends ListActivity
  implements TextToSpeech.OnInitListener
{
  public static final String TAG = "Askme";

  // private ArrayList<String> strings_ = new ArrayList<String>();

  private ArrayAdapter<String> list_adapter_;
  private static final String[] strings_ = new String[] { "foo", "bar", "baz" };

  private TextToSpeech tts_;

  private Button start_button_;
  private Button stop_button_;

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

  public void onInit(int status) {
    if (status == TextToSpeech.SUCCESS) {
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

  private void start() {
    tts_.speak("Hello", TextToSpeech.QUEUE_FLUSH, null);
  }

  private void stop() {
  }
}
