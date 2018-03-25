package lah;

import javafx.application.Application;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args){

//        Scanner scanner = new Scanner(System.in);
//
//        HashSet<String> h = new HashSet<>();
//        while(scanner.hasNextLine()){
//            String l = scanner.nextLine();
//            if(l.contains("END")) break;
//            for(int i = 0; i < l.length(); i++)
//                h.add(l.substring(i, i+1));
//        }
//
//        h.forEach(System.out::println);
//
//
//
//        if(true) return;

        String file = "social-media";

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        //clipboard.setContents(sel, sel);

        String ss = "";

        for (List<String> l : ConversionUtil.convert(1,"input/" + file + ".jpg", "output/" + file + ".png")) {
            String s = "";
            for (String s1 : l)
                s += s1;
            ss += (ss.equals("") ? "" : "\n") + s;
            //System.out.println(s);
        }

        /*StringSelection sel = new StringSelection("<code><span style=\"display:block;line-height:8px; font-size: 4px; font-weight:bold;white-space:pre;font-family: monospace;color: black; background: white;\">"
                + ss + "</span></code>");

        clipboard.setContents(sel, sel);*/

        Window.text = ss;
        Window.zoom = 0.3;
        Application.launch(Window.class);
    }

}
