import json as json

import cv2
from Isolator.CardAnalyser import CardAnalyser
from Isolator.Isolator import Isolator
from Statistiks.statestics import statistics
from Validator.CardValidator import CardValidator
from VideoInput.Video import videoGen
from imageOperator.imageOperator import imageOperator

cardAnal = CardAnalyser()
cardVal = CardValidator()


SUCCES_TIMER = 30

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

            # This if ends the video recording, however it contains another if, that resets the loop.
            # If the recording was errored. it is errored if it has half initiated cards.
            # a card with a rank, but no suit. or reversed.

            if succesCounter > SUCCES_TIMER:
                if self.reEnableLoop():
                    #todo check while runing that this self.statestics = None actualy resets the statestics.
                    self.statestics = None
                    self.statestics = statistics()
                    succesCounter = 0
                else:
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

            if self.gotCardImageStack:
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

        # The decided positions for the card placement on the board. This is the placement the java program expects to
        # get. The corresponding elements in the cards list for this class starts with 0th element at the buttom right
        # corner of a game board, you imagine in from of you

        results = json.dumps({
            "DRAWSTACK":        None if not stackTop[5].exists else     {"suit": stackTop[5].suit.upper()   , "rank": int(stackTop[5].rank)     , "isFacedUp": "true"},
            "SUITSTACKHEARTS":  None if not stackTop[0].exists else     {"suit": stackTop[0].suit.upper()   , "rank": int(stackTop[0].rank)     , "isFacedUp": "true"},
            "SUITSTACKCLUBS":   None if not stackTop[1].exists else     {"suit": stackTop[1].suit.upper()   , "rank": int(stackTop[1].rank)     , "isFacedUp": "true"},
            "SUITSTACKDIAMONDS":None if not stackTop[2].exists else     {"suit": stackTop[2].suit.upper()   , "rank": int(stackTop[2].rank)     , "isFacedUp": "true"},
            "SUITSTACKSPADES":  None if not stackTop[3].exists else     {"suit": stackTop[3].suit.upper()   , "rank": int(stackTop[3].rank)     , "isFacedUp": "true"},
            "BUILDSTACK1":      None if not stackBottom[6].exists else  {"suit": stackBottom[6].suit.upper(), "rank": int(stackBottom[6].rank)  , "isFacedUp": "true"},
            "BUILDSTACK2":      None if not stackBottom[5].exists else  {"suit": stackBottom[5].suit.upper(), "rank": int(stackBottom[5].rank)  , "isFacedUp": "true"},
            "BUILDSTACK3":      None if not stackBottom[4].exists else  {"suit": stackBottom[4].suit.upper(), "rank": int(stackBottom[4].rank)  , "isFacedUp": "true"},
            "BUILDSTACK4":      None if not stackBottom[3].exists else  {"suit": stackBottom[3].suit.upper(), "rank": int(stackBottom[3].rank)  , "isFacedUp": "true"},
            "BUILDSTACK5":      None if not stackBottom[2].exists else  {"suit": stackBottom[2].suit.upper(), "rank": int(stackBottom[2].rank)  , "isFacedUp": "true"},
            "BUILDSTACK6":      None if not stackBottom[1].exists else  {"suit": stackBottom[1].suit.upper(), "rank": int(stackBottom[1].rank)  , "isFacedUp": "true"},
            "BUILDSTACK7":      None if not stackBottom[0].exists else  {"suit": stackBottom[0].suit.upper(), "rank": int(stackBottom[0].rank)  , "isFacedUp": "true"},
        })
        return results

    def recogniseCards(self):
        # looping throuch all cards found in the isolater.
        i = 0
        gotCards = False
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
                    gotCards = True
            i += 1
        self.gotCardImageStack = gotCards

    def reEnableLoop(self):
        for stackCard in self.cards:
            if stackCard is not None:
                if stackCard.exists:
                    if stackCard.rank is None:
                        return True

                    if stackCard.suit is None:
                        return True

                    if stackCard.rank:
                        return True

                    if stackCard.suit:
                        return True

        return False
