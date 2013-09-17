android-multiprocess
====================

android:multiprocess question on StackOverflow.

## Big picture
I have two Android application, `Host` and `Guest`. I am trying to run an activity from `Guest` such that it immediately opens an activity in `Host`. The intention is that launching the `Guest` app should run in the `Host`, but appear to be running as the `Guest`.

This question is about getting that `Host` activity to run in the process belonging to `Host`.

(_why_ I want to do this is probably out of scope for this question, but it's definitely something I want to do).

## What I have tried before
To start with, it was thought that process separation from the rest of the `Host` activities was enough.

To do this, the process was named in the `<activity>` element in the manifest.

    <activity android:name=".MainHostActivity" android:process="host.process" />

For two reasons, this proved to be problematic:

 * it kind've worked, but it gets very hacky and messy in the face of multiple guests.
 * the standard Android tools to enable the user to manage the apps don't work (e.g. swiping away the app from the Recent Apps List).
 * the process names have to be known at compile time.

## `android:multiprocess`

Looking at the AndroidManifest.xml documentation, I have found [this activity attribute][1] which seems helpful:

> `android:multiprocess`
>
> Whether an instance of the activity can be launched into the process of the component that started it — `true` if it can be, and `false` if not. The default value is `false`.
>
> Normally, a new instance of an activity is launched into the process of the application that defined it, so all instances of the activity run in the same process. However, if this flag is set to `true`, instances of the activity can run in multiple processes, allowing the system to create instances wherever they are used (provided permissions allow it), something that is almost never necessary or desirable.

My reading of this documentation is that this could be what I am looking for. I would prefer it to say "the activity _is_ launched" rather than "the activity _can be_ launched" "into the process of the component that started it".

Unfortunately, this doesn't seem to be the case. Launching `Host` activity from `Guest` always ends up with different processes, as measured by `android.os.Process.myPid()`.

I have put up a [pair of dummy applications][2] which should show what I am trying to do:

 * install `Host`.
 * install and run `Guest`
 * The activity launched will have a number (the pid of the guest) and three buttons. Clicking on either of the first two should launch a second activity. The second activity should show the same number as the first, and `true`, which shows that the host and guest are running in the same process.

Unfortunately, this does not appear to be the case.

### TL;DR
How do I get the activities from the different activities to run in the same process owned by the the caller application?

 * [Github link to test code][3]
 * [Stackoveflow question][4]


  [1]: https://developer.android.com/guide/topics/manifest/activity-element.html#multi
  [2]: https://github.com/jhugman/android-multiprocess
  [3]: https://github.com/jhugman/android-multiprocess
  [4]: http://stackoverflow.com/questions/18832542/one-android-applications-activity-running-in-the-process-of-another-what-does