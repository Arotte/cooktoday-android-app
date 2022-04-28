[![Android CI](https://github.com/UoA-Software-Engineering/Alpha-21-22-FE/actions/workflows/android.yml/badge.svg)](https://github.com/UoA-Software-Engineering/Alpha-21-22-FE/actions/workflows/android.yml)


CookToday Android Application
================================


Table of Contents
- [CookToday Android Application](#cooktoday-android-application)
  - [Running the app](#running-the-app)
    - [From APK](#from-apk)
    - [Inside an emulator](#inside-an-emulator)
    - [On a physical Android device](#on-a-physical-android-device)
  - [Exporting an installable APK](#exporting-an-installable-apk)
  - [Dependencies](#dependencies)
  - [Testing](#testing)
  - [Contributing](#contributing)
  - [Acknowledgements](#acknowledgements)
  - [Developers](#developers)


CookToday is the digital side-chef your kitchen needs, packed with cool and powerful features that are hard to miss. The user interface is intuitive and easy to use, designed with inclusivity and yummy-ness in mind. It features:
- Personalized recipe recommendations
- A cookbook allowing users to save recipes
- Ability to view and add recipes
- Recipe import into the app from any properly formatted website


This repository (https://github.com/UoA-Software-Engineering/Alpha-21-22-FE) contains the source of the CookToday Android mobile application.

*Note:* The application was written in Java using Android Studio. Thus, the guides in this manual are heavily reliant on a functioning Android Studio on the developer's computer.

Running the app
---------------

### From APK

We have provided a pre-built APK that should be installable and runnable on any Android smartphone.

The operating system of the device used must be relatively new. If not, some UI elements might not render correctly.

The API is accessible under `/prebuilt/apk/...` and is called `TODO`.

Upon downloading the APK to a suitable device, tapping on it should automatically start the installation process.

### Inside an emulator

The app can also be run inside an Android emulator. Virtually any Android emulator can be used for this task, however the most tested approach is using Android Studio's built-in emulator feature.

The steps to run CookToday in Android Studio's emulator are the following:
   1. Click on "Device Manager" on the right sidebar.
   2. Click on "Create Device".
   3. Select "Phone" as a category, then select a phone from the list. Any "Pixel" device should do, we used "Pixel 4".
   4. Click on "Next".
   5. Select either "S" or "R" from the list that appears. If you didn't do this process before, Android Studio will start downloading the selected system image.
   6. Click on "Next".
   7. Click on "Finish".

After these steps your virtual device should be up a running. If not, follow these steps:
   1. Click on "Device Manager" on the right sidebar.
   2. Click on the appearing green launch icon of your previously created device.

Once the virtual device is up and running, you should be able to start the CookToday application by clicking the green start button on the top right corner of Android Studio.

### On a physical Android device

If you don't want to install CookToday on your physical device from the provided APK, you can install, start and debug the application directly thourgh Android Studio.

**Enabling developer options**

If you don't have developer options enabled on your device, follow these steps to enable it:
   1. Go to Settings > About Phone.
   2. Tap Software Info > Build Number.
   3. Tap Build Number seven times. After the first few taps, you should see the steps descreasing. Continue tapping until you get a notification that developer options are now enabled.

**Enabling USB debugging**

Once the developer options have been enabled, you can enable USB debugging by:
   1. Go to Settings > Developer options.
   2. Enable USB debugging.

**Running CookToday**

If you have developer options and USB debugging enabled on your Android device, then running the app is as simple as:
   1. Connect the device to your computer with a (USB) cable.
   2. Android Studio should automatically recognize it. Check the "Device Manager" by navigating to the "Physical" tab.
   3. Run the application by selecting your device from the drop-down list on the top righ corner, then hitting the play button.




Exporting an installable APK
------------


Dependencies
-------------

Project dependencies are automatically managed by Gradle. If they are not present on the developer's machine, Gradle will download them.

For sake of completeness, the 3rd party dependencies of CookToday are listed below in Gradle format.

```gradle
    // Toggle Button Group: creates a toggle button group
    // Source: https://github.com/Bryanx/themed-toggle-button-group
    implementation 'nl.bryanderidder:themed-toggle-button-group:1.4.1'

    // Picasso: loads images from web URLs
    // Source: https://square.github.io/picasso/
    implementation 'com.squareup.picasso:picasso:2.8'

    // Retrofit, Gson, OkHttp3 are needed for server communication
    implementation 'com.squareup.retrofit2:retrofit:2.7.1'
    implementation 'com.squareup.retrofit2:converter-gson:2.7.1'
    implementation 'com.squareup.okhttp3:logging-interceptor:4.4.0'

    // Shimmer: https://facebook.github.io/shimmer-android/
    // for shimmering loading effect
    implementation 'com.facebook.shimmer:shimmer:0.5.0'
```

Testing
------------

Contributing
----------------

Branches

* `develop`: this is our main branch that we use for development
* `initial_code`: this branch contains the initial app code created by Zixin and Dimana in the first semester


Development Setup

**If you already have a copy of this repository on your machine:**

1. Navigate to your project folder `<your-path>` in a terminal.

2. Download and checkout the `develop` branch:
   ```sh
   git fetch
   git branch
   git checkout develop
   git pull
   ```

**If you don't have a copy of this repository on your machine:**

1. Find a convenient location on your computer (eg. `D:/cooktoday`). The location path must NOT contain special characters and spaces (**OK:** `D:/cooktoday`, **DON'T:** `D:/CS2322 Software Engineering/CookToday`). I'll refer to this path as `<your-path>`.

2. Open up a terminal/bash/PowerShell/Command Line/whathever prompt. Navigate to `<your-path>` (`cd <your-path>`).

3. Clone this repository. Don't download it as a zip, instead: 1. Click the "Code" button on the top right corner of this page; 2. Select "SSH"; 3. Copy the link provided; 4. Go back to your terminal and type:
   ```sh
   git clone git@github.com:UoA-Software-Engineering/Alpha-21-22-FE.git .
   ```
   This will download the repository to your current working directory (which should be `<your-path>`).

4. Checkout the `develop` branch. Git will probably set the default branch to `main`, but we use the `develop` branch for development. 
   ```sh
   git fetch
   git branch
   git checkout develop
   ``` 

Development Practices

TODO

Working on your feature branch

1. **Create a feature branch**

TODO

2. **Push your changes to GitHub**

**If the `develop` branch has been changed:**

```sh

git status

# 1. commit changes
git add .
git commit -m "your informative commit message"

gitk
git branch

# 2. update develop branch
git checkout develop
git pull

gitk
git branch
git status

# 3. rebase feature branch
git checkout your_feature_branch
git rebase develop

gitk
git status

# 4. push changes to github
git push origin yout_feature_branch
```

**If the the develop branch hasn't been changed:**


```sh
git status
git branch # check if you are in the correct branch
# NOTE: if you are not on your feature branch, check it out with `git checkout your-feature-branch`
git add .
git commit -m "your informative commit message"
git push origin your-feature-branch
```


Acknowledgements
-------------


Developers
-------------