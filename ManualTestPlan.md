## Prerequisite

#### The following OS should be used for testing
- Mac OS X
- Linux
- Windows 7

#### Testing IDE
*Using the Test Package in src folder and run test*
- IntelliJ IDEA CE
- Eclipse

#### Project JDK
- 9.0 Java Version "9.0.4"

---
## Environment Setup
I set up the testing environment using **IntelliJ IDEA** in **Mac OS**.


---
## Operation and Results
### Start Scene
![StartOfGame](/Users/chenmaggie/Desktop/StartofGame.png)
The user can input the number of players of the game in the text box. 
After input a number and click the "START" button, the game starts.


### During the Game
![Game(notHided)](/Users/chenmaggie/Desktop/Game(notHided).png)
The current game state is shown in the window. 

The color, number, symbol of the latest card from the discard pile is shown in the bottom left corner next to the text "Latest Card:".
Number of cards to stack and the current player in turn are also shown beside.

There are also buttons to Skip, Draw Card, Hide and Change Color.

#### If the "Hide" button is clicked
![Game(hided)](/Users/chenmaggie/Desktop/Game(hided).png
The cards of the player of the current turn are hided. 
This is to prevent opponents see current player's cards.
The "Hide" button now changes to "Show". And if "Show" is clicked again, the cards are revealed.
And cards in the players and are revealed as the previous screenshot above.

#### If the "Change Color" button is clicked
![ChangeColor](/Users/chenmaggie/Desktop/ChangeColor.png)
Color options are shown. The player can choose which color to change.
This button is valid to click only if a wild card is played.

### End Scene of the Game
![ChangeColor](/Users/chenmaggie/Desktop/EndOfGame.png)
The winner of the game is shown. If the player click the "Start a new game" button,
a new game will start. And the Start scene windows pop up.

---
## Error Messages
To be continued


