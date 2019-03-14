package bohdan.sushchak.elementzonetest.internal

import android.annotation.SuppressLint
import java.text.SimpleDateFormat


const val  API_DATE_FORMAT_PATTERN= "YYYY-mm-DD HH:MM:SS"
const val DATE_FORMAT_PATTERN = "dd.MM.yyyy"

@SuppressLint("SimpleDateFormat")
fun normalizeDate(dateStr: String): String {

    val parser = SimpleDateFormat(API_DATE_FORMAT_PATTERN)
    val formatter = SimpleDateFormat(DATE_FORMAT_PATTERN)
    return formatter.format(parser.parse(dateStr))
}