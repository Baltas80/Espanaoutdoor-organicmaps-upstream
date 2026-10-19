# España Outdoor Flutter + Organic Maps — Android host integration

## Target behaviour

España Outdoor remains the primary user experience. The launcher opens the original Flutter application; entering **Mapa** opens Organic Maps' native map/downloader activity on top of it. Android Back closes that native activity and returns to the Flutter screen. Both screens and all native map data run in one installed España Outdoor APK. No separately installed Organic Maps application is required for this flow.

## Build structure

- The Android application and native Organic Maps engine remain hosted by this fork's existing Gradle application project.
- The Flutter source is pinned as the `flutter_module` Git submodule to commit `347395727341ebcb2f24ded8211400f9bd5320b4` of `Baltas80/Espana-Outdoors`. That source commit contains the existing España Outdoor `lib/`, assets and module declaration.
- `EspanaOutdoorActivity` is the launcher activity and hosts Flutter.
- The Flutter method channel `es.espanaoutdoor/organic_maps` handles `openMap`; Android calls Organic Maps' own `MwmActivity.createShowMapIntent(...)` startup path.
- Organic Maps' `MwmApplication`, C++ engine, offline maps and native activities remain in the same package/APK. The normal native attribution and licensing surfaces must remain available.
- Flutter's current PMTiles map stays as a safe fallback if the native bridge is unavailable. The bridge reports that condition rather than crashing or attempting to launch another installed application.

## Prepare the source module

From the Organic Maps fork root after initializing all submodules:

```bash
git submodule update --init --recursive
tools/android/prepare_flutter_module.sh
```

The preparation script verifies the exact Flutter source commit and invokes `flutter pub get` to generate `flutter_module/.android/include_flutter.groovy`. Gradle settings then include the generated `:flutter` project, and the application depends on it.

On Windows, use Git Bash for the preparation script. Run it once after a clean clone and again after updating the pinned Flutter module or its dependencies.

## Acceptance gates

Do not call this integrated or release-ready until all of these pass:

1. Clean recursive clone initializes the Flutter module at the pinned commit.
2. The preparation script succeeds with the declared Flutter SDK and generates the Gradle include file.
3. The Android Gradle project syncs and assembles the F-Droid Debug ARM64 variant without duplicate resources, manifests, plugin or native-library conflicts.
4. The single installed APK opens to the España Outdoor Flutter home screen.
5. Entering Mapa opens the native Organic Maps map/downloader screen; Android Back returns to the original Flutter screen without a second application launch.
6. Offline map download, restart, airplane mode, location permissions, GPS, map gestures, track/route features and native service lifecycle are tested on a physical Android device.
7. Organic Maps/OpenStreetMap attribution and third-party notices remain visible as required; Spain Outdoor-specific services are not represented as implemented until their own integrations pass tests.

## Known validation status

This branch contains the host and method-channel integration code, but no successful Android compile or physical-device validation is recorded yet. Gradle/module compatibility, duplicate native dependencies and plugin compatibility must be resolved from actual build output before a release claim.
