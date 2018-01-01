# FireDroid *[Under development]*

[![Build Status](https://travis-ci.org/ugurcany/FireDroid.svg?branch=develop)](https://travis-ci.org/ugurcany/FireDroid)

**FireDroid** is an architectural framework for **Android** apps that use **Firebase** services. The framework takes on the burden of integrating Firebase services to your app and helps you avoid boilerplate code.

## Setup

**Step 1.** Add the framework dependency *(not released yet)* to your app level `build.gradle` file

**Step 2.** Add `apply plugin: 'com.google.gms.google-services'` at the end of your app level `build.gradle` file

**Step 3.** Add the dependency `classpath 'com.google.gms:google-services:3.1.1'` to your project level `build.gradle` file

**Step 4.** Put the `google-services.json` file (provided by Firebase) under your app module directory

**Step 5.** Initialize the framework inside your app's `onCreate()` method as follows:
```
FireDroid.init(getApplicationContext());
```

## Authentication

### Setup

**Step 1.** Initiliaze auth services that you want to use inside your app's `onCreate()` method as follows:
```
FireDroid.authInitializer(LoginActivity.class)
		.google(getString(R.string.google_web_client_id)) //OPTIONAL
		.facebook(getString(R.string.facebook_app_id)) //OPTIONAL
		.twitter(getString(R.string.twitter_key), getString(R.string.twitter_secret)) //OPTIONAL
		.init();
```
Here, required keys and ids you see above are obtained from respective dev/sdk websites of Google, Facebook, and Twitter.

*[ ! ] If you want to use Google login, you need to add your app's SHA-1 fingerprint to the Firebase dashboard of your app.*

**Step 2.** ...