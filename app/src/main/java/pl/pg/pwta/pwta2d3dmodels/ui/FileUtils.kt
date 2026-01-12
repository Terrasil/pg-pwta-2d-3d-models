package pl.pg.pwta.pwta2d3dmodels.util

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import java.util.Date
import pl.pg.pwta.pwta2d3dmodels.model.ModelFormat
import pl.pg.pwta.pwta2d3dmodels.model.ModelInfo

fun readModelInfoFromUri(
    context: Context,
    uri: Uri
): ModelInfo {

    val resolver = context.contentResolver

    var fileName = "unknown"
    var fileSize = -1L
    var mimeType: String? = null
    var createdAt: Date? = null
    var modifiedAt: Date? = null

    resolver.query(uri, null, null, null, null)?.use { cursor ->
        if (cursor.moveToFirst()) {
            fun getLong(name: String): Long? {
                val idx = cursor.getColumnIndex(name)
                return if (idx >= 0 && !cursor.isNull(idx)) cursor.getLong(idx) else null
            }
            fun getString(name: String): String? {
                val idx = cursor.getColumnIndex(name)
                return if (idx >= 0 && !cursor.isNull(idx)) cursor.getString(idx) else null
            }
            getString(OpenableColumns.DISPLAY_NAME)?.let {
                fileName = it
            }
            getLong(OpenableColumns.SIZE)?.let {
                fileSize = it
            }
            getLong("date_added")?.let {
                createdAt = Date(it * 1000) // sekundy → ms
            }
            getLong("last_modified")?.let {
                modifiedAt = Date(it)
            }
        }
    }

    mimeType = resolver.getType(uri)

    val extension = fileName
        .substringAfterLast('.', "")
        .lowercase()

    val format = when (extension) {
        "png" -> ModelFormat.PNG
        "jpg", "jpeg" -> ModelFormat.JPEG
        "svg" -> ModelFormat.SVG
        "webp" -> ModelFormat.WEBP
        "obj" -> ModelFormat.OBJ
        "gltf" -> ModelFormat.GLTF
        "glb" -> ModelFormat.GLB
        "ply" -> ModelFormat.PLY
        "stl" -> ModelFormat.STL
        else -> ModelFormat.UNKNOWN
    }

    val isReadable = try {
        resolver.openInputStream(uri)?.close()
        true
    } catch (_: Exception) {
        false
    }

    val isWritable = try {
        resolver.openOutputStream(uri)?.close()
        true
    } catch (_: Exception) {
        false
    }

    return ModelInfo(
        fileName = fileName,
        filePath = uri.toString(),
        extension = extension,
        format = format,
        uri = uri.toString(),
        isEmbedded = false,
        fileSizeBytes = fileSize,
        createdAt = createdAt,
        modifiedAt = modifiedAt,
        accessedAt = null,
        mimeType = mimeType,
        isReadable = isReadable,
        isWritable = isWritable
    )
}
