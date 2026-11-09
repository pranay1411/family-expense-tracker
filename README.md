# Family Expense Tracker

Android-first family expense capture app.

## MVP scope
- Detect likely payment/debit SMS messages locally on the device.
- Extract amount, merchant, sender and timestamp where available.
- Ask the user to confirm before recording an expense.
- Provide Add, Edit and Ignore notification actions.
- Support quick manual entry through an optional floating bubble.
- Persist confirmed expenses locally; add family sync in a later milestone.

## Privacy and Android permissions
SMS content is sensitive. Process it locally, request access only after the user enables the feature, do not upload raw SMS by default, and never retain OTPs. Google Play restricts SMS permissions to qualifying use cases; review current Play policy before publishing. A notification-listener approach may be a better distribution option.

The floating bubble requires the user to grant Android's Display over other apps permission. Android 13+ requires notification permission.

## Development
- Kotlin
- Jetpack Compose
- JDK 17
- Android Studio

Build (once the Gradle project is added):
```bash
./gradlew assembleDebug
```

## Roadmap
- [ ] Android project skeleton and onboarding
- [ ] Local SMS transaction classifier/parser with tests
- [ ] Confirmation notification with Add/Edit/Ignore
- [ ] Room persistence and duplicate detection
- [ ] Floating quick-add bubble
- [ ] Family accounts and secure cloud sync
- [ ] Monthly summaries and statement import

This repository is the initial project setup; it is not yet a production-ready app or APK.
