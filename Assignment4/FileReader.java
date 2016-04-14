import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class FileReader
{

	private File file;
	private Scanner fileScanner;
	private ArrayList<String> contents;

	public FileReader(String inputFile) throws IOException
	{
		file = new File(inputFile);
		fileScanner = new Scanner(file);
		contents = new ArrayList<>();
	}

	// Add file contents to contents ArrayList. Return contents ArrayList.
	public ArrayList<String> getContent()
	{
		while(this.fileScanner.hasNext())
		{
			this.contents.add(this.fileScanner.nextLine());
		}
		return this.contents;
	}

}