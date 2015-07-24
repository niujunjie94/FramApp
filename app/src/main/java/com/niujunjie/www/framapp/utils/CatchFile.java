package com.niujunjie.www.framapp.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import libcore.io.DiskLruCache;

/**
 * Created by niujunjie on 2015/7/24.
 */
public class CatchFile {


    /**
     * 获取文件缓存的位置
     * @param context
     * @param uniqueName 缓存文件的名字
     * @return
     */
    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * 获取应用的版本号
     * @param context
     * @return
     */
    public static int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 创建缓存文件
     */
    public static DiskLruCache createDiskLruCache() {
        DiskLruCache mDiskLruCache = null;
        try {
            File cacheDir = getDiskCacheDir(UIUtils.getAppContext(), Constants.CACHE_FOLDER_NAME);
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            if(mDiskLruCache == null) {
                mDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(UIUtils.getAppContext()), 1, Constants.CACHE_SIZE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mDiskLruCache;
    }

    /**
     * 将一个字符串进行MD5加密并转换为16进制
     * @param key
     * @return
     */
    public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    /**
     * 字节码转换为16进制
     * @param bytes
     * @return
     */
    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 缓存数据入口
     * @param cacheFileName
     */
    public static void writeCacheData(String cacheFileName,OutputStream out){
        String key = hashKeyForDisk(cacheFileName);
        DiskLruCache.Editor editor;
        try {
            editor = createDiskLruCache().edit(key);
            OutputStream outputStream = editor.newOutputStream(0);
            outputStream = out;
            if(outputStream!=null) {
                editor.commit();
            }else{
                editor.abort();
            }
            createDiskLruCache().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 缓存读取
     * @param cacheFileName
     * @return
     */
    public static InputStream readCacheData(String cacheFileName){
        String key = hashKeyForDisk(cacheFileName);
        try {
            DiskLruCache.Snapshot snapShot = createDiskLruCache().get(key);
            if(snapShot != null){
                InputStream is = snapShot.getInputStream(0);
                return is;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *移除缓存的
     * 一条数据
     * @param cacheFileName
     */
    public static void removeCacheData(String cacheFileName){
        String key = hashKeyForDisk(cacheFileName);
        try {
            createDiskLruCache().remove(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得缓存数据的大小
     * @return
     */
    public static float getCacheFileSize(){
        return createDiskLruCache().size();
    }

    public static void deleteCacheFile(){
        try {
            createDiskLruCache().delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
