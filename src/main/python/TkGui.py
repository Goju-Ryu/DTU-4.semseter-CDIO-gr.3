import os
from tkinter import *
#====================================
# This file containing a GUI is
# intented to work as to test sercer communication
# as well as a back-up should open CV not work
#====================================
from proto import grpc_pb2

root = Tk()

moveCount = 0

def myClick():
    #global moveCount = moveCount + 1
    global moveCount
    moveCount = moveCount + 1
    clickCount = "You did click me: "+ str(moveCount)
    butLabel = Label(root, text =clickCount)
    butLabel.grid(row =3, column=7)
    inPile = ePile.get()
    inSuits = eSuit1.get()+", "+eSuit2.get()+", "+eSuit3.get()+", "+eSuit4.get()
    inCols = ecol1.get()+", "+ecol2.get()+", "+ecol3.get()+", "+ecol4.get()+", "+ecol5.get()+", "+ecol6.get()+", "+ecol7.get()
    input = inPile + " | "+ inSuits + "| "+inCols
    #Todo: instead of making this print out make this send to the server
    print(input)


#Buttons
myButton = Button(root, text="Make New Move",command = myClick)
myButton.grid(row =4, column=7)

#Input fields
ePile = Entry(root)
eSuit1 = Entry(root)
eSuit2 = Entry(root)
eSuit3 = Entry(root)
eSuit4 = Entry(root)
ecol1 = Entry(root)
ecol2 = Entry(root)
ecol3 = Entry(root)
ecol4 = Entry(root)
ecol5 = Entry(root)
ecol6 = Entry(root)
ecol7 = Entry(root)


ePile.grid(row =2, column=0)
eSuit1.grid(row =1, column=2)
eSuit2.grid(row =1, column=3)
eSuit3.grid(row =1, column=4)
eSuit4.grid(row =1, column=5)
ecol1.grid(row =3, column=1)
ecol2.grid(row =4, column=2)
ecol3.grid(row =5, column=3)
ecol4.grid(row =6, column=4)
ecol5.grid(row =7, column=5)
ecol6.grid(row =8, column=6)
ecol6.grid(row =9, column=7)








#Labels I use
myLabel0 = Label(root, text="Solitare Klodinke CDIO DTU GR. 3")
myLabel1 = Label(root, text = "Pile")
myLabel2 = Label(root, text = "Coulumns")
myLabel3 = Label(root, text = "Suits")

#making sure they are shown onscreen
myLabel0.grid(row =0, column=1)
myLabel1.grid(row =1, column=0)
myLabel2.grid(row =2, column=1)
myLabel3.grid(row =1, column=1)

root.mainloop()
print("Program ended")
