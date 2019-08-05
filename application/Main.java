package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Main extends Application {
    
    
    
    
    
	@Override
	public void start(Stage primaryStage) {
	    
	    ConnectToDB myDB = new ConnectToDB();
	    myDB.Connection();
	    //myDB.Query("SELECT * FROM Album;"); //IT WORKS!
	    	    
        /* Set up the GUI */
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,1000,600);
			
			/* set title of the application */
            primaryStage.setTitle("Crate Digger");
            
            /* add background image */
            Image image = new Image("file:crate_digger.jpeg");
            ImageView mv = new ImageView(image);
            mv.setOpacity(.2f);
            root.getChildren().addAll(mv);
            
            VBox center = new VBox();
            
            /* Add a test button */
            Button touch = new Button("Touch Me To Query ;)");
            touch.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    /* Set up the new dialog box */
                    final Stage dialog = new Stage();
                    dialog.initModality(Modality.APPLICATION_MODAL);
                    dialog.initOwner(primaryStage);
                    VBox dialogVbox = new VBox();
                    Label l = new Label();
                    
                    /* Perform the query */
                    ResultSet resultSet = myDB.Query("SELECT * FROM Album LIMIT 10;");
                    String ans = "";
                    try {
                        ResultSetMetaData metaData = resultSet.getMetaData();
                        int columns = metaData.getColumnCount();
                        
                        for (int i=1; i<= columns; i++) {
                            ans += metaData.getColumnName(i)+"\t";
                        }

                        ans += "\n";

                        while (resultSet.next()) {
                   
                            for (int i=1; i<= columns; i++) {
                                ans += resultSet.getObject(i)+"\t\t";
                            }
                            ans += "\n";
                        }
                        
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    
                    l.setText(ans);
                    dialogVbox.getChildren().add(l);
                    l.setWrapText(true);
                    Scene dialogScene = new Scene(dialogVbox, 700, 400);
                    dialog.setScene(dialogScene);
                    dialog.show();
                }
            });
            
            center.getChildren().add(touch);
            root.setCenter(center);
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
	    
	    
	    
		launch(args);
	}
}
