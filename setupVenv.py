import os
import subprocess

class myInstaller:

    def __init__(self):
        self.og = "&&"
        self.command = ""
        self.environmentName = "PrivateVenv"
        self.first = True

    def run(self):

        String = "This step of the process will upgrade your pip \n" \
                 "And install virtual environment if it isent installed\n" \
                 "Do you want to continue? [y/n]"

        # INSTALL PREREQUISITES
        if self.userCheck(String):
            print("python -m pip install --upgrade pip")
            a = subprocess.run("python -m pip install --upgrade pip", capture_output=True)
            if a.returncode == 0:
                print( a.stdout.decode().split(' ')[0] +" "+a.stdout.decode().split(' ')[1] +" "+ a.stdout.decode().split(' ')[2] )
            else :
                print(a.stderr.decode())

            print("\npip install virtualenv")
            a = subprocess.run("pip install virtualenv", capture_output=True)
            if a.returncode == 0:
                print(a.stdout.decode().split(' ')[0] + " " + a.stdout.decode().split(' ')[1] + " " + a.stdout.decode().split(' ')[2])
            else:
                print(a.stderr.decode())

        # CREATING ENVIRONMENT IN FOLDER
        path = str(os.path.dirname(os.path.realpath(__file__)))
        subprocess.run("python -m venv " + self.environmentName)
        print("\n environment : " + self.environmentName + ". Created as folder in directory")
        print(path)

        # Sending Commands to this Environment
        String = "This step of the process will Attmept to \n" \
                     "install all dependencies from requirements.txt \nin the new environment "\
                     "Do you want to continue? [y/n]"

        if self.userCheck(String):
            pass
            self.crossProcessCom_()


    def crossProcessCom_(self):
        dir_path = str(os.path.dirname(os.path.realpath(__file__)))

        environmentName = "HANS_VENV"
        path = str(dir_path + "\\" + environmentName + "\\Scripts\\")
        pipCommand = "pip install -r ..\\..\\requirements.txt"
        command = "cd " + path + " & activate.bat & " + pipCommand

        a = subprocess.run(command, capture_output=True, shell=True)
        if a.returncode != 0:
            print(a.stderr)
            return False

        print("\nDependencies List")
        textReturn = a.stdout.decode().split('\n')
        for r in textReturn:
            pStr = r.split(':')
            Str = pStr[0].split(' ')

            if Str[0] == "Collecting":
                operation = Str[0] + "\t"
                name = Str[1]
                print(str(operation) + str(name))

            elif Str[0] == "Requirement":
                operation ="Already installed \t"
                name = pStr[1].split(' ')# == "setuptools"
                print(str(operation) + str(name[1]))

            else:
                pass
                #print("\n"+r+"\n")

    def userCheck(self, Question):
        accept = False
        print("\n" + Question)
        while True:
            try:
                val = str(input(":"))
            except ValueError:
                print("sorry i did not understand that input '"+val+"' plaese try again" )
                continue

            if val == "y" or val =="Y":
                return True
            elif val =="n" or val =="N":
                return False
            else:
                print("sorry i did not understand that input '"+val+"' plaese try again" )

        return accept




install = myInstaller()
install.run()
