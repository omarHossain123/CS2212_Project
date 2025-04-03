# Virtual Pet Game

## Group 15

## Description

Virtual Pet Game is a desktop application that simulates caring for a virtual pet, similar to a Tamagotchi. Players select a pet type and must maintain its health, happiness, hunger, and sleep levels through various interactions. The game features:

- Multiple pet types with different attributes (Debatchi, Tsuyopitchi, Kikitchi, Mametchi)
- Real-time stat management (health, happiness, hunger, sleep)
- Various pet interactions (feeding, playing, walking, vet visits, etc.)
- Different pet emotions and states based on well-being
- Save/load game functionality
- Parental controls with time restrictions
- Inventory and shop system

## Required Libraries & Tools

- **Java Development Kit (JDK)** - Version 17 or higher
- **Java Swing** - Built into JDK (for GUI components)
- **javax.swing** - Built into JDK (for UI components)
- **java.io** - Built into JDK (for file operations)
- **java.util** - Built into JDK (for utility classes)
- **java.time** - Built into JDK (for time operations)
- **IDE** - VS Code was used for development, but any Java IDE will work

## Building from Source

### Prerequisites

1. Install JDK 17 or higher:

   - Visit [Oracle's JDK download page](https://www.oracle.com/java/technologies/downloads/) or use OpenJDK
   - Follow the installation instructions for your operating system
   - Verify installation by running `java -version` in a terminal

2. Install VS Code (optional, but recommended):
   - Download from [VS Code website](https://code.visualstudio.com/)
   - Install the "Extension Pack for Java" extension from the marketplace

### Compiling the Project

1. Clone the repository by using the commands `git clone (repo)` or extract the source code to a directory
2. Open a terminal and navigate to the project directory
3. Create a directory for the compiled classes:
   ```
   mkdir -p out/production
   ```
4. Compile the Java files:

   ```
   javac -d out/production src/*.java
   ```

   Note: Adjust the path if your Java files are in a different directory

5. Copy the assets folder to the output directory:
   ```
   cp -r assets out/production/
   ```

## Running the Game

### From Compiled Classes

1. Navigate to the directory containing the compiled classes
2. Run the application with:
   ```
   java -cp out/production UserInterface
   ```

### From an IDE

1. Open the project in your preferred Java IDE
2. Set the main class to `UserInterface`
3. Run the project using the IDE's run functionality

## User Guide

### Getting Started

1. Launch the game
2. From the main menu, select "Start New Game"
3. Choose your pet type:
   - Debatchi: Balanced stats with higher health
   - Tsuyopitchi: Higher hunger and happiness stats
   - Kikitchi: Balanced attributes
   - Mametchi: Higher health and sleep stats
4. Enter a name for your save file when prompted
5. The main game screen will appear with your pet

### Game Controls

- **Mouse**: Click buttons to interact with UI elements
- **Keyboard Shortcuts**:
  - ESC: Open settings menu
  - P: Pause/resume game
  - F: Feed pet
  - G: Give gift to pet
  - C: Open commands menu
  - Ctrl+S: Save game

### UI Elements

- **Top Left**: Commands and Shop buttons
- **Top Right**: Score and Settings
- **Center**: Your pet
- **Bottom**: Status bars for Health, Happiness, Hunger, and Sleep

### Pet Care

1. **Keep an eye on the status bars**:

   - Health (Green): If it reaches zero, your pet will die
   - Happiness (Red): If it reaches zero, your pet becomes angry
   - Hunger (Brown): If it reaches zero, your pet becomes hungry and loses health
   - Sleep (Blue): If it reaches zero, your pet falls asleep automatically

2. **Interact with your pet**:

   - Commands Menu: Access various interactions like feeding, playing, etc.
   - Shop: Buy items for your pet

3. **Pet States**:
   - Default: Normal state
   - Hungry: When hunger is depleted
   - Angry: When happiness is depleted
   - Sleep: When sleep is depleted
   - Dead: When health reaches zero

### Saving and Loading

- Click the Save button or press Ctrl+S to save your game
- From the main menu, click "Continue Game" to load a saved game

## Accounts & Parental Controls

### Default Access

The game does not require accounts or passwords to access basic functionality.

### Parental Controls

Parental controls restrict when the game can be played.

#### Accessing Parental Controls

1. The parental controls settings are stored in `saves/parental_controls.dat`
2. Time restrictions can be set by modifying this file through the ParentalControls class
3. By default, the settings include:
   - Start time: Default is set in the morning
   - End time: Default is set in the evening
   - Daily playtime limit: Not enforced by default

#### Modifying Parental Controls

To modify parental controls, you'll need to access the settings through the ParentalControls class. This can be done by:

1. Adding a UI for parental controls (requires modifying the source code)
2. Directly modifying the `parental_controls.dat` file (requires knowledge of Java serialization)

## Additional Information

### File Structure

- `assets/`: Contains all game assets (images, etc.)
- `saves/`: Directory where save files are stored
- Main Java classes:
  - `UserInterface.java`: Main entry point and UI handler
  - `mainGame.java`: Main game window
  - `Pet.java`: Pet class with attributes and states
  - `Game.java`: Game logic
  - Other supporting classes for inventory, commands, etc.

### Known Issues

- Save Game Functionality is not working as intended and saves the chosen pet but all other features are reset.
- Since the Save Game does not work as intended the Revive pet in the Parent controls does not either as it needs to access the save files to see if a pet is dead.

### Development Information

- Developed using VS Code and GitLab for version control
- Java was chosen for cross-platform compatibility
- Swing was used for UI components

## Troubleshooting

### Common Issues

- **Game doesn't start**: Ensure Java is properly installed and PATH is configured
- **Missing assets**: Verify that the assets folder is in the correct location
- **Save files not found**: Check if the saves directory exists and has write permissions
