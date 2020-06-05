from BoardRuler import BoardRuler
from Card import Card
from CardAnalyser import CardAnalyser
from ImageProcessor import ImageProcessor
from Video import SVideo
import time
import cv2
import numpy as np

import os
from CardValidator import CardValidator

#komment

#rec = SVideo()
cardAnal = CardAnalyser()
ruler = BoardRuler()

cardVal = CardValidator()

class KabaleRecogniser:
    def run(self):
        rec = cv2.VideoCapture('video_02.mp4',0)
        while True:
            # to be removed
            empt, frame = rec.read()
            img = frame
            if not empt:
                rec.release()
                rec = cv2.VideoCapture('video_01.mp4', 0)
                empt, frame = rec.read()
                img = frame
            # to be re made
            #img = rec.getFrame()




            compareSuits = cardVal.loadSuits(path + "/Card_Imgs/")
            for i in range(len(compareSuits)):
                cv2.imshow(compareSuits[i].name, compareSuits[i].img)

            compareRanks = cardVal.loadRanks(path + "/Card_Imgs/")
            for i in range(len(compareRanks)):
                cv2.imshow(compareRanks[i].name, compareRanks[i].img)





            # # find cards two variables, hasCards = boolean, cards list of cards found in the image.
            # cards, succes = cardAnal.findCards(img)
            # if succes:
            #     for i in range(len(cards)):
            #         #cv2.imshow("card" + str(i), cards[i].profile)
            #         pass
            #
            #         # for testing comparison images
            #         path = os.path.dirname(os.path.abspath(__file__))






                    # find cards two variables, hasCards = boolean, cards list of cards found in the image.
                    cards, succes = cardAnal.findCards(img)
                    if succes:
                        for i in range(len(cards)):
                            cv2.imshow("card" + str(1), cards[1].profile)

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

                            # # tested sharpening the image -
                            # # Used https://stackoverflow.com/questions/58231849/how-to-remove-blurriness-from-an-image-using-opencv-python-c
                            # sharpen_kernel = np.array([[-1, -1, -1], [-1, 9, -1], [-1, -1, -1]])
                            # sharpen = cv2.filter2D(cards[i].suitImg, -1, sharpen_kernel)
                            # cv2.imshow("sharpen", sharpen)





            if cv2.waitKey(20) & 0xff == ord('q'):
                break
            time.sleep(0)

