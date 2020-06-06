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
            img = cv2.resize(img ,(600,400))

            if cv2.waitKey(1) & 0xFF == ord('q'):
                rec.release()
                break

            board = BoardRuler()
            image, mask, succes = board.isolate(img)

            if succes:
                cv2.imshow("mask", mask)
                cv2.imshow("image",image)

                sectionsT,sectionsB = board.cutImageWithRulerLines(image)

                i = 1
                stack = self.stackImages(sectionsT[0],sectionsT)
                stack2 = self.stackImages(sectionsB[0],sectionsB)
                cv2.imshow("stack",stack)
                cv2.imshow("stack2", stack2)

                """
                # find cards two variables, hasCards = boolean, cards list of cards found in the image.
                cards, succes = cardAnal.findCards(image)
                if succes:
                    for i in range(len(cards)):
                        pass
                """