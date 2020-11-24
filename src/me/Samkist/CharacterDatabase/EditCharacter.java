package me.Samkist.CharacterDatabase;

import BreezySwing.GBDialog;
import BreezySwing.IntegerField;

import javax.swing.*;
import java.util.Objects;
import java.util.Optional;

public class EditCharacter extends GBDialog {

    private Main main;
    private CharacterDatabase characterDatabase;
    private Character character;
    private final JButton addCharacter = addButton("Add Character", 2, 1, 1, 1);
    private final JButton deleteCharacter = addButton("Delete Character", 4, 1, 1, 1);
    private final JLabel nameLabel = addLabel("Name: ", 1, 2, 1, 1);
    private final JTextField nameField = addTextField("", 1, 3, 1, 1);
    private final JLabel classLabel = addLabel("Class: ", 2, 2, 1, 1);
    private final JTextField classField = addTextField("", 2, 3, 1, 1);
    private final JLabel levelLabel = addLabel("Level: ", 3, 2, 1, 1);
    private final IntegerField levelField = addIntegerField(0, 3, 3, 1, 1);
    private final JLabel abilityLabel = addLabel("Ability: ", 4, 2, 1, 1);
    private final JTextField abilityField = addTextField("", 4, 3, 1, 1);
    private final JLabel weaknessLabel = addLabel("Weakness: ", 5, 2, 1, 1);
    private final JTextField weaknessField = addTextField("", 5, 3, 1, 1);

    public EditCharacter(Main main) {
        super(main);
        this.main = main;
        characterDatabase = main.getCharacterDatabase();
        deleteCharacter.setVisible(false);
        setup();
    }

    public EditCharacter(Main main, Character character) {
        super(main);
        deleteCharacter.setVisible(true);
        this.main = main;
        this.character = character;
        this.addCharacter.setText("Edit Character");
        nameField.setText(character.getName());
        classField.setText(character.getCClass());
        levelField.setNumber(character.getLevel());
        abilityField.setText(character.getSpecialAbility());
        weaknessField.setText(character.getWeakness());
        setup();
    }

    public void setup() {
        setName("Add Character");
        setSize(400, 400);
        setVisible(true);
    }

    @Override
    public void buttonClicked(JButton jButton) {
        if(jButton.equals(addCharacter)) {
            if (Objects.isNull(character))
                characterDatabase.addCharacter(new Character(
                        nameField.getText(),
                        classField.getText(),
                        levelField.getNumber(),
                        abilityField.getText(),
                        weaknessField.getText()
                ));
            else {
                character.setName(nameField.getText());
                character.setClass(classField.getText());
                character.setLevel(levelField.getNumber());
                character.setSpecialAbility(abilityField.getText());
                character.setWeakness(weaknessField.getText());
                character = null;
            }
        } else {
            main.getCharacterDatabase().removeCharacter(character);
        }
        main.updateList();
        setVisible(false);
    }


}
