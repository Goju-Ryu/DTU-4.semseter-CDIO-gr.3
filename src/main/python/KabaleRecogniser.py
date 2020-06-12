import os
from operator import itemgetter

import cv2

from Card import Card
from Isolator.Isolator import Isolator
from VideoInput.Video import SVideo, Video
from Validator.CardValidator import CardValidator
import time as TIME

from Isolator.CardAnalyser import CardAnalyser
cardAnal = CardAnalyser()


cardVal = CardValidator()



class KabaleRecogniser:

    def run(self, Settings):
        timeStart = TIME.time()
        rec = Video()
        sNameCnts = [["Hearts", 0], ["Spades", 0], ["Clubs", 0], ["Diamonds", 0], ["Ace", 0], ["Two", 0],
                  ["Three", 0], [ "Four", 0], ["Five", 0], ["Six", 0], ["Seven", 0],
                  ["Eight", 0], [ "Nine", 0], [ "Ten", 0], ["Jack", 0], [ "Queen", 0], [ "King", 0]]
        # statestik = [sNameCnts] * 14
        statistics = []

        for q in range(14):
            statistics.append([["Hearts", 0], ["Spades", 0], ["Clubs", 0], ["Diamonds", 0], ["Ace", 0], ["Two", 0],
                      ["Three", 0], [ "Four", 0], ["Five", 0], ["Six", 0], ["Seven", 0],
                      ["Eight", 0], [ "Nine", 0], [ "Ten", 0], ["Jack", 0], [ "Queen", 0], [ "King", 0]])



        while True:
            # Retrieving an image.
            img = rec.getFrame()

            # KEY PRESS CASES ________________
            keyPressed = cv2.waitKey(1) & 0xFF
            # stopping the program if 'q' is pressed
            if keyPressed == ord('q'):
                rec.stop()
                break

            # CARD ISOLATION AND ASSIGNMENT TO LIST _________________
            isolator = Isolator(False,False,True,False)
            cards, succes = isolator.isolateCards(img, Settings)
# CARD ISOLATION AND ASSIGNMENT TO LIST _________________
            i = 0
            for c in cards:
                stat = statistics[i]
                thisCard = cards[i]
                if thisCard.exists:
                    name1, name2 = cardVal.setCardRankAndSuit(thisCard)

                    # for counting occurences of card symbols
                    for name in stat:
                        if name1 == name[0]:
                            name[1] += 1
                        if name2 == name[0]:
                            name[1] += 1
                i += 1
            timeNow = TIME.time()
            timeDiff = timeNow - timeStart
            if ( timeDiff ) > 300:
                break
        cv2.destroyAllWindows()
        k = 0
        for stat in statistics:

            card1SuitStats = sorted(stat[0:4], key=itemgetter(1), reverse=True)
            # print("sorted suits: " + str(card1SuitStats))

            card1RankStats = sorted(stat[4:], key=itemgetter(1), reverse=True)
            # print("sorted ranks: " + str(card1RankStats))

            if card1RankStats[0][1] == 0 and card1SuitStats[0][1] == 0:
                cards[k].suit = "[No suit found]"
                cards[k].rank = "[No rank found]"
            else:
                cards[k].rank = card1RankStats[0][0]
                cards[k].suit = card1SuitStats[0][0]

            print("card " + str(k) + "  : " + cards[k].rank + " " + cards[k].suit)


            # print("stat " + str(k) + "  " + str(stat))
            k += 1

        # j = 0
        # for card in matchedCards:
        #     print("element " + str(j) + " " + card.rank + " " + card.suit)
        #     j += 1








