package com.shui.fastdfs;

import lombok.Data;

@Data
public class FastDFSFile {

    private String name;

    private byte[] content;

    private String ext;

    private String md5;

    private String author;
}
