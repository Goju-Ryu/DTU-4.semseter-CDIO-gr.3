import numpy as np
from cv2 import cv2

from GUI.SettingsObject import SettingsObject
from VideoInput.Video import SVideo, Video


#Author : Hans
def empty(x):
    pass
#Author : Hans
class SettingsGUI():
    # Author : Hans
    def __init__(self):
        self.hej = 1

    # Author : Hans
    def run(self):
        vid = Video()
        img = vid.getFrame()
        size=(900,100)

        self.windowName = "window"
        cv2.namedWindow(self.windowName)

        cv2.createTrackbar("H_min", self.windowName, 50, 180, empty)
        cv2.createTrackbar("H_max", self.windowName, 90, 180, empty)

        cv2.createTrackbar("S_min", self.windowName, 60, 255, empty)
        cv2.createTrackbar("S_max", self.windowName, 230, 255, empty)

        cv2.createTrackbar("V_min", self.windowName, 60, 255, empty)
        cv2.createTrackbar("V_max", self.windowName, 230, 255, empty)

        while True:
            img = vid.getFrame()
            img = cv2.resize(img,size)

            Hmin = cv2.getTrackbarPos("H_min",self.windowName)
            Hmax = cv2.getTrackbarPos("H_max", self.windowName)
            Smin = cv2.getTrackbarPos("S_min", self.windowName)
            Smax = cv2.getTrackbarPos("S_max", self.windowName)
            Vmin = cv2.getTrackbarPos("V_min", self.windowName)
            Vmax = cv2.getTrackbarPos("V_max", self.windowName)

            hsv = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)
            lower_blue = np.array([Hmin, Smin, Vmin])
            upper_blue = np.array([Hmax, Smax, Vmax])
            mask = cv2.inRange(hsv, lower_blue, upper_blue)

            cv2.imshow(self.windowName, mask )

            if cv2.waitKey(1) & 0xFF == ord('q'):
                vid.stop()
                cv2.destroyAllWindows()
                break

        Sett = SettingsObject()
        Sett.setHue(Hmin,Hmax)
        Sett.setSat(Smin,Smax)
        Sett.setVal(Vmin,Vmax)
        return Sett
