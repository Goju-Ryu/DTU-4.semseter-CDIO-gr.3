from BoardRuler import BoardRuler
from Card import Card
from CardAnalyser import CardAnalyser
from ImageProcessor import ImageProcessor
from Video import SVideo, Video
import time
import cv2
import numpy as np

#rec = Video()
cardAnal = CardAnalyser()
ruler = BoardRuler()

class KabaleRecogniser:
    def stackImages(self,image,imageArr, i = 1):

        """
        if image.shape[0] > imageArr[i].shape[0]:
            imageArr[i] = cv2.resize( imageArr[i], ( imageArr[i].shape[0], image.shape[0] ))
        else:
            image = cv2.resize(image, (image.shape[0],imageArr[i].shape[0] ))
        """
        img = np.concatenate((image, imageArr[i]), axis=1)

        if i == (len(imageArr) - 1):
            return img
        else:
            i +=1
            return self.stackImages(img, imageArr , i)

    def run(self):
        rec = cv2.VideoCapture("video_01.mp4")
        counter = 1
        while True:
            # Retrieving an image.
            counter +=1
            _,img = rec.read()
            img = cv2.resize(img ,(1200,800))

            #cv2.imshow("img", img)
            if cv2.waitKey(1) & 0xFF == ord('q'):
                rec.release()
                break

            board = BoardRuler()
            image, mask, succes = board.isolate(img)

            if succes:
                #cv2.imshow("mask", mask)
                #cv2.imshow("board",image)

                topRow,bottomRow = board.cutImageWithRulerLines(image)
                topRowMask, bottomRowMask = board.cutImageWithRulerLines(mask)

                stack = self.stackImages(topRow[0],topRow)
                #stack2 = self.stackImages(sectionsB[0],sectionsB)
               # cv2.imshow("stack",stack)
                #cv2.imshow("single",topRow[6])
                #cv2.imshow("stack2", stack2)

                # find cards two variables, hasCards = boolean, cards list of cards found in the image.
                cardAnal.findCards(topRow, topRowMask)

