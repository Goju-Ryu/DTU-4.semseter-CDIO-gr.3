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
        self.Settings = None
        self.firstRun = True

    def setSettings(self):
        s = SettingsGUI()
        self.Settings = s.run()

    def run(self):
        if(self.firstRun):
            self.setSettings()

        k = KabaleRecogniser()
        x = k.run(self.Settings)
        return x
