# TripBuddy 

TripBuddy is a trip budget organizer that will keep track of all your expenses in one place. 

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Configuration](#configuration)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## Overview

If you are planning on going on a trip soon and want to save some money, this is your app! You will be able
to input your expenses, organize them in different categories and see how much budget you have left for 
the rest of the trip. 

## Features

- **Expense date:** we keep track of when you inputed the expense, so that it is easier for you to remember when
you did it. 
- **Multi-currency support**: You can have your budget in any of the supported currencies. You can also input 
an expense in a different currency. Note: exchange rates are not updated. 
- **Storage system**: you won't have to input the expenses every time you use the app. The app 
keeps track of your past expenses in a json file. 

## Installation

### Prerequisites
Prerequisites: JDK 17 (use the exact version), update Intellij to the most recent version.

1. **Ensure Intellij JDK 17 is defined as an SDK**, as described [here](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk) 
-- this step is not needed if you have used JDK 17 in a previous Intellij project.
2. **Import the project _as a Gradle project_**, as described [here](https://se-education.org/guides/tutorials/intellijImportGradleProject.html).
3. **Verify the setup**: After the importing is complete, locate the `src/main/java/seedu/tripbuddy/TripBuddy.java` file, right-click it, and choose `Run TripBuddy.main()`.
You should be seeing something like this: 
```
> Task :compileJava UP-TO-DATE
> Task :processResources NO-SOURCE
> Task :classes UP-TO-DATE

> Task :seedu.tripbuddy.TripBuddy.main()
____________________________________________________________
Loaded data from file: tripbuddy_data.json
____________________________________________________________
____________________________________________________________
Welcome to TripBuddy! Type `tutorial` for a list of available commands.
____________________________________________________________
```

### Steps

1. Clone the repository:
   ```bash
   git clone https://github.com/AY2425S2-CS2113-W11-4/tp.git

