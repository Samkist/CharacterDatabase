package me.Samkist.CharacterDatabase;

import BreezySwing.GBFrame;

import javax.swing.*;
import java.util.Iterator;
import java.util.LinkedList;

public class CharacterDatabase extends GBFrame {

    private static JFrame frame = new CharacterDatabase();

    private final JButton add = addButton("New Word", 1, 1, 1, 1);
    private final JButton generate = addButton("Generate Sentence", 2, 1, 1, 1);
    private final JList<String> nounList = addList(1, 2, 1, 4);
    private final JList<String> verbList = addList(1, 3, 1, 4);
    private final JList<String> sentenceList = addList(1, 4, 1, 4);



    public CharacterDatabase() {
        //Thread thread = new Thread(new MyRunnable(sentenceGenerator, this));
        //thread.start();

        DoubleLinkedList<String> list = new DoubleLinkedList<>();

        for(int i = 0; i <= 10; i++) {
            list.add("test" + i);
        }

        list.forEach(s -> {
            System.out.println(s);
        });

        DoubleLinkedList<String>.SamIterator it = list.iterator();

        updateList();
    }

    public static void main(String[] args) {
        frame.setSize(1200, 600);
        frame.setTitle("Character Database");
        //frame.setVisible(true);
    }


    public void updateList() {

    }

    @Override
    public void buttonClicked(JButton jButton) {

        updateList();
    }

    public void listDoubleClicked(JList<String> listObj, String itemClicked) {

    }
}
