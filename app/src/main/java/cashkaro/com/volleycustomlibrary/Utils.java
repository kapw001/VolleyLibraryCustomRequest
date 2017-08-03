package cashkaro.com.volleycustomlibrary;

import android.animation.Animator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by yasar on 26/4/17.
 */

public class Utils {
    private static final String TAG = "Utils";
    private static ProgressDialog progressDialog;

    public static void show(Activity context, String title, String msg) {
        new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.AppTheme))
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .show();
    }

    public static void showProgress(Context context, String msg) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    public static void hideProgress() {
        if (progressDialog != null) {
            progressDialog.cancel();
        }
    }

    public static Bitmap decodeBitmap(Context context, String encodedImage) {
        Log.e(TAG, "decodeBitmap: " + encodedImage);
        if (!encodedImage.equalsIgnoreCase("")) {
            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        } else {
            return BitmapFactory.decodeResource(context.getResources(),
                    R.mipmap.ic_launcher);
        }
    }


    public static String getConvertedDate(String d) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Set your date format
        String currentData = sdf.format(date);
        return currentData;
    }

    public static void animateRevealShow(View viewRoot) {

        if (Build.VERSION.SDK_INT >= 21) {
            int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
            int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
            int finalRadius = Math.max(viewRoot.getWidth(), viewRoot.getHeight());

            Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, 0, finalRadius);
            viewRoot.setVisibility(View.VISIBLE);
//        anim.setDuration(500);
            anim.setInterpolator(new AccelerateInterpolator());
            anim.start();
            // Call some material design APIs here
        }

    }

    public static Calendar getCalendar(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c;
    }


    public static Date convertStringToDate(String s) {
        String str_date = s;
        DateFormat formatter;
        Date date = null;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = formatter.parse(str_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static long putdays(String startdate, String enddate) throws ParseException {
        String str_date = startdate;
        String end_date = enddate;
        DateFormat formatter;
        Date s;
        Date e;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        s = formatter.parse(str_date);
        e = formatter.parse(end_date);
        long diff = e.getTime() - s.getTime();
        long leavedays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1;
        return leavedays;
    }


}
