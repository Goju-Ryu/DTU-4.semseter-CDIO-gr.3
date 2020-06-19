import json as json
import cv2
from Isolator.Isolator import Isolator
from Statistiks.statestics import statistics
from VideoInput.Video import SVideo, Video, videoGen
from Validator.CardValidator import CardValidator
import time as TIME
from Isolator.CardAnalyser import CardAnalyser
from imageOperator.imageOperator import imageOperator

cardAnal = CardAnalyser()
cardVal = CardValidator()


SUCCESCRITERIA = 30

# kabale recogniser is a class that recognises our kabale syntax, and returns a string of what cards, the program
# can see on the board.
class KabaleRecogniser:


    def __init__(self):
        self.cards = []
        self.font = cv2.FONT_HERSHEY_SIMPLEX

    # a method to take a video, and then for each image get a result,  and then make statisk analasys
    # ( for each card, whats the best result for that card ( by counting most votes) ). to then give a result.
    def run(self, Settings):

        Operator = imageOperator()

        # start a counter to know when to end the loop.
        succesCounter = 0

        # this is a method to initialise the video capture.
        vidG = videoGen()
        rec = vidG.getVideo()

        # statestik initialisering. is used for counting the results.
        self.statestics = statistics()

        # filming Loop.
        while True:

            if succesCounter > SUCCESCRITERIA :
                break
            # this is openCV code, get the image, and then it gives an error if the keypressed isent there.
            # or rather it refuses to return an image, so it is necesary for it to be here.
            img = rec.getFrame()
            keyPressed = cv2.waitKey(1) & 0xFF
            if keyPressed == ord('q'):
                rec.stop()
                break

            # isolater, isolates the board, and all potential cards on this board, it
            # uses the HSV settings passed in the settings object for its thresholding.
            # the boolean paramters are : showBoard, ShowBoardMask, ShowCards, showCardsMask;
            isolator = Isolator(False,True,False,False)
            self.cards, succes = isolator.isolateCards(img, Settings)

            if succes :
                succesCounter += 1

                # looping throuch all cards found in the isolater.
                self.recogniseCards()


            if len(self.cardImagesStack) > 1:
                cardImageStacked = Operator.stackImages(self.cardImagesStack[0], self.cardImagesStack)
                cv2.imshow("cardStacked", cardImageStacked)

        # when the loop is done it is necesary to close all windows if any are open, otherwise the programs becomes
        # unresponsive, this is not necesary in the final version, but when testing it becomes an issue.
        cv2.destroyAllWindows()

        # evaluate results
        self.cards = self.statestics.evalListOfCards(self.cards)

        return self.interpreteResults()

    def interpreteResults(self):

        stackBottom = self.cards[0:7]
        stackTop = self.cards[7:]

        # The decided positions for the card placement on the board. This is the placement the java program expects to get.
        # The corresponding elements in the cards list for this class starts with 0th element at the buttom right cornor of a game board, you imagine in from of you

        results = json.dumps({
            "DRAWSTACK": {"suit": stackTop[5].suit, "rank": stackTop[5].rank},
            "SUITSTACKHEARTS": {"suit": stackTop[0].suit, "rank": stackTop[0].rank},
            "SUITSTACKCLUBS": {"suit": stackTop[1].suit, "rank": stackTop[1].rank},
            "SUITSTACKDIAMONDS": {"suit": stackTop[2].suit, "rank": stackTop[2].rank},
            "SUITSTACKSPADES": {"suit": stackTop[3].suit, "rank": stackTop[3].rank},
            "BUILDSTACK1": {"suit": stackBottom[6].suit, "rank": stackBottom[6].rank},
            "BUILDSTACK2": {"suit": stackBottom[5].suit, "rank": stackBottom[5].rank},
            "BUILDSTACK3": {"suit": stackBottom[4].suit, "rank": stackBottom[4].rank},
            "BUILDSTACK4": {"suit": stackBottom[3].suit, "rank": stackBottom[3].rank},
            "BUILDSTACK5": {"suit": stackBottom[2].suit, "rank": stackBottom[2].rank},
            "BUILDSTACK6": {"suit": stackBottom[1].suit, "rank": stackBottom[1].rank},
            "BUILDSTACK7": {"suit": stackBottom[0].suit, "rank": stackBottom[0].rank},
        })
        return results

    def recogniseCards(self):
        # looping throuch all cards found in the isolater.
        i = 0
        self.cardImagesStack = []
        for c in self.cards:
            if c.exists:

                # this returns the evaluated names of the two best contours.
                # so they are name1 and name2, are the names of these, and
                # there isent a way of knowing wich is wich, so we do a check on this

                name1, name2, cardImage, succes = cardVal.setCardRankAndSuit(c)
                if succes:
                    self.statestics.statisticInput(name1,name2,i)
                    rank, suit = self.statestics.statGetCardValue(i)

                    cv2.putText(cardImage,str( rank ) , (0, 70), self.font, 0.5, (0, 0, 255), 2, cv2.LINE_AA)
                    cv2.putText(cardImage, str(suit[0]), (25, 70), self.font, 0.5, (255, 0, 0), 2, cv2.LINE_AA)
                    self.cardImagesStack.append(cardImage)

            i += 1
