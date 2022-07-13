package com.dailyyoga.filter

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.Project

class FileSizeTask extends DefaultTask {

    FileSizeTask() {
        group = 'fileFilter'
        description = 'filter large files'
        filterLargeFiles()
    }


    void filterLargeFiles() {
        project.afterEvaluate {
            int filterSize = project.extensions.fileFilterExt.filterSize
            ArrayList<String> whiteList = project.extensions.fileFilterExt.whiteList
            Map<String, String> resPath = project.extensions.fileFilterExt.resPath
            project.rootProject.childProjects.each { Map.Entry entry ->
                Project currentProject = entry.value
                if (currentProject.name == "yoga_h2") {
                    def currentResPath = 'src/main/res'
                    if (resPath?.containsKey(currentProject.name)) {
                        currentResPath = resPath.get(currentProject.name)
                    }
                    def fileTree = currentProject.fileTree(currentResPath)
                    if (fileTree != null) {
                        fileTree.dir.eachDir { File subFile ->
                            def bigResource = []
                            subFile.eachFile { File file ->
                                def size = file.size() / 1000
                                // 超过限制大小 && 不在白名单之中
                                if (size > filterSize && !whiteList?.contains(file.name) && "strings.xml" != file.name) {
                                    bigResource.add(file)
                                }
                            }
                            if (bigResource.size() > 0) {
                                def str = "\r\n文件路径:${fileTree.dir}/${subFile.name}\r\n${bigResource.size()}个资源文件过大\r\n"
                                bigResource.each { File bigFile ->
                                    def size = bigFile.size() / 1000
                                    str += "文件名：$bigFile.name 文件大小：${size}kb\r\n"
                                }
                                throw new GradleException(str)
                            }
                        }
                    }
                }
            }
        }
    }
}