package com.example.qrapp.model
import android.annotation.SuppressLint
import android.os.Parcelable

import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

@SuppressLint("ParcelCreator")
@Parcelize
data class Profile(
    val barcode: String?,
    val birthday: String?,
    val cancelDate: String?,
    val cancelReason: String?,
    val companyName: String?,
    val documents: List<Document>,
    val endDate: String?,
    val firstName: String?,
    val guid: String?,
    val id: Int?,
    val isCancelled: Boolean?,
    val itemId: Int?,
    val lastName: String?,
    val middleName: String?,
    val objectName: String?,
    val passId: Int?,
    val photoFileName: String?,
    val position1: String?,
    val position2: String?,
    val startDate: String?,
    val subcontractorName: String?,
    val terminalCode: String?,
    val violations: List<String>
) : Parcelable{
    fun isInvalid():Boolean = isCancelled!! || parser.parse(endDate).before(Date())
    fun getInvalidDate(): String =
        when {
            cancelDate == null -> endDate!!
            parser.parse(endDate).before(parser.parse(cancelDate)) -> endDate!!
            else -> cancelDate!!
        }
}

@SuppressLint("ParcelCreator")
@Parcelize
data class Document(
    val expirationDate: String?,
    val guid: String?,
    val id: Int?,
    val issueDate: String?,
    val name: String?,
    val number: String?,
    val passId: Int?,
    val uploadedFileExt: String?
) : Parcelable