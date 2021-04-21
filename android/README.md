# IBM Security Verify Adaptive SDK for Android

This repository contains a sample Kotlin app which uses the IBM Security Verify Adaptive SDK for Android. The purpose of this sample app is to showcase the IBM Security Verify Adaptive SDK for Android, and provide guidance when developing Android applications with it.

## Prerequisites

- Download the IBM Security Verify Adaptive SDK for Android from the [IBM App Exchange](https://exchange.xforce.ibmcloud.com/hub/IdentityandAccess).

- Download and unzip the Trusteer SDK for Android after [application onboarding](https://docs.verify.ibm.com/verify/docs/on-boarding-a-native-application).

## Setup

### Import the SDKs

1. Clone the sample project in this repository, and open it in Android Studio. Alternatively, launch Android Studio and open or create a Kotlin project.
2. Go to **File > New > New Module...**.
3. Select **Import .JAR/.AAR Package**, and click **Next**.
4. Under **File name**, select the location of the downloaded **tas-release.aar** file from the Trusteer SDK (see [Prerequisites](#prerequisites)).
5. Click **Finish**.
6. Repeat steps *2.* to *5.* for the downloaded **IBMAdaptiveSdk.aar** file from the IBM Security Verify Adaptive SDK (see [Prerequisites](#prerequisites)).
7. Add the following lines to the `dependencies` body of your application's **build.gradle** file:
    ```gradle
    implementation project(':tas-release')
    implementation project(':IBMAdaptiveSdk')
    implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0'
    ```

### Reference the SDK support files

1. From the downloaded Trusteer SDK, move the `SDK-S/TrusteerServiceCollection.kt` file to your Android Studio project.
   - You may need to update the `package` name at the top of this file.
2. From the downloaded Trusteer SDK, move the `default_conf.rpkg` and `manifest.rpkg` files to your Android Studio project's `assets` folder at `<YOUR_PROJECT>/app/src/main/assets`.
   - You may need to manually create this `assets` folder if it doesn't exist.
   - You will need to generate the `manifest.rpkg` file. To do this, refer to *Step 4C* in the [Manual Configuration Procedure](https://www.ibm.com/support/knowledgecenter/SS7PQ8_5.1.0/qsg/t_Configure_Your_Build_System_Android.html) documentation.
3. You can use the SDKs in-code by importing the modules:

```kotlin
import com.ibm.security.adaptivesdk.*
import com.trusteer.tas.*
```

For a detailed guide on how to implement the IBM Security Verify Adaptive SDK's interfaces, refer to the IBM Security Verify Adaptive SDK documentation.

For a sample Kotlin app using the IBM Security Verify Adaptive SDK, refer to the code in the current repository.
