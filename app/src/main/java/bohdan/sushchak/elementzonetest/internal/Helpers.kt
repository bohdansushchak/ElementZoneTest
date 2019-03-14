package bohdan.sushchak.elementzonetest.internal

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun normalizeDate(dateStr: String): String {

    val parser = SimpleDateFormat(Constants.API_DATE_FORMAT_PATTERN)
    val formatter = SimpleDateFormat(Constants.DATE_FORMAT_PATTERN)
    return formatter.format(parser.parse(dateStr))
}

@SuppressLint("SimpleDateFormat")
fun formatDate(date: Date, pattern: String): String {
    val sdf = SimpleDateFormat(pattern)
    return sdf.format(date)
}
