package lah;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;
import javafx.scene.web.WebViewBuilder;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Window extends Application implements EventHandler<KeyEvent> {

    public static List<String> text = new ArrayList<>();
    public static double zoom = 1;

    public double TITLE_HEIGHT = 40;
    public double WIDTH = 1920;
    public double HEIGHT = 1080 - TITLE_HEIGHT;

    private WebView webView;

    @Override
    public void start(Stage stage) throws Exception {

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        WIDTH = size.getWidth();
        HEIGHT = size.getHeight() - TITLE_HEIGHT;
        /*Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext context = canvas.getGraphicsContext2D();

        Scene scene = new Scene(new Group(canvas));*/
        final Group rootGroup = new Group();
        rootGroup.getChildren().add(buildWebView());
        //.add(prepareAccordion(titleToUrl));
        final Scene scene = new Scene(rootGroup, WIDTH, HEIGHT, Color.WHITE);
        stage.setScene(scene);
        stage.show();

        stage.setResizable(true);
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);

        stage.setTitle("AsKEE");

        stage.show();

        /*context.setFill(Color.BLACK);
        context.setStroke(Color.BLACK);
        context.setFont(Font.font(java.awt.Font.MONOSPACED, 1.5));
        context.fillText(text, 0, 0);

        scene.setOnKeyPressed(this);
        scene.setOnKeyReleased(this);*/

        new java.util.Timer().schedule(
                new java.util.TimerTask() {

                    int i = 0;

                    @Override
                    public void run() {
                        i = (i + 1) % text.size();
                        Platform.runLater(() -> setContent(i));
                    }
                },
                100, 100
        );

    }

    private WebView buildWebView()
    {
        webView =
                WebViewBuilder.create().prefHeight(HEIGHT).prefWidth(WIDTH).build();
//        webView.getEngine().loadContent("<code><span style=\"display:block;line-height:8px; font-size: 5px; white-space:pre;font-family: monospace;color: black; background: white;\">"
//        + text + "</span></code>");//.load(url);
        setContent(0);
        webView.setZoom(zoom);

        return webView;
    }

    private void setContent(int i){
        webView.getEngine().loadContent("<code><span style=\"display:block;line-height:8px; font-size: 12px; white-space:pre;font-family: monospace;color: black; background: #686868;\">"
                + text.get(i) + "</span></code>");
    }

    @Override
    public void handle(KeyEvent event) {
        //System.out.println(event.getCode().toString());
        if(event.getCode() == KeyCode.Q)
            System.exit(0);
    }
}
