import cv2


from Isolator.Isolator import Isolator
from VideoInput.Video import SVideo

rec = SVideo(True)


class KabaleRecogniser:

    def run(self):
        while True:
            # Retrieving an image.
            img = rec.getFrame()

            #cv2.imshow("img", img)
            if cv2.waitKey(1) & 0xFF == ord('q'):
                rec.release()
                break

            isolator = Isolator(False,False,True,True)
            cards = isolator.isolateCards(img)