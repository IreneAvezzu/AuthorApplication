/*
Student Code Ethics
Students are expected to maintain the highest standards of academic integrity. Work that is not
of the student's own creation will receive no credit. Remember that you cannot give or receive
unauthorized aid on any assignment, quiz, or exam. A student cannot use the ideas of another
and declare it as his or her own. Students are required to properly cite the original source of
the ideas and information used in his or her work.
*/

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList; 
import java.util.Scanner;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Author {
	public ArrayList<String> quotes = new ArrayList<>();
	public ArrayList<Boolean> check = new ArrayList<>(); //false if the quote hasn't been displayed true if it's been
	private double score = 0.0; //initial score is 0.0
	private String authorName;
	private int numQuotes, numShownQuotes=0, likes=0;
	private Image picture;
	
	//constructor
	public Author (String quotesFile) throws FileNotFoundException {
		importQuotes (quotesFile);
		numQuotes=quotes.size();
	}	
	
	//import quotes from a file
	public void importQuotes(String fileAuthor) throws FileNotFoundException  {
    	Scanner file = new Scanner(new File(fileAuthor));
		authorName = file.nextLine();
		InputStream input = new FileInputStream(file.nextLine());
		picture = new Image (input);
		String s = "";
        while (file.hasNext()) {
        	s=file.nextLine();
        	if (quotes.contains(s) == false) { //check if the quote is not already present
        		quotes.add(s); 
        		check.add(false);
        	}
        }
	}
	
	//getters and setters
	public String getName() {
		return authorName;
	}
	public double getScore() {
		return score;
	}
	public void setScore() {
		if (numShownQuotes!=0)
			score = (double) likes/numShownQuotes;
		else
			score = 0.00;
	}
	public void setnumQuotes() {
		numQuotes++;
	}
	public int getNumQuotes () {
		return numQuotes;
	}
	public int getNumShownQuotes () {
		return numShownQuotes;
	}
	public void setNumShownQuotes () {
		numShownQuotes++;
	}
	public int getLikes () {
		return likes;
	}
	public void resetLikes () {
		likes=0;
	}
	public void resetNumShownQuotes () {
		numShownQuotes=0; 
	}
	public Boolean getCheck(int index) {
		Boolean tf = check.get(index);
		return tf;
	}
	public void resetCeck () {
		for (int i = 0; i<check.size(); i++)
    		check.set(i, false);
	}
	public Image getPicture () {
		return picture;
	}
	
	//print all the quotes
	@Override
	public String toString () {
		String str="";
		for (String quote : quotes)
			str += quote + "\n";
		return (str);
	}
	
	//print the quotes of the index
	public String getQuote (int index) {
		return quotes.get(index);
	}
    
	public void setCheck(int index) {
		check.set(index, true);
	}
	
    public void increaseLike () {
    	likes+=1;
    }

    public void reset() { 
    	resetNumShownQuotes(); 
    	resetLikes ();
    	resetCeck (); 
    }

}
