# FireDroid ==[Under development]==

[![Build Status](https://travis-ci.org/ugurcany/FireDroid.svg?branch=develop)](https://travis-ci.org/ugurcany/FireDroid)

**FireDroid** is an architectural framework for **Android** apps that use **Firebase** services. The framework takes on the burden of integrating Firebase services to your app and helps you avoid boilerplate code.

## Setup

**Step 1.** Add the framework dependency ==(not released yet)== to your app level `build.gradle` file

**Step 2.** Add `apply plugin: 'com.google.gms.google-services'` at the end of your app level `build.gradle` file

**Step 3.** Add the dependency `classpath 'com.google.gms:google-services:3.1.1'` to your project level `build.gradle` file

**Step 4.** Put the `google-services.json` file (provided by Firebase) under your app module directory