package org.nonstdout.askme;

import org.nonstdout.askme.Question;
import org.nonstdout.askme.QuestionAsker;
import org.nonstdout.askme.QuestionSource;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;
import android.view.View;

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

  private Vector<QuestionPack> question_packs()
  {
    Vector<QuestionPack> question_packs;
    
    try
    {
      question_packs = question_source_.question_packs();
    }
    catch (java.io.IOException e)
    {
      Toast toast = new Toast(this);
      toast.setText("Unable to get list of question packs");
      toast.show();
      return new Vector<QuestionPack>();
    }

    return question_packs;
  }

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.main);

    question_asker_ = new QuestionAsker(this);
    question_source_ = new QuestionSource(this);

    ArrayAdapter<String> list_adapter = new ArrayAdapter<String>(this, R.layout.list_item);
    for(QuestionPack question_pack: question_packs())
    {
      list_adapter.add(question_pack.name());
    }
    setListAdapter(list_adapter);

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
  public void onDestroy()
  {
    super.onDestroy();
    question_asker_.shutdown();
  }

  private void start()
  {
    Vector<Question> questions = new Vector<Question>();
    questions.add(new Question("question1", "answer1"));
    questions.add(new Question("question2", "answer2"));
    questions.add(new Question("question3", "answer3"));
    question_asker_.start(questions);
  }

  private void stop()
  {
    question_asker_.stop();
  }
}
