
class SettingsObject():

    def __init__(self):
        self.Hmin = 0
        self.Hmax = 0
        self.Smin = 0
        self.Smax = 0
        self.Vmin = 0
        self.Vmax = 0

    def setHue(self, Hmin, Hmax):
        self.Hmin = Hmin
        self.Hmax = Hmax

    def setSat(self, Smin, Smax):
        self.Smin = Smin
        self.Smax = Smax

    def setVal(self, Vmin, Vmax):
        self.Vmin = Vmin
        self.Vmax = Vmax

    def getHue(self):
        return self.Hmin, self.Hmax

    def getSat(self):
        return self.Smin, self.Smax

    def getVak(self):
        return self.Vmin, self.Vmax