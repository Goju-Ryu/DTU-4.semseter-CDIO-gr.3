from cv2 import cv2

from Isolator.BoardRuler import BoardRuler
from Isolator.CardAnalyser import CardAnalyser

import numpy as np


from imageOperator.imageOperator import imageOperator

# isolator class responsibility is to isolate the card contours on the playing board.
# it does this by isolating the board - transforming the board into a topdown image
# then taking that top downimage and cutting it into images of the fields where we exspect cards to exists
# then for each of these scanning for squares and taking ones that are large enough to be cards.
class Isolator:

    def __init__(self, showBoard, showBoardMask, showCards, showCardsMask ):

        ## show image booleans
        self.showBoard = showBoard
        self.showBoardMask = showBoardMask
        self.showCards = showCards
        self.showCardsMask = showCardsMask

        ## objects
        self.imageOperator = imageOperator()
        self.cardAnalyser = CardAnalyser()
        self.board = BoardRuler()

    # returns cards opbject that it finds on an image, but it is only filled with its profile image.
    def isolateCards(self, image, Settings):

        # isolates the board on the image, using the Settings object.
        image, mask, succes = self.board.isolate(image, Settings)
        # the mask needs invering to find the cards, otherwise it finds everything else than the cards.
        mask = cv2.bitwise_not(mask)

        if succes:
            if self.showBoard:
                cv2.imshow("board",image)

            if self.showBoardMask:
                cv2.imshow("boardMask", mask)

            # here we cut the images into sections, where a section is an array where we exspect
            # a single card to be.
            # eks bottomRow = images of sections on the board bottom row, where each image has one card.

            topRow, bottomRow = self.board.cutImageWithRulerLines(image)
            topRowMask, bottomRowMask = self.board.cutImageWithRulerLines(mask)
            topCardArray, topMaskArray, topCards = self.cardAnalyser.findCards(topRow, topRowMask)
            botCardArray, botMaskArray, botCards = self.cardAnalyser.findCards(bottomRow, bottomRowMask)

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

            ## adds cards from bottomCards, to topcards,
            # such that they are all in a single array.
            for card in botCards:
                topCards.append(card)

            return topCards, succes

        return [], False



