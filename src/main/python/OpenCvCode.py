from GUI.SettingsGUI import SettingsGUI
from KabaleRecogniser import KabaleRecogniser


s = SettingsGUI()
k = KabaleRecogniser()
Settings = s.run()
print(k.run(Settings))