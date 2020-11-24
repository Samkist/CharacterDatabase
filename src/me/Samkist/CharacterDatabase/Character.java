package me.Samkist.CharacterDatabase;

public class Character {

    private String name;
    private String cClass;
    private int level = 0;
    private String specialAbility;
    private String weakness;

    public Character(String name, String cClass, int level, String specialAbility, String weakness) {
        this.name = name;
        this.cClass = cClass;
        this.level = level;
        this.specialAbility = specialAbility;
        this.weakness = weakness;
    }

    public void setClass(String cClass) {
        this.cClass = cClass;
    }

    public String getCClass() {
        return this.cClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getSpecialAbility() {
        return specialAbility;
    }

    public void setSpecialAbility(String specialAbility) {
        this.specialAbility = specialAbility;
    }

    public String getWeakness() {
        return weakness;
    }

    public void setWeakness(String weakness) {
        this.weakness = weakness;
    }
}
