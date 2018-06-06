package br.com.luan2.lgutilsk.utils

import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by luan silva on 06/06/18.
 */



 fun JSONObject.getStringOrNull(name: String): String? = if (has(name)) getString(name) else null


 fun JSONObject.getIntOrNull(name: String): Int? = if (has(name)) getInt(name) else null

 fun JSONObject.getBooleanOrNull(name: String): Boolean? = if (has(name)) getBoolean(name) else null


 fun JSONObject.getDoubleOrNull(name: String): Double? = if (has(name)) getDouble(name) else null


 fun JSONObject.getLongOrNull(name: String): Long?= if (has(name)) getLong(name) else null


 fun JSONObject.getJSONObjectOrNull(name: String): JSONObject? = if (has(name)) getJSONObject(name) else null


 fun JSONObject.getJSONArrayOrNull(name: String): JSONArray? = if (has(name)) getJSONArray(name) else null
