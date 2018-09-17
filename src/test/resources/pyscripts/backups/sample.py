import subprocess
from subprocess import *
s,o=subprocess.check_output("dir")
print(o)
