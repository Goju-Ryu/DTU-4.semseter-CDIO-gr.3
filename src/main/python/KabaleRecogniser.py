import os
from operator import itemgetter

import cv2
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



        while True:
            # Retrieving an image.
            img = rec.getFrame()

            #cv2.imshow("img", img)
            if cv2.waitKey(1) & 0xFF == ord('q'):
                rec.stop()
                break



            # img2 = cv2.imread()





            isolator = Isolator(False,False,True,False)
            cards, succes = isolator.isolateCards(img)

            # img = cv2.imread("test.png")
            # cv2.imshow("test", img)
            # cards, succes = isolator.isolateCards(img)


            # cards[0].profile = cv2.imread("cardProfile_Ace_Clubs.jpg")
            # cards[1].profile = cv2.imread("cardProfile_Ace_Diamonds.jpg")
            # cards[2].profile = cv2.imread("cardProfile_Ace_Hearts.jpg")
            # cards[3].profile = cv2.imread("cardProfile_Ace_Spades.jpg")
            # cards[0].profile = cv2.resize(cards[0].profile, (225,300))
            # cards[1].profile = cv2.resize(cards[1].profile, (225, 300))
            # cards[2].profile = cv2.resize(cards[2].profile, (225, 300))
            # cards[3].profile = cv2.resize(cards[3].profile, (225, 300))
            # cards[0].profile = cv2.imread("C:\\Users\\Emil\\Dropbox\\Sent files\\kabale\\cardRanks\\profile_Two.jpg")
            # cards[1].profile = cv2.imread("C:\\Users\\Emil\\Dropbox\\Sent files\\kabale\\cardRanks\\profile_Three.jpg")
            # cards[2].profile = cv2.imread("C:\\Users\\Emil\\Dropbox\\Sent files\\kabale\\cardRanks\\profile_Four.jpg")
            # cards[3].profile = cv2.imread("C:\\Users\\Emil\\Dropbox\\Sent files\\kabale\\cardRanks\\profile_Five.jpg")
            # cards[4].profile = cv2.imread("C:\\Users\\Emil\\Dropbox\\Sent files\\kabale\\cardRanks\\profile_Six.jpg")

            # cards[0].profile = cv2.imread("C:\\Users\\Emil\\Dropbox\\Sent files\\kabale\\cardRanks\\profile_Seven.jpg")
            # cards[1].profile = cv2.imread("C:\\Users\\Emil\\Dropbox\\Sent files\\kabale\\cardRanks\\profile_Eight.jpg")
            # cards[2].profile = cv2.imread("C:\\Users\\Emil\\Dropbox\\Sent files\\kabale\\cardRanks\\profile_Nine.jpg")
            # cards[3].profile = cv2.imread("C:\\Users\\Emil\\Dropbox\\Sent files\\kabale\\cardRanks\\profile_Ten.jpg")
            # cards[4].profile = cv2.imread("C:\\Users\\Emil\\Dropbox\\Sent files\\kabale\\cardRanks\\profile_Jack.jpg")
            # cards[5].profile = cv2.imread("C:\\Users\\Emil\\Dropbox\\Sent files\\kabale\\cardRanks\\profile_Queen.jpg")
            # cards[6].profile = cv2.imread("C:\\Users\\Emil\\Dropbox\\Sent files\\kabale\\cardRanks\\profile_King.jpg")



            if succes:
                # for i in range(len(cards)-1):
                for i in range(7):

                    # cv2.imshow("card" + str(i), cards[i].profile)

                    # cardCornorProfile = cards[i].profile[600-135:600-15, 400-52:400-16]
                    # cv2.imshow("nice",cardCornorProfile)
                    # rotatedCornorProfile = cv2.rotate(cardCornorProfile, cv2.ROTATE_180)
                    # cv2.imshow("nice2",rotatedCornorProfile)
                    # cardCornorZoom = cv2.resize(cardCornorProfile, (0,0), fx=2, fy=2)
                    # cv2.imshow("tester", cardCornorZoom)
                    # cards[i].rankImg = cv2.cvtColor(cardCornorZoom[0:125, ], cv2.COLOR_BGR2GRAY)
                    # cards[i].suitImg = cv2.cvtColor(cardCornorZoom[150:,], cv2.COLOR_BGR2GRAY)

                    thisCard = cards[0]
                    name1, name2 = cardVal.setCardRankAndSuit(thisCard)


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


                    # for sname in sNameCnts:
                    #     print(sname )
                    #
                    #     string a = ""
                    #     for sname in sNameCnts2:
                    #         if yaydada
                    #             then
                    #             a = asdad



                    for img in thisCard.rankAndSuitContourImgs:
                        thisCard.rankImg = img
                        thisCard.suitImg = img
                        cardVal.matchCard2(thisCard)

                        print(thisCard.rank_match_difference_value)
                        print(thisCard.suit_match_difference_value)
                        print(thisCard.best_rank_match)
                        print(thisCard.best_suit_match)



                    # thisCard = cards[3]
                    # cardVal.setCardRankAndSuit(thisCard)
                    # # cv2.imshow("rank", thisCard.rankImg)
                    # # cv2.imshow("suit", thisCard.suitImg)
                    # cards[i] = cardVal.matchCard(thisCard, compareRanks, compareSuits)
                    # print(thisCard.best_rank_match)
                    # print(thisCard.best_suit_match)







