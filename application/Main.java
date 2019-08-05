package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
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
			Scene scene = new Scene(root,1300,600);
			
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
            
            TableView<String[]> table = new TableView<>(); //This will appear in center
            
                        
            /* Add a button to find the listings from a given artist */
            Button Search = new Button("Search");
            Search.setTooltip(new Tooltip("Search for a Listing with the above criteria"));
            Search.setOnAction(new EventHandler<ActionEvent>() {
                @SuppressWarnings("unchecked")
                @Override
                public void handle(ActionEvent event) {
                    
                    /* Build the query */
                    String q = new String("SELECT r.title, a.artist_name, la.label_name, li.format, li.price FROM " + 
                            "Album r, Artist a, Label la, Listing li WHERE r.release_id = li.release_id AND r.label_id = la.label_id " + 
                            "AND r.artist_id = a.artist_id");
                    boolean first_flag = true; //check if this is the first condition. Used to properly format the query
                    if(!titleSearch.getText().isEmpty() && titleSearch.getText() != null) {
                        q += " AND r.title LIKE \"%" + titleSearch.getText() + "%\"";
                        first_flag = false;
                    }
                    if(!artistSearch.getText().isEmpty() && artistSearch.getText() != null) {
                        q += " AND a.artist_name LIKE \"%" + artistSearch.getText() + "%\"";
                        first_flag = false;
                    }
                    if(!labelSearch.getText().isEmpty() && labelSearch.getText() != null) {
                        q += " AND la.label_name LIKE \"%" + labelSearch.getText() + "%\"";
                        first_flag = false;
                    }
                    if(!formatSearch.getText().isEmpty() && formatSearch.getText() != null) {
                        q += " AND li.format LIKE \"%" + formatSearch.getText() + "%\"";
                        first_flag = false;
                    }
                    if(!priceSearch.getText().isEmpty() && priceSearch.getText() != null) {
                        q += " AND li.price <= " + priceSearch.getText();
                        first_flag = false;
                    }
                    q += ";";
                    
                    /* Only submit a querry if they typed something into one of the fields */
                    if(!first_flag) {
                        String [][] result = myDB.Query(q);
                        
                        ObservableList<String[]> data = FXCollections.observableArrayList();
                        data.addAll(Arrays.asList(result));
                        data.remove(0);//remove titles from data
                        
                        for (int i = 0; i < result[0].length; i++) {
                            TableColumn tc = new TableColumn(result[0][i]);
                            final int colNo = i;
                            tc.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
                                @Override
                                public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
                                    return new SimpleStringProperty((p.getValue()[colNo]));
                                }
                            });
                            tc.setPrefWidth(90);
                            table.getColumns().add(tc);
                        }
                        table.setItems(data);
                        
                        
                    } else {
                        Alert noText = new Alert(AlertType.INFORMATION);
                        noText.setTitle("ALERT");
                        noText.setHeaderText(null);
                        noText.setContentText("Please enter a search request.");
                        noText.showAndWait();
                    }
                }
            });
            
            Button randomListing = new Button("Random Listing");
            randomListing.setTooltip(new Tooltip("Click to find a random listing"));
            randomListing.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    
                }
            });
            
            
            /* Begin building center */
            
            
            
            /* Begin building right */
            TableView<String[]> cart = new TableView<String[]>();
            
            Button refreshCart = new Button("Refresh Cart");
            refreshCart.setTooltip(new Tooltip("Click to refresh the cart"));
            refreshCart.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String cartQuery = new String("SELECT r.title, a.artist_name, li.format, li.price from ");
                }
            });
            
            Button delete = new Button("Delete");
            delete.setTooltip(new Tooltip("Delete selected items from cart"));
            delete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    
                }
            });
            
            /* Populate VBoxes */
            left.getChildren().addAll(new Label("\n"), new Label("Submit a Query"), new Label("\n"), new Label("Title"), titleSearch, new Label("\n"), 
                    new Label("Artist"), artistSearch, new Label("\n"), new Label("Label"), labelSearch, 
                    new Label("\n"), new Label("Format"), formatSearch, new Label("\n"), new Label("Max Price"), priceSearch, 
                    new Label("\n"), Search, new Label("\n"), randomListing);
            left.setPrefWidth(200);
            
            center.getChildren().addAll(new Label("\n"), new Label("Search Results"), new Label("\n"), table);
            
            right.getChildren().addAll(new Label("\n"), new Label("Cart"), new Label("\n"), cart, new Label("\n"), refreshCart, new Label("\n"), delete);
            right.setPrefWidth(300);
            
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
