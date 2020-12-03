package me.Samkist.CharacterDatabase;

import lombok.Getter;

import java.util.NoSuchElementException;
import java.util.Objects;

public class CharacterDatabase {


    private DoubleLinkedList<Character> characters = new DoubleLinkedList<>();
    public CharacterDatabase() {

    }

    public DoubleLinkedList<Character> getCharacters() {
        return characters;
    }

    public void addCharacter(Character c) {
        characters.add(c);
    }

    public void removeCharacter(Character c) {
        DoubleLinkedList<Character>.SamIterator it = characters.iterator();
        while(it.hasNext()) {
            if(Objects.deepEquals(it.next(), c)) {
                it.remove();
                return;
            }
        }
    }

}
