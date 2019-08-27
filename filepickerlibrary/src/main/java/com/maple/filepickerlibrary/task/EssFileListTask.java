package com.maple.filepickerlibrary.task;

import android.os.AsyncTask;


import com.maple.filepickerlibrary.model.EssFile;
import com.maple.filepickerlibrary.model.EssFileFilter;
import com.maple.filepickerlibrary.model.EssFileListCallBack;
import com.maple.filepickerlibrary.util.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * EssFileListTask
 * Created by 李波 on 2018/3/5.
 */

public class EssFileListTask extends AsyncTask<Void,Void,List<EssFile>> {

    private List<EssFile> mSelectedFileList;
    private String queryPath;
    private String[] types;
    private int mSortType;
    private EssFileListCallBack callBack;

    public EssFileListTask(List<EssFile> mSelectedFileList, String queryPath, String[] types, int mSortType, EssFileListCallBack fileCallBack) {
        this.mSelectedFileList = mSelectedFileList;
        this.queryPath = queryPath;
        this.types = types;
        this.mSortType = mSortType;
        this.callBack = fileCallBack;
    }

    @Override
    protected List<EssFile> doInBackground(Void... voids) {
        File file = new File(queryPath);
        File[] files = file.listFiles(new EssFileFilter(types));
        if(files == null){
            return new ArrayList<>();
        }
        List<File> fileList = Arrays.asList(files);
        if(mSortType == FileUtils.BY_NAME_ASC){
            Collections.sort(fileList, new FileUtils.SortByName());
        }else if(mSortType == FileUtils.BY_NAME_DESC){
            Collections.sort(fileList, new FileUtils.SortByName());
            Collections.reverse(fileList);
        }else if(mSortType == FileUtils.BY_TIME_ASC){
            Collections.sort(fileList,new FileUtils.SortByTime());
        }else if(mSortType == FileUtils.BY_TIME_DESC){
            Collections.sort(fileList,new FileUtils.SortByTime());
            Collections.reverse(fileList);
        }else if(mSortType == FileUtils.BY_SIZE_ASC){
            Collections.sort(fileList,new FileUtils.SortBySize());
        }else if(mSortType == FileUtils.BY_SIZE_DESC){
            Collections.sort(fileList,new FileUtils.SortBySize());
            Collections.reverse(fileList);
        }else if(mSortType == FileUtils.BY_EXTENSION_ASC){
            Collections.sort(fileList,new FileUtils.SortByExtension());
        }else if(mSortType == FileUtils.BY_EXTENSION_DESC){
            Collections.sort(fileList,new FileUtils.SortByExtension());
            Collections.reverse(fileList);
        }
        List<EssFile> tempFileList = EssFile.getEssFileList(fileList);
        for (EssFile selectedFile :
                mSelectedFileList) {
            for (int i = 0; i < tempFileList.size(); i++) {
                if (selectedFile.getAbsolutePath().equals(tempFileList.get(i).getAbsolutePath())) {
                    tempFileList.get(i).setChecked(true);
                    break;
                }
            }
        }
        return tempFileList;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<EssFile> essFileList) {
        if(callBack!=null){
            callBack.onFindFileList(queryPath,essFileList);
        }
    }
}
