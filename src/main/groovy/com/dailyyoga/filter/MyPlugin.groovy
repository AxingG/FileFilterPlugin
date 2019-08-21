package com.dailyyoga.filter

import org.gradle.api.Plugin
import org.gradle.api.Project

class MyPlugin implements Plugin<Project> {

    /**
     * 插件被引入时要执行的方法
     * @param project
     */
    @Override
    void apply(Project project) {

        project.extensions.create('fileFilterExt', FileSizeExtension)

        project.tasks.create('FileFilterTask', FileSizeTask)
    }
}