#!/usr/bin/python
# -*- coding: utf-8 -*-
import os,sys,subprocess,time,re,platform,argparse
from com.web.tests.webtestcases.ifusion import ResultsHolder
#import Beach
#import pyautogui,shutil
#sys.path.insert(0,"C:\\Users\\ssunke\\Desktop\\python_scripts")
#from config import *
#shutil.copy("config.py","config_copy.py")
res = ResultsHolder()
def banner(msg):
    print("\n" + "#" * 60 + "\n")
    print("#" * 3 + " " * 2 + msg + " " * 4 + "#" * 3)
    print("\n" + "#" * 60 + "\n")

banner("Adding command line arguements to parse")
#parser = argparse.ArgumentParser()
#parser.add_argument('--input_file')

#args = parser.parse_args()
input_file= "D:/streamlined/HarmonyCoreiFusion/src/test/resources/pyscripts/config.txt" #args.input_file

try:
    with open(input_file,"r") as fh:
        data_input=fh.readlines()
except Exception as e:
    print("\n----Missing Mandatory argument, pls provide input file as below with path or without path\(file should exist in same location\)---\n\n----python ping.py --input_file config.txt----\n\n")
    #print("exception accur due to ",e)
    sys.exit(1)
header = "Scenario Name, Test Name, Browser Name, Os Name, Test Status, Test Data Row, Link to Screen Shot, Error Log, Test Type\n"
#filename = r'ping_output.csv'

Time = time.strftime("%H:%M:%S")
date = time.strftime("%d/%m/%Y")

	
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
	
# try:
#     #fh = open(filename,"w")
#     #fh.write(header)
#     #fh.close()
# except Exception as e:
#     print("Unable to open csv file in write mode\n Error: %s"%e)
#     print("\n----pls close the file before writing to it-----\n")
#     sys.exit(1)
testcase_list=[]
for number,ip in enumerate(data_input):
 if re.search(r'[\n]',ip):
     ip=ip.replace("\n","")
 try:
    #ip = sys.argv[1]
    print("\n----Validating given ip %s------\n"%ip)
    k = re.search(
        "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$",
        ip)
    if k:
        print("----Got valid ip----")
    else:
        print("----pls provide valid ip in %s file-----"%input_file)
        #sys.exit(1)
 except Exception as e:
    print("exception accur due to", e, "exit from script")
    #sys.exit(1)

 print("\n----testing whether ip %s reachable or not from existing machine--\n" % ip)
 #s, out = subprocess.getstatusoutput("ping %s -n 2" % ip)
 #status = subprocess.check_output("ping %s -n 2" % ip)
 try:
    #s, out = subprocess.getstatusoutput("ping %s -n 2" % ip)
    out = subprocess.check_output("ping %s -n 2" % ip)
    # if isinstance(out,bytes):
    # 	print("Entering isinstance before str call")
    # 	print(type(out))
    #     out = str(out, 'utf-8')

    # if type(out) is str:
    # 	print("This does not print")
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
    # else:
    #     print("\n###### ping output, No return type string from subprocess.check_output########\n", out)
    #     print("\n----ping unsuccessful for %s----\n" % ip)
    #     msg=out
    #     Test_Status="Fail"
 except Exception as e:
    print("exception occur due to ", e, "continue to script")
    Test_Status="Fail"
    msg=e


 Scenario_Name="HealthCheck"
 Test_Name="IPTraceAndValidate"
 Browser_name="Console"
 Os_Name=platform.system()[:3] + platform.release()
 Status=Test_Status
 #Test_Data="NA"
 number=number+1
 if re.search(r'^%s.*'%ip,ip):
     Test_Data="TD%d"%number
 else:
     Test_Data="NA"

 if Status=="Fail":
    #pic = pyautogui.screenshot()
    #pic.save('ping_%s.png'%ip)
    #Link_to_Screen=os.getcwd()+"\\ping_%s.png"%ip
    #Error_Log=trace.group(0)
    if type(msg) is subprocess.CalledProcessError:
        msg=str(msg)
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
 split_string=string.split(",")
 testcase_list.append(split_string)
 res.insert(string)
 # try:
 #    write_to_file(string)
 # except Exception as e:
 #    print("Fail due to",e)
    #sys.exit(1)

#beach = Beach("Scenario_Name","Test_Name","Browser_name","Os_Name","Status","Test_Data","Link_to_Screen",Error_Log","Test_Type")
#beach = Beach(testcase_list)
#beach.getName()
