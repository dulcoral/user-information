package com.unam.userinformation.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.unam.userinformation.R
import com.unam.userinformation.models.UserInformation

class UserDataActivity : AppCompatActivity(),
    UserDataFragment.UserDataListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unam_user_data)
    }

    private fun currentNavController(): NavController =
        findNavController(R.id.navHostFragment)

    override fun onSupportNavigateUp(): Boolean {
        return when (currentNavController().currentDestination?.id) {
            R.id.user_data -> {
                finish()
                false
            }
            else -> currentNavController().navigateUp()
        }
    }

    override fun onSendPersonalInfo(userInfo: UserInformation) {
        val args = UserInformationFragment.getArgsBundle(userInfo)
        currentNavController().navigate(R.id.action_to_user_information, args)
    }
}