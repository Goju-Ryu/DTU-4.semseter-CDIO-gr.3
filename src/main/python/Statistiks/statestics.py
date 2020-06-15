from operator import itemgetter


class statistics:

    def __init__(self):
        # statistics is a 2d arrray. where the integeres in these spaces are the "counters" where we count votes.
        # it needs to persist across the loop, so is instantiated outside it.
        stat = []
        for q in range(14):
            stat.append([["Hearts", 0], ["Spades", 0], ["Clubs", 0], ["Diamonds", 0], ["1", 0], ["2", 0],
                               ["3", 0], ["4", 0], ["5", 0], ["6", 0], ["7", 0],
                               ["8", 0], ["9", 0], ["10", 0], ["11", 0], ["12", 0], ["13", 0]])
        self.stat = stat

    def statGetCardValue(self, i ):
        stat = self.stat[i]
        Ranks = stat[4:]
        Suits = stat[0:4]
        suit = max(Suits, key=lambda x: x[1])
        rank = max(Ranks, key=lambda x: x[1])

        return rank[0],suit[0]


    def statisticInput(self, a, b,i):
        # for counting occurences of card symbols
        stat = self.stat[i]
        for dataP in stat:
            if a == dataP[0]:
                dataP[1] += 1
            if b == dataP[0]:
                dataP[1] += 1

    def evalListOfCards(self, cards):
        # here we gather the conclusion of the statistics. in a "card" array.
        k = 0
        for stat in self.stat:
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
        return cards