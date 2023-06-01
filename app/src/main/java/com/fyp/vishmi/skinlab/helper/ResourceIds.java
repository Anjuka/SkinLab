package com.fyp.vishmi.skinlab.helper;

import android.content.Context;

public class ResourceIds {

   /* public static int getNotificationResourceId(Context context, int position, List<Notification> notification){
        return context.getResources().getIdentifier(notification.get(position).
                getNotificationTemplate().get(0).getType(), "drawable", context.getPackageName());
    }

    public static int getSummaryStatResourceId(Context context, int position, List<SummaryStat> summaryStats){
        return context.getResources().getIdentifier(removeExtension(summaryStats.get(position).getIcon()),
                "drawable", context.getPackageName());
    }

    public static int getSummaryStatResourceId(Context context, String iconName){
        return context.getResources().getIdentifier(removeExtension(iconName),
                "drawable", context.getPackageName());
    }

    public static int getModeResourceId(Context context, int position, List<Mode> mode){
        return context.getResources().getIdentifier(removeExtension(mode.get(position).getIcon()), "drawable",
                context.getPackageName
                        ());
    }

    public static int getPresetResourceId(Context context, int position, List<Preset> preset){
        return context.getResources().getIdentifier(removeExtension(preset.get(position).getIcon()), "drawable",
                context.getPackageName
                        ());
    }*/

    public static int getLoadingGifResourceId(Context context){
        return context.getResources().getIdentifier("loading_gif", "drawable",
                context.getPackageName
                        ());
    }
}
