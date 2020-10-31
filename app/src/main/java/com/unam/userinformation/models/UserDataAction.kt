package com.unam.userinformation.models

sealed class UserDataAction {
    data class SendUserData(val userData: UserData) : UserDataAction()
    data class ShowError(val errorType: ErrorType) : UserDataAction()
}