package com.baizhi.test.fileupload;

import com.baizhi.UserModelRestApplication;
import com.baizhi.service.IFastDfsPhotoUploadService;
import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.upload.FastImageFile;
import com.github.tobato.fastdfs.domain.upload.ThumbImage;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;

@SpringBootTest(classes = UserModelRestApplication.class)
@RunWith(SpringRunner.class)
public class TestFastDfsFileUpload {
    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Test
    public void test() throws IOException {
        FileInputStream inputStream = new FileInputStream("C:\\Users\\SUN6\\Desktop\\23.png");
        FastImageFile fastImageFile = new FastImageFile(inputStream, inputStream.available(), "png", new HashSet<MetaData>(), new ThumbImage());
        StorePath storePath = fastFileStorageClient.uploadImage(fastImageFile);
        String s = storePath.getGroup() + storePath.getPath();
        System.out.println(s);
        String s2 = storePath.getFullPath();
        System.out.println(s2);
        System.out.println(storePath.getFullPath());
    }

    @Test
    public void test2() {
        String fileName = "aa.png";
        int s = fileName.lastIndexOf(".");
        System.out.println(s);
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        System.out.println(suffix);
        System.out.println(FilenameUtils.getExtension(fileName));
        //group2/M00/00/00/Cg8AFl50qSaADOJxAARYdm_I9v4577.png
    }

    @Test
    public void test3() {
        String fileName = "group2/M00/00/00/Cg8AFl50qSaADOJxAARYdm_I9v4577.png";
        /*int index = fileName.indexOf("/");
        String group = fileName.substring(0,index);
        String path = fileName.substring(index+1);
        System.out.println("group= "+group);*/
        String pathSuffix = FilenameUtils.getExtension(fileName);
        int indexP = fileName.indexOf(".");
        String path2 = fileName.substring(0, indexP) + "_100x100";
        System.out.println("path=" + path2 + "." + pathSuffix);

        //fastFileStorageClient.deleteFile("group3","M00/00/00/wKjvgl0prl6AX6ygAL26hh1kYdE312_80x80.png");
    }

    @Autowired
    private IFastDfsPhotoUploadService iFastDfsPhotoUploadService;

    @Test
    public void test4() {
       /* String fileName = "group2/M00/00/00/Cg8AFl50qSaADOJxAARYdm_I9v4577.png";
           // fastFileStorageClient.deleteFile("group2","M00/00/00/Cg8AFl50qSaADOJxAARYdm_I9v4577_100x100.png");

         fastFileStorageClient.deleteFile("group2/M00/00/00/Cg8AFl50q-KANDIDAARYdhmE-ho987_100x100.png");*/

        // iFastDfsPhotoUploadService.deleteThumbPhoto("group2/M00/00/00/Cg8AFl50tlCATcn8AARYdmjPtY8661.png");
    }
}
