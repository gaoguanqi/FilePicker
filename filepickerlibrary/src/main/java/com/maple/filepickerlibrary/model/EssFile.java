package com.maple.filepickerlibrary.model;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;

import com.maple.filepickerlibrary.util.FileUtils;
import com.maple.filepickerlibrary.util.MimeType;
import com.maple.filepickerlibrary.util.PathUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * EssFile
 * Created by 李波 on 2018/2/5.
 */

public class EssFile implements Parcelable {

    public static final int CAPTURE = 0;
    public static final int MEDIA = 1;

    private String mFilePath;
    private String mimeType;
    private String childFolderCount = "加载中";
    private String childFileCount = "加载中";
    private boolean isChecked = false;
    private boolean isExits = false;
    private boolean isDirectory = false;
    private boolean isFile = false;
    private String mFileName;
    private Uri uri;

    private int itemType = MEDIA;

    protected EssFile(Parcel in) {
        mFilePath = in.readString();
        mimeType = in.readString();
        childFolderCount = in.readString();
        childFileCount = in.readString();
        isChecked = in.readByte() != 0;
        isExits = in.readByte() != 0;
        isDirectory = in.readByte() != 0;
        isFile = in.readByte() != 0;
        mFileName = in.readString();
        uri = in.readParcelable(Uri.class.getClassLoader());
        itemType = in.readInt();
    }

    public static final Creator<EssFile> CREATOR = new Creator<EssFile>() {
        @Override
        public EssFile createFromParcel(Parcel in) {
            return new EssFile(in);
        }

        @Override
        public EssFile[] newArray(int size) {
            return new EssFile[size];
        }
    };

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public EssFile(String path) {
        mFilePath = path;
        File file = new File(mFilePath);
        if (file.exists()) {
            isExits = true;
            isDirectory = file.isDirectory();
            isFile = file.isFile();
            mFileName = file.getName();
        }
        mimeType = FileUtils.getMimeType(mFilePath);
    }

    public EssFile(File file) {
        mFilePath = file.getAbsolutePath();
        if (file.exists()) {
            isExits = true;
            isDirectory = file.isDirectory();
            isFile = file.isFile();
        }
        mimeType = FileUtils.getMimeType(file.getAbsolutePath());
    }

    public EssFile(long id, String mimeType) {
        this.mimeType = mimeType;
        Uri contentUri;
        if (isImage()) {
            contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        } else if (isVideo()) {
            contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        } else {
            // ?
            contentUri = MediaStore.Files.getContentUri("external");
        }
        this.uri = ContentUris.withAppendedId(contentUri, id);
    }

    public Uri getUri() {
        return uri;
    }

    public boolean isExits() {
        return isExits;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getChildFolderCount() {
        return childFolderCount;
    }

    public void setChildFolderCount(String childFolderCount) {
        this.childFolderCount = childFolderCount;
    }

    public String getChildFileCount() {
        return childFileCount;
    }

    public void setChildFileCount(String childFileCount) {
        this.childFileCount = childFileCount;
    }

    public void setChildCounts(String childFileCount, String childFolderCount) {
        this.childFileCount = childFileCount;
        this.childFolderCount = childFolderCount;
    }


    public File getFile() {
        return new File(mFilePath);
    }

    public String getName() {
        return new File(mFilePath).getName();
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public boolean isFile() {
        return isFile;
    }

    public String getAbsolutePath() {
        return mFilePath;
    }

    public static List<EssFile> getEssFileList(List<File> files) {
        List<EssFile> essFileList = new ArrayList<>();
        for (File file :
                files) {
            essFileList.add(new EssFile(file));
        }
        return essFileList;
    }

    public static ArrayList<EssFile> getEssFileList(Context context, Set<EssFile> essFileSet) {
        ArrayList<EssFile> essFileArrayList = new ArrayList<>();
        for (EssFile ess_file :
                essFileSet) {
            ess_file.mFilePath = PathUtils.getPath(context, ess_file.uri);
            essFileArrayList.add(ess_file);
        }
        return essFileArrayList;
    }

    public static ArrayList<String> getFilePathList(ArrayList<EssFile> essFileArrayList){
        ArrayList<String> resultList = new ArrayList<>();
        for (EssFile essFile:essFileArrayList) {
            resultList.add(essFile.getAbsolutePath());
        }
        return resultList;
    }


    @Override
    public String toString() {
        return "EssFile{" +
                "mFilePath='" + mFilePath + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", mFileName='" + mFileName + '\'' +
                '}';
    }

    public boolean isImage() {
        if (mimeType == null) return false;
        return mimeType.equals(MimeType.JPEG.toString())
                || mimeType.equals(MimeType.PNG.toString())
                || mimeType.equals(MimeType.GIF.toString())
                || mimeType.equals(MimeType.BMP.toString())
                || mimeType.equals(MimeType.WEBP.toString());
    }

    public boolean isGif() {
        if (mimeType == null) return false;
        return mimeType.equals(MimeType.GIF.toString());
    }

    public boolean isVideo() {
        if (mimeType == null) return false;
        return mimeType.equals(MimeType.MPEG.toString())
                || mimeType.equals(MimeType.MP4.toString())
                || mimeType.equals(MimeType.QUICKTIME.toString())
                || mimeType.equals(MimeType.THREEGPP.toString())
                || mimeType.equals(MimeType.THREEGPP2.toString())
                || mimeType.equals(MimeType.MKV.toString())
                || mimeType.equals(MimeType.WEBM.toString())
                || mimeType.equals(MimeType.TS.toString())
                || mimeType.equals(MimeType.AVI.toString());
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public int getItemType() {
        return itemType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mFilePath);
        dest.writeString(mimeType);
        dest.writeString(childFolderCount);
        dest.writeString(childFileCount);
        dest.writeByte((byte) (isChecked ? 1 : 0));
        dest.writeByte((byte) (isExits ? 1 : 0));
        dest.writeByte((byte) (isDirectory ? 1 : 0));
        dest.writeByte((byte) (isFile ? 1 : 0));
        dest.writeString(mFileName);
        dest.writeParcelable(uri, flags);
        dest.writeInt(itemType);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof EssFile)) {
            return false;
        }

        EssFile other = (EssFile) obj;
        if (uri == null) {
            return mFilePath.equalsIgnoreCase(other.getAbsolutePath());
        } else {
            return uri.equals(other.getUri());
        }
    }

    @Override
    public int hashCode() {
        int result = mFilePath != null ? mFilePath.hashCode() : 0;
        result = 31 * result + (uri != null ? uri.hashCode() : 0);
        result = 31 * result + itemType;
        return result;
    }
}
