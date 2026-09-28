# Setting up your editor

**When programming, there are 3 options:**

- Codespaces via a web browser
- Codespaces via VS Code
- Docker via VS Code

## Codespaces

**Both codespace options start with the same initial steps:**

1. Navigate to the repo for this years code on github.com
2. Click the `...` under Code>Codespaces
   ![... under Code>Codespaces](./uploads/Codespace%20Options.png)
3. Click `New with options...`
   ![New with options...](./uploads/New%20With%20Options.png)
4. Change `Machine Type` to `4-Core`
   ![Machine Type Dropdown](./uploads/Machine%20Type.png)
5. Click `Create Codespace`
   _This will open up a new tab and begin setting up your codespace. This can take a little while the very first time. If you feel like nothing is happening, feel free to reload the page. This will sometimes cause it to show more up-to-date information_
6. Once the codespace is created, it may open in restricted mode. This is an intentional feature, but needs to be disabled to begin programming. If you see `Restricted Mode is intended for safe code browsing. Trust this folder to enable all features`, then follow steps 7 & 8 below. If not, skip to step 9.
7. Next to the notice at the top of your screen, click `Manage`
   ![Restricted mode notice](./uploads/Restricted%20Mode%20Notice.png)
8. This will open an editor tab with two options. Click `Trust`
   ![Restricted mode trust](./uploads/Restricted%20Mode%20Trust.png)
9. At this point, you will notice a lot of activity on your bottom bar. The key thing to watch is the `Java` status. Here is an example of what that may look like:
   ![Java Status](./uploads/Java%20Status.png)
10. Once this displays ready, you are ready to start coding!
    ![Java Ready](./uploads/Java%20Ready.png)

### Recommended next steps for codespaces:

**One of the things we did during setup was changes the `Machine Type` to 4-core. This significantly speeds up the initial setup. However, once setup is complete it is no longer necessary, and will use up quotas faster. It is recommended to follow the steps below to change it back to `2-Core`:**

1. Go back to the repo for this years code on github.com
2. Click `...` next to your newly created codespace under Code>Codespaces
   ![Codespace Options](./uploads/New%20Codespace%20Options.png)
3. Click `Change Machine Type`
   ![Change machine type](./uploads/Change%20Machine%20Type.png)
4. Select `2-Core` and click `Update Codespace`
   ![Updated machine type](./uploads/Update%20Machine%20Type.png)
5. Done! It may not take effect until the codespace has been turned off and then turned back on, but that will happen automatically when you stop coding

**For more info on codespaces, see: https://docs.github.com/en/codespaces/about-codespaces/what-are-codespaces**

### Codespaces via VS Code

**Now that your codespace is created, you can connect to it via vscode**

1. Download and install VSCode on your computer at https://code.visualstudio.com/download
2. Navigate to https://github.com/settings/codespaces
3. Scroll down to `Editor Preference`
4. Select `Visual Studio Code`
   ![Editor preference](./uploads/Editor%20Preference.png)
5. Go back to the repo for this years code on github.com
6. Click on the name of your codespace
   ![Open your codespace](./uploads/Open%20Codespace.png)
7. This will open vscode and prompt you to install the codespace extension if you do not already have it installed and open your codespace
8. You're good to go!

## Docker via VSCode

**This is a more advanced option, and as such will require more independent setup/troubleshooting. This method originally worked on Apple Silicon Macs, but starting the end of 2026 Android Gradle blocked this**

1. First, go to https://www.docker.com/products/docker-desktop/ and download Docker Desktop to your computer.
2. Download and install VSCode on your computer at https://code.visualstudio.com/download
3. Download the devcontainers extension - https://marketplace.visualstudio.com/items?itemName=ms-vscode-remote.remote-containers
4. Clone the repo for this years code from github.com
5. You should get a notification that a devcontainer is detected, but if not open your command pallet (cmd+shift+p) and run `Reopen in container`
6. Wait for the Java extension to be ready like the options above
7. Once that's done, you should be good to go!
