from AbstractUi import AbstractUI
from tkinter import *
import json as json
#====================================
# ManGUI or Manual GUI - requres you to manully enter the input data
#------------------------------------
# This file containing a GUI is
# intented to work as to test sercer communication
# as well as a back-up should open CV not work
#====================================.


def stringToJson(inputStr):
    returnStr = ""
    suit = ""
    su = inputStr[0:1]
    nr = inputStr[1:2]
    if(su == "h"):
        suit = "Hearts"
    elif(su == "c"):
        suit = "Clubs"
    elif(su == "d"):
        suit = "Dimonds"
    elif(su == "s"):
        suit = "Spades"
    returnStr = json.dumps(
        {"suit" : suit, "rank" : nr}
    )
    return returnStr

class ManGUI(AbstractUI):
    input =""
    def updateInp(self,newString):
        self.input = newString


    def run(self):
        root = Tk()
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

        #making sure they are shown onscreen
        ePile.grid(row =6, column=0)
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
        myLabel0 = Label(root, text="SOLITARE KLODINKE")
        myLabel1 = Label(root, text = "Pile : below")
        myLabel2 = Label(root, text = "Coulumns: below")
        myLabel3 = Label(root, text = "Suits: right")
        myLabel4 = Label(root, text="CDIO DTU GR. 3")
        myLabel5 = Label(root, text="h1 = hearts 1")
        myLabel6 = Label(root, text="c2 = clubs 2")
        myLabel7 = Label(root, text="d1 = dimonds 1")
        myLabel8 = Label(root, text="s5 = spades 5")


        myLabel0.grid(row =0, column=0)
        myLabel4.grid(row =0, column=1)
        myLabel5.grid(row =0, column=2)
        myLabel6.grid(row =0, column=3)
        myLabel7.grid(row =0, column=4)
        myLabel8.grid(row =0, column=5)


        myLabel1.grid(row =5, column=0)
        myLabel2.grid(row =2, column=1)
        myLabel3.grid(row =1, column=1)

        moveCount = 0
        def myClick():
            clickCount = "You did click me: "
            butLabel = Label(root, text =clickCount)
            butLabel.grid(row =3, column=7)

            inp = json.dumps(
                {
                    "drawPile": [stringToJson(ePile.get())],
                    "suitStack1": [stringToJson(eSuit1.get())],
                    "suitStack2": [stringToJson(eSuit2.get())],
                    "suitStack3": [stringToJson(eSuit3.get())],
                    "suitStack4": [stringToJson(eSuit4.get())],
                    "column1": [stringToJson(ecol1.get())],
                    "column2": [stringToJson(ecol2.get())],
                    "column3": [stringToJson(ecol3.get())],
                    "column4": [stringToJson(ecol4.get())],
                    "column5": [stringToJson(ecol5.get())],
                    "column6": [stringToJson(ecol6.get())],
                    "column7": [stringToJson(ecol7.get())]
                }
            )
            self.updateInp(inp)
            print(inp)
            root.destroy()

        #Buttons
        myButton = Button(root, text="Make New Move",command = myClick)
        myButton.grid(row =4, column=7)
        #----------------------------------------------
        #this is a button made for testing purposes
        # should be comented out when not developing
        def myMove():
            inp ="{\n\"drawPile\":\"[\"suits\":\"hearts\"]\",\n" \
                 "\"suitStack1\":\"h1\",\n" \
                 "\"suitStack2\":\"\",\n" \
                 "\"suitStack3\":\"\",\n" \
                 "\"suitStack4\":\"\",\n" \
                 "\"column1\":\"h3\",\n" \
                 "\"column2\":\"h4\",\n" \
                 "\"column3\":\"h5\",\n" \
                 "\"column4\":\"h6\",\n" \
                 "\"column5\":\"h7\",\n" \
                 "\"column6\":\"h8\",\n" \
                 "\"column7\":\"h9\",\n" \
                 "}"

            self.updateInp(inp)
            root.destroy()
        myButton = Button(root, text="testmove",command = myMove)
        myButton.grid(row =2, column=7)
        #----------------------------------------------

        root.mainloop()
        return self.input
        print("GUI ended")
