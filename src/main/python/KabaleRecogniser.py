from BoardRuler import BoardRuler
from Card import Card
from CardAnalyser import CardAnalyser
from ImageProcessor import ImageProcessor
from Video import SVideo
import time
import cv2
import numpy as np

#komment


rec = SVideo()
cardAnal = CardAnalyser()
ruler = BoardRuler()

class KabaleRecogniser:

    def run(self):
        while True:
            img = rec.getFrame()
            if cv2.waitKey(1) & 0xFF == ord('q'):
                rec.release()
                break

            cv2.imshow("frame", img)
            board = BoardRuler()
            image ,mask,succes = board.isolate(img)

            cv2.imshow("image",image)
            cv2.imshow("mask",mask)
            """if succes :
                sections = board.cutImageWithRulerLines(image)

                # find cards two variables, hasCards = boolean, cards list of cards found in the image.
                cards, succes = cardAnal.findCards(image)
                if succes:
                    for i in range(len(cards)):
                        pass
            """