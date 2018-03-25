package lah;

import javafx.application.Application;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args){
        // 0-3

        runDemo(4);
    }

    private static void runDemo(int demo){

        if(demo == 0) {
            Window.text = Arrays.asList(ConversionUtil.convert(true, 1, 5, "input/pepe.jpg", "output/pepe.png"));
            Window.zoom = 1;
            Window.fontSize = 12;
            Window.background = "white";
            Application.launch(Window.class);

        }
        else if(demo == 1) {

            Window.text = Arrays.asList(ConversionUtil.convert(true, 1, 6, "input/lah.png", "output/lah.png"));
            Window.zoom = 0.6;
            Window.fontSize = 12;
            Window.background = "white";
            Application.launch(Window.class);

        }
        else if(demo == 2) {
            Window.text = ConversionUtil.convertGif(true, 1, 5, 55, 1, "input/lah.gif");
            Window.fontSize = 12;
            Window.background = "#a8a8a8";//"#686868";
            Window.loopMax = 55;
            Window.period = 50;
            Application.launch(Window.class);
        }
        else if(demo == 3) {
            Window.text = ConversionUtil.convertGif(true, 1, 5, -1, 1, "input/nyan.gif");
            Window.zoom = 1;
            Window.fontSize = 8;
            Window.background = "black";
            Window.period = 30;
            Application.launch(Window.class);
        }
        else if(demo == 4) {
            Window.text = ConversionUtil.convertGif(true, 1, 5, -1, 1, "input/tumblr.gif");
            Window.zoom = 0.5;
            Window.fontSize = 8;
            Window.background = "black";
            Window.period = 30;
            Application.launch(Window.class);
        }
        else if(demo == 5) {

            Window.text = ConversionUtil.convertGif(true, 2, 5, -1, 1, "input/lah2.gif");
            Window.zoom = 0.5;
            Window.fontSize = 5;
            Window.background = "#a8a8a8";//"#686868";
            Window.loopMax = -1;
            Window.period = 50;
            Application.launch(Window.class);

        }
        else if(demo == 6) {
            Window.text = Arrays.asList(ConversionUtil.convert(false, 1, 0.5, "input/lah3.png", "output/lah3.png"));
            Window.zoom = 0.2;
            Window.fontSize = 5;
            Window.background = "white";
            Application.launch(Window.class);
        }
    }

    private static String concat(List<String> l){
        String s = "";
        for (String s1 : l)
            s += s1;
        return s;
    }

}
