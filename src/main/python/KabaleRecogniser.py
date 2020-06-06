from BoardRuler import BoardRuler
from Card import Card
from CardAnalyser import CardAnalyser
from ImageProcessor import ImageProcessor
from Video import SVideo, Video
import time
import cv2
import numpy as np

#komment


#rec = Video()
cardAnal = CardAnalyser()
ruler = BoardRuler()

class KabaleRecogniser:

    def run(self):
        rec = cv2.VideoCapture("video_01.mp4")
        counter = 1
        while True:
            # Retrieving an image.
            counter +=1
            _,img = rec.read()
            img = cv2.resize(img ,(600,400))

            if cv2.waitKey(1) & 0xFF == ord('q'):
                rec.release()
                break

            board = BoardRuler()
            image, mask, succes = board.isolate(img)

            cv2.imshow("mask",img)

            if succes:
                cv2.imshow("image",image)
            """
            if succes :
                sections = board.cutImageWithRulerLines(image)
    
                # find cards two variables, hasCards = boolean, cards list of cards found in the image.
                cards, succes = cardAnal.findCards(image)
                if succes:
                    for i in range(len(cards)):
                        pass
            """