package week7;

/**
 * Problem 4: Arena Battle Simulator
 * Multiple interfaces (Attackable, Defendable), abstract GameCharacter with static ID, and non-character Trap.
 */
public class Problem4_ArenaBattleSimulator {

    public interface Attackable {
        String attack();
        String attack(String weaponName);
    }

    public interface Defendable {
        String defend();
    }

    public static abstract class GameCharacter {
        private static int characterCounter = 0;
        private final String characterId;
        private final String name;

        public GameCharacter(String name) {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Character name cannot be blank");
            }
            characterCounter++;
            this.characterId = "CHAR-" + characterCounter;
            this.name = name.trim();
        }

        public abstract String getSpecialMove();

        public String getCharacterId() {
            return characterId;
        }

        public String getName() {
            return name;
        }
    }

    public static class Warrior extends GameCharacter implements Attackable, Defendable {
        public Warrior(String name) {
            super(name);
        }

        @Override
        public String attack() {
            return getName() + " strikes with a blade";
        }

        @Override
        public String attack(String weaponName) {
            String article = (weaponName != null && "AEIOUaeiou".indexOf(weaponName.charAt(0)) != -1) ? "an" : "a";
            return getName() + " strikes with " + article + " " + weaponName;
        }

        @Override
        public String defend() {
            return getName() + " raises a shield";
        }

        @Override
        public String getSpecialMove() {
            return getName() + " unleashes Whirlwind Slash";
        }
    }

    public static class Trap implements Defendable {
        private final String trapType;

        public Trap(String trapType) {
            if (trapType == null || trapType.trim().isEmpty()) {
                throw new IllegalArgumentException("Trap type cannot be blank");
            }
            this.trapType = trapType.trim();
        }

        @Override
        public String defend() {
            return trapType + " triggers automatically";
        }
    }

    public static void resolveDefense(Defendable[] combatants) {
        if (combatants == null) return;
        for (Defendable d : combatants) {
            if (d != null) {
                System.out.println(d.defend());
            }
        }
    }

    public static void main(String[] args) {
        Warrior w = new Warrior("Kael");
        System.out.println(w.attack());
        System.out.println(w.attack("Iron Sword"));
        System.out.println(w.defend());
        System.out.println(w.getSpecialMove());

        Trap t = new Trap("Spike Pit");
        System.out.println(t.defend());

        System.out.println("\n--- Resolving defense polymorphically ---");
        resolveDefense(new Defendable[]{w, t});
    }
}
