package pl.pg.pwta.pwta2d3dmodels.model

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

    // 3D – OpenGL (ModelViewer3D)
    STL,
    PLY,

    UNKNOWN
}