package me.ykrank.s1next.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import me.ykrank.s1next.R
import me.ykrank.s1next.view.fragment.WebViewFragment
import me.ykrank.s1next.view.internal.BackPressDelegate

/**
 * Created by ykrank on 2017/1/5.
 */

class WebViewActivity : BaseActivity() {
    private var fragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_without_drawer)

        if (savedInstanceState == null) {
            fragment = WebViewFragment.getInstance(intent.getStringExtra(ARG_URL), intent.getBooleanExtra(ARG_ENABLE_JS, false))
            supportFragmentManager.beginTransaction()
                    .add(R.id.frame_layout, fragment, WebViewFragment.TAG)
                    .commit()
        } else {
            fragment = supportFragmentManager.findFragmentByTag(WebViewFragment.TAG)
        }
    }

    override fun onBackPressed() {
        if (fragment != null && fragment is BackPressDelegate) {
            val backPressDelegate = fragment as BackPressDelegate
            if (backPressDelegate.onBackPressed()) {
                return
            }
        }
        super.onBackPressed()
    }

    companion object {
        val ARG_URL = "arg_url"
        var ARG_ENABLE_JS = "arg_enable_js"
        var ARG_PC_USER_AGENT = "arg_pc_user_agent"

        fun start(context: Context, url: String, enableJS: Boolean = false, pcUserAgent:Boolean = false) {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(ARG_URL, url)
            intent.putExtra(ARG_ENABLE_JS, enableJS)
            intent.putExtra(ARG_PC_USER_AGENT, pcUserAgent)
            context.startActivity(intent)
        }
    }
}
