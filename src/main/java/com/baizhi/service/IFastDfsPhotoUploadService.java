package com.baizhi.service;

import org.springframework.web.multipart.MultipartFile;

public interface IFastDfsPhotoUploadService {
    /**
     * 图片上传
     *
     * @param multipartFile
     * @return
     */
    String uploadPhoto(MultipartFile multipartFile);

    /**
     * 删除缩略图
     *
     * @param path
     */
    void deleteThumbPhoto(String path);
}
