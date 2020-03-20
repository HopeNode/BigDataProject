package com.baizhi.service.impl;

import com.baizhi.service.IFastDfsPhotoUploadService;
import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.upload.FastImageFile;
import com.github.tobato.fastdfs.domain.upload.ThumbImage;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;

@Service
public class FastDfsPhotoUploadService implements IFastDfsPhotoUploadService {
    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Override
    public String uploadPhoto(MultipartFile multipartFile) {
        String fileUrl = null;
        //获取文件类型
        String fileName = multipartFile.getOriginalFilename();
        // String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
        String suffix = FilenameUtils.getExtension(fileName);
        //将multipartFile转换为 InputStream
        try {
            InputStream inputStream = multipartFile.getInputStream();
            FastImageFile fastImageFile = new FastImageFile(inputStream, inputStream.available(), suffix, new HashSet<MetaData>(), new ThumbImage());
            StorePath storePath = fastFileStorageClient.uploadImage(fastImageFile);
            fileUrl = storePath.getFullPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileUrl;
    }

    @Override
    public void deleteThumbPhoto(String path) {
        String pathSuffix = FilenameUtils.getExtension(path);
        int indexP = path.indexOf(".");
        String path2 = path.substring(0, indexP) + "_100x100";
        String resultPath = path2 + "." + pathSuffix;
        fastFileStorageClient.deleteFile(resultPath);
    }
}
