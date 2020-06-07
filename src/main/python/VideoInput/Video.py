import time

import cv2
import threading
#Author : Hans
class Video:
    # Author : Hans
    def __init__(self):

        # finals
        self.FRAME_NAME = " video recording Frame "

        # regularly needed Variables
        self.cancelled = False

        # Video Stats
        self.rec = cv2.VideoCapture("video_01.mp4")

        # starting the Thread
        self.empt, self.frame = self.rec.read() # setting the veryFirst Frame just to avoid null pointers.
        self.thread = threading.Thread(target=self.run, args=()).start()

    # Author : Hans
    def run(self):
        while True:
            self.empt, self.frame = self.rec.read()
            if self.cancelled:
                self.rec.release()
                return

    # Author : Hans
    def start(self):
        self.thread.start()

    # Author : Hans
    def getFrame(self):
        return cv2.resize(self.frame,(1200,800))

    # Author : Hans
    def stop(self):
        self.cancelled = True

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
        return cv2.resize(img, (800,500))
        #return self.frame

    # Author : Hans
    def stop(self):
        self.rec.release()

