package me.Samkist.CharacterDatabase;

import BreezySwing.GBFrame;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class Main extends GBFrame {

    private static JFrame frame = new Main();

    private final JButton add = addButton("New Character", 1, 1, 1, 1);
    private final JButton edit = addButton("Edit Character", 2, 1, 1, 1);
    private final JButton reset = addButton("Reset", 3, 1, 1, 1);
    private final JButton exit = addButton("Exit", 5, 1, 1, 1);
    private final JList<String> characterList = addList(1, 2, 1, 4);
    private final JPanel infoPanel = addPanel(1, 3, 2, 4);
    private final JTextPane infoArea = new JTextPane();
    private final JButton previous = addButton("<< Previous", 5, 3, 1, 1);
    private final JButton next = addButton("Next >>", 5, 4, 1, 1);
    private final ArrayList<JButton> buttons = new ArrayList<>();
    private CharacterDatabase characterDatabase;
    private DoubleLinkedList<Character>.SamIterator characterIterator;
    private Character cachedCharacter;



    public Main() {
        characterDatabase = new CharacterDatabase();
        infoArea.setEditable(false);
        infoPanel.setLayout(new BorderLayout());
        buttons.add(next);
        buttons.add(previous);
        buttons.add(add);
        buttons.add(edit);
        buttons.add(reset);
        buttons.add(exit);
        buttons.forEach(b -> {
            b.setBackground(Color.DARK_GRAY);
            b.setBorder(new RoundedBorder(5));
            b.setForeground(Color.WHITE);
        });
        this.getContentPane().setBackground(Color.GRAY);
        characterList.setBackground(Color.DARK_GRAY);
        infoArea.setBackground(Color.DARK_GRAY);
        infoPanel.add(infoArea);
        characterList.setCellRenderer(new ListRenderer());
        appendToPane(infoArea, "Character Info: ", Color.WHITE);
        infoArea.setText("Character Info: ");
        validate();
        characterIterator = characterDatabase.getCharacters().iterator();
        updateList();
    }

    private static class RoundedBorder implements Border {

        private int radius;


        RoundedBorder(int radius) {
            this.radius = radius;
        }


        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
        }


        public boolean isBorderOpaque() {
            return true;
        }


        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width-1, height-1, radius, radius);
        }
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
        if(characterDatabase.getCharacters().contains(cachedCharacter)) {
            updateIterator();
        }
    }

    private Optional<Character> next() {
        if(characterIterator.hasNext()) {
            Character c = characterIterator.next();
            cachedCharacter = c;
            return Optional.ofNullable(c);
        }
        return Optional.empty();
    }

    private Optional<Character> previous() {
        if(characterIterator.hasPrevious()) {
            Character c = characterIterator.previous();
            cachedCharacter = c;
            return Optional.ofNullable(c);
        }
        return Optional.empty();
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
                validate();
            }
            return;
        } else if(jButton.equals(previous)) {
            Optional<Character> oPrevious = previous();
            if(oPrevious.isPresent()) {
                setInfoArea(oPrevious.get());
                validate();
            }
            return;
        }
        updateList();
    }

    private void updateIterator() {
        characterIterator = characterDatabase.getCharacters().iterator();
        while(characterIterator.hasNext()) {
            Character c = characterIterator.next();
            if(Objects.deepEquals(cachedCharacter, c)) {
                setInfoArea(c);
                return;
            }
        }
    }

    @Override
    public void listItemSelected(JList<String> jList) {
        if (Objects.isNull(characterList.getSelectedValue())) return;

        Optional<Character> oCharacter = characterDatabase.getCharacters().stream().filter(c
                -> c.getName().equalsIgnoreCase(characterList.getSelectedValue())).findFirst();

        if (!oCharacter.isPresent()) return;

        cachedCharacter = oCharacter.get();
        updateIterator();
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

    private class ListRenderer extends DefaultListCellRenderer
    {
        public Component getListCellRendererComponent( JList list,
                                                       Object value, int index, boolean isSelected,
                                                       boolean cellHasFocus )
        {
            super.getListCellRendererComponent( list, value, index,
                    isSelected, cellHasFocus );
            setForeground(Color.WHITE);

            return( this );
        }
    }

    private void appendToPane(JTextPane tp, String msg, Color c)
    {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }
}