package org.nonstdout.askme;

import org.nonstdout.askme.Question;
import org.nonstdout.askme.QuestionAsker;
import org.nonstdout.askme.QuestionSource;
import org.nonstdout.askme.QuestionPack;
// import org.nonstdout.askme.QuestionService;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;
import android.view.View;
import android.util.SparseBooleanArray;
import android.util.Log;
import android.content.Intent;
import android.speech.tts.TextToSpeech;

import java.util.ArrayList;
import java.util.Vector;

public class Askme
  extends ListActivity
{
  public static final String TAG = "Askme";

  private Button start_button_;
  private Button stop_button_;

  private QuestionAsker question_asker_;
  private QuestionSource question_source_;
  private Vector<QuestionPack> question_packs_;

  private ArrayAdapter<QuestionPack> list_adapter_;

  private Intent question_intent_;

  private Vector<QuestionPack> get_question_packs()
  {
    Vector<QuestionPack> question_packs;
    
    try
    {
      question_packs = question_source_.question_packs();
    }
    catch (java.io.IOException e)
    {
      Toast toast = Toast.makeText(
          this,
          "Unable to get list of question packs",
          Toast.LENGTH_LONG);
      toast.show();
      return new Vector<QuestionPack>();
    }

    return question_packs;
  }

  private static final int DATA_CHECK_CODE = 1;

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data)
  {
    if (requestCode == DATA_CHECK_CODE)
    {
      if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS)
      {
        run();
      }
      else
      {
        // missing data, install it
        Intent installIntent = new Intent();
        installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
        startActivity(installIntent);
      }
    }
  }

  private void run()
  {
    setContentView(R.layout.main);

    question_asker_ = new QuestionAsker(this);
    question_source_ = new QuestionSource(this);

    // question_intent_ = new Intent(this, QuestionService.class);

    question_packs_ = get_question_packs();

    list_adapter_ = new ArrayAdapter<QuestionPack>(
        this,
        android.R.layout.simple_list_item_multiple_choice,
        question_packs_);
    setListAdapter(list_adapter_);

    final ListView list_view = getListView();
    list_view.setItemsCanFocus(false);
    list_view.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

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
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    Intent intent = new Intent();
    intent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
    startActivityForResult(intent, DATA_CHECK_CODE);
  }

  @Override
  public void onDestroy()
  {
    super.onDestroy();
    question_asker_.shutdown();
  }

  private void start()
  {
    Vector<Question> questions = new Vector<Question>();

    for (int i = 0; i < question_packs_.size(); ++i)
    {
      if (!getListView().isItemChecked(i))
      {
        continue;
      }

      QuestionPack question_pack = question_packs_.get(i);

      try
      {
        questions.addAll(question_pack.get_questions());
      }
      catch (java.io.IOException e)
      {
        StringBuffer buf = new StringBuffer();
        buf.append("Unable to read question pack: ");
        buf.append(e.toString());

        Toast toast = Toast.makeText(
            this,
            buf.toString(),
            Toast.LENGTH_LONG);
        toast.show();
      }
    }

    question_asker_.use_questions(questions);

    question_asker_.start();
    // startService(intent);
  }

  private void stop()
  {
    question_asker_.stop();
  }
}
