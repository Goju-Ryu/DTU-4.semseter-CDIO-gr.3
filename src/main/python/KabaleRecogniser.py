from BoardRuler import BoardRuler
from Card import Card
from CardAnalyser import CardAnalyser
from ImageProcessor import ImageProcessor
from Video import SVideo
import time
import cv2
import numpy as np


#rec = SVideo()
cardAnal = CardAnalyser()
ruler = BoardRuler()

class KabaleRecogniser:

    def run(self):
        #rec = cv2.VideoCapture(0)
        rec = cv2.VideoCapture('video_01.mp4',0)
        while True:
            # to be removed
            empt, frame = rec.read()
            img = frame
            if not empt:
                rec.release()
                rec = cv2.VideoCapture('video_01.mp4', 0)
                empt, frame = rec.read()
                img = frame
            # to be re made
            #img = rec.getFrame()

            # find cards two variables, hasCards = boolean, cards list of cards found in the image.
            cards, succes = cardAnal.findCards(img)
            if succes:
                for i in range(len(cards)):
                    #cv2.imshow("card" + str(i), cards[i].profile)
                    pass

            #ruler.decorateImageRulerLines(img)


            if cv2.waitKey(20) & 0xff == ord('q'):
                break
            time.sleep(0)

