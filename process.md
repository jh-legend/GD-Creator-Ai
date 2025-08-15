# GD Creator AI App - Development Process

This document tracks the entire **GD Creator AI App** development journey, including prompts, tools, integrations, and resolutions. It is intended to be updated continuously and used by Jules.ai for auto-task management and workflow understanding.

---

## ✅ Project Overview

* **App Name:** GD Creator AI
* **Goal:** Allow users to generate Bangla General Diary (GD) documents using AI and export as printable PDF.
* **Platform:** Android (Java + XML)

---

## 🔧 Core Functionalities

1. **Voice/Text Input for GD Description**
2. **AI-generated Complaint Draft using ChatGPT API**
3. **Bangla Font Support (Kalpurush)**
4. **PDF Export using iText 7**
5. **Download Folder Auto-Save (Scoped Storage)**
6. **Firebase Auth (Planned)**

---

## 📦 Used Libraries & APIs

* `ChatGPT API` - for dynamic content generation
* `iText 7` - for PDF generation
* `Firebase` - for future auth & storage
* `Kalpurush.ttf` - for Bangla font rendering
* `Android PrintManager` - for future print support

---

## 🧠 Prompts Used in Jules.ai

* "Generate a GD text using OpenAI for a lost mobile with IMEI and location"
* "Add a scrollable input form for incident data"
* "Create a PDF from dynamic text using iText 7 and Kalpurush font"
* "Fix iText errors related to missing packages"
* "Suggest dependencies for iText 7 and setup Bengali font"
* "Fix scoped storage for Android Q+ while saving PDF"

---

## 🐞 Errors Encountered & Fixes

### ❌ iText Errors (Missing packages)

**Problem:**

```
package com.itextpdf.kernel.font does not exist
package com.itextpdf.text does not exist
```

**Fix:**

* Removed mixed imports from iText 5 & 7.
* Added this dependency:

```gradle
implementation 'com.itextpdf:itext7-core:7.1.15'
```

* Rewrote PDF generation with iText 7.

### ❌ Keystore Path Error

**Problem:** `Keystore file does not exist: ~/.android/debug.keystore`
**Fix:** Used full Windows path:

```cmd
keytool -list -v -keystore "C:\Users\hp\.android\debug.keystore" -alias androiddebugkey -storepass android -keypass android
```

---

## 🗃️ File Structure (Important Locations)

```
assets/fonts/kalpurush.ttf
utils/PdfGenerator.java
activities/MainActivity.java
layout/activity_main.xml
```

---

### ❌ Keyboard Overlapping UI

**Problem:** `BottomNavigationView` remains visible and floats over the keyboard in `ChatFragment`.

**Fix:**

* Implemented `ViewTreeObserver.OnGlobalLayoutListener` in `ChatFragment` to detect keyboard visibility.
* When the keyboard is open, the `BottomNavigationView` is hidden.
* When the keyboard is closed, the `BottomNavigationView` is made visible again.
* Exposed the `BottomNavigationView` from `MainActivity` for `ChatFragment` to access.

---

## 🧩 Next Planned Features

* [ ] Firebase Auth integration
* [ ] Chat history saving (Firestore)
* [ ] PrintManager integration
* [ ] User Profile & Settings screen
* [ ] Jules.ai task auto-sync for all codegen & design prompts

---

## 📝 Update Instructions (For Jules.ai)

> Jules, whenever a new screen, API call, or fix is made, update this file under respective sections. If a prompt is used, log it in the "Prompts Used" section. If an error is fixed, log it in the "Errors Encountered & Fixes" section.

---

## 🚀 Recent Changes (August 2025)

### 🔒 Secure API Key Handling & Comprehensive Logging

**Problem:** The OpenAI API key was hardcoded and logging was insufficient for debugging.

**Fix:**
*   Implemented a secure way to handle the OpenAI API key by reading it from `local.properties` and making it available through `BuildConfig`.
*   Added comprehensive logging throughout the application using consistent tags (`Auth`, `Firestore`, `OpenAI`, etc.) to facilitate debugging.
*   Created a `LOGGING_GUIDE.md` to explain how to trace logs in Android Studio.

### ⚙️ Improved Authentication Flow

**Problem:** The login and registration flows needed improvement for better user experience.

**Fix:**
*   Updated the registration flow to show a more informative dialog after sending the verification email.
*   Removed the email verification check from the login flow to allow users to log in immediately after registration.
*   Added a password visibility toggle to the password fields in both the login and registration forms.
*   Updated the password requirements UI and validation logic.

### 🐛 OpenAI API Fix & Auto-Login

**Problem:** The app was failing to get a response from the OpenAI API, and the user had to log in manually every time.

**Fix:**
*   Fixed the `build.gradle` configuration for the API key.
*   Updated the OpenAI model from `text-davinci-003` to `gpt-3.5-turbo`.
*   Enhanced error logging for API calls.
*   Implemented an auto-login feature that automatically logs the user in after they verify their email address.

---

*Last updated: 2025-08-15*
