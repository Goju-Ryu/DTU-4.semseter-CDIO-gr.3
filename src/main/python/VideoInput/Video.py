import time

import cv2
import threading


class Video:
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

    def run(self):
        while True:
            self.empt, self.frame = self.rec.read()
            if self.cancelled:
                self.rec.release()
                return

    def start(self):
        self.thread.start()

    def getFrame(self):
        return cv2.resize(self.frame,(1200,800))

    def stop(self):
        self.cancelled = True


class SVideo:

    def __init__(self, truthVal:bool):
        print(truthVal)
        # finals
        self.VidName ="VideoInput/video_01.mp4"
        self.FRAME_NAME = " video recording Frame "

        # Video Stats
        self.rec = cv2.VideoCapture(self.VidName)

        # starting the Thread
        self.empt, self.frame = self.rec.read()  # setting the veryFirst Frame just to avoid null pointers.
        self.empt, self.frame = self.rec.read()


    def getFrame(self):
        self.empt, self.frame = self.rec.read()
        img = self.frame
        return cv2.resize(img, (1200, 800))
        #return self.frame

    def stop(self):
        self.rec.release()