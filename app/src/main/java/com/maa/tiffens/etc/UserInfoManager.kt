package com.maa.tiffens.etc

import android.content.Context
import android.content.SharedPreferences

class UserInfoManager private constructor() {

    private val KEY_ACCESS_TOKEN = "F3ZT7"
    private val KEY_ACCOUNT_ID = "V8D85H"
    private val KEY_ACCOUNT_NAME = "key_account_name"
    private val KEY_DEVICE_TOKEN = "key_device_token"
    private val KEY_PROFILE_PIC = "key_profile_pic"

    private var accessToken: String? = null
    private var accountName: String? = ""
    private var accountId: String? = null
    private var profilePic: String? = ""
    private var deviceToken: String? = ""

    private var prefs: SharedPreferences? = null
    private var prefsnoclear: SharedPreferences? = null

    val authToken: String?
        get() {
            if (accessToken == null) {
                this.accessToken = this.prefs!!.getString(KEY_ACCESS_TOKEN, null)
                if (this.accessToken != null)
                    if (this.accessToken!!.toLowerCase().contains("false")) {
                        this.accessToken = null
                    }
                if (accessToken == null)
                    return null
            }
            return accessToken
        }

    private fun openPrefsNoclear(c: Context) {
        this.prefsnoclear = c.getSharedPreferences(FILE_NAME_NOCLR, Context.MODE_PRIVATE)
    }

    private fun openPrefs(c: Context) {
        this.prefs = c.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    }

    fun saveAuthToken(accessToken: String?) {
        this.accessToken = accessToken
        val editor = this.prefs!!.edit()
        editor.putString(KEY_ACCESS_TOKEN, this.accessToken)
        editor.commit()
    }



    fun saveAccountId(accountId: String) {
        this.accountId = accountId
        val editor = this.prefs!!.edit()
        editor.putString(KEY_ACCOUNT_ID, accountId)
        editor.commit()
    }
    fun saveProfilePic(profilePic: String) {
        this.profilePic = accountId
        val editor = this.prefs!!.edit()
        editor.putString(KEY_PROFILE_PIC, profilePic)
        editor.commit()
    }
    fun getProfilePic(): String {
        this.profilePic = this.prefs!!.getString(KEY_PROFILE_PIC, null)
        return profilePic!!
    }
    fun getAccountId(): String {
        if (accountId == null) {
            this.accountId = this.prefs!!.getString(KEY_ACCOUNT_ID, null)
        }
        return accountId!!
    }

    fun saveAccountName(accountName: String) {
        this.accountName = accountName
        val editor = this.prefs!!.edit()
        editor.putString(KEY_ACCOUNT_NAME, accountName)
        editor.commit()
    }

    fun getAccountName(): String {
        this.accountName = this.prefs!!.getString(KEY_ACCOUNT_NAME, null)
        return accountName!!
    }

    fun saveNotiToken(deviceToken: String?) {
        this.deviceToken = deviceToken
        val editor = this.prefs!!.edit()
        editor.putString(KEY_DEVICE_TOKEN, deviceToken)
        editor.commit()

    }
    fun getNotiToken(): String {
        return this.prefs!!.getString(KEY_DEVICE_TOKEN, "")!!
    }
    fun logout() {
        saveAuthToken(null)
        prefs!!.edit().clear().commit()
        _userInfo = null
    }
    companion object {

        private var _userInfo: UserInfoManager? = null
        private val FILE_NAME = "mt_user_sec"
        private val FILE_NAME_NOCLR = "mt_user_sec_no_clr"

        fun getInstance(c: Context): UserInfoManager {
            if (_userInfo == null) {
                _userInfo = UserInfoManager()
                _userInfo!!.openPrefs(c.applicationContext)
                _userInfo!!.openPrefsNoclear(c.applicationContext)
            }

            return _userInfo!!
        }
    }

}