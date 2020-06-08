import os

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
        while True:
            # Retrieving an image.
            img = rec.getFrame()

            #cv2.imshow("img", img)
            if cv2.waitKey(1) & 0xFF == ord('q'):
                rec.stop()
                break



            # img2 = cv2.imread()

            # for testing comparison images
            path = os.path.dirname(os.path.abspath(__file__))

            # compareSuits = cardVal.loadSuits(path + "/Card_Imgs/")
            # for i in range(len(compareSuits)):
            #     cv2.imshow(compareSuits[i].name, compareSuits[i].img)

            # compareRanks = cardVal.loadRanks(path + "/Card_Imgs/")
            # for i in range(len(compareRanks)):
            #     cv2.imshow(compareRanks[i].name, compareRanks[i].img)



            isolator = Isolator(False,False,False,False)
            cards, succes = isolator.isolateCards(img)

            # img = cv2.imread("test.png")
            # cv2.imshow("test", img)
            # cards, succes = isolator.isolateCards(img)


            cards[0].profile = cv2.imread("cardProfile_Ace_Clubs.jpg")
            cards[1].profile = cv2.imread("cardProfile_Ace_Diamonds.jpg")
            cards[2].profile = cv2.imread("cardProfile_Ace_Hearts.jpg")
            cards[3].profile = cv2.imread("cardProfile_Ace_Spades.jpg")
            cards[0].profile = cv2.resize(cards[3].profile, (225,300))
            cards[1].profile = cv2.resize(cards[3].profile, (225, 300))
            cards[2].profile = cv2.resize(cards[3].profile, (225, 300))
            cards[3].profile = cv2.resize(cards[3].profile, (225, 300))
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

                    thisCard = cards[3]
                    cardVal.setCardRankAndSuit(thisCard)
                    for img in thisCard.rankAndSuitContourImgs:
                        thisCard.rankImg = img
                        thisCard.suitImg = img
                        cardVal.matchCard(thisCard, compareRanks, compareSuits)

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







