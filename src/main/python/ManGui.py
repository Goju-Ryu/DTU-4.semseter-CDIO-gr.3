from AbstractUi import AbstractUI
from tkinter import *
#====================================
# ManGUI or Manual GUI - requres you to manully enter the input data
#------------------------------------
# This file containing a GUI is
# intented to work as to test sercer communication
# as well as a back-up should open CV not work
#====================================


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

        myLabel0.grid(row =0, column=1)
        myLabel1.grid(row =1, column=0)
        myLabel2.grid(row =2, column=1)
        myLabel3.grid(row =1, column=1)

        moveCount = 0
        def myClick():
            clickCount = "You did click me: "
            butLabel = Label(root, text =clickCount)
            butLabel.grid(row =3, column=7)
            inPile = ePile.get()
            inSuits = eSuit1.get()+", "+eSuit2.get()+", "+eSuit3.get()+", "+eSuit4.get()
            inCols = ecol1.get()+", "+ecol2.get()+", "+ecol3.get()+", "+ecol4.get()+", "+ecol5.get()+", "+ecol6.get()+", "+ecol7.get()
            inp = inPile + " | "+ inSuits + "| "+inCols
            self.updateInp(inp)
            print(inp)
            root.destroy()

        #Buttons
        myButton = Button(root, text="Make New Move",command = myClick)
        myButton.grid(row =4, column=7)

        root.mainloop()
        return self.input
        print("GUI ended")
