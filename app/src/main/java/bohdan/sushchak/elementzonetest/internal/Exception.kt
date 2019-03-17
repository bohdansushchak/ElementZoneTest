package bohdan.sushchak.elementzonetest.internal

import java.io.IOException
import java.lang.Exception

class NoConnectivityException: IOException()

class BadRequestException(message: String?): IOException(message)

class UnathorizedException: Exception()

