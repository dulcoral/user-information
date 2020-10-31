package com.unam.userinformation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.unam.userinformation.R
import com.unam.userinformation.databinding.FragmentUnamUserInformationBinding
import com.unam.userinformation.models.UserInformation

class UserInformationFragment : Fragment() {

    private var info: UserInformation? = null

    private lateinit var binding: FragmentUnamUserInformationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUnamUserInformationBinding.inflate(inflater)
        info = arguments?.getParcelable(USER_INFO)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.apply {
            textViewTitle.text = resources.getString(
                R.string.info_user_title, info?.fullName
            )

            textViewChineseZodiac.text = resources.getString(
                R.string.chinese_zodiac,
                info?.chineseZodiac?.get(0),
                info?.chineseZodiac?.get(1),
                info?.chineseZodiac?.get(2)
            )

            textViewZodiac.text = resources.getString(
                R.string.zodiac_text, info?.zodiac
            )

            textViewFavoriteSport.text = resources.getString(
                R.string.favorite_sport, info?.favoriteSport
            )

            editTextZipCode.setText(info?.zipCode)

            editTextUserAge.setText(info?.age)

            editTextRfc.setText(info?.rfc)
        }
    }

    companion object {
        private const val USER_INFO = "userInfo"

        fun getArgsBundle(
            userInfo: UserInformation
        ): Bundle {
            return bundleOf(
                USER_INFO to userInfo
            )
        }
    }
}