from appium import webdriver
import time
import unittest
import os


class UITests(unittest.TestCase):
    def setUp(self):
        desired_caps = {'appium-version' : '1.0',
                        'platformName' : 'Android',
                        'platformVersion' : '5.0.1',
                        'app' : os.path.abspath('/Users/luismoscoso/Source-Tree/personal-android-driving-test-app/KnowledgeTestPractice/app/build/outputs/apk/app-debug.apk'),
                        'appPackage' : 'com.example.luismoscoso.knowledgetestpractice',
                        'appActivity' : 'com.example.luismoscoso.knowledgetestpractice.SplashActivity',
                        'deviceName' : 'Nexus_5_API_21'}

        self.wd = webdriver.Remote('http://0.0.0.0:4723/wd/hub', desired_caps)
        self.wd.implicitly_wait(60)

    def tearDown(self):
        self.wd.quit()

    def test_ui_loop(self):
        try:
            # Main Menu
            self.wd.find_element_by_xpath("//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.Button[1]").click()

            # Question Fragment
            self.wd.find_element_by_xpath("//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.LinearLayout[2]/android.widget.RadioGroup[1]/android.widget.RadioButton[2]").click()
            
            self.wd.find_element_by_xpath("//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.LinearLayout[3]/android.widget.Button[1]").click()

            # Answer Explanation Fragment
            self.wd.find_element_by_xpath("//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.LinearLayout[3]/android.widget.Button[1]").click()

            # Results Fragment
            self.wd.find_element_by_xpath("//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.LinearLayout[2]/android.widget.Button[2]").click()
        except:
            self.fail("Test has failed")


if __name__ == '__main__':
    suite = unittest.TestLoader().loadTestsFromTestCase(UITests)
    unittest.TextTestRunner(verbosity=2).run(suite)

