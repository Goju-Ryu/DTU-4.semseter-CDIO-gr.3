### HER SKAL VI LAVE REEL BILLED GENKENDELSE
import cv2
import numpy as np

#TODO lav genkendelse af rank og suit NEDE i venstre hjørne af kortbillede:
# - sammenlign antal matchende pixels med billeder af rank og suit
# - sæt kortets best_rank_match og best_suit_match til de bedst matchende billeder


class CardValidator:
    """For matching cards with suits and ranks"""

    def loadSuits(self, filepath):
        compareSuits = []

        for Suit in ["Hearts", "Spades", "Clubs", "Diamonds"]:
            compareSuit = CompareSuit()
            filename = Suit + "2.jpg"
            compareSuit.name = Suit
            compareSuit.img = cv2.imread(filepath + filename, cv2.IMREAD_GRAYSCALE) #pic is 100*70

            # compareSuit.img = cv2.imread("cardProfile_Ace_" + filename, cv2.IMREAD_GRAYSCALE) #this is grayscaled profile

            cv2.imshow("cardProfile_Ace_" + filename, compareSuit.img)


            compareSuits.append(compareSuit)

        return compareSuits

    def loadRanks(self, filepath):
        compareRanks = []

        for Rank in ["Ace", "Two", "Three", "Four", "Five", "Six", "Seven",
                     "Eight", "Nine", "Ten", "Jack", "Queen", "King"]:
            compareRank = CompareRank()
            filename = Rank + "2.jpg"
            compareRank.name = Rank
            compareRank.img = cv2.imread(filepath+filename, cv2.IMREAD_GRAYSCALE)   #pic is 125*70

            compareRanks.append(compareRank)

        return compareRanks

    def matchCard(self, card, compareRanks, compareSuits):
        best_rank_match_name = "[Card rank name here]"
        best_suit_match_name = "[Card suit name here]"

        # initial difference values. Less is better, as can be seen in comparison in following code
        best_rank_match_difference_value = 10000
        best_suit_match_difference_value = 10000

        # checks if images for rank and suit are found
        if (len(card.rankImg) != 0 and len(card.suitImg) != 0):

            # iterates the ranks and suits and saves the value for the best matched rank and suit in returnvariables

            invertedRankImg = cv2.bitwise_not(card.rankImg)
            invertedSuitImg = cv2.bitwise_not(card.suitImg)
            for Rank in compareRanks:
                rankDiff = int(np.sum(cv2.absdiff(invertedRankImg, Rank.img)) / 255)

                if (rankDiff < best_rank_match_difference_value):
                    best_rank_match_difference_value = rankDiff
                    best_rank_match_name = Rank.name

                # TODO kan finde forskellen ved at lægge billederne
                #  ovenpå hinanden, og tælle hvide pixels, hvis det andet ikke virker
                # #number of white pixels
                # n_white_pix = np.sum(Rank.img == 255)
                # print('Number of white pixels:', n_white_pix)


                # print(Rank.name)
                # print(rankDiff)



            for Suit in compareSuits:
                suitDiff = int(np.sum(cv2.absdiff(invertedSuitImg, Suit.img)) / 255)

                # suitDiff = int(np.sum(cv2.absdiff(card.profile, Suit.img) / 255))

                if (suitDiff < best_suit_match_difference_value):
                    best_suit_match_difference_value = suitDiff
                    best_suit_match_name = Suit.name
                # print(Suit.name)
                # print(suitDiff)
            # print(best_rank_match_name)
            # print(best_suit_match_name)
        # TODO some error handling can be added here if needed

        # TODO figure out if just sending the return values to be set to a card afterwards, or if the card is instead set inside this function
        # can return a card or the values beneath ... tbd ...
        # return best_rank_match_name, best_suit_match_name, best_rank_match_difference_value, best_suit_match_difference_value

        card.best_rank_match = best_rank_match_name
        card.best_suit_match = best_suit_match_name
        card.rank_match_difference_value = best_rank_match_difference_value
        card.suit_match_difference_value = best_suit_match_difference_value

        return card

    def setCardRankAndSuit(self, card):
        # rotate to check buttom right cornor instead
        # to avoid problems if cards have faulty contours due to rows with stacked cards
        # rotatedProfile = cv2.rotate(card.profile, cv2.ROTATE_180)



        # Isolated cornor profile
        cardCornorProfile = card.profile[0:140, 0:70]
        cv2.imshow("cardCornorProfile", cardCornorProfile)

        #TODO check for contours on cornorprofile
        img = cv2.bitwise_not(cardCornorProfile)
        imggray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
        ret, thresh = cv2.threshold(imggray, 127, 255, 0)
        cv2.imshow("imggray", imggray)

        contours, hierachy = cv2.findContours(thresh, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_NONE)


        cv2.imshow("cardCornorThresh", thresh)
        cv2.drawContours(thresh, contours, -1, (100, 30, 255), 3)
        cv2.imshow("cardCornorThreshContour", thresh)

        #TODO add boundingRectangle around contour
        rectangle = cv2.boundingRect(thresh)
        # cv2.imshow("rectangle",rectangle)
        #TODO compare boundingRectangle with Card_Imgs


        # cardCornorProfile = card.profile[15:135, 8:48]
        cardCornorZoom = cv2.resize(cardCornorProfile, (0, 0), fx=2, fy=2)
        cv2.imshow("tester", cardCornorZoom)
        card.rankImg = cv2.cvtColor(cardCornorZoom[30:155, 10:80], cv2.COLOR_BGR2GRAY)
        card.suitImg = cv2.cvtColor(cardCornorZoom[180:280, 10:80], cv2.COLOR_BGR2GRAY)
        # cv2.imwrite("Card_Imgs2/King2.jpg", card.rankImg)
        return card

    #__________________________
    def SortPoints(self, p1, p2 , p3 , p4, image):

        points = [p4,p3,p2,p1]
        sortedList = sorted(points, key=lambda x: x[1], reverse=False)
        if sortedList[0][0] > sortedList[1][0]:
            temp = sortedList[0]
            sortedList[0] = sortedList[1]
            sortedList[1] = temp

        if sortedList[2][0] > sortedList[3][0]:
            temp = sortedList[2]
            sortedList[2] = sortedList[3]
            sortedList[3] = temp

        for point in sortedList:
            image = cv2.circle(image, sortedList[0], 2, (255, 0, ), 2)
            image = cv2.circle(image, sortedList[1], 2, (0, 255, 255), 2)
            image = cv2.circle(image, sortedList[2], 2, (255, 0, 255), 2)
            image = cv2.circle(image, sortedList[3], 2, (255, 255, 255), 2)
        return sortedList[3], sortedList[2], sortedList[0], sortedList[1]
    #__________________________

class CompareRank:
    def __init__(self):
        self.img = []
        self.name = "[Card rank name here]"


class CompareSuit:
    def __init__(self):
        self.img = []
        self.name = "[Card suit name here]"





    #TODO Isolate Rank and Suit (probably in ImageProcessor.py) from card image, and resize it to match the compareImages
    #maybe some extra preprocessing making the outline thicker is needed, depending on the card deck's font


    #TODO Compare the cards Ranks and suit with the loaded compareImages

    # def matchCard(self, card, compareRanks, compareSuits):
        # best_rank_match_name = "[Card rank name here]"
        # best_suit_match_name = "[Card suit name here]"
        #
        # #initial difference values. Less is better, as can be seen in comparison in following code
        # best_rank_match_difference_value = 10000
        # best_suit_match_difference_value = 10000
        #
        # # checks if images for rank and suit are found
        # if(len(card.rankImg) != 0 and len(card.suitImg) != 0):
        #
        #     # iterates the ranks and suits and saves the value for the best matched rank and suit in returnvariables
        #
        #     for Rank in compareRanks:
        #         rankDiff = int(np.sum(cv2.absdiff(card.rankImg, Rank.img))/255)
        #
        #         if(rankDiff < best_rank_match_difference_value):
        #             best_rank_match_difference_value = rankDiff
        #             best_rank_match_name = Rank.name
        #
        #     for Suit in compareSuits:
        #         suitDiff = int(np.sum(cv2.absdiff(card.suitImg, Suit.img))/255)
        #
        #         if(suitDiff < best_suit_match_difference_value):
        #             best_suit_match_difference_value = suitDiff
        #             best_suit_match_name = Suit.name
        #
        # #TODO some error handling can be added here if needed
        #
        # #TODO figure out if just sending the return values to be set to a card afterwards, or if the card is instead set inside this function
        # #can return a card or the values beneath ... tbd ...
        # # return best_rank_match_name, best_suit_match_name, best_rank_match_difference_value, best_suit_match_difference_value
        #
        # card.best_rank_match = best_rank_match_name
        # card.best_suit_match = best_suit_match_name
        # card.rank_match_difference_value = best_rank_match_difference_value
        # card.suit_match_difference_value = best_suit_match_difference_value
        #
        # return card




