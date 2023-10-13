package com.linhtetko.monthly.ui.locale.language

import kotlinx.serialization.Serializable

@Serializable
data class Language(
    val appName: String,
    val lblName: String,
    val lblEmail: String,
    val lblPhone: String,
    val lblAddImage: String,
    val lblAdd: String,
    val lblRegister: String,
    val lblRegisterAcc: String,
    val lblLoginAcc: String,
    val lblPassword: String,
    val lblLogin: String,
    val lblAmountToPay: String,
    val lblTotal: String,
    val lblEach: String,
    val lblKs: String,
    val lblFlatmates: String,
    val lblAddNew: String,
    val lblGeneralCost: String,
    val lblCategory: String,
    val lblCost: String,
    val lblBoughtItems: String,
    val lblEmptyCategory: String,
    val lblEmptyBoughtItems: String,
    val lblProfileDetail: String,
    val lblAddCategoryName: String,
    val lblAddCost: String,
    val lblPlsWait: String,
    val lblSomethingWrong: String,
    val lblNameOrEmailRequired: String,
    val lblUsedMoney: String,
    val lblSetting: String,
    val lblChangeLang: String,
    val lblLogout: String,
    val lblChooseLang: String,
    val lblChangeColor: String,
    val lblChooseColor: String
){
    companion object{
        const val ENGLISH = "eng"
        const val MYANMAR = "my"
    }
}
