import os
from operator import itemgetter

import cv2

from Card import Card
from Isolator.Isolator import Isolator
from VideoInput.Video import SVideo
from CardValidator import CardValidator


from Isolator.CardAnalyser import CardAnalyser
cardAnal = CardAnalyser()

rec = SVideo()
cardVal = CardValidator()



class KabaleRecogniser:

    def run(self):

        sNameCnts = [["Hearts", 0], ["Spades", 0], ["Clubs", 0], ["Diamonds", 0], ["Ace", 0], ["Two", 0],
                  ["Three", 0], [ "Four", 0], ["Five", 0], ["Six", 0], ["Seven", 0],
                  ["Eight", 0], [ "Nine", 0], [ "Ten", 0], ["Jack", 0], [ "Queen", 0], [ "King", 0]]
        i = 0
        while True:
            # Retrieving an image.
            img = rec.getFrame()


# CARD ISOLATION AND ASSIGNMENT TO LIST _________________
            isolator = Isolator(False,False,True,False)
            cards, succes = isolator.isolateCards(img)
# CARD ISOLATION AND ASSIGNMENT TO LIST _________________


# KEY PRESS CASES ________________
            keyPressed = cv2.waitKey(1) & 0xFF
            # stopping the program if 'q' is pressed
            if keyPressed == ord('q'):
                rec.stop()
                break

            # checking the next card if i is pressed
            if keyPressed == ord('i'):
                if i is len(cards) - 2:
                    i = 0
                else:
                    i = i + 1
                print("counter: " + str(i))
                for s in sNameCnts:
                    s[1] = 0
# KEY PRESS CASES ________________


        #TODO overwriting test images for testing with easy to read profiles
            # cards[0].profile = cv2.imread("cardProfile_Ace_Clubs.jpg")
            # cards[1].profile = cv2.imread("cardProfile_Ace_Diamonds.jpg")
            # cards[2].profile = cv2.imread("cardProfile_Ace_Hearts.jpg")
            # cards[3].profile = cv2.imread("cardProfile_Ace_Spades.jpg")
            # cards[0].profile = cv2.resize(cards[0].profile, (225,300))
            # cards[1].profile = cv2.resize(cards[1].profile, (225, 300))
            # cards[2].profile = cv2.resize(cards[2].profile, (225, 300))
            # cards[3].profile = cv2.resize(cards[3].profile, (225, 300))

            # if succes:
                # for i in range(len(cards)-1):
                    # for i in range(7):


            thisCard = cards[i]
            name1, name2 = cardVal.setCardRankAndSuit(thisCard)

            # for counting occurences of card symbols
            for sName in sNameCnts:
                if name1 == sName[0]:
                    sName[1] += 1
                if name2 == sName[0]:
                    sName[1] += 1
            print(name1 + "\n" + name2 + "\n")
            print("all symbols counted: " + str(sNameCnts))


            #sorted lists and the found card identity
            cardSortedSuitDiffs = sorted(sNameCnts[0:4], key=itemgetter(1), reverse=True)
            print("sorted suits: " + str(cardSortedSuitDiffs))

            cardSortedRankDiffs = sorted(sNameCnts[4:], key=itemgetter(1), reverse=True)
            print("sorted ranks: " + str(cardSortedRankDiffs))

            cardIdentity = cardSortedRankDiffs[0][0] + " " + cardSortedSuitDiffs[0][0]
            print("THE CARD IS: " + cardIdentity)





        # TODO delete every non-used-attribute for the Card() class and cleanup by deleting and commenting
            # for img in thisCard.rankAndSuitContourImgs:
            #     thisCard.rankImg = img
            #     thisCard.suitImg = img
            #     cardVal.matchCard2(thisCard)
            #
            #     print(thisCard.rank_match_difference_value)
            #     print(thisCard.suit_match_difference_value)
            #     print(thisCard.best_rank_match)
            #     print(thisCard.best_suit_match)



            # thisCard = cards[3]
            # cardVal.setCardRankAndSuit(thisCard)
            # # cv2.imshow("rank", thisCard.rankImg)
            # # cv2.imshow("suit", thisCard.suitImg)
            # cards[i] = cardVal.matchCard(thisCard, compareRanks, compareSuits)
            # print(thisCard.best_rank_match)
            # print(thisCard.best_suit_match)







