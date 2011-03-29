package org.nonstdout.askme;

import org.nonstdout.askme.Question;
import org.nonstdout.askme.QuestionAsker;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View;

import java.util.ArrayList;
import java.util.Vector;

public class Askme
  extends ListActivity
{
  public static final String TAG = "Askme";

  // private ArrayList<String> strings_ = new ArrayList<String>();

  private ArrayAdapter<String> list_adapter_;
  private static final String[] strings_ = new String[] { "foo", "bar", "baz" };

  private Button start_button_;
  private Button stop_button_;

  private QuestionAsker asker_;

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

    asker_ = new QuestionAsker(this);

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
    asker_.shutdown();
  }

  private void start()
  {
    Vector<Question> questions = new Vector<Question>();
    questions.add(new Question("question1", "answer1"));
    questions.add(new Question("question2", "answer2"));
    questions.add(new Question("question3", "answer3"));
    asker_.start(questions);
  }

  private void stop()
  {
    asker_.stop();
  }
}
