package com.novalux.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.GridLayout;
import java.util.List;

public class MainActivity extends Activity {

    int bg = Color.rgb(12, 15, 30);
    int accent = Color.rgb(130, 110, 255);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(bg);
        getWindow().setNavigationBarColor(bg);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(20, 28, 20, 12);
        root.setBackgroundColor(bg);

        TextView brand = new TextView(this);
        brand.setText("N O V A   L U X");
        brand.setTextColor(Color.WHITE);
        brand.setTextSize(25);
        brand.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        root.addView(brand);

        TextView subtitle = new TextView(this);
        subtitle.setText("YOUR SPACE. YOUR STYLE.");
        subtitle.setTextColor(accent);
        subtitle.setTextSize(11);
        subtitle.setPadding(0, 8, 0, 22);
        root.addView(subtitle);

        TextView heading = new TextView(this);
        heading.setText("YOUR APPS");
        heading.setTextColor(Color.LTGRAY);
        heading.setTextSize(13);
        heading.setPadding(0, 8, 0, 14);
        root.addView(heading);

        android.widget.ScrollView scroll = new android.widget.ScrollView(this);
        GridLayout grid = new GridLayout(this);
        grid.setColumnCount(3);

        PackageManager pm = getPackageManager();
        Intent query = new Intent(Intent.ACTION_MAIN);
        query.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> apps = pm.queryIntentActivities(query, 0);

        for (ResolveInfo app : apps) {
            final String pkg = app.activityInfo.packageName;
            final String cls = app.activityInfo.name;

            LinearLayout item = new LinearLayout(this);
            item.setOrientation(LinearLayout.VERTICAL);
            item.setGravity(Gravity.CENTER);
            item.setPadding(5, 12, 5, 12);

            TextView icon = new TextView(this);
            icon.setText(app.loadIcon(pm) == null ? "◈" : "");
            icon.setGravity(Gravity.CENTER);
            icon.setTextSize(28);
            icon.setBackground(round(accent, 28));
            icon.setCompoundDrawables(null, null, null, null);
            android.graphics.drawable.Drawable drawable = app.loadIcon(pm);
            drawable.setBounds(0, 0, 88, 88);
            icon.setCompoundDrawables(null, drawable, null, null);
            item.addView(icon, new LinearLayout.LayoutParams(88, 88));

            TextView label = new TextView(this);
            label.setText(app.loadLabel(pm));
            label.setTextColor(Color.WHITE);
            label.setTextSize(11);
            label.setGravity(Gravity.CENTER);
            label.setMaxLines(2);
            label.setPadding(0, 8, 0, 0);
            item.addView(label);

            item.setOnClickListener(v -> {
                Intent launch = new Intent(Intent.ACTION_MAIN);
                launch.setClassName(pkg, cls);
                launch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(launch);
            });

            grid.addView(item, new ViewGroup.LayoutParams(
                getResources().getDisplayMetrics().widthPixels / 3 - 22, 132));
        }

        scroll.addView(grid);
        root.addView(scroll, new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));

        TextView footer = new TextView(this);
        footer.setText("NOVA LUX   •   PREMIUM ANDROID EXPERIENCE");
        footer.setTextColor(Color.GRAY);
        footer.setTextSize(9);
        footer.setGravity(Gravity.CENTER);
        footer.setPadding(0, 14, 0, 8);
        root.addView(footer);

        setContentView(root);
    }

    GradientDrawable round(int color, int radius) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(radius);
        return d;
    }
}
