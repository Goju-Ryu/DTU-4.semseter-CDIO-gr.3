from KabaleRecogniser import KabaleRecogniser
from GUI.SettingsGUI import SettingsGUI

#Author : Hans
class openCVCode:
    def __init__(self):
        pass

    def run(self):
        # Author : Hans
        s = SettingsGUI()
        k = KabaleRecogniser()
        Settings = s.run()
        return k.run(Settings)