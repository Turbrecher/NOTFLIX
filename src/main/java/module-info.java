module com.notflix {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;


    opens com.notflix to javafx.fxml;
    exports com.notflix;
    exports com.notflix.reproductor;
    opens com.notflix.reproductor to javafx.fxml;
}