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

            compareSuits = cardVal.loadSuits(path + "/Card_Imgs/")
            # for i in range(len(compareSuits)):
            #     cv2.imshow(compareSuits[i].name, compareSuits[i].img)

            compareRanks = cardVal.loadRanks(path + "/Card_Imgs/")
            # for i in range(len(compareRanks)):
            #     cv2.imshow(compareRanks[i].name, compareRanks[i].img)



            isolator = Isolator(True,False,True,False)
            cards, succes = isolator.isolateCards(img)

            # img = cv2.imread("test.png")
            # cv2.imshow("test", img)
            # cards, succes = isolator.isolateCards(img)

            print(succes)

            if succes:
                for i in range(len(cards)-1):
                    cv2.imshow("card" + str(i), cards[i].profile)

                    # cardCornorProfile = cards[i].profile[600-135:600-15, 400-52:400-16]
                    # cv2.imshow("nice",cardCornorProfile)
                    # rotatedCornorProfile = cv2.rotate(cardCornorProfile, cv2.ROTATE_180)
                    # cv2.imshow("nice2",rotatedCornorProfile)
                    # cardCornorZoom = cv2.resize(cardCornorProfile, (0,0), fx=2, fy=2)
                    # cv2.imshow("tester", cardCornorZoom)
                    # cards[i].rankImg = cv2.cvtColor(cardCornorZoom[0:125, ], cv2.COLOR_BGR2GRAY)
                    # cards[i].suitImg = cv2.cvtColor(cardCornorZoom[150:,], cv2.COLOR_BGR2GRAY)




                    cardVal.setCardRankAndSuit(cards[1])
                    cv2.imshow("rank", cards[1].rankImg)
                    cv2.imshow("suit", cards[1].suitImg)
                    cards[i] = cardVal.matchCard(cards[1], compareRanks, compareSuits)
                    print(cards[1].best_rank_match)
                    print(cards[1].best_suit_match)



                    # tested sharpening the image -
                    # Used https://stackoverflow.com/questions/58231849/how-to-remove-blurriness-from-an-image-using-opencv-python-c
                    # sharpen_kernel = np.array([[-1, -1, -1], [-1, 9, -1], [-1, -1, -1]])
                    # sharpen = cv2.filter2D(cards[i].suitImg, -1, sharpen_kernel)
                    # cv2.imshow("sharpen", sharpen)





