package app.organicmaps;

import android.content.Intent;

import androidx.annotation.NonNull;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel;

/**
 * España Outdoor's primary Android activity.
 *
 * Flutter owns the application experience; Organic Maps is opened as a native
 * activity in the same Android application and APK when the user enters Map.
 */
public final class EspanaOutdoorActivity extends FlutterActivity
{
  private static final String CHANNEL = "es.espanaoutdoor/organic_maps";

  @Override
  public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine)
  {
    super.configureFlutterEngine(flutterEngine);

    new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL)
        .setMethodCallHandler((call, result) -> {
          if (!"openMap".equals(call.method))
          {
            result.notImplemented();
            return;
          }

          try
          {
            // Follow Organic Maps' own startup path so its map/resource state is initialized correctly.
            final Intent intent = MwmActivity.createShowMapIntent(this, null);
            startActivity(intent);
            result.success(true);
          }
          catch (Exception exception)
          {
            result.error(
                "organic_maps_unavailable",
                "Could not open the native Organic Maps screen.",
                null);
          }
        });
  }
}
