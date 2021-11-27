package de.jet.jvm.extension.data

import java.nio.charset.Charset

/**
 * Returns the text of the web page at the given [url].
 * @param url the url of the web page, which will be downloaded
 * @return the text of the web page
 * @author Fruxz
 * @since 1.0
 */
fun getWebText(url: String, charset: Charset = Charsets.UTF_8) = url(url).readText(charset)