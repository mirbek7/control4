import java.util.*;

public class AppRunner {
    private static List<Cat> cats;
    private int choice;
    Scanner scanner = new Scanner (System.in);

    public void run() {

        cats = new ArrayList<> ();
        cats.add (new Cat ("Peach", 11));
        cats.add (new Cat ("Poppy", 12));
        cats.add (new Cat ("Jasper", 9));


        while (true) {
            System.out.println ("1. Show Cats");
            System.out.println ("2. Add Cat");
            System.out.println ("3. Perform Action");
            System.out.println ("4. Simulate Next Day");
            System.out.println ("5. Next Day");
            System.out.println ("0. Exit");

            int choice = scanner.nextInt ();

            switch (choice) {
                case 1:
                    AppRunner.showCats ();
                    break;
                case 2:
                    System.out.print ("Enter Cat Name: ");
                    String name = scanner.next ();
                    System.out.print ("Enter Cat Age: ");
                    scanner.nextInt ();
                    AppRunner.addCat ();
                    break;
                case 3:
                    AppRunner.showCats ();
                    System.out.print ("Enter Cat Index: ");
                    int catIndex = scanner.nextInt ();
                    System.out.println ("1. Feed Cat");
                    System.out.println ("2. Play with Cat");
                    System.out.println ("3. Heal Cat");
                    System.out.print ("Enter Action Index: ");
                    int actionIndex = scanner.nextInt ();
                    AppRunner.performAction ();
                    break;
                case 4:
                    AppRunner.simulateNextDay ();
                    System.out.println ("Next day simulated.");
                    break;
                case 5:
                    AppRunner.nextDay ();
                    System.out.println ("Invalid choice. Please try again.");
                case 0:
                    System.exit (0);
                    break;
                default:
                    System.out.println ("Invalid choice");

            }
        }


    }


    private static void showCats() {
        System.out.printf ("%-3s | %-10s | %-7s | %-10s | %-7s | %-16s%n",
                "#", "Имя", "Возраст", "Здоровье", "Настроение", "Сытость");
        System.out.println ("----------------------------------------------------------------------");
        for (int i = 0; i < cats.size (); i++) {
            Cat cat = cats.get (i);
            System.out.printf ("%-3d | %-10s | %-7d | %-10d | %-10d | %-20d%n",
                    (i + 1), cat.getName (), cat.getAge (), cat.getHealthLevel (),
                    cat.getMoodLevel (), cat.getHungerLevel ());
        }
        System.out.println ("-----------------------------------------------------------------------");
    }

    private static void addCat() {
        Scanner scanner = new Scanner (System.in);

        String name;
        do {
            System.out.print ("Enter Cat Name: ");
            name = scanner.nextLine ().trim ();
            if (name.isEmpty ()) {
                System.out.println ("Cat name cannot be empty. Please try again.");
            }
        } while (name.isEmpty ());

        int age = 0;
        boolean validInput = false;
        do {
            try {
                System.out.print ("Enter Cat Age: ");
                age = scanner.nextInt ();
                scanner.nextLine ();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println ("Invalid input for age. Please enter a valid integer.");
                scanner.nextLine ();
            }
        } while (!validInput);

        cats.add (new Cat (name, age));
        sortCatsByAverageLifeLevel ();
        System.out.println ("Cat added successfully.");
    }

    private static int getRandomValueInRange() {
        return new Random ().nextInt (18 - 1 + 1) + 1;
    }

    private static void sortCatsByAverageLifeLevel() {
        cats.sort (Comparator.comparingInt (Cat::calculateAverageLifeLevel).reversed ());
    }


    private static void performAction() {
        Scanner scanner = new Scanner (System.in);

        showCats ();

        System.out.println ("""
                    Choose an action:
                    1.Feed Cat
                    2.Play with Cat
                    3.Heal Cat
                    a.Get a new pet
                """);

        int actionChoice;
        try {
            actionChoice = scanner.nextInt ();
        } catch (Exception e) {
            System.out.println ("Invalid input. Please enter a number.");
            scanner.nextLine ();
            return;
        }

        System.out.print ("Enter Cat Index or Name: ");
        String catChoice = scanner.next ();


        Cat selectedCat = null;
        try {
            int catIndex = Integer.parseInt (catChoice) - 1;
            selectedCat = cats.get (catIndex);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            selectedCat = findCatByName (catChoice);
        }

        if (selectedCat == null) {
            System.out.println ("Cat not found.");
            return;
        }

        switch (actionChoice) {
            case 1:
                selectedCat.feed ();
                System.out.println (selectedCat.getName () + " has been fed.");
                break;
            case 2:
                selectedCat.play ();
                System.out.println (selectedCat.getName () + " has played.");
                break;
            case 3:
                selectedCat.heal ();
                System.out.println (selectedCat.getName () + " has been healed.");
                break;
            default:
                System.out.println ("Invalid action.");
        }

        sortCatsByAverageLifeLevel ();
    }

    private static Cat findCatByName(String name) {
        for (Cat cat : cats) {
            if (cat.getName ().equalsIgnoreCase (name)) {
                return cat;
            }
        }
        return null;
    }

    private static void simulateNextDay() {
        for (Cat cat : cats) {
            int hungerChange = getRandomValueInRange (1, 5);
            cat.setHungerLevel (cat.getHungerLevel () - hungerChange);

            int moodChange = getRandomValueInRange (-3, 3);
            cat.setMoodLevel (cat.getMoodLevel () + moodChange);

            int healthChange = getRandomValueInRange (-3, 3);
            cat.setHealthLevel (cat.getHealthLevel () + healthChange);

            cat.updateLevels ();
        }

        System.out.println ("Day has passed. Cat attributes have been updated.");

    }

    private static int getRandomValueInRange(int min, int max) {
        return new Random ().nextInt (max - min + 1) + min;
    }

    public static void nextDay() {
        for (Cat cat : cats) {
            cat.resetActionPerformedToday ();
        }
        for (Cat cat : cats) {
            int hungerChange = getRandomValueInRange (1, 5);
            cat.setHungerLevel (cat.getHungerLevel () - hungerChange);

            int moodChange = getRandomValueInRange (-3, 3);
            cat.setMoodLevel (cat.getMoodLevel () + moodChange);

            int healthChange = getRandomValueInRange (-3, 3);
            cat.setHealthLevel (cat.getHealthLevel () + healthChange);

            cat.updateLevels ();
        }
        System.out.println ("Day has passed. Cat attributes have been updated.");
    }
}
