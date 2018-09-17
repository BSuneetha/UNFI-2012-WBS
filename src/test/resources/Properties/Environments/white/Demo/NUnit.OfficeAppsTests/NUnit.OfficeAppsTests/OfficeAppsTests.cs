using NUnit.Framework;
using NUnit.Framework.Interfaces;
using System.Diagnostics;
using System.Linq;
using TestStack.White.Factory;
using TestStack.White.UIItems;
using TestStack.White.UIItems.Finders;
using TestStack.White.UIItems.ListBoxItems;
using TestStack.White.UIItems.MenuItems;
using TestStack.White.UIItems.WindowItems;
using TestStack.White.UIItems.WindowStripControls;
using TestStack.White.WindowsAPI;

namespace TestStack.White.UITests.Scenarios
{
    [TestFixture]
    public class WordTests
    {
        const string WordSourceFile = @"C:\Program Files\Microsoft Office\root\Office16\WINWORD.EXE";
        const string ExcelSourceFile = @"C:\Program Files\Microsoft Office\root\Office16\EXCEL.EXE";
        //const string OneNoteSourceFile = @"C:\Program Files\Microsoft Office\root\Office16\ONENOTE.EXE";
        // const string Notepad = @"C:\Windows\system32\notepad.exe";
        //const string InternetExplorer = @"C:\Program Files\Internet Explorer\iexplore.exe";
        
        [Test]
        public void WordSecurityTest()
        {
            var sw = Stopwatch.StartNew();
            using (var app = Application.Launch(WordSourceFile))
            using (var window = app.GetWindow("Word"))
            {
                Assert.That(window.IsCurrentlyActive, Is.True);
                window.Get<Button>(SearchCriteria.ByText("Blank document")).Click();
                window.Keyboard.HoldKey(KeyboardInput.SpecialKeys.CONTROL);
                window.Keyboard.Enter("B");
                window.Keyboard.LeaveKey(KeyboardInput.SpecialKeys.CONTROL);
                window.Keyboard.Enter("Innominds Test Automation Framework");
                window.Keyboard.PressSpecialKey(KeyboardInput.SpecialKeys.RETURN);
                window.Keyboard.PressSpecialKey(KeyboardInput.SpecialKeys.RETURN);
                window.Keyboard.HoldKey(KeyboardInput.SpecialKeys.CONTROL);
                window.Keyboard.Enter("B");
                window.Keyboard.LeaveKey(KeyboardInput.SpecialKeys.CONTROL);

                //Save the document

                window.Keyboard.Enter("Innominds Test Automation Framework (Harmony) is a customized and innominds automation tool which helps in building the automation test scripts for Desktop (TestStack.White), Web, Mobile and API. It reduces test execution cycle times and related costs.");
                window.Keyboard.PressSpecialKey(KeyboardInput.SpecialKeys.RETURN);
                window.Keyboard.PressSpecialKey(KeyboardInput.SpecialKeys.RETURN);
                window.Keyboard.Enter("This tool helps in building the scripts automatically based on the inbuilt reusable functions developed.It is built on idea to reduce the scripting time based on the reusable functions.");
                window.Keyboard.PressSpecialKey(KeyboardInput.SpecialKeys.ALT);
                window.Keyboard.Enter("f");
                window.Keyboard.Enter("a");
                window.Keyboard.Enter("s1");
                window.Keyboard.Enter("y3");
                //Document name
                window.Keyboard.Enter("W" + GetCurrentMillis());
                System.Threading.Thread.Sleep(2000);
                window.Keyboard.PressSpecialKey(KeyboardInput.SpecialKeys.ALT);
                window.Keyboard.Enter("f");
                window.Keyboard.Enter("a");
                window.Keyboard.Enter("s1");
                System.Threading.Thread.Sleep(1000);
                //Save Button
                window.Keyboard.Enter("y5");
                System.Threading.Thread.Sleep(20000);
                System.Collections.Generic.List<UIItems.WindowItems.Window> windows = app.GetWindows();

                var w1 = windows.Last();
                if (w1.Title.Equals("Microsoft Word"))
                    w1.Get<Button>(SearchCriteria.ByText("OK")).Click();

                System.Threading.Thread.Sleep(2000);
                System.Threading.Thread.Sleep(8000);
                //Add security
                window.Keyboard.PressSpecialKey(KeyboardInput.SpecialKeys.ALT);
                window.Keyboard.Enter("FIPE");
                //window.Keyboard.Enter("i");
                //window.Keyboard.Enter("p");
                System.Threading.Thread.Sleep(500);
                //window.Keyboard.PressSpecialKey(KeyboardInput.SpecialKeys.DOWN);
                //window.Keyboard.PressSpecialKey(KeyboardInput.SpecialKeys.RETURN);

                System.Threading.Thread.Sleep(1000);
                //The password
                windows = app.GetWindows();
                w1 = windows.Last();
                if (w1.Title.Equals("Encrypt Document"))
                {
                    w1.Keyboard.Enter("Tester");
                    w1.Get<Button>(SearchCriteria.ByText("OK")).Click();
                }
                System.Threading.Thread.Sleep(1000);
                //Re-confirm Password
                windows = app.GetWindows();
                w1 = windows.Last();
                if (w1.Title.Equals("Confirm Password"))
                {
                    w1.Keyboard.Enter("Tester");
                    w1.Get<Button>(SearchCriteria.ByText("OK")).Click();
                }

                System.Threading.Thread.Sleep(1000);
                window.Keyboard.PressSpecialKey(KeyboardInput.SpecialKeys.ESCAPE);
                System.Threading.Thread.Sleep(30000);
                //Close the window
                window.Close();
                //If prompts to save changes, click Save
                windows = app.GetWindows();
                w1 = windows.Last();
                if (w1.Title.Equals("Microsoft Word"))
                    w1.Get<Button>(SearchCriteria.ByText("Save")).Click();
            }
        }

        [Test]
        public void ExcelSecurityTest()
        {
            var sw = Stopwatch.StartNew();
            using (var app = Application.Launch(ExcelSourceFile))
            using (var window = app.GetWindow("Excel"))
            {
                Assert.That(window.IsCurrentlyActive, Is.True);
                window.Get<Button>(SearchCriteria.ByText("Blank workbook")).Click();
                System.Threading.Thread.Sleep(1000);
                window.Keyboard.Enter("EmpName");
                System.Threading.Thread.Sleep(1000);
                window.Keyboard.PressSpecialKey(KeyboardInput.SpecialKeys.TAB);
                window.Keyboard.Enter("EmpNo");
                System.Threading.Thread.Sleep(1000);
                window.Keyboard.PressSpecialKey(KeyboardInput.SpecialKeys.TAB);
                window.Keyboard.Enter("Phone#");
                System.Threading.Thread.Sleep(1000);
                window.Keyboard.PressSpecialKey(KeyboardInput.SpecialKeys.RETURN);
                window.Keyboard.Enter("Melvin");
                System.Threading.Thread.Sleep(1000);
                window.Keyboard.PressSpecialKey(KeyboardInput.SpecialKeys.TAB);
                window.Keyboard.Enter("456");
                System.Threading.Thread.Sleep(1000);
                window.Keyboard.PressSpecialKey(KeyboardInput.SpecialKeys.TAB);
                window.Keyboard.Enter("8888999977");
                System.Threading.Thread.Sleep(1000);
                window.Keyboard.PressSpecialKey(KeyboardInput.SpecialKeys.RETURN);
                window.Keyboard.Enter("John");
                System.Threading.Thread.Sleep(1000);
                window.Keyboard.PressSpecialKey(KeyboardInput.SpecialKeys.TAB);
                window.Keyboard.Enter("555");
                System.Threading.Thread.Sleep(1000);
                window.Keyboard.PressSpecialKey(KeyboardInput.SpecialKeys.TAB);
                window.Keyboard.Enter("1223785253");
                System.Threading.Thread.Sleep(1000);
                window.Keyboard.PressSpecialKey(KeyboardInput.SpecialKeys.RETURN);
                System.Threading.Thread.Sleep(1000);
                window.Keyboard.Enter("Manu");
                System.Threading.Thread.Sleep(1000);
                window.Keyboard.PressSpecialKey(KeyboardInput.SpecialKeys.TAB);
                window.Keyboard.Enter("855");
                System.Threading.Thread.Sleep(1000);
                window.Keyboard.PressSpecialKey(KeyboardInput.SpecialKeys.TAB);
                window.Keyboard.Enter("2225785253");
                System.Threading.Thread.Sleep(1000);
                window.Keyboard.PressSpecialKey(KeyboardInput.SpecialKeys.RETURN);
                System.Threading.Thread.Sleep(1000);
                window.Keyboard.Enter("Jack");
                System.Threading.Thread.Sleep(1000);
                window.Keyboard.PressSpecialKey(KeyboardInput.SpecialKeys.TAB);
                window.Keyboard.Enter("355");
                System.Threading.Thread.Sleep(1000);
                window.Keyboard.PressSpecialKey(KeyboardInput.SpecialKeys.TAB);
                window.Keyboard.Enter("1225784253");
                System.Threading.Thread.Sleep(1000);
                window.Keyboard.PressSpecialKey(KeyboardInput.SpecialKeys.RETURN);
                System.Threading.Thread.Sleep(1000);

                //Save the document               
                window.Keyboard.PressSpecialKey(KeyboardInput.SpecialKeys.ALT);
                window.Keyboard.Enter("f");
                window.Keyboard.Enter("a");
                window.Keyboard.Enter("s1");
                window.Keyboard.Enter("y3");
                //var elapsedStage2 = sw.ElapsedMilliseconds;
                //Document Name
                window.Keyboard.Enter("X" + GetCurrentMillis());
                //window.Keyboard.Enter("ExcelFileDemo55");
                System.Threading.Thread.Sleep(2000);
                window.Keyboard.PressSpecialKey(KeyboardInput.SpecialKeys.ALT);
                window.Keyboard.Enter("f");
                window.Keyboard.Enter("a");
                window.Keyboard.Enter("s1");
                System.Threading.Thread.Sleep(1000);
                //Save Button
                window.Keyboard.Enter("y5");
                System.Threading.Thread.Sleep(20000);
                System.Collections.Generic.List<UIItems.WindowItems.Window> windows = app.GetWindows();

                var w1 = windows.Last();
                if (w1.Title.Equals("Save As"))
                    w1.Get<Button>(SearchCriteria.ByText("OK")).Click();

                System.Threading.Thread.Sleep(2000);
                System.Threading.Thread.Sleep(8000);
                //Add security
                window.Keyboard.PressSpecialKey(KeyboardInput.SpecialKeys.ALT);
                window.Keyboard.Enter("FIPE");
                //window.Keyboard.Enter("i");
                //window.Keyboard.Enter("p");
                System.Threading.Thread.Sleep(500);
                //window.Keyboard.PressSpecialKey(KeyboardInput.SpecialKeys.DOWN);
                //window.Keyboard.PressSpecialKey(KeyboardInput.SpecialKeys.RETURN);

                System.Threading.Thread.Sleep(1000);
                //The password
                windows = app.GetWindows();
                w1 = windows.Last();
                if (w1.Title.Equals("Encrypt Document"))
                {
                    w1.Keyboard.Enter("Tester");
                    w1.Get<Button>(SearchCriteria.ByText("OK")).Click();
                }
                System.Threading.Thread.Sleep(1000);
                //Re-confirm Password
                windows = app.GetWindows();
                w1 = windows.Last();
                if (w1.Title.Equals("Confirm Password"))
                {
                    w1.Keyboard.Enter("Tester");
                    w1.Get<Button>(SearchCriteria.ByText("OK")).Click();
                }


                System.Threading.Thread.Sleep(1000);
                window.Keyboard.PressSpecialKey(KeyboardInput.SpecialKeys.ESCAPE);
                System.Threading.Thread.Sleep(30000);
                //Close the window
                window.Close();
                //If prompts to save changes, click Save
                windows = app.GetWindows();
                w1 = windows.Last();
                if (w1.Title.Equals("Microsoft Excel"))
                    w1.Get<Button>(SearchCriteria.ByText("Save")).Click();
            }
        }

        public static double GetCurrentMillis()
        {
            System.DateTime Jan1970 = new System.DateTime(1970, 1, 1, 0, 0, 0, System.DateTimeKind.Utc);
            System.TimeSpan javaSpan = System.DateTime.UtcNow - Jan1970;
            return javaSpan.TotalMilliseconds;
        }
        /*
        [Test]
        public void OneNoteSecurityTest()
        {
            var sw = Stopwatch.StartNew();
            var elapsedStage3 = sw.ElapsedMilliseconds;
            
            //CreateNotes("OneNote", "AAAA" + GetCurrentMillis(), true);
            //elapsedStage3 = sw.ElapsedMilliseconds;
            //CreateNotes("OneNote", "AAAAA" + GetCurrentMillis(), false);
        }*/

        /*[Test]
        public void Demochks()
        {
            var app = Application.Launch(OneNoteSourceFile);
            var wins = app.GetWindows();
            foreach (var w1 in wins)
            {
                if (w1.IsCurrentlyActive)
                {
                    System.Console.WriteLine(w1.Title);
                } else
                    System.Console.WriteLine(w1.Title);
            }
            //using (var window = app.GetWindows().Last())
        }*/




        /*public void CreateNotes(string wtitle, string testname, bool flag)
        {
            using (var app = Application.Launch(OneNoteSourceFile))            
            using (var window = app.GetWindow(wtitle))
            {
                //Assert.That(window.IsCurrentlyActive, Is.True);
                //Open the One Note -new notebook
                window.Keyboard.PressSpecialKey(KeyboardInput.SpecialKeys.ALT);
                window.Keyboard.Enter("fns1B");
                System.Threading.Thread.Sleep(2000);
                System.Collections.Generic.List<Window> modalWindows = window.ModalWindows(); //list of all the modal windows belong to the window.
                foreach (var mw in modalWindows)
                {
                    System.Console.WriteLine(mw.Title);
                    System.Threading.Thread.Sleep(2000);
                }
                Window childWindow = window.ModalWindow("Create New Notebook"); //modal window with title "child"
                System.Threading.Thread.Sleep(2000);
                //const string testname = "DemoTest1";
                childWindow.Get<ComboBox>(SearchCriteria.ByText("Notebook Name:")).EditableText = testname;
                System.Threading.Thread.Sleep(2000);
                childWindow.Get<Button>(SearchCriteria.ByText("Create")).Click();
                System.Threading.Thread.Sleep(20000);
                System.Collections.Generic.List<UIItems.WindowItems.Window> windows = app.GetWindows();
                System.Threading.Thread.Sleep(2000);
                windows = app.GetWindows();
                var w1 = windows.Last();
                System.Threading.Thread.Sleep(2000);
                if (w1.Title.Equals("Microsoft OneNote"))
                {
                    //w1.Get<TextBox>(SearchCriteria.ByText("NoteBook Name")).BulkText = "DemoTest Notebook";

                    System.Threading.Thread.Sleep(1000);
                    w1.Close();
                }
                //w1.Get<Button>(SearchCriteria.ByText("Not now")).Click();
                //w1.Close();
                //w1.Enter(KeyboardInput.SpecialKeys.ESCAPE);
                //window.Keyboard.Enter("B");               
                window.Keyboard.Enter("Framework Notes");
                System.Threading.Thread.Sleep(2000);
                //window.Keyboard.LeaveKey(KeyboardInput.SpecialKeys.CONTROL);
                window.Keyboard.PressSpecialKey(KeyboardInput.SpecialKeys.RETURN);
                window.Keyboard.PressSpecialKey(KeyboardInput.SpecialKeys.RETURN);
                System.Threading.Thread.Sleep(2000);
                window.Keyboard.Enter("Innominds Test Automation Framework (Harmony) is a customized and innominds automation tool which helps in building the automation test scripts for Desktop (TestStack.White), Web, Mobile and API. It reduces test execution cycle times and related costs.");
                System.Threading.Thread.Sleep(2000);
                window.Keyboard.PressSpecialKey(KeyboardInput.SpecialKeys.RETURN);
                window.Keyboard.PressSpecialKey(KeyboardInput.SpecialKeys.RETURN);
                window.Keyboard.Enter("This tool helps in building the scripts automatically based on the inbuilt reusable functions developed.It is built on idea to reduce the scripting time based on the reusable functions.");
                System.Threading.Thread.Sleep(2000);

                //Add a new section
                window.Get<Button>(SearchCriteria.ByText("Section Create a New Section")).Click();
                //Type section name
                window.Keyboard.Enter("Test Section");
                //Press Tab
                window.Keyboard.PressSpecialKey(KeyboardInput.SpecialKeys.TAB);
                //Some more text
                window.Keyboard.Enter("Test Automation is to realize ROI by automating repeatable tasks with coverage to address Orthogonal cases and address key issues");
                //Secure the notebook
                if (flag)
                {
                    window.Keyboard.HoldKey(KeyboardInput.SpecialKeys.ALT);
                    //System.Threading.Thread.Sleep(2000);
                    window.Keyboard.Enter("RP");
                    window.Keyboard.LeaveKey(KeyboardInput.SpecialKeys.ALT);
                    //System.Threading.Thread.Sleep(2000);
                    //window.Keyboard.Enter("P");
                    System.Threading.Thread.Sleep(2000);
                    window.Get<Button>(SearchCriteria.ByText("Set Password...")).Click();
                    //window.Keyboard.PressSpecialKey(KeyboardInput.SpecialKeys.RETURN);

                    System.Threading.Thread.Sleep(2000);
                    //Prompts password
                    windows = app.GetWindows();
                    w1 = windows.Last();
                    if (w1.Title.Equals("Password Protection"))
                    {
                        //w1.Get<TextBox>(SearchCriteria.ByText("NoteBook Name")).BulkText = "DemoTest Notebook";
                        w1.Keyboard.Enter("Tester");
                        System.Threading.Thread.Sleep(2000);
                        window.Keyboard.PressSpecialKey(KeyboardInput.SpecialKeys.TAB);
                        w1.Keyboard.Enter("Tester");
                        System.Threading.Thread.Sleep(2000);
                        w1.Get<Button>(SearchCriteria.ByText("OK")).Click();
                        System.Threading.Thread.Sleep(2000);
                    }
                    window.Get<Button>(SearchCriteria.ByText("Lock All")).Click();
                    //Protect the other section too
                    window.Get<Button>(SearchCriteria.ByText("New Section 1")).Click();
                    window.Keyboard.HoldKey(KeyboardInput.SpecialKeys.ALT);
                    //System.Threading.Thread.Sleep(2000);
                    window.Keyboard.Enter("RP");
                    window.Keyboard.LeaveKey(KeyboardInput.SpecialKeys.ALT);
                    //System.Threading.Thread.Sleep(2000);
                    //window.Keyboard.Enter("P");
                    System.Threading.Thread.Sleep(2000);
                    window.Get<Button>(SearchCriteria.ByText("Set Password...")).Click();

                    System.Threading.Thread.Sleep(2000);
                    //Prompts password
                    windows = app.GetWindows();
                    w1 = windows.Last();
                    if (w1.Title.Equals("Password Protection"))
                    {
                        //w1.Get<TextBox>(SearchCriteria.ByText("NoteBook Name")).BulkText = "DemoTest Notebook";
                        w1.Keyboard.Enter("Tester");
                        System.Threading.Thread.Sleep(2000);
                        window.Keyboard.PressSpecialKey(KeyboardInput.SpecialKeys.TAB);
                        w1.Keyboard.Enter("Tester");
                        System.Threading.Thread.Sleep(2000);
                        w1.Get<Button>(SearchCriteria.ByText("OK")).Click();
                        System.Threading.Thread.Sleep(2000);
                    }
                    window.Get<Button>(SearchCriteria.ByText("Lock All")).Click();
                }

                System.Threading.Thread.Sleep(3000);
                window.Get<Button>(SearchCriteria.ByText("Test Section")).Click();
                System.Threading.Thread.Sleep(3000);
                window.Get<Button>(SearchCriteria.ByText("New Section 1")).Click();
                System.Threading.Thread.Sleep(30000);
                //Close the window

                window.Close();

            }
        }*/

        /*Nunit One time tear down handler to capture screenshots on error 
        [OneTimeTearDown]
        public void OneTimeTearDown()
        {
            if (TestContext.CurrentContext.Result.Outcome != ResultState.Success)
            {
                //Takes a screenshot of the entire desktop, and saves it to disk
                Desktop.TakeScreenshot("C:\\white-framework.png", System.Drawing.Imaging.ImageFormat.Png);
            }
        }*/

        /*[Test]
        [Ignore("There are different IE versions which make this test fail")]
        public void InternetExplorerTests()
        {
            using (var app = Application.Launch(InternetExplorer))
            {
                using (var window = app.GetWindows().Single())
                {
                    var button = window.Get<Button>(SearchCriteria.ByAutomationId("Item 3"));
                    //check if we can get a win32 tooltip
                    Assert.That(window.GetToolTipOn(button).Text, Is.EqualTo("Tools (Alt+X)"));
                    button.Click();
                    window.PopupMenu("Internet options").Click();
                    using (var internetOptions = window.ModalWindow("Internet Options"))
                    {
                        var textBox = internetOptions.Get<TextBox>(SearchCriteria.ByAutomationId("1487"));

                        textBox.Text = "http://google.com";

                        Assert.That(textBox.Text, Is.EqualTo("http://google.com"));
                    }
                }
            }
        }        

        /*[Test]
        [Ignore("There are different IE versions which make this test fail")]
        public void InternetExplorerTests()
        {
            using (var app = Application.Launch(InternetExplorer))
            {
                using (var window = app.GetWindows().Single())
                {
                    var button = window.Get<Button>(SearchCriteria.ByAutomationId("Item 3"));
                    //check if we can get a win32 tooltip
                    Assert.That(window.GetToolTipOn(button).Text, Is.EqualTo("Tools (Alt+X)"));
                    button.Click();
                    window.PopupMenu("Internet options").Click();
                    using (var internetOptions = window.ModalWindow("Internet Options"))
                    {
                        var textBox = internetOptions.Get<TextBox>(SearchCriteria.ByAutomationId("1487"));

                        textBox.Text = "http://google.com";

                        Assert.That(textBox.Text, Is.EqualTo("http://google.com"));
                    }
                }
            }
        }

        [Test]
        public void CalculatorTests()
        {
            //strat process for the above exe file location
            var psi = new ProcessStartInfo(ExeSourceFile);
            // launch the process through white application
            using (var application = Application.AttachOrLaunch(psi))
            using (var mainWindow = application.GetWindow(SearchCriteria.ByText("Calculator"), InitializeOption.NoCache))
            {
                // Verify can click on menu twice
                var menuBar = mainWindow.Get<MenuBar>(SearchCriteria.ByText("Application"));
                menuBar.MenuItem("Edit", "Copy").Click();
                menuBar.MenuItem("Edit", "Copy").Click();

                mainWindow.Keyboard.HoldKey(KeyboardInput.SpecialKeys.CONTROL);
                mainWindow.Keyboard.Enter("E");
                mainWindow.Keyboard.LeaveKey(KeyboardInput.SpecialKeys.CONTROL);

                //On Date window find the difference between dates.
                //Set value into combobox
                mainWindow.Get<ComboBox>(SearchCriteria.ByAutomationId("4003")).Select("Calculate the difference between two dates");
                //Click on Calculate button
                mainWindow.Get<Button>(SearchCriteria.ByAutomationId("4009")).Click();

                mainWindow.Keyboard.HoldKey(KeyboardInput.SpecialKeys.CONTROL);
                mainWindow.Keyboard.PressSpecialKey(KeyboardInput.SpecialKeys.F4);
                mainWindow.Keyboard.LeaveKey(KeyboardInput.SpecialKeys.CONTROL);

                var menuView = mainWindow.Get<Menu>(SearchCriteria.ByText("View"));
                menuView.Click();
                var menuViewBasic = mainWindow.Get<Menu>(SearchCriteria.ByText("Basic"));
                menuViewBasic.Click();

                PerformSummationOnCalculator(mainWindow);
            }
        }

        /// <summary>
        /// method to Perform Addition of two numbers and validate the result
        /// </summary>
        private static void PerformSummationOnCalculator(UIItemContainer mainWindow)
        {
            mainWindow.Get<Button>(SearchCriteria.ByText("1")).Click();
            mainWindow.Get<Button>(SearchCriteria.ByText("2")).Click();
            mainWindow.Get<Button>(SearchCriteria.ByText("3")).Click();
            mainWindow.Get<Button>(SearchCriteria.ByText("4")).Click();
            mainWindow.Get<Button>(SearchCriteria.ByText("Add")).Click();
            mainWindow.Get<Button>(SearchCriteria.ByText("5")).Click();
            mainWindow.Get<Button>(SearchCriteria.ByText("6")).Click();
            mainWindow.Get<Button>(SearchCriteria.ByText("7")).Click();
            mainWindow.Get<Button>(SearchCriteria.ByText("8")).Click();
            //Button with text as +(for sum)
            //Read button to get the result
            mainWindow.Get<Button>(SearchCriteria.ByText("Equals")).Click();

            //Get the result
            var resultLable = mainWindow.Get<Label>(SearchCriteria.ByAutomationId("150"));
            var result = resultLable.Text;
            Assert.That(result, Is.EqualTo("6912"));
        }*/
    }
}