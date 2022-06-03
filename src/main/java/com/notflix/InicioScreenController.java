package com.notflix;

import com.notflix.modelo.Pelicula;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class InicioScreenController implements Initializable {

    ArrayList<Pelicula> peliculas = new ArrayList<>();

    ArrayList<ImageView> caratulasPeliculas;
    @FXML
    private GridPane listaPeliculasGrid;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Pelicula pelicula = new Pelicula(new Image(getClass().getResource("recursos/DR_STRANGE.jpg") +
                "54347174621000&source=images&cd=vfe&ved=0CAwQjRxqFwoTCOj-7c6pkfgCFQAAAAAdAAAAABAD"), "adad");

        ImageView imageView = new ImageView(new Image((getClass().getResource("recursos/DR_STRANGE.jpg")).toString()));
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);


        imageView.setOnMouseClicked(mouseEvent -> {
            System.out.println("hola");
        });
        pelicula.setOnMouseClicked(mouseEvent -> {
            System.out.println("hola");
        });





        GridPane.setConstraints(imageView,0,0);

        listaPeliculasGrid.getChildren().addAll(imageView);


    }
}
