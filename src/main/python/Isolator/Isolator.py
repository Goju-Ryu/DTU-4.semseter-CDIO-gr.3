from cv2 import cv2

from Isolator.BoardRuler import BoardRuler
from Isolator.CardAnalyser import CardAnalyser
import numpy as np

class Isolator:

    def __init__(self, showBoard, showBoardMask, showCards, showCardsMask ):
        self.showBoard = showBoard
        self.showBoardMask = showBoardMask
        self.showCards = showCards
        self.showCardsMask = showCardsMask

    def isolateCards(self, image):

        cardAnal = CardAnalyser()
        board = BoardRuler()
        image, mask, succes = board.isolate(image)

        if self.showBoard:
            cv2.imshow("board",image)

        if self.showBoardMask:
            cv2.imshow("boardMask", mask)

        if succes:
            # cv2.imshow("mask", mask)
            # cv2.imshow("board",image)

            topRow, bottomRow = board.cutImageWithRulerLines(image)
            topRowMask, bottomRowMask = board.cutImageWithRulerLines(mask)
            SuccesArray, CardArray, maskArray = cardAnal.findCards(topRow, topRowMask)

            if(self.showCards):
                cards = self.stackImages(CardArray[0], CardArray)
                cv2.imshow("stack", cards)

            if(self.showCardsMask):
                cardsMask = self.stackImages(maskArray[0], maskArray)
                cv2.imshow("stack2", cardsMask)

            return cards

    def stackImages(self,image,imageArr, i = 1):


        """if image.shape[0] > imageArr[i].shape[0]:
            imageArr[i] = cv2.resize( imageArr[i], ( imageArr[i].shape[0], image.shape[0] ))
        else:
            image = cv2.resize(image, (image.shape[0],imageArr[i].shape[0] ))"""

        img = np.concatenate((image, imageArr[i]), axis=1)

        if i == (len(imageArr) - 1):
            return img
        else:
            i +=1
            return self.stackImages(img, imageArr , i)
