package com.unam.userinformation.models

sealed class UserDataAction {
    data class SendUserData(val userData: UserInformation) : UserDataAction()
    data class ShowError(val errorType: ErrorType) : UserDataAction()
}