import os
import subprocess

class textColor:
    HEADER = '\033[95m'
    OKBLUE = '\033[94m'
    OKGREEN = '\033[92m'
    WARNING = '\033[93m'
    FAIL = '\033[91m'
    ENDC = '\033[0m'
    BOLD = '\033[1m'
    UNDERLINE = '\033[4m'

class myInstaller:

    def __init__(self):
        self.og = "&&"
        self.command = ""
        self.environmentName = "HANS_VENV"
        self.first = True

    def run(self):
        noErr = True
        while noErr:
            print(textColor.OKGREEN + "..UPGRADING PIP.. \n "+textColor.ENDC +"installing virtual environment \n ")
            subprocess.run("python -m pip install --upgrade pip")
            subprocess.run("pip install virtualenv")

            print(textColor.OKGREEN +"..Creating Environment..")
            subprocess.run("python -m venv " + self.environmentName)
            print(textColor.ENDC +"environment : " + self.environmentName)


            a = subprocess.run(".\\" + self.environmentName + "\\Scripts\\activate.bat",capture_output=True, text=True)


            if a.returncode == 0:
                print(textColor.OKGREEN + "Installing Dependencies on Local Environment.. \n"+textColor.ENDC +"in : "+ self.environmentName +"\n - - - -  ")
                a = subprocess.run("pip install -r requirements.txt", capture_output=True, text= True)
                if a.returncode == 0:
                    installs = a.stdout.split('\n')
                    for req in installs:
                        reqStr = req.split(" in ")
                        print( textColor.OKBLUE + reqStr[0])
                else:
                    installs = a.stdout.split('\n')
                    for req in installs:
                        reqStr = req.split(" in ")
                        print(textColor.WARNING + reqStr[0])

            print("done")
            break


install = myInstaller()
install.run()
