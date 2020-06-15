import time

import cv2
import threading

# this class is the video recording, with information about capturing images, and where
# to do it from. this one records from the next camera than the webcam
class Video:

    def __init__(self):

        # finals
        self.VidName = 1
        self.FRAME_NAME = " video recording Frame "

        # Video Stats
        self.rec = cv2.VideoCapture(self.VidName,cv2.CAP_DSHOW)
        # self.rec = cv2.VideoCapture("video_01.mp4")
        self.rec.set(3, 1920)
        self.rec.set(4, 1080)

        # starting the Thread
        self.empt, self.frame = self.rec.read()  # setting the veryFirst Frame just to avoid null pointers.
        self.empt, self.frame = self.rec.read()


    def getFrame(self):
        self.empt, self.frame = self.rec.read()
        img = self.frame
        return self.frame


    def stop(self):
        self.rec.release()


# this class is the video recording, with information about capturing images, and where
# to do it from. this one does not record, but imulates it by reading a video();
class SVideo:


    def __init__(self):
        # finals
        self.VidName ="VideoInput/video_01.mp4"
        self.FRAME_NAME = " video recording Frame "


        # Video Stats
        self.rec = cv2.VideoCapture(self.VidName)
        self.rec.set(3,1920)
        self.rec.set(4,1080)

        # starting the Thread
        self.empt, self.frame = self.rec.read()  # setting the veryFirst Frame just to avoid null pointers.
        self.empt, self.frame = self.rec.read()

    def getFrame(self):
        self.empt, self.frame = self.rec.read()
        img = self.frame
        #return cv2.resize(img, (800,500))
        return self.frame

    def stop(self):
        self.rec.release()

