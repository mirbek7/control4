import java.util.Random;

class Cat {
    private final String name;
    private final int age;
    private int hungerLevel;
    private int moodLevel;
    private int healthLevel;
    private boolean actionTakenToday;


    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getHungerLevel() {
        return hungerLevel;
    }

    public void setHungerLevel(int hungerLevel) {
        this.hungerLevel = hungerLevel;
    }

    public int getMoodLevel() {
        return moodLevel;
    }

    public void setMoodLevel(int moodLevel) {
        this.moodLevel = moodLevel;
    }

    public int getHealthLevel() {
        return healthLevel;
    }

    public void setHealthLevel(int healthLevel) {
        this.healthLevel = healthLevel;
    }


    public Cat(String name, int age) {
        this.name = name;
        this.age = age;
        this.hungerLevel = getRandomValueInRange ();
        this.moodLevel = getRandomValueInRange ();
        this.healthLevel = getRandomValueInRange ();
        this.actionTakenToday = false;
    }


    public void feed() {
        if (!actionTakenToday) {
            hungerLevel += 10;
            moodLevel += 10;

            hungerLevel = hungerLevel + (age <= 5 ? 7 : (age <= 10 ? 5 : 4));
            moodLevel = moodLevel + (age <= 5 ? 7 : (age <= 10 ? 5 : 4));
            updateLevels ();
            actionTakenToday = true;
        }
    }

    public void play() {
        if (!actionTakenToday) {
            moodLevel += 15;
            healthLevel += 15;
            hungerLevel -= 10;

            moodLevel += age <= 5 ? 7 : (age <= 10 ? 5 : 4);
            healthLevel += age <= 5 ? 7 : (age <= 10 ? 5 : 4);
            hungerLevel -= age <= 5 ? 3 : (age <= 10 ? 5 : 6);
            updateLevels ();
            actionTakenToday = true;
        }
    }

    public void heal() {
        if (!actionTakenToday) {
            healthLevel += 20;
            moodLevel -= 10;
            hungerLevel -= 10;

            healthLevel += age <= 5 ? 7 : (age <= 10 ? 5 : 4);
            moodLevel -= age <= 5 ? 3 : (age <= 10 ? 5 : 6);
            hungerLevel -= age <= 5 ? 3 : (age <= 10 ? 5 : 6);
            updateLevels ();
            actionTakenToday = true;
        }
    }


    private int getRandomValueInRange() {
        return new Random ().nextInt (80 - 20 + 1) + 20;
    }

    public int calculateAverageLifeLevel() {
        return (hungerLevel + moodLevel + healthLevel) / 3;
    }

    void updateLevels() {
        healthLevel = Math.min (Math.max (healthLevel, 0), 100);
        moodLevel = Math.min (Math.max (moodLevel, 0), 100);
        hungerLevel = Math.min (Math.max (hungerLevel, 0), 100);
    }

    @Override
    public String toString() {
        return String.format ("Cat{name='%s', age=%d, hunger=%d, mood=%d, health=%d}",
                name, age, hungerLevel, moodLevel, healthLevel);
    }

    public void resetActionPerformedToday() {
        this.actionTakenToday = false;
    }
}