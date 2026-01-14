package pl.pg.pwta.pwta2d3dmodels.model

import java.util.Date

data class ModelInfo(
    val fileName: String,
    val filePath: String,
    val extension: String,
    val format: ModelFormat,
    val uri: String?,
    val isEmbedded: Boolean,
    val fileSizeBytes: Long,
    val createdAt: Date?,
    val modifiedAt: Date?,
    val accessedAt: Date?,
    val mimeType: String?,
    val isReadable: Boolean,
    val isWritable: Boolean
)

enum class ModelFormat {
    // 2D
    PNG,
    JPEG,
    SVG,
    WEBP,

    // 3D – Filament
    OBJ,
    GLTF,
    GLB,

    // 3D – Optional
    STL,
    PLY,

    UNKNOWN
}