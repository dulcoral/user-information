package com.unam.userinformation.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.unam.userinformation.R
import com.unam.userinformation.models.UserData
import com.unam.userinformation.models.UserDataAction
import com.unam.userinformation.models.ErrorType
import com.unam.userinformation.models.UserInformation
import com.unam.userinformation.utils.SingleLiveEvent
import com.unam.userinformation.utils.firstVowel
import com.unam.userinformation.utils.oneLine
import com.unam.userinformation.utils.trimUpperCase
import java.util.Calendar

class UserDataViewModel(context: Context?) : ViewModel() {
    private val _action = SingleLiveEvent<UserDataAction>()
    fun getAction(): LiveData<UserDataAction> = _action

    private val sings = context?.resources?.getStringArray(R.array.signs_array)

    private val elements = context?.resources?.getStringArray(R.array.elements_array)

    private val polarities = context?.resources?.getStringArray(R.array.polarity_array)

    private val zodiacs = context?.resources?.getStringArray(R.array.zodiac_array)

    var userAge: String? = null

    private fun validateForm(userData: UserData): Boolean {
        return validateName(userData.name) &&
            validateLastName(userData.lastName) &&
            validateMotherLastName(userData.motherLastName) &&
            validateDateOfBirth(userData.dateOfBirth) &&
            validateSport(userData.sportSelected) &&
            validateZipCode(userData.zipCode)
    }

    private fun validateName(name: String?): Boolean {
        if (name.isNullOrEmpty()) {
            _action.value = UserDataAction.ShowError(ErrorType.NAME_ERROR)
            return false
        }
        return true
    }

    private fun validateLastName(lastName: String?): Boolean {
        if (lastName.isNullOrEmpty()) {
            _action.value = UserDataAction.ShowError(ErrorType.LAST_NAME_ERROR)
            return false
        }
        return true
    }

    private fun validateMotherLastName(motherLastName: String?): Boolean {
        if (motherLastName.isNullOrEmpty()) {
            _action.value = UserDataAction.ShowError(ErrorType.MOTHER_LAST_NAME_ERROR)
            return false
        }
        return true
    }

    private fun validateSport(sport: String?): Boolean {
        if (sport.isNullOrEmpty()) {
            _action.value = UserDataAction.ShowError(ErrorType.SPORT_ERROR)
            return false
        }
        return true
    }

    private fun validateDateOfBirth(date: String?): Boolean {
        if (date.isNullOrEmpty()) {
            _action.value = UserDataAction.ShowError(ErrorType.DATE_OF_BIRTH_ERROR)
            return false
        }
        return true
    }

    private fun validateZipCode(code: String?): Boolean {
        if (code.isNullOrEmpty() || code.length != ZIP_CODE_LENGTH) {
            _action.value = UserDataAction.ShowError(ErrorType.ZIP_CODE_ERROR)
            return false
        }
        return true
    }

    fun getAge(birthday: Calendar) {
        val today = Calendar.getInstance()
        var age = today.get(Calendar.YEAR) - birthday.get(Calendar.YEAR)
        if (today.get(Calendar.DAY_OF_YEAR) < birthday.get(Calendar.DAY_OF_YEAR)) {
            age--
        }
        val ageInt = age
        userAge = ageInt.toString()
    }

    private fun generateRFC(
        name: String,
        lastName: String,
        mothersLastName: String,
        bornDate: String
    ): String {
        val upperLastName = lastName.trimUpperCase()
        val dateArray = bornDate.split(DATE_DELIMITER)
        return """
         ${upperLastName[0]}
         ${upperLastName.substring(1).firstVowel()}
         ${mothersLastName.trimUpperCase()[0]}
         ${name.trimUpperCase()[0]}
         ${dateArray[2].takeLast(2)}
         ${dateArray[1]}
         ${dateArray[0]}
     """.oneLine()
    }

    private fun gregorianToSexagenarian(year: Int) = (year - 3) - (60 * ((year - 3) / 60))

    private fun getZodiacalChinese(bornDate: String): List<String?> {
        val year = bornDate.split(DATE_DELIMITER)[2].toInt()

        val animal = sings?.get(gregorianToSexagenarian(year) % 12)
        val element = elements?.get((year % 10) / 2)
        val polarity = polarities?.get(year % 2)
        return listOf(animal,element,polarity)
    }

    private fun getZodiacSign(bornDate: String): String {
        val arrayBornDate = bornDate.split(DATE_DELIMITER)
        val day = arrayBornDate[0].toInt()
        return when (arrayBornDate[1].toInt()) {
            12 -> if (day < 22) zodiacs?.get(8).orEmpty() else zodiacs?.get(9).orEmpty()
            1 -> if (day < 20) zodiacs?.get(9).orEmpty() else zodiacs?.get(10).orEmpty()
            2 -> if (day < 19) zodiacs?.get(10).orEmpty() else zodiacs?.get(11).orEmpty()
            3 -> if (day < 21) zodiacs?.get(11).orEmpty() else zodiacs?.get(0).orEmpty()
            4 -> if (day < 20) zodiacs?.get(0).orEmpty() else zodiacs?.get(1).orEmpty()
            5 -> if (day < 21) zodiacs?.get(1).orEmpty() else zodiacs?.get(2).orEmpty()
            6 -> if (day < 21) zodiacs?.get(2).orEmpty() else zodiacs?.get(3).orEmpty()
            7 -> if (day < 23) zodiacs?.get(3).orEmpty() else zodiacs?.get(4).orEmpty()
            8 -> if (day < 23) zodiacs?.get(4).orEmpty() else zodiacs?.get(5).orEmpty()
            9 -> if (day < 23) zodiacs?.get(5).orEmpty() else zodiacs?.get(6).orEmpty()
            10 -> if (day < 23) zodiacs?.get(6).orEmpty() else zodiacs?.get(7).orEmpty()
            11 -> if (day < 22) zodiacs?.get(7).orEmpty() else zodiacs?.get(8).orEmpty()
            else -> {
                _action.value = UserDataAction.ShowError(ErrorType.OTHER)
                ""
            }
        }
    }

    fun send(userData: UserData) {
        if (validateForm(userData)) {
            _action.value =
                UserDataAction.SendUserData(
                    UserInformation(
                        fullName = "${userData.name} ${userData.lastName} ${userData.motherLastName}",
                        age = userAge.orEmpty(),
                        zodiac = getZodiacSign(userData.dateOfBirth.orEmpty()),
                        chineseZodiac = getZodiacalChinese(userData.dateOfBirth.orEmpty()),
                        favoriteSport = userData.sportSelected,
                        rfc = generateRFC(
                            userData.name.orEmpty(),
                            userData.lastName.orEmpty(),
                            userData.motherLastName.orEmpty(),
                            userData.dateOfBirth.orEmpty()
                        ),
                        zipCode = userData.zipCode
                    )
                )
        }
    }

    companion object {
        private const val ZIP_CODE_LENGTH = 5
        private const val DATE_DELIMITER = "/"
    }
}