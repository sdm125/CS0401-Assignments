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

	private String[] makeAnswerList(int answerListLength, ArrayList<String> questionList){
		String[] answerList = new String[answerListLength];
		for(int i = 0; i < answerListLength; i++){
			answerList[i] = questionList.get(++this.line);
		}
		return answerList;
	}

	private int moveLineNum(ArrayList<String> text){
		return Integer.parseInt(text.get(++this.line));
	}

	public String getQuestion(){
		return question;
	}

	public String[] getAnswerList(){
		return answerList;
	}

	public int getAnswer(){
		return answer;
	}

	public int getTimesTried(){
		return timesTried;
	}

	public int getTimesCorrect(){
		return timesCorrect;
	}

	public double getPercentCorrect(){
		return (double) this.timesCorrect / (double) this.timesTried;
	}

	public int getLine(){
		return line;
	}

	public void setTimesTried(){
		this.timesTried++;
	}

	public void setTimesCorrect(){
		this.timesCorrect++;
	}

}