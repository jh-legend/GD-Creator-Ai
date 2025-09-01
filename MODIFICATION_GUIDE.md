# How to Modify Your Android App

This guide provides step-by-step instructions on how to modify common elements of your Android application, such as the app name, package name, logo, and theme.

---

## 1. Changing the App Name

The app name is what users see on their device's home screen and in the app drawer.

1.  **Open `strings.xml`:** Navigate to `app/src/main/res/values/strings.xml`.
2.  **Find the `app_name` string:** You will see a string resource named `app_name`.
    ```xml
    <string name="app_name">GD Creator</string>
    ```
3.  **Change the value:** Replace `"GD Creator"` with your desired app name.
    ```xml
    <string name="app_name">My New App Name</string>
    ```
4.  **Rebuild the project:** The new app name will be applied.

---

## 2. Changing the Package Name (Application ID)

Changing the package name is a more involved process and should be done carefully, as it uniquely identifies your app on the device and in the Google Play Store.

1.  **Open `app/build.gradle`:** Navigate to the `build.gradle` file in your `app` module.
2.  **Find `applicationId`:** Inside the `defaultConfig` block, you will find the `applicationId`.
    ```groovy
    defaultConfig {
        applicationId "com.liveinaura.gdcreator"
        // ...
    }
    ```
3.  **Change the `applicationId`:** Replace `"com.liveinaura.gdcreator"` with your new package name.
    ```groovy
    applicationId "com.yourcompany.newpackagename"
    ```
4.  **Sync Gradle:** Click the "Sync Now" button that appears in the top right corner of the `build.gradle` file editor.
5.  **Refactor Your Package Structure (Important):**
    *   In the Project view (select "Project" from the dropdown, not "Android"), navigate to `app/src/main/java`.
    *   Right-click on your old package name (`com/liveinaura/gdcreator`) and select `Refactor > Rename`.
    *   Choose "Rename package" and enter your new package name.
    *   Android Studio will find all usages and update them.
6.  **Update `google-services.json`:** If you are using Firebase, you will need to update your `google-services.json` file with the new package name. You will need to create a new app in your Firebase project with the new package name and download the new `google-services.json` file.
7.  **Clean and Rebuild:** Go to `Build > Clean Project` and then `Build > Rebuild Project`.

---

## 3. Changing the App Logo

The app logo is the icon that represents your app.

1.  **Prepare your new logo images:** You will need your logo in different sizes to support various screen densities. A good practice is to have versions for `mdpi`, `hdpi`, `xhdpi`, `xxhdpi`, and `xxxhdpi`.
2.  **Use the Image Asset Studio:**
    *   Right-click on the `app` folder in the Project view and select `New > Image Asset`.
    *   In the "Asset Type" dropdown, select "Launcher Icons (Adaptive & Legacy)".
    *   In the "Foreground Layer" and "Background Layer" tabs, you can specify your new logo image and a background color or image.
    *   Adjust the size and shape of your logo as needed.
    *   Click "Next" and then "Finish". Android Studio will automatically generate the necessary icon files in the `res/mipmap-*` directories.
3.  **Verify the `AndroidManifest.xml`:**
    *   Open `app/src/main/AndroidManifest.xml`.
    *   Make sure the `android:icon` and `android:roundIcon` attributes in the `<application>` tag are pointing to your new mipmap resource (e.g., `@mipmap/ic_launcher`).

---

## 4. Changing the App Theme and Colors

You can customize the look and feel of your app by changing its theme and colors.

1.  **Open `colors.xml`:** Navigate to `app/src/main/res/values/colors.xml`. Here you can define the colors used throughout your app.
    ```xml
    <color name="colorPrimary">#6200EE</color>
    <color name="colorPrimaryDark">#3700B3</color>
    <color name="colorAccent">#03DAC5</color>
    ```
2.  **Open `themes.xml`:** Navigate to `app/src/main/res/values/themes.xml` (or `styles.xml` in older projects). This file defines the themes for your app.
    ```xml
    <style name="AppTheme" parent="Theme.MaterialComponents.DayNight.NoActionBar">
        <!-- Customize your theme here. -->
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
    </style>
    ```
3.  **Modify the colors and theme:** You can change the hex codes in `colors.xml` or create new color resources and reference them in your theme.

---

## 5. Managing Dependencies

You can add or update libraries (dependencies) in your `app/build.gradle` file.

1.  **Open `app/build.gradle`:** Navigate to the `build.gradle` file in your `app` module.
2.  **Find the `dependencies` block:** This block lists all the libraries your app uses.
    ```groovy
    dependencies {
        implementation 'androidx.appcompat:appcompat:1.4.1'
        implementation 'com.google.android.material:material:1.5.0'
        // ...
    }
    ```
3.  **Add or update a dependency:** To add a new library, you can add a new `implementation` line. To update a library, you can change its version number.
4.  **Sync Gradle:** After making changes, click the "Sync Now" button.

By following these steps, you can modify the most common aspects of your Android application. Always remember to back up your project before making significant changes. Happy coding!
