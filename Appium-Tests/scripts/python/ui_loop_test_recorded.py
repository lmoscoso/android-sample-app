from appium import webdriver
import time
import unittest
import os

success = True

desired_caps = {'appium-version' : '1.0',
                'platformName' : 'Android',
                'platformVersion' : '5.0.1',
                'app' : os.path.abspath('/Users/luismoscoso/Source-Tree/personal-android-driving-test-app/KnowledgeTestPractice/app/build/outputs/apk/app-debug.apk'),
                'appPackage' : 'com.example.luismoscoso.knowledgetestpractice',
                'appActivity' : 'com.example.luismoscoso.knowledgetestpractice.SplashActivity',
                'deviceName' : 'Nexus_5_API_21'}

wd = webdriver.Remote('http://0.0.0.0:4723/wd/hub', desired_caps)
wd.implicitly_wait(60)

def is_alert_present(wd):
    try:
        wd.switch_to_alert().text
        return True
    except:
        return False

try:
    wd.find_element_by_xpath("//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.Button[1]").click()
    wd.find_element_by_xpath("//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.LinearLayout[2]/android.widget.RadioGroup[1]/android.widget.RadioButton[2]").click()
    wd.find_element_by_xpath("//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.LinearLayout[3]/android.widget.Button[1]").click()
    wd.find_element_by_xpath("//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.LinearLayout[3]/android.widget.Button[1]").click()
    wd.find_element_by_xpath("//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.LinearLayout[2]/android.widget.Button[2]").click()
finally:
    wd.quit()
    if not success:
        raise Exception("Test failed.")
