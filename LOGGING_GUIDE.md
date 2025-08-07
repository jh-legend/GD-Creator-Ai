# How to Trace Logs in Android Studio

This guide will walk you through the process of viewing and filtering logs in Android Studio using the Logcat window. This is essential for debugging the application and understanding its flow.

## 1. Opening the Logcat Window

First, you need to open the Logcat window in Android Studio. You can do this in a few ways:

*   **Click the "Logcat" tab** at the bottom of the Android Studio window.
*   **Go to `View > Tool Windows > Logcat`** from the main menu.
*   **Press `Alt + 6`** (or `Cmd + 6` on Mac).

## 2. Understanding the Logcat Window

The Logcat window displays system messages and messages that you've added to your app with the `Log` class. Each message has a timestamp, a log level, a tag, and the actual message content.

Here's an example of a log message:
```
2025-08-07 08:21:46.926 D/Auth: LoginActivity:onCreate
```
*   `2025-08-07 08:21:46.926`: Timestamp
*   `D`: Log level (D for Debug)
*   `Auth`: Log tag
*   `LoginActivity:onCreate`: The log message

## 3. Filtering Logs

The Logcat window can be very noisy, with messages from the Android system and other apps. To make it easier to find the logs you're interested in, you can use filters.

### Filtering by Log Level

You can filter logs by their severity level. The available log levels are:

*   **V**: Verbose (least important)
*   **D**: Debug
*   **I**: Info
*   **W**: Warn
*   **E**: Error
*   **A**: Assert

In the Logcat window, there's a dropdown menu that lets you select the minimum log level to display. For example, if you select "Debug", you will see Debug, Info, Warn, Error, and Assert messages, but not Verbose messages.

### Filtering by Log Tag

This is the most useful way to filter logs for this project. We have used specific tags for different parts of the application. You can filter by these tags to see only the logs relevant to that part of the app.

In the search bar at the top of the Logcat window, you can type `tag:` followed by the tag name. For example:

*   To see only authentication logs, type: `tag:Auth`
*   To see only Firestore logs, type: `tag:Firestore`
*   To see only OpenAI API logs, type: `tag:OpenAI`

Here is a list of all the tags we've used in this project:

*   `Auth`
*   `Firestore`
*   `OpenAI`
*   `AI_Response`
*   `UserProfile`
*   `PDF`
*   `Lifecycle`
*   `Docs`
*   `Theme`

### Combining Filters

You can also combine filters. For example, to see only error logs for the `Auth` tag, you can select "Error" from the log level dropdown and type `tag:Auth` in the search bar.

## 4. Clearing the Logcat

To clear all the logs from the Logcat window, you can click the "Clear Logcat" button (a trash can icon) on the left side of the Logcat window.

By using these filtering techniques, you can easily trace the execution of the application and pinpoint the source of any issues. Happy debugging!
