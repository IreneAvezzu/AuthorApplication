import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class QuotesPanel extends VBox {
	private Label quote, scores, globalScore, mostLikedAuthor, quotesLeft, phrase;
	private Button start, stop, nextQuote, nextAuthor, gSGraph1, gSGraph2, info1, info2, restart, back1, back2;
	private RadioButton like, dislike;
	private Alert abilities, critheria;
	private double globalScoreValue;
	private int numQuotes=0, totQuotes=0;
	private DecimalFormat fmt = new DecimalFormat("0.00");
	private importantFigures impFig;
	private LineChart<Number,Number> lineChart;
	private XYChart.Series series = new XYChart.Series ();
	private Rectangle r, r2;
	private ImageView imgView;
	private ProgressBar pb1, pb2, pb3, pb4, pb5;
	private ChoiceBox authorChoiceBox; 
	private ArrayList<String> authorOptions = new ArrayList<>();
	private ArrayList<String> chosenAuthors = new ArrayList<>();
	private int counter = 0;
	
	//constructor
	public QuotesPanel () {  
		Font font = new Font(20); 
		Font font2 = new Font(15); 
		
		//first pane: welcome panel
		quote = new Label ("Quotes Game!");
		quote.setAlignment(Pos.CENTER);
		quote.setFont(Font.font("verdania", FontWeight.BOLD, FontPosture.REGULAR, 70));
		quote.setTextFill(Color.BROWN);  
		quote.setWrapText(true);
		
		//quote.setFont(font);
		info1 = new Button("?");
		info1.setFont(font);
		info1.setTextFill(Color.WHITE);
		info1.setAlignment(Pos.CENTER);
		info1.setOnAction(this::info1);
		info1.setShape(new Circle(10));
		info1.setStyle(
                "-fx-background-radius: 5em; " +
                "-fx-min-width: 40px; " +
                "-fx-min-height: 40px; " +
                "-fx-max-width: 40px; " +
                "-fx-max-height: 40px;" +
                "-fx-background-color: chocolate; ");
		
		start = new Button("Start!" );
		start.setAlignment(Pos.CENTER);
		start.setOnAction(event -> {
			try {
				startGame(event);
			} catch (FileNotFoundException e) {
				System.out.println("Something is not working :(");
			}
		});
		start.setMaxSize(100, 200);
		start.setFont(font);
		start.setTextFill(Color.WHITE);
		start.setStyle("-fx-background-color: darkgoldenrod; ");
		
        //choice of authors		
		phrase = new Label ("Chose your Authors selection");
		phrase.setAlignment(Pos.CENTER);
		phrase.setFont(Font.font("verdania", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 20));
		phrase.setTextFill(Color.BROWN);
		
        authorOptions.add("Ada Lovelace");
        authorOptions.add("Alan Turing");
        authorOptions.add("Aristotele");
        authorOptions.add("Jane Austen");
        authorOptions.add("J. K. Rowling");
        authorOptions.add("John Green");
        authorOptions.add("J. R. R. Tolkien");
        authorOptions.add("Katherine Johnson");
        authorOptions.add("Virginia Woolf");
        authorOptions.add("William Shakespeare");
		
		authorChoiceBox = new ChoiceBox();
        authorChoiceBox.getItems().add(authorOptions.get(0));
        authorChoiceBox.getItems().add(authorOptions.get(1));
        authorChoiceBox.getItems().add(authorOptions.get(2));
        authorChoiceBox.getItems().add(authorOptions.get(3));
        authorChoiceBox.getItems().add(authorOptions.get(4));
        authorChoiceBox.getItems().add(authorOptions.get(5));
        authorChoiceBox.getItems().add(authorOptions.get(6));
        authorChoiceBox.getItems().add(authorOptions.get(7));
        authorChoiceBox.getItems().add(authorOptions.get(8));
        authorChoiceBox.getItems().add(authorOptions.get(9));
        authorChoiceBox.setMinSize(200, 30);
        
        nextAuthor = new Button("Next Author"); 
        nextAuthor.setAlignment(Pos.CENTER);
        nextAuthor.setOnAction(this::nextAuthor);
        nextAuthor.setStyle("-fx-background-color: burlywood; ");
        
        HBox hB = new HBox (authorChoiceBox, nextAuthor);
        hB.setSpacing(20);
        hB.setAlignment(Pos.CENTER);
		
		setSpacing(10);
        setAlignment(Pos.CENTER);
        getChildren().addAll(quote, phrase, hB, info1);
        
		//pane of quotes: display the quotes and accept user's feedback (radiobutton for feedback, 
        //button for next quote and stop, label for quote and authors'scores)
        //all these button/label/radio button are not shown until the user click the start button and 
        //the program enters the startGame method
        
        //top area scores
        scores = new Label (null);
		scores.setAlignment(Pos.CENTER);
		scores.setFont(Font.font("verdania", FontWeight.BOLD, FontPosture.REGULAR, 12));
		scores.setTextFill(Color.BLACK);
		
		r = new Rectangle (0, 0, 700, 100);
		r.setFill(Color.CHOCOLATE);
		
		pb1 = new ProgressBar (0.0);
		pb1.setStyle("-fx-accent: brown;");
		pb2 = new ProgressBar (0.0);
		pb2.setStyle("-fx-accent: brown;");
		pb3 = new ProgressBar (0.0);
		pb3.setStyle("-fx-accent: brown;");
		pb4 = new ProgressBar (0.0);
		pb4.setStyle("-fx-accent: brown;");
		pb5 = new ProgressBar (0.0);
		pb5.setStyle("-fx-accent: brown;");
		
		//area quotes
		
        //area area like/dislike radiobutton, next and stop button
		stop = new Button("Stop"); //stop the application
		stop.setAlignment(Pos.CENTER);
		stop.setOnAction(this::stopGame1);
		stop.setStyle("-fx-background-color: burlywood; ");
		
		ToggleGroup feedbackButton = new ToggleGroup();
		like = new RadioButton ("Like"); 	
		like.setSelected(false);
		like.setAlignment(Pos.CENTER);
		like.setToggleGroup(feedbackButton); 
		
		dislike = new RadioButton ("Dislike"); 	
		dislike.setSelected(true);
		dislike.setAlignment(Pos.CENTER);
		dislike.setToggleGroup(feedbackButton); 
				
		nextQuote = new Button("Next quote"); //keeps the application going
		nextQuote.setAlignment(Pos.CENTER);
		nextQuote.setOnAction(this::nextQuote);
		nextQuote.setStyle("-fx-background-color: burlywood; ");
		
		//left 3 area show graph
		gSGraph1 = new Button("Show Graph"); //will show the graph of the global score from the quotes
		gSGraph1.setAlignment(Pos.CENTER);
		gSGraph1.setOnAction(this::showGraph1);
		gSGraph1.setStyle("-fx-background-color: burlywood; ");
		
		quotesLeft = new Label (null);
		quotesLeft.setAlignment(Pos.CENTER);
		quotesLeft.setFont(Font.font("verdania", FontWeight.BOLD, FontPosture.REGULAR, 12));
		quotesLeft.setTextFill(Color.BLACK);
		
		//left 4 area info button back home
		restart = new Button("Restart game"); //go back to the homepage
		restart.setAlignment(Pos.CENTER);
		restart.setOnAction(this::homePage);
		restart.setStyle("-fx-background-color: burlywood; ");
		
		info2 = new Button("?");
		info2.setFont(font2);
		info2.setTextFill(Color.WHITE);
		info2.setAlignment(Pos.CENTER);
		info2.setOnAction(this::info2);
		info2.setShape(new Circle(10));
		info2.setStyle(
                "-fx-background-radius: 5em; " +
                "-fx-min-width: 30px; " +
                "-fx-min-height: 30px; " +
                "-fx-max-width: 30px; " +
                "-fx-max-height: 30px;" +
                "-fx-background-color: chocolate; ");
		
		//end game pane: display the most liked author and the global score
		//all these button/label are not shown until the user click the stop button and the program enters the stopGame method
		
		//top area most liked author
		mostLikedAuthor = new Label (null);
		mostLikedAuthor.setAlignment(Pos.CENTER); 
		
        //lower bottom global score label and show graph button
        globalScore = new Label (null);
		globalScore.setAlignment(Pos.CENTER);
		
		gSGraph2 = new Button("Show Graph"); //will show the graph of the global score from the final statistics
		gSGraph2.setAlignment(Pos.CENTER);
		gSGraph2.setOnAction(this::showGraph2);
		gSGraph2.setStyle("-fx-background-color: burlywood; "); 
		gSGraph2.setMinSize(130, 50);
		gSGraph2.setFont(Font.font("verdania", FontWeight.BOLD, FontPosture.REGULAR, 15));
		
	}
	
	public void nextAuthor (ActionEvent event) {
		String str = (String) authorChoiceBox.getValue();
		int index = authorOptions.indexOf(str);
		chosenAuthors.add(str);
		authorOptions.remove(index);
		counter++;
		phrase.setFont(Font.font("verdania", FontWeight.BOLD, FontPosture.REGULAR, 30));
		
		//reset the choice box
		authorChoiceBox.getItems().clear();
		for (int i=0; i<authorOptions.size(); i++) {
			authorChoiceBox.getItems().add(authorOptions.get(i));
		}
		
		//display panel
		getChildren().clear();
		
		if (counter<4) {
			phrase.setText("Choose your next Author");
			getChildren().addAll(phrase, authorChoiceBox, nextAuthor);
		}
		else {
			phrase.setText("Choose your last Author");
			getChildren().addAll(phrase, authorChoiceBox, start);
		}
		
	}
	
	public void startGame (ActionEvent event) throws FileNotFoundException {
		//clear the pane
		getChildren().clear();
		
		String str = (String) authorChoiceBox.getValue();
		int index = authorOptions.indexOf(str);
		chosenAuthors.add(str);
		authorOptions.remove(index);
		
		for (int i=0; i<5; i++) {
			String s = "";
			
			switch (chosenAuthors.get(i)) {
			case "Ada Lovelace": 
				s="AdaLovelace.txt";
				break;
			case "Alan Turing": 
				s="AlanTuring.txt";
				break;
			case "Aristotele": 
				s="Aristotele.txt";
				break;
			case "Jane Austen": 
				s="JaneAusten.txt";
				break;
			case "J. K. Rowling": 
				s="JKRowling.txt";
				break;
			case "John Green": 
				s="JohnGreen.txt";
				break;
			case "J. R. R. Tolkien": 
				s="JRRTolkien.txt";
				break;
			case "Katherine Johnson": 
				s="KatherineJohnson.txt";
				break;
			case "Virginia Woolf": 
				s="VirginiaWoolf.txt";
				break;
			case "William Shakespeare": 
				s="WilliamShakespeare.txt";
				break;
			}    
			chosenAuthors.set(i, s);
		}
		
		impFig = new importantFigures (chosenAuthors.get(0), chosenAuthors.get(1), chosenAuthors.get(2), chosenAuthors.get(3), chosenAuthors.get(4));
		totQuotes=impFig.getTotQuotes();
		
		//set all the label to their correct text and font
		quote.setFont(Font.font("verdania", FontWeight.NORMAL, FontPosture.REGULAR, 14));
		quote.setTextFill(Color.BLACK);
		quote.setText(impFig.chooseQuote());
		quote.setMaxWidth(600);
		quote.setMinHeight(80);
		scores.setText(impFig.getAuthor(0).getName() + ": 0,00     " +
				impFig.getAuthor(1).getName() +": 0,00     " +
				impFig.getAuthor(2).getName() +": 0,00     " +
				impFig.getAuthor(3).getName() +": 0,00     " +
				impFig.getAuthor(4).getName() +": 0,00");
		quotesLeft.setText("Remaning quotes: " + (totQuotes-numQuotes-1));
		
		//shows elements form this pane
		HBox level0 = new HBox (pb1, pb2, pb3, pb4, pb5);
		level0.setAlignment(Pos.CENTER);
		level0.setSpacing(40);
		
		VBox vb = new VBox (scores, level0);
		vb.setSpacing(10);
		vb.setAlignment(Pos.CENTER);
		
		StackPane level1 = new StackPane ();
		level1.getChildren().addAll(r, vb);
		
		HBox level2 = new HBox (quote);
		level2.setAlignment(Pos.CENTER);
		
		HBox level3 = new HBox ();
		level3.getChildren().add(stop);
		level3.getChildren().add(like);
		level3.getChildren().add(dislike);
		level3.getChildren().add(nextQuote);
		level3.setSpacing(20);
		level3.setAlignment(Pos.CENTER);
		
		HBox level4 = new HBox ();
		level4.getChildren().add(gSGraph1);
		level4.getChildren().add(quotesLeft);
		level4.setSpacing(20);
		level4.setAlignment(Pos.CENTER);
		
		HBox level5 = new HBox ();
		level5.getChildren().add(restart);
		level5.getChildren().add(info2);
		level5.setSpacing(20);
		level5.setAlignment(Pos.CENTER);
		
		setSpacing(20);
		//setAlignment(Pos.CENTER);
		getChildren().addAll(level1, level2, level3, level4, level5);
		
		//graph pane: display the graph of the global score
		final NumberAxis xAxis = new NumberAxis (0.0, totQuotes, 5);
		final NumberAxis yAxis = new NumberAxis (0., 1.1, 0.1);
		xAxis.setLabel("Number of quotes shown");
		lineChart = new LineChart<Number, Number> (xAxis, yAxis);
		lineChart.setTitle("Global scores");
		series.setName("Global scores");
		series.getData().add(new XYChart.Data(0, 0));
		back1 = new Button("Back to quotes"); //go back to quotes
		back1.setAlignment(Pos.CENTER_RIGHT);
		back1.setOnAction(this::displayQuote);
		back2 = new Button("Back to statistics"); //go back to statistics
		back2.setAlignment(Pos.CENTER);
		back2.setOnAction(this::statistics);
	}
	
	public void info1 (ActionEvent event) { //info dialog box: what can the application do?
		abilities = new Alert(AlertType.INFORMATION);
		abilities.setTitle("Apllication abilities");
		abilities.setHeaderText("What can the application do?");
		abilities.setContentText("The application will display some quotes form five authors\r\n" + 
				"chosen by the user who can interact with the app by liking or \r\n" + 
				"disliking the quote. The application will calculate the most \r\n" + 
				"liked author and, after 25 quotes will start displaying only \r\n" + 
				"quotes from the most liked author. Some statistics are \r\n" + 
				"computed as the interaction happens, some are displayed \r\n" + 
				"during the interaction (authors' score) and other only when \r\n" + 
				"the application is stopped (most liked author and global \r\n" + 
				"score). To like or dislike a quote just click the like/dislike \r\n" + 
				"button; if no feedback is expressed the feedback will be \r\n" + 
				"considered negative (dislike). When the user is ready for \r\n" + 
				"the next quote can click next otherwise if they wants to stop \r\n" + 
				"the application and see the final scores they can click the \r\n" + 
				"stop button. The graph button will display the graph of the \r\n" + 
				"global score.\r\n" + 
				"\r\n" + 
				"					Let's start!\r\n"); 
		abilities.showAndWait();
	}

	public void info2 (ActionEvent event) { //info dialog box: how is the next quote chosen?
		critheria = new Alert(AlertType.INFORMATION);
		critheria.setTitle("Critheria for chosing the next quote");
		critheria.setHeaderText("How is the next quote chosen?");
		critheria.setContentText("All the quotes are randomly picked by the selected author.\r\n" + 
				"The author is selected in two different ways based on how\r\n" + 
				"many quotes have been displayed, the first 25 quotes are\r\n" + 
				"chosen from all authors (five per author) so the application\r\n" + 
				"can develop which is the most liked author by the user the\r\n" + 
				"next quotes will be from the most liked author that is\r\n" + 
				"upgraded after each quotes. When the quotes from the most \r\n" + 
				"liked author have all been displayed the app starts showing \r\n" + 
				"quotes from the next author in the ranking and keeps going \r\n" + 
				"until all the quotes have been displayed.");
		critheria.showAndWait();
	}

	public void nextQuote (ActionEvent event) {
		if((totQuotes==(numQuotes+1)))// || (impFig.getShown().getNumShownQuotes() == impFig.getShown().getNumQuotes()))
			stopGame2();
		else {
			//computes the value needed for the computation of scores
			impFig.feedback(like.isSelected());
			numQuotes++;
			impFig.getAuthor(0).setScore();
			impFig.getAuthor(1).setScore();
			impFig.getAuthor(2).setScore();
			impFig.getAuthor(3).setScore();
			impFig.getAuthor(4).setScore();
			pb1.setProgress(impFig.getAuthor(0).getScore());
			pb2.setProgress(impFig.getAuthor(1).getScore());
			pb3.setProgress(impFig.getAuthor(2).getScore());
			pb4.setProgress(impFig.getAuthor(3).getScore());
			pb5.setProgress(impFig.getAuthor(4).getScore());
			setGlobalScoreValue();
			series.getData().add(new XYChart.Data(numQuotes, globalScoreValue)); //add data to the graph
			impFig.setRanking();
			
			//set all the label to their correct text
			scores.setText(impFig.getAuthor(0).getName() + ": " + fmt.format(impFig.getAuthor(0).getScore()) + "     " +
							impFig.getAuthor(1).getName() + ": " + fmt.format(impFig.getAuthor(1).getScore()) + "     " +
							impFig.getAuthor(2).getName() + ": " + fmt.format(impFig.getAuthor(2).getScore()) + "     " +
							impFig.getAuthor(3).getName() + ": " + fmt.format(impFig.getAuthor(3).getScore()) + "     " +
							impFig.getAuthor(4).getName() + ": " + fmt.format(impFig.getAuthor(4).getScore()));
			like.setSelected(false);
			dislike.setSelected(true);
			quote.setText(impFig.chooseQuote());
			quotesLeft.setText("Remaning quotes: " + (totQuotes-numQuotes-1));				
		}

			
	}
	
	private void stopGame2() {
		//computes the value needed for the computation of scores
		impFig.feedback(like.isSelected());
		numQuotes++;
		impFig.getAuthor(0).setScore();
		impFig.getAuthor(1).setScore();
		impFig.getAuthor(2).setScore();
		impFig.getAuthor(3).setScore();
		impFig.getAuthor(4).setScore();
		impFig.setRanking();
		setGlobalScoreValue();
		series.getData().add(new XYChart.Data(numQuotes, globalScoreValue)); //add data to the graph
		
		//set all the label to their correct text
		globalScore.setText("The global score is: " + fmt.format(globalScoreValue));
		mostLikedAuthor.setText("Your most liked Author was: " + impFig.getFinalRanking().get(0).getName());
		//clear the pane
		getChildren().clear();
		
		//shows elements form this pane
		ImageView imgView = new ImageView (impFig.getFinalRanking().get(0).getPicture());
		
		HBox top = new HBox (mostLikedAuthor);
		top.setSpacing(20);
        top.setAlignment(Pos.CENTER);
        
        HBox bottom = new HBox (gSGraph2, globalScore);
		bottom.setSpacing(20);
		bottom.setAlignment(Pos.CENTER);
		
		VBox left = new VBox (top, bottom);
		left.setSpacing(20);
		left.setAlignment(Pos.CENTER);

		HBox all = new HBox (left, imgView);
		all.setSpacing(20);
		all.setAlignment(Pos.CENTER);
			
		setSpacing(20);
		setAlignment(Pos.CENTER);
		getChildren().addAll(all, restart);
		
	}

	public void stopGame1 (ActionEvent event) {
		//computes the value needed for the computation of scores
		impFig.feedback(like.isSelected());
		numQuotes++;
		impFig.getAuthor(0).setScore();
		impFig.getAuthor(1).setScore();
		impFig.getAuthor(2).setScore();
		impFig.getAuthor(3).setScore();
		impFig.getAuthor(4).setScore();
		impFig.setFinalRanking();
		setGlobalScoreValue();
		series.getData().add(new XYChart.Data(numQuotes, globalScoreValue)); //add data to the graph
		
		//set all the label to their correct text
		globalScore.setFont(Font.font("verdania", FontWeight.BOLD, FontPosture.REGULAR, 20));
		globalScore.setTextFill(Color.BROWN);  
		mostLikedAuthor.setFont(Font.font("verdania", FontWeight.BLACK, FontPosture.REGULAR, 20));
		mostLikedAuthor.setTextFill(Color.BROWN);  
		globalScore.setText("The global score is: " + fmt.format(globalScoreValue));
		mostLikedAuthor.setText("Your most liked Author was: " + impFig.getFinalRanking().get(0).getName());
		
		//clear the pane
		getChildren().clear();
		
		//shows elements form this pane
		ImageView imgView = new ImageView (impFig.getFinalRanking().get(0).getPicture());
		
		HBox top = new HBox (mostLikedAuthor);
		top.setSpacing(20);
        top.setAlignment(Pos.CENTER);
        
        HBox bottom = new HBox (gSGraph2, globalScore);
		bottom.setSpacing(20);
		bottom.setAlignment(Pos.CENTER);
		
		VBox left = new VBox (top, bottom);
		left.setSpacing(20);
		left.setAlignment(Pos.CENTER);

		HBox all = new HBox (left, imgView);
		all.setSpacing(20);
		all.setAlignment(Pos.CENTER);
			
		setSpacing(20);
		setAlignment(Pos.CENTER);
		getChildren().addAll(all, restart);
	}
	
	public void showGraph1 (ActionEvent event) {
		//clear the pane
		getChildren().clear();
		
		//display the graph
		lineChart.getData().add(series);
		setSpacing(10);
		setAlignment(Pos.CENTER);
		getChildren().addAll(lineChart, back1);
	}
	
	public void showGraph2 (ActionEvent event) {
		//clear the pane
		getChildren().clear();
		
		lineChart.getData().clear();
		lineChart.getData().addAll(series);
		
		back2.setText("Go Back");
		
		//display the graph
		getChildren().addAll(lineChart, back2);
	}

	public void homePage (ActionEvent event) {
		//clear the pane
		getChildren().clear();
		
		//reset labels
		quote.setText("Quotes Game!");
		quote.setAlignment(Pos.CENTER);
		quote.setFont(Font.font("verdania", FontWeight.BOLD, FontPosture.REGULAR, 70));
		quote.setTextFill(Color.FIREBRICK);  
		quote.setWrapText(true);
		
		phrase.setText("Chose your Authors selection");
		phrase.setFont(Font.font("verdania", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 20));
		
		HBox hB = new HBox (authorChoiceBox, nextAuthor);
        hB.setSpacing(20);
        hB.setAlignment(Pos.CENTER);
		
		setSpacing(10);
        setAlignment(Pos.CENTER);
        getChildren().addAll(quote, phrase, hB, info1);
        
        //reset all variables
        reset();
	}
	
	private void reset() {
		numQuotes = 0;
		impFig.reset();
		lineChart.getData().clear();
		pb1.setProgress(0.0);
		pb2.setProgress(0.0);
		pb3.setProgress(0.0);
		pb4.setProgress(0.0);
		pb5.setProgress(0.0);
		chosenAuthors.clear();
		authorOptions.clear();
		
		authorOptions.add("Ada Lovelace");
        authorOptions.add("Alan Turing");
        authorOptions.add("Aristotele");
        authorOptions.add("Jane Austen");
        authorOptions.add("J. K. Rowling");
        authorOptions.add("John Green");
        authorOptions.add("J. R. R. Tolkien");
        authorOptions.add("Katherine Johnson");
        authorOptions.add("Virginia Woolf");
        authorOptions.add("William Shakespeare");
        
        authorChoiceBox.getItems().remove(5);
        authorChoiceBox.getItems().remove(4);
        authorChoiceBox.getItems().remove(3);
        authorChoiceBox.getItems().remove(2);
        authorChoiceBox.getItems().remove(1);
        authorChoiceBox.getItems().remove(0);
		
        authorChoiceBox.getItems().add(authorOptions.get(0));
        authorChoiceBox.getItems().add(authorOptions.get(1));
        authorChoiceBox.getItems().add(authorOptions.get(2));
        authorChoiceBox.getItems().add(authorOptions.get(3));
        authorChoiceBox.getItems().add(authorOptions.get(4));
        authorChoiceBox.getItems().add(authorOptions.get(5));
        authorChoiceBox.getItems().add(authorOptions.get(6));
        authorChoiceBox.getItems().add(authorOptions.get(7));
        authorChoiceBox.getItems().add(authorOptions.get(8));
        authorChoiceBox.getItems().add(authorOptions.get(9));
	}
	
	public void setGlobalScoreValue () {
		globalScoreValue = (double) impFig.getLikes()/numQuotes;
	}
	
	public void statistics (ActionEvent event) {
		//clear the pane
		getChildren().clear();
		
		//shows elements form this pane
		ImageView imgView = new ImageView (impFig.getFinalRanking().get(0).getPicture());
		
		HBox top = new HBox (mostLikedAuthor);
		top.setSpacing(20);
        top.setAlignment(Pos.CENTER);
        
        HBox bottom = new HBox (gSGraph2, globalScore);
		bottom.setSpacing(20);
		bottom.setAlignment(Pos.CENTER);
		
		VBox left = new VBox (top, bottom);
		left.setSpacing(20);
		left.setAlignment(Pos.CENTER);

		HBox all = new HBox (left, imgView);
		all.setSpacing(20);
		all.setAlignment(Pos.CENTER);
			
		setSpacing(20);
		setAlignment(Pos.CENTER);
		getChildren().addAll(all, restart);
	}

	public void displayQuote (ActionEvent event) {
		getChildren().clear();

		//shows elements form this pane
		StackPane level1 = new StackPane ();
		level1.getChildren().addAll(r, scores);
		
		HBox level2 = new HBox (quote);
		level2.setAlignment(Pos.CENTER);
		
		HBox level3 = new HBox ();
		level3.getChildren().add(stop);
		level3.getChildren().add(like);
		level3.getChildren().add(dislike);
		level3.getChildren().add(nextQuote);
		level3.setSpacing(20);
		level3.setAlignment(Pos.CENTER);
		
		HBox level4 = new HBox ();
		level4.getChildren().add(gSGraph1);
		level4.getChildren().add(quotesLeft);
		level4.setSpacing(20);
		level4.setAlignment(Pos.CENTER);
		
		HBox level5 = new HBox ();
		level5.getChildren().add(restart);
		level5.getChildren().add(info2);
		level5.setSpacing(20);
		level5.setAlignment(Pos.CENTER);
		
		setSpacing(20);
		//setAlignment(Pos.CENTER);
		getChildren().addAll(level1, level2, level3, level4, level5);
	}
}
