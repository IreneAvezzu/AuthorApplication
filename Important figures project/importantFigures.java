import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class importantFigures {
	private Author a1, a2, a3, a4, a5, aT, shown;
	private ArrayList<Author> authors = new ArrayList<>();
	private ArrayList<Author> ranking = new ArrayList<>();
	private ArrayList<Author> finalRanking = new ArrayList<>();
	private double globalScore = 0.0;
	private Random rnd = new Random (); 
	private int r, a = 0, x, c=0, totQuotes, top=0;
	
	public importantFigures(String fileA1, String fileA2, String fileA3, String fileA4, String fileA5) throws FileNotFoundException {
		a1 = new Author (fileA1);
		a2 = new Author (fileA2);
		a3 = new Author (fileA3);
		a4 = new Author (fileA4);
		a5 = new Author (fileA5);
		
		//fill the arrays
		authors.add(a1);
		authors.add(a2);
		authors.add(a3);
		authors.add(a4);
		authors.add(a5);
		
		ranking.add(a1);
		ranking.add(a2);
		ranking.add(a3);
		ranking.add(a4);
		ranking.add(a5);
		
		finalRanking.add(a1);
		finalRanking.add(a2);
		finalRanking.add(a3);
		finalRanking.add(a4);
		finalRanking.add(a5);
		
		shown = a1;
		
		totQuotes = a1.getNumQuotes() + 
					a2.getNumQuotes() +
					a3.getNumQuotes() +
					a4.getNumQuotes() +
					a5.getNumQuotes();
	}

	public Author getAuthor(int index) {
		return authors.get(index);
	}
	public void setShown (Author aut) {
		shown = aut;
	}
	public Author getShown() {
		return shown;
	}	
	public int getLikes () {
		return (a1.getLikes() + a2.getLikes() + a3.getLikes() + a4.getLikes() + a5.getLikes());
	}
	public void setRanking() {
		Author aT1, aT2,aT3;
		aT1 = ranking.get(0);
		for (int i = 0; i<(ranking.size()-1); i++) {
			int max = i+1;
			aT2 = ranking.get(i+1);
			for (int g = i+2; g<ranking.size(); g++) {
				if (aT2.getScore()<ranking.get(g).getScore()) {
					aT2 = ranking.get(g);
					max=g;
				}
			}
			if (aT1.getScore()<aT2.getScore()) {
				aT3 = aT1;
				ranking.set(i, aT2);
				ranking.set(max, aT3);
			}
		}
	}
	public void setFinalRanking() {
		Author aT1, aT2,aT3;
		aT1 = finalRanking.get(0);
		for (int i = 0; i<4; i++) {
			int max = i+1;
			aT2 = finalRanking.get(i+1);
			for (int g = i+2; g<5; g++) {
				if (aT2.getScore()<finalRanking.get(g).getScore()) {
					aT2 = finalRanking.get(g);
					max=g;
				}
			}
			if (aT1.getScore()<aT2.getScore()) {
				aT3 = aT1;
				finalRanking.set(i, aT2);
				finalRanking.set(max, aT3);
			}
		}
	}
	public ArrayList<Author> getRanking () {
		return ranking;
	}
	public ArrayList<Author> getFinalRanking () {
		return finalRanking;
	}
	public int getTotQuotes() {
		return (totQuotes);
	}
	
	public void feedback (Boolean tf) {
		if (tf)
			shown.increaseLike();	
	}

	public String chooseQuote () {
		String str = "\"";
		if (c<25) { //display 25 quotes, five per authors
			shown = authors.get(a); //first 5 quotes form the first author, 6th to 10th quotes from the second author, ecc.;
			x = shown.getNumQuotes() ; //how many quotes has the author that is going to be shown
			r = rnd.nextInt(x); //random number that represent a quote
			while (shown.getCheck(r)) { //check if the quote has already been displayed
				r = rnd.nextInt(x); //if the quotes has already been displayed it will look for another one
			}
			if(c==4 || c==9 || c==14 || c==19 || c==24)
				a++; //every five quotes will switch author 
			c++;
		}
		else {
			if (ranking.get(0).getNumShownQuotes() == ranking.get(0).getNumQuotes()) {
				ranking.remove(0); //if the quotes from the most liked authors have already been displayed the author is removed from the arrayList of authors that are displayed
			}
			shown = ranking.get(0);
			x = shown.getNumQuotes();
			r = rnd.nextInt(x);
			while (shown.getCheck(r)) {
				r = rnd.nextInt(x); //if the quotes has already been displayed it looks for another one
			}
		}
		
		shown.setCheck (r);
		shown.setNumShownQuotes();
		str += shown.getQuote(r) + "\n\t-" + shown.getName() + " -";
		return str;
	}
	
	public void setGlobalScore(double value) {
		globalScore = value;
	}
	
	public void reset() {
		for (int i=0; i<5; i++)
			authors.get(i).reset();
		a = 0;
		c = 0;
		globalScore = 0.0;
		shown = a1;
		ranking.clear();
		ranking.add(a1);
		ranking.add(a2);
		ranking.add(a3);
		ranking.add(a4);
		ranking.add(a5);
	}
}
