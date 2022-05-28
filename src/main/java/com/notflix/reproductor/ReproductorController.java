package com.notflix.reproductor;

import com.notflix.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class ReproductorController implements Initializable {
    @FXML
    private Button btnClose;
    @FXML
    private ImageView playImage;
    @FXML
    private Button btnFullScreen;

    @FXML
    private Button btnMinimize;

    @FXML
    private Button btnPlay;

    @FXML
    private Label labelTiempo;

    @FXML
    private Slider sliderDuration;

    @FXML
    private Slider sliderVolume;

    @FXML
    private AnchorPane barraSuperior;

    @FXML
    private MediaView video;
    @FXML
    private Pane rellenoSliderTiempo;
    private Media media;
    private MediaPlayer mp;
    private double offSetX, offSetY;
    private int horas, minutos, segundos;
    private double width, height;


    @FXML
    void cambiarDuracion(MouseEvent event) {
        horas = (int) (mp.getTotalDuration().toHours() * (sliderDuration.getValue() / 100.0));
        minutos = (int) (mp.getTotalDuration().toMinutes() * sliderDuration.getValue() / 100 - 60 * horas);
        segundos = (int) (mp.getTotalDuration().toSeconds() * sliderDuration.getValue() / 100 - 60 * minutos);
        mp.seek(mp.getTotalDuration().multiply(sliderDuration.getValue() / 100.0));
        labelTiempo.setText(imprimeHoras() + " : " + imprimeMinutos() + " : " + imprimeSegundos());
        sliderDuration.setValue(mp.getCurrentTime().toSeconds() / mp.getTotalDuration().toSeconds() * 100);
    }

    @FXML
    void close(ActionEvent event) throws InterruptedException {
        ((Stage) (video.getScene().getWindow())).close();
        tareaOcultarUI.stop();
        tareaMostrarUI.stop();
        tareaOffSetSlider.stop();
    }

    @FXML
    void colorearBlanquito(MouseEvent event) {
        final boolean PULSAR_BTN_FULLSCREEN = event.getTarget().equals(btnFullScreen);
        final boolean PULSAR_BTN_MINIMIZE = event.getTarget().equals(btnMinimize);
        final boolean PULSAR_BTN_PLAY = event.getTarget().equals(btnPlay);

        if (PULSAR_BTN_FULLSCREEN) {
            btnFullScreen.setStyle("-fx-background-color:#494b4d");
        }
        if (PULSAR_BTN_MINIMIZE) {
            btnMinimize.setStyle("-fx-background-color:#494b4d");
        }
        if (PULSAR_BTN_PLAY) {
            btnPlay.setStyle("-fx-background-color:#494b4d");
        }


    }

    @FXML
    void colorearRojo(MouseEvent event) {
        btnClose.setStyle("-fx-background-color:#e34444");
    }

    @FXML
    void colorearTransparente(MouseEvent event) {
        btnMinimize.setStyle("-fx-background-color:transparent");
        btnClose.setStyle("-fx-background-color:transparent");
        btnFullScreen.setStyle("-fx-background-color:transparent");
        btnPlay.setStyle("-fx-background-color:transparent");
    }

    @FXML
    void exitFullScreen(KeyEvent event) {
        final boolean PULSAR_F11 =event.getCode().equals(event.getCode().F11);
        final boolean PULSAR_ESC =event.getCode().equals(event.getCode().ESCAPE) ;
        if (PULSAR_F11 || PULSAR_ESC) {
            miniWindowMode();
            reescalarBotones();
        }
    }

    @FXML
    void goFullScreen(MouseEvent event) {
        boolean EN_PANTALLA_COMPLETA = ((Stage) (video.getScene().getWindow())).isFullScreen();

        if (EN_PANTALLA_COMPLETA) {
            miniWindowMode();
            reescalarBotones();
        } else {
            fullScreenMode();
            reescalarBotones();
        }
    }

    private void miniWindowMode() {
        setPantallaCompleta(false);
        video.setY(0);
        barraSuperior.setVisible(true);
        video.setFitWidth(video.getScene().getWindow().getWidth());
        video.setFitHeight(video.getScene().getWindow().getHeight());
    }

    private void fullScreenMode() {
        setPantallaCompleta(true);
        video.setY(-25);
        barraSuperior.setVisible(false);
        video.setFitWidth(width);
        video.setFitHeight(height);
    }


    @FXML
    void minimize(ActionEvent event) {
        ((Stage) (video.getScene().getWindow())).setIconified(true);
    }

    @FXML
    void moverVentana(MouseEvent event) {
        ((Stage) (video.getScene().getWindow())).setX(event.getScreenX() - offSetX);
        ((Stage) (video.getScene().getWindow())).setY(event.getScreenY() - offSetY);
    }

    private void setPantallaCompleta(boolean estado) {
        ((Stage) (video.getScene().getWindow())).setFullScreen(estado);
    }

    @FXML
    void play(ActionEvent event) {
        final boolean IS_NOT_PLAYING = mp.getStatus().equals(MediaPlayer.Status.PAUSED) || mp.getStatus().equals(MediaPlayer.Status.READY);
        if (IS_NOT_PLAYING) {
            mp.play();
            playImage.setImage(new Image(Main.class.getResource("Recursos/pause.png").toString()));
        } else {
            mp.pause();
            playImage.setImage(new Image(Main.class.getResource("Recursos/play.png").toString()));
        }
    }


    private void reescalarBotones() {
        Stage ventana = (Stage) video.getScene().getWindow();

        btnMinimize.setLayoutX(ventana.getWidth() - (btnClose.getWidth() * 2));
        btnClose.setLayoutX(ventana.getWidth() - (btnClose.getWidth()));
    }

    @FXML
    private void registrarOffSet(MouseEvent event) {
        offSetX = event.getSceneX();
        offSetY = event.getSceneY();
    }

    @FXML
    private void mostrarMenu(MouseEvent event) {
        btnFullScreen.setVisible(true);
        btnPlay.setVisible(true);
        sliderVolume.setVisible(true);
        sliderDuration.setVisible(true);
    }

    @FXML
    private void esconderMenu(MouseEvent event) {
        btnFullScreen.setVisible(false);
        btnPlay.setVisible(false);
        sliderVolume.setVisible(false);
        sliderDuration.setVisible(false);
    }


    @FXML
    void regularVolumen(MouseEvent event) {
        mp.setVolume(sliderVolume.getValue() / 100);
    }


    //TAREAS SEGUNDO PLANO:
    private TareaOcultarUI tareaOcultarUI;
    private TareaMostrarUI tareaMostrarUI;
    private TareaOffSetSlider tareaOffSetSlider;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {


            FileChooser fc = new FileChooser();
            fc.setTitle("ELIJA EL ARCHIVO QUE DESEA REPRODUCIR");
            ArrayList<FileChooser.ExtensionFilter> efs = new ArrayList<>();
            efs.add(new FileChooser.ExtensionFilter("MP4", "*mp4"));
            efs.add(new FileChooser.ExtensionFilter("MPEG", "*mpeg"));
            fc.getExtensionFilters().addAll(efs);
            File file = fc.showOpenDialog(new Stage());

            if (file == null) {
                //media = new Media("http://download2390.mediafire.com/5fy96ge4szbg/jrds7j9rbbdfr8t/1+resubido.mp4");
                //media = new Media("http://80.30.126.234:8096/Items/f97aab02d43f6134967515648ff06577/Download?api_key=263515084b2348f0a4f31c5bcec8f83b");
            } else {

                media = new Media(file.toURI().toString());
            }

            sliderDuration.setStyle("-fx-control-inner-background: #bbbbbb;");
            sliderVolume.setStyle("-fx-control-inner-background: white;");
            width = Screen.getPrimary().getBounds().getWidth();
            height = Screen.getPrimary().getBounds().getHeight();

            mp = new MediaPlayer(media);
            video.setMediaPlayer(mp);
            mp.setVolume(0.50);
            sliderVolume.setValue(mp.getVolume() * 100);

            video.smoothProperty().set(true);
            video.setFitHeight(height / 2);
            video.setFitWidth(width / 2);


            //video.setX(0);
            //video.setY(0);

            //INICIAMOS TAREAS QUE SE EJECUTAN SIMULTANEAMENTE:
            tareaOcultarUI = new TareaOcultarUI(sliderDuration, sliderVolume, labelTiempo, btnPlay, btnFullScreen, mp,rellenoSliderTiempo);
            tareaOcultarUI.start();
            tareaMostrarUI = new TareaMostrarUI(sliderDuration, sliderVolume, labelTiempo, btnPlay, btnFullScreen, mp,rellenoSliderTiempo);
            tareaMostrarUI.start();
            tareaOffSetSlider = new TareaOffSetSlider(sliderDuration,rellenoSliderTiempo);
            tareaOffSetSlider.start();


            mp.currentTimeProperty().addListener((observableValue, duration, t1) -> {

                horas = (int) (mp.getTotalDuration().toHours() * (sliderDuration.getValue() / 100.0));
                minutos = (int) (mp.getTotalDuration().toMinutes() * sliderDuration.getValue() / 100 - 60 * horas);
                segundos = (int) (mp.getTotalDuration().toSeconds() * sliderDuration.getValue() / 100 - 60 * minutos);

                labelTiempo.setText(imprimeHoras() + " : " + imprimeMinutos() + " : " + imprimeSegundos());

                if (!sliderDuration.isPressed()) {
                    double offSet = sliderDuration.getValue()*0.15;
                    sliderDuration.setValue(mp.getCurrentTime().toSeconds() / mp.getTotalDuration().toSeconds() * 100);
                }

            });

        } catch (Exception e) {
            System.out.println(e);

        }

    }

    private String imprimeHoras() {
        if (horas < 10) {
            return ("0" + horas);
        } else {
            return String.valueOf(horas);
        }

    }

    private String imprimeMinutos() {
        if (minutos < 10) {
            return ("0" + minutos);
        } else {
            return String.valueOf(minutos);
        }

    }

    private String imprimeSegundos() {
        if (segundos < 10) {
            return ("0" + segundos);
        } else {
            return String.valueOf(segundos);
        }

    }

}
