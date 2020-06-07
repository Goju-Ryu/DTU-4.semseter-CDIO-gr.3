from KabaleRecogniser import KabaleRecogniser
from GUI.SettingsGUI import SettingsGUI

s = SettingsGUI()
k = KabaleRecogniser()
Settings = s.run()
k.run(Settings)

"""FRAME_NAME = "main frame"
#vid = Video(True)
proc = ImageProcessor()

while True:
    img = cv2.imread('image2.png')
    #img2 = vid.getFrame()
    #if cv2.waitKey(20) & 0xff == ord('q'):
    #    vid.stop()
    #    break
    #cv2.imshow("frame", img2)

    # start the recording.
    #cv2.imshow("1", img)

    # get thresh image.
    img_thresh = proc.getThreshImage(img)

    # find cards two variables, hasCards = boolean, cards list of cards found in the image.
    cards, succes = proc.findCardsCandidates(img_thresh, img)
    if succes:
        for i in range(len(cards)):
           print("hej")
           # cv2.imshow("card" + str(i) , cards[i].cardProfileImage)



    #
    time.sleep(1)
    if cv2.waitKey(20) & 0xff == ord('q'):
        break"""

