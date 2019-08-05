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
	    
	    /* Establish database connection */
	    ConnectToDB myDB = new ConnectToDB();
	    myDB.Connection();
	    	    
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
            
            /* Define the three sections of the GUI */
            VBox left = new VBox(); //SEARCH
            VBox center = new VBox(); //RESULTS
            VBox right = new VBox(); //CART
            
            /* Begin building left */
            TextField titleSearch = new TextField();
            TextField artistSearch = new TextField();
            TextField labelSearch = new TextField();
            TextField formatSearch = new TextField();
            TextField priceSearch = new TextField();
                        
            /* Add a button to find the listings from a given artist */
            Button Search = new Button("Search");
            Search.setTooltip(new Tooltip("Search for a Listing with the above criteria"));
            Search.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    
                    /* Build the query */
                    String q = new String("SELECT r.title, a.artist_name, la.label_name, li.format, li.price FROM "+ 
                    "Album r, Artist a, Label la, Listing li WHERE");
                    boolean first_flag = true; //check if this is the first condition. Used to properly format the query
                    if(!titleSearch.getText().isEmpty() && titleSearch.getText() != null) {
                        if(first_flag) {
                            
                            first_flag = false;
                        } else {
                            
                        }
                    }
                    if(!artistSearch.getText().isEmpty() && artistSearch.getText() != null) {
                        if(first_flag) {
                            
                            first_flag = false;
                        } else {
                            
                        }
                    }
                    if(!labelSearch.getText().isEmpty() && labelSearch.getText() != null) {
                        if(first_flag) {
                            
                            first_flag = false;
                        } else {
                            
                        }
                    }
                    if(!formatSearch.getText().isEmpty() && formatSearch.getText() != null) {
                        if(first_flag) {
                            
                            first_flag = false;
                        } else {
                            
                        }
                    }
                    if(!priceSearch.getText().isEmpty() && priceSearch.getText() != null) {
                        if(first_flag) {
                            
                            first_flag = false;
                        } else {
                            
                        }
                    }
                    
                    
                    final Stage dialog = new Stage();
                    dialog.initModality(Modality.APPLICATION_MODAL);
                    dialog.initOwner(primaryStage);
                    VBox dialogVbox = new VBox();
                    
                    
                    
                    Scene dialogScene = new Scene(dialogVbox, 300, 200);
                    dialog.setScene(dialogScene);

                    dialog.show();
                }
            });
            
            /* Populate VBoxes */
            left.getChildren().addAll(new Label("Title"), titleSearch, new Label("Artist"), artistSearch, new Label("Label"), labelSearch, 
                    new Label("Format"), formatSearch,new Label("Max Price"), priceSearch, Search);
            
            /* Place the VBoxes */
            root.setLeft(left);
            root.setCenter(center);
            root.setRight(right);
			
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
