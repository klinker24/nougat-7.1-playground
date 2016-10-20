package xyz.klinker.nougatplayground;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import xyz.klinker.nougatplayground.util.DensityUtil;
import xyz.klinker.nougatplayground.util.ImageUtil;

public class DynamicShortcutHelper {

    private Context context;
    private ShortcutManager manager;

    @SuppressWarnings("WrongConstant")
    public DynamicShortcutHelper(Context context) {
        this.context = context;
        manager = (ShortcutManager) context.getSystemService(Context.SHORTCUT_SERVICE);
    }

    public void buildDynamicShortcuts(String[] titles) {
        buildDynamicShortcuts(Arrays.asList(titles));
    }

    public void buildDynamicShortcuts(List<String> titles) {
        List<ShortcutInfo> infos = new ArrayList<>();

        for (String title : titles) {
            Intent messenger = new Intent(context, MainActivity.class);
            messenger.setAction(Intent.ACTION_VIEW);

            // example of a web link that you can open in your app
            // should probably be some kind of ID or something representing the shortcut you clicked
            messenger.setData(Uri.parse("https://klinkerapps.com/" + title.toLowerCase(Locale.US).replace(" ", "_")));

            Set<String> category = new HashSet<>();
            category.add("android.shortcut.conversation");

            ShortcutInfo info = new ShortcutInfo.Builder(context, title)
                    .setIntent(messenger)
                    .setRank(infos.size())
                    .setShortLabel(title)
                    .setCategories(category)
                    .setIcon(getIcon())
                    .build();

            infos.add(info);
        }

            manager.setDynamicShortcuts(infos);
    }

    private Icon getIcon() {
        Bitmap color = Bitmap.createBitmap(DensityUtil.toDp(context, 148), DensityUtil.toDp(context, 148), Bitmap.Config.RGB_565);
        color.eraseColor(Color.BLUE);
        color = ImageUtil.clipToCircle(color);

        return Icon.createWithBitmap(color);
    }
}
