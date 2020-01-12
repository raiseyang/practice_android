package com.raise.practice.launch

import android.content.Intent
import com.abupdate.common.Trace
import com.raise.practice.autojs.AutoJs
import com.stardust.autojs.engine.encryption.ScriptEncryption
import com.stardust.autojs.execution.ExecutionConfig
import com.stardust.autojs.project.ProjectConfig
import com.stardust.autojs.script.JavaScriptFileSource
import com.stardust.autojs.script.JavaScriptSource
import com.stardust.pio.PFiles
import com.stardust.util.MD5
import java.io.File
import kotlin.properties.Delegates

/**
 * Created by raise.yang on 20/01/10.
 */
class FileScriptLauncher {
    companion object {
        const val TAG = "FileScriptLauncher"
    }

    private var projectConfig: ProjectConfig by Delegates.notNull()

    fun launch(projectDir: String) {
        prepare(projectDir)
        runScript(projectDir)
    }

    private fun runScript(projectDir: String) {
        Trace.d(TAG, "runScript() start:dir=$projectDir")
        try {
            val source = JavaScriptFileSource(File(projectDir, projectConfig.mainScriptFile))
            val config = ExecutionConfig(workingDirectory = projectDir)
            if (source.executionMode and JavaScriptSource.EXECUTION_MODE_UI != 0) {
                // 脚本包含UI
                config.intentFlags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME
            }
            val scriptExecution = AutoJs.instance.scriptEngineService.execute(source, config)
            Trace.d(TAG, "runScript() end:${scriptExecution.source}")
        } catch (e: Exception) {
            Trace.e(TAG, "runScript() $e")
            AutoJs.instance.globalConsole.error(e)
        }

    }

    /**
     * @param projectDir /sdcard
     */
    private fun prepare(projectDir: String) {
        //配置文件路径
        val projectConfigPath = PFiles.join(projectDir, ProjectConfig.CONFIG_FILE_NAME)
        //加载配置
        projectConfig = ProjectConfig.fromFile(projectConfigPath)
        //初始化key
        initKey()
    }

    private fun initKey() {
        val key = MD5.md5(projectConfig.packageName + projectConfig.versionName + projectConfig.mainScriptFile)
        val vec = MD5.md5(projectConfig.buildInfo.buildId + projectConfig.name).substring(0, 16)
        try {
            val fieldKey = ScriptEncryption::class.java.getDeclaredField("mKey")
            fieldKey.isAccessible = true
            fieldKey.set(null, key)
            val fieldVector = ScriptEncryption::class.java.getDeclaredField("mInitVector")
            fieldVector.isAccessible = true
            fieldVector.set(null, vec)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}