# FireDroid *[Under development]*

[![JitPack](https://jitpack.io/v/ugurcany/FireDroid.svg)](https://jitpack.io/#ugurcany/FireDroid) [![API](https://img.shields.io/badge/API-16%2B-red.svg?style=flat)](https://android-arsenal.com/api?level=16) [![Build Status](https://travis-ci.org/ugurcany/FireDroid.svg?branch=develop)](https://travis-ci.org/ugurcany/FireDroid) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-FireDroid-orange.svg?style=flat)](https://android-arsenal.com/details/1/6718)

**FireDroid** is an architectural framework for **Android** apps that use **Firebase** services. The framework takes on the burden of integrating Firebase services to your app and helps you avoid boilerplate code.

## Getting started

**Step 1.** Add the framework dependency `compile 'com.github.ugurcany:FireDroid:X.Y'` to your app level `build.gradle` file
* To replace `X.Y` with the latest release version, see the JitPack badge at the top

**Step 2.** Add `apply plugin: 'com.google.gms.google-services'` at the end of your app level `build.gradle` file

**Step 3.** Add the dependency `classpath 'com.google.gms:google-services:3.1.1'` to your project level `build.gradle` file

**Step 4.** Put the `google-services.json` file (provided by Firebase) under your app module directory

**Step 5.** Initialize the framework inside your app's `onCreate()` method as follows:
```java
FireDroid.init(getApplicationContext());
```

**Step 6.** Make sure that each `Activity` class in your app extends the `FireDroidActivity` class provided by the framework


- - -


## Authentication

### Setup

Initialize auth service together with the auth methods that you want to use inside your app's `onCreate()` method as follows:
```java
FireDroid.authInitializer()
		.google(getString(R.string.google_web_client_id)) //OPTIONAL
		.facebook(getString(R.string.facebook_app_id)) //OPTIONAL
		.twitter(getString(R.string.twitter_key), getString(R.string.twitter_secret)) //OPTIONAL
		.init();
```

* Keys/ids required by auth SDKs of Google, Facebook, and Twitter are obtained from their respective dev websites. To obtain yours, follow the links below:
	* For **Google web client id**, go to: https://console.developers.google.com/apis/credentials
	* For **Facebook app id**, go to: https://developers.facebook.com
	* For **Twitter consumer key** and **consumer secret**, go to: https://apps.twitter.com

* If you want to use **Google login**, you need to add your app's SHA-1 fingerprint to the Firebase dashboard of your app.

* If you want to use **Facebook or Twitter login**, you need to add your app's Firebase OAuth redirect URI (that can be found on Firebase dashboard) to your app's dashboard page on Facebook/Twitter dev website.

* You also need to enable corresponding login methods on your app's Firebase dashboard.

### How to perform login

1. Have your **login activity** (the activity that has login buttons, etc.) implement the `LoginListener` interface
	- This will add two methods to the activity: `onLoginStarted()` and `onLoginCompleted(isSuccessful)`. It is obvious when these methods are triggered :)
	- You can do whatever you want inside these methods. What is recommended is blocking UI (showing dialog, etc.) when login is started; unblocking UI and finishing login activity when login is completed.

2. Add your **login button(s)** to the layout of login activity
	- On Google login button click, you simply call: `FireDroid.auth().logInWithGoogle();`
	- On Facebook login button click, you simply call: `FireDroid.auth().logInWithFacebook();`
	- On Twitter login button click, you simply call: `FireDroid.auth().logInWithTwitter();`

### How to perform logout

1. Have your **logout activity** (the activity that has logout button, etc.) implement the `LogoutListener` interface
	- This will add two methods to the activity: `onLogoutStarted()` and `onLogoutCompleted()`. It is obvious when these methods are triggered :)

2. Add your **logout button** to the layout of logout activity
	- On logout button click, you simply call: `FireDroid.auth().logOut();`


- - -


## Realtime Database

### Setup

Initialize realtime database service inside your app's `onCreate()` method as follows:
```java
FireDroid.dbInitializer()
		.diskPersistence(isEnabled)
		.init();
```

* To enable or disable the persistence of cached data and pending write operations in disk (not only in memory), simply add the `diskPersistence(isEnabled)` call to the init chain.

### How to perform simple database operations

Before performing simple database operations, have your activity implement the `DbOperationListener` interface. This will add two methods to the activity: `onDbOperationSuccessful(operationId, data)` and `onDbOperationFailed(operationId, exception)`. One of them is triggered, depending on the result of the operation that you execute.

Simple database operations are as follows:

* To **write** data to a path, you simply call:
```java
FireDroid.db().writeTo(operationId, "path/to/data", dbObject);
```

* To **push** data under a path, you simply call:
```java
FireDroid.db().pushUnder(operationId, "path/to/data-list", dbObject);
```

* To **read** data from a path, you simply call:
```java
FireDroid.db().readFrom(operationId, "path/to/data", DbObject.class);
```

### How to subscribe to data changes

...

### How to subscribe to child data changes

...

### About `FireDbObject`

You can have the class of the data object (that you want to write to database) extend the `FireDbObject` class in order to add `timestamp` field besides the fields inside your object.


- - -


### More coming soon...