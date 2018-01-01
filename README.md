# FireDroid *[Under development]*

[![Build Status](https://travis-ci.org/ugurcany/FireDroid.svg?branch=develop)](https://travis-ci.org/ugurcany/FireDroid)

**FireDroid** is an architectural framework for **Android** apps that use **Firebase** services. The framework takes on the burden of integrating Firebase services to your app and helps you avoid boilerplate code.

## Getting started

**Step 1.** Add the framework dependency *(not released yet)* to your app level `build.gradle` file

**Step 2.** Add `apply plugin: 'com.google.gms.google-services'` at the end of your app level `build.gradle` file

**Step 3.** Add the dependency `classpath 'com.google.gms:google-services:3.1.1'` to your project level `build.gradle` file

**Step 4.** Put the `google-services.json` file (provided by Firebase) under your app module directory

**Step 5.** Initialize the framework inside your app's `onCreate()` method as follows:
```java
FireDroid.init(getApplicationContext());
```

## Authentication

### Setup

Initialize auth services that you want to use inside your app's `onCreate()` method as follows:
```java
FireDroid.authInitializer(LoginActivity.class)
		.google(getString(R.string.google_web_client_id)) //OPTIONAL
		.facebook(getString(R.string.facebook_app_id)) //OPTIONAL
		.twitter(getString(R.string.twitter_key), getString(R.string.twitter_secret)) //OPTIONAL
		.init();
```

* To the auth initializer, you need to pass the activity class (LoginActivity) that is responsible for login stuff in your app. This activity is forcibly displayed by the framework as long as user is not logged in.

* The keys/ids required by login processes of Google, Facebook, and Twitter are obtained from their respective dev websites. To obtain these keys/ids, follow the links below:
	* Google: https://developers.google.com/identity/sign-in/android/start-integrating?authuser=0#get_your_backend_servers_oauth_20_client_id
	* Facebook: https://developers.facebook.com/
	* Twitter: https://apps.twitter.com/

* If you want to use Google login, you need to add your app's SHA-1 fingerprint to the Firebase dashboard of your app.

* If you want to use Facebook or Twitter login, you need to add your app's Firebase OAuth redirect URI (that can be found on Firebase dashboard) to your app's dashboard page on Facebook/Twitter dev website.

* You also need to enable corresponding login methods on your app's Firebase dashboard.

### ...
