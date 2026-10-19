package app.organicmaps;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import app.organicmaps.bookmarks.BookmarkCategoriesActivity;
import app.organicmaps.downloader.DownloaderActivity;

/**
 * España Outdoor entry point built on top of Organic Maps' native map and navigation stack.
 *
 * Keep this screen honest about integrations that are not wired to production services yet.
 * The map, offline downloads, bookmarks and tracks remain the existing Organic Maps features.
 */
public final class EspanaOutdoorActivity extends AppCompatActivity
{
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    ScrollView scrollView = new ScrollView(this);
    LinearLayout content = new LinearLayout(this);
    content.setOrientation(LinearLayout.VERTICAL);
    int padding = dp(20);
    content.setPadding(padding, padding, padding, padding);
    scrollView.addView(content);
    setContentView(scrollView);

    TextView title = new TextView(this);
    title.setText(R.string.espana_outdoor_title);
    title.setTextSize(28);
    title.setTypeface(null, android.graphics.Typeface.BOLD);
    content.addView(title);

    TextView introduction = new TextView(this);
    introduction.setText(R.string.espana_outdoor_intro);
    introduction.setTextSize(16);
    LinearLayout.LayoutParams introParams =
        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                      LinearLayout.LayoutParams.WRAP_CONTENT);
    introParams.topMargin = dp(8);
    introParams.bottomMargin = dp(24);
    content.addView(introduction, introParams);

    addSectionTitle(content, R.string.espana_outdoor_navigation_section);
    addActionButton(content, R.string.espana_outdoor_open_map, view -> finish());
    addActionButton(content, R.string.espana_outdoor_offline_maps, view -> {
      startActivity(new Intent(this, DownloaderActivity.class));
      finish();
    });
    addActionButton(content, R.string.espana_outdoor_routes_tracks, view -> {
      BookmarkCategoriesActivity.start(this);
      finish();
    });

    addSectionTitle(content, R.string.espana_outdoor_services_section);
    TextView pendingServices = new TextView(this);
    pendingServices.setText(R.string.espana_outdoor_services_pending);
    pendingServices.setTextSize(15);
    content.addView(pendingServices);

    TextView attribution = new TextView(this);
    attribution.setText(R.string.espana_outdoor_attribution);
    attribution.setTextSize(12);
    LinearLayout.LayoutParams attributionParams =
        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                      LinearLayout.LayoutParams.WRAP_CONTENT);
    attributionParams.topMargin = dp(28);
    content.addView(attribution, attributionParams);
  }

  private void addSectionTitle(LinearLayout parent, int textRes)
  {
    TextView title = new TextView(this);
    title.setText(textRes);
    title.setTextSize(19);
    title.setTypeface(null, android.graphics.Typeface.BOLD);
    LinearLayout.LayoutParams params =
        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                      LinearLayout.LayoutParams.WRAP_CONTENT);
    params.topMargin = dp(12);
    params.bottomMargin = dp(10);
    parent.addView(title, params);
  }

  private void addActionButton(LinearLayout parent, int textRes, View.OnClickListener listener)
  {
    Button button = new Button(this);
    button.setText(textRes);
    button.setAllCaps(false);
    button.setOnClickListener(listener);
    LinearLayout.LayoutParams params =
        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                      LinearLayout.LayoutParams.WRAP_CONTENT);
    params.bottomMargin = dp(8);
    parent.addView(button, params);
  }

  private int dp(int value)
  {
    return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
  }
}
