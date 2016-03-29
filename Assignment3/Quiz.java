import java.util.*;
import java.io.*;
import java.text.DecimalFormat;

public class Quiz {

	public static void main(String[] args) throws IOException{

		File questionsFile = new File(args[0]); 	// Input text file from command line argument.
		Scanner fileInput = new Scanner(questionsFile);
		Scanner userInput = new Scanner(System.in);
		PrintWriter outputFile;
		ArrayList<String> questionText = new ArrayList<>();
		ArrayList<Question> questionList = new ArrayList<>();
		DecimalFormat df = new DecimalFormat("#.#%");

		int questionNum = 0;
		int line = 0;
		int questionCounter = 0;
		int userAnswer;
		int answerNum;
		int[] userAnswers;
		int right = 0;
		int wrong = 0;

		// Add contents of questionFile to questionText
		while(fileInput.hasNext()){
			questionText.add(fileInput.nextLine());
		}
		// Create individual Question objects from questionText. Add Question objects to questionList.
		while(line != questionText.size()){
			questionList.add(new Question(questionText));
			line = questionList.get(questionCounter++).getLine();
		}

		userAnswers = new int[questionList.size()];

		System.out.println("Welcome to the Quiz Program! Good Luck!\n");

		for(int i = 0; i < questionList.size(); i++){
			answerNum = 0;
			System.out.println("Question " + (questionNum++) + ":");
			System.out.println(questionList.get(i).getQuestion());
			System.out.println("Answers:");
			for(String answer : questionList.get(i).getAnswerList()){
				System.out.println((answerNum++) + ": " + answer);
			}
			System.out.println();
			do{
				System.out.print("Your answer? (enter a number): ");
				userAnswer = userInput.nextInt();
			}while(userAnswer > (questionList.get(i).getAnswerList().length - 1) || userAnswer < 0);
			userAnswers[i] = userAnswer;
			System.out.println();
		}

		System.out.println("Thanks for your answers!");
		System.out.println("Here are your results:\n");

		for(int i = 0; i < questionList.size(); i++){
			System.out.println("Question: " + questionList.get(i).getQuestion());
			System.out.println("Answer: " + questionList.get(i).getAnswerList()[questionList.get(i).getAnswer()]);
			System.out.println("Player Guess: " + questionList.get(i).getAnswerList()[userAnswers[i]]);
			if(questionList.get(i).getAnswer() == userAnswers[i]){
				System.out.println("Result: CORRECT! Great Work!");
				right++;
				questionList.get(i).setTimesCorrect();
				questionList.get(i).setTimesTried();
			}
			else{
				System.out.println("Result: INCORRECT! Remember the answer for next time!");
				wrong++;
				questionList.get(i).setTimesTried();
			}
			System.out.println();
		}

		System.out.println("Your overall performance was:");
		System.out.println("\tRight: " + right);
		System.out.println("\tWrong: " + wrong);
		System.out.println("\tPct: " + df.format((double) right / (double) questionList.size()) + "\n");

		System.out.println("Here are some cumulative statistics:");
		for(int i = 0; i < questionList.size(); i++){
			System.out.println("Question: " + questionList.get(i).getQuestion());
			System.out.println("Times Tried: " + questionList.get(i).getTimesTried());
			System.out.println("Times Correct: " + questionList.get(i).getTimesCorrect());
			System.out.println("Percent Correct: " + df.format(questionList.get(i).getPercentCorrect()));
		}

		Question easiest = questionList.get(0);
		Question hardest = questionList.get(0);
		for(int i = 0; i < questionList.size(); i++){
			if(questionList.get(i).getPercentCorrect() > easiest.getPercentCorrect()){
				easiest = questionList.get(i);
			}
			if(questionList.get(i).getPercentCorrect() < hardest.getPercentCorrect()){
				hardest = questionList.get(i);
			}
		}

		System.out.println("\nEasiest Question:");
		System.out.println("Question: " + easiest.getQuestion());
		System.out.println("Times Tried: " + easiest.getTimesTried());
		System.out.println("Times Correct: " + easiest.getTimesCorrect());
		System.out.println("Percent Correct: " + df.format(easiest.getPercentCorrect())); 

		System.out.println("Hardest Question:");
		System.out.println("Question: " + hardest.getQuestion());
		System.out.println("Times Tried: " + hardest.getTimesTried());
		System.out.println("Times Correct: " + hardest.getTimesCorrect());
		System.out.println("Percent Correct: " + df.format(hardest.getPercentCorrect()));

		outputFile = new PrintWriter(args[0]);
		for(int i = 0; i < questionList.size(); i++){
			outputFile.println(questionList.get(i).getQuestion());
			outputFile.println(questionList.get(i).getAnswerList().length);
			for(int j = 0; j < questionList.get(i).getAnswerList().length; j++){
				outputFile.println(questionList.get(i).getAnswerList()[j]);
			}
			outputFile.println(questionList.get(i).getAnswer());
			outputFile.println(questionList.get(i).getTimesTried());
			outputFile.println(questionList.get(i).getTimesCorrect());
		}
		outputFile.close();	
	}

}