import os
from operator import itemgetter
import json as json
import cv2

from Card import Card
from Isolator.Isolator import Isolator
from VideoInput.Video import SVideo, Video
from Validator.CardValidator import CardValidator
import time as TIME

from Isolator.CardAnalyser import CardAnalyser
cardAnal = CardAnalyser()
cardVal = CardValidator()


# kabale recogniser is a class that recognises our kabale syntax, and returns a string of what cards, the program
# can see on the board.
class KabaleRecogniser:

    # a method to take a video, and then for each image get a result,  and then make statisk analasys
    # ( for each card, whats the best result for that card ( by counting most votes) ). to then give a result.
    def run(self, Settings):

        # start a Timer, as a way to end the loop.
        timeStart = TIME.time()

        # this is a method to initialise the video capture.
        rec = Video()

        # statistics is a 2d arrray. where the integeres in these spaces are the "counters" where we count votes.
        # it needs to persist acros the loop, so is instantiated outside it.
        sNameCnts = [["Hearts", 0], ["Spades", 0], ["Clubs", 0], ["Diamonds", 0], ["Ace", 0], ["Two", 0],
                  ["Three", 0], [ "Four", 0], ["Five", 0], ["Six", 0], ["Seven", 0],
                  ["Eight", 0], [ "Nine", 0], [ "Ten", 0], ["Jack", 0], [ "Queen", 0], [ "King", 0]]

        sNameCnts2 = [["Hearts", 0], ["Spades", 0], ["Clubs", 0], ["Diamonds", 0], ["Ace", 0], ["Two", 0],
                     ["Three", 0], [ "Four", 0], ["Five", 0], ["Six", 0], ["Seven", 0],
                     ["Eight", 0], [ "Nine", 0], [ "Ten", 0], ["Jack", 0], [ "Queen", 0], [ "King", 0]]

        statistics = []
        for q in range(14):
            statistics.append([["Hearts", 0], ["Spades", 0], ["Clubs", 0], ["Diamonds", 0], ["Ace", 0], ["Two", 0],
                      ["Three", 0], [ "Four", 0], ["Five", 0], ["Six", 0], ["Seven", 0],
                      ["Eight", 0], [ "Nine", 0], [ "Ten", 0], ["Jack", 0], [ "Queen", 0], [ "King", 0]])


        # filming Loop.
        while True:

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
            isolator = Isolator(False,False,True,False)
            cards, succes = isolator.isolateCards(img, Settings)

            # looping throuch all cards found in the isolater.
            i = 0
            for c in cards:
                stat = statistics[i]
                if c.exists:

                    #this returns the evaluated names of the two best contours.
                    #so they are name1 and name2, are the names of these, and
                    #there isent a way of knowing wich is wich, so we do a check on this

                    name1, name2 = cardVal.setCardRankAndSuit(c)
                    # for counting occurences of card symbols
                    for name in stat:
                        if name1 == name[0]:
                            name[1] += 1
                        if name2 == name[0]:
                            name[1] += 1
                i += 1

            #this is a way of closing the loop.
            # where timeDiff overcedes the time limit
            # then end the loop.
            timeNow = TIME.time()
            timeDiff = timeNow - timeStart
            if ( timeDiff ) > 300:
                break

        # when the loop is done it is necesary to close all windows if any are open, otherwise the programs becomes
        # unresponsive, this is not necesary in the final version, but when testing it becomes an issue.
        cv2.destroyAllWindows()

        # here we gather the conclusion of the statistics. in a "card" array.
        k = 0
        for stat in statistics:

            # getting the first sub array of the first 4 elements   - the Suits
            card1SuitStats = sorted(stat[0:4], key=itemgetter(1), reverse=True)
            # getting everything else in a subarray                 - the Ranks
            card1RankStats = sorted(stat[4:], key=itemgetter(1), reverse=True)

            # if the answer is no card detected.
            if card1RankStats[0][1] == 0 or card1SuitStats[0][1] == 0:
                # no card contents definded
                cards[k].suit = "[No suit found]"
                cards[k].rank = "[No rank found]"
            else:
                cards[k].rank = card1RankStats[0][0]
                cards[k].suit = card1SuitStats[0][0]

            print("card " + str(k) + "  : " + cards[k].rank + " " + cards[k].suit)
            k += 1

        stackBottom = cards[0:7]
        stackTop = cards[7:]
        # jSonLiteral = "{"
        # jSonLiteral.append("\"drawpile\": \"" + stackTop[0].suit + stackTop[0].rank + "\", ")       # drawpile open card
        # jSonLiteral.append("\"suitStack1\": \"" + stackTop[3].suit + stackTop[3].rank) + "\", "     # suitStack 1
        # jSonLiteral.append("\"suitStack2\": \"" + stackTop[4].suit + stackTop[4].rank) + "\", "     # suitStack 2
        # jSonLiteral.append("\"suitStack3\": \"" + stackTop[5].suit + stackTop[5].rank) + "\", "     # suitStack 3
        # jSonLiteral.append("\"suitStack4\": \"" + stackTop[6].suit + stackTop[6].rank) + "\", "     # suitStack 4
        # jSonLiteral.append("\"coulumn1\": \"" + stackBottom[0].suit + stackBottom[0].rank) + "\", "    # column 1
        # jSonLiteral.append("\"coulumn2\": \"" + stackBottom[1].suit + stackBottom[1].rank) + "\", "    # column 2
        # jSonLiteral.append("\"coulumn3\": \"" + stackBottom[2].suit + stackBottom[2].rank) + "\", "    # column 3
        # jSonLiteral.append("\"coulumn4\": \"" + stackBottom[3].suit + stackBottom[3].rank) + "\", "    # column 4
        # jSonLiteral.append("\"coulumn5\": \"" + stackBottom[4].suit + stackBottom[4].rank) + "\", "    # column 5
        # jSonLiteral.append("\"coulumn6\": \"" + stackBottom[5].suit + stackBottom[5].rank) + "\", "    # column 6
        # jSonLiteral.append("\"coulumn7\": \"" + stackBottom[6].suit + stackBottom[6].rank) + "\", "    # column 7
        # jSonLiteral.append("}")
        inpu = json.dumps({
            "drawPile" : {"suit" : stackTop[0].suit, "rank" : stackTop[0].rank},
            "SuitStackHearts" : {"suit" : "hearts", "rank" : 1},
            "SuitStackClubs" : {"" : "", "" : 0},
            "SuitStackDimons" : {"" : "", "" : 0},
            "SuitStackSpades" : {"" : "", "" : 0},
            "Column1" : {"suit" : "hearts", "rank" : 1},
            "Column2" : {"suit" : "hearts", "rank" : 1},
            "Column3" : {"suit" : "hearts", "rank" : 1},
            "Column4" : {"suit" : "hearts", "rank" : 1},
            "Column5" : {"suit" : "hearts", "rank" : 1},
            "Column6" : {"suit" : "hearts", "rank" : 1},
            "Column7" : {"suit" : "hearts", "rank" : 1}

        })
            
        return inpu



