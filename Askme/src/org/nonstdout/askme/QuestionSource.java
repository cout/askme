package org.nonstdout.askme;

import org.nonstdout.askme.Question;
import org.nonstdout.askme.QuestionPack;

import android.content.res.AssetManager;
import android.content.Context;

import java.util.Vector;
import java.io.InputStream;

class QuestionSource
{
  public QuestionSource(Context context)
  {
    context_ = context;
  }

  public Vector<QuestionPack> question_packs()
    throws java.io.IOException
  {
    AssetManager am = context_.getResources().getAssets();
    String[] paths = am.list("questions");

    Vector<QuestionPack> question_packs = new Vector<QuestionPack>();

    for (String path: paths)
    {
      QuestionPack question_pack = new QuestionPack(this, path);
      question_packs.add(question_pack);
    }

    return question_packs;
  }

  public Vector<Question> read_question_pack(QuestionPack question_pack)
    throws java.io.IOException
  {
    AssetManager am = context_.getResources().getAssets();
    InputStream in = am.open(question_pack.path());
    return new Vector<Question>();
  }

  private Context context_;
}

