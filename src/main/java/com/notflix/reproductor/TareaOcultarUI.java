package com.notflix.reproductor;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;

import java.awt.*;

public class TareaOcultarUI extends Thread {

    private MouseEvent event;
    private Slider sliderDuracion, sliderVolumen;
    private Label labelDuracion;
    private Button btnPlay, btnFullScreen;
    private MediaPlayer mediaPlayer;
    private Pane rellenoSliderTiempo;

    public TareaOcultarUI() {
    }

    public TareaOcultarUI(Slider sliderDuracion, Slider sliderVolumen, Label labelDuracion, Button btnPlay,
                          Button btnFullScreen, MediaPlayer mediaPlayer, Pane rellenoSliderTiempo) {
        this();
        this.btnFullScreen = btnFullScreen;
        this.btnPlay = btnPlay;
        this.sliderDuracion = sliderDuracion;
        this.labelDuracion = labelDuracion;
        this.sliderVolumen = sliderVolumen;
        this.mediaPlayer = mediaPlayer;
        this.rellenoSliderTiempo = rellenoSliderTiempo;


    }


    @Override
    public void run() {
        try {
            while (true) {
                double posRatonX = MouseInfo.getPointerInfo().getLocation().getX();
                double posRatonY = MouseInfo.getPointerInfo().getLocation().getY();
                Thread.sleep(150L);
                boolean NO_MUEVE_RATON = (MouseInfo.getPointerInfo().getLocation().getX() == posRatonX || MouseInfo.getPointerInfo().getLocation().getY() == posRatonY);
                boolean VIDEO_REPRODUCIENDOSE = mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING);
                if (VIDEO_REPRODUCIENDOSE && NO_MUEVE_RATON) {
                    esconderBarras();
                    Thread.sleep(5000L);//Hacemos que no pueda volver a esconderse durante 5 segundos.
                }
            }
        } catch (Exception ex) {
            System.out.println("Ha ocurrido el siguiente error: " + ex);
        }
    }

    //metodo
    public void esconderBarras() {
        sliderDuracion.setVisible(false);
        sliderVolumen.setVisible(false);
        labelDuracion.setVisible(false);
        btnPlay.setVisible(false);
        btnFullScreen.setVisible(false);
        rellenoSliderTiempo.setVisible(false);
    }
}
