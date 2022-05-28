package com.notflix.reproductor;

import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;

public class TareaOffSetSlider extends Thread {


    private Slider sliderDuration;
    private Pane rellenoSliderTiempo;

    public TareaOffSetSlider(Slider sliderDuration, Pane rellenoSliderTiempo) {
        this.rellenoSliderTiempo = rellenoSliderTiempo;
        this.sliderDuration = sliderDuration;
    }

    @Override
    public void run() {
        double offSet;

        while (true) {
            offSet = sliderDuration.getValue() * 0.15;
            if (!sliderDuration.isPressed()) {
                if (sliderDuration.getValue() < 25) {
                    rellenoSliderTiempo.setMinWidth(sliderDuration.getWidth() * (sliderDuration.getValue() / 100) - offSet + 0.015);
                } else {
                    rellenoSliderTiempo.setMinWidth(sliderDuration.getWidth() * (sliderDuration.getValue() / 100) - offSet);
                }
            }
            try {
                Thread.sleep(200L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


    }
}
