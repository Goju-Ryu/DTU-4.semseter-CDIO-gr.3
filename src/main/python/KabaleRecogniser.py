from BoardRuler import BoardRuler
from Card import Card
from CardAnalyser import CardAnalyser
from ImageProcessor import ImageProcessor
from Video import SVideo
import time
import cv2
import numpy as np

#komment


#rec = SVideo()
cardAnal = CardAnalyser()
ruler = BoardRuler()

class KabaleRecogniser:

    def run(self):
        rec = cv2.VideoCapture(1)
        time.sleep(1.000)
        while True:
            # to be removed
            ret, img = rec.read()

            if cv2.waitKey(1) & 0xFF == ord('q'):
                rec.release()
                break


            # to be re made
            #img = rec.getFrame()

            board = BoardRuler()
            image ,succes = board.isolate(img)


            if succes :
                cv2.imshow("res", image)
                sections = board.cutImageWithRulerLines(image)


            # find cards two variables, hasCards = boolean, cards list of cards found in the image.
            #cards, succes = cardAnal.findCards(img)
            #if succes:
            #    for i in range(len(cards)):
            #        #cv2.imshow("card" + str(i), cards[i].profile)
            #        pass

            if cv2.waitKey(20) & 0xff == ord('q'):
                break
            time.sleep(0)

