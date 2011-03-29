package org.nonstdout.askme;

public class Question
{
  private String question_;
  private String answer_;

  public String question()
  {
    return question_;
  }

  public String answer()
  {
    return answer_;
  }

  public Question(String question, String answer)
  {
    question_ = question;
    answer_ = answer;

  }
}

