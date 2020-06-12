from AbstractUi import AbstractUI
from tkinter import *
#====================================
# ManGUI or Manual GUI - requres you to manully enter the input data
#------------------------------------
# This file containing a GUI is
# intented to work as to test sercer communication
# as well as a back-up should open CV not work
#====================================.


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
            inPile = ePile.get()
            inSuits = eSuit1.get()+",su1; "+eSuit2.get()+",su2; "+eSuit3.get()+",su3;"+eSuit4.get()+",su4;"
            inCols = ecol1.get()+",co1; "+ecol2.get()+",co2; "+ecol3.get()+",co3; "+ecol4.get()+",co4; "+ecol5.get()+",co5; "+ecol6.get()+",co6; "+ecol7.get()+",co7;"
            inp = inPile + " | "+ inSuits + "| "+inCols +"| "
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
            inp ="{\n\"h10\":\"pi\",\n" \
                 "\"\":\"su1\",\n" \
                 "\"\":\"su2\",\n" \
                 "\"\":\"su3\",\n" \
                 "\"\":\"su4\",\n" \
                 "\"h9\":\"co1\",\n" \
                 "\"h2\":\"co2\",\n" \
                 "\"h3\":\"co3\",\n" \
                 "\"h4\":\"co4\",\n" \
                 "\"h5\":\"co5\",\n" \
                 "\"h6\":\"co5\",\n" \
                 "\"h7\":\"co6\",\n" \
                 "\"h8\":\"co7\",\n" \
                 "}"

            self.updateInp(inp)
            root.destroy()
        myButton = Button(root, text="testmove",command = myMove)
        myButton.grid(row =2, column=7)
        #----------------------------------------------

        root.mainloop()
        return self.input
        print("GUI ended")
