package com.ericho.coupleshare.ui

import android.os.Bundle
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ericho.coupleshare.BuildConfig
import com.ericho.coupleshare.R
import com.ericho.coupleshare.ui.license.LicensesActivity
import org.jetbrains.anko.browse
import org.jetbrains.anko.email
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast

/**
 * Created by steve_000 on 24/8/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.ui
 */
class SettingFrag : PreferenceFragmentCompat(), SettingContract.View {
    lateinit var mPresenter:SettingContract.Presenter

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

        setPreferencesFromResource(R.xml.setting, rootKey)
        mPresenter.calCacheSize()
        findPreference(KEY_LICENSE).onPreferenceClickListener = Preference.OnPreferenceClickListener {
            startActivity(activity.intentFor<LicensesActivity>())
            true
        }
        findPreference(KEY_FEEDBACK).onPreferenceClickListener = Preference.OnPreferenceClickListener {
            startActivity(activity.intentFor<LicensesActivity>())
            true
        }

        findPreference(KEY_OKHTTP_CACHE).onPreferenceClickListener = Preference.OnPreferenceClickListener {
            mPresenter.cleanOkhttpCache()
            true
        }
        findPreference(KEY_GLIDE_CACHE).onPreferenceClickListener = Preference.OnPreferenceClickListener {
            mPresenter.cleanGlideCache()
            true
        }
        findPreference(KEY_AUTHOR_EMIL).onPreferenceClickListener = Preference.OnPreferenceClickListener {
            activity.email(getString(R.string.author_email),"Feedback For Fruit Share")
        }
        findPreference("pref_version").summary = BuildConfig.VERSION_NAME
        findPreference("contributors").onPreferenceClickListener = Preference.OnPreferenceClickListener {
            context.browse(getString(R.string.contributors_desc), true)
            true
        }
    }


    override fun setPresenter(presenter: SettingContract.Presenter) {
        mPresenter = presenter
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mPresenter.subscribe()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        mPresenter.unsubscribe()
        super.onDestroyView()
    }

    override fun showOkhttpCacheSize(size: Int) {
        findPreference(KEY_OKHTTP_CACHE).summary = getString(R.string.clear_cache_desc, size)
    }

    override fun showGlideCacheSize(size: Int) {
        findPreference(KEY_GLIDE_CACHE).summary = getString(R.string.clear_cache_desc, size)
    }


    override fun showToast(str: String) {
        activity.toast(str)
    }

    companion object {
        @JvmStatic
        fun newInstance(): SettingFrag {
            val f = SettingFrag()
            val b = Bundle()
            f.arguments = b
            return f
        }


        val KEY_OKHTTP_CACHE = "pref_okhttp_cache"
        val KEY_GLIDE_CACHE = "pref_glide_cache"
        val KEY_LICENSE = "pref_license"
        val KEY_FEEDBACK = "pref_feedback"
        val KEY_AUTHOR_EMIL = "pref_author_email"
        val KEY_VERSION = "pref_version"
    }
}