from AbstractUi import AbstractUI
from tkinter import *
from GUI.SettingsGUI import SettingsGUI
from GUI.SettingsObject import SettingsObject
from KabaleRecogniser import KabaleRecogniser
#====================================
# ManGUI or Manual GUI - requres you to manully enter the input data
#------------------------------------
# This file containing a GUI is
# intented to work as to test sercer communication
# as well as a back-up should open CV not work
#====================================.


class ManGUI(AbstractUI):

    def __init__(self):
        self.SatSet = False

    def settingsSet(self):
        s = SettingsGUI()
        Settings = s.run()
        self.Settings = Settings

    def run(self):
        k = KabaleRecogniser()
        if(self.SatSet):
            x = k.run(self.Settings)
        else:
            Settings = SettingsObject()
            x = k.run(Settings)
        return x
