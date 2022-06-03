package com.notflix.modelo;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Pelicula extends ImageView {
    private String urlPelicula;

    public Pelicula(Image image, String urlPelicula) {
        super(image);
        this.urlPelicula = urlPelicula;
    }




}
