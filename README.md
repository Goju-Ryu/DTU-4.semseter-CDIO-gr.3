# DTU-4.semseter-CDIO-gr.3
A program that allows you to play soliatre, you give it a pictute or number representation of the board and it gives you a move

# User guide 
### for the Manual GUI
coming soon.

### for the OpenCV GUI
coming soon.


# Developer guide
NB: No guarantee this works on IOS

### java back-end side:
you need to make sure you are using       
[project SDK: 12]     
[Project language level: 11 - local variabel for lambda parameteres]


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
