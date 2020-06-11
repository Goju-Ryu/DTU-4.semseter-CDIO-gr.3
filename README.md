# DTU-4.semseter-CDIO-gr.3
A program that allows you to play soliatre, you give it a picture and it gives you a move

# User guide 
## for the Manual GUI
coming soon.

## for the OpenCV GUI

to install the Python requirements, you can either use pycharm to open the project, where it ought to install the dependencies to your local python environment automaticaly using the requirements.txt
if this is not the case. then you can use the requirements.txt with the command
```
pip install -r requirements.txt
```

### Using the program
first the program runs a HSV Settings window, where if the original settings are incorrect they can be corrected for the remainder of the programs run time. 
```
click q to close the window
```
then the program runs with theese new HSV settings. 


# Developer guide
## java back-end side:
you need to make sure you are using       
[project SDK: 11]     
[Project language level: 11 - local variabel for lambda parameteres]

## java python communication:
Once the above is done comes the tricky part, setting grpc up allowing the java side to talk to the server side.

**Prereqs:**
- having python 3.4(or higher installed)
- make sure your pip is up to date
Then you need to install grpc, you can do that with the command
```
pip install grpcio
pip install grpcio-tools
```
finally you can run a
```
mvn clean
mvn compile
```
to make sure every you target is clean and generate the files you need. 
Alternatively you can use the maven plugin for Intellij to run the commands of the same name.

To start the connection you have to start *src/main/python/server.py* 
then run *src/main/java/examples/HelloWoldClient*
This should send a message between the java and python program.