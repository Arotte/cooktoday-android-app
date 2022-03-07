:: TODO: MAKE IT GENERIC (only works for Aron's machine)
:: 
:: This script start the development
:: environment for Windows users.
::
:: It:
:: - Starts the Android Debug Bridge
:: - Starts Android Studio
:: - cd's to the project directory


:: Start Android Debug Bridge
D:\Users\thear\AppData\Local\Android\Sdk\platform-tools\adb.exe start-server

:: Start Android Studio
START "" "D:\AndroidStudio\bin\studio64.exe"

:: cd to the project directory
cd "D:\_uni_data\cooktoday\android_app\"