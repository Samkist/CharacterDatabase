package me.Samkist.CharacterDatabase;

import BreezySwing.GBFrame;

import javax.swing.*;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;

public class Main extends GBFrame {

    private static JFrame frame = new Main();

    private final JButton add = addButton("New Character", 1, 1, 1, 1);
    private final JButton edit = addButton("Edit Character", 2, 1, 1, 1);
    private final JButton reset = addButton("Reset", 3, 1, 1, 1);
    private final JButton exit = addButton("Exit", 5, 1, 1, 1);
    private final JList<String> characterList = addList(1, 2, 1, 4);
    private final JTextArea infoArea = addTextArea("Character Info: ", 1, 3, 2, 4);
    private final JButton previous = addButton("<< Previous", 5, 3, 1, 1);
    private final JButton next = addButton("Next >>", 5, 4, 1, 1);
    private CharacterDatabase characterDatabase;
    private DoubleLinkedList<Character>.SamIterator characterIterator;
    private int cachedIndex;



    public Main() {
        //Thread thread = new Thread(new  MyRunnable(sentenceGenerator, this));
        //thread.start();
        characterDatabase = new CharacterDatabase();
        characterIterator = characterDatabase.getCharacters().iterator();
        updateList();
    }

    public void reset() {
        characterDatabase = new CharacterDatabase();
        characterIterator = characterDatabase.getCharacters().iterator();
    }

    public CharacterDatabase getCharacterDatabase() {
        return characterDatabase;
    }

    public static void main(String[] args) {
        frame.setSize(1200, 600);
        frame.setTitle("Character Database");
        frame.setVisible(true);
    }


    public void updateList() {
        DefaultListModel<String> model = (DefaultListModel<String>) characterList.getModel();
        model.clear();
        characterDatabase.getCharacters().forEach(s -> model.addElement(s.getName()));
        characterIterator = characterDatabase.getCharacters().iterator();
    }

    public Optional<Character> next() {

        if(cachedIndex + 1 > characterList.getMaxSelectionIndex()) return Optional.empty();

        int newIndex = ++cachedIndex;

        DefaultListModel<String> model = (DefaultListModel<String>) characterList.getModel();
        String name = model.get(newIndex);

        return characterDatabase.getCharacters().stream().filter(c
                -> c.getName().equalsIgnoreCase(name)).findFirst();
    }

    public Optional<Character> previous() {

        if(cachedIndex - 1 < characterList.getMinSelectionIndex()) return Optional.empty();

        int newIndex = --cachedIndex;

        DefaultListModel<String> model = (DefaultListModel<String>) characterList.getModel();
        String name = model.get(newIndex);

        return characterDatabase.getCharacters().stream().filter(c
                -> c.getName().equalsIgnoreCase(name)).findFirst();
    }



    @Override
    public void buttonClicked(JButton jButton) {
        if (jButton.equals(add)) {
            new EditCharacter(this);
        } else if (jButton.equals(edit)) {
            if (Objects.isNull(characterList.getSelectedValue())) return;

            Optional<Character> oCharacter = characterDatabase.getCharacters().stream().filter(c
                    -> c.getName().equalsIgnoreCase(characterList.getSelectedValue())).findFirst();

            if (!oCharacter.isPresent()) return;

            new EditCharacter(this, oCharacter.get());
        } else if (jButton.equals(exit)) {
            System.exit(0);
        } else if(jButton.equals(reset)) {
            reset();
        } else if(jButton.equals(next)) {
            Optional<Character> oNext = next();
            if(oNext.isPresent()) {
                setInfoArea(oNext.get());
            }
        } else if(jButton.equals(previous)) {
            Optional<Character> oPrevious = previous();
            if(oPrevious.isPresent()) {
                setInfoArea(oPrevious.get());
            }
        }
        updateList();
    }

    @Override
    public void listItemSelected(JList<String> jList) {
        if (Objects.isNull(characterList.getSelectedValue())) return;

        cachedIndex = characterList.getSelectedIndex();

        Optional<Character> oCharacter = characterDatabase.getCharacters().stream().filter(c
                -> c.getName().equalsIgnoreCase(characterList.getSelectedValue())).findFirst();

        if (!oCharacter.isPresent()) return;

        Character character = oCharacter.get();

        setInfoArea(character);
    }

    public void setInfoArea(Character c) {
        String info = "Character Info: "
                + "\n" + "Character Name: " + c.getName()
                + "\n" + "Character Class: " + c.getCClass()
                + "\n" + "Character Level: " + c.getLevel()
                + "\n" + "Character Ability: " + c.getSpecialAbility()
                + "\n" + "Character Weakness(es): " + c.getWeakness();
        infoArea.setText(info);
    }

    public void listDoubleClicked(JList<String> listObj, String itemClicked) {
        String name = itemClicked;

        Optional<Character> oCharacter = characterDatabase.getCharacters().stream().filter(c
                -> c.getName().equalsIgnoreCase(name)).findFirst();

        if(!oCharacter.isPresent()) return;

        new EditCharacter(this, oCharacter.get());
    }
}
