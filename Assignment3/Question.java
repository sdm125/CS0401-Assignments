import java.util.ArrayList;
import java.util.Scanner;


public class Question {

	String question;
	String[] answerList;
	int answerListLength;
	int answer;
	int timesTried;
	int timesCorrect;
	private static int line = 0;

	public Question(ArrayList<String> questionText){
		question = questionText.get(this.line);
		answerListLength = moveLineNum(questionText);
		answerList = makeAnswerList(answerListLength, questionText);
		answer = moveLineNum(questionText);
		timesTried = moveLineNum(questionText);
		timesCorrect = moveLineNum(questionText);
		this.line = this.line + 1;
	}

	/*
		Create answer list by @param answerListLength taken from current line in questionList.
		Add lines to answerList array.
	*/ 
	private String[] makeAnswerList(int answerListLength, ArrayList<String> questionList){
		String[] answerList = new String[answerListLength];
		for(int i = 0; i < answerListLength; i++){
			answerList[i] = questionList.get(++this.line);
		}
		return answerList;
	}

	// Move to the next line in @param text ArrayList. Return int.
	private int moveLineNum(ArrayList<String> text){
		return Integer.parseInt(text.get(++this.line));
	}

	// Return question.
	public String getQuestion(){
		return question;
	}

	// Return answerList array.
	public String[] getAnswerList(){
		return answerList;
	}

	// Return answer int.
	public int getAnswer(){
		return answer;
	}

	// Return timesTried int.
	public int getTimesTried(){
		return timesTried;
	}

	// Return timesCorrect int.
	public int getTimesCorrect(){
		return timesCorrect;
	}

	// Return percent correct double.
	public double getPercentCorrect(){
		return (double) this.timesCorrect / (double) this.timesTried;
	}

	// Return where line is in questionList ArrayList.
	public int getLine(){
		return line;
	}

	// Increment one up timesTried.
	public void setTimesTried(){
		this.timesTried++;
	}

	// Increment one up timesCorrect.
	public void setTimesCorrect(){
		this.timesCorrect++;
	}

}