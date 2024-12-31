# Kids Pet Game: PET FARM

## Overview

This software is a kids' pet game designed to combine fun with education. The game features four animated pets—dog, fox, mouse, and cat—with interactive gameplay focused on answering educational questions to maintain the pet’s attributes. Players must keep their pet alive as long as possible while managing various attributes such as health, hunger, sleep, and happiness.

### Key Features
- **Educational Gameplay:** Answer timed questions for attribute upgrades or penalties.
- **Pet Features:** Each pet has special bonuses, emotional states, and attributes that decay over time.
- **Parental Controls:** A parental page allows monitoring, restriction of access times, and restoration of health stats.
- **Game Modes:** Choose between creating a new game or loading a saved game for each pet.

The goal is to keep your pet alive by strategically managing its attributes.

---

## Requirements

- **Java Version:** Minimum Java 17.
- **IDE:** Any IDE with JDK installed (e.g., IntelliJ IDEA, Eclipse, etc.).
- **Third-party Libraries:** None required.

---

## Building the Software

1. **Download the Game Folder:**
    - Clone the repository or download it as a ZIP file and extract it.

2. **Locate the Source Code:**
    - Navigate to the `src` folder.

3. **Run Main.java:**
    - Open your preferred IDE and import the project.
    - Navigate to the `Main.java` file within the `src` folder.
    - Compile and run the file.
---

## Running the Compiled Software

1. **Navigate to the Game Folder:**
    - Locate the folder containing the downloaded game files.

2. **Run the Main File:**
    - Open the `src` folder and run `Main.java` using your IDE.

---

## User Guide

### Starting the Game
- **New Game:** Create a new game for any of the pets—dog, fox, mouse, or cat.
- **Load Game:** Load a previously saved game for any pet.

### Save/load Game States
- In new Game a new game can be made for all 4 pets if that game is saved, the saved game for that pet is overridded
- If a game is not saved ever and the play exists the game is discarded
- If the pet dies, the game is discarded
- The saved games are saved from the last time the save button was pressed, with all inventory, stats and overall score
- If load game is selected on a game with no saved state, a new game will simply load

### Gameplay
- **Answer Questions:**
    - **Garden:** Answer questions for random inventory items.
    - **Pet Shelter:** Answer questions for sleep points.
    - **Vet Shelter:** Answer questions for health points.

- **Attributes:**
    - Food increases hunger points.
    - Treats increase happiness points.
    - Wrong Pet Shelter questions decrease sleep points.
    - Wrong Vet Shelter questions decrease health.
    - Wrong Garden questions decrease both happiness and hunger.

- **Game Challenges:**
    - Attributes decrease automatically over time.
    - Penalties apply when any attribute drops to zero:
      - If health goes to 0 pet dies and game ends.
      - If happiness goes to 0 shelters can not be entered (health and sleep can't be increased)
      - If hunger goes to 0 happiness decrease by 25
      - If sleep goes to 0 sleep decrease by 25
- ** Pet special attributes: **
  - Dog: Max Happiness
  - Cat: Max Sleep 
  - Fox: Max Health
  - Hamster: Max Hunger

### Winning the Game
- Keep your pet alive as long as possible by balancing attributes.
- Total score is displayed as the sum of all attributes and updates live with stats

---

## Parental Controls

### Features
- **Health Restoration:** Set all health stats of a saved game to full.
- **Access Restrictions:**
    - Restrict times of the day when the game can be played.
    - Monitor and set restrictions on overall game time.

### Accessing Parental Controls
1. Enter the parental menu.
2. Use the password: **myPassword**.

---

## Additional Notes
- The game does not require internet connectivity.
