package lah;

import javafx.application.Application;
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

public class Window extends Application implements EventHandler<KeyEvent> {

    public static String text = "";
    public static double zoom = 1;

    public double TITLE_HEIGHT = 40;
    public double WIDTH = 1920;
    public double HEIGHT = 1080 - TITLE_HEIGHT;

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

        stage.setResizable(false);
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

    }

    private WebView buildWebView()
    {
        final WebView webView =
                WebViewBuilder.create().prefHeight(HEIGHT).prefWidth(WIDTH).build();
        webView.getEngine().loadContent("<code><span style=\"display:block;line-height:8px; font-size: 5px; white-space:pre;font-family: monospace;color: black; background: white;\">"
        + text + "</span></code>");//.load(url);
        webView.setZoom(zoom);
        return webView;
    }

    @Override
    public void handle(KeyEvent event) {
        //System.out.println(event.getCode().toString());
        if(event.getCode() == KeyCode.Q)
            System.exit(0);
    }
}
