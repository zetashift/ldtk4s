//> using scala "3.1.2"
//> using lib "io.circe::circe-core::0.14.1"
//> using lib "io.circe::circe-generic::0.14.1"
//> using lib "io.circe::circe-parser::0.14.1"
//> using options "-Xmax-inlines", "64"

package zetashift.ldtk4s

import io.circe.Decoder

/** This is the root of any LDtk project JSON file. It contains: the project
  * settings, an array of levels and a group of definitions
  *
  * @param bgColor
  *   Project background color as a hex color
  * @param defs
  *   A structure containing all the definitions for this project
  * @param externalLevels
  *   If `true`, one file will be saved for the project and one file in a
  *   sub-folder for each level
  * @param jsonVersion
  *   File format version
  * @param levels
  *   All levels, The order of this array is only relevant for
  *   `LinearHorizontal` and `leinearVertical` world layouts.
  * @param worlds
  *   An array containing worlds, each world containing levels and layout
  *   settings.
  */
case class LdtkJsonRoot(
    bgColor: String,
    defs: Definitions,
    externalLevels: Boolean,
    jsonVersion: String,
    levels: Vector[Level],
    worlds: Vector[World]
) derives Decoder

// Describes how levels are organized, ie linearly or in 2D space.
enum WorldLayout:
  case Free, GridVania, LinearHorizontal, LinearVertical

/** A World contains multiple levels, and it has its own layout settings
  * @param identifier
  *   User defined unique identifier
  * @param iid
  *   Unique instance identifier
  * @param levels
  *   An array of `Level`s. All levels from this world
  * @param worldGridHeight
  *   Height of the world grid in pixels
  * @param worldGridWidth
  *   Width of the world grid in pixels
  * @param worldLayout
  *   An enum that describes how levels are organized in this project (ie.
  *   linearly or in a 2D space)
  */
case class World(
    identifier: String,
    iid: String,
    levels: Vector[Level],
    worldGridHeight: Int,
    worldGridWidth: Int,
    worldLayout: WorldLayout
) derives Decoder

/** A level contains all the level data, it can be found in 2 distinct forms:
  *   - If "Separate level files" is disabled then full level data is embedded
  *     in the main project JSON file.
  *   - Else level data is stored in separate standalone `.ldtk` files, one per
  *     level.
  *
  * @param __bgColor
  *   Background color of the level
  * @param __bgPos
  *   Position information of the background image, if there is one
  * @param __neighbors
  *   An array listing all other levels touching this one on the world map
  * @param bgRelPath
  *   An optional relative path to the level background image
  * @param externalRelPath
  *   This value is not null, if the project option "Save levels separately" is
  *   enabled. In that case, this relative path points to the level JSON file
  * @param fieldInstances
  *   An array containing this level custom field values
  * @param identifier
  *   User defined unique identifier
  * @param iid
  *   Unique instance identifier
  * @param layerInstances
  *   An array containing all Layer instances. If the project option "save
  *   levels" separately is enabled, this field will be null!
  * @param pxHei
  *   Height of the level in pixels
  * @param pxWid
  *   Width of the level in pixels
  * @param uid
  *   Unique integer identifier
  * @param worldDepth
  *   Index that represents the "depth" of the level in the world. Default is 0,
  *   greater means "above" and lower means "below"
  * @param worldX
  *   World X coordinate in pixels
  * @param worldY
  *   World Y coordinate in pixels
  */
case class Level(
    __bgColor: String,
    __bgPos: Option[BackgroundPosition],
    __neighbours: Vector[Neighbour],
    bgRelPath: Option[String],
    externalRelPath: Option[String],
    fieldInstances: Vector[FieldInstance],
    identifier: String,
    iid: String,
    layerInstances: Option[Vector[LayerInstance]],
    pxHei: Int,
    pxWid: Int,
    uid: Int,
    worldDepth: Int,
    worldX: Int,
    worldY: Int
) derives Decoder

/** Level data
  * @param __cHei
  *   Grid based height
  * @param __cWid
  *   Grid based width
  * @param __gridSize
  *   Grid size
  * @param __identifier
  *   Layer definition identifier
  * @param __opacity
  *   Layer opacity as a float between 0 and 1
  * @param __pxTotalOffsetX
  *   Total layer X-offset, including both instance and definition offsets
  * @param __pxTotalOffsetY
  *   Total layer Y-offset, including both instance and definition offsets
  * @param __tilesetDefUid
  *   The definition UID of corresponding Tileset, if any
  * @param __tilesetRelPath
  *   The relative path to corresponding Tileset, if any
  * @param __type
  *   Layer type of either `IntGrid`, `Entities`, `Tiles` or `AutoLayer`
  * @param autoLayerTiles
  *   An array containing all tiles generated by Auto-layer rules.
  * @param entityInstances
  *   An array of `EntityInstance`s, only for Entity layer types.
  * @param gridTiles
  *   An array of `TileInstance`s, only for Tile layers
  * @param iid
  *   Unique layer instance identifier
  * @param layerDefUid
  *   Reference the layer definition UID.
  * @param levelId
  *   Reference to the UID of level containing this layer instance
  * @param overrideTilesetUId
  *   This layer can use another tileset by overriding the tileset UID here
  * @param pxOffsetX
  *   X offset in pixels to render this layer, usually 0
  * @param pxOffsetY
  *   Y offset in pixels to render this layer, usually 0
  * @param visible
  *   Layer instance visibility
  */
case class LayerInstance(
    __cHei: Int,
    __cWid: Int,
    __gridSize: Int,
    __identifier: String,
    __opacity: Float,
    __pxTotalOffsetX: Int,
    __pxTotalOffsetY: Int,
    __tilesetDefUid: Option[Int],
    __tilesetRelPath: Option[String],
    __type: String,
    autoLayerTiles: Vector[TileInstance],
    entityInstances: Vector[EntityInstance],
    gridTiles: Vector[TileInstance],
    iid: String,
    intGridCsv: Vector[Int],
    layerDefUid: Int,
    levelId: Int,
    overrideTilesetUId: Option[Int],
    pxOffsetX: Int,
    pxOffsetY: Int,
    visible: Boolean
) derives Decoder

/** A TileInstance represents a single tile from a given Tileset.
  * @param f
  *   "Flip bits", a 2-bits integer to represent the mirror transformations of
  *   the tile
  *   - Bit 0 = X flip
  *   - Bit 1 = Y flip
  * @param px
  *   Pixel coordinates of the tile in the layer, (x,y) formatted
  * @param src
  *   Pixel coordinates of the tile in the tileset. (x, y) formatted
  * @param t
  *   The Tile ID in the corresponding tileset
  */
case class TileInstance(f: Int, px: Vector[Int], src: Vector[Int], t: Int)
    derives Decoder

/** Contains the actual data for an Entity
  *
  * @param __grid
  *   Grid-based coordinates, in (x, y) coordinates
  * @param __identifier
  *   Entity definition identifier
  * @param __pivot
  *   Pivot coordinates in (x, y) coordinates of the Entity
  * @param __smartColor
  *   The entity "smart" color
  * @param __tags
  *   Array of tags defined in the EntityDefinition
  * @param __tile
  *   Optional `TilesetRect` used to display this entity
  * @param defUid
  *   Reference of the EntityDefinition UID
  * @param fieldInstances
  *   An array of all custom fields and their values
  * @param height
  *   Entity height in pixels
  * @param iid
  *   Unique instance identifier
  * @param px
  *   Pixel coordinates, in (x, y) format, in current level coordinate space.
  * @param width
  *   Entity width in pixels.
  */
case class EntityInstance(
    __grid: Vector[Int],
    __identifier: String,
    __pivot: Vector[Float],
    __smartColor: String,
    __tags: Vector[String],
    __tile: Option[TilesetRectangle],
    defUid: Int,
    fieldInstances: Vector[FieldInstance],
    height: Int,
    iid: String,
    px: Vector[Int],
    width: Int
) derives Decoder

/** Contains the actual data for a Field
  * @param __identifier
  *   Field definition identifier
  * @param __tile
  *   Optional TilesetRect used to display this field
  * @param __value
  *   Actual value of the field instance, the value type varies depending on
  *   __type
  * @param defUid
  *   Reference of the FieldDefinition UID
  */
case class FieldInstance(
    __identifier: String,
    __tile: Option[TilesetRectangle],
    __type: String,
    defUid: Int
) derives Decoder

/** This object is used in Field instances to describe an EntityRef value
  *
  * @param entityIid
  *   IID of the refered EntityInstance
  * @param layerIid
  *   IID of the LayerInstance containing the refered EntityInstance
  * @param levelIid
  *   IID of the Level containing the refered EntityInstance
  * @param worldIid
  *   IID of the World containing the refered EntityInstance
  */
case class FieldInstanceEntityReference(
    entityIid: String,
    layerIid: String,
    levelIid: String,
    worldIid: String
)

/** A grid-based coordinate used in Field values
  *
  * @param cx
  *   X grid-based coordinate
  * @param cy
  *   Y grid-based coordinate
  */
case class FieldInstanceGridPoint(
    cx: Int,
    cy: Int
)

/** Position information of the background image
  *
  * @param cropRect
  *   An array of 4 float values describing the cropped sub-rectangle of the
  *   displayed background image. This cropping happens when original is larger
  *   than the level bounds. Format: [cropX, cropY, cropWidth, cropHeight]
  * @param scale
  *   An array containing the [scaleX, scaleY] values of the cropped background
  *   image.
  * @param topLeftPixel
  *   An array containing the [x, y] pixel coordinates of the top-left corner
  *   oif the cropped background image.
  */
case class BackgroundPosition(
    cropRect: Vector[Float],
    scale: Vector[Float],
    topLeftPx: Vector[Int]
) derives Decoder

// Describes the fields in a "neighbor", a level touching another level on the world map
case class Neighbour(dir: String, levelIid: String)
    derives Decoder

/** A definition describes how each component(layers, entities etc) is.
  * @param entities
  *   All entities definitions
  * @param enums
  *   All internal enums
  * @param externalEnums
  *   Exactly the same as enums, but they have an `relPath` to point to an
  *   external source file
  * @param layers
  *   All layer defintions
  * @param levelFields
  *   All custom fields available to all levels
  * @param tilesets
  *   All tilesets
  */
case class Definitions(
    entities: Vector[EntityDefinition],
    enums: Vector[EnumDefinition],
    externalEnums: Vector[EnumDefinition],
    layers: Vector[LayerDefinition],
    levelFields: Vector[FieldDefinition],
    tilesets: Vector[TilesetDefinition]
)

// Extra IntGrid information
case class IntGridValueDefinition(
    color: String,
    identifier: Option[String],
    value: Int
) derives Decoder

/** @param __type
  *   Type of the layer (IntGrid, Entities, Tiles, AutoLayer)
  * @param autoSourceLayerDefUid
  *   Only matters for auto-layers,
  * @param displayOpacity
  *   Opacity of the layer (0 to 1.0)
  * @param gridSize
  *   Width and height of the grid in pixels
  * @param identifier
  *   User defined unique identifier
  * @param intGridValues
  *   An array that defines extra optional info for each IntGrid value
  * @param parallaxFactorX
  *   Parallax horizontal factor which affects the scrolling speed of this layer
  * @param parallaxFactorY
  *   Parallax vertical factor which affects the scrolling speed of this layer
  * @param parallaxScaling
  *   If true, a layer with a parallax factor will also be scaled up/down
  *   accordingly
  * @param pxOffsetX
  *   X offset of the layer, in pixels
  * @param pxOffsetY
  *   Y offset of the layer, in pixels
  * @param tilesetDefUid
  *   Reference to the default Tileset UID being used by this layer definition
  * @param uid
  *   Unique Int identifier
  */
case class LayerDefinition(
    __type: String,
    autoSourceLayerDefUid: Option[Int],
    displayOpacity: Float,
    gridSize: Int,
    identifier: String,
    intGridValues: Vector[IntGridValueDefinition],
    parallaxFactorX: Float,
    parallaxFactorY: Float,
    parallaxScaling: Boolean,
    pxOffsetX: Int,
    pxOffsetY: Int,
    tilesetDefUid: Option[Int],
    uid: Int
) derives Decoder

//An enum describing how the Entity tile is rendered inside the Entity bounds
enum TileRenderMode:
  case Cover
  case FitInside
  case FullSizeCropped
  case FullSizeUncropped
  case NineSlice
  case Repeat
  case Stretch

object TileRenderMode:
  given Decoder[TileRenderMode] =
    Decoder.decodeString.emap {
      case "Cover"             => Right(Cover)
      case "FitInside"         => Right(FitInside)
      case "FullSizeCropped"   => Right(FullSizeCropped)
      case "FullSizeUncropped" => Right(FullSizeUncropped)
      case "NineSlice"         => Right(NineSlice)
      case "RepeatSlice"       => Right(Repeat)
      case "Stretch"           => Right(Stretch)
      case x                   => Left(s"No valid TileRenderMode $x")
    }

/** @param color
  *   Base entity color
  * @param height
  *   Pixel height
  * @param identifier
  *   User defined unique identifier
  * @param nineSliceBorders
  *   An array of 4 dimensions for the up/right/down/left borders
  * @param pivotX
  *   Pivot X coordinate (from 0 to 1.0)
  * @param pivotY
  *   Pivot Y coordinate (from 0 to 1.0)
  * @param tileRect
  *   An object representing a rectangle from an existing Tileset
  * @param tileRenderMode
  *   An enum describing how the Entity tile is rendered inside the Entity
  *   bounds
  * @param tilesetId
  *   Tileset ID used for optional tile display
  * @param uid
  *   Unique Int identifier
  * @param width
  *   Pixel width
  */
case class EntityDefinition(
    color: String,
    height: Int,
    identifier: String,
    nineSliceBorders: Vector[Int],
    pivotX: Float,
    pivotY: Float,
    tileRect: Option[TilesetRectangle],
    tileRenderMode: TileRenderMode,
    tilesetId: Option[Int],
    uid: Int,
    width: Int
) derives Decoder

// This is mostly used for LDtk editor so it can be safely ignored, according to the docs
case class FieldDefinition(
    description: String,
    title: String
) derives Decoder

/** A tileset rectangle represents a custom sub rectangle in a Tileset image
  * @param h
  *   Height in pixels
  * @param tilesetUid
  *   UID of the tileset
  * @param w
  *   Width in pixels
  * @param x
  *   X pixels coordinate of the top-left corner in the Tileset image
  * @param y
  *   Y pixels coordinate of the top-left corner in the Tileset image
  */
case class TilesetRectangle(
    h: Int,
    tilesetUid: Int,
    w: Int,
    x: Int,
    y: Int
) derives Decoder

case class TileCustomMetaData(data: String, tileId: Int) derives Decoder
case class EnumTagValue(enumValueId: String, tileIds: Vector[Int])
    derives Decoder

enum EmbedAtlas:
  case LdtkIcons

object EmbedAtlas:
  given Decoder[EmbedAtlas] =
    Decoder.decodeString.emap {
      case "LdtkIcons" => Right(LdtkIcons)
      case x           => Left(s"No valid EmbedAtlas: $x")
    }

/** The most important definition. It contains some extra information about each
  * integrated tileset.
  *
  * @param __cHei
  *   Grid based height
  * @param __cWid
  *   Grid based width
  * @param customData
  *   An array of custom tile metadata
  * @param embedAtlas
  *   If this value is set, then it means that this atlas uses an internal LDtk
  *   atlas image instead of a loaded one
  * @param enumTags
  *   Tileset tags using enum values specified by `tagsSourceEnumId`. This array
  *   contains 1 element per enum value
  * @param identifier
  *   User defined unique identifier
  * @param padding
  *   Distance in pixels from image borders
  * @param pxHei
  *   Image height in pixels
  * @param pxWid
  *   Image width in pixels
  * @param relPath
  *   Path to the source file, relative to the current project JSON file
  * @param spacing
  *   Space in pixels between all tiles
  * @param tags
  *   An array of user-defined tags to organize the Tilesets
  * @param tagsSourceEnumUid
  *   Optional enum definition UID used for this tileset metadata
  * @param tileGridSize
  * @param uid
  *   Unique Int identifier
  */
case class TilesetDefinition(
    __cHei: Int,
    __cWid: Int,
    customData: Vector[TileCustomMetaData],
    embedAtlas: Option[EmbedAtlas],
    enumTags: Vector[EnumTagValue],
    identifier: String,
    padding: Int,
    pxHei: Int,
    pxWid: Int,
    relPath: Option[String],
    spacing: Int,
    tags: Vector[String],
    tagsSourceEnumUid: Option[Int],
    tileGridSize: Int,
    uid: Int
) derives Decoder

/** All possible enum values structure
  * @param __tileSrcRect
  *   An array of 4 Int values that refers to the tile in the tileset image
  * @param color
  *   Optional color
  * @param id
  *   Enum value
  * @param tileId
  *   The optional ID of the tile
  */
case class EnumValueDefinition(
    __tileSrcRect: Option[Vector[Int]],
    color: Int,
    id: String,
    tileId: Option[Int]
) derives Decoder

/** @param externalRelPath
  *   Relative path to the external file providing this enum
  * @param iconTilesetUid
  *   Tileset UID if provided
  * @param identifier
  *   User defined unique identifier
  * @param tags
  *   An array of user-defined tags to organize the enums
  * @param uid
  *   Unique Int identifier
  * @param values
  *   All possible enum values, with their optional Tile info
  */
case class EnumDefinition(
    externalRelPath: Option[String],
    iconTilesetUid: Option[Int],
    identifier: String,
    tags: Vector[String],
    uid: Int,
    values: Vector[EnumValueDefinition]
) derives Decoder
