import time

import cv2
import threading
#Author : Hans
#Author : Hans
class Video:

    # Author : Hans
    def __init__(self):
        # finals
        self.VidName = 1
        self.FRAME_NAME = " video recording Frame "

        # Video Stats
        self.rec = cv2.VideoCapture(self.VidName, cv2.CAP_DSHOW)

        # starting the Thread
        self.empt, self.frame = self.rec.read()  # setting the veryFirst Frame just to avoid null pointers.
        self.empt, self.frame = self.rec.read()

    # Author : Hans
    def getFrame(self):
        self.empt, self.frame = self.rec.read()
        # img = self.frame
        # return cv2.resize(img, (800,500))

        return self.frame

    # Author : Hans
    def stop(self):
        self.rec.release()


#Author : Hans
class SVideo:

    # Author : Hans
    def __init__(self):
        # finals
        self.VidName ="VideoInput/video_01.mp4"
        self.FRAME_NAME = " video recording Frame "

        # Video Stats
        self.rec = cv2.VideoCapture(self.VidName)

        # starting the Thread
        self.empt, self.frame = self.rec.read()  # setting the veryFirst Frame just to avoid null pointers.
        self.empt, self.frame = self.rec.read()

    # Author : Hans
    def getFrame(self):
        self.empt, self.frame = self.rec.read()
        img = self.frame
        #return cv2.resize(img, (800,500))
        return self.frame

    # Author : Hans
    def stop(self):
        self.rec.release()

