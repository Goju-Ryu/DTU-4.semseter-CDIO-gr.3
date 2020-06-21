from AbstractUi import AbstractUI
from tkinter import *
import json as json
#====================================
# ManGUI or Manual GUI - requires you to manually enter the input data
#------------------------------------
# This file containing a GUI is
# intended to work as to test server communication
# as well as a back-up should open CV not work
#====================================.


def stringToJson(inputStr):
    suit = ""
    su = inputStr[0:1]
    nr = inputStr[1:]
    if(su == "h"):
        suit = "HEARTS"
    elif(su == "c"):
        suit = "CLUBS"
    elif(su == "d"):
        suit = "DIAMONDS"
    elif(su == "s"):
        suit = "SPADES"

    return [suit, nr]

class ManGUI(AbstractUI):
    input = ""
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
        eSuit1.grid(row =1, column=3)
        eSuit2.grid(row =1, column=4)
        eSuit3.grid(row =1, column=5)
        eSuit4.grid(row =1, column=6)
        ecol1.grid(row =3, column=1)
        ecol2.grid(row =4, column=2)
        ecol3.grid(row =5, column=3)
        ecol4.grid(row =6, column=4)
        ecol5.grid(row =7, column=5)
        ecol6.grid(row =8, column=6)
        ecol7.grid(row =9, column=7)

        #Labels I use
        myLabel0 = Label(root, text="SOLITARE KLODINKE")
        myLabel1 = Label(root, text = "Pile : below")
        myLabel2 = Label(root, text = "Coulumns: below")
        myLabel3 = Label(root, text = "Suits: right")
        myLabel4 = Label(root, text="CDIO DTU GR. 3")
        myLabel5 = Label(root, text="h1 = hearts 1")
        myLabel6 = Label(root, text="c2 = clubs 2")
        myLabel7 = Label(root, text="d1 = diamonds 1")
        myLabel8 = Label(root, text="s5 = spades 5")


        myLabel0.grid(row =0, column=0)
        myLabel4.grid(row =0, column=1)
        myLabel5.grid(row =0, column=3)
        myLabel6.grid(row =0, column=4)
        myLabel7.grid(row =0, column=5)
        myLabel8.grid(row =0, column=6)


        myLabel1.grid(row =5, column=0)
        myLabel2.grid(row =2, column=1)
        myLabel3.grid(row =1, column=2)

        moveCount = 0
        def myClick():
            clickCount = "You did click me: "
            butLabel = Label(root, text =clickCount)
            butLabel.grid(row =3, column=7)

            # stringToJson(...) now returns a list containing two strings: "[suitInput]", "[rankInput]"
            inp = json.dumps(
                {
                    "DRAWSTACK": None if stringToJson(ePile.get())[0] == "" or stringToJson(ePile.get())[1] == ""
                    else {
                        "suit": stringToJson(ePile.get())[0],
                        "rank": int(stringToJson(ePile.get())[1]),
                        "isFacedUp": True
                        },
                    "SUITSTACKHEARTS": None if stringToJson(eSuit1.get())[0] == "" or stringToJson(eSuit1.get())[1] == ""
                    else {
                        "suit": stringToJson(eSuit1.get())[0],
                        "rank": int(stringToJson(eSuit1.get())[1]),
                        "isFacedUp": True
                        },
                    "SUITSTACKCLUBS": None if stringToJson(eSuit2.get())[0] == "" or stringToJson(eSuit2.get())[1] == ""
                    else {
                        "suit": stringToJson(eSuit2.get())[0],
                        "rank": int(stringToJson(eSuit2.get())[1]),
                        "isFacedUp": True
                        },
                    "SUITSTACKDIAMONDS": None if stringToJson(eSuit3.get())[0] == "" or stringToJson(eSuit3.get())[1] == ""
                    else {
                        "suit": stringToJson(eSuit3.get())[0],
                        "rank": int(stringToJson(eSuit3.get())[1]),
                        "isFacedUp": True
                         },
                    "SUITSTACKSPADES": None if stringToJson(eSuit4.get())[0] == "" or stringToJson(eSuit4.get())[1] == ""
                    else {
                        "suit": stringToJson(eSuit4.get())[0],
                        "rank": int(stringToJson(eSuit4.get())[1]),
                        "isFacedUp": True
                        },
                    "BUILDSTACK1": None if stringToJson(ecol1.get())[0] == "" or stringToJson(ecol1.get())[1] == ""
                    else {
                        "suit": stringToJson(ecol1.get())[0],
                        "rank": int(stringToJson(ecol1.get())[1]),
                        "isFacedUp": True
                        },
                    "BUILDSTACK2": None if stringToJson(ecol2.get())[0] == "" or stringToJson(ecol2.get())[1] == ""
                    else {
                        "suit": stringToJson(ecol2.get())[0],
                        "rank": int(stringToJson(ecol2.get())[1]),
                        "isFacedUp": True
                        },
                    "BUILDSTACK3": None if stringToJson(ecol3.get())[0] == "" or stringToJson(ecol3.get())[1] == ""
                    else {
                        "suit": stringToJson(ecol3.get())[0],
                        "rank": int(stringToJson(ecol3.get())[1]),
                        "isFacedUp": True
                        },
                    "BUILDSTACK4": None if stringToJson(ecol4.get())[0] == "" or stringToJson(ecol4.get())[1] == ""
                    else {
                        "suit": stringToJson(ecol4.get())[0],
                        "rank": int(stringToJson(ecol4.get())[1]),
                        "isFacedUp": True
                        },
                    "BUILDSTACK5": None if stringToJson(ecol5.get())[0] == "" or stringToJson(ecol5.get())[1] == ""
                    else {
                        "suit": stringToJson(ecol5.get())[0],
                        "rank": int(stringToJson(ecol5.get())[1]),
                        "isFacedUp": True
                        },
                    "BUILDSTACK6": None if stringToJson(ecol6.get())[0] == "" or stringToJson(ecol6.get())[1] == ""
                    else {
                        "suit": stringToJson(ecol6.get())[0],
                        "rank": int(stringToJson(ecol6.get())[1]),
                        "isFacedUp": True
                        },
                    "BUILDSTACK7": None if stringToJson(ecol7.get())[0] == "" or stringToJson(ecol7.get())[1] == ""
                    else {
                        "suit": stringToJson(ecol7.get())[0],
                        "rank": int(stringToJson(ecol7.get())[1]),
                        "isFacedUp": True
                        }
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
            inp = json.dumps(
                {"DRAWSTACK": {"suit": "HEARTS", "rank": 2, "isFacedUp": "true"},
                 "SUITSTACKHEARTS": None,
                 "SUITSTACKCLUBS": None,
                 "SUITSTACKDIAMONDS": None,
                 "SUITSTACKSPADES": None,
                 "BUILDSTACK1": {"suit": "HEARTS", "rank": 3, "isFacedUp": "true"},
                 "BUILDSTACK2": {"suit": "SPADES", "rank": 4, "isFacedUp": "true"},
                 "BUILDSTACK3": {"suit": "DIAMONDS", "rank": 5, "isFacedUp": "true"},
                 "BUILDSTACK4": {"suit": "HEARTS", "rank": 6, "isFacedUp": "true"},
                 "BUILDSTACK5": {"suit": "CLUBS", "rank": 7, "isFacedUp": "true"},
                 "BUILDSTACK6": {"suit": "HEARTS", "rank": 9, "isFacedUp": "true"},
                 "BUILDSTACK7": {"suit": "HEARTS", "rank": 10, "isFacedUp": "true"}}
            )

            self.updateInp(inp)
            root.destroy()
        myButton = Button(root, text="testmove",command = myMove)
        myButton.grid(row =6, column=7)
        #----------------------------------------------

        root.mainloop()
        return self.input
        print("GUI ended")
