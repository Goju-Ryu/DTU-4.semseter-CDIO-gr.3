# DTU-4.semseter-CDIO-gr.3
A program that allows you to play soliatre, you give it a picture and it gives you a move

# User guide 
## coming soon.

## for the OpenCV GUI
coming soon.


# Developer guide
## java back-end side:
you need to make sure you are using       
[project SDK: 12]     
[Project language level: 11 - local variabel for lambda parameteres]

## java python communication:
Once the above is done comes the tricky part, setting grpc up allowing the java side to talk to the server side.
prereqs:
- having bash installed in your terminal
- having python 3.4(or higher installed)
- having maven installed in bash
- make sure your pip is up to date
Then you need to install grpc, you can do that by
```
pip install grpcio-tools
```
finally you can run a
```
mvn clean
mvn compile
```
to make sure every you target is clean and generate the files you neeed 
