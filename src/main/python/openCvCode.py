from GUI.SettingsGUI import SettingsGUI
from KabaleRecogniser import KabaleRecogniser


s = SettingsGUI()
k = KabaleRecogniser()
Settings = s.run()
print(k.run(Settings))

"""from KabaleRecogniser import KabaleRecogniser
from GUI.SettingsGUI import SettingsGUI

#Author : Hans
class openCVCode:
    
    # Author : Hans
    def __init__(self):
        pass
    
    # Author : Hans
    def run(self):
        s = SettingsGUI()
        k = KabaleRecogniser()
        Settings = s.run()
        return k.run(Settings)"""