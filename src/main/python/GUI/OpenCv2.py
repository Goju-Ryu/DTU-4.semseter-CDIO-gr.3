from AbstractUi import AbstractUI
from tkinter import *
from GUI.SettingsGUI import SettingsGUI
from KabaleRecogniser import KabaleRecogniser
#====================================
# ManGUI or Manual GUI - requres you to manully enter the input data
#------------------------------------
# This file containing a GUI is
# intented to work as to test sercer communication
# as well as a back-up should open CV not work
#====================================.


class ManGUI(AbstractUI):

    def run(self):
        s = SettingsGUI()
        k = KabaleRecogniser()
        Settings = s.run()
        x = k.run(Settings)

        # print(x)
        inp ="{\n\"h80\":\"pi\",\n" \
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


        return x
