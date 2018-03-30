package education.zhiyuan.com.picturebooks.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by Spring on 2017/8/3.
 */

public class CacheUtilsNew {
    private Context context;
    private long fileSize;
    private String cacheSize = "0KB";
    private String loadPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/huib";

    public CacheUtilsNew(Context context) {
        this.context = context;
    }

    public String getTotalCacheSize() {
        File filesDir = context.getFilesDir();// /data/data/package_name/files
        File cacheDir = context.getCacheDir();// /data/data/package_name/cache
        fileSize += getDirSize(filesDir);
        fileSize += getDirSize(cacheDir);
        // 2.2版本才有将应用缓存转移到sd卡的功能
        if (isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File externalCacheDir = getExternalCacheDir(context);//"<sdcard>/Android/data/<package_name>/cache/"
                fileSize += getDirSize(externalCacheDir);
            }
        }
        if (fileSize > 0) {
            cacheSize = formatFileSize(fileSize);
        }
        return cacheSize;
    }

    public String getTotalCacheSize2() {
        File filesDir = context.getFilesDir();// /data/data/package_name/files
        File cacheDir = context.getCacheDir();// /data/data/package_name/cache
        File loadDir = new File(loadPath);
        fileSize += getDirSize(filesDir);
        fileSize += getDirSize(cacheDir);
        fileSize += getDirSize(loadDir);
        Log.e("cache", "getTotalCacheSize2: "+ formatFileSize(getDirSize(filesDir))+"==="+formatFileSize(getDirSize(cacheDir))+"====="+formatFileSize(getDirSize(loadDir)));
        // 2.2版本才有将应用缓存转移到sd卡的功能
        if (isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File externalCacheDir = getExternalCacheDir(context);//"<sdcard>/Android/data/<package_name>/cache/"
                fileSize += getDirSize(externalCacheDir);
            }
        }
        if (fileSize > 0) {
            cacheSize = formatFileSize(fileSize);
        }
        return cacheSize;
    }


    /**
     * 获取目录文件大小
     */
    public long getDirSize(File dir) {
        if (dir == null) {
            return 0;
        }
        if (!dir.isDirectory()) {
            return 0;
        }
        long dirSize = 0;
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                dirSize += file.length();
            } else if (file.isDirectory()) {
                dirSize += file.length();
                dirSize += getDirSize(file); // 递归调用继续统计
            }
        }
        return dirSize;
    }

    /**
     * 判断当前版本是否兼容目标版本的方法
     */
    public boolean isMethodsCompat(int VersionCode) {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }

    @TargetApi(8)
    public File getExternalCacheDir(Context context) {
        return context.getExternalCacheDir();
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return B/KB/MB/GB
     */
    public String formatFileSize(long fileS) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 清除app缓存
     */
    public boolean clearAppCache() {
        boolean clearResult = false;
        clearResult = deleteDir(context.getCacheDir());
        clearResult = deleteDir(context.getFilesDir());
        clearResult = deleteDir(new File(loadPath));
        if (isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                clearResult = deleteDir(getExternalCacheDir(context));
            }
        }
        return clearResult;
    }

    //根据文件名称删除文件
    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    /**
     * 在项目中经常会使用到WebView 控件, 当加载html
     * 页面时,会在/data/data/
     * package_name目录下生成database与cache 两个文件夹。
     * 请求的url 记录是保存在WebViewCache.db,
     * 而url 的内容是保存在WebViewCache
     * 文件夹下
     * 清除缓存目录
     *
     * @param dir 目录
     * @param
     * @return
     */
    private int clearCacheFolder(File dir, long curTime) {
        int deletedFiles = 0;
        if (dir != null && dir.isDirectory()) {
            try {
                for (File child : dir.listFiles()) {
                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child, curTime);
                    }
                    if (child.lastModified() < curTime) {
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return deletedFiles;
    }
}
