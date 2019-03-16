package bohdan.sushchak.elementzonetest.internal

import java.io.IOException
import java.lang.Exception

class NoConnectivityException: IOException()

class LostArgumentsException: Exception()

class TokenNullException: Exception()
