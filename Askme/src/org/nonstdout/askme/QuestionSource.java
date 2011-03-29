package org.nonstdout.askme;

import org.nonstdout.askme.Question;
import org.nonstdout.askme.QuestionPack;

import android.content.res.AssetManager;
import android.content.Context;

import java.util.Vector;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

  private static final Pattern QUESTION_PATTERN = Pattern.compile(
      "(?i)^Question:\\s(.*)");

  private static final Pattern ANSWER_PATTERN = Pattern.compile(
      "(?i)^Answer:\\s(.*)");
 
  private static final Pattern COMMENT_PATTERN = Pattern.compile(
      "^(\\s*|#.*)$");

  public Vector<Question> read_question_pack(QuestionPack question_pack)
    throws java.io.IOException
  {
    AssetManager am = context_.getResources().getAssets();
    InputStream in = am.open(question_pack.path());
    InputStreamReader isr = new InputStreamReader(in);
    BufferedReader reader = new BufferedReader(isr);

    Vector<Question> questions = new Vector<Question>();

    StringBuffer current_question = new StringBuffer();
    StringBuffer current_answer = new StringBuffer();
    StringBuffer append_to = new StringBuffer();

    String line;
    while ((line = reader.readLine()) != null)
    {
      if (QUESTION_PATTERN.matcher(line).matches())
      {
        if (!current_question.equals(""))
        {
          Question question = new Question(
              current_question.toString(),
              current_answer.toString());
          questions.add(question);

          current_question = new StringBuffer(line);
          current_answer = new StringBuffer();
          append_to = current_question;
        }
      }
      else if (ANSWER_PATTERN.matcher(line).matches())
      {
        current_answer = new StringBuffer(line);
        append_to = current_answer;
      }
      else if (COMMENT_PATTERN.matcher(line).matches())
      {
        // ignore
      }
      else
      {
        append_to.append(line);
      }
    }

    return questions;
  }

  private Context context_;
}

