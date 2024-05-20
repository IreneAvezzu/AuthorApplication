import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class QuotesGUI extends Application{
	@Override
    public void start(Stage primaryStage) throws Exception {
		QuotesPanel pane = new QuotesPanel();
		pane.setStyle("-fx-background-color: #f9e79f "); 
    	Scene scene = new Scene (pane , 750, 450);
        primaryStage.setTitle("Quotes GUI");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
            launch(args);
    }
}
