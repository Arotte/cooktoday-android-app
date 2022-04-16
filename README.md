# CookToday Android Application

This repository contains the source of the CookToday Android mobile application.

## TODO

* integrate media upload endpoint
* update step addition in UploadActivity
* extend and update this readme with: env setup, git practices, basic documentation, how-to run, etc

## Branches

* `develop`: this is our main branch that we use for development
* `initial_code`: this branch contains the initial app code created by Zixin and Dimana in the first semester


## Development Setup

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

## Development Practices

TODO

### Working on your feature branch

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