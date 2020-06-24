**NB:** Do to a restructuring, git commits and stats are not representive of who did what. To get a feel for who did what you can see your hour budget in the report attached.

# DTU-4.semseter-CDIO-gr.3
A program that allows you to play soliatre, you give it a pictute or number representation of the board and it gives you a move. 
The project is build so that it could be run over a network, if the computer you pocess is not strong enough to compute the images.

### link to demo videos 
Using Computer Vision to play actual games: Coming Soon.
Simulating a lot of games: Coming Soon.

# User guide 
- Plug in a USB camera into your PC
- setup your solitare card game on a green background.
- Download or clone the code from git
- (if your IDE requires, set it up as a maven project.)
- Run the server.py file(the python side of the server)
- at the moment what UI you want to use is decided by writing code in main,
so write the UI you want to use in main
- run main.java (the java side of the server)

now you should be good to go

# Developer guide
NB: No guarantee this works on IOS

### java back-end side:
you need to make sure you are using       
[project SDK: 11] or higher     
[Project language level: 11 - local variabel for lambda parameteres]

- There might be a need to import a jar-file for com.google.gson in the InputDTO.java class.
This can simply be done using alt+enter whit a gson element selected.
Then import jar from web, and choose one of the newer versions.

### python front-end and openCV:
You need to have Python 3.8
and having an external USB camera plugged in
### java python communication:
Once the above is done comes the tricky part, setting grpc up allowing the java side to talk to the server side.

**prereqs:**
- having python 3.4(or higher installed)
- Having pip installed and up to date
Then you need to install grpc, you can do that by
```
pip install grpcio
pip install grpcio-tools
```
finally(if you are in linux) you can run a
```
mvn clean
mvn compile
```
to make sure every you target is clean and generate the files you neeed 

otherwise you can use intellij or a simmilar ui

should that not work you can manually try to run
```
proto_py.bat(for windows) or grpc.proto(for linux)
```
found under 'src/python/proto',

**to set up grpc and then clean and compile**

if this is not working and you are on a windows computer you can try
- installing bash in your terminal
- installed maven in you bash terminal

# Future improvement and possible refactoring
there is 2 things we sould like to change should we do the project over again or compleatly refactor the code
- We would like to change the use of collection to a use of Arraylist, however this refactoring is not within our scope.
Are you planning to make a project simmilar to this(in java) we would advise you to use Arraylist, as the implentation is faster and easier and we found no mesurable advantage in terms of run time. 

- We are using GRPC, but because we still pars json formattet string, we are not gaining any advantage by using it. Ideally we would send things in bytecode.

and one speculation that we did not make, but new people might make:
- We might consider using the YOLO libary for the computer vision.
