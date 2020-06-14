
class SettingsObject():
    # Author : Hans
    def __init__(self):
        self.Hmin = 50
        self.Hmax = 90
        self.Smin = 60
        self.Smax = 255
        self.Vmin = 60
        self.Vmax = 255

    # Author : Hans
    def setHue(self, Hmin, Hmax):
        self.Hmin = Hmin
        self.Hmax = Hmax

    # Author : Hans
    def setSat(self, Smin, Smax):
        self.Smin = Smin
        self.Smax = Smax

    # Author : Hans
    def setVal(self, Vmin, Vmax):
        self.Vmin = Vmin
        self.Vmax = Vmax

    # Author : Hans
    def getHue(self):
        return self.Hmin, self.Hmax

    # Author : Hans
    def getSat(self):
        return self.Smin, self.Smax

    # Author : Hans
    def getVal(self):
        return self.Vmin, self.Vmax