package com.maa.tiffens.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.maa.tiffens.R
import com.maa.tiffens.etc.Helper
import kotlinx.android.synthetic.main.mainfragment.*

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import q.rorbin.badgeview.Badge
import q.rorbin.badgeview.QBadgeView


class MainFragment : BaseFragment() {

    val MAINFRAGMENT_LAYOUT = R.layout.mainfragment

    private var currentTab = FIRST_TAB
    var cartTabBadge: Badge? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(MAINFRAGMENT_LAYOUT, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBottomNavigationView()
        initializingFlow()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
    }

    fun initializingFlow() {
        setCurrentItem(currentTab)
    }

    fun setCurrentTab(tab: Int) {
        currentTab = tab
    }


    fun setCurrentItem(which: Int) {

        resetBottomNavigationState()

        if (which == FIRST_TAB) {
            currentTab = FIRST_TAB
            setNavStateSelected(FIRST_TAB)
            home().setOrShowExistingFragmentByTag(
                R.id.mainLayoutFragment, "FIRST_TAB",
                "MAIN_TAB", HomeFragment(), Helper.listFragmentsMainTab
            )
        } else if (which == SECOND_TAB) {
            currentTab = SECOND_TAB
            setNavStateSelected(SECOND_TAB)
            home().setOrShowExistingFragmentByTag(
                R.id.mainLayoutFragment, "SECOND_TAB",
                "MAIN_TAB", HistoryFragment(), Helper.listFragmentsMainTab
            )

        } else if (which == THIRD_TAB) {
            currentTab = THIRD_TAB
            setNavStateSelected(THIRD_TAB)
            home().setOrShowExistingFragmentByTag(
                R.id.mainLayoutFragment, "THIRD_TAB",
                "MAIN_TAB", CartFragment(), Helper.listFragmentsMainTab
            )
        } else if (which == FOURTH_TAB) {
            currentTab = FOURTH_TAB
            setNavStateSelected(FOURTH_TAB)
            home().setOrShowExistingFragmentByTag(
                R.id.mainLayoutFragment, "FOURTH_TAB",
                "MAIN_TAB", ProfileFragment(), Helper.listFragmentsMainTab
            )
        }
    }

    fun initBottomNavigationView(){
        btmNavigation.setEnabled(true)
        btmNavigation.enableAnimation(true)
        btmNavigation.enableItemShiftingMode(false)
        btmNavigation.enableShiftingMode(false)

        btmNavigation.setOnNavigationItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(@NonNull item: MenuItem): Boolean {
                when(item.titleCondensed.toString().toLowerCase()){
                    "home" -> setCurrentItem(FIRST_TAB)
                    "history" -> setCurrentItem(SECOND_TAB)
                    "cart" -> setCurrentItem(THIRD_TAB)
                    "profile" -> setCurrentItem(FOURTH_TAB)
                }

                return false
            }
        })

        btmNavigation.setTextSize(12.0f)
    }

    fun addBadgeAt(position: Int, number: Int)
    {
        if(position == FOURTH_TAB) {

            if (cartTabBadge == null) {
                cartTabBadge = QBadgeView(context!!)
                    .setBadgeNumber(number)
                    .setGravityOffset(12F, 2F, true)
                    .bindTarget(btmNavigation.getBottomNavigationItemView(position))
            } else {
                cartTabBadge!!.setBadgeNumber(number)
            }
        }
    }

    fun resetBottomNavigationState(){
        setNavStateUnselected(FIRST_TAB)
        setNavStateUnselected(SECOND_TAB)
        setNavStateUnselected(THIRD_TAB)
        setNavStateUnselected(FOURTH_TAB)
    }

    fun setNavStateSelected(position : Int){
        when(position){
            FIRST_TAB -> {
                btmNavigation.setIconTintList(0, getResources()
                    .getColorStateList(R.color.themeColor))
                btmNavigation.setTextTintList(0, getResources()
                    .getColorStateList(R.color.themeColor))
            }

            SECOND_TAB -> {
                btmNavigation.setIconTintList(1, getResources()
                    .getColorStateList(R.color.themeColor))
                btmNavigation.setTextTintList(1, getResources()
                    .getColorStateList(R.color.themeColor))
            }

            THIRD_TAB -> {
                btmNavigation.setIconTintList(2, getResources()
                    .getColorStateList(R.color.themeColor))
                btmNavigation.setTextTintList(2, getResources()
                    .getColorStateList(R.color.themeColor))
            }

            FOURTH_TAB -> {
                btmNavigation.setIconTintList(3, getResources()
                    .getColorStateList(R.color.themeColor))
                btmNavigation.setTextTintList(3, getResources()
                    .getColorStateList(R.color.themeColor))
            }

        }
    }

    fun setNavStateUnselected(position : Int){
        when(position){
            FIRST_TAB -> {
                btmNavigation.setIconTintList(0, getResources()
                    .getColorStateList(R.color.Gray))
                btmNavigation.setTextTintList(0, getResources()
                    .getColorStateList(R.color.Gray))
            }

            SECOND_TAB -> {
                btmNavigation.setIconTintList(1, getResources()
                    .getColorStateList(R.color.Gray))
                btmNavigation.setTextTintList(1, getResources()
                    .getColorStateList(R.color.Gray))
            }

            THIRD_TAB -> {
                btmNavigation.setIconTintList(2, getResources()
                    .getColorStateList(R.color.Gray))
                btmNavigation.setTextTintList(2, getResources()
                    .getColorStateList(R.color.Gray))
            }

            FOURTH_TAB -> {
                btmNavigation.setIconTintList(3, getResources()
                    .getColorStateList(R.color.Gray))
                btmNavigation.setTextTintList(3, getResources()
                    .getColorStateList(R.color.Gray))
            }

        }
    }

    companion object {
        var FIRST_TAB = 0 //Homepage
        var SECOND_TAB = 1 //Pay
        var THIRD_TAB = 2 //De Era
        var FOURTH_TAB = 3 //Cart

    }

}
