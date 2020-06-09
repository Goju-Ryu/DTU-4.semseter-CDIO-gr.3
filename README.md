# DTU-4.semseter-CDIO-gr.3
A program that allows you to play soliatre, you give it a picture and it gives you a move

# User guide 
## for the Manual GUI
coming soon.

## for the OpenCV GUI
coming soon.


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