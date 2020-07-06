# DTU-4.semseter-CDIO-gr.3
This repo started out as an assignment in the class "CDIO" at Danmarks Tekniske Universitet (DTU).
The assignment was to create a program that helps solve a physical game of solitaire.
The player can provide the program with a pictures or number representation of the board and the program returns a move 
to be made by the player. The project is build so that it could be run over a network, if the computer you 
possess is not powerful enough to both analyse the input and pick the moves.

**Links to demo videos**  
Using Computer Vision to play actual games: <!--TODO-->Coming Soon.  
Simulating a lot of games: <!--TODO-->Coming Soon.  

## User guide 
- Plug in a USB camera into your PC
- Setup your solitaire card game on a green background.  
  _To ensure best performance:_
  - let the bottom card in very pile lay 2 to 6 cm beneath the other cards in the pile
  - make sure that all cards stacked on top of each other are tightly packed so only one card is visible
  - put the suits stacks in the following order from the left: <!--TODO-->Coming soon
- Download or clone the code from GitHub
- <!--TODO-->As there are no compiled versions available yet you have to follow the developer guide
- Run the server.py file(the python side of the server)
- To choose the type of game to run you can give the program different arguments when starting it
- run main.java (the java side of the server)

now you should be good to go

### Program arguments
The program takes several different arguments when started. These define the nature of how this program is being run .
Below is a description of the options and their effect.  
- **cam** runs the program using the python server with a camera
- **gui** runs the program using the python server with a gui that takes text representations of cards
- **std** runs a standard simulation with a predetermined board using no network communication
- **sim** runs a simulation with a randomly generated board using no network communication
    - **\<integer>** providing sim with an integer will make it run that many simulations and summarize the results

**No arguments**  
  providing no arguments runs the program as if the argument "cam" was given
  
  
## Developer guide
**NB** _No guarantee this works on IOS. Only Linux and windows is currently supported._

### Project Setup

#### Java back-end:
you need to make sure you are using       
[project SDK: 11] or higher     
[Project language level: 11 - local variable for lambda parameters]

- If your IDE requires it, set up the project as a maven project.
- Either using IDE plugins or the command line, download the maven dependencies.

_**NB** without performing the [network communication](#Network-communication:) step, the java code won't compile. 
It is therefore very important to complete this step even if you are only simulating games without any networking._

#### Python front-end and openCV:
- You need to have Python 3.8 installed on your computer
- Set up python in the project
- get the requirements listed in the [requirements.txt](requirements.txt) file using pip


#### Network communication:
Once the above steps are done comes the tricky part; setting up grpc to allow the java side to talk to the server side.

**Prerequisites:**
<!--TODO remove this?
- having python 3.8(or higher installed)
- Having pip installed and up to date
Then you need to install grpc, you can do that by
```
pip install grpcio
pip install grpcio-tools
```
--> 
run the commands
```
mvn clean
mvn compile
```
to make sure your target is clean and to generate the networking files from the proto description. Otherwise you can 
use the Intellij maven plugin or a similar maven ui to run the same commands.

should that not work you can manually try to run
```
protoc.bat(for windows) or protoc.sh(for linux)
```
found under 'src/python/proto'.


** Common problem solutions**
if this is not working and you are on a windows computer you can try
- installing bash in your terminal
- installing maven in you bash terminal
- run maven from this new bash terminal (some dependencies may be lacking)


## Future improvement and possible refactoring
there is 2 things we sould like to change should we do the project over again or compleatly refactor the code
- We would like to change the use of collection to a use of Arraylist, however this refactoring is not within our scope.
Are you planning to make a project simmilar to this(in java) we would advise you to use Arraylist, as the implentation is faster and easier and we found no mesurable advantage in terms of run time. 

- We are using GRPC, but because we still pars json formattet string, we are not gaining any advantage by using it. Ideally we would send things in bytecode.

and one speculation that we did not make, but new people might make:
- We might consider using the YOLO libary for the computer vision.
