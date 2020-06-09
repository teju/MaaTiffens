package com.maa.tiffens

import android.app.FragmentManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.franmontiel.localechanger.LocaleChanger
import com.maatiffens.gon.etc.callback.NotifyListener
import com.maatiffens.libs.helpers.BaseHelper
import com.maatiffens.libs.helpers.BaseUIHelper
import com.maa.tiffens.etc.Helper
import com.maa.tiffens.etc.UserInfoManager
import com.maa.tiffens.ui.dialog.NotifyDialogFragment
import com.maa.tiffens.ui.fragments.BaseFragment
import com.maa.tiffens.ui.fragments.HomeFragment
import com.maa.tiffens.ui.fragments.LoginFragment
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main.*

class ActivityMain : AppCompatActivity() {


    companion object {
        private var MAIN_FLOW_INDEX = 0
        private val MAIN_FLOW_TAG = "MainFlowFragment"
    }
    var submitPressed = true;

    private var mReceiver: BroadcastReceiver? = null
    private var mIntentFilter: IntentFilter? = null

        @RequiresApi(Build.VERSION_CODES.O)
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            Paper.init(this)
            Handler().postDelayed(
                Runnable // Using handler with postDelayed called runnable run method

                {
                    maatiffen_logo_icon.visibility = View.GONE
                    triggerMainProcess()

                }, 2 * 2000
            ) // wait for 5 s
            BaseHelper.triggerNotifLog(this);

            val mContext = this.getBaseContext();

            mReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                   // showDialog()
                }
            }

            mIntentFilter = IntentFilter("OPEN_NEW_ACTIVITY")
            BaseHelper.getHAshKey(this)
        }


        open fun showNotifyDialog(
            tittle: String?,
            messsage: String?,
            button_positive:String?,
            button_negative: String?,
            n: NotifyListener){
            try {
                val f = NotifyDialogFragment().apply {
                    this.listener = n
                }
                f.notify_tittle = tittle
                f.notify_messsage = messsage
                f.button_positive = button_positive
                f.button_negative = button_negative
                f.isCancelable = false
                f.show(supportFragmentManager, NotifyDialogFragment.TAG)
            } catch (e : Exception){
                System.out.println("Notification_received Exception " +e.toString())
            }
        }

        override fun attachBaseContext(newBase: Context) {
            var newBase = newBase
            newBase = LocaleChanger.configureBaseContext(newBase)
            super.attachBaseContext(newBase)
        }

        fun exitApp() {
            finish()
        }
        override fun onBackPressed() {
            val f = getSupportFragmentManager().beginTransaction()
            val list = getSupportFragmentManager().getFragments()
            var foundVisible = false
            for(i in  0..(list.size - 1)){
                if(list.get(i).isVisible){
                    if(list.get(i) is BaseFragment) {
                        foundVisible = true
                        (list.get(i) as BaseFragment).onBackTriggered()
                    }
                }
            }

            if(!foundVisible)
                proceedDoOnBackPressed()
        }


        fun proceedDoOnBackPressed(){
            Helper.hideSoftKeyboard(this@ActivityMain)

            val f = getSupportFragmentManager().beginTransaction()
            val list = getSupportFragmentManager().getFragments()

            for(frag in list){
                if(frag.tag!!.contentEquals(MAIN_FLOW_TAG + (MAIN_FLOW_INDEX - 1))){
                    f.show(frag)
                }
            }

            if (getSupportFragmentManager().getBackStackEntryCount() <= 1 || (currentFragment is LoginFragment)) {
                this@ActivityMain.finish()
            } else {
                super.onBackPressed()
            }

            MAIN_FLOW_INDEX = MAIN_FLOW_INDEX - 1
        }

        override fun onResume() {
            super.onResume()
            registerReceiver(mReceiver, mIntentFilter);

        }

        override fun onPause() {
            super.onPause()

        }

        fun triggerMainProcess(){

            if(!BaseHelper.isEmpty(UserInfoManager.getInstance(this).authToken))
                setFragment(HomeFragment())
            else
                setFragment(LoginFragment())
        }


        fun setFragment(frag: Fragment) {
            try {
                val f = getSupportFragmentManager().beginTransaction()
                val list = getSupportFragmentManager().getFragments()
                for(frag in list){
                    if(frag.isVisible){
                        f.hide(frag)
                    }
                }

                MAIN_FLOW_INDEX = MAIN_FLOW_INDEX + 1
                f.add(R.id.layoutFragment, frag, MAIN_FLOW_TAG + MAIN_FLOW_INDEX).addToBackStack(
                    MAIN_FLOW_TAG
                ).commitAllowingStateLoss()
                BaseUIHelper.hideKeyboard(this)
            } catch (e: Exception) {
                Helper.logException(this@ActivityMain, e)
            }

        }


        fun jumpToPreviousFlowThenGoTo(fullFragPackageNameThatStillExistInStack: String, targetFrag: Fragment){
            jumpToPreviousFragment(fullFragPackageNameThatStillExistInStack)
            setFragment(targetFrag)
        }

        fun jumpToPreviousFragment(fullFragPackageNameThatStillExistInStack: String) {
            try {
                var indexTag: String? = null

                val f = getSupportFragmentManager().beginTransaction()
                var list = getSupportFragmentManager().getFragments()
                for(i in  0..(list.size - 1)){

                    if(list.get(i).javaClass.name.equals(fullFragPackageNameThatStillExistInStack, ignoreCase = false)){
                        indexTag = list.get(i).tag
                    }

                    if(list.get(i).isVisible){
                        f.hide(list.get(i))
                    }
                }

                if(indexTag == null){
                    onBackPressed()
                }else{

                    val currentIndex = MAIN_FLOW_INDEX
                    for(i in currentIndex downTo 0){
                        try {
                            if((MAIN_FLOW_TAG + i).equals(indexTag, ignoreCase = true)) break
                            getSupportFragmentManager().popBackStackImmediate()
                            MAIN_FLOW_INDEX = MAIN_FLOW_INDEX - 1
                        } catch (e: Exception) {
                            Helper.logException(this@ActivityMain, e)
                        }
                    }

                    list = getSupportFragmentManager().getFragments()
                    for(i in  0..(list.size - 1)){
                        if(list.get(i).tag.equals(indexTag, ignoreCase = false)){
                            f.show(list.get(i))
                            break
                        }
                    }

                    BaseUIHelper.hideKeyboard(this)
                }

            } catch (e: Exception) {
                Helper.logException(this@ActivityMain, e)
            }
        }

        fun jumpToLastPreviousFragment(fullFragPackageNameThatStillExistInStack: String) {
            try {
                var indexTag: String? = null

                val f = getSupportFragmentManager().beginTransaction()
                var list = getSupportFragmentManager().getFragments()

                for(i in  0..(list.size - 1)){

                    if(list.get(i).isVisible){
                        f.hide(list.get(i))
                    }
                }

                for(i in  0..(list.size - 1)){

                    if(list.get(i).javaClass.name.equals(fullFragPackageNameThatStillExistInStack, ignoreCase = false)){
                        indexTag = list.get(i).tag
                        break;
                    }
                }

                if(indexTag == null){
                    onBackPressed()
                }else{

                    val currentIndex = MAIN_FLOW_INDEX
                    for(i in currentIndex downTo 0){
                        try {
                            if((MAIN_FLOW_TAG + i).equals(indexTag, ignoreCase = true)) break
                            getSupportFragmentManager().popBackStackImmediate()
                            MAIN_FLOW_INDEX = MAIN_FLOW_INDEX - 1
                        } catch (e: Exception) {
                            Helper.logException(this@ActivityMain, e)
                        }
                    }

                    list = getSupportFragmentManager().getFragments()
                    for(i in  0..(list.size - 1)){
                        if(list.get(i).tag.equals(indexTag, ignoreCase = false)){
                            f.show(list.get(i))
                            break
                        }
                    }

                    BaseUIHelper.hideKeyboard(this)
                }

            } catch (e: Exception) {
                Helper.logException(this@ActivityMain, e)
            }
        }

        fun jumpBackPreviousFragment2(howManyTimes: Int){
            try {
                for(i in 0..(howManyTimes-1)) {
                    getSupportFragmentManager().popBackStackImmediate()
                    MAIN_FLOW_INDEX = MAIN_FLOW_INDEX - 1
                }
            } catch (e: Exception) {
                Helper.logException(this@ActivityMain, e)
            }
        }

        fun setFragmentByReplace(frag: Fragment) {

            try {
                val f = getSupportFragmentManager().beginTransaction()
                MAIN_FLOW_INDEX = MAIN_FLOW_INDEX + 1
                f.replace(R.id.layoutFragment, frag, MAIN_FLOW_TAG + MAIN_FLOW_INDEX).addToBackStack(
                    MAIN_FLOW_TAG
                ).commitAllowingStateLoss()
                BaseUIHelper.hideKeyboard(this)
            } catch (e: Exception) {
                Helper.logException(this@ActivityMain, e)
            }

        }

        fun getCurrentFragmentByTag(): Fragment?{
            val fragmentManager = this@ActivityMain.getSupportFragmentManager()
            val fragments = fragmentManager.getFragments()
            if (fragments != null) {
                for (fragment in fragments) {
                    if (fragment != null && fragment!!.isVisible())
                        return fragment
                }
            }
            return null
        }

        fun clearFragment() {

            getSupportFragmentManager().popBackStack(MAIN_FLOW_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)

            for (i in MAIN_FLOW_INDEX downTo 0) {
                try {
                    val fragment = getSupportFragmentManager().findFragmentByTag(MAIN_FLOW_TAG + i)
                    if (fragment != null)
                        getSupportFragmentManager().beginTransaction().remove(fragment).commitNowAllowingStateLoss()
                } catch (e: Exception) {
                    Helper.logException(this@ActivityMain, e)
                }

            }

            getSupportFragmentManager().popBackStack("MAIN_TAB", FragmentManager.POP_BACK_STACK_INCLUSIVE)

            MAIN_FLOW_INDEX = 0
        }

        fun removeFragments(fragList: ArrayList<Fragment>) {

            var list = getSupportFragmentManager().getFragments()

            for (frag in list) {
                try {
                    for(fragx in fragList){

                        if(frag.tag.equals(fragx.tag)) {
                            val fragment = getSupportFragmentManager().findFragmentByTag(frag.tag)
                            if (fragment != null)
                                getSupportFragmentManager().beginTransaction().remove(fragment).commitNowAllowingStateLoss()
                        }
                    }
                } catch (e: Exception) {
                    Helper.logException(this@ActivityMain, e)
                }
            }

        }

        fun backToMainScreen(){
            clearFragment()
            triggerMainProcess()
        }

        fun resetAndGoToFragment(frag: Fragment) {
            clearFragment()
            setFragment(frag)
        }

        fun setOrShowExistingFragmentByTag(
            layoutId: Int,
            fragTag: String,
            backstackTag: String,
            newFrag: Fragment,
            listFragmentTagThatNeedToHide: ArrayList<String>
        ) {

            var foundExistingFragment = false

            val fragment = supportFragmentManager.findFragmentByTag(fragTag)
            val transaction = supportFragmentManager.beginTransaction()
            if (fragment != null) {
                for (i in 0 until supportFragmentManager.fragments.size) {

                    try {
                        val f = supportFragmentManager.fragments[i]

                        for (tag in listFragmentTagThatNeedToHide) {
                            try {
                                if (f.tag.toString().toLowerCase().equals(tag.toLowerCase())) {
                                    transaction.hide(f)
                                }
                            } catch (e: Exception) {
                                Helper.logException(this@ActivityMain, e)
                            }

                        }

                    } catch (e: Exception) {
                        Helper.logException(this@ActivityMain, e)
                    }

                }

                try {
                    transaction.show(fragment).commitAllowingStateLoss()
                } catch (e: Exception) {
                    try {
                        transaction.show(fragment).commitAllowingStateLoss()
                    } catch (e1: Exception) {
                        Helper.logException(this@ActivityMain, e)
                    }

                }

                foundExistingFragment = true

            }

            if (!foundExistingFragment) {
                setFragmentInFragment(layoutId, newFrag, fragTag, backstackTag)
            }

        }

        fun setFragmentInFragment(fragmentLayout: Int, frag: Fragment, tag: String, backstackTag: String) {
            try {
                supportFragmentManager.beginTransaction().add(fragmentLayout, frag, tag).addToBackStack(backstackTag)
                    .commitAllowingStateLoss()
                BaseUIHelper.hideKeyboard(this)
            } catch (e: Exception) {
                try {
                    supportFragmentManager.beginTransaction().add(fragmentLayout, frag, tag).addToBackStack(backstackTag)
                        .commitAllowingStateLoss()
                    BaseUIHelper.hideKeyboard(this)
                } catch (e1: Exception) {
                    Helper.logException(this@ActivityMain, e)
                }

            }

        }

        // Sometimes the last fragment in the list is null
        // Idk why
        val backstack: Fragment?
        get() {
            val list = getSupportFragmentManager().getFragments()
            return if (list != null && !list!!.isEmpty()) {
                if (list!!.get(list!!.size - 1) == null) list!!.get(list!!.size - 2) else list!!.get(list!!.size - 1)

            } else null

        }

        val currentFragment: Fragment
        get() = getSupportFragmentManager().findFragmentById(R.id.layoutFragment)!!


        val currentstack: Fragment?
        get() {
            val list = getSupportFragmentManager().getFragments()
            return if (list != null && !list!!.isEmpty()) {
                list!!.get(list!!.size - 1)
            } else null
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            LoginFragment().onActivityResult(requestCode, resultCode, data);

        }
        /******************************************
         * COMMON FUNCTIONS
         */


        /******************************************
         * LOGOUT FUNCTIONS
         */



    }
