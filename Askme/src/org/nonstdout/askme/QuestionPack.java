package org.nonstdout.askme;

import org.nonstdout.askme.QuestionSource;

import java.util.Vector;

class QuestionPack
{
  public String name()
  {
    return name_;
  }

  public String path()
  {
    return path_;
  }

  QuestionPack(QuestionSource question_source, String path)
  {
    question_source_ = question_source;
    name_ = path;
    path_ = path;
  }

  private QuestionSource question_source_;
  private String name_;
  private String path_;

  public Vector<Question> get_questions()
    throws java.io.IOException
  {
    return question_source_.read_question_pack(this);
  }
}

