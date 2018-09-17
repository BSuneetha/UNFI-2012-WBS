#!/usr/bin/python
# -*- coding: utf-8 -*-
import os,sys,subprocess,time,re,platform,shutil
#sys.path.insert(0,"C:\\Users\\ssunke\\Desktop\\python_scripts")
#from config import *
#shutil.copy("config.py","config_copy.py")
with open("D:/streamlined/HarmonyCoreiFusion/src/test/resources/pyscripts/config.txt","r") as fh:
 data_input=fh.readlines()

header = "Scenario Name, Test Name, Browser Name, Os Name, Test Status, Test Data Row, Link to Screen Shot, Error Log, Test Type\n"
filename = r'ping_output.csv'

Time = time.strftime("%H:%M:%S")
date = time.strftime("%d/%m/%Y")
def banner(msg):
    print("\n" + "#" * 60 + "\n")
    print("#" * 3 + " " * 2 + msg + " " * 4 + "#" * 3)
    print("\n" + "#" * 60 + "\n")
	
def write_to_file(string,mode="a"):
    try:
        fh = open(filename,mode)
        fh.write(string)
        fh.close()
        return 1
    except Exception as e:
        print("Not able to open file %s \nError: %filename"%e)
        return 0


try:
    banner("Opening user info file and writing data")
except Exception as e:
    print("banner function failed due to", e)
    sys.exit(1)
	
try:
    fh = open(filename,"w")
    fh.write(header)
    fh.close()
except Exception as e:
    print("Unable to open csv file in write mode\n Error: %s"%e)
    print("\n-----pls close the file before writing to it-----\n")
    sys.exit(1)

for number,ip in enumerate(data_input):
 try:
    #ip = sys.argv[1]
    print("\n=====Validating given %s======\n" % ip)
    k = re.search(
        "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$",
        ip)
    if k:
        print("Got valid ip")
    else:
        print("pls provide valid ip in config.py file")
        sys.exit(1)
 except Exception as e:
    print("exception accur due to", e, "exit from script")
    sys.exit(1)

 print("\n---testing whether ip %s reachable or not from existing machine--\n" % ip)
 if re.search(r'[\n]',ip):
     ip=ip.replace("\n","")
 try:
    s, out = subprocess.getstatusoutput("ping %s -n 2" % ip)
    if s == 0:
        ping_percen = re.search(r'Packets: Sent \= \d+, Received \= \d+, (Lost \= \d+ \((\d+)\% loss\))', out)
        if int(ping_percen.group(2)) == 0:
            print("\n###### ping output ########\n", out)
            print("\n----ping successful for %s----\n" % ip)
            Test_Status="Pass"
        else:
            print("\n###### ping output ########\n", out)
            print("\n----ping unsuccessful for %s----\n" % ip)
            msg=out
            Test_Status="Fail"
    else:
        print("\n###### ping output ########\n", out)
        ping_percen = re.search(r'Packets: Sent \= \d+, Received \= \d+, (Lost \= \d+ \((\d+)\% loss\))', out)
        #print("@@@@@@@@@@@@@@",ping_percen.group(1))
        print("\n----ping unsuccessful for %s----\n" % ip)
        msg=out
        Test_Status="Fail"
 except Exception as e:
    print("exception accur due to ", e, "exit from script")
    Test_Status="Fail"
    msg=e


 Scenario_Name="HealthCheck"
 Test_Name="IPTraceAndValidate"
 Browser_name=" Console"
 Os_Name=platform.system()[:3] + platform.release()
 Status=Test_Status
 #Test_Data="NA"
 number=number+1
 if re.search(r'^%s.*'%ip,ip):
     Test_Data="TD %d"%number
 else:
     Test_Data="NA"

 if Status=="Fail":
    #pic = pyautogui.screenshot()
    #pic.save('ping_%s.png'%ip)
    #Link_to_Screen=os.getcwd()+"\\ping_%s.png"%ip
    #Error_Log=trace.group(0)
    if re.search(r'[,\n]',msg):
        msg=msg.replace(","," ").replace("\n"," ")
        Error_Log=msg
    else:
	    Error_Log=msg
 else:
    Error_Log="NA"
 Link_to_Screen = "NA"

 Test_Type="Sanity"
#print("$$$$$$$$$$","".join(Test_Data.split(" ")))
 string=Scenario_Name + "," + Test_Name + "," + Browser_name + "," + Os_Name + "," + Status + "," + Test_Data + "," + Link_to_Screen + "," + Error_Log + "," + Test_Type + "," + "\n"
 try:
    write_to_file(string)
 except Exception as e:
    print("Fail due to",e)
    sys.exit(1)


	
"""with open("config_copy.py","r") as fh:
    data=fh.readlines()
    for number,line in enumerate(data):
        number=number+1
        td=re.search(r'^%s.*'%ip,line)
        Test_Data="TD %d"%number
        break"""
        #fh.write("%d %s"%(number+1,line))
#with open('config.py', 'r') as fh:
#    data1=fh.read()
#td=re.search(r'^(\d+)\s+ip.*',data1)

#pic = pyautogui.screenshot()
#pic.save('trace.png')

	

"""Scenario_Name="HealthCheck"
Test_Name="IPPingAndValidate"
Browser_name=" Console"
Os_Name=platform.platform()
Status=Test_Status
Test_Data=ping_percen.group(1)
Link_to_Screen="NA"
if Status=="Fail":
    Error_Log=ping_percen.group(1)
else:
    Error_Log="NA"
Test_Type="Sanity"
#print("$$$$$$$$$$","".join(Test_Data.split(" ")))
string=Scenario_Name + "," + Test_Name + "," + Browser_name + "," + Os_Name + "," + Status + "," + Test_Data + "," + Link_to_Screen + "," + Error_Log + "," + Test_Type + "," + "\n"
try:
    write_to_file(string)
except Exception as e:
    print("Fail due to",e)
    sys.exit(1)
try:

    out=subprocess.check_output("ping %s"%ip,timeout=5)
    out=str(out)
except subprocess.TimeoutExpired:

    print('process ran too long')

ping_percen=re.search(r'Packets: Sent \= \d+, Received \= \d+, Lost \= \d+ \((\d+)\% loss\)',out)

if int(ping_percen.group(1))==0:
    print("\n###### ping output ########\n",out)
    print("\n----ping successful for %s----\n"%ip)

else:
    print("\n###### ping output ########\n",out)
    print("\n----ping unsuccessful for %s----\n"%ip)"""