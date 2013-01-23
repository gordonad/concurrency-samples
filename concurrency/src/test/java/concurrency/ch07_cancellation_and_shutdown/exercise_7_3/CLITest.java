package concurrency.ch07_cancellation_and_shutdown.exercise_7_3;

/*
 * Code by Maximilian Eberl - used with permission.
 *
 * So here is Maximilian's problem:
 *
 * He wants his command line tool to ask the user n times for input and give him
 * every time n seconds to provide this input.
 *
 * The tool reads the input easily in the first round, but in the second round
 * he has to type ENTER twice, and so on.  The n-th round, he has to type
 * ENTER n times!
 *
 * What is he doing wrong?
 *
 *
 * In response to our question of why he wanted such code (Heinz was suspecting
 * a school assignment), we got the following explanation (you don't need to
 * read everything unless you are particularly interested):
 *
 * From Maximilian Eberl:
 * ----------------------
 * I am planning to develop a software that uses voice recognition and a dialog
 * system to create documentation for nurses who provide help to elderly or
 * handicapped people in the homes of these people.
 *
 * This is a special german social system. You find more information here:
 *
 * http://www.netzdenker.de/dict-a-doc/
 *
 * The main problem is that these nurses have to write tons of documentation
 * about any move they make. This consumes 25-30% of their worktime.
 *
 * So creating this documentation while they work with the people would be a big
 * help.
 *
 * Such a system is built around a "Dialog Manager". In my system the software
 * uses so called "scenarios", which are human-readable files in a proprietary
 * own scripting language, which are parsed and compiled to specific
 * DialogManagers (Java classes) for the specific tasks.
 *
 * To test those scripts before the voice system is ready for use I utilize the
 * command line to replace the voice.
 *
 * Imagine the following nurse-computer dialogue via Bluetooth headset to a PDA
 * or smartphone:
 *
 * "Welcome to dict-a-doc. Do You want to create new documentation? Anwer yes
 * or no!" (=first loop)
 *
 * (- 5 seconds pass by without input)
 *
 * "Do You want to create new documentation? Anwer yes or no!" (=second loop)
 *
 * "Hmbl .."
 *
 * "Sorry, I did not understand. Do You want to create new documentation?
 * Anwer yes or no!" (=third loop)
 *
 * "Yes"
 *
 * "Yes, good. I am opening new documentation. Please provide the family name of
 * the patient!"
 *
 * ... (and so on)
 *
 * In the .scenario script this reads:
 *
 * --- snip ---
 * if ( $config.welcome == true )
 *    out($vocabulary.welcome)
 *
 * out($vocabulary.asknewdocumentation)
 *
 * $tmp = yesno(3,5)
 *
 * if ( $tmp == "yes" )
 *    goto newdocumentation()
 * else if ( $tmp == "no" )
 *    goto mainmenu()
 * else if ( $tmp == "" ) # answer was given but could not be recognized
 *    out($vocabulary.didntunderstand)
 *    goto(mainmenu())
 *
 * ... and so on
 * --- /snip ---
 *
 * The vocabulary.* has two versions:
 *
 * Simple Strings for the command line or small audio files for the voice
 * dialog.  In the configuration it can be set that out(*) points to the Console
 * out (using Strings) or the sound card (using sound files).
 *
 * Because the DialogManager and scenarios are develloped independently from the
 * voice part I need the dialogue functionality on the command line.
 *
 * So as an example the yesno(m,n) function that takes 2 parameters: number of
 * tries and timeout for the single try.
 *
 * If a String (not empty) comes from the command line it is used like the
 * result of the voice recognition engine, that means here: it can be "yes" or
 * "no" or anything else which means "didn't understand" and is returned by an
 * empty "". (If the user didn't answer at all, a logical null will be returned)
 *
 * So the CommandLineClient is used to develop the DialogManager and the
 * different scenarios without the voice engine.
 *
 * Hope this helped You to understand.
 * Have a fine day !
 */
public class CLITest {
    public CLITest() {
    }

    public static void main(String[] args) {
        int tries = 5;
        int timeout = 5;
        long ltimeout = 5000L;

        try {
            tries = Integer.parseInt(args[0]);
            timeout = Integer.parseInt(args[1]);
            ltimeout = (long) timeout * 1000;
        } catch (Exception e) {
            System.out.println("Usage: java CLITest <number of tries> <timeout>");
            System.out.println("Wrong input. Using default values.");
            tries = 5;
            timeout = 5;
            ltimeout = 5000L;
        }

        long starttime = 0L;
        boolean keepon = false;

        // start working
        StringHolder.getInstance().setString(null);

        for (int i = 0; i < tries; i++) {
            System.out.println(String.valueOf(i + 1) + ". loop");
            if (null == StringHolder.getInstance().getString()) {
                System.out.println("Starting CLIThread ...");
                CLIThread ct = new CLIThread();
                ct.start();
                starttime = System.currentTimeMillis();
                keepon = true;
                while (keepon) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException ignored) {
                    }

                    // criteria to skip the next round
                    if (null != StringHolder.getInstance().getString()) {
                        keepon = false;
                    }
                    if (System.currentTimeMillis() - starttime > ltimeout) {
                        keepon = false;
                    }
                }
                ct.cancel();
                System.out.println("\nThread cancelled. input is now: " +
                        StringHolder.getInstance().getString());
            }
        }

        System.out.println("Done. Your input was: " +
                StringHolder.getInstance().getString());
    }
}


