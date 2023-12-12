import java.util.Random;

public class Main {
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {280, 270, 250, 400, 500, 200, 300, 250};
    public static int[] heroesDamage = {10, 20, 15, 0, 5, 8, 0, 30};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Golem", "Lucky", "Witcher", "Thor"};

    public static int roundNumber;

    public static void main(String[] args) {
        showStatistics();
        while (!isGameOver()) {
            playRound();
        }
    }

    public static void medicSkill() {
        for (int i = 0; i < heroesAttackType.length; i++) {
            if (heroesAttackType[i].equals("Medic")) {
                continue;
            } else if (heroesHealth[i] < 100 && heroesHealth[i] > 0 && heroesHealth[3] > 0) {
                heroesHealth[i] += 50;
                System.out.println("Medic вылечил:" + heroesAttackType[i]);
                break;
            }
        }
    }

    public static void golemSkill() {
        double takeDamage = 0;
        boolean oneHit = false;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (bossHealth >= 0 && heroesHealth[4] > 0) {
                if (heroesAttackType[i] != "Golem") {
                    if (!oneHit) {
                        oneHit = true;
                        double damageToOtherPlayer = bossDamage * 0.2;
                        heroesHealth[i] -= damageToOtherPlayer;
                        // heroesHealth[i] = heroesHealth[i] - damageToOtherPlayer;
                        takeDamage = damageToOtherPlayer;
                    }
                }
            }
        }
        if (heroesHealth[4] > 0) {
            heroesHealth[4] -= takeDamage;
            System.out.println("Golem принял удар босса");
        }
    }

    public static void luckySkill() {
        Random random = new Random();
        boolean randomEscape = random.nextBoolean();
        if (randomEscape) {
            if (heroesHealth[5] > 0) {
                heroesHealth[5] += bossDamage;
                System.out.println("Lucky ускользнул от босса");
            }
        }
    }

    public static void witcherSkill() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (bossHealth >= 0 && heroesHealth[6] > 0) {
                if (heroesHealth[i] <= 0) {
                    if (heroesAttackType[i] != "Witcher") {
                        heroesHealth[i] += heroesHealth[6];
                        heroesHealth[6] = 0;
                        System.out.println("Witcher оживил " + heroesAttackType[i]);
                    }
                }
            }
        }
    }

    public static void thorSkill() {
        Random random2 = new Random();
        boolean stun = random2.nextBoolean();
        if (stun) {
            for (int i = 0; i < heroesDamage[i]; i++) {
                heroesHealth[i] += bossDamage;

            }
            System.out.println("thor оглушил босса");
        }
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        bossAttacks();
        heroesAttack();
        medicSkill();
        golemSkill();
        luckySkill();
        witcherSkill();
        thorSkill();
        showStatistics();

    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesHealth.length); // 0,1,2
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void heroesAttack() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; //2,3,4,5,6,7,8,9,10
                    damage = heroesDamage[i] * coeff;
                    System.out.println("Critical damage: " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }
            }
        }
    }

    public static void bossAttacks() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
       /* if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;*/
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void showStatistics() {
        /*String defence;
        if (bossDefence == null) {
            defence = "No defence";
        } else {
            defence = bossDefence;
        }*/
        System.out.println("ROUND " + roundNumber + " ---------------");
        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage + " defence: " +
                (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i] + " damage: " + heroesDamage[i]);
        }
    }
}
