from cv2 import cv2

from Isolator.BoardRuler import BoardRuler
from Isolator.CardAnalyser import CardAnalyser

import numpy as np

# isolator class responsibility is to isolate the card contours on the playing board.
# it does this by isolating the board - transforming the board into a topdown image
# then taking that top downimage and cutting it into images of the fields where we exspect cards to exists
# then for each of these scanning for squares and taking ones that are large enough to be cards.
from imageOperator.imageOperator import imageOperator


class Isolator:

    def __init__(self, showBoard, showBoardMask, showCards, showCardsMask ):
        self.showBoard = showBoard
        self.showBoardMask = showBoardMask
        self.showCards = showCards
        self.showCardsMask = showCardsMask
        self.imageOperator = imageOperator();


    def isolateCards(self, image, Settings):

        cardAnal = CardAnalyser()
        board = BoardRuler()
        image, mask, succes = board.isolate(image, Settings)
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

                stack1 = self.imageOperator.stackImages(botCardArray[0],botCardArray)
                stack = self.imageOperator.stackImages(topCardArray[0],topCardArray)

                cv2.imshow("stack Bottom", stack1)
                cv2.imshow("stack Top", stack)

            if(self.showCardsMask):

                stack1 = self.imageOperator.stackImages(botMaskArray[0],botMaskArray)
                stack = self.imageOperator.stackImages(topMaskArray[0],topMaskArray)

                cv2.imshow("stack Bottom Mask", stack1)
                cv2.imshow("stack Top Mask", stack)

            for card in botCards:
                topCards.append(card)
            return topCards, succes
        return [], False



