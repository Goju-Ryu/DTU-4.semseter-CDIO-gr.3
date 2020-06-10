from cv2 import cv2

from Isolator.BoardRuler import BoardRuler
from CardAnalyser import CardAnalyser
import numpy as np

#Author : Hans
class Isolator:
    # Author : Hans
    def __init__(self, showBoard, showBoardMask, showCards, showCardsMask ):
        self.showBoard = showBoard
        self.showBoardMask = showBoardMask
        self.showCards = showCards
        self.showCardsMask = showCardsMask

    # Author : Hans
    def isolateCards(self, image, Settings):

        cardAnal = CardAnalyser()
        board = BoardRuler()
        image, mask, succes = board.isolate(image,Settings)
        if succes:
            if self.showBoard:
                cv2.imshow("board",image)

            if self.showBoardMask:
                cv2.imshow("boardMask", mask)

            topRow, bottomRow = board.cutImageWithRulerLines(image)
            topRowMask, bottomRowMask = board.cutImageWithRulerLines(mask)
            topCardArray, topMaskArray, topCards = cardAnal.findCards(topRow, topRowMask)
            botCardArray, botMaskArray, botCards = cardAnal.findCards(bottomRow, bottomRowMask)

            if(self.showCards):
                stack1= self.stackImages(botCardArray[0], botCardArray)
                stack = self.stackImages(topCardArray[0], topCardArray)
                cv2.imshow("stack Bottom", stack1)
                cv2.imshow("stack Top", stack)

            if(self.showCardsMask):
                stack1= self.stackImages(botMaskArray[0], botMaskArray)
                stack = self.stackImages(topMaskArray[0], topMaskArray)
                cv2.imshow("stack Bottom Mask", stack1)
                cv2.imshow("stack Top Mask", stack)

            #HER EMIL
            for card in botCards:
                topCards.append(card)
            return topCards, succes
        return [], False

    # Author : Hans
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
