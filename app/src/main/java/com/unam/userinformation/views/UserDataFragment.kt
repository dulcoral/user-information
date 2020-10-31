package com.unam.userinformation.views

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.unam.userinformation.models.UserDataAction
import com.unam.userinformation.R
import com.unam.userinformation.databinding.FragmentUnamUserDataBinding
import com.unam.userinformation.models.ErrorType
import com.unam.userinformation.models.UserData
import com.unam.userinformation.models.UserInformation
import com.unam.userinformation.utils.DATE_FORMAT_PICKER
import com.unam.userinformation.utils.DatePicker
import com.unam.userinformation.utils.getDatefromCalendarFormatDash
import com.unam.userinformation.viewModels.UserDataViewModel
import com.unam.userinformation.viewModels.UserDataViewModelFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class UserDataFragment : Fragment() {

    private lateinit var binding: FragmentUnamUserDataBinding
    private lateinit var viewModel: UserDataViewModel
    private var listener: UserDataListener? = null

    private val pickerFormatter = SimpleDateFormat(DATE_FORMAT_PICKER)

    override fun onAttach(context: Context) {
        super.onAttach(context)

        listener = context as? UserDataListener
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUnamUserDataBinding.inflate(inflater)
        viewModel =
            ViewModelProvider(requireActivity(), UserDataViewModelFactory(requireContext())).get(
                UserDataViewModel::class.java
            )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initUi()
        bindViewModel()
    }

    private fun bindViewModel() {
        viewModel.getAction().observe(viewLifecycleOwner, Observer(this::handleAction))
    }

    private fun initUi() {
        var sports: List<String> = resources.getStringArray(R.array.sports_array).toList()
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            dropDownSportType.setupItems(sports)
            textInputLayoutBirthDate.editText?.setOnClickListener { showDatePicker() }
            buttonNext.setOnClickListener {
                viewModel.send(
                    UserData(
                        name = "${editTextName.text}",
                        lastName = "${editTextLastName.text}",
                        motherLastName = "${editTextMotherLastName.text}",
                        zipCode = "${editTextZipCode.text}",
                        sportSelected = dropDownSportType.textSelected,
                        dateOfBirth = "${editTextBirthDate.text}"
                    )
                )
            }
        }
    }

    private fun handleAction(action: UserDataAction) {
        when (action) {
            is UserDataAction.SendUserData -> listener?.onSendPersonalInfo(action.userData)
            is UserDataAction.ShowError -> showError(action.errorType)
        }
    }

    private fun showError(errorType: ErrorType) {
        when (errorType) {
            ErrorType.NAME_ERROR -> binding.editTextName.error = getString(R.string.name_error)
            ErrorType.LAST_NAME_ERROR -> binding.editTextLastName.error =
                getString(R.string.last_name_error)
            ErrorType.MOTHER_LAST_NAME_ERROR -> binding.editTextMotherLastName.error =
                getString(R.string.mother_last_name_error)
            ErrorType.ZIP_CODE_ERROR -> binding.editTextZipCode.error =
                getString(R.string.zip_code_error)
            ErrorType.DATE_OF_BIRTH_ERROR -> binding.editTextBirthDate.error =
                getString(R.string.name_error)
            ErrorType.SPORT_ERROR -> binding.textViewSports.error = getString(R.string.sport_error)
            else -> Toast.makeText(context, getString(R.string.generic_error), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun hideKeyboard(activity: Activity) {
        activity.currentFocus?.let {
            val inputMethodManager =
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
            activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        }
    }

    private fun showDatePicker() {
        hideKeyboard(requireActivity())
        DatePicker().let {
            it.arguments = Bundle().apply {
                putInt(
                    DatePicker.THEME_KEY,
                    R.style.HoloDatePickerDialog
                )
                putLong(DatePicker.MAX_DATE_KEY, Date().time)
            }
            it.setDatePickerListener(object : DatePicker.DatePickerListener {
                override fun onDateSelected(calendar: Calendar, dateSelected: String) {
                    viewModel.getAge(calendar)
                    pickerFormatter.parse(dateSelected)?.let { date ->
                        binding.editTextBirthDate.setText(getDatefromCalendarFormatDash(date))
                    }
                }
            })
            it.show(childFragmentManager, it.tag)
        }
    }

    interface UserDataListener {
        fun onSendPersonalInfo(userInformation: UserInformation)
    }
}