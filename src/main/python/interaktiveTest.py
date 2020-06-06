from cv2 import cv2

from Video import SVideo

vid = SVideo()


while True:
    img = vid.getFrame()
    if cv2.waitKey(1) & 0xFF == ord('q'):
        vid.release()
        break


    cv2.imshow("image", img)
    bboxes = []
    bbox = cv2.selectROI('MultiTracker', img)
    bboxes.append(bbox)
    print("Press q to quit selecting boxes and start tracking")
    print("Press any other key to select next object")
    k = cv2.waitKey(0) & 0xFF
    if (k == 113):  # q is pressed
        break

    # Specify the tracker type
    trackerType = "CSRT"

    # Create MultiTracker object
    multiTracker = cv2.MultiTracker_create()

    # Initialize MultiTracker
    for bbox in bboxes:
        multiTracker.add(createTrackerByName(trackerType), frame, bbox)
