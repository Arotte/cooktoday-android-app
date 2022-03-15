# start_cooktoday_env.ps1
# =======================
#
# This little Windows PowerShell script will start the environment
# that is used to develop the CookToday system.
# 
# The script does the following:
#   - Start the ADB server
#   - Open backend/frontend repos in Chrome
#   - Open a Windows Terminal for both repos
#   - Open the backend repo in VSCode
#   - Start Postman
#   - Start GitHub Desktop
#   - Start Android Studio
#
# Usage:
#   a) You can run the script from the command line by typing:
#       <location-of-script>/start_cooktoday_env.ps1
#
#   b) Or, you can add the scripts/ directory to PATH, and run
#       start_cooktoday_env.ps1

# =============================================================================
# Variables

# IMPORTANT: Change the following paths to match your system
$ANDROID_REPO_LOCAL_PATH = "D:\_uni_data\cooktoday\android_app"
$BACKEND_REPO_LOCAL_PATH = "D:\_uni_data\cooktoday\backend"
$POSTMAN_EXE_PATH = "C:\Users\thear\AppData\Local\Postman\Postman.exe"
$ADB_PATH = "D:\Users\thear\AppData\Local\Android\Sdk\platform-tools\adb.exe"
$ANDROID_STUDIO_PATH = "D:\AndroidStudio\bin\studio64.exe"
$GITHUB_DESKTOP_PATH = "C:\Users\thear\AppData\Local\GitHubDesktop\GitHubDesktop.exe"

$ANDROID_REPO_URL = "https://github.com/UoA-Software-Engineering/Alpha-21-22-FE"
$BACKEND_REPO_URL = "https://github.com/UoA-Software-Engineering/Alpha-21-22"

# =============================================================================
# Operations

# start ADB
$start_adb_cmd_output = [string] (& $ADB_PATH 'start-server' 2>&1)
echo "ADB server started"
# echo $start_adb_cmd_output

# open android repo in Chrome
[system.Diagnostics.Process]::Start("chrome", $ANDROID_REPO_URL)

# open backend repo in Chrome
[system.Diagnostics.Process]::Start("chrome", $BACKEND_REPO_URL)

# open Windows Terminal for both repos,
# open backend repo in VSCode
wt -w 0 nt -d $ANDROID_REPO_LOCAL_PATH --title "CookToday Android Repo"`; new-tab -p "Windows PowerShell" --title "CookToday Backend Repo" -d $BACKEND_REPO_LOCAL_PATH cmd /k "code ./"

# start Postman
& $POSTMAN_EXE_PATH

# start GitHub Desktop
& $GITHUB_DESKTOP_PATH

# start Android Studio
& $ANDROID_STUDIO_PATH
