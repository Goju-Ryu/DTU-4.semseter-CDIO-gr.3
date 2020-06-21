from AbstractUi import AbstractUI
from tkinter import *
import json as json
#====================================
# ManGUI or Manual GUI - requires you to manually enter the input data
#------------------------------------
# This is for when you have initiated your board
# and desire to only know what Card have been revealed
#====================================.
#TODO: consider moving this functionality into ManGUI
param = "d1,h2,,h4,,h6,h7,h8,ace1,,ace3,EE"
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

def makeOutputString(inputStr, entries):
    outputs = []
    inputs = inputStr.split(",")
    i = 0
    for input in inputs:
        if input == "EE":
            outputs.append(None)
        elif input != "":
            outputs.append(input)
        elif input == "":
            outputs.append(entries[i])
            i += i
    return outputs



class ManGUI(AbstractUI):

    def __init__(self, paramStr):
        self.returnedData = json.loads(paramStr)
        self.paramStr = paramStr




    input = ""
    def updateInp(self,newString):
        self.input = newString

    def run(self):
        root = Tk()

        # list = param.split(",")
        #list = self.paramStr.split(",")


        # TODO get a list with each cards suit and rank for later use
        # for i in self.returnedData:
        #     list = self.returnedData[i]


        self.labels_list = []
        self.entry_list = []

        aceList = list[8:13]
        buildList = list[1:8]
        drawList = list[0:1]
        #Creates entries and labels for known and unknown builStacks
        for i in range(len(buildList)):
            if buildList[i] == '':
                entry = Entry(root, text=buildList[i])
                entry.grid(row=3+i, column=1+i)
                self.entry_list.append(entry)
            else:
                label = Label(root, text=buildList[i])
                label.grid(row=3+i, column=1+i)
                self.labels_list.append(label)
        #Creates entries and labels for known and unknown acestacks
        for i in range(len(aceList)):
            if aceList[i] == '':
                label = Label(root, text="EMPTY")
                label.grid(row=1, column=3+i)
                self.labels_list.append(label)
            elif aceList[i] == "EE":
                entry = Entry(root, text=aceList[i])
                entry.grid(row=1, column=3+i)
                self.entry_list.append(entry)
            else:
                label = Label(root, text=aceList[i])
                label.grid(row=1, column=3+i)
                self.labels_list.append(label)
        #Creates entries and labels for known and unknown drawstack
        for i in range(len(drawList)):
            if drawList[i] == '':
                label = Label(root, text="EMPTY")
                label.grid(row=6, column=0)
                self.labels_list.append(label)
            elif drawList[i] == "EE":
                entry = Entry(root, text=drawList[i])
                entry.grid(row=6, column=0)
                self.entry_list.append(entry)
            else:
                label = Label(root, text=drawList[i])
                label.grid(row=6, column=0)
                self.labels_list.append(label)


        #Labels I use
        myLabel0 = Label(root, text="SOLITARE KLODINKE")
        myLabel1 = Label(root, text = "Insert revealed card below")
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
            list2 = makeOutputString(self.paramStr, self.entry_list)
             #stringToJson(...) now returns a list containing two strings: "[suitInput]", "[rankInput]"
            inp = json.dumps(
                {
                    "DRAWSTACK": None if stringToJson(ePile.get())[0] == "" or stringToJson(ePile.get())[1] == ""
                    else {
                        "suit": stringToJson(list2[0])[0],
                        "rank": int(stringToJson(ePile.get())[1]),
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
