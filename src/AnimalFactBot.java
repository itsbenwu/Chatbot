import java.util.Random;
import java.util.Scanner;
/**
 * A program to carry on conversations with a human user about rhymes.
 * This version:
 * @author Benjamin Wu
 * @version November 2018
 */
public class AnimalFactBot {
    //emotion can alter the way our bot responds. Emotion can become more negative or positive over time.
    int emotion = 0;

    public void chatLoop(String statement) {
        Scanner in = new Scanner(System.in);
        System.out.println(getGreeting());
        while (!statement.equals("Bye")) {
            statement = in.nextLine();
            //getResponse handles the user reply
            System.out.println(getResponse(statement));
        }
    }
    /**
     * Get a default greeting
     *
     * @return a greeting
     */
    public String getGreeting() {
        return "Are you ready to learn about animals?";
    }

    /**
     * Gives a response to a user statement
     *
     * @param statement the user statement
     * @return a response based on the rules given
     */
    public String getResponse(String statement) {
        String response = "";
        if (statement.length() == 0) {
            response = "Is that a yes or no?";
        } else if (findKeyword(statement, "no") >= 0) {
            response = "Why not? There's so many interesting facts to learn about animals!";
            emotion--;
        } else if (findKeyword(statement, "yes") >= 0) {
            System.out.println("Yay! Which would you like to learn about? Mammals, birds, reptiles, amphibians or fish?");
            emotion++;
            return getAnimalFacts();
        }
        // Response transforming I want to statement
        else if (findKeyword(statement, "I want to", 0) >= 0) {
            response = transformIWantToStatement(statement);
        } else if (findKeyword(statement, "I want", 0) >= 0) {
            response = transformIWantStatement(statement);
        } else {
            response = getRandomResponse();
        }
        return response;
    }
    /**
     * Take a statement with the name of what user wants to learn and transforms it
     * "Why do you want to learn <statement>"
     * @param statement the user statement, assumed to contain a type of animal
     * @return the transformed statement
     */
    private String transformIWantToStatement(String statement) {
        //  Remove the final period, if there is one
        statement = statement.trim();
        String lastChar = statement.substring(statement
                .length() - 1);
        if (lastChar.equals(".")) {
            statement = statement.substring(0, statement
                    .length() - 1);
        }
        int psn = findKeyword(statement, "I want to learn about", 0);
        String restOfStatement = statement.substring(psn + 10).trim();
        return "Why do you want to learn about" + restOfStatement + "?";
    }
    /**
     * Take a statement with "I want <something>." and transform it into
     * "Why do you want a <something>?"
     * @param statement the user statement, assumed to contain "I want a
     * @return the transformed statement
     */

    private String transformIWantStatement(String statement) {
        //  Remove the final period, if there is one
        statement = statement.trim();
        String lastChar = statement.substring(statement
                .length() - 1);
        if (lastChar.equals(".")) {
            statement = statement.substring(0, statement
                    .length() - 1);
        }
        int psn = findKeyword(statement, "I want a", 0);
        String restOfStatement = statement.substring(psn + 8).trim();
        return "Why do you want a " + restOfStatement + "?";
    }

    /**
     * Search for one word in phrase. The search is not case
     * sensitive. This method will check that the given goal
     * is not a substring of a longer string (so, for
     * example, "I know" does not contain "no").
     *
     * @param statement the string to search
     * @param goal      the string to search for
     * @param startPos  the character of the string to begin the
     *                  search at
     * @return the index of the first occurrence of goal in
     * statement or -1 if it's not found
     */
    private int findKeyword(String statement, String goal, int startPos) {
        String phrase = statement.trim().toLowerCase();
        goal = goal.toLowerCase();
        // The only change to incorporate the startPos is in
        // the line below
        int psn = phrase.indexOf(goal, startPos);
        // Refinement--make sure the goal isn't part of a
        // word
        while (psn >= 0) {
            // Find the string of length 1 before and after
            // the word
            String before = " ", after = " ";
            if (psn > 0) {
                before = phrase.substring(psn - 1, psn);
            }
            if (psn + goal.length() < phrase.length()) {
                after = phrase.substring(
                        psn + goal.length(),
                        psn + goal.length() + 1);
            }
            // If before and after aren't letters, we've
            // found the word
            if (((before.compareTo("a") < 0) || (before
                    .compareTo("z") > 0)) // before is not a
                    // letter
                    && ((after.compareTo("a") < 0) || (after
                    .compareTo("z") > 0))) {
                return psn;
            }

            // The last position didn't work, so let's find
            // the next, if there is one.
            psn = phrase.indexOf(goal, psn + 1);

        }

        return -1;
    }
    /**
     * Search for one word in phrase.  The search is not case sensitive.
     * This method will check that the given goal is not a substring of a longer string
     * (so, for example, "I know" does not contain "no").  The search begins at the beginning of the string.
     *
     * @param statement the string to search
     * @param goal      the string to search for
     * @return the index of the first occurrence of goal in statement or -1 if it's not found
     */
    private int findKeyword(String statement, String goal) {
        return findKeyword(statement, goal, 0);
    }

    private String getRandomResponse() {
        Random r = new Random();
        if (emotion == 0) {
            return randomNeutralResponses[r.nextInt(randomNeutralResponses.length)];
        }
        if (emotion < 0) {
            return randomBoringResponse[r.nextInt(randomBoringResponse.length)];
        }
        return randomHappyResponses[r.nextInt(randomHappyResponses.length)];
    }

    /**
     * @param: User's input of Bird, Mammal, Fish, Amphibian, Reptile
     * @return Facts based on user's inputs
     */
    private String getAnimalFacts() {
        Scanner scan2 = new Scanner(System.in);
        String statement2 = scan2.nextLine();
        String phrase1 = statement2.trim().toLowerCase();
        statement2 = statement2.toLowerCase();
        Random x = new Random();
        if (statement2.equals("mammals")) {
            return MammalFact[x.nextInt(MammalFact.length)];
        } else if (statement2.equals("amphibians")) {
            return AmphibianFact[x.nextInt(AmphibianFact.length)];
        } else if (statement2.equals("birds")) {
            return BirdFact[x.nextInt(BirdFact.length)];
        } else if (statement2.equals("reptiles")) {
            return ReptileFact[x.nextInt(ReptileFact.length)];
        } else if (statement2.equals("fish")) {
            return FishFact[x.nextInt(ReptileFact.length)];
        } else {
            return "Sorry, that is not an animal type!";
        }
    }
	private String [] randomNeutralResponses = {"I have many interesting facts! What do you want to learn about?"};
	private String [] randomBoringResponse = {"You seem like you've learned all the facts! Feel free to try out our math and rhyme bot! "};
	private String [] randomHappyResponses = {"Do you want to learn more?"};
	private String [] MammalFact = {"A giraffe's tongue is 20 inches long. They use it to clean their own ears","A hard working mole can dig a hole up to 300 feet deep over night","A whale's heart beats very slowly. As slow as once every 6 seconds","Beavers can hold their breath for up to 15 minutes","Even though it has a hump, a camel's spine is straight"};
	private String [] AmphibianFact = {"Most amphibians have thin, moist skin that helps them to breathe","Frogs swallow their food whole. The size of what they can eat is determined by the size of their mouths and their stomach","A group of frogs is called an army","All amphibians have gills, some only as larvae and others for their entire lives","Frogs cannot live in salt water"};
	private String [] FishFact = {"Whales can't swim backwards","A jellyfish isn't really a fish","Baby sharks are called pups","An electric eel can produce a powerful jolt of electricity of up to 600 volts","A few fish, like the spotted climbing perch, are able to breathe oxygen from the air"};
	private String [] BirdFact = {"The peregrine falcon can dive as fast as 200 mph when hunting prey","Although an ostrich cannot fly, it can run up to 60 mph.","Owls can rotate their heads almost 360°, but they cannot move their eyes.","Many birds are considered incredible navigators: when transported 400 miles from its home, a champion racing pigeon can find its way back in 1 day.","Some birds like penguins and emus are classified as flightless: despite having wings, they cannot fly."};
	private String [] ReptileFact = {"Crocodiles have been known to swallow rocks so they can dive deeper into the water.","Some snakes have over 300 pairs of ribs.","The shell of a turtle is made up of a bunch of bones (around 60) all connected together.","Lizards and snakes smell with their tongues.","Turtles have no ears to hear with, but they are thought to have excellent eye sight and sense of smell. They can also feel vibrations from loud sounds."}; //
}
