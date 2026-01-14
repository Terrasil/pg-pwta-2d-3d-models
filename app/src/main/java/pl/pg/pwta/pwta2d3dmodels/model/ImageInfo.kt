package pl.pg.pwta.pwta2d3dmodels.model

import java.util.Date

data class ImageInfo(
    val widthPx: Int?,
    val heightPx: Int?,
    val viewBoxWidth: Float?,
    val viewBoxHeight: Float?,
    val aspectRatio: Float?,
    val isVector: Boolean,
    val hasAlpha: Boolean?,
    val colorModel: ImageColorModel?,
    val bitDepthPerChannel: Int?,
    val dpiX: Int?,
    val dpiY: Int?,
    val exifOrientation: Int?,
    val exifDateTime: Date?,
    val exifDateTimeOriginal: Date?,
    val exifDateTimeDigitized: Date?,
    val subSecTime: String?,
    val cameraMake: String?,
    val cameraModel: String?,
    val software: String?,
    val lensMake: String?,
    val lensModel: String?,
    val exposureTime: Double?,
    val fNumber: Double?,
    val isoSpeed: Int?,
    val focalLength: Double?,
    val exposureBias: Double?,
    val meteringMode: Int?,
    val whiteBalance: Int?,
    val flash: Int?,
    val brightnessValue: Double?,
    val contrast: Int?,
    val saturation: Int?,
    val sharpness: Int?,
    val hasGpsMetadata: Boolean?,
    val gpsLatitude: Double?,
    val gpsLongitude: Double?,
    val gpsAltitude: Double?,
    val gpsTimestamp: Date?,
    val mimeType: String
)

enum class ImageColorModel {
    RGB,
    RGBA,
    GRAYSCALE,
    UNKNOWN
}