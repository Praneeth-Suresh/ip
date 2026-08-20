# Odysseus

Odysseus is a personal-assistant chatbot. Given below are instructions on how to use the project.

## AI use

AI assistance was used to set up Beryl's repository workflow, consolidate the
existing project guidance into Beryl's canonical agent files, and add this
acknowledgement. AI has not implemented application behavior for this project.

## Setting up in Intellij

Prerequisites: JDK 25, update Intellij to the most recent version.

1. Open Intellij (if you are not in the welcome screen, click `File` > `Close Project` to close the existing project first)
1. Open the project into Intellij as follows:
   1. Click `Open`.
   1. Select the project directory, and click `OK`.
   1. If there are any further prompts, accept the defaults.
1. Configure the project to use **JDK 25** (not other versions) as explained in [here](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk).<br>
   In the same dialog, set the **Project language level** field to the `SDK default` option.
1. After that, locate the `src/main/java/Odysseus.java` file, right-click it, and choose `Run Odysseus.main()` (if the code editor is showing compile errors, try restarting the IDE). If the setup is correct, you should see:
   ```
     ___    ____  __   __  ____   ____  _____  _   _  ____
    / _ \  |  _ \ \ \ / / / ___| / ___|| ____|| | | |/ ___|
   | | | | | | | | \ V /  \___ \ \___ \|  _|  | | | |\___ \
   | |_| | | |_| |  | |    ___) | ___) | |___ | |_| | ___) |
    \___/  |____/   |_|   |____/ |____/|_____| \___/ |____/

   Ahoy, traveler! I am Odysseus, long tested by sea and fate.
   What course shall we chart together?
   ____________________________________________________________

   read book
   ____________________________________________________________
   Added to my ship's log: read book
   ____________________________________________________________

   return book
   ____________________________________________________________
   Added to my ship's log: return book
   ____________________________________________________________

   buy bread
   ____________________________________________________________
   Added to my ship's log: buy bread
   ____________________________________________________________

   list
   ____________________________________________________________
   Here are the tasks on our voyage, traveler:
   1. [ ] read book
   2. [ ] return book
   3. [ ] buy bread
   ____________________________________________________________

   mark 2
   ____________________________________________________________
   Well sailed! I've marked this task as done:
     [X] return book
   ____________________________________________________________

   unmark 2
   ____________________________________________________________
   This task awaits its hour again:
     [ ] return book
   ____________________________________________________________

   bye
   ____________________________________________________________
   Farewell, traveler. May Athena guide your voyage until we meet again.
   ____________________________________________________________
   ```

**Warning:** Keep the `src\main\java` folder as the root folder for Java files (i.e., don't rename those folders or move Java files to another folder outside of this folder path), as this is the default location some tools (e.g., Gradle) expect to find Java files.
