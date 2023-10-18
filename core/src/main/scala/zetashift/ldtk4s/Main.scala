/*
 * Copyright 2023 Example
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ldtk4s

import cats.syntax.functor.*
import io.circe.*

import scala.util.Try

// For serialising string unions
given [A <: Singleton](using A <:< String): Decoder[A] =
  Decoder.decodeString.emapTry(x => Try(x.asInstanceOf[A]))
given [A <: Singleton](using ev: A <:< String): Encoder[A] =
  Encoder.encodeString.contramap(ev)

// If a union has a null in, then we'll need this too...
type NullValue = None.type

/** This file is a JSON schema of files created by LDtk level editor
  * (https://ldtk.io).
  *
  * This is the root of any Project JSON file. It contains: - the project
  * settings, - an array of levels, - a group of definitions (that can probably
  * be safely ignored for most users).
  */
case class Coordinate(
    /** This object is not actually used by LDtk. It ONLY exists to force
      * explicit references to all types, to make sure QuickType finds them and
      * integrate all of them. Otherwise, Quicktype will drop types that are not
      * explicitely used.
      */
    val __FORCED_REFS: Option[ForcedRefs] = None,

    /** LDtk application build identifier.<br/> This is only used to identify
      * the LDtk version that generated this particular project file, which can
      * be useful for specific bug fixing. Note that the build identifier is
      * just the date of the release, so it's not unique to each user (one
      * single global ID per LDtk public release), and as a result, completely
      * anonymous.
      */
    val appBuildId: Double,

    /** Number of backup files to keep, if the `backupOnSave` is TRUE
      */
    val backupLimit: Long,

    /** If TRUE, an extra copy of the project will be created in a sub folder,
      * when saving.
      */
    val backupOnSave: Boolean,

    /** Target relative path to store backup files
      */
    val backupRelPath: Option[String] = None,

    /** Project background color
      */
    val bgColor: String,

    /** An array of command lines that can be ran manually by the user
      */
    val customCommands: Seq[LdtkCustomCommand],

    /** Default height for new entities
      */
    val defaultEntityHeight: Long,

    /** Default width for new entities
      */
    val defaultEntityWidth: Long,

    /** Default grid size for new layers
      */
    val defaultGridSize: Long,

    /** Default background color of levels
      */
    val defaultLevelBgColor: String,

    /** **WARNING**: this field will move to the `worlds` array after the
      * "multi-worlds" update. It will then be `null`. You can enable the
      * Multi-worlds advanced project option to enable the change
      * immediately.<br/><br/> Default new level height
      */
    val defaultLevelHeight: Option[Long] = None,

    /** **WARNING**: this field will move to the `worlds` array after the
      * "multi-worlds" update. It will then be `null`. You can enable the
      * Multi-worlds advanced project option to enable the change
      * immediately.<br/><br/> Default new level width
      */
    val defaultLevelWidth: Option[Long] = None,

    /** Default X pivot (0 to 1) for new entities
      */
    val defaultPivotX: Double,

    /** Default Y pivot (0 to 1) for new entities
      */
    val defaultPivotY: Double,

    /** A structure containing all the definitions of this project
      */
    val defs: Definitions,

    /** If the project isn't in MultiWorlds mode, this is the IID of the
      * internal "dummy" World.
      */
    val dummyWorldIid: String,

    /** If TRUE, the exported PNGs will include the level background (color or
      * image).
      */
    val exportLevelBg: Boolean,

    /** **WARNING**: this deprecated value is no longer exported since version
      * 0.9.3 Replaced by: `imageExportMode`
      */
    val exportPng: Option[Boolean] = None,

    /** If TRUE, a Tiled compatible file will also be generated along with the
      * LDtk JSON file (default is FALSE)
      */
    val exportTiled: Boolean,

    /** If TRUE, one file will be saved for the project (incl. all its
      * definitions) and one file in a sub-folder for each level.
      */
    val externalLevels: Boolean,

    /** An array containing various advanced flags (ie. options or other
      * states). Possible values: `DiscardPreCsvIntGrid`,
      * `ExportPreCsvIntGridFormat`, `IgnoreBackupSuggest`,
      * `PrependIndexToLevelFileNames`, `MultiWorlds`, `UseMultilinesType`
      */
    val flags: Seq[Flag],

    /** Naming convention for Identifiers (first-letter uppercase, full
      * uppercase etc.) Possible values: `Capitalize`, `Uppercase`, `Lowercase`,
      * `Free`
      */
    val identifierStyle: IdentifierStyle,

    /** Unique project identifier
      */
    val iid: String,

    /** "Image export" option when saving project. Possible values: `None`,
      * `OneImagePerLayer`, `OneImagePerLevel`, `LayersAndLevels`
      */
    val imageExportMode: ImageExportMode,

    /** File format version
      */
    val jsonVersion: String,

    /** The default naming convention for level identifiers.
      */
    val levelNamePattern: String,

    /** All levels. The order of this array is only relevant in
      * `LinearHorizontal` and `linearVertical` world layouts (see `worldLayout`
      * value).<br/> Otherwise, you should refer to the `worldX`,`worldY`
      * coordinates of each Level.
      */
    val levels: Seq[Level],

    /** If TRUE, the Json is partially minified (no indentation, nor line
      * breaks, default is FALSE)
      */
    val minifyJson: Boolean,

    /** Next Unique integer ID available
      */
    val nextUid: Long,

    /** File naming pattern for exported PNGs
      */
    val pngFilePattern: Option[String] = None,

    /** If TRUE, a very simplified will be generated on saving, for quicker &
      * easier engine integration.
      */
    val simplifiedExport: Boolean,

    /** All instances of entities that have their `exportToToc` flag enabled are
      * listed in this array.
      */
    val toc: Seq[LdtkTableOfContentEntry],

    /** This optional description is used by LDtk Samples to show up some
      * informations and instructions.
      */
    val tutorialDesc: Option[String] = None,

    /** **WARNING**: this field will move to the `worlds` array after the
      * "multi-worlds" update. It will then be `null`. You can enable the
      * Multi-worlds advanced project option to enable the change
      * immediately.<br/><br/> Height of the world grid in pixels.
      */
    val worldGridHeight: Option[Long] = None,

    /** **WARNING**: this field will move to the `worlds` array after the
      * "multi-worlds" update. It will then be `null`. You can enable the
      * Multi-worlds advanced project option to enable the change
      * immediately.<br/><br/> Width of the world grid in pixels.
      */
    val worldGridWidth: Option[Long] = None,

    /** **WARNING**: this field will move to the `worlds` array after the
      * "multi-worlds" update. It will then be `null`. You can enable the
      * Multi-worlds advanced project option to enable the change
      * immediately.<br/><br/> An enum that describes how levels are organized
      * in this project (ie. linearly or in a 2D space). Possible values:
      * &lt;`null`&gt;, `Free`, `GridVania`, `LinearHorizontal`,
      * `LinearVertical`
      */
    val worldLayout: Option[WorldLayout] = None,

    /** This array will be empty, unless you enable the Multi-Worlds in the
      * project advanced settings.<br/><br/> - in current version, a LDtk
      * project file can only contain a single world with multiple levels in it.
      * In this case, levels and world layout related settings are stored in the
      * root of the JSON.<br/> - with "Multi-worlds" enabled, there will be a
      * `worlds` array in root, each world containing levels and layout
      * settings. Basically, it's pretty much only about moving the `levels`
      * array to the `worlds` array, along with world layout related values (eg.
      * `worldGridWidth` etc).<br/><br/>If you want to start supporting this
      * future update easily, please refer to this documentation:
      * https://github.com/deepnight/ldtk/issues/231
      */
    val worlds: Seq[World]
) derives Encoder.AsObject,
      Decoder

case class LdtkCustomCommand(
    val command: String,

    /** Possible values: `Manual`, `AfterLoad`, `BeforeSave`, `AfterSave`
      */
    val when: When
) derives Encoder.AsObject,
      Decoder

/** Possible values: `Manual`, `AfterLoad`, `BeforeSave`, `AfterSave`
  */

type When = "AfterLoad" | "AfterSave" | "BeforeSave" | "Manual"

/** If you're writing your own LDtk importer, you should probably just ignore
  * *most* stuff in the `defs` section, as it contains data that are mostly
  * important to the editor. To keep you away from the `defs` section and avoid
  * some unnecessary JSON parsing, important data from definitions is often
  * duplicated in fields prefixed with a double underscore (eg. `__identifier`
  * or `__type`). The 2 only definition types you might need here are
  * **Tilesets** and **Enums**.
  *
  * A structure containing all the definitions of this project
  */
case class Definitions(
    /** All entities definitions, including their custom fields
      */
    val entities: Seq[EntityDefinition],

    /** All internal enums
      */
    val enums: Seq[EnumDefinition],

    /** Note: external enums are exactly the same as `enums`, except they have a
      * `relPath` to point to an external source file.
      */
    val externalEnums: Seq[EnumDefinition],

    /** All layer definitions
      */
    val layers: Seq[LayerDefinition],

    /** All custom fields available to all levels.
      */
    val levelFields: Seq[FieldDefinition],

    /** All tilesets
      */
    val tilesets: Seq[TilesetDefinition]
) derives Encoder.AsObject,
      Decoder

case class EntityDefinition(
    /** Base entity color
      */
    val color: String,

    /** User defined documentation for this element to provide help/tips to
      * level designers.
      */
    val doc: Option[String] = None,

    /** If enabled, all instances of this entity will be listed in the project
      * "Table of content" object.
      */
    val exportToToc: Boolean,

    /** Array of field definitions
      */
    val fieldDefs: Seq[FieldDefinition],
    val fillOpacity: Double,

    /** Pixel height
      */
    val height: Long,
    val hollow: Boolean,

    /** User defined unique identifier
      */
    val identifier: String,

    /** Only applies to entities resizable on both X/Y. If TRUE, the entity
      * instance width/height will keep the same aspect ratio as the definition.
      */
    val keepAspectRatio: Boolean,

    /** Possible values: `DiscardOldOnes`, `PreventAdding`, `MoveLastOne`
      */
    val limitBehavior: LimitBehavior,

    /** If TRUE, the maxCount is a "per world" limit, if FALSE, it's a "per
      * level". Possible values: `PerLayer`, `PerLevel`, `PerWorld`
      */
    val limitScope: LimitScope,
    val lineOpacity: Double,

    /** Max instances count
      */
    val maxCount: Long,

    /** Max pixel height (only applies if the entity is resizable on Y)
      */
    val maxHeight: Option[Long] = None,

    /** Max pixel width (only applies if the entity is resizable on X)
      */
    val maxWidth: Option[Long] = None,

    /** Min pixel height (only applies if the entity is resizable on Y)
      */
    val minHeight: Option[Long] = None,

    /** Min pixel width (only applies if the entity is resizable on X)
      */
    val minWidth: Option[Long] = None,

    /** An array of 4 dimensions for the up/right/down/left borders (in this
      * order) when using 9-slice mode for `tileRenderMode`.<br/> If the
      * tileRenderMode is not NineSlice, then this array is empty.<br/> See:
      * https://en.wikipedia.org/wiki/9-slice_scaling
      */
    val nineSliceBorders: Seq[Long],

    /** Pivot X coordinate (from 0 to 1.0)
      */
    val pivotX: Double,

    /** Pivot Y coordinate (from 0 to 1.0)
      */
    val pivotY: Double,

    /** Possible values: `Rectangle`, `Ellipse`, `Tile`, `Cross`
      */
    val renderMode: RenderMode,

    /** If TRUE, the entity instances will be resizable horizontally
      */
    val resizableX: Boolean,

    /** If TRUE, the entity instances will be resizable vertically
      */
    val resizableY: Boolean,

    /** Display entity name in editor
      */
    val showName: Boolean,

    /** An array of strings that classifies this entity
      */
    val tags: Seq[String],

    /** **WARNING**: this deprecated value is no longer exported since version
      * 1.2.0 Replaced by: `tileRect`
      */
    val tileId: Option[Long] = None,
    val tileOpacity: Double,

    /** An object representing a rectangle from an existing Tileset
      */
    val tileRect: Option[TilesetRectangle] = None,

    /** An enum describing how the the Entity tile is rendered inside the Entity
      * bounds. Possible values: `Cover`, `FitInside`, `Repeat`, `Stretch`,
      * `FullSizeCropped`, `FullSizeUncropped`, `NineSlice`
      */
    val tileRenderMode: TileRenderMode,

    /** Tileset ID used for optional tile display
      */
    val tilesetId: Option[Long] = None,

    /** Unique Int identifier
      */
    val uid: Long,

    /** This tile overrides the one defined in `tileRect` in the UI
      */
    val uiTileRect: Option[TilesetRectangle] = None,

    /** Pixel width
      */
    val width: Long
) derives Encoder.AsObject,
      Decoder

/** This section is mostly only intended for the LDtk editor app itself. You can
  * safely ignore it.
  */
case class FieldDefinition(
    /** Human readable value type. Possible values: `Int, Float, String, Bool,
      * Color, ExternEnum.XXX, LocalEnum.XXX, Point, FilePath`.<br/> If the
      * field is an array, this field will look like `Array<...>` (eg.
      * `Array<Int>`, `Array<Point>` etc.)<br/> NOTE: if you enable the advanced
      * option **Use Multilines type**, you will have "*Multilines*" instead of
      * "*String*" when relevant.
      */
    val __type: String,

    /** Optional list of accepted file extensions for FilePath value type.
      * Includes the dot: `.ext`
      */
    val acceptFileTypes: Option[Seq[String]] = None,

    /** Possible values: `Any`, `OnlySame`, `OnlyTags`, `OnlySpecificEntity`
      */
    val allowedRefs: AllowedRefs,
    val allowedRefsEntityUid: Option[Long] = None,
    val allowedRefTags: Seq[String],
    val allowOutOfLevelRef: Boolean,

    /** Array max length
      */
    val arrayMaxLength: Option[Long] = None,

    /** Array min length
      */
    val arrayMinLength: Option[Long] = None,
    val autoChainRef: Boolean,

    /** TRUE if the value can be null. For arrays, TRUE means it can contain
      * null values (exception: array of Points can't have null values).
      */
    val canBeNull: Boolean,

    /** Default value if selected value is null or invalid.
      */
    val defaultOverride: Option[Json] = None,

    /** User defined documentation for this field to provide help/tips to level
      * designers about accepted values.
      */
    val doc: Option[String] = None,
    val editorAlwaysShow: Boolean,
    val editorCutLongValues: Boolean,
    val editorDisplayColor: Option[String] = None,

    /** Possible values: `Hidden`, `ValueOnly`, `NameAndValue`, `EntityTile`,
      * `LevelTile`, `Points`, `PointStar`, `PointPath`, `PointPathLoop`,
      * `RadiusPx`, `RadiusGrid`, `ArrayCountWithLabel`, `ArrayCountNoLabel`,
      * `RefLinkBetweenPivots`, `RefLinkBetweenCenters`
      */
    val editorDisplayMode: EditorDisplayMode,

    /** Possible values: `Above`, `Center`, `Beneath`
      */
    val editorDisplayPos: EditorDisplayPos,
    val editorDisplayScale: Double,

    /** Possible values: `ZigZag`, `StraightArrow`, `CurvedArrow`, `ArrowsLine`,
      * `DashedLine`
      */
    val editorLinkStyle: EditorLinkStyle,
    val editorShowInWorld: Boolean,
    val editorTextPrefix: Option[String] = None,
    val editorTextSuffix: Option[String] = None,

    /** User defined unique identifier
      */
    val identifier: String,

    /** TRUE if the value is an array of multiple values
      */
    val isArray: Boolean,

    /** Max limit for value, if applicable
      */
    val max: Option[Double] = None,

    /** Min limit for value, if applicable
      */
    val min: Option[Double] = None,

    /** Optional regular expression that needs to be matched to accept values.
      * Expected format: `/some_reg_ex/g`, with optional "i" flag.
      */
    val regex: Option[String] = None,
    val symmetricalRef: Boolean,

    /** Possible values: &lt;`null`&gt;, `LangPython`, `LangRuby`, `LangJS`,
      * `LangLua`, `LangC`, `LangHaxe`, `LangMarkdown`, `LangJson`, `LangXml`,
      * `LangLog`
      */
    val textLanguageMode: Option[TextLanguageMode] = None,

    /** UID of the tileset used for a Tile
      */
    val tilesetUid: Option[Long] = None,

    /** Internal enum representing the possible field types. Possible values:
      * F_Int, F_Float, F_String, F_Text, F_Bool, F_Color, F_Enum(...), F_Point,
      * F_Path, F_EntityRef, F_Tile
      */
    val `type`: String,

    /** Unique Int identifier
      */
    val uid: Long,

    /** If TRUE, the color associated with this field will override the Entity
      * or Level default color in the editor UI. For Enum fields, this would be
      * the color associated to their values.
      */
    val useForSmartColor: Boolean
) derives Encoder.AsObject,
      Decoder

/** Possible values: `Any`, `OnlySame`, `OnlyTags`, `OnlySpecificEntity`
  */

type AllowedRefs = "OnlySame" | "OnlySpecificEntity" | "OnlyTags" | "Any"

/** Possible values: `Hidden`, `ValueOnly`, `NameAndValue`, `EntityTile`,
  * `LevelTile`, `Points`, `PointStar`, `PointPath`, `PointPathLoop`,
  * `RadiusPx`, `RadiusGrid`, `ArrayCountWithLabel`, `ArrayCountNoLabel`,
  * `RefLinkBetweenPivots`, `RefLinkBetweenCenters`
  */

type EditorDisplayMode = "ArrayCountNoLabel" | "ArrayCountWithLabel" |
  "EntityTile" | "Hidden" | "LevelTile" | "NameAndValue" | "PointPath" |
  "PointPathLoop" | "PointStar" | "Points" | "RadiusGrid" | "RadiusPx" |
  "RefLinkBetweenCenters" | "RefLinkBetweenPivots" | "ValueOnly"

/** Possible values: `Above`, `Center`, `Beneath`
  */

type EditorDisplayPos = "Above" | "Beneath" | "Center"

/** Possible values: `ZigZag`, `StraightArrow`, `CurvedArrow`, `ArrowsLine`,
  * `DashedLine`
  */

type EditorLinkStyle = "ArrowsLine" | "CurvedArrow" | "DashedLine" |
  "StraightArrow" | "ZigZag"
type TextLanguageMode = "LangC" | "LangHaxe" | "LangJS" | "LangJson" |
  "LangLog" | "LangLua" | "LangMarkdown" | "LangPython" | "LangRuby" | "LangXml"

/** Possible values: `DiscardOldOnes`, `PreventAdding`, `MoveLastOne`
  */

type LimitBehavior = "DiscardOldOnes" | "MoveLastOne" | "PreventAdding"

/** If TRUE, the maxCount is a "per world" limit, if FALSE, it's a "per level".
  * Possible values: `PerLayer`, `PerLevel`, `PerWorld`
  */

type LimitScope = "PerLayer" | "PerLevel" | "PerWorld"

/** Possible values: `Rectangle`, `Ellipse`, `Tile`, `Cross`
  */

type RenderMode = "Cross" | "Ellipse" | "Rectangle" | "Tile"

/** This object represents a custom sub rectangle in a Tileset image.
  */
case class TilesetRectangle(
    /** Height in pixels
      */
    val h: Long,

    /** UID of the tileset
      */
    val tilesetUid: Long,

    /** Width in pixels
      */
    val w: Long,

    /** X pixels coordinate of the top-left corner in the Tileset image
      */
    val x: Long,

    /** Y pixels coordinate of the top-left corner in the Tileset image
      */
    val y: Long
) derives Encoder.AsObject,
      Decoder

/** An enum describing how the the Entity tile is rendered inside the Entity
  * bounds. Possible values: `Cover`, `FitInside`, `Repeat`, `Stretch`,
  * `FullSizeCropped`, `FullSizeUncropped`, `NineSlice`
  */

type TileRenderMode = "Cover" | "FitInside" | "FullSizeCropped" |
  "FullSizeUncropped" | "NineSlice" | "Repeat" | "Stretch"
case class EnumDefinition(
    val externalFileChecksum: Option[String] = None,

    /** Relative path to the external file providing this Enum
      */
    val externalRelPath: Option[String] = None,

    /** Tileset UID if provided
      */
    val iconTilesetUid: Option[Long] = None,

    /** User defined unique identifier
      */
    val identifier: String,

    /** An array of user-defined tags to organize the Enums
      */
    val tags: Seq[String],

    /** Unique Int identifier
      */
    val uid: Long,

    /** All possible enum values, with their optional Tile infos.
      */
    val values: Seq[EnumValueDefinition]
) derives Encoder.AsObject,
      Decoder

case class EnumValueDefinition(
    /** **WARNING**: this deprecated value is no longer exported since version
      * 1.4.0 Replaced by: `tileRect`
      */
    val __tileSrcRect: Option[Seq[Long]] = None,

    /** Optional color
      */
    val color: Long,

    /** Enum value
      */
    val id: String,

    /** **WARNING**: this deprecated value is no longer exported since version
      * 1.4.0 Replaced by: `tileRect`
      */
    val tileId: Option[Long] = None,

    /** Optional tileset rectangle to represents this value
      */
    val tileRect: Option[TilesetRectangle] = None
) derives Encoder.AsObject,
      Decoder

case class LayerDefinition(
    /** Type of the layer (*IntGrid, Entities, Tiles or AutoLayer*)
      */
    val __type: String,

    /** Contains all the auto-layer rule definitions.
      */
    val autoRuleGroups: Seq[AutoLayerRuleGroup],
    val autoSourceLayerDefUid: Option[Long] = None,

    /** **WARNING**: this deprecated value is no longer exported since version
      * 1.2.0 Replaced by: `tilesetDefUid`
      */
    val autoTilesetDefUid: Option[Long] = None,

    /** Allow editor selections when the layer is not currently active.
      */
    val canSelectWhenInactive: Boolean,

    /** Opacity of the layer (0 to 1.0)
      */
    val displayOpacity: Double,

    /** User defined documentation for this element to provide help/tips to
      * level designers.
      */
    val doc: Option[String] = None,

    /** An array of tags to forbid some Entities in this layer
      */
    val excludedTags: Seq[String],

    /** Width and height of the grid in pixels
      */
    val gridSize: Long,

    /** Height of the optional "guide" grid in pixels
      */
    val guideGridHei: Long,

    /** Width of the optional "guide" grid in pixels
      */
    val guideGridWid: Long,
    val hideFieldsWhenInactive: Boolean,

    /** Hide the layer from the list on the side of the editor view.
      */
    val hideInList: Boolean,

    /** User defined unique identifier
      */
    val identifier: String,

    /** Alpha of this layer when it is not the active one.
      */
    val inactiveOpacity: Double,

    /** An array that defines extra optional info for each IntGrid value.<br/>
      * WARNING: the array order is not related to actual IntGrid values! As
      * user can re-order IntGrid values freely, you may value "2" before value
      * "1" in this array.
      */
    val intGridValues: Seq[IntGridValueDefinition],

    /** Group informations for IntGrid values
      */
    val intGridValuesGroups: Seq[IntGridValueGroupDefinition],

    /** Parallax horizontal factor (from -1 to 1, defaults to 0) which affects
      * the scrolling speed of this layer, creating a fake 3D (parallax) effect.
      */
    val parallaxFactorX: Double,

    /** Parallax vertical factor (from -1 to 1, defaults to 0) which affects the
      * scrolling speed of this layer, creating a fake 3D (parallax) effect.
      */
    val parallaxFactorY: Double,

    /** If true (default), a layer with a parallax factor will also be scaled
      * up/down accordingly.
      */
    val parallaxScaling: Boolean,

    /** X offset of the layer, in pixels (IMPORTANT: this should be added to the
      * `LayerInstance` optional offset)
      */
    val pxOffsetX: Long,

    /** Y offset of the layer, in pixels (IMPORTANT: this should be added to the
      * `LayerInstance` optional offset)
      */
    val pxOffsetY: Long,

    /** If TRUE, the content of this layer will be used when rendering levels in
      * a simplified way for the world view
      */
    val renderInWorldView: Boolean,

    /** An array of tags to filter Entities that can be added to this layer
      */
    val requiredTags: Seq[String],

    /** If the tiles are smaller or larger than the layer grid, the pivot value
      * will be used to position the tile relatively its grid cell.
      */
    val tilePivotX: Double,

    /** If the tiles are smaller or larger than the layer grid, the pivot value
      * will be used to position the tile relatively its grid cell.
      */
    val tilePivotY: Double,

    /** Reference to the default Tileset UID being used by this layer
      * definition.<br/> **WARNING**: some layer *instances* might use a
      * different tileset. So most of the time, you should probably use the
      * `__tilesetDefUid` value found in layer instances.<br/> Note: since
      * version 1.0.0, the old `autoTilesetDefUid` was removed and merged into
      * this value.
      */
    val tilesetDefUid: Option[Long] = None,

    /** Type of the layer as Haxe Enum Possible values: `IntGrid`, `Entities`,
      * `Tiles`, `AutoLayer`
      */
    val `type`: Type,

    /** User defined color for the UI
      */
    val uiColor: Option[String] = None,

    /** Unique Int identifier
      */
    val uid: Long
) derives Encoder.AsObject,
      Decoder

case class AutoLayerRuleGroup(
    val active: Boolean,

    /** *This field was removed in 1.0.0 and should no longer be used.*
      */
    val collapsed: Option[Boolean] = None,
    val color: Option[String] = None,
    val icon: Option[TilesetRectangle] = None,
    val isOptional: Boolean,
    val name: String,
    val rules: Seq[AutoLayerRuleDefinition],
    val uid: Long,
    val usesWizard: Boolean
) derives Encoder.AsObject,
      Decoder

/** This complex section isn't meant to be used by game devs at all, as these
  * rules are completely resolved internally by the editor before any saving.
  * You should just ignore this part.
  */
case class AutoLayerRuleDefinition(
    /** If FALSE, the rule effect isn't applied, and no tiles are generated.
      */
    val active: Boolean,
    val alpha: Double,

    /** When TRUE, the rule will prevent other rules to be applied in the same
      * cell if it matches (TRUE by default).
      */
    val breakOnMatch: Boolean,

    /** Chances for this rule to be applied (0 to 1)
      */
    val chance: Double,

    /** Checker mode Possible values: `None`, `Horizontal`, `Vertical`
      */
    val checker: Checker,

    /** If TRUE, allow rule to be matched by flipping its pattern horizontally
      */
    val flipX: Boolean,

    /** If TRUE, allow rule to be matched by flipping its pattern vertically
      */
    val flipY: Boolean,

    /** Default IntGrid value when checking cells outside of level bounds
      */
    val outOfBoundsValue: Option[Long] = None,

    /** Rule pattern (size x size)
      */
    val pattern: Seq[Long],

    /** If TRUE, enable Perlin filtering to only apply rule on specific random
      * area
      */
    val perlinActive: Boolean,
    val perlinOctaves: Double,
    val perlinScale: Double,
    val perlinSeed: Double,

    /** X pivot of a tile stamp (0-1)
      */
    val pivotX: Double,

    /** Y pivot of a tile stamp (0-1)
      */
    val pivotY: Double,

    /** Pattern width & height. Should only be 1,3,5 or 7.
      */
    val size: Long,

    /** Array of all the tile IDs. They are used randomly or as stamps, based on
      * `tileMode` value.
      */
    val tileIds: Seq[Long],

    /** Defines how tileIds array is used Possible values: `Single`, `Stamp`
      */
    val tileMode: TileMode,

    /** Max random offset for X tile pos
      */
    val tileRandomXMax: Long,

    /** Min random offset for X tile pos
      */
    val tileRandomXMin: Long,

    /** Max random offset for Y tile pos
      */
    val tileRandomYMax: Long,

    /** Min random offset for Y tile pos
      */
    val tileRandomYMin: Long,

    /** Tile X offset
      */
    val tileXOffset: Long,

    /** Tile Y offset
      */
    val tileYOffset: Long,

    /** Unique Int identifier
      */
    val uid: Long,

    /** X cell coord modulo
      */
    val xModulo: Long,

    /** X cell start offset
      */
    val xOffset: Long,

    /** Y cell coord modulo
      */
    val yModulo: Long,

    /** Y cell start offset
      */
    val yOffset: Long
) derives Encoder.AsObject,
      Decoder

/** Checker mode Possible values: `None`, `Horizontal`, `Vertical`
  */

type Checker = "Horizontal" | "None" | "Vertical"

/** Defines how tileIds array is used Possible values: `Single`, `Stamp`
  */

type TileMode = "Single" | "Stamp"

/** IntGrid value definition
  */
case class IntGridValueDefinition(
    val color: String,

    /** Parent group identifier (0 if none)
      */
    val groupUid: Long,

    /** User defined unique identifier
      */
    val identifier: Option[String] = None,
    val tile: Option[TilesetRectangle] = None,

    /** The IntGrid value itself
      */
    val value: Long
) derives Encoder.AsObject,
      Decoder

/** IntGrid value group definition
  */
case class IntGridValueGroupDefinition(
    /** User defined color
      */
    val color: Option[String] = None,

    /** User defined string identifier
      */
    val identifier: Option[String] = None,

    /** Group unique ID
      */
    val uid: Long
) derives Encoder.AsObject,
      Decoder

/** Type of the layer as Haxe Enum Possible values: `IntGrid`, `Entities`,
  * `Tiles`, `AutoLayer`
  */

type Type = "AutoLayer" | "Entities" | "IntGrid" | "Tiles"

/** The `Tileset` definition is the most important part among project
  * definitions. It contains some extra informations about each integrated
  * tileset. If you only had to parse one definition section, that would be the
  * one.
  */
case class TilesetDefinition(
    /** Grid-based height
      */
    val __cHei: Long,

    /** Grid-based width
      */
    val __cWid: Long,

    /** The following data is used internally for various optimizations. It's
      * always synced with source image changes.
      */
    val cachedPixelData: Option[Map[String, Option[Json]]] = None,

    /** An array of custom tile metadata
      */
    val customData: Seq[TileCustomMetadata],

    /** If this value is set, then it means that this atlas uses an internal
      * LDtk atlas image instead of a loaded one. Possible values:
      * &lt;`null`&gt;, `LdtkIcons`
      */
    val embedAtlas: Option[EmbedAtlas] = None,

    /** Tileset tags using Enum values specified by `tagsSourceEnumId`. This
      * array contains 1 element per Enum value, which contains an array of all
      * Tile IDs that are tagged with it.
      */
    val enumTags: Seq[EnumTagValue],

    /** User defined unique identifier
      */
    val identifier: String,

    /** Distance in pixels from image borders
      */
    val padding: Long,

    /** Image height in pixels
      */
    val pxHei: Long,

    /** Image width in pixels
      */
    val pxWid: Long,

    /** Path to the source file, relative to the current project JSON file<br/>
      * It can be null if no image was provided, or when using an embed atlas.
      */
    val relPath: Option[String] = None,

    /** Array of group of tiles selections, only meant to be used in the editor
      */
    val savedSelections: Seq[Map[String, Option[Json]]],

    /** Space in pixels between all tiles
      */
    val spacing: Long,

    /** An array of user-defined tags to organize the Tilesets
      */
    val tags: Seq[String],

    /** Optional Enum definition UID used for this tileset meta-data
      */
    val tagsSourceEnumUid: Option[Long] = None,
    val tileGridSize: Long,

    /** Unique Intidentifier
      */
    val uid: Long
) derives Encoder.AsObject,
      Decoder

/** In a tileset definition, user defined meta-data of a tile.
  */
case class TileCustomMetadata(
    val data: String,
    val tileId: Long
) derives Encoder.AsObject,
      Decoder

type EmbedAtlas = "LdtkIcons"

/** In a tileset definition, enum based tag infos
  */
case class EnumTagValue(
    val enumValueId: String,
    val tileIds: Seq[Long]
) derives Encoder.AsObject,
      Decoder

type Flag = "DiscardPreCsvIntGrid" | "ExportPreCsvIntGridFormat" |
  "IgnoreBackupSuggest" | "MultiWorlds" | "PrependIndexToLevelFileNames" |
  "UseMultilinesType"

/** This object is not actually used by LDtk. It ONLY exists to force explicit
  * references to all types, to make sure QuickType finds them and integrate all
  * of them. Otherwise, Quicktype will drop types that are not explicitely used.
  */
case class ForcedRefs(
    val AutoLayerRuleGroup: Option[AutoLayerRuleGroup] = None,
    val AutoRuleDef: Option[AutoLayerRuleDefinition] = None,
    val CustomCommand: Option[LdtkCustomCommand] = None,
    val Definitions: Option[Definitions] = None,
    val EntityDef: Option[EntityDefinition] = None,
    val EntityInstance: Option[EntityInstance] = None,
    val EntityReferenceInfos: Option[ReferenceToAnEntityInstance] = None,
    val EnumDef: Option[EnumDefinition] = None,
    val EnumDefValues: Option[EnumValueDefinition] = None,
    val EnumTagValue: Option[EnumTagValue] = None,
    val FieldDef: Option[FieldDefinition] = None,
    val FieldInstance: Option[FieldInstance] = None,
    val GridPoint: Option[GridPoint] = None,
    val IntGridValueDef: Option[IntGridValueDefinition] = None,
    val IntGridValueGroupDef: Option[IntGridValueGroupDefinition] = None,
    val IntGridValueInstance: Option[IntGridValueInstance] = None,
    val LayerDef: Option[LayerDefinition] = None,
    val LayerInstance: Option[LayerInstance] = None,
    val Level: Option[Level] = None,
    val LevelBgPosInfos: Option[LevelBackgroundPosition] = None,
    val NeighbourLevel: Option[NeighbourLevel] = None,
    val TableOfContentEntry: Option[LdtkTableOfContentEntry] = None,
    val Tile: Option[TileInstance] = None,
    val TileCustomMetadata: Option[TileCustomMetadata] = None,
    val TilesetDef: Option[TilesetDefinition] = None,
    val TilesetRect: Option[TilesetRectangle] = None,
    val World: Option[World] = None
) derives Encoder.AsObject,
      Decoder

case class EntityInstance(
    /** Grid-based coordinates (`[x,y]` format)
      */
    val __grid: Seq[Long],

    /** Entity definition identifier
      */
    val __identifier: String,

    /** Pivot coordinates (`[x,y]` format, values are from 0 to 1) of the Entity
      */
    val __pivot: Seq[Double],

    /** The entity "smart" color, guessed from either Entity definition, or one
      * its field instances.
      */
    val __smartColor: String,

    /** Array of tags defined in this Entity definition
      */
    val __tags: Seq[String],

    /** Optional TilesetRect used to display this entity (it could either be the
      * default Entity tile, or some tile provided by a field value, like an
      * Enum).
      */
    val __tile: Option[TilesetRectangle] = None,

    /** X world coordinate in pixels
      */
    val __worldX: Long,

    /** Y world coordinate in pixels
      */
    val __worldY: Long,

    /** Reference of the **Entity definition** UID
      */
    val defUid: Long,

    /** An array of all custom fields and their values.
      */
    val fieldInstances: Seq[FieldInstance],

    /** Entity height in pixels. For non-resizable entities, it will be the same
      * as Entity definition.
      */
    val height: Long,

    /** Unique instance identifier
      */
    val iid: String,

    /** Pixel coordinates (`[x,y]` format) in current level coordinate space.
      * Don't forget optional layer offsets, if they exist!
      */
    val px: Seq[Long],

    /** Entity width in pixels. For non-resizable entities, it will be the same
      * as Entity definition.
      */
    val width: Long
) derives Encoder.AsObject,
      Decoder

case class FieldInstance(
    /** Field definition identifier
      */
    val __identifier: String,

    /** Optional TilesetRect used to display this field (this can be the field
      * own Tile, or some other Tile guessed from the value, like an Enum).
      */
    val __tile: Option[TilesetRectangle] = None,

    /** Type of the field, such as `Int`, `Float`, `String`,
      * `Enum(my_enum_name)`, `Bool`, etc.<br/> NOTE: if you enable the advanced
      * option **Use Multilines type**, you will have "*Multilines*" instead of
      * "*String*" when relevant.
      */
    val __type: String,

    /** Actual value of the field instance. The value type varies, depending on
      * `__type`:<br/>
      *   - For **classic types** (ie. Integer, Float, Boolean, String, Text and
      *     FilePath), you just get the actual value with the expected
      *     type.<br/> - For **Color**, the value is an hexadecimal string using
      *     "#rrggbb" format.<br/> - For **Enum**, the value is a String
      *     representing the selected enum value.<br/> - For **Point**, the
      *     value is a [GridPoint](#ldtk-GridPoint) object.<br/> - For **Tile**,
      *     the value is a [TilesetRect](#ldtk-TilesetRect) object.<br/> - For
      *     **EntityRef**, the value is an
      *     [EntityReferenceInfos](#ldtk-EntityReferenceInfos) object.<br/><br/>
      *     If the field is an array, then this `__value` will also be a JSON
      *     array.
      */
    val __value: Option[Json],

    /** Reference of the **Field definition** UID
      */
    val defUid: Long,

    /** Editor internal raw values
      */
    val realEditorValues: Seq[Option[Json]]
) derives Encoder.AsObject,
      Decoder

/** This object describes the "location" of an Entity instance in the project
  * worlds.
  */
case class ReferenceToAnEntityInstance(
    /** IID of the refered EntityInstance
      */
    val entityIid: String,

    /** IID of the LayerInstance containing the refered EntityInstance
      */
    val layerIid: String,

    /** IID of the Level containing the refered EntityInstance
      */
    val levelIid: String,

    /** IID of the World containing the refered EntityInstance
      */
    val worldIid: String
) derives Encoder.AsObject,
      Decoder

/** This object is just a grid-based coordinate used in Field values.
  */
case class GridPoint(
    /** X grid-based coordinate
      */
    val cx: Long,

    /** Y grid-based coordinate
      */
    val cy: Long
) derives Encoder.AsObject,
      Decoder

/** IntGrid value instance
  */
case class IntGridValueInstance(
    /** Coordinate ID in the layer grid
      */
    val coordId: Long,

    /** IntGrid value
      */
    val v: Long
) derives Encoder.AsObject,
      Decoder

case class LayerInstance(
    /** Grid-based height
      */
    val __cHei: Long,

    /** Grid-based width
      */
    val __cWid: Long,

    /** Grid size
      */
    val __gridSize: Long,

    /** Layer definition identifier
      */
    val __identifier: String,

    /** Layer opacity as Float [0-1]
      */
    val __opacity: Double,

    /** Total layer X pixel offset, including both instance and definition
      * offsets.
      */
    val __pxTotalOffsetX: Long,

    /** Total layer Y pixel offset, including both instance and definition
      * offsets.
      */
    val __pxTotalOffsetY: Long,

    /** The definition UID of corresponding Tileset, if any.
      */
    val __tilesetDefUid: Option[Long] = None,

    /** The relative path to corresponding Tileset, if any.
      */
    val __tilesetRelPath: Option[String] = None,

    /** Layer type (possible values: IntGrid, Entities, Tiles or AutoLayer)
      */
    val __type: String,

    /** An array containing all tiles generated by Auto-layer rules. The array
      * is already sorted in display order (ie. 1st tile is beneath 2nd, which
      * is beneath 3rd etc.).<br/><br/> Note: if multiple tiles are stacked in
      * the same cell as the result of different rules, all tiles behind opaque
      * ones will be discarded.
      */
    val autoLayerTiles: Seq[TileInstance],
    val entityInstances: Seq[EntityInstance],
    val gridTiles: Seq[TileInstance],

    /** Unique layer instance identifier
      */
    val iid: String,

    /** **WARNING**: this deprecated value is no longer exported since version
      * 1.0.0 Replaced by: `intGridCsv`
      */
    val intGrid: Option[Seq[IntGridValueInstance]] = None,

    /** A list of all values in the IntGrid layer, stored in CSV format (Comma
      * Separated Values).<br/> Order is from left to right, and top to bottom
      * (ie. first row from left to right, followed by second row, etc).<br/>
      * `0` means "empty cell" and IntGrid values start at 1.<br/> The array
      * size is `__cWid` x `__cHei` cells.
      */
    val intGridCsv: Seq[Long],

    /** Reference the Layer definition UID
      */
    val layerDefUid: Long,

    /** Reference to the UID of the level containing this layer instance
      */
    val levelId: Long,

    /** An Array containing the UIDs of optional rules that were enabled in this
      * specific layer instance.
      */
    val optionalRules: Seq[Long],

    /** This layer can use another tileset by overriding the tileset UID here.
      */
    val overrideTilesetUid: Option[Long] = None,

    /** X offset in pixels to render this layer, usually 0 (IMPORTANT: this
      * should be added to the `LayerDef` optional offset, so you should
      * probably prefer using `__pxTotalOffsetX` which contains the total offset
      * value)
      */
    val pxOffsetX: Long,

    /** Y offset in pixels to render this layer, usually 0 (IMPORTANT: this
      * should be added to the `LayerDef` optional offset, so you should
      * probably prefer using `__pxTotalOffsetX` which contains the total offset
      * value)
      */
    val pxOffsetY: Long,

    /** Random seed used for Auto-Layers rendering
      */
    val seed: Long,

    /** Layer instance visibility
      */
    val visible: Boolean
) derives Encoder.AsObject,
      Decoder

/** This structure represents a single tile from a given Tileset.
  */
case class TileInstance(
    /** Alpha/opacity of the tile (0-1, defaults to 1)
      */
    val a: Double,

    /** Internal data used by the editor.<br/> For auto-layer tiles: `[ruleId,
      * coordId]`.<br/> For tile-layer tiles: `[coordId]`.
      */
    val d: Seq[Long],

    /** "Flip bits", a 2-bits integer to represent the mirror transformations of
      * the tile.<br/>
      *   - Bit 0 = X flip<br/> - Bit 1 = Y flip<br/> Examples: f=0 (no flip),
      *     f=1 (X flip only), f=2 (Y flip only), f=3 (both flips)
      */
    val f: Long,

    /** Pixel coordinates of the tile in the **layer** (`[x,y]` format). Don't
      * forget optional layer offsets, if they exist!
      */
    val px: Seq[Long],

    /** Pixel coordinates of the tile in the **tileset** (`[x,y]` format)
      */
    val src: Seq[Long],

    /** The *Tile ID* in the corresponding tileset.
      */
    val t: Long
) derives Encoder.AsObject,
      Decoder

/** This section contains all the level data. It can be found in 2 distinct
  * forms, depending on Project current settings: - If "*Separate level files*"
  * is **disabled** (default): full level data is *embedded* inside the main
  * Project JSON file, - If "*Separate level files*" is **enabled**: level data
  * is stored in *separate* standalone `.ldtkl` files (one per level). In this
  * case, the main Project JSON file will still contain most level data, except
  * heavy sections, like the `layerInstances` array (which will be null). The
  * `externalRelPath` string points to the `ldtkl` file. A `ldtkl` file is just
  * a JSON file containing exactly what is described below.
  */
case class Level(
    /** Background color of the level (same as `bgColor`, except the default
      * value is automatically used here if its value is `null`)
      */
    val __bgColor: String,

    /** Position informations of the background image, if there is one.
      */
    val __bgPos: Option[LevelBackgroundPosition] = None,

    /** An array listing all other levels touching this one on the world map.
      * Since 1.4.0, this includes levels that overlap in the same world layer,
      * or in nearby world layers.<br/> Only relevant for world layouts where
      * level spatial positioning is manual (ie. GridVania, Free). For
      * Horizontal and Vertical layouts, this array is always empty.
      */
    val __neighbours: Seq[NeighbourLevel],

    /** The "guessed" color for this level in the editor, decided using either
      * the background color or an existing custom field.
      */
    val __smartColor: String,

    /** Background color of the level. If `null`, the project
      * `defaultLevelBgColor` should be used.
      */
    val bgColor: Option[String] = None,

    /** Background image X pivot (0-1)
      */
    val bgPivotX: Double,

    /** Background image Y pivot (0-1)
      */
    val bgPivotY: Double,

    /** An enum defining the way the background image (if any) is positioned on
      * the level. See `__bgPos` for resulting position info. Possible values:
      * &lt;`null`&gt;, `Unscaled`, `Contain`, `Cover`, `CoverDirty`, `Repeat`
      */
    val bgPos: Option[BgPos] = None,

    /** The *optional* relative path to the level background image.
      */
    val bgRelPath: Option[String] = None,

    /** This value is not null if the project option "*Save levels separately*"
      * is enabled. In this case, this **relative** path points to the level
      * Json file.
      */
    val externalRelPath: Option[String] = None,

    /** An array containing this level custom field values.
      */
    val fieldInstances: Seq[FieldInstance],

    /** User defined unique identifier
      */
    val identifier: String,

    /** Unique instance identifier
      */
    val iid: String,

    /** An array containing all Layer instances. **IMPORTANT**: if the project
      * option "*Save levels separately*" is enabled, this field will be
      * `null`.<br/> This array is **sorted in display order**: the 1st layer is
      * the top-most and the last is behind.
      */
    val layerInstances: Option[Seq[LayerInstance]] = None,

    /** Height of the level in pixels
      */
    val pxHei: Long,

    /** Width of the level in pixels
      */
    val pxWid: Long,

    /** Unique Int identifier
      */
    val uid: Long,

    /** If TRUE, the level identifier will always automatically use the naming
      * pattern as defined in `Project.levelNamePattern`. Becomes FALSE if the
      * identifier is manually modified by user.
      */
    val useAutoIdentifier: Boolean,

    /** Index that represents the "depth" of the level in the world. Default is
      * 0, greater means "above", lower means "below".<br/> This value is mostly
      * used for display only and is intended to make stacking of levels easier
      * to manage.
      */
    val worldDepth: Long,

    /** World X coordinate in pixels.<br/> Only relevant for world layouts where
      * level spatial positioning is manual (ie. GridVania, Free). For
      * Horizontal and Vertical layouts, the value is always -1 here.
      */
    val worldX: Long,

    /** World Y coordinate in pixels.<br/> Only relevant for world layouts where
      * level spatial positioning is manual (ie. GridVania, Free). For
      * Horizontal and Vertical layouts, the value is always -1 here.
      */
    val worldY: Long
) derives Encoder.AsObject,
      Decoder

/** Level background image position info
  */
case class LevelBackgroundPosition(
    /** An array of 4 float values describing the cropped sub-rectangle of the
      * displayed background image. This cropping happens when original is
      * larger than the level bounds. Array format: `[ cropX, cropY, cropWidth,
      * cropHeight ]`
      */
    val cropRect: Seq[Double],

    /** An array containing the `[scaleX,scaleY]` values of the **cropped**
      * background image, depending on `bgPos` option.
      */
    val scale: Seq[Double],

    /** An array containing the `[x,y]` pixel coordinates of the top-left corner
      * of the **cropped** background image, depending on `bgPos` option.
      */
    val topLeftPx: Seq[Long]
) derives Encoder.AsObject,
      Decoder

type BgPos = "Contain" | "Cover" | "CoverDirty" | "Repeat" | "Unscaled"

/** Nearby level info
  */
case class NeighbourLevel(
    /** A single lowercase character tipping on the level location (`n`orth,
      * `s`outh, `w`est, `e`ast).<br/> Since 1.4.0, this character value can
      * also be `<` (neighbour depth is lower), `>` (neighbour depth is greater)
      * or `o` (levels overlap and share the same world depth).
      */
    val dir: String,

    /** Neighbour Instance Identifier
      */
    val levelIid: String,

    /** **WARNING**: this deprecated value is no longer exported since version
      * 1.2.0 Replaced by: `levelIid`
      */
    val levelUid: Option[Long] = None
) derives Encoder.AsObject,
      Decoder

case class LdtkTableOfContentEntry(
    val identifier: String,
    val instances: Seq[ReferenceToAnEntityInstance]
) derives Encoder.AsObject,
      Decoder

/** **IMPORTANT**: this type is available as a preview. You can rely on it to
  * update your importers, for when it will be officially available. A World
  * contains multiple levels, and it has its own layout settings.
  */
case class World(
    /** Default new level height
      */
    val defaultLevelHeight: Long,

    /** Default new level width
      */
    val defaultLevelWidth: Long,

    /** User defined unique identifier
      */
    val identifier: String,

    /** Unique instance identifer
      */
    val iid: String,

    /** All levels from this world. The order of this array is only relevant in
      * `LinearHorizontal` and `linearVertical` world layouts (see `worldLayout`
      * value). Otherwise, you should refer to the `worldX`,`worldY` coordinates
      * of each Level.
      */
    val levels: Seq[Level],

    /** Height of the world grid in pixels.
      */
    val worldGridHeight: Long,

    /** Width of the world grid in pixels.
      */
    val worldGridWidth: Long,

    /** An enum that describes how levels are organized in this project (ie.
      * linearly or in a 2D space). Possible values: `Free`, `GridVania`,
      * `LinearHorizontal`, `LinearVertical`, `null`
      */
    val worldLayout: Option[WorldLayout] = None
) derives Encoder.AsObject,
      Decoder

type WorldLayout = "Free" | "GridVania" | "LinearHorizontal" | "LinearVertical"

/** Naming convention for Identifiers (first-letter uppercase, full uppercase
  * etc.) Possible values: `Capitalize`, `Uppercase`, `Lowercase`, `Free`
  */

type IdentifierStyle = "Capitalize" | "Free" | "Lowercase" | "Uppercase"

/** "Image export" option when saving project. Possible values: `None`,
  * `OneImagePerLayer`, `OneImagePerLevel`, `LayersAndLevels`
  */

type ImageExportMode = "LayersAndLevels" | "None" | "OneImagePerLayer" |
  "OneImagePerLevel"
