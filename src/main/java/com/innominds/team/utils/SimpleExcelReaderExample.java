package com.innominds.team.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SimpleExcelReaderExample {

	public static void convertResultReportToHtml(String infilepath, String finalReportName, String startTime,
			String endTime, float passper, float failper) throws IOException {
		try {
			// define a HTML String Builder
			StringBuilder htmlStringBuilder = new StringBuilder();
			// append html header and title
			htmlStringBuilder.append("<!DOCTYPE html>");
			htmlStringBuilder.append("<html>\r\n");
			htmlStringBuilder.append("<head>\r\n");
			htmlStringBuilder.append("<style type='text/css'>\r\n");
			htmlStringBuilder.append("h1 { text-shadow: 2px 2px #808080; }\r\n");
			htmlStringBuilder.append("body\r\n");
			htmlStringBuilder.append("{\r\n");
			htmlStringBuilder.append("background-color: #FFFFFF;\r\n");
			htmlStringBuilder.append("font-family: Verdana, Geneva, sans-serif;\r\n");
			htmlStringBuilder.append("text-align: center;\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("small\r\n");
			htmlStringBuilder.append("{\r\n");
			htmlStringBuilder.append("font-size: 0.7em;\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("table\r\n");
			htmlStringBuilder.append("{\r\n");
			htmlStringBuilder.append("box-shadow: 9px 9px 10px 4px #BDBDBD;\r\n");
			htmlStringBuilder.append("border: 0px solid #4D7C7B;\r\n");
			htmlStringBuilder.append("border-collapse: collapse;\r\n");
			htmlStringBuilder.append("border-spacing: 0px;\r\n");
			htmlStringBuilder.append("width: 1300px;\r\n");
			htmlStringBuilder.append("margin-left: auto;\r\n");
			htmlStringBuilder.append("margin-right: auto;\r\n");
			htmlStringBuilder.append("table-layout:fixed;\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("tr.heading\r\n");
			htmlStringBuilder.append("{\r\n");
			htmlStringBuilder.append("background-color: #286B6A;\r\n");
			htmlStringBuilder.append("color: #FFFFFF;\r\n");
			htmlStringBuilder.append("font-size: 1.5em;\r\n");
			htmlStringBuilder.append("font-weight: bold;\r\n");
			htmlStringBuilder.append("background:-o-linear-gradient(bottom, #286B6A 5%, #000000 100%);\r\n");
			htmlStringBuilder.append(
					"background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #286B6A), color-stop(1, #000000) );\r\n");
			htmlStringBuilder.append(
					"background:-moz-linear-gradient( center top, #286B6A 5%, #000000 100% );filter:progid:DXImageTransform.Microsoft.gradient(startColorstr=#286B6A, endColorstr=#000000);\r\n");
			htmlStringBuilder.append("background: -o-linear-gradient(top,#286B6A,000000);\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("tr.subheading\r\n");
			htmlStringBuilder.append("{\r\n");
			htmlStringBuilder.append("background-color: #6A90B6;\r\n");
			htmlStringBuilder.append("color: #000000;\r\n");
			htmlStringBuilder.append("font-weight: bold;\r\n");
			htmlStringBuilder.append("font-size: 0.7em;\r\n");
			htmlStringBuilder.append("text-align: justify;\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("tr.section\r\n");
			htmlStringBuilder.append("{\r\n");
			htmlStringBuilder.append("background-color: #A4A4A4;\r\n");
			htmlStringBuilder.append("color: #333300;\r\n");
			htmlStringBuilder.append("cursor: pointer;\r\n");
			htmlStringBuilder.append("font-weight: bold;\r\n");
			htmlStringBuilder.append("font-size: 0.8em;\r\n");
			htmlStringBuilder.append("text-align: justify;\r\n");
			htmlStringBuilder.append("background:-o-linear-gradient(bottom, #56aaff 5%, #e5e5e5 100%);\r\n");
			htmlStringBuilder.append(
					"background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #56aaff), color-stop(1, #e5e5e5) );\r\n");
			htmlStringBuilder.append(
					"background:-moz-linear-gradient( center top, #56aaff 5%, #e5e5e5 100% );filter:progid:DXImageTransform.Microsoft.gradient(startColorstr=#56aaff, endColorstr=#e5e5e5);\r\n");
			htmlStringBuilder.append("background: -o-linear-gradient(top,#56aaff,e5e5e5);\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("tr.subsection\r\n");
			htmlStringBuilder.append("{\r\n");
			htmlStringBuilder.append("cursor: pointer;\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("tr.content\r\n");
			htmlStringBuilder.append("{\r\n");
			htmlStringBuilder.append("background-color: #FFFFFF;\r\n");
			htmlStringBuilder.append("color: #000000;\r\n");
			htmlStringBuilder.append("font-size: 0.5em;\r\n");
			htmlStringBuilder.append("display: table-row;\r\n");
			htmlStringBuilder.append("white-space:nowrap;\r\n");
			htmlStringBuilder.append("width: 100%;\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("tr.content2\r\n");
			htmlStringBuilder.append("{\r\n");
			htmlStringBuilder.append("background-color:#;E1E1E1border: 1px solid #4D7C7B;\r\n");
			htmlStringBuilder.append("color: #000000;\r\n");
			htmlStringBuilder.append("font-size: 0.7em;\r\n");
			htmlStringBuilder.append("display: table-row;\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("th\r\n");
			htmlStringBuilder.append("{\r\n");
			htmlStringBuilder.append("padding: 10px;\r\n");
			htmlStringBuilder.append("border: 1px solid #4D7C7B;\r\n");
			htmlStringBuilder.append("text-align: inherit;\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("th.main\r\n");
			htmlStringBuilder.append("{\r\n");
			htmlStringBuilder.append("padding: 10px;\r\n");
			htmlStringBuilder.append("border: 1px solid #4D7C7B;\r\n");
			htmlStringBuilder.append("text-align: inherit;\r\n");
			htmlStringBuilder.append("font: bold 15px Arial,Helvetica,Sans-serif;\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("td\r\n");
			htmlStringBuilder.append("{\r\n");
			htmlStringBuilder.append("padding: 2px;\r\n");
			htmlStringBuilder.append("font: 13px Arial,Helvetica,Sans-serif;\r\n");
			htmlStringBuilder.append("border: 1px solid #4D7C7B;\r\n");
			htmlStringBuilder.append("text-align: inherit;\r\n");
			htmlStringBuilder.append("white-space:nowrap;\r\n");
			htmlStringBuilder.append("width: 100%;\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("td.justified\r\n");
			htmlStringBuilder.append("{\r\n");
			htmlStringBuilder.append("text-align: justify;\r\n");
			htmlStringBuilder.append("word-wrap: break-word;\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("td.pass\r\n");
			htmlStringBuilder.append("{\r\n");
			htmlStringBuilder.append("font-weight: bold;\r\n");
			htmlStringBuilder.append("color: green;\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("td.skipped\r\n");
			htmlStringBuilder.append("{\r\n");
			htmlStringBuilder.append("font-weight: bold;\r\n");
			htmlStringBuilder.append("bold; color: red;\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("td.fail\r\n");
			htmlStringBuilder.append("{\r\n");
			htmlStringBuilder.append("font-weight: bold;\r\n");
			htmlStringBuilder.append("bold; color: red;\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("td.done, td.screenshot\r\n");
			htmlStringBuilder.append("{\r\n");
			htmlStringBuilder.append("font-weight:\r\n");
			htmlStringBuilder.append("bold; color: black;\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("td.debug\r\n");
			htmlStringBuilder.append("{\r\n");
			htmlStringBuilder.append("font-weight: bold;\r\n");
			htmlStringBuilder.append("color: blue;\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("td.warning\r\n");
			htmlStringBuilder.append("{\r\n");
			htmlStringBuilder.append("font-weight: bold;\r\n");
			htmlStringBuilder.append("color: orange;\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("a:link {\r\n");
			htmlStringBuilder.append("color: green;\r\n");
			htmlStringBuilder.append("background-color: transparent;\r\n");
			htmlStringBuilder.append("text-decoration: none;\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("a:visited {\r\n");
			htmlStringBuilder.append("color: red;\r\n");
			htmlStringBuilder.append("background-color: transparent;\r\n");
			htmlStringBuilder.append("text-decoration: none;\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("a:hover {\r\n");
			htmlStringBuilder.append("color: black;\r\n");
			htmlStringBuilder.append("background-color: transparent;\r\n");
			htmlStringBuilder.append("text-decoration: underline;\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("a:active {\r\n");
			htmlStringBuilder.append("color: yellow;\r\n");
			htmlStringBuilder.append("background-color: transparent;\r\n");
			htmlStringBuilder.append("text-decoration: underline;\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("#myInput {\r\n");
			htmlStringBuilder.append("background-image: url('/css/searchicon.png');\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("#myInput1 {\r\n");
			htmlStringBuilder.append("background-image: url('/css/searchicon.png');\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("#myInput2 {\r\n");
			htmlStringBuilder.append("background-image: url('/css/searchicon.png');\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("#myInput3 {\r\n");
			htmlStringBuilder.append("background-image: url('/css/searchicon.png');\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("#myInput4 {\r\n");
			htmlStringBuilder.append("background-image: url('/css/searchicon.png');\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("#myInput5 {\r\n");
			htmlStringBuilder.append("background-image: url('/css/searchicon.png');\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("#myTable {\r\n");
			htmlStringBuilder.append("border-collapse: collapse;\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("</style>\r\n");
			htmlStringBuilder.append("<style>\r\n");
			htmlStringBuilder.append("input[type=\"text\"] {\r\n");
			htmlStringBuilder.append("background: url(search-white.png) no-repeat 10px 6px #fcfcfc;\r\n");
			htmlStringBuilder.append("border: 0px solid #d1d1d1;\r\n");
			htmlStringBuilder.append("font: bold 12px Arial,Helvetica,Sans-serif;\r\n");
			htmlStringBuilder.append("color: #300909;\r\n");
			htmlStringBuilder.append("width: 110px;\r\n");
			htmlStringBuilder.append("padding: 6px 12px 6px 36px;\r\n");
			htmlStringBuilder.append("-webkit-border-radius: 20px;\r\n");
			htmlStringBuilder.append("-moz-border-radius: 20px;\r\n");
			htmlStringBuilder.append("border-radius: 100px;\r\n");
			htmlStringBuilder.append("text-shadow: 0 2px 3px rgba(0, 0, 0, 0.0);\r\n");
			htmlStringBuilder.append("-webkit-box-shadow: 0 1px 3px rgba(0, 0, 0, 0.15) inset;\r\n");
			htmlStringBuilder.append("-moz-box-shadow: 0 1px 3px rgba(0, 0, 0, 0.15) inset;\r\n");
			htmlStringBuilder.append("box-shadow: 0 1px 3px rgba(0, 0, 0, 0.15) inset;\r\n");
			htmlStringBuilder.append("-webkit-transition: all 0.7s ease 0s;\r\n");
			htmlStringBuilder.append("-moz-transition: all 0.7s ease 0s;\r\n");
			htmlStringBuilder.append("-o-transition: all 0.7s ease 0s;\r\n");
			htmlStringBuilder.append("transition: all 0.7s ease 0s;\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("input[type=\"text\":focus {\r\n");
			htmlStringBuilder.append("width: 100px;\r\n");
			htmlStringBuilder.append("outline:none; box-shadow:none;\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("</style>\r\n");
			htmlStringBuilder.append(
					"<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script>\r\n");
			htmlStringBuilder.append(
					"<script type=\"text/javascript\" src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js\"></script>\r\n");
			htmlStringBuilder.append(
					"<script src=\"http://ajax.aspnetcdn.com/ajax/jquery.ui/1.8.9/jquery-ui.js\" type=\"text/javascript\"></script>\r\n");
			htmlStringBuilder.append(
					"<link href=\"http://ajax.aspnetcdn.com/ajax/jquery.ui/1.8.9/themes/blitzer/jquery-ui.css\"  rel=\"stylesheet\" type=\"text/css\" />\r\n");
			htmlStringBuilder.append("<script type=\"text/javascript\">\r\n");
			htmlStringBuilder.append("$(function () {\r\n");
			htmlStringBuilder.append("$(\"[id^=bb_]\").click(function () {\r\n");
			htmlStringBuilder.append("var id = $(this).attr('id');\r\n");
			htmlStringBuilder.append("var splitId = id.split('_');\r\n");
			htmlStringBuilder.append("$(\"#error_\"+splitId[1]).dialog({\r\n");
			htmlStringBuilder.append("modal: true,\r\n");
			htmlStringBuilder.append("autoOpen: false,\r\n");
			htmlStringBuilder.append("title: \"Error Message\",\r\n");
			htmlStringBuilder.append("width: 700,\r\n");
			htmlStringBuilder.append("height: 300\r\n");
			htmlStringBuilder.append("});\r\n");
			htmlStringBuilder.append("$(\"#error_\"+splitId[1]).dialog('open');\r\n");
			htmlStringBuilder.append(" });\r\n");
			htmlStringBuilder.append("});\r\n");
			htmlStringBuilder.append("</script>\r\n");
			htmlStringBuilder.append("<script>\r\n");
			htmlStringBuilder.append("function errorLog()\r\n");
			htmlStringBuilder.append("{\r\n");
			htmlStringBuilder.append("document.write(\"<script>\");\r\n");
			htmlStringBuilder.append("document.write(\"document.write('<h2>This is the quoted script</h2>')\");\r\n");
			htmlStringBuilder.append("document.write(\"</\"" + "+" + "script>\");\r\n");
			htmlStringBuilder.append("function toggleMenu(objID)\r\n");
			htmlStringBuilder.append("{\r\n");
			htmlStringBuilder.append("if (!document.getElementById)\r\n");
			htmlStringBuilder.append("return;\r\n");
			htmlStringBuilder.append("var ob = document.getElementById(objID).style;\r\n");
			htmlStringBuilder.append("if(ob.display === 'none')\r\n");
			htmlStringBuilder.append("{\r\n");
			htmlStringBuilder.append("try\r\n");
			htmlStringBuilder.append("{\r\n");
			htmlStringBuilder.append("ob.display='table-row-group';\r\n");
			htmlStringBuilder.append("} catch(ex)\r\n");
			htmlStringBuilder.append("{\r\n");
			htmlStringBuilder.append("ob.display='block';\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("} else\r\n");
			htmlStringBuilder.append("{\r\n");
			htmlStringBuilder.append("ob.display='none';\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("function toggleSubMenu(objId)\r\n");
			htmlStringBuilder.append("{\r\n");
			htmlStringBuilder.append("for(i=1; i<10000; i++)\r\n");
			htmlStringBuilder.append("{\r\n");
			htmlStringBuilder.append("var ob = document.getElementById(objId.concat(i));\r\n");
			htmlStringBuilder.append("if(ob === null)\r\n");
			htmlStringBuilder.append("{\r\n");
			htmlStringBuilder.append("break;\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("if(ob.style.display === 'none')\r\n");
			htmlStringBuilder.append("{\r\n");
			htmlStringBuilder.append("try\r\n");
			htmlStringBuilder.append("{\r\n");
			htmlStringBuilder.append("ob.style.display='table-row';\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("catch(ex)\r\n");
			htmlStringBuilder.append("{\r\n");
			htmlStringBuilder.append("ob.style.display='block';\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("else\r\n");
			htmlStringBuilder.append("{\r\n");
			htmlStringBuilder.append("ob.style.display='none';\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("</script>\r\n");
			htmlStringBuilder.append("<script>\r\n");
			htmlStringBuilder.append("function myFunction() {\r\n");
			htmlStringBuilder.append(
					"var input,filter,td, input1, filter1, td1, input2, filter2, td2, input3, filter3, td3,input4, filter4, td4,input5, filter5, td5,table, tr, i;\r\n");
			htmlStringBuilder.append("input = document.getElementById(\"myInput\");\r\n");
			htmlStringBuilder.append("filter = input.value.toUpperCase();\r\n");
			htmlStringBuilder.append("input1 = document.getElementById(\"myInput1\");\r\n");
			htmlStringBuilder.append("filter1 = input1.value.toUpperCase();\r\n");
			htmlStringBuilder.append("input2 = document.getElementById(\"myInput2\");\r\n");
			htmlStringBuilder.append("filter2 = input2.value.toUpperCase();\r\n");
			htmlStringBuilder.append("input3 = document.getElementById(\"myInput3\");\r\n");
			htmlStringBuilder.append("filter3 = input3.value.toUpperCase();\r\n");
			htmlStringBuilder.append("input4 = document.getElementById(\"myInput4\");\r\n");
			htmlStringBuilder.append("filter4 = input4.value.toUpperCase();\r\n");
			htmlStringBuilder.append("input5 = document.getElementById(\"myInput5\");\r\n");
			htmlStringBuilder.append("filter5 = input5.value.toUpperCase();\r\n");
			htmlStringBuilder.append("table = document.getElementById(\"myTable\");\r\n");
			htmlStringBuilder.append("tr = table.getElementsByTagName(\"tr\");\r\n");
			htmlStringBuilder.append("for (i = 0; i < tr.length; i++) {\r\n");
			htmlStringBuilder.append("td = tr[i].getElementsByTagName(\"td\")[0];\r\n");
			htmlStringBuilder.append("td1 = tr[i].getElementsByTagName(\"td\")[1];\r\n");
			htmlStringBuilder.append("td2 = tr[i].getElementsByTagName(\"td\")[2];\r\n");
			htmlStringBuilder.append("td3 = tr[i].getElementsByTagName(\"td\")[3];\r\n");
			htmlStringBuilder.append("td4 = tr[i].getElementsByTagName(\"td\")[4];\r\n");
			htmlStringBuilder.append("td5 = tr[i].getElementsByTagName(\"td\")[5];\r\n");
			htmlStringBuilder.append("if (td) {\r\n");
			htmlStringBuilder.append("if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {\r\n");
			htmlStringBuilder.append("if(td1){\r\n");
			htmlStringBuilder.append("if (td1.innerHTML.toUpperCase().indexOf(filter1) > -1) {\r\n");
			htmlStringBuilder.append("if(td2){\r\n");
			htmlStringBuilder.append("if (td2.innerHTML.toUpperCase().indexOf(filter2) > -1) {\r\n");
			htmlStringBuilder.append("if(td3){\r\n");
			htmlStringBuilder.append("if (td3.innerHTML.toUpperCase().indexOf(filter3) > -1) {\r\n");
			htmlStringBuilder.append("if(td4){\r\n");
			htmlStringBuilder.append("if (td4.innerHTML.toUpperCase().indexOf(filter4) > -1) {\r\n");
			htmlStringBuilder.append("if(td5){\r\n");
			htmlStringBuilder.append("if (td5.innerHTML.toUpperCase().indexOf(filter5) > -1) {\r\n");
			htmlStringBuilder.append("tr[i].style.display = \"\";\r\n");
			htmlStringBuilder.append("}else{\r\n");
			htmlStringBuilder.append("tr[i].style.display = \"none\";\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("}else{\r\n");
			htmlStringBuilder.append("tr[i].style.display = \"\";\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("}else{\r\n");
			htmlStringBuilder.append("tr[i].style.display = \"none\";\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("}else{\r\n");
			htmlStringBuilder.append("tr[i].style.display = \"\";\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("}else{\r\n");
			htmlStringBuilder.append("tr[i].style.display = \"none\";\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("}else{\r\n");
			htmlStringBuilder.append("tr[i].style.display = \"\";\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("}else{\r\n");
			htmlStringBuilder.append("tr[i].style.display = \"none\";\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("}else{\r\n");
			htmlStringBuilder.append("tr[i].style.display = \"\";\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("}else{\r\n");
			htmlStringBuilder.append("tr[i].style.display = \"none\";\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("}else{\r\n");
			htmlStringBuilder.append("tr[i].style.display = \"\";\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("} else {\r\n");
			htmlStringBuilder.append("tr[i].style.display = \"none\";\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("}\r\n");
			htmlStringBuilder.append("</script>\r\n");
			htmlStringBuilder.append("<title>Boneal - Automation Report</title\r\n");
			htmlStringBuilder.append("</head>\r\n");
			htmlStringBuilder.append("<body>\r\n");
			htmlStringBuilder.append("<table height=\"100%\" border=\"1\">\r\n");
			htmlStringBuilder.append("<tr class='heading'> \r\n");
			htmlStringBuilder.append(
					"<th colspan='4' style='font-family:Copperplate Gothic Bold; font-size:1.1em;'> Boneal - Test Automation Report </th>\r\n");
			htmlStringBuilder.append("</tr>\r\n");
			htmlStringBuilder.append("<tr class='subheading'>\r\n");
			htmlStringBuilder.append("<th>&nbsp;Start&nbsp;&nbsp;Time&nbsp;&nbsp;&nbsp;:&nbsp;</th>\r\n");
			htmlStringBuilder.append("<th>&nbsp;&nbsp;" + startTime + "&nbsp;</th>\r\n");
			htmlStringBuilder.append("<th>&nbsp;Pass&nbsp;&nbsp;Percentage&nbsp;&nbsp;&nbsp;:&nbsp;</th>\r\n");
			htmlStringBuilder.append("<th>&nbsp;&nbsp;" + passper + "%</th>\r\n");
			htmlStringBuilder.append("</tr>\r\n");
			htmlStringBuilder.append("<tr class='subheading'>\r\n");
			htmlStringBuilder.append("<th>&nbsp;End&nbsp;&nbsp;Time&nbsp;&nbsp;&nbsp;&nbsp&nbsp;:&nbsp;</th>\r\n");
			htmlStringBuilder.append("<th> &nbsp;&nbsp;" + endTime + "&nbsp;</th>\r\n");
			htmlStringBuilder
					.append("<th>&nbsp;Fail&nbsp;&nbsp;Percentage&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;</th>\r\n");
			htmlStringBuilder.append("<th>&nbsp;&nbsp;" + failper + "%</th>\r\n");
			htmlStringBuilder.append("</tr>\r\n");
			htmlStringBuilder.append("<table height=\"5%\" cellspacing=\"10\" border=\"1\">\r\n");
			htmlStringBuilder.append("<tr>\r\n");
			htmlStringBuilder.append(
					"<td width=\"12%\"><input type=\"text\" id=\"myInput\" onkeyup=\"myFunction()\" size=\"40\" placeholder=\"Search by scenario...\" /></td>\r\n");
			htmlStringBuilder.append(
					"<td width=\"12%\"><input type=\"text\" id=\"myInput1\" onkeyup=\"myFunction()\" size=\"40\" placeholder=\"Search by test...\" /></td>\r\n");
			htmlStringBuilder.append(
					"<td width=\"12%\"><input type=\"text\" id=\"myInput2\" onkeyup=\"myFunction()\" size=\"40\" placeholder=\"Search by browser...\" /></td>\r\n");
			htmlStringBuilder.append(
					"<td width=\"12%\"><input type=\"text\" id=\"myInput3\" onkeyup=\"myFunction()\" size=\"40\" placeholder=\"Search by OS...\" /></td>\r\n");
			htmlStringBuilder.append(
					"<td width=\"12%\"><input type=\"text\" id=\"myInput4\" onkeyup=\"myFunction()\" size=\"40\" placeholder=\"Search by Data Row...\" /></td>\r\n");
			htmlStringBuilder.append(
					"<td width=\"12%\"><input type=\"text\" id=\"myInput5\" onkeyup=\"myFunction()\" size=\"40\" placeholder=\"Search by Status...\" /></td>\r\n");
			htmlStringBuilder.append("<td width=\"20%\"></td>\r\n");
			htmlStringBuilder.append("<td width=\"20%\"></td>\r\n");
			htmlStringBuilder.append("</tr>\r\n");
			htmlStringBuilder.append("</table>\r\n");
			htmlStringBuilder.append("<table id=\"myTable\" border=\"1\">\r\n");
			htmlStringBuilder.append("<tr class=\"header\" bgcolor=\"#8E8A8A\">\r\n");
			htmlStringBuilder.append("<th class =\"main\" width=\"12%\">Senario Name </th>\r\n");
			htmlStringBuilder.append("<th class =\"main\"  width=\"12%\">Test Name</th>\r\n");
			htmlStringBuilder.append("<th class =\"main\"  width=\"12%\">Browser</th>\r\n");
			htmlStringBuilder.append("<th class =\"main\"  width=\"12%\">OS</th>\r\n");
			htmlStringBuilder.append("<th class =\"main\"  width=\"12%\">Test Status</th>\r\n");
			htmlStringBuilder.append("<th class =\"main\"  width=\"12%\">Test Data Row</th>\r\n");
			htmlStringBuilder.append("<th class =\"main\"  width=\"12%\">Screenshot</th>\r\n");
			htmlStringBuilder.append("<th class =\"main\"  width=\"12%\">Error Log</th>\r\n");
			htmlStringBuilder.append("</tr>\r\n");

			// String excelFilePath =
			// "C:\\Amazon\\Amazon\\logs\\ITAFLogs_06-28-2017 06-59.xlsx";
			String excelFilePath = infilepath;
			FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

			Workbook workbook = new XSSFWorkbook(inputStream);
			Sheet firstSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = firstSheet.iterator();

			Row nextRow;
			int errorCount = 1;
			while (iterator.hasNext()) {
				htmlStringBuilder.append("<tr>\r\n");
				nextRow = iterator.next();
				if (nextRow.getRowNum() == 0) {
					nextRow = iterator.next();
				}
				Iterator<Cell> cellIterator = nextRow.cellIterator();
				int count = 0;
				String cellresult = "";
				while (cellIterator.hasNext()) {
					count++;
					Cell cell = cellIterator.next();

					if (count == 1 || count == 2 || count == 3 || count == 4) {
						htmlStringBuilder.append("<td width=\"12%\">" + cell.getStringCellValue() + "</td>\r\n");
					} else if (count == 6) {
						htmlStringBuilder.append("<td width=\"7%\">" + cell.getStringCellValue() + "</td>\r\n");
					} else if ((count == 5) && ("Pass".equalsIgnoreCase(cell.getStringCellValue()))) {
						cellresult = cell.getStringCellValue();
						htmlStringBuilder.append("<td class = \"pass\" width=\"7%\">" + cellresult + "</td>\r\n");
					} else if ((count == 5) && ("Fail".equalsIgnoreCase(cell.getStringCellValue()))) {
						cellresult = cell.getStringCellValue();
						htmlStringBuilder.append("<td class = \"fail\" width=\"7%\">" + cellresult + "</td>\r\n");
					} else if ((count == 5) && ("Skipped".equalsIgnoreCase(cell.getStringCellValue()))) {
						cellresult = cell.getStringCellValue();
						htmlStringBuilder.append("<td class = \"skipped\" width=\"7%\">" + cellresult + "</td>\r\n");
					} else if ((count == 7) && ("Pass".equalsIgnoreCase(cellresult))) {
						htmlStringBuilder.append("<td width=\"20%\">" + cell.getStringCellValue() + "</td>\r\n");
					} else if ((count == 7) && ("Skipped".equalsIgnoreCase(cellresult))) {
						htmlStringBuilder.append("<td width=\"20%\">" + cell.getStringCellValue() + "</td>\r\n");
					} else if ((count == 7) && ("Fail".equalsIgnoreCase(cellresult))) {
						htmlStringBuilder.append("<td class =\"fail\"  width=\"20%\">\r\n");
						htmlStringBuilder.append(
								"<a target=\"_blank\" href=\"" + cell.getStringCellValue() + "\">Screenshot</a>\r\n");
						htmlStringBuilder.append("</td>\r\n");
					} else if ((count == 8) && ("Pass".equalsIgnoreCase(cellresult))) {
						htmlStringBuilder.append(
								"<td class =\"pass\"  width=\"20%\">" + cell.getStringCellValue() + "</td>\r\n");
					} else if ((count == 8) && ("Skipped".equalsIgnoreCase(cellresult))) {
						htmlStringBuilder.append(
								"<td class =\"pass\"  width=\"20%\">" + cell.getStringCellValue() + "</td>\r\n");
					} else if ((count == 8) && ("Fail".equalsIgnoreCase(cellresult))) {					
						
						//Replace occurrences of html code inside html error with gt and lt
						String err_msg = cell.getStringCellValue().replaceAll("<(.*?)>", "&lt;$1&gt;");						
						htmlStringBuilder.append("<td width=\"20%\">\r\n");
						htmlStringBuilder
								.append("<p id=\"error_" + errorCount + "\" style=\"display:none\" align = \"center\"> "
										+err_msg +" </p>"+ "\r\n");
						htmlStringBuilder.append("<button id=\"bb_" + errorCount + "\" >Show</button>\r\n");
						htmlStringBuilder.append("</td>\r\n");
						errorCount++;
					}

				}
				htmlStringBuilder.append("</tr>\r\n");
			}

			workbook.close();
			inputStream.close();
			htmlStringBuilder.append("</table>\r\n");
			htmlStringBuilder.append("</table>\r\n");
			htmlStringBuilder.append("</body>\r\n");
			htmlStringBuilder.append("</html>\r\n");
			// write html string content to a file
			String projectPath = System.getProperty("user.dir");
			createFolders(projectPath + "/Reports");
			WriteToFile(htmlStringBuilder.toString(), "Reports/" + finalReportName + ".html");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void WriteToFile(String fileContent, String fileName) throws IOException {
		String projectPath = System.getProperty("user.dir");
		String tempFile = projectPath + File.separator + fileName;
		File file = new File(tempFile);
		// if file does exists, then delete and create a new file
		if (file.exists()) {
			try {
				File newFileName = new File(projectPath + File.separator + "backup_" + fileName);
				file.renameTo(newFileName);
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// write to file with OutputStreamWriter
		OutputStream outputStream = new FileOutputStream(file.getAbsoluteFile());
		Writer writer = new OutputStreamWriter(outputStream);
		writer.write(fileContent);
		writer.close();

	}

	public static String createFolders(String sFilePath) {

		String sAbsolutePath = "";
		try {

			File file = new File(sFilePath);
			if (!file.exists()) {
				file.mkdirs();
			}

			sAbsolutePath = file.getAbsolutePath();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return sAbsolutePath;
	}

}
