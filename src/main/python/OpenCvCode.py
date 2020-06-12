from GUI.SettingsGUI import SettingsGUI
from KabaleRecogniser import KabaleRecogniser
from AbstractUi import AbstractUI


class OpenCvCode(AbstractUI):
    retString = ""

    def __init__(self):
        self.retString = ""
        pass
    '''
    def run(self):
        s = SettingsGUI()
        k = KabaleRecogniser()
        Settings = s.run()
        print(k.run(Settings))
        self.retString = "hey"
        return self.retString
    '''


    def run(self):
        inp ="{\n\"h14\":\"pi\",\n" \
             "\"\":\"su1\",\n" \
             "\"\":\"su2\",\n" \
             "\"\":\"su3\",\n" \
             "\"\":\"su4\",\n" \
             "\"h9\":\"co1\",\n" \
             "\"h2\":\"co2\",\n" \
             "\"h3\":\"co3\",\n" \
             "\"h4\":\"co4\",\n" \
             "\"h5\":\"co5\",\n" \
             "\"h6\":\"co5\",\n" \
             "\"h7\":\"co6\",\n" \
             "\"h8\":\"co7\",\n" \
             "}"


        return inp

        """from KabaleRecogniser import KabaleRecogniser
        from GUI.SettingsGUI import SettingsGUI
        
        #Author : Hans
        class openCVCode:
            
            # Author : Hans
            def __init__(self):
                pass
            
            # Author : Hans
            def run(self):
                s = SettingsGUI()
                k = KabaleRecogniser()
                Settings = s.run()
                return k.run(Settings)"""