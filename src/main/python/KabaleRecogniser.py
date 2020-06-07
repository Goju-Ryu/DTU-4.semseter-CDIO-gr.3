import cv2
from Isolator.Isolator import Isolator
from VideoInput.Video import SVideo

rec = SVideo()
#Author : Hans
class KabaleRecogniser:
    # Author : Hans
    def run(self,Settings):
        while True:
            # Retrieving an image.
            img = rec.getFrame()

            #cv2.imshow("img", img)
            if cv2.waitKey(1) & 0xFF == ord('q'):
                rec.stop()
                break

            isolator = Isolator(False,False,True,False)
            cards = isolator.isolateCards(img, Settings)

