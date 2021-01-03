package com.raise.practice

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.accessibilityservice.GestureDescription.StrokeDescription
import android.content.Intent
import android.graphics.Path
import android.graphics.PixelFormat
import android.media.AudioManager
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Toast
import com.raise.practice.databinding.ActionBarBinding
import com.raise.weapon_base.LLog


class GlobalActionBarService : AccessibilityService() {

    //    var mLayout: FrameLayout? = null
    lateinit var mViewBinding: ActionBarBinding

    override fun onCreate() {
        super.onCreate()
        LLog.d("GlobalActionBarService", "onCreate() ")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        LLog.d("GlobalActionBarService", "onStartCommand() ")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        LLog.d("GlobalActionBarService", "onServiceConnected() ")
        Toast.makeText(this, "yds::onServiceConnected() ", Toast.LENGTH_SHORT).show()
        createFloatWindow()
        initPerformGlobalAction()
        mViewBinding.apply {
            volumeRaise.setOnClickListener {
                LLog.d("GlobalActionBarService", "configureVolumeButton() ")
                val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI)
            }
            volumeLower.setOnClickListener {
                LLog.d("GlobalActionBarService", "configureVolumeButton() ")
                val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI)
            }
            volumeMute.setOnClickListener {
                LLog.d("GlobalActionBarService", "configureVolumeButton() ")
                val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_TOGGLE_MUTE, AudioManager.FLAG_SHOW_UI)
            }
            scrollUp.setOnClickListener {
                val scrollable = findScrollableNode(windows.get(0).root)
                scrollable?.performAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD.id)
            }
            swipeToLeft.setOnClickListener {
                val swipePath = Path()
                swipePath.moveTo(1000f, 1000f)
                swipePath.lineTo(100f, 1000f)
                if (Build.VERSION.SDK_INT >= 24) {
                    val gestureBuilder = GestureDescription.Builder()
                    gestureBuilder.addStroke(StrokeDescription(swipePath, 0, 500))
                    dispatchGesture(gestureBuilder.build(), null, null)
                }
            }
            swipeToRight.setOnClickListener {
                val swipePath = Path()
                swipePath.moveTo(100f, 1000f)
                swipePath.lineTo(1000f, 1000f)
                if (Build.VERSION.SDK_INT >= 24) {
                    val gestureBuilder = GestureDescription.Builder()
                    gestureBuilder.addStroke(StrokeDescription(swipePath, 0, 500))
                    dispatchGesture(gestureBuilder.build(), null, null)
                }
            }
        }

    }

    private fun initPerformGlobalAction() {
        mViewBinding.apply {
//            back.setOnClickListener {
//                performGlobalAction(GLOBAL_ACTION_BACK)
//            }
//            home.setOnClickListener {
//                performGlobalAction(GLOBAL_ACTION_HOME)
//            }
//            lockScreen.setOnClickListener {
//                performGlobalAction(GLOBAL_ACTION_LOCK_SCREEN)
//            }
//            notifications.setOnClickListener {
//                performGlobalAction(GLOBAL_ACTION_NOTIFICATIONS)
//            }
//            powerDialog.setOnClickListener {
//                performGlobalAction(GLOBAL_ACTION_POWER_DIALOG)
//            }
//            quickSetting.setOnClickListener {
//                performGlobalAction(GLOBAL_ACTION_QUICK_SETTINGS)
//            }
//            recents.setOnClickListener {
//                performGlobalAction(GLOBAL_ACTION_RECENTS)
//            }
//            takeScreenshot.setOnClickListener {
//                performGlobalAction(GLOBAL_ACTION_TAKE_SCREENSHOT)
//            }
//            toggleSplitScreen.setOnClickListener {
//                performGlobalAction(GLOBAL_ACTION_TOGGLE_SPLIT_SCREEN)
//            }
        }
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        LLog.d("GlobalActionBarService", "onAccessibilityEvent() start.")
    }

    private fun findScrollableNode(root: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        val deque: ArrayDeque<AccessibilityNodeInfo> = ArrayDeque<AccessibilityNodeInfo>()
        deque.add(root)
        while (!deque.isEmpty()) {
            val node: AccessibilityNodeInfo = deque.removeFirst()
            if (node.actionList.contains(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD)) {
                return node
            }
            for (i in 0 until node.childCount) {
                deque.addLast(node.getChild(i))
            }
        }
        return null
    }

    private fun configureVolumeButton() {
//        val volumeUpButton = mViewBinding.volumeUp
//        volumeUpButton.setOnClickListener {
//            LLog.d("GlobalActionBarService", "configureVolumeButton() ")
//            val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
//            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
//                    AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI)
//        }
    }

    private fun createFloatWindow() {
        // Create an overlay and display the action bar
        val wm = getSystemService(WINDOW_SERVICE) as WindowManager
        mViewBinding = ActionBarBinding.inflate(LayoutInflater.from(this))
//        mLayout = FrameLayout(this)
        val lp = WindowManager.LayoutParams()
        lp.type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY
        lp.format = PixelFormat.TRANSLUCENT
        lp.flags = lp.flags or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.TOP
//        val inflater = LayoutInflater.from(this)
//        inflater.inflate(R.layout.action_bar, mLayout)
        wm.addView(mViewBinding.root, lp)


    }

    override fun onInterrupt() {
        LLog.d("GlobalActionBarService", "onInterrupt() ")
    }

    override fun onDestroy() {
        LLog.d("GlobalActionBarService", "onDestroy() ")
        super.onDestroy()
    }

}