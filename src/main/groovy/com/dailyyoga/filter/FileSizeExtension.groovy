package com.dailyyoga.filter

class FileSizeExtension {

    int fileFilterSize = 100 // 单位kb
    ArrayList<String> whiteList // 白名单
    Map<String, String> resPath// 对应路径

    FileSizeExtension() {

    }

    @Override
    String toString() {
        return super.toString()
    }
}