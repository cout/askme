package org.nonstdout.askme;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class Askme extends ListActivity
{
  // private ArrayList<String> strings_ = new ArrayList<String>();
  private ArrayAdapter<String> list_adapter_;
  private static final String[] strings_ = new String[] { "foo", "bar", "baz" };

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
  }
}
