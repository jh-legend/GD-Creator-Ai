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

*Last updated: 2025-07-22*
