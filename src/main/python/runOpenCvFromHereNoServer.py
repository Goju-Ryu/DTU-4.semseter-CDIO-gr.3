from GUI.OpenCv2 import ManGUI
from KabaleRecogniser import KabaleRecogniser

prog = ManGUI()
k = KabaleRecogniser()
setting = prog.settingsSet()
k.run(setting)