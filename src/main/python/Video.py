import time

import cv2
import threading


class Video:
    def __init__(self, visible: bool):

        # finals
        self.FRAME_NAME = " video recording Frame "

        # regularly needed Variables
        self.cancelled = False
        self.visible = visible

        # Video Stats
        self.rec = cv2.VideoCapture(0)

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
        return self.frame

    def stop(self):
        self.cancelled = True


class SVideo:

    def __init__(self):
        # finals
        self.FRAME_NAME = " video recording Frame "

        # regularly needed Variables
        self.cancelled = False

        # Video Stats
        self.rec = cv2.VideoCapture('video_01.mp4')

        # starting the Thread
        self.empt, self.frame = self.rec.read()  # setting the veryFirst Frame just to avoid null pointers.
        self.thread = threading.Thread(target=self.run, args=()).start()

    def run(self):
        while True:
            self.empt, self.frame = self.rec.read()
            time.sleep(0.1)
            if self.cancelled:
                self.rec.release()

                return

    def start(self):
        self.thread.start()

    def getFrame(self):
        return self.frame

    def stop(self):
        self.cancelled = True