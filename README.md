# Virtual Pet Application

## Group 15 - CS2212 Winter 2025

**Team Members:**

- Isa Alif
- Omar Hossain
- Hamza Khan
- Ahmed Sinjab
- Jacob Tran

## Software Description

The Virtual Pet Application is a modern implementation of the classic virtual pet concept (similar to Tamagotchi). Players select a pet character from several options and care for it by managing its health, happiness, hunger, and sleep levels. The game features a user-friendly graphical interface, save/load functionality, parental controls for managing play time, and an in-game shop system.

Key features:

- Multiple pet types with unique attributes (Debatchi, Kikitchi, Mametchi, Tsuyopitchi)
- Real-time state management
- Pet emotion system that responds to care
- Inventory and shop system
- Comprehensive save/load functionality
- Parental controls for limiting play time and reviving a dead pet
- Interactive actions (feeding, playing, walking, vet visits, etc.)

## Required Libraries and Tools

### Required Software:

- **Java Development Kit (JDK)** - Version 17 or higher
- **Windows 10/11** operating system

### Required Libraries:

- **Java Swing and AWT** (included in JDK)
- No additional third-party libraries are required

## Step-by-Step Build Guide

### 1. Install Java Development Kit (JDK)

1. Download the JDK 17 or higher from Oracle's website
2. Select the Windows x64 Installer option
3. Run the downloaded installer and follow the prompts
4. Accept the default installation locations
5. Wait for the installation to complete
6. Verify installation by opening Command Prompt and typing:
   ```
   java -version
   ```
   You should see version information for your installed JDK

### 2. Download and Extract the Source Code

1. Unzip the project files to a folder of your choice (e.g., C:\VirtualPet)
2. The folder structure should include:
   - group15/ (contains all Java source files)
   - group15/assets/ (contains images and other resources)
   - group15/saves (contains all save files made, should be empty when starting)

### 4. Compile the Source Code

#### Using Command Prompt:

1. Open Command Prompt
2. Navigate to the project directory:
   ```
   cd C:\VirtualPet
   ```
3. Create a 'build' directory:
   ```
   mkdir build
   ```
4. Compile all Java files:
   ```
   javac -d build src/*.java
   ```
5. Copy the assets folder to the build directory:
   ```
   xcopy /E /I assets build\assets
   ```

#### Using Visual Studio Code:

1. Open VS Code
2. Select "File" > "Open Folder" and choose the project directory
3. Install the "Extension Pack for Java" if not already installed
4. Open the Explorer view (Ctrl+Shift+E)
5. Right-click on the src folder and select "Build Java"

## Running the Application

### Running from Command Line:

1. Open Command Prompt
2. Navigate to the build directory:
   ```
   cd C:\VirtualPet\build
   ```
3. Run the application:
   ```
   java UserInterface
   ```

### Running from Visual Studio Code:

1. Open the project in VS Code
2. Navigate to the UserInterface.java file
3. Click the "Run" button (triangle icon) in the upper right
4. Select "Run Java"

## User Guide

### Main Menu

The main menu provides access to the following options:

- **Start New Game**: Create a new virtual pet
- **Continue Game**: Load a previously saved game
- **Tutorial**: View instructions on how to play
- **Parental Controls**: Configure play time restrictions
- **Exit**: Close the application

### Selecting a Pet

1. Click "Start New Game" from the main menu
2. Browse the available pet types: Debatchi, Kikitchi, Mametchi, Tsuyopitchi
3. Click "About" to learn more about each pet's characteristics
4. Click "Choose" on your preferred pet
5. Enter a name for your pet and confirm your selection
6. Enter a name for the save file that will store that game

### Game Interface

The main game screen displays:

- Your pet in the center of the screen
- Status bars at the bottom showing Health, Happiness, Sleep, and Hunger levels
- Score display showing your current points in the top right
- Control buttons for accessing various functions

### Caring for Your Pet

Use the "Commands" button or keyboard shortcuts to perform actions:

- **Feed (F key)**: Give food to your pet to increase hunger level
- **Gift (G key)**: Give a gift to increase happiness
- **Play**: Engage in play to boost happiness
- **Walk**: Take your pet for a walk to improve multiple stats
- **Sleep**: Put your pet to bed to restore sleep
- **Vet**: Take your pet to the vet to restore health

### Shop & Inventory

1. Click the "Shop" button to access the store
2. Purchase items using points from your score
3. Items include various foods and toys that affect different pet stats
4. Manage your inventory carefully as space is limited

### Saving & Loading

1. Press Ctrl+S or click the save button to save your game
2. To continue a saved game, select "Continue Game" from the main menu and choose your save file

### Game Over Conditions

Your pet will die if:

- Health reaches zero

### Keyboard Shortcuts

- **F**: Feed your pet
- **G**: Give a gift
- **P**: Pause the game
- **C**: Open commands menu
- **Esc**: Open settings
- **Ctrl+S**: Save the game

## Parental Controls

The game includes a parental control system to limit play time:

### Accessing Parental Controls

1. From the main menu, click "Parental Controls"
2. The default PIN is **123**
3. After entering the PIN, you can:
   - Enable/disable time restrictions
   - Set allowed play hours (start and end time)
   - View play statistics
   - Revive a dead pet from specific save files

## Additional Information

- The game automatically saves statistics on exit
- Pet emotions and animations change based on their state and your interactions
- Different pets have different base stats and respond differently to care
- The fourth pet (Tsuyopitchi) has special characteristics for more experienced players
- Background scenes change depending on the activity (like visiting the vet)
