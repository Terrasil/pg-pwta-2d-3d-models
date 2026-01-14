package pl.pg.pwta.pwta2d3dmodels.model

sealed class AssetInfo {
    abstract val base: ModelInfo
}

data class ImageAsset(
    override val base: ModelInfo,
    val imageInfo: ImageInfo
) : AssetInfo()

data class Model3DAsset(
    override val base: ModelInfo
) : AssetInfo()