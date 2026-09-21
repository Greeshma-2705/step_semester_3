package main.java.oop_fundamentals.assignment_problems;
interface Attackable {
    String attack();
    String attack(String weaponName);
}

interface Defendable {
    String defend();
}

abstract class GameCharacter {
    private static int counter = 0;
    private final String characterId;

    public GameCharacter() {
        counter++;
        this.characterId = "CHAR_" + counter;
    }

    public abstract String getSpecialMove();

    public String getCharacterId() {
        return characterId;
    }
}

class Warrior extends GameCharacter implements Attackable, Defendable {
    private String name;

    public Warrior(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String attack() {
        return name + " strikes with a blade";
    }

    @Override
    public String attack(String weaponName) {
        return name + " strikes with an " + weaponName;
    }

    @Override
    public String defend() {
        return name + " raises a shield";
    }

    @Override
    public String getSpecialMove() {
        return name + " unleashes Whirlwind Slash";
    }
}

class Trap implements Defendable {
    private String trapType;

    public Trap(String trapType) {
        this.trapType = trapType;
    }

    public String getTrapType() {
        return trapType;
    }

    @Override
    public String defend() {
        return trapType + " triggers automatically";
    }
}

public class Arena {
    public static void resolveDefense(Defendable[] combatants) {
        for (Defendable combatant : combatants) {
            System.out.println(combatant.defend());
        }
    }

    public static void main(String[] args) {
        Warrior w = new Warrior("Kael");
        System.out.println("Character ID: " + w.getCharacterId());
        System.out.println(w.attack());
        System.out.println(w.attack("Iron Sword"));
        System.out.println(w.defend());
        System.out.println(w.getSpecialMove());

        System.out.println("-----------------------------------");

        Trap t = new Trap("Spike Pit");
        System.out.println(t.defend());

        System.out.println("-----------------------------------");

        Defendable[] combatants = new Defendable[]{w, t};
        resolveDefense(combatants);
    }
}