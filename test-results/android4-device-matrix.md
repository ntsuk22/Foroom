# Android 4 — Device Matrix

Test class: `com.example.foroom.tests.ConversationTests`
Build: current debug build (Foroom Training, `com.alternator.foroom.training`), the same build on every device.

| # | Device / AVD | Android / API | Screen resolution | Type | Scenario 1 — johnWeek message | Scenario 2 — own chat question | Scenario 3 — two-account conversation |
|---|---|---|---|---|---|---|---|
| 1 | Pixel_4_API_33 (Pixel 4 profile) | Android 13 / API 33 | 1080 × 2280, 440 dpi | Emulator | Pass | Pass | Pass |
| 2 | Nexus_5X_API_34 (Nexus 5X profile) | Android 14 / API 34 | 1080 × 1920, 420 dpi | Emulator | Pass | Pass | Pass |
| 3 | Pixel_3a_API_35 (Pixel 3a profile) | Android 15 / API 35 | 1080 × 2220, 440 dpi | Emulator | Pass | Pass | Pass |

No physical device was available, so all three configurations are emulators.

## Runs per configuration

Accounts (`nino_user_a`, `nino_user_b`) and chats (`johnWeek`, `Nino Tsukhishvili`, `something`)
were prepared separately on each device by the reusable test setup, which reuses existing data
and creates only what is missing.

| # | Full class | Scenario 1 alone | Scenario 2 alone | Scenario 3 alone | Full class repeated |
|---|---|---|---|---|---|
| 1 | Pass | Pass | Pass | Pass | Pass |
| 2 | Pass | Pass | Pass | Pass | Pass |
| 3 | Pass | Pass | Pass | Pass | Pass |

Every device had already been used for earlier runs, so these runs also confirm that earlier
messages and saved sessions do not break the assertions.

## Notes

- Test environment: animations were disabled on each emulator (`window_animation_scale`,
  `transition_animation_scale`, `animator_duration_scale` = 0).
- A first Android 15 attempt used a Nexus 4 profile (768 × 1280, 320 dpi). It was replaced because
  the app's registration screen does not scroll, so its Sign Up button is off-screen at that
  size and an account cannot be registered, either manually or by the tests. That is an app
  issue on small screens, not a test failure, and no results are claimed for that device.
- Screenshots of Android Studio test results are not included: Android Studio is not installed;
  the tests were run with Gradle (`connectedDebugAndroidTest`) from VS Code.
