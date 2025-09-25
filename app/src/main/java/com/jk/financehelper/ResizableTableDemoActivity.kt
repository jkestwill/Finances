package com.jk.financehelper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.jk.common_ui.FinanceHelperTheme
import com.jk.common_ui.composable.ResizableTableExample

/**
 * Демонстрационная активность для тестирования компонента ResizableTable
 * 
 * Для использования добавьте в AndroidManifest.xml:
 * <activity
 *     android:name=".ResizableTableDemoActivity"
 *     android:exported="true"
 *     android:theme="@style/Theme.FinanceHelper">
 *     <intent-filter>
 *         <action android:name="android.intent.action.MAIN" />
 *         <category android:name="android.intent.category.LAUNCHER" />
 *     </intent-filter>
 * </activity>
 */
class ResizableTableDemoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinanceHelperTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ResizableTableExample()
                }
            }
        }
    }
}